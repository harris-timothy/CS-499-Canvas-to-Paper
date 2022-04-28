package cs499.qti;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.data_classes.Tables.QUIZ;
import static cs499.data_classes.Tables.QUESTION_BANK_QUESTION;
import static cs499.data_classes.Tables.QUIZ_TO_QUESTION;
import static cs499.data_classes.Tables.QUESTION_GROUP;
import static cs499.data_classes.Tables.QUESTION_BANK;
import static cs499.data_classes.Tables.COURSE;
import static cs499.data_classes.Tables.REFERENCE_MATERIAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.utils.DataHelper;

public class QtiToDB {
	
	private static final int FIRST = 0;
	
	public static Integer storeQuiz(HashMap<String, String> data) {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			Record exists = create.select()
					.from(QUIZ)
					.where(QUIZ.NAME.eq(data.get("quiz_title")))
					.fetchOne();
			
			if(exists == null) {
				return create.insertInto(QUIZ,
						QUIZ.QTI_ID,
						QUIZ.NAME)
				.values(data.get("quiz_qti_id"),
						data.get("quiz_title"))
				.onDuplicateKeyIgnore()
				.returningResult(QUIZ.ID)
				.fetchOne(QUIZ.ID);
			}
			else {
				return exists.getValue(QUIZ.ID);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
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
				.set(QUIZ.DESCRIPTION, data.get("description"))
				.set(QUIZ.POINTS_POSSIBLE, Float.parseFloat(data.get("points_possible")))
				.set(QUIZ.DUE_DATE, data.get("due_date"))
				.where(QUIZ.NAME.eq(data.get("name")))
				.execute();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Integer storeQuestion(HashMap<String, String> data) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION)
					.where(QUESTION.DESCRIPTION.eq(data.get("description")))
					.fetchOne();

			if(exists == null) {
				Record record = create.insertInto(QUESTION,
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
				.onDuplicateKeyIgnore()
				.returning(QUESTION.ID)
				.fetchOne();
				
				if(record != null) {
					
					return record.getValue(QUESTION.ID);
				}

			}
			else {
				
				Record record = create.update(QUESTION)
				.set(QUESTION.NAME, data.get("name"))
				.set(QUESTION.TYPE, data.get("question_type"))
				.set(QUESTION.ANSWERS, data.get("answers"))
				.set(QUESTION.POINTS_POSSIBLE, Float.parseFloat(data.get("points_possible")))
				.where(QUESTION.DESCRIPTION.eq(data.get("description")))
				.returning(QUESTION.ID)
				.fetchOne();
				
				if(record != null) {
					return record.getValue(QUESTION.ID);
				}
			}		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Integer storeQuestionBank(HashMap<String, String> data) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION_BANK)
					.where(QUESTION_BANK.NAME.eq(data.get("bank_title")))
					.fetchOne();

			if(exists == null) {
				return create.insertInto(QUESTION_BANK,
						QUESTION_BANK.QTI_ID,
						QUESTION_BANK.NAME)
				.values(data.get("qti_id"),
						data.get("bank_title"))
				.onDuplicateKeyIgnore()
				.returning(QUESTION_BANK.ID)
				.fetchOne(QUESTION_BANK.ID);
			}
			else {
			
				return exists.getValue(QUESTION_BANK.ID);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
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
	
	

	public static Integer storeCourse(String courseName) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Record exists = create.select()
					.from(COURSE)
					.where(COURSE.NAME.eq(courseName))
					.fetchOne();
			
			if(exists == null) {
				return create.insertInto(COURSE, 
						COURSE.NAME)
				.values(courseName)
				.returning(COURSE.ID)
				.fetchOne(COURSE.ID);
			}
			else {
				return exists.getValue(COURSE.ID);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
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
	
	public static void storeReference(String filename, String filepath, int questionId) {

		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			int referenceId = create.insertInto(REFERENCE_MATERIAL,
					REFERENCE_MATERIAL.CONTENT,
					REFERENCE_MATERIAL.NAME)
					.values(filepath,
							filename)
					.returning(REFERENCE_MATERIAL.ID)
					.fetchOne(REFERENCE_MATERIAL.ID);

			create.update(QUESTION)
			.set(QUESTION.REFERENCE_ID, referenceId)
			.where(QUESTION.ID.eq(questionId))
			.execute();


		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
