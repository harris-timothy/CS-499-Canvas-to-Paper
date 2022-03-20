package cs499.question;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.question.QuestionType.*;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONArray;

import cs499.DataHelper;
import cs499.ReferenceMaterial;

public class SingleAnswerQuestion extends Question {

	private int id;

	private String name;

	private String description;

	private String answer;

	private boolean abet;

	private String gradingInstructions;

	private ReferenceMaterial reference;

	public SingleAnswerQuestion(int id) {
		this.id = id;
		loadQuestion();
	}

	public int getId() {
		return this.id;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean getAbet() {
		return this.abet;
	}

	public void setAbet(Boolean abet) {
		this.abet = abet;
	}

	public String getGradingInstructions() {
		return this.gradingInstructions;
	}

	public void setGradingInstructions(String gradingInstructions) {
		this.gradingInstructions = gradingInstructions;
	}

	@Override
	public void attachReference(ReferenceMaterial reference) {
		this.reference = reference;
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			create.update(QUESTION)
			.set(QUESTION.REFERENCE_ID, reference.getId())
			.where(QUESTION.ID.eq(this.id))
			.execute();
			

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void createReference(int id) {
		this.reference = new ReferenceMaterial(id);
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			create.update(QUESTION)
			.set(QUESTION.REFERENCE_ID, id)
			.where(QUESTION.ID.eq(this.id))
			.execute();
			

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void loadReference() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			int reference_id = create.select(QUESTION.REFERENCE_ID)
			.from(QUESTION)
			.where(QUESTION.ID.eq(id))
			.fetchOne(QUESTION.REFERENCE_ID);
			
			this.reference = new ReferenceMaterial(reference_id);			

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	public void loadQuestion() {

		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record result = create.select()
					.from(QUESTION)
					.where(QUESTION.ID.eq(id))
					.fetchOne();

			if(result != null) {
				setName(result.getValue(QUESTION.NAME));
				setDescription(result.getValue(QUESTION.DESCRIPTION));
				setAbet(DataHelper.intToBool(result.getValue(QUESTION.ABET)));
				setGradingInstructions(result.getValue(QUESTION.GRADING_INSTRUCTIONS));
				setAnswer(AnswerFormatter.answerString(result.getValue(QUESTION.ANSWERS)));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveQuestion() {

		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record exists = create.select()
					.from(QUESTION)
					.where(QUESTION.ID.eq(id))
					.fetchOne();

			if(exists == null) {
				create.insertInto(QUESTION,
						QUESTION.ID,
						QUESTION.NAME,
						QUESTION.DESCRIPTION,
						QUESTION.TYPE,
						QUESTION.GRADING_INSTRUCTIONS,
						QUESTION.ANSWERS,
						QUESTION.ABET)
				.values(id,
						name,
						description,
						SINGLE_ANSWER.toString(),
						gradingInstructions,
						AnswerFormatter.answerJSONString(answer),
						DataHelper.boolToInt(abet))
				.execute();

			}
			else {
				create.update(QUESTION)
				.set(QUESTION.NAME, name)
				.set(QUESTION.DESCRIPTION, description)
				.set(QUESTION.TYPE, "general")
				.set(QUESTION.ABET, DataHelper.boolToInt(abet))
				.set(QUESTION.GRADING_INSTRUCTIONS, gradingInstructions)
				.set(QUESTION.ANSWERS, AnswerFormatter.answerJSONString(answer))
				.where(QUESTION.ID.eq(id))
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		


	

}