package com.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIntegrationTest {

	private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question4";

	private static final String SURVEY1_ALL_QUESTIONS_URL = "/surveys/Survey1/questions";

	private static final String SPECIFIC_SURVEY_URL = "/surveys/Survey1";

	private static final String ALL_SURVEYS_URL = "/surveys";

	private String expectedSurvey1Question1 = """
			{
			  "id": "Question4",
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

	private String expectedSurvey1AllQuestions = """
						[
			    {
			        "id": "Question1"
			    },
			    {
			        "id": "Question2"
			    },
			    {
			        "id": "Question3"
			    },
			    {
			        "id": "Question4"
			    }
			]

									""";
	private String expectedSurvey1 = """
						{
			    "id": "Survey1",
			    "title": "My Favorite Survey",
			    "description": "Description of the Survey",
			    "questions": [
			        {
			            "id": "Question1"
			        },
			        {
			            "id": "Question2"
			        },
			        {
			            "id": "Question3"
			        },
			        {
			            "id": "Question4"
			        }
			    ]
			}

						""";

	private String expectedAllSurveys = """
			[
			    {
			        "id": "Survey1",
			        "title": "My Favorite Survey",
			        "description": "Description of the Survey"
			    }
			]
						""";

	@Autowired
	TestRestTemplate template;

	@Test
	void getSpecificSurveyQuestion_basicScenario() throws JSONException {

		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedSurvey1Question1, responseEntity.getBody(), true);
	}

	@Test
	void getSurveyQuestions_basicScenario() throws JSONException {

		ResponseEntity<String> responseEntity = template.getForEntity(SURVEY1_ALL_QUESTIONS_URL, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedSurvey1AllQuestions, responseEntity.getBody(), false);
	}

	@Test
	void getSpecificSurvey_basicScenario() throws JSONException {

		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_SURVEY_URL, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedSurvey1, responseEntity.getBody(), false);
	}

	@Test
	void getAllSurveys_basicScenario() throws JSONException {

		ResponseEntity<String> responseEntity = template.getForEntity(ALL_SURVEYS_URL, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedAllSurveys, responseEntity.getBody(), false);
	}
}
