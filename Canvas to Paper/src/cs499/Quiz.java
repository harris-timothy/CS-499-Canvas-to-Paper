package cs499;

import static cs499.data_classes.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import cs499.question.AnswerFormatter;
import cs499.question.Question;
import cs499.utils.DataHelper;

public class Quiz implements Reference{
	
	private int id;
	
	private String name;
	
	private String date;
	
	private Instructor instructor;
	
	private String course;
	
	private String description;
	
	private float pointsPossible;
	
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	private ArrayList<ReferenceMaterial> references;
	
	public Quiz(int id) {
		this.id = id;
		loadQuiz();
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
	
	public Instructor getInstructor() {
		return this.instructor;
	}
	
	public String getCourse() { 
		return this.course;
	}
	
	public void setCourse(String course) { // Different table
		this.course = course;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getPointsPossible() {
		return this.pointsPossible;
	}
	
	public void setPointsPossible(float pointsPossible) {
		this.pointsPossible = pointsPossible;
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
	
	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}
	
	public ArrayList<Question> getQuestions() {
		return this.questions;
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
	
	public void loadQuiz() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record result = create.select()
					.from(QUIZ)
					.where(QUIZ.ID.eq(id))
					.fetchOne();
			
			String courseName = create.select(COURSE.NAME)
                    .from(COURSE)
                    .where(COURSE.ID.eq(result.getValue(QUIZ.COURSE_ID)))
                    .fetchOne(COURSE.NAME);
			
			Record instructorRecord = create.select(COURSE.INSTRUCTOR_ID)
                    .from(COURSE)
                    .where(COURSE.ID.eq(result.getValue(QUIZ.COURSE_ID)))
                    .fetchOne();
			
			if(instructorRecord != null) {
				if(instructorRecord.getValue(COURSE.INSTRUCTOR_ID) != null) {
					this.instructor = new Instructor(instructorRecord.getValue(COURSE.INSTRUCTOR_ID));
				}
			}

			if(result != null) {
				setName(result.getValue(QUIZ.NAME));
				setId(result.getValue(QUIZ.ID));
				setDate(result.getValue(QUIZ.DUE_DATE));
				setDescription(result.getValue(QUIZ.DESCRIPTION));
				
				if(result.getValue(QUIZ.POINTS_POSSIBLE) != null) {
					setPointsPossible(result.getValue(QUIZ.POINTS_POSSIBLE));
				}
				

			}			
			
			setCourse(courseName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	

	
}
