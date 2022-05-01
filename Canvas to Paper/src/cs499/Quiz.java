package cs499;

import static cs499.data_classes.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;

import org.json.JSONArray;
import org.json.JSONObject;

import cs499.question.Question;
import cs499.utils.DataHelper;

public class Quiz implements Reference{
	
	private int id;
	
	private String name;
	
	private String date;
	
	private Instructor instructor;
	
	private String course;
	
	private String shortCourse;
	
	private String description;
	
	private float pointsPossible;
	
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	private ArrayList<ReferenceMaterial> references;
	
	private ArrayList<QuestionGroup> groups = new ArrayList<QuestionGroup>();
	
	public Quiz() {
		newQuiz();
		this.instructor = new Instructor();
	}
	
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
		setShortCourse();
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
	
	public void sortQuestionsByType() {
		questions.sort(Comparator.comparing(Question::getType));
	}
	
	public ArrayList<Question> getQuestions() {
		return this.questions;
	}
	
	public ArrayList<ReferenceMaterial> getReferences() {
		return this.references;
	}
	
	private String metaJSON() {
		LocalDateTime timestamp = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		JSONArray metadataArray = new JSONArray();
		
		JSONObject quizMeta = new JSONObject();
		quizMeta.put("name", this.getName());
		quizMeta.put("date", timestamp.format(formatter));
		metadataArray.put(quizMeta);
		
		
		for(int i = 0; i < this.questions.size(); i++) {
			JSONObject question = new JSONObject();
			question.put("question_id", this.questions.get(i).getId());
			metadataArray.put(question);
		}
		
		return metadataArray.toString();
	}
	
	public void saveMetadata() {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            create.insertInto(
            		METADATA,
            		METADATA.QUIZ_ID,
            		METADATA.DATA)
            .values(this.getId(), 
            		this.metaJSON())
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
			setShortCourse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void newQuiz() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			this.id = create.insertInto(QUIZ, QUIZ.NAME).values("").returning(QUIZ.ID).fetchOne(QUIZ.ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveQuiz() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			if(!(course == null|| course.isEmpty())) {
				Record result = create.select(COURSE.ID)
						.from(COURSE)
						.where(COURSE.NAME.eq(course))
						.fetchOne();
				
				create.update(QUIZ)
				.set(QUIZ.COURSE_ID, result.getValue(COURSE.ID))
				.where(QUIZ.ID.eq(id))
				.execute();
			}			
			
			create.update(QUIZ)
			.set(QUIZ.NAME, name)
			.set(QUIZ.DESCRIPTION, description)
			.set(QUIZ.POINTS_POSSIBLE, pointsPossible)
			.set(QUIZ.DUE_DATE, date)
			.where(QUIZ.ID.eq(id))
			.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void setShortCourse() {
		this.shortCourse = StringUtils.substringBetween(course, "(",")");
	}
	
	public String getShortCourse() {
		return this.shortCourse;
	}
	
	public void loadQuestionGroups() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Result<Record> result = create.select()
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.QUIZ_ID.eq(id))
					.fetch();
			
			for(Record r: result) {
				groups.add(new QuestionGroup(r.getValue(QUESTION_GROUP.ID)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<QuestionGroup> getQuestionGroups(){
		return groups;
	}
	
	public void createGroup(int bankId) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			int groupId = create.insertInto(QUESTION_GROUP,
						QUESTION_GROUP.QUIZ_ID,
						QUESTION_GROUP.QUESTION_BANK_ID)
					.values(id,
							bankId)
					.returning(QUESTION_GROUP.ID)
					.fetchOne(QUESTION_GROUP.ID);
			
			groups.add(new QuestionGroup(groupId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAssociation(Question question) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			create.insertInto(QUIZ_TO_QUESTION,
					QUIZ_TO_QUESTION.QUESTION_ID,
					QUIZ_TO_QUESTION.QUIZ_ID)
			.values(question.getId(), id)
			.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
