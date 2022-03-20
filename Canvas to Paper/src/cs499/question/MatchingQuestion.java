package cs499.question;

import static cs499.data_classes.Tables.QUESTION;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONArray;

import cs499.DataHelper;
import cs499.ReferenceMaterial;

public class MatchingQuestion extends Question {

	private int id;

	private String name;

	private String description;

	private boolean abet;

	private String gradingInstructions;

	private ReferenceMaterial reference;
		
	private ArrayList<String> left;

	private HashMap<String, String> right;

	public MatchingQuestion(int id) {
		this.id = id;
		
	}

	public ArrayList<String> getLeft(){
		return this.left;
	}

	public void setLeft(ArrayList<String> left) {
		this.left = left;
	}

	public HashMap<String, String> getRight() {
		return this.right;
	}

	public void setRight(HashMap<String, String> right) {
		this.right = right;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGradingInstructions(String gradingInstructions) {
		this.gradingInstructions = gradingInstructions;
	}
	
	@Override
	public void loadQuestion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveQuestion() {
		// TODO Auto-generated method stub
		
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
