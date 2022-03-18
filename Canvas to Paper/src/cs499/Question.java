package cs499;

import io.github.cdimascio.dotenv.Dotenv;

public abstract class Question implements Reference{
	
	protected static Dotenv dotenv;
	
	public abstract int getId();
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getGradingInstructions();
	
	public abstract Boolean getAbet();
	
	public abstract void loadQuestion();
	
	public abstract void saveQuestion();
	

}
