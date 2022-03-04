package cs499;

public class Question implements Reference {
	
	private String name;
	
	private String description;
	
	private String answer;
	
	private Boolean abet;
	
	private String gradingInstructions;
	
	public Question(String name, String description, String answer, Boolean abet, String gradingInstructions) {
		this.name = name;
		this.description = description;
		this.answer = answer;
		this.abet = abet;
		this.gradingInstructions = gradingInstructions;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Boolean getAbet() {
		return this.abet;
	}
	
	public void setAbet(Boolean abet) {
		this.abet = abet;
	}
	
	public String getGradingInstructions() {
		return this.gradingInstructions;
	}
	
	public void setGradingInstructions(String gradingInstructions) {
		this.gradingInstructions = gradingInstructions;
	}

	@Override
	public void attachReference(ReferenceMaterial reference) {
		// TODO Auto-generated method stub
		
	}

}
