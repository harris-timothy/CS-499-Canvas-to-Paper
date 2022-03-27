package cs499.qti;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cs499.qti.data_mapping.*;
import cs499.question.AnswerFormatter;
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
    
    /**
     * @param filePath
     * @return
     */
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
        		  
        		  if (ext.equals("xml") || ext.equals("qti"))
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
    	  return null;
    }
        
    /**
     * @param filepath
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
	public void xmlParse(String filepath) throws JAXBException {
    	
    	JAXBContext jc = JAXBContext.newInstance("cs499.qti.data_mapping");
    	
    	File testfile = new File("D:\\black\\Documents\\GitHub\\CS-499-Canvas-to-Paper\\Canvas to Paper\\qti\\gd360ceb1d866eef9962487e2be79b4b8\\gd360ceb1d866eef9962487e2be79b4b8.xml");
    	
    	Unmarshaller unmarshaller = jc.createUnmarshaller();
    	
		JAXBElement<QuestestinteropType> root = (JAXBElement<QuestestinteropType>) unmarshaller.unmarshal(testfile);
    	
    	QuestestinteropType qti = (QuestestinteropType) root.getValue();
    	
    	if(qti.getAssessment() != null) {
    		parseAssessment(qti.getAssessment());
    		
    	}
    	else if(qti.getObjectbank() != null) {
    		parseBank(qti.getObjectbank());
    	}    	    			
    		
       }
    
    /**
     * @param assessment
     */
    private void parseAssessment(AssessmentType assessment) {
    	
    	//store quiz has to go in this function somewhere, so we need all the quiz info to populate to here
    	// arraylist questionIds stores ids to add to quiz_questions table
    	ArrayList<Integer> questionIds = new ArrayList<Integer>();
    	
    	SectionType rootSection = (SectionType) assessment.getSectionrefOrSection().get(FIRST);

    	for(Object a: rootSection.getItemrefOrItemOrSectionref()) {
    		if (a instanceof SectionType) {
    			//this is where all of the quiz to bank information comes from
    			System.out.println(((SectionType)a).getTitle()); // quiz title
    			System.out.println(((SectionType)a).getIdent()); // assessment_qti_id
    			SelectionType selection = ((SectionType)a).getSelectionOrdering().getSelection().get(FIRST);
    			System.out.println(selection.getSelectionNumber()); // number to choose from bank
    			System.out.println(selection.getSourcebankRef()); // bank qti_id

    			Element test = (Element) removeNull(selection.getSelectionExtension().getContent()).get(FIRST);
    			System.out.println(test.getFirstChild().getTextContent()); // points per item
    			
    			//data from here goes into question group table
    		}
    		else if (a instanceof ItemType) {
    			ItemType item = (ItemType) a;
    			questionIds.add(parseItem(item));			

    		}
    	}
    }
    
    /**
     * @param bank
     */
    private void parseBank(ObjectbankType bank) {
    	//store bank has to happen in this function somewhere, so all bank info has to populate up
    	//Arraylist of question ids to insert into bank questions table
    	
    	ArrayList<Integer> questionIds = new ArrayList<Integer>();
    	
    	System.out.println(bank.getIdent()); //bank qti_id
    	List<QtimetadatafieldType> fields = bank.getQtimetadata().get(FIRST).getQtimetadatafield();
    	for(Object f: fields) {
			System.out.println(((QtimetadatafieldType) f).getFieldlabel()); //attributes - bank_title
			System.out.println(((QtimetadatafieldType) f).getFieldentry()); //values - bank_title 	
		}
    	for(Object s: bank.getSectionOrItem()) {
    		if(s instanceof ItemType) {
    			questionIds.add(parseItem((ItemType) s));
    		}
    	}
    	//function for question to bank association goes here
    	
    }
    
    
    /**
     * @param item
     * @return
     */
    private int parseItem(ItemType item) {
    	//store question has to happen in here somewhere
    	//maybe return question id after storing question?
    	//insert ids into quiz_to_question or bank_questions as applicable
    	int questionId = 0;
    	
    	HashMap<String, String> values = new HashMap<String, String>();
    	values.put("name", item.getTitle());
    	
    	List<QtimetadatafieldType> fields = item.getItemmetadata().getQtimetadata().get(FIRST).getQtimetadatafield();
    	for(Object f: fields) {
			String label = ((QtimetadatafieldType) f).getFieldlabel();
			if(label == "question_type") {
				values.put("question_type", ((QtimetadatafieldType) f).getFieldentry());
			}
			else if (label == "points_possible") {
				values.put("points_possible", ((QtimetadatafieldType) f).getFieldentry());
			}			
		}
    	
		for(Object o : item.getPresentation().getMaterialOrResponseLidOrResponseXy()) {
			if(o instanceof MaterialType) {
				values.put("description", parseMaterial((MaterialType) o));
			}
			else if (o instanceof ResponseLidType) {
				parseResponseLid((ResponseLidType) o, values.get("question_type"));
			}						
		}
		
		for(Object r: item.getResprocessing()) {
			parseResprocessing((ResprocessingType) r);
		}
		
		//use hashmap to store question
		//get id from stored question
		return questionId;
		
    	
    }
    
    /**
     * @param responseLid
     */
    @SuppressWarnings("rawtypes")
	private HashMap<String, String> parseResponseLid(ResponseLidType responseLid, String type) {
    	
    	List<JAXBElement<?>> list = removeNull(responseLid.getContent());
		
    	if(type == "multiple_choice_question" || 
    			 
    			type == "true_false_question" ||
    			type == "multiple_dropdowns_question") {
    		//do stuff
    	}
    	else if(type == "matching_question") {
    		//do stuff
    	}
    	else if(type == "short_answer_question" || 
    			type == "essay_question" ||
    			type == "numerical_question" ||
    			type == "fill_in_multiple_blanks_question") {
    		//do stuff
    	}
    	else {
    		//do stuff
    	}
    	
		for(JAXBElement<?> e: list) {
								
			if(e.getValue() instanceof MaterialType) {
				
				parseMaterial((MaterialType) e.getValue());
				//returns string
			}
			else if (e.getValue() instanceof RenderChoiceType) {
				RenderChoiceType render = (RenderChoiceType) e.getValue();
				for(Object val: render.getMaterialOrMaterialRefOrResponseLabel()) {
					if(val instanceof MaterialType) {
						parseMaterial((MaterialType) val);
						//returns string
					}
					else if (val instanceof ResponseLabelType) {
						System.out.println(((ResponseLabelType) val).getIdent()); 
						List<Serializable> label = removeNull(((ResponseLabelType) val).getContent());
						for(Object t: label) {
							JAXBElement element = (JAXBElement) t;
							if(element.getValue() instanceof MaterialType) {
								parseMaterial((MaterialType) element.getValue());
								//returns string
							}
						}								
					}
				}
			}
		}
		return null;
    	
    }
	
	/**
	 * @param material
	 * @return
	 */
	private String parseMaterial(MaterialType material) {
		String text = "";
		for(Object mat: material.getMattextOrMatemtextOrMatimage()) {
			if(mat instanceof MattextType) {
				MattextType mattext = (MattextType) mat;
				text = mattext.getValue(); //question or answer text
			}					
		}
		return Jsoup.parse(text).text();
	}
	
	/**
	 * @param resprocessing
	 */
	private void parseResprocessing(ResprocessingType resprocessing) {
		DecvarType decvar = (DecvarType) resprocessing.getOutcomes().getDecvarAndInterpretvar().get(FIRST);
		System.out.println(decvar.getMaxvalue());
		List<Object> respconditions = resprocessing.getRespconditionOrItemprocExtension();
		for(Object o: respconditions) {
			if(o instanceof RespconditionType) {
				VarequalType var = (VarequalType) ((RespconditionType) o).getConditionvar().getNotOrAndOrOr().get(FIRST);
				System.out.println(var.getValue());
				System.out.println(((RespconditionType) o).getSetvar().get(FIRST).getValue());
			}
		}
		
	}
	
	/**
	 * @param <T>
	 * @param list
	 * @return
	 */
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