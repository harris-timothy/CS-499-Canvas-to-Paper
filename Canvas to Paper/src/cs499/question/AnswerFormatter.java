package cs499.question;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class AnswerFormatter {
	
	public static String answerJSONString(ArrayList<String> answers) {
		
		JSONArray answerArray = new JSONArray();
		
		for(String a: answers) {
			JSONObject answer = new JSONObject();
			answer.put("answer", a);
			answerArray.put(answer);		
		}
		
		return answerArray.toString();
		
	}
	
	public static String answerJSONString(String answer, ArrayList<String> choices) {
		JSONArray answerArray = new JSONArray();
		
		JSONObject correct = new JSONObject();
		correct.put("answer", answer);
		correct.put("correct", true);
		
		answerArray.put(correct);
		
		for(String s : choices) {
			JSONObject wrongAnswer = new JSONObject();
			wrongAnswer.put("answer", s);
			wrongAnswer.put("correct", false);
			answerArray.put(wrongAnswer);
		}
		
		return answerArray.toString();
	}
	
	public static String answerJSONString(ArrayList<String> left, HashMap<String, String> right) {
		JSONArray answerArray = new JSONArray();
		
		for(String key : left)
		{
			JSONObject match = new JSONObject();
			match.put("left", key);
			match.put("right", right.get(key));
			answerArray.put(match);
		}
		
		return answerArray.toString();
	}
	
	public static ArrayList<String> answerArray(String answerJSONString) {
		
		JSONArray answerJSON = new JSONArray(answerJSONString);
		ArrayList<String> answers = new ArrayList<String>();
		
		for(int i = 0; i < answerJSON.length(); i++) {
			answers.add(answerJSON.getJSONObject(i).getString("answer"));	
		}
		
		return answers;
	}
	
	public static String correctAnswer(String answerJSONString) {
		
		JSONArray answerJSON = new JSONArray(answerJSONString);
		String correct = "";
		
		for(int i = 0; i < answerJSON.length(); i++) {
			if(answerJSON.getJSONObject(i).getBoolean("correct") == true) {
				correct = answerJSON.getJSONObject(i).getString("answer");
			}
		}
		
		return correct;
	}
	
	public static ArrayList<String> choicesArray(String answerJSONString) {
		
		JSONArray answerJSON = new JSONArray(answerJSONString);
		ArrayList<String> choices = new ArrayList<String>();
		for(int i = 0; i < answerJSON.length(); i++) {
			choices.add(answerJSON.getJSONObject(i).getString("answer"));
		}
				
		return choices;
	}
	
	public static ArrayList<String> keyArray(String answerJSONString) {
		
		JSONArray answerJSON = new JSONArray(answerJSONString);
		ArrayList<String> keys = new ArrayList<String>();
		for(int i = 0; i < answerJSON.length(); i++) {
			keys.add(answerJSON.getJSONObject(i).getString("left"));
		}
		
		return keys;
	}
	
	public static HashMap<String, String> answerMap(String answerJSONString) {
		
		JSONArray answerJSON = new JSONArray(answerJSONString);
		HashMap<String, String> matches = new HashMap<String, String>();
		for(int i = 0; i < answerJSON.length(); i++) {
			matches.put(answerJSON.getJSONObject(i).getString("left"),
					answerJSON.getJSONObject(i).getString("right"));
		}
		return matches;
	}
}
