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
import cs499.question.AnswerFormatter;
import cs499.question.QuestionType;

public class QtiToDB {
	
	private static final int FIRST = 0;
	
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
	
	@SuppressWarnings("unchecked")
	public static String parseAnswers(ArrayList<Object> correctAnswers, ArrayList<Object> allChoices, HashMap<String, String> questionInfo) {
		
		QuestionType type = valueOf(questionInfo.get("question_type"));
		
		switch(type) {
		case MATCHING:
		case MULTIPLE_ANSWERS:
			return findMatches(correctAnswers, allChoices);
		case MULTIPLE_CHOICE:
		case TRUE_FALSE:
		case MULTIPLE_DROPDOWNS:
			String correct = findCorrect((HashMap<String, String>) correctAnswers.get(FIRST), allChoices);
			ArrayList<String> choices = findChoices(allChoices);
			return AnswerFormatter.answerJSONString(correct, choices);			
		case CALCULATED:
		case ESSAY:		
		case MULTIPLE_BLANKS:		
		case NUMERICAL:
		case SHORT_ANSWER:
			ArrayList<String> answers = new ArrayList<String>();
			for(Object i: correctAnswers) {
				answers.add(findCorrect((HashMap<String,String>) i, allChoices));
			}
			return AnswerFormatter.answerJSONString(answers);
		case TEXT_ONLY:
		case FILE_UPLOAD:
			// no answers for these		
		default:
			break;		
		}		
		return null;
	}
	
	
	private static String findCorrect(HashMap<String,String> correct, ArrayList<Object> allChoices) {
		//find correct answer
		String ident = correct.get("answer_ident");
		String answer = "";
		for(Object o: allChoices) {
			if(((HashMap<?,?>)o).containsValue(ident)) {
				answer = (String) ((HashMap<?,?>)o).get("answer_value");
			}
		}		
		return answer;
		
		//get answer_ident from correct
		//find the matching answer_ident from all
		//return answer_value from all
		//answer_name is only used for matching questions
	}
	
	@SuppressWarnings("unchecked")
	private static ArrayList<String> findChoices(ArrayList<Object> allChoices) {
		ArrayList<String> choices = new ArrayList<String>();
		for(Object o: allChoices) {
			choices.add(((HashMap<String,String>)o).get("answer_value"));	
		}		
		return choices;
		//get answer_value from allChoices
		//return array of just answer text
		
	}
	
	private static String findMatches(ArrayList<Object> correct, ArrayList<Object> all) {
		//correct should contain the left-right association information
		//all should contain all the options
		HashMap<String,String> matches = new HashMap<String,String>();
		ArrayList<String> keys = new ArrayList<String>();
		for(Object o: all) {
			keys.add((String) ((HashMap<?,?>)o).get("answer_name"));
			String ident = (String) ((HashMap<?,?>)o).get("matching_ident");
			for(Object j: correct) {
				if(((HashMap<?,?>)j).get("response_ident") == ident) {
					String answerIdent = (String) ((HashMap<?,?>)j).get("answer_ident");
					if(((HashMap<?,?>)o).get("answer_ident") == answerIdent) {
						matches.put((String) ((HashMap<?,?>)o).get("answer_name"),
								(String) ((HashMap<?,?>)o).get("answer_value"));
					}
				}
				
			}
				
		}
		return AnswerFormatter.answerJSONString(keys,matches);
	}

}
