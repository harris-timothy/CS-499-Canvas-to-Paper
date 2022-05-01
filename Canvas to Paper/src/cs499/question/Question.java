package cs499.question;

import cs499.Reference;
import cs499.ReferenceMaterial;

public abstract class Question implements Reference{
	
	public abstract int getId();
	
	public abstract String getName();
	
	public abstract void setName(String name);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
	public abstract String getGradingInstructions();
	
	public abstract void setGradingInstructions(String instructions);
	
	public abstract boolean getAbet();
	
	public abstract void setAbet(boolean abet);
	
	public abstract void loadQuestion();
	
	public abstract void saveQuestion();
	
	public abstract float getPoints();
	
	public abstract QuestionType getType();
	
	public abstract void setType(QuestionType type);
	
	public abstract void shuffleChoices();
	
	public abstract String getAnswer();
	
	public abstract void setAnswer(String answer);
	
	public abstract ReferenceMaterial getReference();

}
