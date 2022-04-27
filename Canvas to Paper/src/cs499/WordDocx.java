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
import java.util.List;

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
	
	private void DocumentBuilder(Quiz quiz, String filepath, String templatepath, List<MultipleChoiceQuestion> tfList) throws Exception
	{
		// Make an empty document
		XWPFDocument document = DocUtils.copyCoverPage(templatepath, filepath, quiz);
	 
		// Make a file by specifying path of the document
		// Get filepath from GUI
		File newFile = new File(filepath);
	
		// File stream is used to write in the document
		FileOutputStream out = new FileOutputStream(newFile);
		
		// quiz.shuffleQuestions();
		ArrayList<Question> questionList = quiz.getQuestions();
		
		DocUtils.header(document, quiz);
		DocUtils.numberedFooter(document);
		
				
		// Display Description, Points, Choices, and Reference Material
		int numbering = 1;
		if(!tfList.isEmpty()) {
			DocUtils.trueFalseSection(document, tfList, numbering);
			numbering = numbering + tfList.size();
		}
		
		for (Question question : questionList)
		{	
			if(question == null) break;
			if(question.getType() == QuestionType.TRUE_FALSE) break;
			
			XWPFParagraph questionParagraph = document.createParagraph();
			questionParagraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun questionRun = questionParagraph.createRun();
			
			if(question.getReference() != null) {
				DocUtils.insertQuestionReference(questionParagraph, question);
			}
			
			questionRun.setText(numbering + ") " + question.getDescription());
			questionRun.addBreak();
			
			questionRun.setText(String.valueOf(question.getPoints() + " points"));
			questionRun.addBreak();			
			
			// Get type of question and print answers depending
			if (question instanceof MultipleChoiceQuestion) {
				MultipleChoiceQuestion multiChoice = (MultipleChoiceQuestion)question;				
				ArrayList<String> choices = multiChoice.getChoices();
								
				char choiceLetter = 'a';
				for (String choice : choices) {
					questionRun.addTab(); // NOTE: This may need to go outside of the for loop, with a removeTab() after. Unsure how it will behave.
					questionRun.setText(choiceLetter + ") " + choice);
					questionRun.addBreak();
					choiceLetter++;
				}
			}
			else if (question instanceof MatchingQuestion) {
				DocUtils.matchingQuestion(document, (MatchingQuestion)question);					
			}
			numbering++;
		}
		
				
		if (quiz.getReferences() != null) {
			
			DocUtils.insertQuizReference(document, quiz);
			
		}
		
		String key_filepath = filepath + "_ANSWER_KEY.docx";
		TestKeyBuilder(quiz, key_filepath, templatepath, tfList);
		
		// Write to file
		document.write(out);
		
		// Close stream and document
		out.close();
		document.close();
	}
	
	//TODO add template file input
	//TODO use section functions
	
	// For use with a built quiz (QuizBuilder)
	private void TestKeyBuilder(Quiz quiz, String filepath, String templatepath, List<MultipleChoiceQuestion> tfList) throws Exception
	{
		// Make an empty document
		XWPFDocument document = DocUtils.copyCoverPage(templatepath, filepath, quiz);
	 
		// Make a file by specifying path of the document
		// Get filepath from GUI
		File newFile = new File(filepath);
	
		// File stream is used to write in the document
		FileOutputStream out = new FileOutputStream(newFile);
		
		ArrayList<Question> questionList = quiz.getQuestions();
		
		DocUtils.keyHeader(document, quiz);
		DocUtils.numberedFooter(document);
		
		// Display Question Name, Description, Points, Choices, and Reference Material
		int numbering = 1;
		if(!tfList.isEmpty()) {
			DocUtils.trueFalseSectionKey(document, tfList, numbering);
			numbering = numbering + tfList.size();
		}		
		
		for (Question question : questionList)
		{
			if(question == null) break;
			if(question.getType() == QuestionType.TRUE_FALSE) break;
			
			
			XWPFParagraph questionParagraph = document.createParagraph();
			questionParagraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun questionRun = questionParagraph.createRun();
			
			if(question.getReference() != null) {
				DocUtils.insertQuestionReference(questionParagraph, question);
			}
			
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
					if (choice.equals(multiChoice.getCorrectAnswer())) {
						questionRun.setBold(true);
						questionRun.setText(choiceLetter + ") " + choice + " - **CORRECT ANSWER**");
						questionRun.setColor("FF0000");
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
				DocUtils.matchingQuestionKey(document, (MatchingQuestion)question);
				
			}
			numbering++;
		}
				
		if (quiz.getReferences() != null) {
			
			DocUtils.insertQuizReference(document, quiz);			
		}
		
		// Write to file
		document.write(out);
		
		// Close stream and document
		out.close();
		document.close();
	}
	
	public void Shuffler(Quiz quiz, String filepath, String templatepath) throws Exception {
		
		quiz.shuffleQuestions();
		List<MultipleChoiceQuestion> tfList = new ArrayList<MultipleChoiceQuestion>();
		for(Question q: quiz.getQuestions()) {
			if(q == null) break;
			if(q.getType() == QuestionType.TRUE_FALSE) {
				tfList.add((MultipleChoiceQuestion) q);
			}
		}
		System.out.println(tfList);
		ArrayList<Question> questionList = quiz.getQuestions();
		
		for (Question question : questionList) {
			if(question == null) break;
			question.shuffleChoices();		
		}
		
		//TODO sort questions by type
		//extract true/false questions
		//add t/f list to
		DocumentBuilder(quiz, filepath, templatepath, tfList);
		
	}

}