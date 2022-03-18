package cs499;

public abstract class Question implements Reference{
	
	public abstract int getId();
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getGradingInstructions();
	
	public abstract Boolean getAbet();
	
	public abstract void loadQuestion();
	
	public abstract void saveQuestion();
	

}
