package com.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.restapi.service.SurveyService;
import com.restapi.survey.Question;

// Launch web context with SurveyResource
@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
class SurveyResourceTest {

	// Mock -> SurveyService.getSurveyQuestionById(id, questionId);
	@MockBean
	private SurveyService surveyService; // Not necessary as my methods are static, but I'll play along.

	// GET http://localhost:8081/surveys/Survey1/questions/Question1
	@Autowired
	private MockMvc mocMvc;

	private static final String SPECIFIC_QUESTION_URL = "http://localhost:8081/surveys/Survey1/questions/Question1";

	private static final String GENERIC_QUESTION_URL = "http://localhost:8081/surveys/Survey1/questions";
	
	private static final String expectedSurvey1Question1 = """
			{
			    "id": "Question1",
			    "description": "Most Popular Cloud Platform Today",
			    "options": [
			        "AWS",
			        "Azure",
			        "Google Cloud",
			        "Oracle Cloud"
			    ],
			    "correctAnswer": "AWS"
			}

						""";


	private String expectedSurvey1Question4 = """
			{
			  "id": "Question4",
			  "description": "Blah Blah Blah",
			  "options": [
			    "AWS",
			    "Azure",
			    "Google Cloud",
			    "Oracle Cloud"
			  ],
			  "correctAnswer": "AWS"
			}
							""";
	@Test
	void getSurveyQuestion_404Scenario() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mocMvc.perform(requestBuilder).andReturn();
		assertTrue(mvcResult.getResponse().getStatus() == 404);
	}

	@Test
	void getSurveyQuestion_basicScenario() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);

		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

		when(surveyService.getSurveyQuestionById("Survey1", "Question1")).thenReturn(question1);

		MvcResult mvcResult = mocMvc.perform(requestBuilder).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 200);

		JSONAssert.assertEquals(expectedSurvey1Question1, mvcResult.getResponse().getContentAsString(), true);
	}


	@Test
	void postSurveyQuestion_basicScenario() throws Exception {

		String newQuestionRequestBody = """
				{
				    "description": "Some Other Cloud Platform",
				    "options": [
				        "Kubernetes",
				        "Docker",
				        "Terraform",
				        "Azure DevOps"
				    ],
				    "correctAnswer": "Kubernetes"
				}

								""";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GENERIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON).content(newQuestionRequestBody).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mocMvc.perform(requestBuilder).andReturn();

		assertTrue(mvcResult.getResponse().getStatus() == 201);

		JSONAssert.assertEquals(newQuestionRequestBody, mvcResult.getResponse().getContentAsString(), false);
	}
}
