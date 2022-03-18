package cs499;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.QuestionType.*;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

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

	public Boolean getAbet() {
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

		try (Connection conn = DriverManager.getConnection(dotenv.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

			create.update(QUESTION)
			.set(QUESTION.REFERENCE_ID, reference.getId())
			.execute();
			
			//create reference object

		}
		catch(Exception e) {
			e.printStackTrace();
		}

		this.reference = reference;

	}

	public void loadQuestion() {

		try (Connection conn = DriverManager.getConnection(dotenv.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record result = create.select()
					.from(QUESTION)
					.where(QUESTION.ID.eq(id))
					.fetchOne();

			if(result != null) {
				setName(result.getValue(QUESTION.NAME));
				setDescription(result.getValue(QUESTION.DESCRIPTION));
				setAbet(result.getValue(QUESTION.ABET) == 1);
				setGradingInstructions(result.getValue(QUESTION.GRADING_INSTRUCTIONS));
				setAnswer(result.getValue(QUESTION.ANSWERS));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveQuestion() {

		try (Connection conn = DriverManager.getConnection(dotenv.get("DB_URL"))) {
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
				.values(id, name, description, SINGLE_ANSWER.toString(), gradingInstructions, answer, (abet ? 1 : 0))
				.execute();

			}
			else {
				create.update(QUESTION)
				.set(QUESTION.NAME, name)
				.set(QUESTION.DESCRIPTION, description)
				.set(QUESTION.TYPE, "general")
				.set(QUESTION.ABET, (abet ? 1 : 0))
				.set(QUESTION.GRADING_INSTRUCTIONS, gradingInstructions)
				.set(QUESTION.ANSWERS, answer)
				.where(QUESTION.ID.eq(id))
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
