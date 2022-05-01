package cs499;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

import cs499.question.MatchingQuestion;
import cs499.question.MultipleChoiceQuestion;
import cs499.question.Question;
import cs499.question.QuestionType;
import cs499.utils.DataHelper;

public class DocUtils {

	private static final String POINTS = "{POINTS}";

	private static final String DESCRIPTION = "{DESCRIPTION}";

	private static CTStyles templateStyles;

	/**
	 * Generates header for test document
	 * 
	 * @param doc
	 * @param quiz
	 * @return
	 */
	public static XWPFDocument header(XWPFDocument doc, Quiz quiz, HashMap<String,String> headervalues) {
		
		
		XWPFHeaderFooterPolicy hfPolicy = doc.getHeaderFooterPolicy();
		if (hfPolicy == null) {
			hfPolicy = doc.createHeaderFooterPolicy();
		}

		XWPFHeaderFooter header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

		XWPFTable table = header.createTable(2, 3);
		table.removeBorders();
		table.setWidth("100%");

		XWPFTableRow row = table.getRow(0);
		if(quiz.getShortCourse() == null || quiz.getShortCourse().isEmpty()) {
			row.getCell(0).setText(headervalues.get("course"));
		}
		else {
			row.getCell(0).setText(quiz.getShortCourse());
		}
		row.getCell(1).setText(quiz.getName());
		row.getCell(2).setText(dateString(quiz.getDate()));
		
		row = table.getRow(1);
		if(quiz.getInstructor() == null) {
			row.getCell(0).setText(headervalues.get("instructor"));
		}
		else {
			row.getCell(0).setText(quiz.getInstructor().getName());
		}		
		row.getCell(1).setText(" ");
		row.getCell(2).setText(Float.toString(quiz.getPointsPossible()) + " Point Exam");

		return doc;

	}

	/**
	 * Generates header for test key document
	 * 
	 * @param doc
	 * @param quiz
	 * @return
	 */
	public static XWPFDocument keyHeader(XWPFDocument doc, Quiz quiz) {

		XWPFHeaderFooterPolicy hfPolicy = doc.getHeaderFooterPolicy();
		if (hfPolicy == null) {
			hfPolicy = doc.createHeaderFooterPolicy();
		}

		XWPFHeaderFooter header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

		XWPFTable table = header.createTable(2, 3);
		table.removeBorders();
		table.setWidth("100%");

		XWPFTableRow row = table.getRow(0);
		row.getCell(0).setText(quiz.getShortCourse());
		row.getCell(1).setText(quiz.getName());
		row.getCell(2).setText(dateString(quiz.getDate()));

		row = table.getRow(1);
		if(quiz.getInstructor() == null) {
			row.getCell(0).setText(" ");
		}
		else {
			row.getCell(0).setText(quiz.getInstructor().getName());
		}
		List<XWPFParagraph> par = row.getCell(1).getParagraphs();
		if (!par.isEmpty()) {
			XWPFRun run = par.get(0).createRun();
			run.setColor("FF0000");
			run.setText("TEST KEY");
			run.setBold(true);
		} else {
			XWPFParagraph newPar = row.getCell(1).addParagraph();
			XWPFRun run = newPar.createRun();
			run.setColor("FF0000");
			run.setText("TEST KEY");
			run.setBold(true);
		}

		row.getCell(2).setText(DataHelper.numToString(quiz.getPointsPossible()) + " Point Exam");

		return doc;

	}

	/**
	 * Generates footer with page numbering
	 * 
	 * @param doc
	 * @return
	 */
	public static XWPFDocument numberedFooter(XWPFDocument doc) {

		CTP ctp = CTP.Factory.newInstance();

		CTText text = ctp.addNewR().addNewT();
		text.setStringValue("Page ");
		text.setSpace(SpaceAttribute.Space.PRESERVE);

		CTR run = ctp.addNewR();
		run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		run.addNewInstrText().setStringValue(" PAGE ");
		run.addNewFldChar().setFldCharType(STFldCharType.END);

		text = ctp.addNewR().addNewT();
		text.setStringValue(" of ");
		text.setSpace(SpaceAttribute.Space.PRESERVE);

		run = ctp.addNewR();
		run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		run.addNewInstrText().setStringValue(" NUMPAGES ");
		run.addNewFldChar().setFldCharType(STFldCharType.END);

		XWPFParagraph par = new XWPFParagraph(ctp, doc);
		par.setAlignment(ParagraphAlignment.CENTER);

		XWPFHeaderFooterPolicy hfPolicy = doc.getHeaderFooterPolicy();
		if (hfPolicy == null) {
			hfPolicy = doc.createHeaderFooterPolicy();
		}
		hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, new XWPFParagraph[] { par });

