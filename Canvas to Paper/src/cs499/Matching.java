package cs499;

import java.util.ArrayList;
import java.util.HashMap;

public class Matching extends Question {

	private int id;

	private String name;

	private String description;

	private Boolean abet;

	private String gradingInstructions;

	private ReferenceMaterial reference;
		
	private ArrayList<String> left;

	private HashMap<String, String> right;

	public Matching(int id) {
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
		// TODO Auto-generated method stub
		
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
	public Boolean getAbet() {
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

}
