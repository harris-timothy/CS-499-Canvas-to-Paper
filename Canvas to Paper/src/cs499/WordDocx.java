package cs499;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

import cs499.question.MatchingQuestion;
import cs499.question.MultipleChoiceQuestion;
import cs499.question.Question;
import cs499.question.QuestionFactory;
import cs499.question.QuestionType;
import cs499.question.SingleAnswerQuestion;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class WordDocx
{
	// For use with a built quiz (QuizBuilder)
	public void DocumentBuilder(Quiz quiz, String filepath) throws Exception
	{
		// Make an empty document
		XWPFDocument document = new XWPFDocument();
	 
		// Make a file by specifying path of the document
		// Get filepath from GUI
		File newFile = new File(filepath);
	
		// File stream is used to write in the document
		FileOutputStream out = new FileOutputStream(newFile);
		
		quiz.shuffleQuestions();
		ArrayList<Question> questionList = quiz.getQuestions();
		
		// TODO:
		// For doc generation:
		// Header generation - Test Name, Teacher Name, Test Points
		// Page numbers
		// Templates
		
		// Display Test Points
		// TODO: Change to full header functionality
		XWPFParagraph testPoints = document.createParagraph();
		testPoints.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun tpRun = testPoints.createRun();
		tpRun.setText(String.valueOf(quiz.getPointsPossible() + " points possible"));
		tpRun.addBreak();
		
		// Display Description, Points, and Choices
		// TODO: Add reference material to document
		for (Question question : questionList)
		{
			Question builtQuestion = QuestionFactory.build(question.getId());
			
			XWPFParagraph questionParagraph = document.createParagraph();
			questionParagraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun questionRun = questionParagraph.createRun();
			
			questionRun.setText(builtQuestion.getDescription());
			questionRun.addBreak();
			
			questionRun.setText(String.valueOf(builtQuestion.getPoints() + " points"));
			questionRun.addBreak();
			
			// Get type of question and print answers depending
			if (builtQuestion instanceof MultipleChoiceQuestion) {
				MultipleChoiceQuestion multiChoice = (MultipleChoiceQuestion)builtQuestion;
				
				// Add the question's reference image to document, if there is one
				if (multiChoice.getReference() != null) {
					
					// Load data of reference image
					BufferedImage reference;
					
					try {
					    reference = ImageIO.read(new File(multiChoice.getReference().getFilepath()));
					    
						if (FileNameUtils.getExtension(multiChoice.getReference().getFilepath()).equals("png"))
						{
							// NOTE: May have to add some sort of size restriction to the width and height parameters
							questionRun.addPicture(new FileInputStream(multiChoice.getReference().getFilepath()), XWPFDocument.PICTURE_TYPE_PNG, multiChoice.getReference().getFilepath(), Units.toEMU(reference.getWidth()), Units.toEMU(reference.getHeight()));
						}
						else if (FileNameUtils.getExtension(multiChoice.getReference().getFilepath()).equals("jpeg") || FileNameUtils.getExtension(multiChoice.getReference().getFilepath()).equals("jpg")) {
							// NOTE: May have to add some sort of size restriction to the width and height parameters
							questionRun.addPicture(new FileInputStream(multiChoice.getReference().getFilepath()), XWPFDocument.PICTURE_TYPE_JPEG, multiChoice.getReference().getFilepath(), Units.toEMU(reference.getWidth()), Units.toEMU(reference.getHeight()));
						}
					    
					} 
					catch (IOException e) {
						
					}
					
				}
				
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
				
				// Add the question's reference image to document, if there is one
				if (matching.getReference() != null) {
					
					// Load data of reference image
					BufferedImage reference;
					
					try {
					    reference = ImageIO.read(new File(matching.getReference().getFilepath()));
					    
						if (FileNameUtils.getExtension(matching.getReference().getFilepath()).equals("png"))
						{
							// NOTE: May have to add some sort of size restriction to the width and height parameters
							questionRun.addPicture(new FileInputStream(matching.getReference().getFilepath()), XWPFDocument.PICTURE_TYPE_PNG, matching.getReference().getFilepath(), Units.toEMU(reference.getWidth()), Units.toEMU(reference.getHeight()));
						}
						else if (FileNameUtils.getExtension(matching.getReference().getFilepath()).equals("jpeg") || FileNameUtils.getExtension(matching.getReference().getFilepath()).equals("jpg")) {
							// NOTE: May have to add some sort of size restriction to the width and height parameters
							questionRun.addPicture(new FileInputStream(matching.getReference().getFilepath()), XWPFDocument.PICTURE_TYPE_JPEG, matching.getReference().getFilepath(), Units.toEMU(reference.getWidth()), Units.toEMU(reference.getHeight()));
						}
					    
					} 
					catch (IOException e) {
						
					}
					
				}
				
				matching.shuffleChoices();
				
				ArrayList<String> keys = matching.getLeft();
				HashMap<String, String> values = new HashMap<String, String>(matching.getRight());

				// Will probably have to toy with this to get it to look decent on paper
				XWPFTable table = document.createTable(values.size(), 2);
				table.removeBorders();
				
				int row = 0;
				for (HashMap.Entry<String, String> entry : values.entrySet()) {
					for (int column = 0; column < 2; column++) {
						if (column == 0) {
							table.getRow(row).getCell(column).setText(keys.get(row));
						}
						else if (column == 1) {
							table.getRow(row).getCell(column).setText(entry.getValue());
						}
					}
					row++;
				}
								
			}
			else if (builtQuestion instanceof SingleAnswerQuestion) {
				SingleAnswerQuestion singleanswer = (SingleAnswerQuestion)builtQuestion;
				
				// Add the question's reference image to document, if there is one
				if (singleanswer.getReference() != null) {
					
					// Load data of reference image
					BufferedImage reference;
					
					try {
					    reference = ImageIO.read(new File(singleanswer.getReference().getFilepath()));
					    
						if (FileNameUtils.getExtension(singleanswer.getReference().getFilepath()).equals("png"))
						{
							// NOTE: May have to add some sort of size restriction to the width and height parameters
							questionRun.addPicture(new FileInputStream(singleanswer.getReference().getFilepath()), XWPFDocument.PICTURE_TYPE_PNG, singleanswer.getReference().getFilepath(), Units.toEMU(reference.getWidth()), Units.toEMU(reference.getHeight()));
						}
						else if (FileNameUtils.getExtension(singleanswer.getReference().getFilepath()).equals("jpeg") || FileNameUtils.getExtension(singleanswer.getReference().getFilepath()).equals("jpg")) {
							// NOTE: May have to add some sort of size restriction to the width and height parameters
							questionRun.addPicture(new FileInputStream(singleanswer.getReference().getFilepath()), XWPFDocument.PICTURE_TYPE_JPEG, singleanswer.getReference().getFilepath(), Units.toEMU(reference.getWidth()), Units.toEMU(reference.getHeight()));
						}
					    
					} 
					catch (IOException e) {
						
					}
					
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
	public void TestKeyBuilder(Quiz quiz, String filepath) throws Exception
	{
		// Make an empty document
		XWPFDocument document = new XWPFDocument();
	 
		// Make a file by specifying path of the document
		// Get filepath from GUI
		File newFile = new File(filepath);
	
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
							table.getRow(row).getCell(column).setText(entry.getKey() + " (" + entry.getValue() + ")");
						}
						else if (column == 1) {
							table.getRow(row).getCell(column).setText(entry.getValue());
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