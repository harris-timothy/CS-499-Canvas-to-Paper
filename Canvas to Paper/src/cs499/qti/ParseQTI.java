package cs499.qti;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

import cs499.qti.data_mapping.AssessmentType;
import cs499.qti.data_mapping.QuestestinteropType;
import cs499.qti.data_mapping.SectionType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ParseQTI {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

	private static ArrayList<Document> parsedFiles = new ArrayList<Document>();
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));

        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
                System.out.println(filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                System.out.println(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    
    public ArrayList<Document> xmlLoop(String filePath)
    {
    	  File dir = new File(filePath);
    	  File[] directoryListing = dir.listFiles();
    	  for (File child : directoryListing) {
    		  if(child.isDirectory())
    		  {
    			  xmlLoop(child.getPath());
    		  }
    		  else
    		  {
    			  String ext = "";
        		  String fileName = child.getName();
        		  int i = fileName.lastIndexOf('.');
        		  if (i >= 0) { 
        			  ext = fileName.substring(i+1); 
        		  }
        		  
        		  if (ext.equals("xml"))
        		  {
        			  try {
						xmlParse(child.getPath());
					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			  // CHANGE THIS TO THE DATABASE ONCE READY
        			  //parsedFiles.add(xmlParse(child.getPath()));
        		  }
    		  }
    	  }
    	  return(parsedFiles);
    }
    
    private Document xmlParseOld(String filePath)
    {
    	Document doc = null;
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            // Combine with XPath? Look into later
            // Might want to look at this later:
            /*NodeList nList = doc.getElementsByTagName("student");
            System.out.println("----------------------------");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
               Node nNode = nList.item(temp);
               System.out.println("\nCurrent Element :" + nNode.getNodeName());
               
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  System.out.println("Student roll no : " 
                     + eElement.getAttribute("rollno"));
                  System.out.println("First Name : " 
                     + eElement
                     .getElementsByTagName("firstname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Last Name : " 
                     + eElement
                     .getElementsByTagName("lastname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Nick Name : " 
                     + eElement
                     .getElementsByTagName("nickname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Marks : " 
                     + eElement
                     .getElementsByTagName("marks")
                     .item(0)
                     .getTextContent());
               }
            }*/
         } catch (Exception e) {
            e.printStackTrace();
         }
        
        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		return doc;
    }
    
    public void xmlParse(String filepath) throws JAXBException {
    	
    	JAXBContext jc = JAXBContext.newInstance("cs499.qti.data_mapping");
    	Unmarshaller unmarshaller = jc.createUnmarshaller();
    	
    	QuestestinteropType questestinterop = (QuestestinteropType) unmarshaller.unmarshal(new File(filepath));
    	
    	AssessmentType assessment = questestinterop.getAssessment();
    	System.out.println(assessment);
    	//get assessment QTI id + title and store in quiz table
    	
    	//SectionType section = (SectionType) assessment.getSectionrefOrSection().get(0);
    	// add option for sectionref type
    	
    	//List<Object> items = section.getItemrefOrItemOrSectionref();
    	
    	//selectionordering could also be here instead of item
    	//selection->sourcebankref + selection_number + selection_extension->points_per_item
    	
    	
    	
    	//for(int i = 0; i < items.size(); i++) {
    		//items.get(i);
    		//check item type
    		//get ident(qti_id) and title and store in question table
    		
    		//get item metadata
    		//get question type, points possible, assessment_question_identifierref(assessment_qti_id)
    		//get presentation
    		//get material
    		//store in question table
    		//get responses
    		//get response material
    		//render_choice -> material = answer
    		//resprocessing -> respcondition -> conditionvar -> varequal value = correct answer for multiple choice
    		//}
    		
    		
    		
    	
    	
    	
    	
    	
    	
    	
    	
    }
    
}
