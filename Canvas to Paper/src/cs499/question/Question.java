package cs499.question;

import java.util.ArrayList;
import java.util.HashMap;

import cs499.Reference;
import cs499.ReferenceMaterial;

public abstract class Question implements Reference{
	
	public abstract int getId();
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getGradingInstructions();
	
	public abstract boolean getAbet();
	
	public abstract void loadQuestion();
	
	public abstract void saveQuestion();
	
	public abstract float getPoints();
	
	public abstract QuestionType getType();
	
	public abstract void shuffleChoices();
	
  public abstract String getAnswer();
	
	public abstract ReferenceMaterial getReference();

}
