package cs499;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import cs499.question.MatchingQuestion;
import cs499.question.MultipleChoiceQuestion;
import cs499.question.Question;
import cs499.question.QuestionFactory;
import cs499.question.QuestionType;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class WordDocx
{
	// For use with a built quiz (QuizBuilder)
	public void DocumentBuilder(Quiz quiz) throws Exception
	{
		// Make an empty document
		XWPFDocument document = new XWPFDocument();
	 
		// Make a file by specifying path of the document
		// TODO: Have user specify path
		File newFile = new File("D:/word.docx");
	
		// File stream is used to write in the document
		FileOutputStream out = new FileOutputStream(newFile);
		
		quiz.shuffleQuestions();
		ArrayList<Question> questionList = quiz.getQuestions();
		
		// TODO:
		// For doc generation
		// Header generation - Test Name, Teacher Name, Test Points
		// Page numbers
		// User-set font, font-size, etc (accessible through GUI somehow)
		
		// Display Test Points
		// TODO: Change to full header functionality
		XWPFParagraph testPoints = document.createParagraph();
		testPoints.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun tpRun = testPoints.createRun();
		tpRun.setText(String.valueOf(quiz.getPointsPossible() + " points possible"));
		tpRun.addBreak();
		
		// Display Question Name, Description, Points, and Choices
		// TODO: Add reference material to document
		for (Question question : questionList)
		{
			Question builtQuestion = QuestionFactory.build(question.getId());
			
			XWPFParagraph questionParagraph = document.createParagraph();
			questionParagraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun questionRun = questionParagraph.createRun();
			
			questionRun.setText(builtQuestion.getName());
			questionRun.addBreak();
			
			questionRun.setText(builtQuestion.getDescription());
			questionRun.addBreak();
			
			questionRun.setText(String.valueOf(builtQuestion.getPoints() + " points"));
			questionRun.addBreak();
			
			// Get type of question and print answers depending
			if (builtQuestion instanceof MultipleChoiceQuestion) {
				MultipleChoiceQuestion multiChoice = (MultipleChoiceQuestion)builtQuestion;
				multiChoice.shuffleChoices();
				
				ArrayList<String> choices = multiChoice.getChoices();
				
				char choiceLetter = 'a';
				for (String choice : choices) {
					questionRun.addTab(); // NOTE: This may need to go outside of the for loop, with a removeTab() after. Unsure how it will behave.
					questionRun.setText(choiceLetter + ") " + choice);
					questionRun.addBreak();
					choiceLetter++;
					
				}
				
			}
			else if (builtQuestion instanceof MatchingQuestion) {
				MatchingQuestion matching = (MatchingQuestion)builtQuestion;
				matching.shuffleChoices();
				
				HashMap<String, String> choices = new HashMap<String, String>(matching.getRight());

				// Will probably have to toy with this to get it to look decent on paper
				XWPFTable table = document.createTable(choices.size(), 2);
				table.removeBorders();
				
				int row = 0;
				for (HashMap.Entry<String, String> entry : choices.entrySet()) {
					for (int column = 0; column < 2; column++) {
						if (column == 0) {
							table.getRow(row).getCell(column).setText(entry.getValue());
						}
						else if (column == 1) {
							table.getRow(row).getCell(column).setText(entry.getKey());
						}
					}
					row++;
				}
								
			}
			
		}
		
		// Write to file
		document.write(out);
		
		// Close stream and document
		out.close();
		document.close();
	}
	
	// For use with a built quiz (QuizBuilder)
	public void TestKeyBuilder(Quiz quiz) throws Exception
	{
		// Make an empty document
		XWPFDocument document = new XWPFDocument();
	 
		// Make a file by specifying path of the document
		// TODO: Have user specify path
		File newFile = new File("D:/word.docx");
	
		// File stream is used to write in the document
		FileOutputStream out = new FileOutputStream(newFile);
		
		quiz.shuffleQuestions();
		ArrayList<Question> questionList = quiz.getQuestions();

		// Display Test Points (and also Test Key marker for now)
		// TODO: Change to full header functionality
		XWPFParagraph testPoints = document.createParagraph();
		testPoints.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun tpRun = testPoints.createRun();
		tpRun.setText(String.valueOf(quiz.getPointsPossible() + " points possible"));
		tpRun.addBreak();
		tpRun.setText("**TEST KEY**");
		
		// Display Question Name, Description, Points, and Choices
		// TODO: Add reference material to document
		for (Question question : questionList)
		{
			Question builtQuestion = QuestionFactory.build(question.getId());
			
			XWPFParagraph questionParagraph = document.createParagraph();
			questionParagraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun questionRun = questionParagraph.createRun();
			
			questionRun.setText(builtQuestion.getName());
			questionRun.addBreak();
			
			if (builtQuestion.getAbet()) {
				questionRun.setText(builtQuestion.getDescription() + " - ABET Question");
				questionRun.addBreak();
			}
			else {
				questionRun.setText(builtQuestion.getDescription());
				questionRun.addBreak();
			}
			
			questionRun.setText(String.valueOf(builtQuestion.getPoints() + " points"));
			questionRun.addBreak();
			
			// Get type of question and print answers depending
			if (builtQuestion instanceof MultipleChoiceQuestion) {
				MultipleChoiceQuestion multiChoice = (MultipleChoiceQuestion)builtQuestion;
				
				ArrayList<String> choices = multiChoice.getChoices();
				
				char choiceLetter = 'a';
				for (String choice : choices) {
					questionRun.addTab(); // NOTE: This may need to go outside of the for loop, with a removeTab() after. Unsure how it will behave.
					if (choice.equals(multiChoice.getAnswer())) {
						questionRun.setBold(true);
						questionRun.setText(choiceLetter + ") " + choice + " - **CORRECT ANSWER**");
						questionRun.setBold(false);
						questionRun.addBreak();
					}
					else {
						questionRun.setText(choiceLetter + ") " + choice);
						questionRun.addBreak();
					}
					
					choiceLetter++;
					
				}
				
			}
			else if (builtQuestion instanceof MatchingQuestion) {
				MatchingQuestion matching = (MatchingQuestion)builtQuestion;
				
				HashMap<String, String> choices = new HashMap<String, String>(matching.getRight());
				
				// Will probably have to toy with this to get it to look decent on paper
				XWPFTable table = document.createTable(choices.size(), 2);
				table.removeBorders();
				
				int row = 0;
				for (HashMap.Entry<String, String> entry : choices.entrySet()) {
					for (int column = 0; column < 2; column++) {
						if (column == 0) {
							table.getRow(row).getCell(column).setText(entry.getValue() + " (" + entry.getKey() + ")");
						}
						else if (column == 1) {
							table.getRow(row).getCell(column).setText(entry.getKey());
						}
					}
					row++;
				}
								
			}
			
		}
		
		// Write to file
		document.write(out);
		
		// Close stream and document
		out.close();
		document.close();
	}

}