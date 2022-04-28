package cs499.utils;

import static cs499.data_classes.Tables.QUIZ;
import static cs499.data_classes.Tables.QUESTION_BANK;
import static cs499.data_classes.Tables.QUIZ_TO_QUESTION;
import static cs499.data_classes.Tables.QUIZ_REFERENCE;
import static cs499.data_classes.Tables.QUESTION_BANK_QUESTION;
import static cs499.data_classes.Tables.QUESTION_GROUP;
import static cs499.data_classes.Tables.QUESTION;
import static cs499.data_classes.Tables.REFERENCE_MATERIAL;
import static cs499.data_classes.Tables.INSTRUCTOR;
import static cs499.data_classes.Tables.COURSE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.QuestionBank;
import cs499.QuestionGroup;
import cs499.Quiz;
import cs499.QuizBuilder;
import cs499.Reference;
import cs499.ReferenceMaterial;
import cs499.qti.ParseUtils;
import cs499.question.Question;

public class DatabaseUtils {
	
	public static ArrayList<Quiz> getAllQuizzes() {
		
		ArrayList<Quiz> quizArray = new ArrayList<Quiz>();
		QuizBuilder builder = new QuizBuilder();
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select()
					.from(QUIZ)
					.fetch();
			
			if (result != null) {
				for(Record r:result) {
					quizArray.add(builder.buildQuiz(r.getValue(QUIZ.ID)));
				}			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return (ArrayList<Quiz>) ParseUtils.removeNull(quizArray);
	}
	
	public static ArrayList<Integer> getAllQuizIds() {
		ArrayList<Integer> quizIds = new ArrayList<Integer>();
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select()
					.from(QUIZ)
					.fetch();
			
			for(Record r: result) {
				quizIds.add(r.getValue(QUIZ.ID));
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return quizIds;
	}
	
	public static ArrayList<Quiz> getQuizzes(int courseId){
		ArrayList<Quiz> quizArray = new ArrayList<Quiz>();
		QuizBuilder builder = new QuizBuilder();
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE); 
			
			Result<Record> result = create.select()
					.from(QUIZ)
					.where(QUIZ.COURSE_ID.eq(courseId))
					.fetch();
			
			for(Record r: result) {
				quizArray.add(builder.buildQuiz(r.getValue(QUIZ.ID)));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return quizArray;
	}
	
	public static ArrayList<Integer> getQuizIds(int courseId){
		ArrayList<Integer> quizIds = new ArrayList<Integer>();
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select()
					.from(QUIZ)
					.where(QUIZ.COURSE_ID.eq(courseId))
					.fetch();
			
			for(Record r: result) {
				quizIds.add(r.getValue(QUIZ.ID));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return quizIds;
	}
	
	//TODO: bank builder?
	public static ArrayList<QuestionBank> getAllBanks(){
		ArrayList<QuestionBank> bankArray = new ArrayList<QuestionBank>();
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select()
					.from(QUESTION_BANK)
					.fetch();
			
			for(Record r: result) {
				QuestionBank bank = new QuestionBank(r.getValue(QUESTION_BANK.ID));
				bankArray.add(bank);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bankArray;
		
	}
	
	public static void deleteQuiz(int quizId) {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			create.delete(QUIZ_TO_QUESTION).where(QUIZ_TO_QUESTION.QUIZ_ID.eq(quizId)).execute();
			
			create.delete(QUIZ_REFERENCE).where(QUIZ_REFERENCE.QUIZ_ID.eq(quizId)).execute();
			
			create.delete(QUIZ).where(QUIZ.ID.eq(quizId)).execute();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteBank(int bankId) {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			create.delete(QUESTION_BANK_QUESTION).where(QUESTION_BANK_QUESTION.QUESTION_BANK_ID.eq(bankId)).execute();
			
			create.update(QUESTION_GROUP).set(QUESTION_GROUP.QUESTION_BANK_ID, 0).where(QUESTION_GROUP.QUESTION_BANK_ID.eq(bankId)).execute();
			
			create.delete(QUESTION_BANK).where(QUESTION_BANK.ID.eq(bankId)).execute();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<QuestionGroup> getQuestionGroups(int quizId){
		ArrayList<QuestionGroup> groupList = new ArrayList<QuestionGroup>();
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select().from(QUESTION_GROUP).where(QUESTION_GROUP.QUIZ_ID.eq(quizId)).fetch();
			
			for(Record r: result) {
				groupList.add(new QuestionGroup(r.getValue(QUESTION_GROUP.ID)));
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return groupList;
	}
	
	public static ArrayList<QuestionGroup> getQuestionGroups(Quiz quiz){
		ArrayList<QuestionGroup> groupList = new ArrayList<QuestionGroup>();
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select().from(QUESTION_GROUP).where(QUESTION_GROUP.QUIZ_ID.eq(quiz.getId())).fetch();
			
			for(Record r: result) {
				groupList.add(new QuestionGroup(r.getValue(QUESTION_GROUP.ID)));
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return groupList;
	}
	
	public static ArrayList<Integer> getQuestionGroupIds(Quiz quiz){
		ArrayList<Integer> idList = new ArrayList<Integer>();
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select().from(QUESTION_GROUP).where(QUESTION_GROUP.QUIZ_ID.eq(quiz.getId())).fetch();
			
			for(Record r: result) {
				idList.add(r.getValue(QUESTION_GROUP.ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
	}
		return idList;
	}
	
	public static ArrayList<Integer> getQuestionGroupIds(int quizId){
		ArrayList<Integer> idList = new ArrayList<Integer>();
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Result<Record> result = create.select().from(QUESTION_GROUP).where(QUESTION_GROUP.QUIZ_ID.eq(quizId)).fetch();
			
			for(Record r: result) {
				idList.add(r.getValue(QUESTION_GROUP.ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
	}
		return idList;
	}
	
	public static void deleteReference(ReferenceMaterial reference) {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			create.delete(QUIZ_REFERENCE)
			.where(QUIZ_REFERENCE.REFERENCE_ID.eq(reference.getId()))
			.execute();
			
			create.update(QUESTION)
			.setNull(QUESTION.REFERENCE_ID)
			.where(QUESTION.REFERENCE_ID.eq(reference.getId()))
			.execute();
			
			create.delete(REFERENCE_MATERIAL)
			.where(REFERENCE_MATERIAL.ID.eq(reference.getId()))
			.execute();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void clearAllData() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			create.truncate(COURSE);
			create.truncate(QUIZ);
			create.truncate(INSTRUCTOR);
			create.truncate(QUESTION);
			create.truncate(QUIZ_TO_QUESTION);
			create.truncate(QUESTION_GROUP);
			create.truncate(QUESTION_BANK);
			create.truncate(QUESTION_BANK_QUESTION);
			create.truncate(REFERENCE_MATERIAL);
			create.truncate(QUIZ_REFERENCE);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
