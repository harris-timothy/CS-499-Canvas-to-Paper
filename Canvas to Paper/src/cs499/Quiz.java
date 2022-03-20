package cs499;

import static cs499.data_classes.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cs499.question.Question;
import cs499.question.SingleAnswerQuestion;

public class Quiz implements Reference{
	
	private int id;
	
	private String name;
	
	private String date;
	
	private String instructor;
	
	private String course;
	
	private String section;
	
	private String instructions;
	
	private ArrayList<Question> questions;
	
	public Quiz(String name, String date, String instructor, String course, String section, String instructions) {
		this.name = name;
		this.date = date;
		this.instructor = instructor;
		this.course = course;
		this.section = section;
		this.instructions = instructions;
	}
	
	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getInstructor() {
		return this.instructor;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public String getCourse() {
		return this.course;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getSection() {
		return this.section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	public String getInstructions() {
		return this.instructions;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public void addQuestion(SingleAnswerQuestion question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(SingleAnswerQuestion question) {
		for (int i = 0; i < questions.size(); i++) {
			if(questions.get(i) == question)
				questions.remove(i);			
		}
	}
	
	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}
	
	private String questionJSON() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			ObjectNode quiz = mapper.createObjectNode();
			
			for (Integer i = 0; i < questions.size(); i++) {
				quiz.put(i.toString(), questions.get(i).getId().toString());
			}
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz);
			return json;
			
		} catch(Exception e) {
			e.printStackTrace();
			return "Error creating JSON String";
		}
	}
	
	public void saveMetadata() {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            create.insertInto(
            		METADATA,
            		METADATA.QUIZ_ID,
            		METADATA.DATA)
            .values(this.getId(), 
            		this.questionJSON())
            .execute();
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attachReference(ReferenceMaterial reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createReference(int id) {
		// TODO Auto-generated method stub
		
	}

	
}
