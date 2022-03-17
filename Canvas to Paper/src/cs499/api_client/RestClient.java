package cs499.api_client;

import java.io.File;

import org.jooq.tools.json.JSONObject;

public interface RestClient {
	
	//Functions to get information from API
	
	JSONObject getAllCourses();
	
	JSONObject getCourseQuizzes(int course);
	
	JSONObject getQuizQuestions(int course, int quiz);
	
	JSONObject getQuestionGroup(int course, int quiz, int group);
	
	//Functions to send information to API
	
	String uploadFile(File file);
	
	String createCanvasQuiz(JSONObject quiz);
	
	String createQuestionGroup(JSONObject group);
	

}
