package cs499.api_client;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.jooq.tools.json.JSONObject;


public class CanvasRestClient implements RestClient{
		
	private String canvas_ID;
	private String canvas_token;
	private String canvas_url;
	private String sandbox_ID;
	private final WebTarget webTarget;
	
	public void readDotENV() {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
		
		canvas_ID = dotenv.get("CANVAS_ID");
		canvas_token = dotenv.get("CANVAS_TOKEN");
		canvas_url = dotenv.get("CANVAS_URL");
		sandbox_ID = dotenv.get("SANDBOX_ID");		
	}
	
	public CanvasRestClient(String host) {
		
		Client client = ClientBuilder.newClient();
		
		webTarget = client.target(host);
		
	}
	

	@Override
	public JSONObject getAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getCourseQuizzes(int course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getCourseRubrics(int course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getQuizQuestions(int course, int quiz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getOutcome(int outcome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getRubric(int rubric) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getQuestionGroup(int course, int quiz, int group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadFile(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createCanvasQuiz(JSONObject quiz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String associateRubric(JSONObject rubric) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createQuestionGroup(JSONObject group) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}