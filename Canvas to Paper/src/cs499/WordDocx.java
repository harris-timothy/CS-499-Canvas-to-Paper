package cs499;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
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
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class WordDocx
{
	// For use with a built quiz (QuizBuilder)
	
	private void DocumentBuilder(Quiz quiz, String filepath) throws Exception
	{
		// Make an empty document
		XWPFDocument document = new XWPFDocument();
	 
		// Make a file by specifying path of the document
		// Get filepath from GUI
		File newFile = new File(filepath);
	
		// File stream is used to write in the document
		FileOutputStream out = new FileOutputStream(newFile);
		
		// quiz.shuffleQuestions();
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
		
		// Display Description, Points, Choices, and Reference Material
		int numbering = 1;
		for (Question question : questionList)
		{
			//Question builtQuestion = QuestionFactory.build(question.getId());
			
			XWPFParagraph questionParagraph = document.createParagraph();
			questionParagraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun questionRun = questionParagraph.createRun();
			
			questionRun.setText(numbering + ") " + question.getDescription());
			questionRun.addBreak();
			
			questionRun.setText(String.valueOf(question.getPoints() + " points"));
			questionRun.addBreak();
			
			// Get type of question and print answers depending
			if (question instanceof MultipleChoiceQuestion) {
				MultipleChoiceQuestion multiChoice = (MultipleChoiceQuestion)question;
				
				// Add the question's reference image to document, if there is one
				if (multiChoice.getReference() != null) {
					
					// Load data of reference image
					BufferedImage reference;
					
					try {
					    reference = ImageIO.read(new File(multiChoice.getReference().getFilepath()));
					    
						if (FileNameUtils.getExtension(multiChoice.getReference().getFilepath()).equals("png")) {
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
				
				//if (question.getType().equals(QuestionType.MULTIPLE_CHOICE)) {
				//	multiChoice.shuffleChoices();
					
				//}
				
				ArrayList<String> choices = multiChoice.getChoices();
				
				if (question.getType().equals(QuestionType.TRUE_FALSE)) {
					if (!choices.get(0).toLowerCase().equals("true")) {
						choices.set(0, "True");
						choices.set(1,  "False");
					}
				}
				
				char choiceLetter = 'a';
				for (String choice : choices) {
					questionRun.addTab(); // NOTE: This may need to go outside of the for loop, with a removeTab() after. Unsure how it will behave.
					questionRun.setText(choiceLetter + ") " + choice);
					questionRun.addBreak();
					choiceLetter++;
					
				}
				
			}
			else if (question instanceof MatchingQuestion) {
				MatchingQuestion matching = (MatchingQuestion)question;
				
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
				
				//matching.shuffleChoices();
				
				ArrayList<String> keys = matching.getLeft();
				HashMap<String, String> values = new HashMap<String, String>(matching.getRight());

				// Will probably have to toy with this to get it to look decent on paper
				XWPFTable table = document.createTable(values.size(), 2);
				table.removeBorders();
				
				int row = 0;
				char lettering = 'A';
				for (HashMap.Entry<String, String> entry : values.entrySet()) {
					for (int column = 0; column < 2; column++) {
						if (column == 0) {
							table.getRow(row).getCell(column).setText(keys.get(row) + "  ____");
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setType(STTblWidth.DXA);
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(10000));
						}
						else if (column == 1) {
							table.getRow(row).getCell(column).setText(lettering + ". " + entry.getValue());
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setType(STTblWidth.DXA);
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(10000));
						}
					}
					row++;
					lettering ++;
				}
								
			}
			else if (question instanceof SingleAnswerQuestion) {
				SingleAnswerQuestion singleanswer = (SingleAnswerQuestion)question;
				
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
			numbering++;
		}
		
		XWPFParagraph referenceParagraph = document.createParagraph();
		referenceParagraph.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun refRun = referenceParagraph.createRun();
		
		if (quiz.getReferences() != null) {
			
			// Load data of reference image
			BufferedImage refImg;
			
			try {
				for (ReferenceMaterial reference : quiz.getReferences()) {
				    refImg = ImageIO.read(new File(reference.getFilepath()));
				    
					if (FileNameUtils.getExtension(reference.getFilepath()).equals("png"))
					{
						// NOTE: May have to add some sort of size restriction to the width and height parameters
						refRun.addPicture(new FileInputStream(reference.getFilepath()), XWPFDocument.PICTURE_TYPE_PNG, reference.getFilepath(), Units.toEMU(refImg.getWidth()), Units.toEMU(refImg.getHeight()));
						refRun.addBreak(BreakType.PAGE);
					}
					else if (FileNameUtils.getExtension(reference.getFilepath()).equals("jpeg") || FileNameUtils.getExtension(reference.getFilepath()).equals("jpg")) {
						// NOTE: May have to add some sort of size restriction to the width and height parameters
						refRun.addPicture(new FileInputStream(reference.getFilepath()), XWPFDocument.PICTURE_TYPE_JPEG, reference.getFilepath(), Units.toEMU(refImg.getWidth()), Units.toEMU(refImg.getHeight()));
						refRun.addBreak(BreakType.PAGE);
					}
				}
			    
			} 
			catch (IOException e) {
				
			}
			
		}
		
		String key_filepath = filepath + "_ANSWER_KEY.docx";
		TestKeyBuilder(quiz, key_filepath);
		
		// Write to file
		document.write(out);
		
		// Close stream and document
		out.close();
		document.close();
	}
	
	// For use with a built quiz (QuizBuilder)
	private void TestKeyBuilder(Quiz quiz, String filepath) throws Exception
	{
		// Make an empty document
		XWPFDocument document = new XWPFDocument();
	 
		// Make a file by specifying path of the document
		// Get filepath from GUI
		File newFile = new File(filepath);
	
		// File stream is used to write in the document
		FileOutputStream out = new FileOutputStream(newFile);
		
		ArrayList<Question> questionList = quiz.getQuestions();

		// Display Test Points (and also Test Key marker for now)
		// TODO: Change to full header functionality
		XWPFParagraph testPoints = document.createParagraph();
		testPoints.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun tpRun = testPoints.createRun();
		tpRun.setText(String.valueOf(quiz.getPointsPossible() + " points possible"));
		tpRun.addBreak();
		tpRun.setText("**TEST KEY**");
		
		// Display Question Name, Description, Points, Choices, and Reference Material
		int numbering = 1;
		for (Question question : questionList)
		{
			// Question builtQuestion = QuestionFactory.build(question.getId());
			
			XWPFParagraph questionParagraph = document.createParagraph();
			questionParagraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun questionRun = questionParagraph.createRun();
			
			if (question.getAbet()) {
				questionRun.setText(numbering + ") " + question.getDescription() + " - ABET Question");
				questionRun.addBreak();
			}
			else {
				questionRun.setText(numbering + ") " + question.getDescription());
				questionRun.addBreak();
			}
			
			questionRun.setText(String.valueOf(question.getPoints() + " points"));
			questionRun.addBreak();
			
			// Get type of question and print answers depending
			if (question instanceof MultipleChoiceQuestion) {
				MultipleChoiceQuestion multiChoice = (MultipleChoiceQuestion)question;
				
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
			else if (question instanceof MatchingQuestion) {
				MatchingQuestion matching = (MatchingQuestion)question;
				
				ArrayList<String> leftSide = new ArrayList<String>(matching.getLeft());
				HashMap<String, String> rightSide = new HashMap<String, String>(matching.getRight());
				
		        Collection<String> values = rightSide.values();
		        ArrayList<String> listOfValues = new ArrayList<>(values);
				
				// Will probably have to toy with this to get it to look decent on paper
				XWPFTable table = document.createTable(rightSide.size(), 2);
				table.removeBorders();
				
				int row = 0;
				char lettering = 'A';
				for (HashMap.Entry<String, String> entry : rightSide.entrySet()) {
					for (int column = 0; column < 2; column++) {
						if (column == 0) {
							System.out.println(rightSide.get(leftSide.get(row)));
							System.out.println(listOfValues.indexOf(rightSide.get(leftSide.get(row))));
							table.getRow(row).getCell(column).setText(leftSide.get(row) + " _" + Character.toString(listOfValues.indexOf(rightSide.get(leftSide.get(row))) + 65) + "_");
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setType(STTblWidth.DXA);
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(10000));
						}
						else if (column == 1) {
							table.getRow(row).getCell(column).setText(lettering + ". " + entry.getValue());
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setType(STTblWidth.DXA);
							table.getRow(row).getCell(column).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(10000));
						}
					}
					row++;
					lettering++;
				}
				
				/*for (HashMap.Entry<String, String> entry : rightSide.entrySet()) {
					for (int column = 0; column < 2; column++) {
						if (column == 0) {
							table.getRow(row).getCell(column).setText(leftSide.get(row) + " (" + rightSide.indexOf(leftSide.get(row)) + ")");
						}
						else if (column == 1) {
							table.getRow(row).getCell(column).setText(entry.getValue());
						}
					}
					row++;
				}*/
								
			}
			else if (question instanceof SingleAnswerQuestion) {
				SingleAnswerQuestion singleanswer = (SingleAnswerQuestion)question;
				
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
			numbering++;
		}
		
		XWPFParagraph referenceParagraph = document.createParagraph();
		referenceParagraph.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun refRun = referenceParagraph.createRun();
		
		if (quiz.getReferences() != null) {
			
			// Load data of reference image
			BufferedImage refImg;
			
			try {
				for (ReferenceMaterial reference : quiz.getReferences()) {
				    refImg = ImageIO.read(new File(reference.getFilepath()));
				    
					if (FileNameUtils.getExtension(reference.getFilepath()).equals("png"))
					{
						// NOTE: May have to add some sort of size restriction to the width and height parameters
						refRun.addPicture(new FileInputStream(reference.getFilepath()), XWPFDocument.PICTURE_TYPE_PNG, reference.getFilepath(), Units.toEMU(refImg.getWidth()), Units.toEMU(refImg.getHeight()));
						refRun.addBreak(BreakType.PAGE);
					}
					else if (FileNameUtils.getExtension(reference.getFilepath()).equals("jpeg") || FileNameUtils.getExtension(reference.getFilepath()).equals("jpg")) {
						// NOTE: May have to add some sort of size restriction to the width and height parameters
						refRun.addPicture(new FileInputStream(reference.getFilepath()), XWPFDocument.PICTURE_TYPE_JPEG, reference.getFilepath(), Units.toEMU(refImg.getWidth()), Units.toEMU(refImg.getHeight()));
						refRun.addBreak(BreakType.PAGE);
					}
				}
			    
			} 
			catch (IOException e) {
				
			}
			
		}
		
		// Write to file
		document.write(out);
		
		// Close stream and document
		out.close();
		document.close();
	}
	
	public void Shuffler(Quiz quiz, String filepath) throws Exception {
		quiz.shuffleQuestions();
		ArrayList<Question> questionList = quiz.getQuestions();
		
		for (Question question : questionList) {
			
			// Get type of question and print answers depending
			if (question instanceof MultipleChoiceQuestion) {
				
				if (question.getType().equals(QuestionType.MULTIPLE_CHOICE)) {
					question.shuffleChoices();
				}
				
			}
			else if (question instanceof MatchingQuestion) {
				
				question.shuffleChoices();
								
			}
			
		}
		DocumentBuilder(quiz, filepath);
		
	}

}