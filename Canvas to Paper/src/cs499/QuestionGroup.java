package cs499;

import static cs499.data_classes.Tables.QUESTION_GROUP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.Record;

import cs499.question.Question;
import cs499.utils.DataHelper;

public class QuestionGroup {
	
	private ArrayList<Question> questions;
	
	private int count;
	
	private float points;
	
	private String name;
	
	private int id;
	
	private QuestionBank bank;
	
	private String description;
	
	public QuestionGroup() {
		newGroup();
	}
	
	public QuestionGroup(int id) {
		this.id = id;
		loadGroup();
	}
	
	public int getId() {
		return id;
	}
	
	public void loadGroup() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record group = create.select()
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.ID.eq(id))
					.fetchOne();
			
			if(group != null) {
				setName(group.getValue(QUESTION_GROUP.NAME));
				setCount(group.getValue(QUESTION_GROUP.PICK_COUNT));
				setPoints(group.getValue(QUESTION_GROUP.QUESTION_POINTS));
				setBank(group.getValue(QUESTION_GROUP.QUESTION_BANK_ID));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveGroup() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			create.insertInto(QUESTION_GROUP,
					QUESTION_GROUP.NAME,
					QUESTION_GROUP.PICK_COUNT,
					QUESTION_GROUP.QUESTION_POINTS)
			.values(name,
					count,
					points)
			.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void newGroup() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			this.id = create.insertInto(QUESTION_GROUP,
					QUESTION_GROUP.NAME)
			.values("")
			.returning(QUESTION_GROUP.ID)
			.fetchOne(QUESTION_GROUP.ID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Question> getQuestions() {
		return questions; 
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	public void pullFromBank() {
		questions.clear();
		ArrayList<Question> bankQuestions = bank.getQuestions();
		Collections.shuffle(bankQuestions);
		for(int i = 0; i < count; i++) {
			questions.add(bankQuestions.get(i));
		}
	}

	public QuestionBank getBank() {
		return bank;
	}

	public void setBank(int bankId) {
		this.bank = new QuestionBank(bankId);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
