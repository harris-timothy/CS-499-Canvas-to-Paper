package cs499;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
 
// To import the Apache library to create document file object import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordDocx
{
public static void main(String[] args) throws Exception
{
// Make an empty document
XWPFDocument document = new XWPFDocument();
 
// Make a file by specifying path of the document
File newFile = new File("D:/word.docx");

// File stream is used to write in the document
FileOutputStream out = new FileOutputStream(newFile);

//Create Question 1 
XWPFParagraph paragraph = document.createParagraph();
XWPFRun run = paragraph.createRun();
run.setText("CS499 test word doc ");
paragraph.setAlignment(ParagraphAlignment.CENTER);
paragraph.setSpacingAfter(800);
XWPFParagraph paragraph2 = document.createParagraph();
XWPFRun run2 = paragraph2.createRun();
run2.setText("1. bla bla bla bla bla  ");
run2.addBreak();
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

// to add content to the document
document.write(out);

// close the documents
out.close();
document.close();
}

}