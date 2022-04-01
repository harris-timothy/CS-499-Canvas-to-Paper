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
import cs499.qti.QtiToDB.*;
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
    public void xmlLoop(String filePath)
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
    }
        
    /**
     * Parses a document that uses the QTI schema
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
     * Parses a quiz (assessment) from the document
     * @param assessment
     */
    private void parseAssessment(AssessmentType assessment) {
    	
    	//store quiz has to go in this function somewhere, so we need all the quiz info to populate to here
    	// arraylist questionIds stores ids to add to quiz_questions table
    	ArrayList<Integer> questionIds = new ArrayList<Integer>();
    	HashMap<String, String> data = new HashMap<String, String>();
    	
    	data.put("quiz_title", assessment.getTitle());
    	data.put("quiz_qti_id", assessment.getIdent());
    	Integer quizId = QtiToDB.storeQuiz(data);

    	SectionType rootSection = (SectionType) assessment.getSectionrefOrSection().get(FIRST);
    	for(Object a: rootSection.getItemrefOrItemOrSectionref()) {
    		if (a instanceof SectionType) {
    			HashMap<String,String> group = new HashMap<String,String>();
    			//this is where all of the quiz to bank information comes from
    			group.put("quiz_id", quizId.toString());
    			group.put("group_name", ((SectionType)a).getTitle()); // question group title
    			SelectionType selection = ((SectionType)a).getSelectionOrdering().getSelection().get(FIRST);
    			group.put("bank_qti_id", selection.getSourcebankRef());
    			group.put("pick_count", selection.getSelectionNumber());

    			Element test = (Element) removeNull(selection.getSelectionExtension().getContent()).get(FIRST);
    			group.put("points_per_item",test.getFirstChild().getTextContent()); // points per item
    			
    			//data from here goes into question group table
    			QtiToDB.storeQuestionGroup(group);
    		}
    		else if (a instanceof ItemType) {
    			ItemType item = (ItemType) a;
    			questionIds.add(parseItem(item));
    		}
    	}
    	for(Integer i: questionIds) {
    		QtiToDB.storeQuizQuestion(quizId, i);
    	}
    }
    
    /**
     * Parses a question bank from the document
     * @param bank
     */
    private void parseBank(ObjectbankType bank) {
    	//store bank has to happen in this function somewhere, so all bank info has to populate up
    	//Arraylist of question ids to insert into bank questions table
    	
    	ArrayList<Integer> questionIds = new ArrayList<Integer>();
    	HashMap<String, String> values = new HashMap<String, String>();
    	
    	values.put("qti_id", bank.getIdent()); //bank qti_id
    	List<QtimetadatafieldType> fields = bank.getQtimetadata().get(FIRST).getQtimetadatafield();
    	for(Object f: fields) {
    		String label = ((QtimetadatafieldType) f).getFieldlabel();
    		if(label == "bank_title") {
    			values.put("bank_title", ((QtimetadatafieldType) f).getFieldentry());
    		}	
		}
    	int bankId = QtiToDB.storeQuestionBank(values);
    	for(Object s: bank.getSectionOrItem()) {
    		if(s instanceof ItemType) {
    			questionIds.add(parseItem((ItemType) s));
    		}
    	}
    	for(Integer i: questionIds) {
    		QtiToDB.storeBankQuestion(bankId, i);
    	}    	
    }
    
    
    /**
     * Parses the item section of the document and returns the question id as an integer
     * @param item
     * @return
     */
    private int parseItem(ItemType item) {
    	//store question has to happen in here somewhere
    	//return question id after storing question to associate with quiz or bank
    	//insert ids into quiz_to_question or bank_questions as applicable
    	int questionId = 0;
    	
    	HashMap<String, String> questionValues = new HashMap<String, String>();
    	questionValues.put("name", item.getTitle());
    	questionValues.put("qti_id", item.getIdent());    	
    	
    	List<QtimetadatafieldType> fields = item.getItemmetadata().getQtimetadata().get(FIRST).getQtimetadatafield();
    	for(Object f: fields) {
			String label = ((QtimetadatafieldType) f).getFieldlabel();
			if(label == "question_type") {
				questionValues.put("question_type", ((QtimetadatafieldType) f).getFieldentry());
			}
			else if (label == "points_possible") {
				questionValues.put("points_possible", ((QtimetadatafieldType) f).getFieldentry());
			}			
		}
    	ArrayList<Object> responseList = new ArrayList<Object>();
		for(Object o : item.getPresentation().getMaterialOrResponseLidOrResponseXy()) {
			if(o instanceof MaterialType) {
				questionValues.put("description", parseMaterial((MaterialType) o));
			}
			else if (o instanceof ResponseLidType) {
				responseList.addAll(parseResponseLid((ResponseLidType) o));
				//returns arraylist of hashmaps
				//response ident
				//choices
			}
			else if (o instanceof ResponseStrType) {
				HashMap<String,String> responseInfo = new HashMap<String,String>();
				responseInfo.put("answer_ident",((ResponseStrType) o).getIdent());
				responseList.add(responseInfo);
				//returns ident for correct answer
			}
		}
		
		ArrayList<Object> correctAnswers = new ArrayList<Object>();
		for(Object r: item.getResprocessing()) {
			correctAnswers.addAll(parseResprocessing((ResprocessingType) r));
			//returns arraylist of hashmaps
			//response ident to match responselist
			//response value to match choice value of correct answer
		}		
		for(Object f: item.getItemfeedback()) {
			HashMap<String, String> feedback;
			((ItemfeedbackType) f).getIdent(); //itemfeedback ident
			//if conditionvar other
			//displayfeedback linkrefid = ident
			feedback = parseItemFeedback((ItemfeedbackType) f);
			correctAnswers.add(feedback);
		}
		
		String answers = QtiToDB.parseAnswers(correctAnswers, responseList, questionValues);
		questionValues.put("answers", answers);
		questionId = QtiToDB.storeQuestion(questionValues);
		//use hashmap to store question
		//get id from stored question
		return questionId;
		
    	
    }
    
    /**
     * Parses the itemfeedback section into a single map
     * @param feedback
     * @return
     */
    private HashMap<String, String> parseItemFeedback(ItemfeedbackType feedback) {
    	
    	HashMap<String, String> answer = new HashMap<String, String>();
    	answer.put("answer_ident", feedback.getIdent());
    	for(Object o: feedback.getFlowMatOrMaterialOrSolution()) {
    		if(o instanceof FlowMatType) {
    			for(Object k: ((FlowMatType) o).getFlowMatOrMaterialOrMaterialRef()) {
    				if(k instanceof MaterialType) {
    					answer.put("answer_value",parseMaterial((MaterialType) k));
    				}
    			}    			   			
    		}
    	}
    	return answer;
    }
    
    /**
     * Parses the responselid section of the document into a list of maps
     * @param responseLid
     * @return
     */
    @SuppressWarnings("rawtypes")
	private ArrayList<Object> parseResponseLid(ResponseLidType responseLid) {
    	
    	ArrayList<Object> data = new ArrayList<Object>();
    	
    	responseLid.getIdent();
    	
    	List<JAXBElement<?>> list = removeNull(responseLid.getContent());    	
    	
		for(JAXBElement<?> e: list) {
			HashMap<String, String> map = new HashMap<String, String>();
								
			if(e.getValue() instanceof MaterialType) {
				
				map.put("answer_name",parseMaterial((MaterialType) e.getValue()));
				
			}
			else if (e.getValue() instanceof RenderChoiceType) {
				HashMap<String,String> choices = new HashMap<String,String>();
				RenderChoiceType render = (RenderChoiceType) e.getValue();
				for(Object val: render.getMaterialOrMaterialRefOrResponseLabel()) {
					if (val instanceof ResponseLabelType) {
						
						choices.put("answer_ident", ((ResponseLabelType) val).getIdent());
					 
						List<Serializable> label = removeNull(((ResponseLabelType) val).getContent());
						for(Object t: label) {
							if(((JAXBElement)t).getValue() instanceof MaterialType) {
								choices.put("answer_value", parseMaterial((MaterialType) ((JAXBElement)t).getValue())); 
							}
						}								
					}
				}
				
			}
			data.add(map);
		}
		return data;
    	
    }
	
	/**
	 * Parses a material entry into a string, and removes any html formatting
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
	 * Parses the resprocessing section of the qti document into a list of maps
	 * @param resprocessing
	 * @return
	 */
	private ArrayList<Object> parseResprocessing(ResprocessingType resprocessing) {
		ArrayList<Object> list = new ArrayList<Object>();
		List<Object> respconditions = resprocessing.getRespconditionOrItemprocExtension();
		for(Object o: respconditions) {
			if(o instanceof RespconditionType) {
				HashMap<String, String> data = new HashMap<String, String>();
				Object var = ((RespconditionType) o).getConditionvar().getNotOrAndOrOr().get(FIRST);
				if(var instanceof VarequalType) {
					data.put("answer_ident", ((VarequalType)var).getRespident()); //answer ident - matches response_lid ident
					data.put("answer_value", ((VarequalType)var).getValue()); //answer value - matches response_label ident
					list.add(data);	
				}
				else {
					data.put("answer_ident",((RespconditionType) o).getDisplayfeedback().get(FIRST).getLinkrefid());
					list.add(data);
				}				
			}
		}
		return list;
	}
	
	
	/**
	 * Helper method to remove null or whitespace only entries from lists
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