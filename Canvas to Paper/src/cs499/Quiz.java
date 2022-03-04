package cs499;

import java.util.ArrayList;
import java.util.Collections;

public class Quiz implements Reference{
	
	private String name;
	
	private String date;
	
	private String instructor;
	
	private String course;
	
	private String section;
	
	private String instructions;
	
	private ArrayList<Question> questions;
	
	public Quiz(String name, String date, String instructor, String course, String section, String instructions) {
		this.name = name;
		this.date = date;
		this.instructor = instructor;
		this.course = course;
		this.section = section;
		this.instructions = instructions;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getInstructor() {
		return this.instructor;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public String getCourse() {
		return this.course;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getSection() {
		return this.section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	public String getInstructions() {
		return this.instructions;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		for (int i = 0; i < questions.size(); i++) {
			if(questions.get(i) == question)
				questions.remove(i);			
		}
	}
	
	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}
	
	public void saveMetadata() {
		//TODO
	}

	@Override
	public void attachReference(ReferenceMaterial reference) {
		// TODO Auto-generated method stub
		
	}

}
