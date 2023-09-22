package com.restapi.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.restapi.survey.Question;
import com.restapi.survey.Survey;
import com.restapi.service.SurveyService;

@RestController
public class SurveyResource {

	// /surveys
	@GetMapping("/surveys")
	public List<Survey> getAllSurveys() {
		return SurveyService.getSurveys();
	}

	@GetMapping("/surveys/{id}")
	public Survey getSurveyById(@PathVariable String id) {
		Survey survey = SurveyService.getSurveyById(id);
		if (survey == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return survey;
	}

	@GetMapping("/surveys/{id}/questions")
	public List<Question> getSurveyQuestions(@PathVariable String id) {
		List<Question> questions = SurveyService.getSurveyQuestions(id);
		if (questions == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return questions;
	}

	@PostMapping("/surveys/{id}/questions")
	public ResponseEntity<Question> addSurveyQuestion(@PathVariable String id, @RequestBody Question question) {
		
		Question createdQuestion = SurveyService.addSurveyQuestion(id, question);
		if (createdQuestion == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Question>(createdQuestion,HttpStatus.CREATED);
	}
	@GetMapping("/surveys/{id}/questions/{questionId}")
	public Question getSurveyQuestion(@PathVariable String id, @PathVariable String questionId) {
		Question question = SurveyService.getSurveyQuestionById(id, questionId);
		if (question == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return question;
	}
	@DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		boolean deleted = SurveyService.deleteSurveyQuestion(surveyId,questionId);
		return deleted? ResponseEntity.noContent().build(): ResponseEntity.badRequest().build();
	}
	@PutMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
		boolean updated = SurveyService.updateSurveyQuestion(surveyId, question);
		return updated ? ResponseEntity.noContent().build(): ResponseEntity.badRequest().build();
	}

}
