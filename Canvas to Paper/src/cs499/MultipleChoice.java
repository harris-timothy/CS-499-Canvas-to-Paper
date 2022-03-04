package cs499;

import java.util.ArrayList;

public class MultipleChoice extends Question {

	private ArrayList<String> choices;
	
	public MultipleChoice(String name, String description, String answer, Boolean abet, String gradingInstructions, ArrayList<String> choices) {
		super(name, description, answer, abet, gradingInstructions);
		this.choices = choices;
	}
	
	public ArrayList<String> getChoices(){
		return this.choices;
	}
	
	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
	
	

}
