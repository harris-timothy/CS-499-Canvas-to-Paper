package cs499.question;

import static cs499.data_classes.Tables.QUESTION;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONArray;

import cs499.DataHelper;
import cs499.ReferenceMaterial;

public class MultipleChoiceQuestion extends Question {

	private int id;

	private String name;

	private String description;

	private String answer;

	private boolean abet;

	private String gradingInstructions;

	private ReferenceMaterial reference;
	
	private ArrayList<String> choices;

	public MultipleChoiceQuestion(int id) {
		this.id = id;
		
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

	@Override
	public void saveQuestion() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
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
				setAnswer(result.getValue(QUESTION.ANSWERS));

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



}