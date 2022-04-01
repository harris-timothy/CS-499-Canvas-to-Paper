package cs499;

public class QuizDoc implements Doc {

	private String filename;
	
	private Quiz quiz;
	
	public QuizDoc(String filename, Quiz quiz) {
		this.filename = filename;
		this.quiz = quiz;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
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
