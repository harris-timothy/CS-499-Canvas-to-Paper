package cs499;

import java.util.HashMap;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

public class DocUtils {
	
	public static XWPFDocument header(XWPFDocument doc, Quiz quiz) {
		
		//TODO: fix once data source is defined
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
		row.getCell(2).setText(quiz.getDate());
		
		row = table.getRow(1);
		row.getCell(0).setText(quiz.getInstructor().getName());
		row.getCell(1).setText(" ");
		row.getCell(2).setText(" ");		
		
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
	
	

}
