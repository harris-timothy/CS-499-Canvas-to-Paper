package cs499;

public class TestKeyDoc implements Doc {
	
	private String filename;
	
	private String rubric;
	
	private Quiz quiz;
	
	public TestKeyDoc(String filename, String rubric, Quiz quiz) {
		this.filename = filename;
		this.rubric = rubric;
		this.quiz = quiz;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getRubric() {
		return this.rubric;
	}
	
	public void setRubric(String rubric) {
		this.rubric = rubric;
	}
	
	public Quiz getQuiz() {
		return this.quiz;
	}
	
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
	public void loadContent() {
		// TODO Auto-generated method stub

	}

}
