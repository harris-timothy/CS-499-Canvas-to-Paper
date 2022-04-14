package cs499.question;

import cs499.Reference;

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

}