		return doc;
	}

	/**
	 * Returns the correct format type for a given image file
	 * 
	 * @param filename
	 * @return
	 */
	public static int getImgFormat(String filename) {
		int format;
		if (filename.endsWith(".emf"))
			format = XWPFDocument.PICTURE_TYPE_EMF;
		else if (filename.endsWith(".wmf"))
			format = XWPFDocument.PICTURE_TYPE_WMF;
		else if (filename.endsWith(".pict"))
			format = XWPFDocument.PICTURE_TYPE_PICT;
		else if (filename.endsWith(".jpeg") || filename.endsWith(".jpg"))
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		else if (filename.endsWith(".png"))
			format = XWPFDocument.PICTURE_TYPE_PNG;
		else if (filename.endsWith(".dib"))
			format = XWPFDocument.PICTURE_TYPE_DIB;
		else if (filename.endsWith(".gif"))
			format = XWPFDocument.PICTURE_TYPE_GIF;
		else if (filename.endsWith(".tiff"))
			format = XWPFDocument.PICTURE_TYPE_TIFF;
		else if (filename.endsWith(".eps"))
			format = XWPFDocument.PICTURE_TYPE_EPS;
		else if (filename.endsWith(".bmp"))
			format = XWPFDocument.PICTURE_TYPE_BMP;
		else if (filename.endsWith(".wpg"))
			format = XWPFDocument.PICTURE_TYPE_WPG;
		else {
			return 0;
		}
		return format;
	}

	/**
	 * Copies the cover page from a source document
	 * 
	 * @param sourceDoc
	 * @param destDoc
	 * @param quiz
	 * @return
	 */
	public static XWPFDocument copyCoverPage(String sourceDoc, String destDoc, Quiz quiz) {

		try {
			FileInputStream instream = new FileInputStream(sourceDoc);
			XWPFDocument source = new XWPFDocument(OPCPackage.open(instream));

			FileOutputStream outstream = new FileOutputStream(destDoc);
			XWPFDocument dest = new XWPFDocument();

			templateStyles = source.getStyle();
			dest.createStyles().setStyles(templateStyles);

			List<XWPFParagraph> paraList = source.getParagraphs();
			parbreak: for (XWPFParagraph par : paraList) {
				XWPFParagraph newpar = dest.createParagraph();
				for (XWPFRun r : par.getRuns()) {
					XWPFRun newrun = newpar.createRun();
					newrun.setStyle(r.getStyle());
					newrun.setFontFamily(r.getFontFamily());
					newrun.setFontSize(r.getFontSizeAsDouble());
					newrun.setCharacterSpacing(r.getCharacterSpacing());
					newrun.setBold(r.isBold());
					if (r.text().equals(POINTS)) {
						newrun.setText(DataHelper.numToString(quiz.getPointsPossible()));
					} else if (r.text().contains(DESCRIPTION)) {
						newrun.setText(quiz.getDescription());
					} else {
						newrun.setText(r.text());
					}
					if (!r.getCTR().getBrList().isEmpty()) {
						newrun.addBreak(BreakType.PAGE);
						break parbreak;
					}
				}
			}

			source.close();
			dest.write(outstream);
			return dest;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Formats the date string to remove time information
	 * 
	 * @param date
	 * @return
	 */
	public static String dateString(String date) {
		if (date == null || date.isEmpty() || date.isBlank()) {
			return LocalDate.now().toString();
		} else {
			try {
				return LocalDateTime.parse(date).toLocalDate().toString();
			} catch(Exception e) {
				return date;
			}
			
		}
	}

	public static XWPFDocument trueFalseSection(XWPFDocument doc, List<MultipleChoiceQuestion> tfList,
			int startingNumber) {

		float points = 0;
		XWPFParagraph par = doc.createParagraph();
		XWPFRun run = par.createRun();
		for (MultipleChoiceQuestion mult : tfList) {
			points += mult.getPoints();
		}
		run.setText("Each question is worth " + DataHelper.numToString(points / tfList.size()) + " points.");

		XWPFTable tfTable = doc.createTable(tfList.size(), 4);
		tfTable.removeBorders();
		tfTable.setWidth("100%");

		for (int i = 0; i < tfList.size(); i++) {
			XWPFTableRow row = tfTable.getRow(i);
			row.getCell(0).setText(Integer.toString(startingNumber));
			row.getCell(1).setText("T");
			row.getCell(2).setText("F");
			row.getCell(3).setText(tfList.get(i).getDescription());
			startingNumber++;
		}
		return doc;
	}

	public static XWPFDocument trueFalseSection(XWPFDocument doc, QuestionGroup group, int startingNumber) {

		XWPFParagraph par = doc.createParagraph();
		XWPFRun run = par.createRun();
		run.setText(group.getDescription());
		run.addCarriageReturn();
		XWPFRun pointsRun = par.createRun();
		pointsRun.setText("Each question is worth " + DataHelper.numToString(group.getPoints()) + " points");

		XWPFTable tfTable = doc.createTable(group.getQuestions().size(), 4);
		tfTable.removeBorders();
		tfTable.setWidth("100%");

		for (int i = 0; i < group.getQuestions().size(); i++) {
			XWPFTableRow row = tfTable.getRow(i);
			row.getCell(0).setText(Integer.toString(startingNumber));
			row.getCell(1).setText("T");
			row.getCell(2).setText("F");
			row.getCell(3).setText(group.getQuestions().get(i).getDescription());
			startingNumber++;
		}
		return doc;
	}
	
	public static XWPFDocument trueFalseSectionKey(XWPFDocument doc, List<MultipleChoiceQuestion> tfList, int startingNumber) {
				float points = 0;
				XWPFParagraph par = doc.createParagraph();
				XWPFRun run = par.createRun();
				for (MultipleChoiceQuestion mult : tfList) {
					points += mult.getPoints();
				}
				run.setText("Each question is worth " + DataHelper.numToString(points / tfList.size()) + " points.");

				XWPFTable tfTable = doc.createTable(tfList.size(), 4);
				tfTable.removeBorders();
				tfTable.setWidth("100%");

				for (int i = 0; i < tfList.size(); i++) {
					XWPFTableRow row = tfTable.getRow(i);
					row.getCell(0).setText(Integer.toString(startingNumber));
					if(tfList.get(i).getCorrectAnswer().equals("True")) {
						List<XWPFParagraph> pars = row.getCell(1).getParagraphs();
						if(!pars.isEmpty()) {
							XWPFRun keyRun = pars.get(0).createRun();
							keyRun.setColor("FF0000");
							keyRun.setText("T");
							keyRun.setBold(true);
						}
						else {
							XWPFParagraph newPar = row.getCell(1).addParagraph();
							XWPFRun keyRun = newPar.createRun();
							keyRun.setColor("FF0000");
							keyRun.setText("T");
							keyRun.setBold(true);				
						}
					}
					else {
						row.getCell(1).setText("T");
					}
					if(tfList.get(i).getCorrectAnswer().equals("False")) {
						List<XWPFParagraph> pars = row.getCell(2).getParagraphs();
						if(!pars.isEmpty()) {
							XWPFRun keyRun = pars.get(0).createRun();
							keyRun.setColor("FF0000");
							keyRun.setText("F");
							keyRun.setBold(true);
						}
						else {
							XWPFParagraph newPar = row.getCell(2).addParagraph();
							XWPFRun keyRun = newPar.createRun();
							keyRun.setColor("FF0000");
							keyRun.setText("F");
							keyRun.setBold(true);				
						}
					}
					else {
						row.getCell(2).setText("F");
					}
										
					row.getCell(3).setText(tfList.get(i).getDescription());
					startingNumber++;
				}
		
		
		return doc;
	}

	public static XWPFDocument matchingQuestion(XWPFDocument doc, MatchingQuestion question) {
		XWPFTable matchingTable = doc.createTable(question.getLeft().size(), 4);
		matchingTable.removeBorders();
		matchingTable.setWidth("100%");
		char alpha = 'A';
		List<String> values = new ArrayList<String>(question.getRight().values());
		for (int i = 0; i < question.getLeft().size(); i++) {
			XWPFTableRow row = matchingTable.getRow(i);
			row.getCell(0).setText("______");
			row.getCell(1).setText(question.getLeft().get(i));
			row.getCell(2).setText(Character.toString(alpha));
			row.getCell(3).setText(values.get(i));
			question.getRight().put(values.get(i), Character.toString(alpha));
			alpha++;
		}

		return doc;
	}
	
	public static XWPFDocument matchingQuestionKey(XWPFDocument doc, MatchingQuestion question) {
		XWPFTable matchingTable = doc.createTable(question.getLeft().size(), 4);
		matchingTable.removeBorders();
		matchingTable.setWidth("100%");
		char alpha = 'A';
		List<String> values = new ArrayList<String>(question.getRight().values());

		for (int i = 0; i < question.getLeft().size(); i++) {
			XWPFTableRow row = matchingTable.getRow(i);
			String keyLetter = question.getRight().get(values.get(i));
			
			List<XWPFParagraph> pars = row.getCell(0).getParagraphs();
			if(!pars.isEmpty()) {
				XWPFRun run = pars.get(0).createRun();
				run.setColor("FF0000");
				run.setText(keyLetter);
				run.setBold(true);
			}
			else {
				XWPFParagraph newPar = row.getCell(1).addParagraph();
				XWPFRun run = newPar.createRun();
				run.setColor("FF0000");
				run.setText(keyLetter);
				run.setBold(true);				
			}

			row.getCell(1).setText(question.getLeft().get(i));
			row.getCell(2).setText(Character.toString(alpha));
			row.getCell(3).setText(values.get(i));
			alpha++;
		}

		return doc;
	}

	public static XWPFDocument multipleChoiceSection(XWPFDocument doc, Quiz quiz, int startingNumber) {
		ArrayList<MultipleChoiceQuestion> multipleChoiceList = new ArrayList<MultipleChoiceQuestion>();
		for (Question q : quiz.getQuestions()) {
			if (q.getType().equals(QuestionType.MULTIPLE_CHOICE)) {
				multipleChoiceList.add((MultipleChoiceQuestion) q);
			}
		}
		for (MultipleChoiceQuestion mult : multipleChoiceList) {
			XWPFParagraph par = doc.createParagraph();
			XWPFRun run1 = par.createRun();
			run1.setText(Integer.toString(startingNumber) + ".)");
			run1.addTab();
			XWPFRun run2 = par.createRun();
			run2.setText(mult.getDescription());
			run2.addCarriageReturn();
			char lettering = 'a';
			for (String choice : mult.getChoices()) {
				XWPFRun numRun = par.createRun();
				numRun.addTab();
				numRun.setText(Character.toString(lettering) + ".) ");
				XWPFRun choiceRun = par.createRun();
				choiceRun.setText(choice);
				lettering++;
			}
			startingNumber++;
		}

		return doc;
	}

	public static XWPFDocument insertQuizReference(XWPFDocument doc, Quiz quiz)
			throws IOException, InvalidFormatException {
		XWPFParagraph par = doc.createParagraph();
		for (ReferenceMaterial ref : quiz.getReferences()) {
			XWPFRun run = par.createRun();
			int format = getImgFormat(ref.getFilepath());
			BufferedImage refImg = ImageIO.read(new File(ref.getFilepath()));
			run.addPicture(new FileInputStream(ref.getFilepath()), format, ref.getDescription(),
					Units.toEMU(refImg.getWidth()), Units.toEMU(refImg.getHeight()));
			run.addCarriageReturn();
		}
		return doc;
	}

	public static XWPFParagraph insertQuestionReference(XWPFParagraph par, Question question)
			throws IOException, InvalidFormatException {

		question.getReference();
		XWPFRun run = par.createRun();
		int format = getImgFormat(question.getReference().getFilepath());
		BufferedImage refImg = ImageIO.read(new File(question.getReference().getFilepath()));
		run.addPicture(new FileInputStream(question.getReference().getFilepath()), format,
				question.getReference().getDescription(), Units.toEMU(refImg.getWidth()),
				Units.toEMU(refImg.getHeight()));
		run.addCarriageReturn();
		return par;

	}

}
