package cs499;

import static cs499.data_classes.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jooq.Record;

import cs499.question.Question;

public class Quiz implements Reference{
	
	private int id;
	
	private String name;
	
	private String date;
	
	private String instructor;
	
	private String course;
	
	private String section;
	
	private String instructions;
	
	private ArrayList<Question> questions;
	
	private ArrayList<ReferenceMaterial> references;
	
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
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		for (int i = 0; i < questions.size(); i++) {
			if(questions.get(i) == question)
				questions.remove(i);			
		}
	}
	
	public void removeQuestion(int id) {
		for (int i = 0; i < questions.size(); i++) {
			if(questions.get(i).getId() == id) {
				questions.remove(i);
			}
		}
	}
	
	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}
	
	private String questionJSON() {
		JSONArray questionList = new JSONArray();
		
		for(int i = 0; i < this.questions.size(); i++) {
			JSONObject question = new JSONObject();
			question.put("id", this.questions.get(i).getId());
			questionList.put(question);
		}
		
		return questionList.toString();
		
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
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			create.insertInto(
					QUIZ_REFERENCE,
					QUIZ_REFERENCE.QUIZ_ID,
					QUIZ_REFERENCE.REFERENCE_ID)
			.values(this.getId(),
					reference.getId())
			.execute();		

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.references.add(reference);
		
	}

	@Override
	public void createReference(int id) {
		ReferenceMaterial reference = new ReferenceMaterial(id);
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			create.insertInto(
					QUIZ_REFERENCE,
					QUIZ_REFERENCE.QUIZ_ID,
					QUIZ_REFERENCE.REFERENCE_ID)
			.values(this.getId(),
					reference.getId())
			.execute();		

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.references.add(reference);
		
	}

	@Override
	public void loadReference() {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Integer[] reference_ids = create.select(QUIZ_REFERENCE.REFERENCE_ID)
					.from(QUIZ_REFERENCE)
					.where(QUIZ_REFERENCE.QUIZ_ID.eq(id))
					.fetchArray(QUIZ_REFERENCE.REFERENCE_ID);
			
			for(Integer i: reference_ids) {
				ReferenceMaterial reference = new ReferenceMaterial(i);
				this.references.add(reference);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void load() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			String courseName = create.select(COURSE.NAME)
					.from(COURSE)
					.where(COURSE.ID.eq(QUIZ.COURSE_ID))
					.fetchOne(COURSE.NAME);
			
			Record instructor =create.select()
				.from(INSTRUCTOR.join(COURSE).on(INSTRUCTOR.ID.eq(COURSE.INSTRUCTOR_ID)))
						.where(COURSE.ID.eq(QUIZ.COURSE_ID))
						.fetchOne();
						
				
			
	

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
