package com.restapi.survey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.restapi.util.Utils;

@Service
public class SurveyService {
	private static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);

	}

	public static List<Survey> getSurveys() {
		return surveys;
	}

	public static void setSurveys(List<Survey> surveys) {
		SurveyService.surveys = surveys;
	}

	public static Survey getSurveyById(String id) {
		Predicate<? super Survey> pred = s -> s.getId().equalsIgnoreCase(id);
		Optional<Survey> optionalQuestion = surveys.stream().filter(pred).findFirst();
		return optionalQuestion.isPresent() ? optionalQuestion.get() : null;
	}

	public static List<Question> getSurveyQuestions(String id) {
		Survey survey = getSurveyById(id);
		if (survey == null || survey.getQuestions() == null)
			return null;
		return survey.getQuestions();
	}

	public static Question getSurveyQuestionById(String surveyId, String questionId) {
		Survey survey = getSurveyById(surveyId);
		System.out.println("getSurveyQuestionById survey: " + survey);
		if (survey == null || survey.getQuestions() == null)
			return null;
		Predicate<? super Question> pred = s -> s.getId().equalsIgnoreCase(questionId);
		Optional<Question> optionalQuestion = survey.getQuestions().stream().filter(pred).findFirst();
		return optionalQuestion.isPresent() ? optionalQuestion.get() : null;
	}

	public static short addSurveyQuestion(String id, Question question) {
		List<Question> surveyQuestions = getSurveyQuestions(id);
		if (surveyQuestions == null) return 400;
		question.setId(Utils.genarateRandomNumber32().toString());
		surveyQuestions.add(question);
		return 201;
		
	}

}
