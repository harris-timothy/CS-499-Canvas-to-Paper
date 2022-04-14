package cs499.qti;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.data_classes.Tables.QUIZ;
import static cs499.data_classes.Tables.QUESTION_BANK_QUESTION;
import static cs499.data_classes.Tables.QUIZ_TO_QUESTION;
import static cs499.data_classes.Tables.QUESTION_GROUP;
import static cs499.data_classes.Tables.QUESTION_BANK;
import static cs499.data_classes.Tables.COURSE;
import static cs499.question.QuestionType.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.question.AnswerFormatter;
import cs499.question.QuestionType;
import cs499.utils.DataHelper;

public class QtiToDB {
	
	private static final int FIRST = 0;
	
	public static int storeQuiz(HashMap<String, String> data) {
		int quizId = 0;
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			Record exists = create.select()
					.from(QUIZ)
					.where(QUIZ.NAME.eq(data.get("quiz_title")))
					.fetchOne();
			
			if(exists == null) {
				quizId =  create.insertInto(QUIZ,
						QUIZ.QTI_ID,
						QUIZ.NAME)
				.values(data.get("quiz_qti_id"),
						data.get("quiz_title"))
				.onDuplicateKeyIgnore()
				.returningResult(QUIZ.ID)
				.fetchOne(QUIZ.ID);
			}
			else {
				quizId = exists.getValue(QUIZ.ID);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return quizId;
		
	}
	
	public static void storeQuizMeta(HashMap<String,String> data) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Record exists = create.select()
					.from(QUIZ)
					.where(QUIZ.QTI_ID.eq(data.get("qti_id")))
					.fetchOne();
			
			if(exists == null) {
				create.insertInto(QUIZ,
						QUIZ.QTI_ID,
						QUIZ.NAME,
						QUIZ.DESCRIPTION,
						QUIZ.POINTS_POSSIBLE,
						QUIZ.DUE_DATE)
				.values(data.get("qti_id"),
						data.get("name"),
						data.get("description"),
						Float.parseFloat(data.get("points_possible")),
						data.get("due_date"))
				.execute();				
			}
			else {
				create.update(QUIZ)
				.set(QUIZ.NAME, data.get("name"))
				.set(QUIZ.DESCRIPTION, data.get("description"))
				.set(QUIZ.POINTS_POSSIBLE, Float.parseFloat(data.get("points_possible")))
				.set(QUIZ.DUE_DATE, data.get("due_date"))
				.where(QUIZ.QTI_ID.eq(data.get("qti_id")))
				.execute();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
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
			
			Record bank = create.select(QUESTION_BANK.ID)
					.from(QUESTION_BANK)
					.where(QUESTION_BANK.QTI_ID.eq(data.get("bank_qti_id")))
					.fetchOne();
			int bankId = 0;
			if(bank!=null) {
				bankId = bank.getValue(QUESTION_BANK.ID);
			}
			
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
	
	public static String parseAnswers(ArrayList<HashMap<String,String>> correctResultsList, ArrayList<HashMap<String,String>> responseChoicesList, HashMap<String, String> questionInfo) {
		
		QuestionType type = QuestionType.valueOfType(questionInfo.get("question_type"));
		
		switch(type) {
		case MATCHING:
		case MULTIPLE_ANSWERS:
		case MULTIPLE_DROPDOWNS:
			return findMatches(responseChoicesList, correctResultsList);
		case MULTIPLE_CHOICE:
		case TRUE_FALSE:
			String correct = findCorrect(correctResultsList.get(FIRST), responseChoicesList);
			ArrayList<String> choices = findChoices(responseChoicesList, correct);
			return AnswerFormatter.answerJSONString(correct, choices);			
		case CALCULATED:		
		case MULTIPLE_BLANKS:		
		case NUMERICAL:		
			ArrayList<String> answers = new ArrayList<String>();
			for(HashMap<String,String> i: correctResultsList) {
				answers.add(findCorrect(i, responseChoicesList));
			}
			return AnswerFormatter.answerJSONString(answers);
		case ESSAY:
			ArrayList<String> essay = new ArrayList<String>();
			for(HashMap<String,String> e: correctResultsList) {
				if(e.containsKey("answer_value")) {
					essay.add(e.get("answer_value"));
				}
			}
			return AnswerFormatter.answerJSONString(essay);
			
		case SHORT_ANSWER:
			ArrayList<String> shortAnswer = new ArrayList<String>();
			for(HashMap<String,String> sa: correctResultsList) {
				shortAnswer.add(sa.get("answer_ident"));
			}
			return AnswerFormatter.answerJSONString(shortAnswer);
		case TEXT_ONLY:
		case FILE_UPLOAD:
			// no answers for these		
		default:
			break;		
		}		
		return null;
	}
	
	
	private static String findCorrect(HashMap<String,String> correct, ArrayList<HashMap<String, String>> allChoices) {
		//find correct answer
		String ident = correct.get("answer_ident");
		String answer = "";
		for(HashMap<String,String> o: allChoices) {
			if(o.containsValue(ident)) {
				answer = o.get("answer_value");
			}
		}
		return answer;
	}
	
	private static ArrayList<String> findChoices(ArrayList<HashMap<String, String>> allChoices, String correct) {
		ArrayList<String> choices = new ArrayList<String>();
		for(HashMap<String,String> o: allChoices) {
			if(!o.get("answer_value").equals(correct)) {
				choices.add(o.get("answer_value"));
			}
				
		}
		return choices;		
	}
	
	
	private static String findMatches(ArrayList<HashMap<String, String>> allChoices, ArrayList<HashMap<String,String>> correctAnswers) {
		HashMap<String,String> matches = new HashMap<String,String>();
		ArrayList<String> keys = new ArrayList<String>();
		for(HashMap<String,String> map: correctAnswers) {
			HashMap<String,String> temp = new HashMap<String,String>();
			for(HashMap<String,String> all:allChoices) {
				if(all.containsValue(map.get("response_ident"))) {
					temp.put("left", all.get("answer_name"));
				}
				if(all.containsValue(map.get("answer_ident"))) {
					temp.put("right", all.get("answer_value"));
				}
			}
			matches.put(temp.get("left"), temp.get("right"));
		}
		keys.addAll(matches.keySet());
		
		return AnswerFormatter.answerJSONString(keys,matches);
	}

	public static int storeCourse(String courseName) {
		int courseId = 0;
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Record exists = create.select()
					.from(COURSE)
					.where(COURSE.NAME.eq(courseName))
					.fetchOne();
			
			
			
			if(exists == null) {
				create.insertInto(COURSE, 
						COURSE.NAME)
				.values(courseName)
				.execute();
			}
			
			courseId = create.select(COURSE.ID)
					.from(COURSE)
					.where(COURSE.NAME.eq(courseName))
					.fetchOne(COURSE.ID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return courseId;
	}
	
	public static void associateCourse(int courseId, ArrayList<String> quizList) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			Record exists = create.select()
					.from(COURSE)
					.where(COURSE.ID.eq(courseId))
					.fetchOne();
			
			if (exists != null) {
				for (String s : quizList) {
					create.update(QUIZ)
					.set(QUIZ.COURSE_ID, courseId)
					.where(QUIZ.QTI_ID.eq(s))
					.execute();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
