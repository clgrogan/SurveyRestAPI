package com.restapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.restapi.survey.Question;
import com.restapi.survey.Survey;
import com.restapi.util.Utils;

@Service
public class SurveyService {
	private static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1","Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2","Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3","Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
		Question question4 = new Question("Question4","Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3, question4));

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
		Optional<Survey> optionalQuestion = surveys.stream().filter(surveyPredicate(id)).findFirst();
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
		Optional<Question> optionalQuestion = survey.getQuestions().stream().filter(questionPredicate(questionId))
				.findFirst();
		return optionalQuestion.isPresent() ? optionalQuestion.get() : null;
	}

	public static Question addSurveyQuestion(String id, Question question) {
		List<Question> surveyQuestions = getSurveyQuestions(id);
		if (surveyQuestions == null)
			return null;
		question.setId(Utils.genarateRandomNumber32().toString());
		surveyQuestions.add(question);
		return question;

	}

	public static boolean deleteSurveyQuestion(String surveyId, String questionId) {

		List<Question> questions = getSurveyQuestions(surveyId);
		if (questions == null)
			return false;
		return questions.removeIf(questionPredicate(questionId));
	}
	public static boolean updateSurveyQuestion(String surveyId, Question updatedQuestion) {

		Question question = getSurveyQuestionById(surveyId, updatedQuestion.getId());
		if (question == null)
			return false;
		question.setDescription(updatedQuestion.getDescription());
		question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
		question.setOptions(updatedQuestion.getOptions());
		return true;
	}

	private static Predicate<? super Question> questionPredicate(String id) {
		Predicate<? super Question> pred = s -> s.getId().equalsIgnoreCase(id);
		return pred;
	}

	private static Predicate<? super Survey> surveyPredicate(String id) {
		Predicate<? super Survey> pred = s -> s.getId().equalsIgnoreCase(id);
		return pred;
	}

}
