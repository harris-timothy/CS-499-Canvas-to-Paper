package cs499.qti;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cs499.qti.data_mapping.AssessmentType;
import cs499.qti.data_mapping.ItemType;
import cs499.qti.data_mapping.MaterialType;
import cs499.qti.data_mapping.MatimageType;
import cs499.qti.data_mapping.MattextType;
import cs499.qti.data_mapping.ObjectbankType;
import cs499.qti.data_mapping.QtimetadatafieldType;
import cs499.qti.data_mapping.QuestestinteropType;
import cs499.qti.data_mapping.RenderChoiceType;
import cs499.qti.data_mapping.ResponseLabelType;
import cs499.qti.data_mapping.ResponseLidType;
import cs499.qti.data_mapping.ResprocessingType;
import cs499.qti.data_mapping.SectionType;
import cs499.qti.data_mapping.SelectionType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ParseQTI {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    private static final int FIRST = 0;

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
    
    @SuppressWarnings("unchecked")
	public void xmlParse(String filepath) throws JAXBException {
    	
    	JAXBContext jc = JAXBContext.newInstance("cs499.qti.data_mapping");
    	
    	File testfile = new File("D:\\black\\Documents\\GitHub\\CS-499-Canvas-to-Paper\\Canvas to Paper\\qti\\g9ab9e81e56ff24bf6f9562fc60c25d80\\g9ab9e81e56ff24bf6f9562fc60c25d80.xml");
    	
    	Unmarshaller unmarshaller = jc.createUnmarshaller();
    	
		JAXBElement<QuestestinteropType> root = (JAXBElement<QuestestinteropType>) unmarshaller.unmarshal(testfile);
    	
    	QuestestinteropType qti = (QuestestinteropType) root.getValue();
    	
    	if(qti.getAssessment() != null) {
    		parseAssessment(qti.getAssessment());
    		
    	}
    	else if(qti.getObjectbank() != null) {
    		
    	}    	    			
    	//get assessment QTI id + title and store in quiz table
    	
    	
    	
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
    
    private void parseAssessment(AssessmentType assessment) {
    	SectionType rootSection = (SectionType) assessment.getSectionrefOrSection().get(FIRST);

    	for(Object a: rootSection.getItemrefOrItemOrSectionref()) {
    		if (a instanceof SectionType) {
    			System.out.println(((SectionType)a).getTitle());
    			SelectionType selection = ((SectionType)a).getSelectionOrdering().getSelection().get(FIRST);
    			System.out.println(selection.getSelectionNumber());
    			System.out.println(selection.getSourcebankRef());

    			Element test = (Element) removeNull(selection.getSelectionExtension().getContent()).get(FIRST);
    			System.out.println(test.getFirstChild().getTextContent());
    		}
    		else if (a instanceof ItemType) {
    			ItemType item = (ItemType) a;
    			parseItem(item);			

    		}
    	}


    }
    
    private void parseBank(ObjectbankType bank) {
    	
    }
    
    private void parseItem(ItemType item) {
    	
    	List<QtimetadatafieldType> fields = item.getItemmetadata().getQtimetadata().get(0).getQtimetadatafield();
		for(Object f: fields) {
			System.out.println(((QtimetadatafieldType) f).getFieldlabel());
			System.out.println(((QtimetadatafieldType) f).getFieldentry());  	
		}
		for(Object o : item.getPresentation().getMaterialOrResponseLidOrResponseXy()) {
			if(o instanceof MaterialType) {
				parseMaterial((MaterialType) o);
			}
			else if (o instanceof ResponseLidType) {
				parseResponseLid((ResponseLidType) o);
			}						
		}
		for(Object r: item.getResprocessing()) {
			parseResprocessing((ResprocessingType) r);
		}
		
    	
    }
    
    @SuppressWarnings("rawtypes")
	private void parseResponseLid(ResponseLidType responseLid) {
    	
		List<JAXBElement<?>> list = removeNull(responseLid.getContent());
		
		for(JAXBElement<?> e: list) {
								
			if(e.getValue() instanceof MaterialType) {
				parseMaterial((MaterialType) e.getValue());
			}
			else if (e.getValue() instanceof RenderChoiceType) {
				RenderChoiceType render = (RenderChoiceType) e.getValue();
				for(Object val: render.getMaterialOrMaterialRefOrResponseLabel()) {
					if(val instanceof MaterialType) {
						parseMaterial((MaterialType) val);
					}
					else if (val instanceof ResponseLabelType) {
						List<Serializable> label = removeNull(((ResponseLabelType) val).getContent());
						for(Object t: label) {
							JAXBElement element = (JAXBElement) t;
							if(element.getValue() instanceof MaterialType) {
								parseMaterial((MaterialType) element.getValue());
							}
						}								
					}
				}
			}
		}
    	
    }
	
	private void parseMaterial(MaterialType material) {
		for(Object mat: material.getMattextOrMatemtextOrMatimage()) {
			if(mat instanceof MattextType) {
				MattextType text = (MattextType) mat;
				System.out.println(text.getValue());
			}
			else if (mat instanceof MatimageType) {
				System.out.println("image");
				//save as reference material?
			}					
		}
	}
	
	private void parseResprocessing(ResprocessingType resprocessing) {
		//outcomes->decvar - maxvalue
		//respcondition->conditionvar->varequal - value
		//setvar - value
		//for storage - if maxvalue = 100, value = percentage of question points
		
	}
	
	private <T> List<T> removeNull(List<T> list) {
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) instanceof String) {
				if(((String) list.get(i)).trim().isEmpty()) {
					list.remove(i);
				}
			}
		}
		list.removeAll(Collections.singletonList(null));
		list.removeAll(Collections.singletonList(""));
		return list;
	}
	
}


