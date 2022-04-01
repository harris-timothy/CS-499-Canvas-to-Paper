package cs499.qti;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.data_classes.Tables.QUIZ;
import static cs499.data_classes.Tables.QUESTION_BANK_QUESTION;
import static cs499.data_classes.Tables.QUIZ_TO_QUESTION;
import static cs499.data_classes.Tables.QUESTION_GROUP;
import static cs499.data_classes.Tables.QUESTION_BANK;
import static cs499.question.QuestionType.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.DataHelper;
import cs499.question.QuestionType;

public class QtiToDB {
	
	public static int storeQuiz(HashMap<String, String> data) {
		int quizId = 0;
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Record exists = create.select()
					.from(QUIZ)
					.where(QUIZ.QTI_ID.eq(data.get("qti_id")))
					.fetchOne();
			
			if(exists == null) {
				create.insertInto(QUIZ,
						QUIZ.QTI_ID,
						QUIZ.NAME)
				.values(data.get("quiz_qti_id"),
						data.get("quiz_title"))
				.execute();				
			}
			else {
				create.update(QUIZ)
				.set(QUIZ.NAME, data.get("name"))
				.where(QUIZ.QTI_ID.eq(data.get("qti_id")))
				.execute();
			}
			
			quizId = create.select(QUIZ.ID)
					.from(QUIZ)
					.where(QUIZ.QTI_ID.eq(data.get("quiz_qti_id")))
					.fetchOne(QUIZ.ID);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return quizId;
		
	}
	
	public static int storeQuestion(HashMap<String, String> data) {
		int questionId = 0;
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION)
					.where(QUESTION.QTI_ID.eq(data.get("qti_id")))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUESTION,
						QUESTION.QTI_ID,
						QUESTION.NAME,
						QUESTION.DESCRIPTION,
						QUESTION.TYPE,
						QUESTION.ANSWERS,
						QUESTION.POINTS_POSSIBLE)
				.values(data.get("qti_id"),
						data.get("name"),
						data.get("description"),
						data.get("question_type"),
						data.get("answers"),
						Float.parseFloat(data.get("points_possible")))
				.execute();

			}
			else {
				create.update(QUESTION)
				.set(QUESTION.NAME, data.get("name"))
				.set(QUESTION.DESCRIPTION, data.get("description"))
				.set(QUESTION.TYPE, data.get("question_type"))
				.set(QUESTION.ANSWERS, data.get("answers"))
				.set(QUESTION.POINTS_POSSIBLE, Float.parseFloat(data.get("points_possible")))
				.where(QUESTION.QTI_ID.eq(data.get("qti_id")))
				.execute();
			}
			
			questionId = create.select(QUESTION.ID)
			.from(QUESTION)
			.where(QUESTION.QTI_ID.eq(data.get("qti_id")))
			.fetchOne(QUESTION.ID);			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return questionId;
	}
	
	public static int storeQuestionBank(HashMap<String, String> data) {
		int bankId = 0;
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION_BANK)
					.where(QUESTION_BANK.QTI_ID.eq(data.get("qti_id")))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUESTION_BANK,
						QUESTION_BANK.QTI_ID,
						QUESTION_BANK.NAME)
				.values(data.get("qti_id"),
						data.get("bank_title"))
				.execute();
			}
			
			bankId = create.select(QUESTION_BANK.ID)
					.from(QUESTION_BANK)
					.where(QUESTION_BANK.QTI_ID.eq(data.get("qti_id")))
					.fetchOne(QUESTION_BANK.ID);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankId;
		
	}
	
	public static void storeQuizQuestion(int quizId, int questionId) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUIZ_TO_QUESTION)
					.where(QUIZ_TO_QUESTION.QUIZ_ID.eq(quizId))
					.and(QUIZ_TO_QUESTION.QUESTION_ID.eq(questionId))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUIZ_TO_QUESTION,
						QUIZ_TO_QUESTION.QUIZ_ID,
						QUIZ_TO_QUESTION.QUESTION_ID)
				.values(quizId,
						questionId)
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void storeBankQuestion(int bankId, int questionId) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION_BANK_QUESTION)
					.where(QUESTION_BANK_QUESTION.QUESTION_BANK_ID.eq(bankId))
					.and(QUESTION_BANK_QUESTION.QUESTION_ID.eq(questionId))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUESTION_BANK_QUESTION,
						QUESTION_BANK_QUESTION.QUESTION_BANK_ID,
						QUESTION_BANK_QUESTION.QUESTION_ID)
				.values(bankId,
						questionId)
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void storeQuestionGroup(HashMap<String, String> data) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			int bankId = create.select(QUESTION_BANK.ID)
					.from(QUESTION_BANK)
					.where(QUESTION_BANK.QTI_ID.eq(data.get("bank_qti_id")))
					.fetchOne(QUESTION_BANK.ID);

			Record exists = create.select()
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.QUIZ_ID.eq(Integer.parseInt(data.get("quiz_id"))))
					.and(QUESTION_GROUP.QUESTION_BANK_ID.eq(bankId))
					.and(QUESTION_GROUP.NAME.eq(data.get("group_name")))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUESTION_GROUP,
						QUESTION_GROUP.QUIZ_ID,
						QUESTION_GROUP.QUESTION_BANK_ID,
						QUESTION_GROUP.PICK_COUNT,
						QUESTION_GROUP.QUESTION_POINTS,
						QUESTION_GROUP.NAME)
				.values(Integer.parseInt(data.get("quiz_id")),
						bankId,
						Integer.parseInt(data.get("pick_count")),
						Float.parseFloat(data.get("points_per_item")),
						data.get("group_name"))
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static String parseAnswers(ArrayList<Object> correctAnswers, ArrayList<Object> allChoices, HashMap<String, String> questionInfo) {
		
		QuestionType type = valueOf(questionInfo.get("question_type"));
		
		switch(type) {
		case MATCHING:
		case MULTIPLE_ANSWERS:
			//matching stuff
			//answer_name = left
			//to find matching right
			//get the corresponding answer_value
			//where answer_ident from correctanswers matches ident from allchoices
			break;
		case MULTIPLE_CHOICE:
		case TRUE_FALSE:
		case MULTIPLE_DROPDOWNS:
			//multiple choice stuff
			//get "answer_ident" from correctanswers(responselid)
			//use that ident to find value from allchoices
			//corretanswers->ident should = allchoices->ident
			//answer text = "answer_value"
			//string for correct answer
			//to find choices
			//allchoices -> answer_value
			//arraylist for options
			
			break;
		case CALCULATED:
		case ESSAY:		
		case MULTIPLE_BLANKS:		
		case NUMERICAL:
		case SHORT_ANSWER:
			//single answer stuff
			//get "answer_ident" from correctanswers(responselid or responsestr)
			//use that ident to find value from allchoices
			//correctanswers->ident should = allchoices->ident
			//answer text = "answer_value"
			
			break;
		case TEXT_ONLY:
		case FILE_UPLOAD:
			// no answers for these		
		default:
			break;		
		}
		
		
		
		return null;
	}
	
	

}
