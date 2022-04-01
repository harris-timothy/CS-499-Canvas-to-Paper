package cs499.qti;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateQTI {
	//TODO: assessment_meta, questions(?), quiz bank(?), create QTI folders
	
	public void CreateQuiz() {
		try (Scanner scanner = new Scanner(System.in)){
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		    Document doc = docBuilder.newDocument();
		    
		    // Create root node
		    Element rootElement = doc.createElement("questestinterop");
		    rootElement.setAttribute("xmlns", "http://www.imsglobal.org/xsd/ims_qtiasiv1p2");
		    rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		    rootElement.setAttribute("xsi:schemaLocation", "http://www.imsglobal.org/xsd/ims_qtiasiv1p2 http://www.imsglobal.org/xsd/ims_qtiasiv1p2p1.xsd");
		    doc.appendChild(rootElement);
		    
		    // Create assessment element
		    Element assessment = doc.createElement("assessment");
		    assessment.setAttribute("ident", "UUID goes here");
		    System.out.println("Enter title of Quiz");
			String title = scanner.nextLine();
			assessment.setAttribute("title", title);
		    rootElement.appendChild(assessment);
		    
		    // Create qtimetadata element
		    Element qtimetadata = doc.createElement("qtimetadata");
		    assessment.appendChild(qtimetadata);
		    
		    // Create elements related to time limit
		    Element qtimetadatafield_timelimit = doc.createElement("qtimetadatafield");
		    qtimetadata.appendChild(qtimetadatafield_timelimit);
		    Element fieldlabel_timelimit = doc.createElement("fieldlabel");
		    fieldlabel_timelimit.appendChild(doc.createTextNode("qmd_timelimit"));
		    Element fieldentry_timelimit = doc.createElement("fieldentry");
		    System.out.println("Enter time limit of quiz (in minutes)");
		    String timelimit = scanner.nextLine();
		    fieldentry_timelimit.appendChild(doc.createTextNode(timelimit));
		    qtimetadatafield_timelimit.appendChild(fieldlabel_timelimit);
		    qtimetadatafield_timelimit.appendChild(fieldentry_timelimit);
		    
		    qtimetadata.appendChild(qtimetadatafield_timelimit);
		    
		    // Create elements related to max attempts
		    Element qtimetadatafield_maxattempts = doc.createElement("qtimetadatafield");
		    qtimetadata.appendChild(qtimetadatafield_maxattempts);
		    Element fieldlabel_maxattempts = doc.createElement("fieldlabel");
		    fieldlabel_maxattempts.appendChild(doc.createTextNode("cc_maxattempts"));
		    Element fieldentry_maxattempts = doc.createElement("fieldentry");
		    System.out.println("Enter number of attempts");
		    String maxattempts = scanner.nextLine();
		    fieldentry_maxattempts.appendChild(doc.createTextNode(maxattempts));
		    qtimetadatafield_maxattempts.appendChild(fieldlabel_maxattempts);
		    qtimetadatafield_maxattempts.appendChild(fieldentry_maxattempts);
		    
		    qtimetadata.appendChild(qtimetadatafield_maxattempts);
		    
		    // TODO: Further elements that require UUIDs
		    // Create parent section element
		    // Create child section elements (require UUIDs)
		    // Create section_ordering element
		    // Create sourcebank_ref element	| Have user choose questionbank source here
		    // Create selection_number element	| Have user choose how many questions from questionbank to pull here
		    // Create selection_extention element
		    // Create points_per_item element	| Have user choose points per question here
		    // Repeat for however many sections in the quiz
		    // Prompt user if they would like to add another section 
		    
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(doc);
		    StreamResult result = new StreamResult(new File("C:/Users/gamin/Desktop/499/TestQuiz.xml"));
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		    transformer.transform(source, result);
		    System.out.println("Quiz created.");
		} 
		catch (ParserConfigurationException pce) {
		    pce.printStackTrace();
		} 
		catch (TransformerException tfe) {
		    tfe.printStackTrace();
		}
	}
	
}
