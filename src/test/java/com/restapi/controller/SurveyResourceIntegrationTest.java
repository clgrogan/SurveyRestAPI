package com.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIntegrationTest {

	private static final String APPLICATION_JSON = "application/json";

	private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question4";

	private static final String SURVEY1_ALL_QUESTIONS_URL = "/surveys/Survey1/questions";

	private static final String SPECIFIC_SURVEY_URL = "/surveys/Survey1";

	private static final String ALL_SURVEYS_URL = "/surveys";

	private String expectedSurvey1Question4 = """
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
	private String expectedSurvey1QuestionPost = """
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
		HttpHeaders headers = createHttpHeaders_ContentType_Security();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_QUESTION_URL, HttpMethod.GET, httpEntity,
				String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedSurvey1Question4, responseEntity.getBody(), true);
	}

	@Test
	void getSurveyQuestions_basicScenario() throws JSONException {
		HttpHeaders headers = createHttpHeaders_ContentType_Security();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity = template.exchange(SURVEY1_ALL_QUESTIONS_URL, HttpMethod.GET, httpEntity,
				String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedSurvey1AllQuestions, responseEntity.getBody(), false);
	}

	@Test
	void getSpecificSurvey_basicScenario() throws JSONException {
		HttpHeaders headers = createHttpHeaders_ContentType_Security();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_SURVEY_URL, HttpMethod.GET, httpEntity,
				String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedSurvey1, responseEntity.getBody(), false);
	}

	@Test
	void getAllSurveys_basicScenario() throws JSONException {
		HttpHeaders headers = createHttpHeaders_ContentType_Security();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity = template.exchange(ALL_SURVEYS_URL, HttpMethod.GET, httpEntity,
				String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedAllSurveys, responseEntity.getBody(), false);
	}

	@Test
	void postSurveyQuestion_basicScenario() throws JSONException {

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
		HttpHeaders headers = createHttpHeaders_ContentType_Security();

		HttpEntity<String> httpEntity = new HttpEntity<String>(newQuestionRequestBody, headers);

		ResponseEntity<String> responseEntity = template.exchange(SURVEY1_ALL_QUESTIONS_URL, HttpMethod.POST,
				httpEntity, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertEquals(APPLICATION_JSON, responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedSurvey1QuestionPost, responseEntity.getBody(), false);

		// A side effect occurs with the creation of the new question.
	}

	private HttpHeaders createHttpHeaders_ContentType_Security() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", APPLICATION_JSON);
		headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin", "password"));
		return headers;
	}

	String performBasicAuthEncoding(String user, String password) {
		String combined = user + ":" + password;
		// Base64 Encoding to bytes
		byte[] encoded = Base64.getEncoder().encode(combined.getBytes());

		return new String(encoded);
	}
}
