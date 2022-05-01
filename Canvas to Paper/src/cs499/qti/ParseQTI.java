package cs499.qti;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;

import org.w3c.dom.Element;

import cs499.qti.data_mapping.*;
import cs499.qti.data_mapping.ItemType;
import cs499.qti.metadata_mapping.*;
import cs499.qti.package_mapping.*;
import cs499.qti.package_mapping.ResourceType;
import cs499.qti.package_mapping.imsmd.*;

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

    private static final String QUIZ_DIRECTORY = "//non_cc_assessments";

    private static final String QTI_PATH = "qti";

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
            destDir.mkdirs();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));

        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
            	try {
                extractFile(zipIn, filePath);
            	} catch(IOException e) {
            		
            	}
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
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
     * Loops through files in directory and performs parsing as appropriate
     * @param filePath
     * @return
     * @throws JAXBException 
     */
    public void xmlLoop(String filePath) throws JAXBException
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
        			if(fileName.equals("imsmanifest.xml")) {
        				  			  
        				ParseUtils.fixManifest(child.getPath());
        				parseManifest(child.getPath());
        			}
        			else if(fileName.equals("assessment_meta.xml")) {
        				parseMeta(child.getPath());
        			}
        			else { 
      					xmlParse(child.getPath());        				  
        			}       			  
				}
    		}
    	}
    }
    
    public void importQTI(String filepath) throws JAXBException {
    	try {
			unzip(filepath, QTI_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	xmlLoop(QTI_PATH);
    	try {
			ParseUtils.deleteDirectory(QTI_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void importIMSCC(String filepath) throws JAXBException {
    	try {
			unzip(filepath, QTI_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	imsccLoop(QTI_PATH);
    }
    
    public void imsccLoop(String filepath) throws JAXBException {
    	
    	String quizDirPath = filepath + QUIZ_DIRECTORY;
    	
    	File quizDir = new File(quizDirPath);
    	
    	for(File qti: quizDir.listFiles()) {
    		xmlParse(qti.getPath());
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
    	
    	File xmlfile = new File(filepath);
    	
    	Unmarshaller unmarshaller = jc.createUnmarshaller();
    	
		JAXBElement<QuestestinteropType> root = (JAXBElement<QuestestinteropType>) unmarshaller.unmarshal(xmlfile);
    	
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

    			Element test = (Element) ParseUtils.removeNull(selection.getSelectionExtension().getContent()).get(FIRST);
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
    		if(label.equals("bank_title")) {
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
			if(label.equals("question_type")) {
				questionValues.put("question_type", ((QtimetadatafieldType) f).getFieldentry());
			}
			else if (label.equals("points_possible")) {
				questionValues.put("points_possible", ((QtimetadatafieldType) f).getFieldentry());
			}			
		}
    	ArrayList<HashMap<String,String>> responseChoicesList = new ArrayList<HashMap<String,String>>();
    	//list that will contain all possible responses
		for(Object o : item.getPresentation().getMaterialOrResponseLidOrResponseXy()) {
			if(o instanceof MaterialType) {
				questionValues.put("description", parseMaterial((MaterialType) o));
			}
			else if (o instanceof ResponseLidType) {
				responseChoicesList.addAll(parseResponseLid((ResponseLidType) o));
				//returns arraylist of hashmaps with matching_ident, answer_name, answer_ident, and answer_value
				//adds all of them to existing list
				
			}
			else if (o instanceof ResponseStrType) {
				HashMap<String,String> responseInfo = new HashMap<String,String>();
				responseInfo.put("answer_ident",((ResponseStrType) o).getIdent());
				responseChoicesList.add(responseInfo);
				//returns single hashmap with answer_ident and answer_value
				//adds to existing list
			}
		}
		
		ArrayList<HashMap<String,String>> correctResultsList = new ArrayList<HashMap<String,String>>();
		//list that will contain all correct responses
		for(Object r: item.getResprocessing()) {
			correctResultsList.addAll(parseResprocessing((ResprocessingType) r));
			//returns arraylist of hashmaps with response_ident and answer_ident
		}			
		for(Object f: item.getItemfeedback()) {
			//returns single hashmap with answer_ident and answer_value
			correctResultsList.add(parseItemFeedback((ItemfeedbackType) f));
		}
		String answers = ParseUtils.parseAnswers(correctResultsList, responseChoicesList, questionValues);
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
	private ArrayList<HashMap<String,String>> parseResponseLid(ResponseLidType responseLid) {
    	
    	ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();    	
    	
    	List<JAXBElement<?>> list = ParseUtils.removeNull(responseLid.getContent());    	
    	
		for(JAXBElement<?> e: list) {
								
			if(e.getValue() instanceof MaterialType) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("matching_ident", responseLid.getIdent());
				map.put("answer_name",parseMaterial((MaterialType) e.getValue()));
				data.add(map);
				
			}
			else if (e.getValue() instanceof RenderChoiceType) {
				RenderChoiceType render = (RenderChoiceType) e.getValue();
				for(Object r: render.getMaterialOrMaterialRefOrResponseLabel()) {
					if (r instanceof ResponseLabelType) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("answer_ident", ((ResponseLabelType) r).getIdent());					 
						List<Serializable> label = ParseUtils.removeNull(((ResponseLabelType) r).getContent());
						for(Object t: label) {
							if(((JAXBElement)t).getValue() instanceof MaterialType) {
								map.put("answer_value", parseMaterial((MaterialType) ((JAXBElement)t).getValue())); 
							}
						}
						data.add(map);
					}
				}				
			}
		}
		return ParseUtils.removeDuplicates(data);
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
		return ParseUtils.fixText(text);
	}
	
	/**
	 * Parses the resprocessing section of the qti document into a list of maps
	 * @param resprocessing
	 * @return
	 */
	private ArrayList<HashMap<String,String>> parseResprocessing(ResprocessingType resprocessing) {
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		List<Object> respconditions = resprocessing.getRespconditionOrItemprocExtension();
		for(Object o: respconditions) {
			if(o instanceof RespconditionType) {
				HashMap<String, String> data = new HashMap<String, String>();

				List<Object> varlist = ((RespconditionType) o).getConditionvar().getNotOrAndOrOr();
				for(Object var: varlist) {
					if(var instanceof VarequalType) {
						data.put("response_ident", ((VarequalType)var).getRespident()); //response ident - matches response_lid ident
						data.put("answer_ident", ((VarequalType)var).getValue()); //answer ident - matches response_label ident
						list.add(data);	
					}
					else if(var instanceof OrType) {
						for(Object or:((OrType) var).getNotOrAndOrOr()) {
							if(or instanceof VarequalType) {
								data.put("answer_ident", ((VarequalType)or).getRespident());
								data.put("answer_value", ((VarequalType)or).getValue());
							}
							else if(or instanceof AndType) {
								for(Object and: ((AndType)or).getNotOrAndOrOr()) {
									if(and instanceof VargteType) {
										data.put("floor", ((VargteType) and).getValue());
									}
									else if(and instanceof VarlteType) {
										data.put("ceiling", ((VarlteType) and).getValue());
									}
								}
							}
							list.add(data);
						}
					}
					else {
						List<DisplayfeedbackType> disp = ((RespconditionType) o).getDisplayfeedback();
						for(DisplayfeedbackType d: disp) {
							data.put("answer_ident", d.getLinkrefid());
							list.add(data);
						}
					}
				}
			}
		}
		return list;
	}
	
	public void parseMeta(String filepath) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("cs499.qti.metadata_mapping");
		
		File xmlFile = new File(filepath);
		
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		
		Quiz quiz = (Quiz) unmarshaller.unmarshal(xmlFile);
		
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("name", quiz.getTitle());
		data.put("description",ParseUtils.fixText(quiz.getDescription()));
		data.put("qti_id",quiz.getIdentifier());
		data.put("points_possible",quiz.getPointsPossible());
		data.put("due_date",quiz.getDueAt());

		QtiToDB.storeQuizMeta(data);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int parseManifest(String filepath) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("cs499.qti.package_mapping:cs499.qti.package_mapping.imsmd");
		
		File xmlFile = new File(filepath);
		
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		
		JAXBElement<ManifestType> root = (JAXBElement<ManifestType>) unmarshaller.unmarshal(xmlFile);
		
		ManifestType manifest = root.getValue();
		
		MetadataType metadata = manifest.getMetadata();
		
		List<Object> elements = metadata.getAny();
		JAXBElement element = (JAXBElement) elements.get(FIRST);
		LomType lom = (LomType) element.getValue();
		
		JAXBElement el = (JAXBElement) ParseUtils.removeNull(lom.getGeneral().getContent()).get(FIRST);
		TitleType title = (TitleType) el.getValue();
		String course = StringUtils.substringBetween(title.getLangstring().get(FIRST).getValue(),"\"","\"");
		
		Integer courseId = QtiToDB.storeCourse(course);
		
		ArrayList<String> courseQuizzes = new ArrayList<String>();
		for(Object o: manifest.getResources().getResource()) {
			ResourceType resource = (ResourceType) o;
			if(resource.getType().contains("imsqti_xmlv1p2")) {
				courseQuizzes.add(resource.getIdentifier());
			}
		}
		
		QtiToDB.associateCourse(courseId, courseQuizzes);
		return courseId;	
	}
}