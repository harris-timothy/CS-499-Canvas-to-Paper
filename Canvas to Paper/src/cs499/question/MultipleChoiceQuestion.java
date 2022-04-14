package cs499.question;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.question.QuestionType.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.ReferenceMaterial;
import cs499.utils.DataHelper;

public class MultipleChoiceQuestion extends Question {

	private int id;

	private String name;

	private String description;

	private String answer;
	
	private QuestionType type;

	private boolean abet;

	private String gradingInstructions;

	private ReferenceMaterial reference;
	
	private ArrayList<String> choices;
	
	private float points;

	public MultipleChoiceQuestion(int id) {
		this.id = id;
		loadQuestion();
		
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ReferenceMaterial getReference() {
		return reference;
	}

	public void setReference(ReferenceMaterial reference) {
		this.reference = reference;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAbet(boolean abet) {
		this.abet = abet;
	}

	public void setGradingInstructions(String gradingInstructions) {
		this.gradingInstructions = gradingInstructions;
	}

	public void shuffleChoices() {
		Collections.shuffle(choices);
	}
	
	public ArrayList<String> getChoices(){
		return this.choices;
	}

	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}	

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getGradingInstructions() {
		return this.gradingInstructions;
	}

	@Override
	public boolean getAbet() {
		return this.abet;
	}
	
	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	@Override
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
						AnswerFormatter.answerJSONString(answer, choices),
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
				.set(QUESTION.ANSWERS, AnswerFormatter.answerJSONString(answer, choices))
				.set(QUESTION.POINTS_POSSIBLE, points)
				.where(QUESTION.ID.eq(id))
				.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	@Override
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
				setAnswer(AnswerFormatter.correctAnswer(result.getValue(QUESTION.ANSWERS)));
				setChoices(AnswerFormatter.choicesArray(result.getValue(QUESTION.ANSWERS)));
				setType(valueOfType(result.getValue(QUESTION.TYPE)));
				setPoints(result.getValue(QUESTION.POINTS_POSSIBLE));

			}



		} catch (Exception e) {
			e.printStackTrace();
		}
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

	@Override
	public float getPoints() {
		return this.points;
	}
	
	public void setPoints(float points) {
		this.points = points;
	}



}
