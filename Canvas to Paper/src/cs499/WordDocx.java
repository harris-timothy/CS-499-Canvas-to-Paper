package cs499;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import cs499.question.Question;
 
// To import the Apache library to create document file object import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordDocx
{
	/*private ArrayList<Question> blacklistQuestions(ArrayList<Question> questions, ArrayList<Integer> blacklist) 
	{
		ArrayList<Question> curatedQuestions = new ArrayList<Question>();
		
		// Create a list of questions with blacklisted questions removed
		for (int i = 0; i < questions.size(); i++)
		{
			// Blacklist pseudocode:
			for (int j = 0; j < blacklist.size(); j++)
			{
				if (questions.get(i).getId() != blacklist.get(j))
				{
					curatedQuestions.add(questions.get(i));
								
				}
				else
				{
					continue; // Something like that
				}
			}			
		}
		
		return curatedQuestions;
	}*/
	
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
		// ArrayList<Question> curatedQuestions = blacklistQuestions(questionList, blacklist);
		
		// How to get Blacklist IDs?
		
		// TODO: 
		// - Quiz
		// - QuizDoc and TestKeyDoc to differentiate between quiz and key
		//		- Same Quiz object would belong to both
		// - Maybe two different methods for Quiz and Test Key and possibly get rid of QuizDoc/TestKeyDoc
		//		- Test Key needs ABET marking
		// - Test points implementation - getPointsPossible()
		// - Properly display all necessary parsed data
		// 		- Question files
		// ---
		// Whitelist idea:
		// NOTE: Need a check for if # of whitelisted questions > # of chosen questions from pool
		// Loop through arraylist of questions from database, copy all non-blacklisted questions (by ID) into a new arraylist
		// Get a count of how many questions to remove (# of questions in arraylist - # of questions on quiz)
		// While count > 0, loop through array however many times needed, delete questions that aren't whitelisted, subtract from count each time
		
		// For doc generation
		// Header generation
		// Add questions with points and question number
		// Page numbers
		// User-set font, font-size, etc (accessible through GUI somehow)

		
		//Create Question 1 
		/*XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(questionList.get(i).getName()); // This may need to be changed. Shuffled questions means this might not make sense.
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setSpacingAfter(800);
		XWPFParagraph paragraph2 = document.createParagraph();
		XWPFRun run2 = paragraph2.createRun();
		// Change question number
		run2.setText((i + 1) + ") " + questionList.get(i).getDescription());
		run2.addBreak();*/
		// Dynamically deal with answers
		// Need info from Emily on how answers work
		/*
		 * 
		XWPFParagraph paragraph3 = document.createParagraph();
		XWPFRun run3 = paragraph3.createRun();
		run3.setText("A. answer 1 ");
		run3.addBreak();
		XWPFParagraph paragraph4 = document.createParagraph();
		XWPFRun run4 = paragraph4.createRun();
		run4.setText("B. answer 2 ");
		run4.addBreak();
		XWPFParagraph paragraph5 = document.createParagraph();
		XWPFRun run5 = paragraph5.createRun();
		run5.setText("C. answer 3 ");
		run5.addBreak();
		XWPFParagraph paragraph6 = document.createParagraph();
		XWPFRun run6= paragraph6.createRun();
		run6.setText("D. answer 4 ");
		run6.addBreak();
		*/
		
		// Commented out for now
		/*
		//Create Question 1 
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText("CS499 test word doc ");
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setSpacingAfter(800);
		XWPFParagraph paragraph2 = document.createParagraph();
		XWPFRun run2 = paragraph2.createRun();
		// Change question number
		run2.setText("1. bla bla bla bla bla  ");
		run2.addBreak();
		// Dynamically deal with answers
		XWPFParagraph paragraph3 = document.createParagraph();
		XWPFRun run3 = paragraph3.createRun();
		run3.setText("A. answer 1 ");
		run3.addBreak();
		XWPFParagraph paragraph4 = document.createParagraph();
		XWPFRun run4 = paragraph4.createRun();
		run4.setText("B. answer 2 ");
		run4.addBreak();
		XWPFParagraph paragraph5 = document.createParagraph();
		XWPFRun run5 = paragraph5.createRun();
		run5.setText("C. answer 3 ");
		run5.addBreak();
		XWPFParagraph paragraph6 = document.createParagraph();
		XWPFRun run6= paragraph6.createRun();
		run6.setText("D. answer 4 ");
		run6.addBreak();
		
		//Create Question 2
		XWPFParagraph paragraph7 = document.createParagraph();
		XWPFRun run7 = paragraph7.createRun();
		run7.setText("2. bla bla bla bla bla  ");
		run7.addBreak();
		XWPFParagraph paragraph8 = document.createParagraph();
		XWPFRun run8 = paragraph8.createRun();
		run8.setText("A. answer 1 ");
		run8.addBreak();
		XWPFParagraph paragraph9 = document.createParagraph();
		XWPFRun run9 = paragraph9.createRun();
		run9.setText("B. answer 2 ");
		run9.addBreak();
		XWPFParagraph paragraph10 = document.createParagraph();
		XWPFRun run10 = paragraph10.createRun();
		run10.setText("C. answer 3 ");
		run10.addBreak();
		XWPFParagraph paragraph11 = document.createParagraph();
		XWPFRun run11 = paragraph11.createRun();
		run11.setText("D. answer 4 ");
		run11.addBreak();
		
		//Create Question 3
		XWPFParagraph paragraph12 = document.createParagraph();
		XWPFRun run12 = paragraph12.createRun();
		run12.setText("3. bla bla bla bla bla  ");
		run12.addBreak();
		XWPFParagraph paragraph13 = document.createParagraph();
		XWPFRun run13 = paragraph13.createRun();
		run13.setText("A. answer 1 ");
		run13.addBreak();
		XWPFParagraph paragraph14 = document.createParagraph();
		XWPFRun run14 = paragraph14.createRun();
		run14.setText("B. answer 2 ");
		run14.addBreak();
		XWPFParagraph paragraph15 = document.createParagraph();
		XWPFRun run15 = paragraph15.createRun();
		run15.setText("C. answer 3 ");
		run15.addBreak();
		XWPFParagraph paragraph16 = document.createParagraph();
		XWPFRun run16 = paragraph16.createRun();
		run16.setText("D. answer 4 ");
		run16.addBreak();
		
		
		// Highlight the correct answer for each problem
		run4.setColor("FF3333");
		run10.setColor("FF3333");
		run13.setColor("FF3333");
		*/
		
		// to add content to the document
		document.write(out);
		
		// close the document
		out.close();
	}

}