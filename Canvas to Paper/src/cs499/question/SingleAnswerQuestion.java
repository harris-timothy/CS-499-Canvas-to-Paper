package cs499.question;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.question.QuestionType.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.ReferenceMaterial;
import cs499.utils.DataHelper;

public class SingleAnswerQuestion extends Question {

	private int id;

	private String name;

	private String description;

	private ArrayList<String> answers;

	private boolean abet;

	private String gradingInstructions;

	private QuestionType type;
	
	private ReferenceMaterial reference;
	
	private float points;
	
	public SingleAnswerQuestion() {
		newQuestion();
	}

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

	public ArrayList<String> getAnswers() {
		return this.answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
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
	
	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}
	
	public ReferenceMaterial getReference() {
		return reference;
	}

	public void setReference(ReferenceMaterial reference) {
		this.reference = reference;
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
			
			if(result.getValue(QUESTION.ABET) == null) {
				result.setValue(QUESTION.ABET, 0);
			}

			if(result != null) {
				setName(result.getValue(QUESTION.NAME));
				setDescription(result.getValue(QUESTION.DESCRIPTION));
				setAbet(DataHelper.intToBool(result.getValue(QUESTION.ABET)));
				setGradingInstructions(result.getValue(QUESTION.GRADING_INSTRUCTIONS));
				setAnswers(AnswerFormatter.answerArray(result.getValue(QUESTION.ANSWERS)));
				setType(valueOfType(result.getValue(QUESTION.TYPE)));
				setPoints(result.getValue(QUESTION.POINTS_POSSIBLE));

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
						QUESTION.ABET,
						QUESTION.POINTS_POSSIBLE)
				.values(id,
						name,
						description,
						type.getType(),
						gradingInstructions,
						AnswerFormatter.answerJSONString(answers),
						DataHelper.boolToInt(abet),
						points)
				.execute();

			}
			else {
				create.update(QUESTION)
				.set(QUESTION.NAME, name)
				.set(QUESTION.DESCRIPTION, description)
				.set(QUESTION.TYPE, type.getType())
				.set(QUESTION.ABET, DataHelper.boolToInt(abet))
				.set(QUESTION.GRADING_INSTRUCTIONS, gradingInstructions)
				.set(QUESTION.ANSWERS, AnswerFormatter.answerJSONString(answers))
				.set(QUESTION.POINTS_POSSIBLE, points)
				.where(QUESTION.ID.eq(id))
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public float getPoints() {
		return this.points;
	}
	
	public void setPoints(float points) {
		this.points = points;
	}
	
	private void newQuestion() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			this.id = create.insertInto(QUESTION, QUESTION.NAME).values("").returning(QUESTION.ID).fetchOne(QUESTION.ID);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void shuffleChoices() {
		// TODO Auto-generated method stub
	}
		
	public String getAnswer() {
		return answers.toString();
	}
}
