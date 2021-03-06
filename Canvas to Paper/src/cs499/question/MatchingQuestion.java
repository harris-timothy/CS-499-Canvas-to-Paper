package cs499.question;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.question.QuestionType.MATCHING;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.ReferenceMaterial;
import cs499.utils.DataHelper;

public class MatchingQuestion extends Question {

	private int id;

	private String name;

	private String description;

	private boolean abet;

	private String gradingInstructions;

	private ReferenceMaterial reference;
	
	private QuestionType type;
		
	private ArrayList<String> left;

	private LinkedHashMap<String, String> right = new LinkedHashMap<String,String>();
	
	private float points;
	
	public MatchingQuestion() {
		newQuestion();
	}

	public MatchingQuestion(int id) {
		this.id = id;
		loadQuestion();
		
	}
	
	public void shuffleChoices() {
		Collections.shuffle(left);
	}

	public ArrayList<String> getLeft(){
		return this.left;
	}

	public void setLeft(ArrayList<String> left) {
		this.left = left;
	}

	public LinkedHashMap<String, String> getRight() {
		return this.right;
	}

	public void setRight(HashMap<String, String> right) {
		this.right.putAll(right);
	}	

	@Override
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getGradingInstructions() {
		return this.gradingInstructions;
	}
	
	public void setGradingInstructions(String gradingInstructions) {
		this.gradingInstructions = gradingInstructions;
	}

	@Override
	public boolean getAbet() {
		return this.abet;
	}
	
	public void setAbet(boolean abet) {
		this.abet = abet;
		
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
	public void loadQuestion() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record result = create.select()
					.from(QUESTION)
					.where(QUESTION.ID.eq(id))
					.fetchOne();
			if(result.getValue(QUESTION.ABET) == null) {
				result.set(QUESTION.ABET, 0);
			}

			if(result != null) {
				setName(result.getValue(QUESTION.NAME));
				setDescription(result.getValue(QUESTION.DESCRIPTION));
				setAbet(DataHelper.intToBool(result.getValue(QUESTION.ABET)));
				setGradingInstructions(result.getValue(QUESTION.GRADING_INSTRUCTIONS));
				String answer = result.getValue(QUESTION.ANSWERS);
				setLeft(AnswerFormatter.keyArray(answer));
				setRight(AnswerFormatter.answerMap(answer));
				setPoints(result.getValue(QUESTION.POINTS_POSSIBLE));
				setType(QuestionType.valueOfType(result.getValue(QUESTION.TYPE)));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
						AnswerFormatter.answerJSONString(left, right),
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
				.set(QUESTION.ANSWERS, AnswerFormatter.answerJSONString(left, right))
				.set(QUESTION.POINTS_POSSIBLE, points)
				.where(QUESTION.ID.eq(id))
				.execute();
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
	
	private void newQuestion() {
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
			
			this.id = create.insertInto(QUESTION, QUESTION.NAME).values("").returning(QUESTION.ID).fetchOne(QUESTION.ID);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getAnswer() {
		return AnswerFormatter.answerJSONString(left,right);
	}

	@Override
	public void setAnswer(String answer) {
		AnswerFormatter.answerMap(answer);
		AnswerFormatter.keyArray(answer);
		if(left.toString().equals(AnswerFormatter.keyArray(answer).toString())){
			//do nothing
		}		
		else if(left.isEmpty()) {
			left.addAll(AnswerFormatter.keyArray(answer));
		}
		else {
			left = AnswerFormatter.keyArray(answer);
		}
		
		if(right.toString().equals(AnswerFormatter.answerMap(answer).toString())){
			//do nothing
		}
		else if(right.isEmpty()) {
			right.putAll(AnswerFormatter.answerMap(answer));
		}
		else {
			right.clear();
			right.putAll(AnswerFormatter.answerMap(answer));
		}
		
	}

	
}
