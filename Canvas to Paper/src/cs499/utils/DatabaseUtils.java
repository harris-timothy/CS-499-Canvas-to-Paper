package cs499.utils;

import static cs499.data_classes.Tables.QUIZ;
import static cs499.data_classes.Tables.QUESTION_BANK;
import static cs499.data_classes.Tables.QUIZ_TO_QUESTION;
import static cs499.data_classes.Tables.QUIZ_REFERENCE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.QuestionBank;
import cs499.Quiz;
import cs499.QuizBuilder;
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
			
			for(Record r:result) {
				quizArray.add(builder.buildQuiz(r.getValue(QUIZ.ID)));
			}			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return quizArray;
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
	
	public static ArrayList<Question> getGroupQuestions(){
		return null;
	}
	
	public static ArrayList<Integer> getQuestionGroupIds(Quiz quiz){
		return null;
	}

}
