package cs499;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

public class DocUtils {
	
	public static XWPFDocument header(XWPFDocument doc, Quiz quiz) {
		
		XWPFHeaderFooterPolicy hfPolicy = doc.getHeaderFooterPolicy();
		if (hfPolicy == null) {
			 hfPolicy = doc.createHeaderFooterPolicy();
		}
		
		XWPFHeaderFooter header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
		
		XWPFTable table = header.createTable(2,3);
		table.removeBorders();
		table.setWidth("100%");
		
		XWPFTableRow row = table.getRow(0);
		row.getCell(0).setText(quiz.getShortCourse());
		row.getCell(1).setText(quiz.getName());
		row.getCell(2).setText(dateString(quiz.getDate()));
		
		row = table.getRow(1);
		row.getCell(0).setText(quiz.getInstructor().getName());
		row.getCell(1).setText(" ");
		row.getCell(2).setText(Float.toString(quiz.getPointsPossible()) + " Point Exam");		
		
		return doc;
		
	}
	
public static XWPFDocument keyHeader(XWPFDocument doc, Quiz quiz) {
		
		XWPFHeaderFooterPolicy hfPolicy = doc.getHeaderFooterPolicy();
		if (hfPolicy == null) {
			 hfPolicy = doc.createHeaderFooterPolicy();
		}
		
		XWPFHeaderFooter header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
		
		XWPFTable table = header.createTable(2,3);
		table.removeBorders();
		table.setWidth("100%");
		
		XWPFTableRow row = table.getRow(0);
		row.getCell(0).setText(quiz.getShortCourse());
		row.getCell(1).setText(quiz.getName());
		row.getCell(2).setText(dateString(quiz.getDate()));
		
		row = table.getRow(1);
		row.getCell(0).setText(quiz.getInstructor().getName());
		List<XWPFParagraph> par = row.getCell(1).getParagraphs();
		if(!par.isEmpty()) {
			XWPFRun run = par.get(0).createRun();
			run.setColor("FF0000");
			run.setText("TEST KEY");
			run.setBold(true);
		}
		else {
			XWPFParagraph newPar = row.getCell(1).addParagraph();
			XWPFRun run = newPar.createRun();
			run.setColor("FF0000");
			run.setText("TEST KEY");
			run.setBold(true);
		}
		
		
		row.getCell(2).setText(Float.toString(quiz.getPointsPossible()) + " Point Exam");		
		
		return doc;
		
	}
	
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
		
		XWPFParagraph par = new XWPFParagraph(ctp,doc);
		par.setAlignment(ParagraphAlignment.CENTER);
		
		XWPFHeaderFooterPolicy hfPolicy = doc.getHeaderFooterPolicy();
		if (hfPolicy == null) {
			 hfPolicy = doc.createHeaderFooterPolicy();
		}		 
		hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, new XWPFParagraph[] { par });
				
		return doc;
	}
	
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
	
	public static XWPFDocument copyCoverPage(String sourceDoc, String destDoc, Quiz quiz) {
		
		try {
			FileInputStream instream = new FileInputStream(sourceDoc);
			XWPFDocument source = new XWPFDocument(OPCPackage.open(instream));
			
			FileOutputStream outstream = new FileOutputStream(destDoc);
			XWPFDocument dest = new XWPFDocument();
			
			List<XWPFParagraph> paraList = source.getParagraphs();
			parbreak:
			for(XWPFParagraph par: paraList) {
				XWPFParagraph newpar = dest.createParagraph();
				for(XWPFRun r: par.getRuns()) {					
					XWPFRun newrun = newpar.createRun();
					newrun.setStyle(r.getStyle());
					newrun.setFontFamily(r.getFontFamily());
					newrun.setFontSize(r.getFontSizeAsDouble());
					newrun.setCharacterSpacing(r.getCharacterSpacing());
					newrun.setBold(r.isBold());
					if(r.text().equals("50")) {
						newrun.setText(Float.toString(quiz.getPointsPossible()));
					}
					else if(r.text().contains("This test covers sections")) {
						newrun.setText(quiz.getDescription());
					}
					else if(r.text().contains("1.1, 1.2")) {
						newrun.setText("");
					}
					else {
						newrun.setText(r.text());
					}
					if(!r.getCTR().getBrList().isEmpty()) {
						newrun.addBreak(BreakType.PAGE);
						break parbreak;
					}
				}
			}
			
			source.close();
			dest.write(outstream);
			return dest;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String dateString(String date) {
		if(date == null || date.isEmpty() || date.isBlank()) {
			return LocalDate.now().toString();
		}
		else {
			return LocalDateTime.parse(date).toLocalDate().toString();
		}
	}
	
	

}
