package cs499;

import static cs499.data_classes.Tables.QUESTION_BANK;
import static cs499.data_classes.Tables.QUESTION_BANK_QUESTION;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.question.Question;

public class QuestionBank {
	
	private ArrayList<Question> questions;
	
	private String name;
	
	private int id;
	
	public QuestionBank(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
		associateQuestion(question.getId());
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
	
	public void saveBank() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION_BANK)
					.where(QUESTION_BANK.ID.eq(id))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUESTION_BANK,
						QUESTION_BANK.ID,
						QUESTION_BANK.NAME)
				.values(id,
						name)
				.execute();
			}
			else {
				create.update(QUESTION_BANK)
				.set(QUESTION_BANK.NAME, name)
				.where(QUESTION_BANK.ID.eq(id))
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadBank() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			Record result = create.select()
					.from(QUESTION_BANK)
					.where(QUESTION_BANK.ID.eq(id))
					.fetchOne();
			
			if(result != null) {
				setName(result.getValue(QUESTION_BANK.NAME));				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Integer> getQuestionIds() {
		
		List<Integer> questionIds = new ArrayList<Integer>();
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			questionIds = create.select(QUESTION_BANK_QUESTION.QUESTION_ID)
					.from(QUESTION_BANK_QUESTION)
					.where(QUESTION_BANK_QUESTION.QUESTION_BANK_ID.eq(id))
					.fetch()
					.getValues(QUESTION_BANK_QUESTION.QUESTION_ID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questionIds;		
		
	}
	
	public void associateQuestion(int questionId) {
		

		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			create.insertInto(QUESTION_BANK_QUESTION,
					QUESTION_BANK_QUESTION.QUESTION_BANK_ID,
					QUESTION_BANK_QUESTION.QUESTION_ID)
			.values(id, questionId)
			.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void removeQuestionAssociation(int questionId) {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			create.delete(QUESTION_BANK_QUESTION)
			.where(QUESTION_BANK_QUESTION.QUESTION_BANK_ID.eq(id))
			.and(QUESTION_BANK_QUESTION.QUESTION_ID.eq(questionId))
			.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}