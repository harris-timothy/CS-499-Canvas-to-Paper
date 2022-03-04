package cs499;

import java.util.ArrayList;
import java.util.HashMap;

public class Matching extends Question {
	
	private ArrayList<String> left;
	
	private HashMap<String, String> right;

	public Matching(String name, String description, String answer, Boolean abet, String gradingInstructions, ArrayList<String> left, HashMap<String, String> right) {
		super(name, description, answer, abet, gradingInstructions);
		this.left = left;
		this.right = right;
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
	

}
