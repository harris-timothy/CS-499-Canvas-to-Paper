package cs499.qti;

import static cs499.data_classes.Tables.COURSE;
import static cs499.data_classes.Tables.INSTRUCTOR;
import static cs499.data_classes.Tables.QUESTION;
import static cs499.data_classes.Tables.QUIZ;
import static cs499.data_classes.Tables.QUESTION_BANK_QUESTION;
import static cs499.data_classes.Tables.QUIZ_TO_QUESTION;
import static cs499.data_classes.Tables.QUESTION_GROUP;
import static cs499.data_classes.Tables.QUESTION_BANK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.DataHelper;

public class QtiToDB {
	
	public void storeQuiz() {
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
	
	public int storeQuestion(HashMap<String, String> data) {
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
						QUESTION.ANSWERS)
				.values(data.get("qti_id"),
						data.get("name"),
						data.get("description"),
						data.get("question_type"),
						data.get("answers"))
				.execute();

			}
			else {
				create.update(QUESTION)
				.set(QUESTION.NAME, data.get("name"))
				.set(QUESTION.DESCRIPTION, data.get("description"))
				.set(QUESTION.TYPE, data.get("question_type"))
				.set(QUESTION.ANSWERS, data.get("answers"))
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
	
	public int storeQuestionBank(HashMap<String, String> data) {
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
						data.get("name"))
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
	
	public void storeQuizQuestion(int quizId, int questionId) {
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
	
	public void storeBankQuestion(int bankId, int questionId) {
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
	
	public void storeQuestionGroup(HashMap<String, Integer> data) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.QUIZ_ID.eq(data.get("quiz_id")))
					.and(QUESTION_GROUP.QUESTION_BANK_ID.eq(data.get("bank_id")))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUESTION_GROUP,
						QUESTION_GROUP.QUIZ_ID,
						QUESTION_GROUP.QUESTION_BANK_ID,
						QUESTION_GROUP.PICK_COUNT,
						QUESTION_GROUP.QUESTION_POINTS)
				.values(data.get("quiz_id"),
						data.get("bank_id"),
						data.get("selection_number"),
						data.get("points_per_item"))
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

}
