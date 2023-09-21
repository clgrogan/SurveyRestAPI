package com.restapi.survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

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
//		for (Survey survey:surveys) {
//			if(survey.getId().equals(id)) return survey;
//		}
//		List<Survey> surveyResult = surveys.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
//		if (surveyResult.size() > 0) return surveyResult.get(0);
		Predicate<? super Survey> pred = s -> s.getId().equalsIgnoreCase(id);
		Optional<Survey> findFirst = surveys.stream().filter(pred).findFirst();
		return findFirst.isPresent() ? findFirst.get() : null;
	}

	public static List<Question> getSurveyQuestions(String id) {
		Survey survey = getSurveyById(id);
		if (survey == null || survey.getQuestions() == null)
			return null;
		return survey.getQuestions();
	}

	public static Question getSurveyQuestionById(String surveyId, String questionId) {
		Survey survey = getSurveyById(surveyId);
		if (survey == null || survey.getQuestions() == null);
		Predicate<? super Question> pred = s -> s.getId().equalsIgnoreCase(questionId);
		Optional<Question> findFirst = survey.getQuestions().stream().filter(pred).findFirst();
		return findFirst.isPresent() ? findFirst.get() : null;
	}

}
