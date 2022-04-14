package cs499.qti;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.zeroturnaround.zip.ZipUtil;

import cs499.Quiz;
import cs499.qti.data_mapping.AssessmentType;
import cs499.qti.data_mapping.ConditionvarType;
import cs499.qti.data_mapping.DecvarType;
import cs499.qti.data_mapping.DisplayfeedbackType;
import cs499.qti.data_mapping.FlowMatType;
import cs499.qti.data_mapping.ItemType;
import cs499.qti.data_mapping.ItemfeedbackType;
import cs499.qti.data_mapping.ItemmetadataType;
import cs499.qti.data_mapping.MaterialType;
import cs499.qti.data_mapping.MattextType;
import cs499.qti.data_mapping.ObjectFactory;
import cs499.qti.data_mapping.PresentationType;
import cs499.qti.data_mapping.QtimetadataType;
import cs499.qti.data_mapping.QtimetadatafieldType;
import cs499.qti.data_mapping.QuestestinteropType;
import cs499.qti.data_mapping.RenderChoiceType;
import cs499.qti.data_mapping.RenderFibType;
import cs499.qti.data_mapping.RespconditionType;
import cs499.qti.data_mapping.ResponseLabelType;
import cs499.qti.data_mapping.ResponseLidType;
import cs499.qti.data_mapping.ResponseStrType;
import cs499.qti.data_mapping.ResprocessingType;
import cs499.qti.data_mapping.SectionType;
import cs499.qti.data_mapping.SetvarType;
import cs499.qti.data_mapping.VarequalType;
import cs499.qti.package_mapping.DependencyType;
import cs499.qti.package_mapping.FileType;
import cs499.qti.package_mapping.ManifestType;
import cs499.qti.package_mapping.MetadataType;
import cs499.qti.package_mapping.ResourceType;
import cs499.qti.package_mapping.ResourcesType;
import cs499.qti.package_mapping.imsmd.LangstringType;
import cs499.qti.package_mapping.imsmd.LomType;
import cs499.qti.package_mapping.imsmd.TitleType;
import cs499.question.MatchingQuestion;
import cs499.question.MultipleChoiceQuestion;
import cs499.question.Question;
import cs499.question.QuestionType;
import cs499.question.SingleAnswerQuestion;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Class that handles creating XML documents to the QTI schema and writing them to files.
 * @author elodick
 *
 */
public class CreateQTI {

	private static ArrayList<Integer> answerIdSource = new ArrayList<Integer>();

	private static final int FIRST = 0;

	private static final int RANGE_START = 10000;

	private static final int RANGE_END = 11000;

	/**
	 * Constructor seeds and shuffles the list of potential answer IDs
	 */
	public CreateQTI() {

		for(int i = RANGE_START; i < RANGE_END; i++) {
			answerIdSource.add(i);
		}
		Collections.shuffle(answerIdSource);
	}

	//TODO: quiz bank(?)
	//TODO: add section for question group?
	
	public void createPackage(ArrayList<Quiz> quizzes, String filepath) throws JAXBException {
		
		File packageFolder = new File(filepath);
        if (!packageFolder.exists()) {
            packageFolder.mkdir();
        }
		
		ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
		for(Quiz quiz: quizzes) {
			HashMap<String,String> map = new HashMap<String,String>();
			String quiz_id = createQuizXML(quiz, filepath);
			map.put("quiz_id", quiz_id);
			map.put("metadata_id", createMetadataXML(quiz, quiz_id, filepath));
			dataList.add(map);
		}
		createManifestXML(dataList, filepath);
		
		ZipUtil.pack(new File(filepath), new File(filepath + ".zip"));
		
		//clean up folder - leave only zip
		try {
			FileUtils.deleteDirectory(packageFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Method to create the XML for a single Quiz
	 * @param quiz, filepath
	 * @throws JAXBException
	 */
	public String createQuizXML(Quiz quiz, String filepath) throws JAXBException {

		JAXBContext jc = JAXBContext.newInstance("cs499.qti.data_mapping");
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		

		ObjectFactory factory = new ObjectFactory();
		QuestestinteropType qti = factory.createQuestestinteropType();

		AssessmentType assessment = factory.createAssessmentType(); 
		assessment.setTitle(quiz.getName());
		String assessmentId = generateIdString();
		
		assessment.setIdent(assessmentId);
		qti.setAssessment(assessment);
		
		String folderPath = filepath + "/" + assessmentId;

		List<Object> sections = assessment.getSectionrefOrSection();

		SectionType root = factory.createSectionType();
		root.setIdent("root_section");
		sections.add(root);

		List<Object> sectionList = root.getItemrefOrItemOrSectionref();

		for(Question q: quiz.getQuestions()) {

			ArrayList<HashMap<String,String>> answerList = processAnswers(q);

			ItemType item = factory.createItemType();

			item.setTitle(q.getName());
			sectionList.add(item);			

			item.setItemmetadata(createItemMetadata(q, factory, answerList));

			PresentationType presentation = factory.createPresentationType();
			item.setPresentation(presentation);
			buildPresentation(q, factory, presentation);

			List<Object> presentationList = presentation.getMaterialOrResponseLidOrResponseXy();

			if(q instanceof MatchingQuestion) {
				buildMatchingResponses(factory, answerList, presentationList);
			}
			else {
				switch(q.getType()) {
				case SHORT_ANSWER:
				case ESSAY:
					buildResponseStr(factory, answerList, presentationList);
					//TODO: investigate problem w/essay
					break;
				case MULTIPLE_ANSWERS:
				case MULTIPLE_BLANKS:
				case MULTIPLE_DROPDOWNS:
					buildRespLidMulti(factory, answerList, presentationList);
					break;
				case MULTIPLE_CHOICE:
				case TRUE_FALSE:
					buildRespLidSingle(factory, answerList, presentationList);
					break;
				case TEXT_ONLY:
				case FILE_UPLOAD:
				case MATCHING:
				case NUMERICAL:
				case CALCULATED:
				default:
					break;

				}
			}

			List<ResprocessingType> resultProcessing = item.getResprocessing();


			resultProcessing.add(createResprocessing(q, factory, answerList));
			if(q.getType() == QuestionType.ESSAY) {
				ItemfeedbackType feedback = createItemFeedback((SingleAnswerQuestion)q,factory);
				item.getItemfeedback().add(feedback);
				System.out.println("feedback created");
			}

		}
		File quizFolder = new File(folderPath);
	        if (!quizFolder.exists()) {
	            quizFolder.mkdir();
	        }
		marshaller.marshal(factory.createQuestestinterop(qti), new File(folderPath + "/" + assessmentId + ".xml"));
		return assessmentId;
		//needs to return id string for manifest
	}

	private void buildRespLidMulti(ObjectFactory factory, ArrayList<HashMap<String, String>> answerList,
			List<Object> presentationList) {

		//not single cardinality
		//need to account for multiple answers
		//each one needs a responselid just like with matching
		//only each responselid just needs one choice
		for(HashMap<String,String> map: answerList) {
			ResponseLidType responselid = factory.createResponseLidType();
			presentationList.add(responselid);

			responselid.setIdent("response_" + map.get("responselid_ident"));
			MaterialType responseMat = factory.createMaterialType();
			MattextType responseText = factory.createMattextType();
			responseText.setValue(map.get("responselid_ident"));
			responseMat.getMattextOrMatemtextOrMatimage().add(responseText);

			List<JAXBElement<?>> responselist = responselid.getContent();
			responselist.add(factory.createMaterial(responseMat));

			RenderChoiceType renderchoice = factory.createRenderChoiceType();
			responselist.add(factory.createRenderChoice(renderchoice));

			ResponseLabelType label = factory.createResponseLabelType();
			label.setIdent(map.get("response_ident"));

			MaterialType mat = factory.createMaterialType();
			MattextType text = factory.createMattextType();

			text.setTexttype("text/plain");
			text.setValue(map.get("response_text"));

			mat.getMattextOrMatemtextOrMatimage().add(text);
			label.getContent().add(factory.createMaterial(mat));

			renderchoice.getMaterialOrMaterialRefOrResponseLabel().add(label);
		}
	}

	private void buildRespLidSingle(ObjectFactory factory, ArrayList<HashMap<String, String>> answerList,
			List<Object> presentationList) {
		ResponseLidType responselid = factory.createResponseLidType();
		presentationList.add(responselid);
		responselid.setIdent("response1");
		responselid.setRcardinality("Single");

		List<JAXBElement<?>> renderlist = responselid.getContent();

		RenderChoiceType render = factory.createRenderChoiceType();
		renderlist.add(factory.createRenderChoice(render));
		for(HashMap<String,String> map: answerList) {

			ResponseLabelType label = factory.createResponseLabelType();
			label.setIdent(map.get("response_ident"));

			MaterialType mat = factory.createMaterialType();
			MattextType text = factory.createMattextType();

			text.setTexttype("text/plain");
			text.setValue(map.get("response_text"));

			mat.getMattextOrMatemtextOrMatimage().add(text);
			label.getContent().add(factory.createMaterial(mat));

			render.getMaterialOrMaterialRefOrResponseLabel().add(label);
		}
	}

	private void buildResponseStr(ObjectFactory factory, ArrayList<HashMap<String, String>> answerList,
			List<Object> presentationList) {
		ResponseStrType responsestr = factory.createResponseStrType();
		presentationList.add(responsestr);
		responsestr.setIdent("response1");
		responsestr.setRcardinality("Single");

		List<JAXBElement<?>> responselist = responsestr.getContent();

		RenderFibType renderfib = factory.createRenderFibType();
		responselist.add(factory.createRenderFib(renderfib));
		for(HashMap<String,String> map: answerList) {

			ResponseLabelType label = factory.createResponseLabelType();
			label.setIdent(map.get("response_ident"));

			renderfib.getMaterialOrMaterialRefOrResponseLabel().add(label);

		}
	}

	private void buildMatchingResponses(ObjectFactory factory, ArrayList<HashMap<String, String>> answerList,
			List<Object> presentationList) {
		ArrayList<ResponseLabelType> responses = new ArrayList<ResponseLabelType>();

		for(HashMap<String,String> map: answerList) {

			if(map.containsKey("matching_ident")) {
				ResponseLidType responselid = factory.createResponseLidType();
				presentationList.add(responselid);
				List<JAXBElement<?>> contentlist = responselid.getContent();
				responselid.setIdent(map.get("responselid_ident"));

				MaterialType mat = factory.createMaterialType();
				MattextType text = factory.createMattextType();
				text.setTexttype("text/plain");
				text.setValue(map.get("response_text"));

				mat.getMattextOrMatemtextOrMatimage().add(text);
				contentlist.add(factory.createMaterial(mat));

				RenderChoiceType render = factory.createRenderChoiceType();
				contentlist.add(factory.createRenderChoice(render));

			}
			else {
				ResponseLabelType label = factory.createResponseLabelType();
				
				label.setIdent(map.get("response_ident"));

				MaterialType mat = factory.createMaterialType();
				MattextType text = factory.createMattextType();

				text.setValue(map.get("response_text"));

				mat.getMattextOrMatemtextOrMatimage().add(text);
				label.getContent().add(factory.createMaterial(mat));

				responses.add(label);

			}
		}

		for(Object o: presentationList) {
			if(o instanceof ResponseLidType) {
				for(ResponseLabelType r: responses) {
					((ResponseLidType) o).getContent().add(factory.createResponseLabelType(r));
				}
			}
		}
	}


	/**
	 * Creates the metadata elements for an item
	 * @param question
	 * @param factory
	 * @param answerList
	 * @return
	 */
	private ItemmetadataType createItemMetadata(Question question, ObjectFactory factory, ArrayList<HashMap<String, String>> answerList) {

		ItemmetadataType meta = factory.createItemmetadataType();

		List<QtimetadataType> qtimeta = meta.getQtimetadata();
		qtimeta.add(factory.createQtimetadataType());

		List<QtimetadatafieldType> fieldList = qtimeta.get(FIRST).getQtimetadatafield();

		QtimetadatafieldType typeField = factory.createQtimetadatafieldType();
		typeField.setFieldlabel("question_type");
		typeField.setFieldentry(question.getType().getType());
		fieldList.add(typeField);

		QtimetadatafieldType pointsField = factory.createQtimetadatafieldType();
		pointsField.setFieldlabel("points_possible");
		pointsField.setFieldentry(Float.toString(question.getPoints()));
		fieldList.add(pointsField);

		String answerIds = "";
		for(HashMap<?,?> h: answerList) {
			if(!answerIds.isEmpty()) {
				answerIds = answerIds + ",";
			}
			answerIds = answerIds + h.get("response_ident");
		}

		QtimetadatafieldType answerIdsField = factory.createQtimetadatafieldType();
		answerIdsField.setFieldlabel("original_answer_ids");
		answerIdsField.setFieldentry(answerIds);
		fieldList.add(answerIdsField);		

		return meta;

	}

	/**
	 * Creates the presentation elements for an item
	 * @param question
	 * @param factory
	 * @param presentation
	 * @return
	 */
	private PresentationType buildPresentation(Question question, ObjectFactory factory, PresentationType presentation) {

		List<Object> presList = presentation.getMaterialOrResponseLidOrResponseXy();

		MaterialType questionText = factory.createMaterialType();
		presList.add(questionText);

		List<Object> mat = questionText.getMattextOrMatemtextOrMatimage();

		MattextType text = factory.createMattextType();
		text.setTexttype("text/html");
		text.setValue(question.getDescription());

		mat.add(text);

		return presentation;
	}

	/**
	 * Creates the resprocessing elements for an item
	 * @param q
	 * @param factory
	 * @param answerList
	 * @return
	 */
	private ResprocessingType createResprocessing(Question q, ObjectFactory factory, ArrayList<HashMap<String, String>> answerList) {

		ResprocessingType resultProcessing = factory.createResprocessingType();
		resultProcessing.setOutcomes(factory.createOutcomesType());
		DecvarType dec = factory.createDecvarType();
		dec.setVartype("Decimal");
		dec.setVarname("SCORE");
		dec.setMinvalue("0");
		dec.setMaxvalue("100");
		resultProcessing.getOutcomes().getDecvarAndInterpretvar().add(dec);

		List<Object> respList = resultProcessing.getRespconditionOrItemprocExtension();
		
		if(q instanceof MultipleChoiceQuestion) {
			RespconditionType respcondition = factory.createRespconditionType();
			respList.add(respcondition);
			for(HashMap<String,String> map: answerList) {
				if(map.get("correct").equals("true")) {
					VarequalType varequal = factory.createVarequalType();
					varequal.setRespident("response1");
					varequal.setValue(map.get("response_ident"));
					ConditionvarType cond = factory.createConditionvarType();
					cond.getNotOrAndOrOr().add(varequal);
					respcondition.setConditionvar(cond);
					SetvarType setvar = factory.createSetvarType();
					setvar.setAction("Set");
					setvar.setVarname("SCORE");
					setvar.setValue("100");
					respcondition.getSetvar().add(setvar);
				}
				}
				
		}
		else if (q instanceof MatchingQuestion) {
			Float pointsPerAnswer = (float) (100.0 / (answerList.size() / 2));
			for(HashMap<String,String> map: answerList) {
				if(map.containsKey("matching_ident")) {
					RespconditionType respcondition = factory.createRespconditionType();
					respList.add(respcondition);

					VarequalType varequal = factory.createVarequalType();
					varequal.setRespident(map.get("responselid_ident"));
					varequal.setValue(map.get("matching_ident"));

					ConditionvarType cond = factory.createConditionvarType();
					cond.getNotOrAndOrOr().add(varequal);
					respcondition.setConditionvar(cond);

					SetvarType setvar = factory.createSetvarType();
					setvar.setAction("Set");
					setvar.setVarname("SCORE");
					setvar.setValue(pointsPerAnswer.toString());

					respcondition.getSetvar().add(setvar);
				}
			}
		}
		else if(q.getType() == QuestionType.ESSAY) {
			createRespFeedback(resultProcessing, answerList, factory);
		}
		else {
			Float pointsPerAnswer = (float) (100.0 / answerList.size());
			for(HashMap<String,String> map: answerList) {
				System.out.println(map);
				RespconditionType respcondition = factory.createRespconditionType();
				respList.add(respcondition);

				VarequalType varequal = factory.createVarequalType();
				varequal.setRespident("response_" + map.get("responselid_ident"));
				varequal.setValue(map.get("response_ident"));

				ConditionvarType cond = factory.createConditionvarType();
				cond.getNotOrAndOrOr().add(varequal);
				respcondition.setConditionvar(cond);

				SetvarType setvar = factory.createSetvarType();
				setvar.setAction("Set");
				setvar.setVarname("SCORE");
				setvar.setValue(pointsPerAnswer.toString());

				respcondition.getSetvar().add(setvar);
				
				//for essay:
				//don't need points per answer
				//don't need set score

			}
		}

		return resultProcessing;
	}
	
	private void createRespFeedback(ResprocessingType resprocessing, ArrayList<HashMap<String,String>> answerList, ObjectFactory factory) {
		
		
		RespconditionType respcondition1 = factory.createRespconditionType();
		respcondition1.setContinue("Yes");
		
		ConditionvarType condition = factory.createConditionvarType();

		respcondition1.setConditionvar(condition);
		
		DisplayfeedbackType feedback = factory.createDisplayfeedbackType();
		feedback.setFeedbacktype("Response");
		feedback.setLinkrefid("general_fb");
		
		respcondition1.getDisplayfeedback().add(feedback);
		
		RespconditionType respcondition2 = factory.createRespconditionType();
		respcondition2.setContinue("No");
		
		ConditionvarType condition2 = factory.createConditionvarType();
		
		respcondition2.setConditionvar(condition2);
		
		resprocessing.getRespconditionOrItemprocExtension().add(respcondition1);
		resprocessing.getRespconditionOrItemprocExtension().add(respcondition2);
		
	}
	
	private ItemfeedbackType createItemFeedback(SingleAnswerQuestion question, ObjectFactory factory) {
		ItemfeedbackType feedback = factory.createItemfeedbackType();
		feedback.setIdent("general_fb");
		
		FlowMatType flow = factory.createFlowMatType();
		
		MaterialType mat = factory.createMaterialType();
		MattextType text = factory.createMattextType();
		text.setTexttype("text/html");
		text.setValue(question.getAnswers().get(FIRST)); 
		
		mat.getMattextOrMatemtextOrMatimage().add(text);
		flow.getFlowMatOrMaterialOrMaterialRef().add(mat);
		feedback.getFlowMatOrMaterialOrSolution().add(flow);
		
		return feedback;
	}

	/**
	 * Processes answer data and generates unique response ids
	 * @param question
	 * @return
	 */
	private ArrayList<HashMap<String,String>> processAnswers(Question question){

		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

		if(question instanceof MultipleChoiceQuestion) {

			for(String s: ((MultipleChoiceQuestion)question).getChoices()) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("response_text", s);
				map.put("response_ident", answerIdSource.get(FIRST).toString());
				if(s.equals(((MultipleChoiceQuestion)question).getAnswer())) {
					map.put("correct", "true");
				}
				else {
					map.put("correct", "false");
				}

				answerIdSource.remove(FIRST);
				list.add(map);
			}
		}
		else if(question instanceof SingleAnswerQuestion) {
			for(String s: ((SingleAnswerQuestion)question).getAnswers()) {
				int i = 1;
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("response_text", s);
				map.put("response_ident", answerIdSource.get(FIRST).toString());
				map.put("responselid_ident", "ans" + i);
				answerIdSource.remove(FIRST);
				list.add(map);
				i++;
			}
		}
		else if(question instanceof MatchingQuestion) {

			HashMap<String,String> values = ((MatchingQuestion)question).getRight();

			for(String s: ((MatchingQuestion)question).getLeft()) {
				HashMap<String,String> keymap = new HashMap<String,String>();
				keymap.put("response_text", s);
				keymap.put("responselid_ident", "response_" + answerIdSource.get(FIRST).toString());
				keymap.put("response_ident", answerIdSource.get(FIRST).toString());
				answerIdSource.remove(FIRST);

				HashMap<String,String> valuemap = new HashMap<String,String>();
				valuemap.put("response_text", values.get(s));
				valuemap.put("response_ident", answerIdSource.get(FIRST).toString());

				keymap.put("matching_ident", answerIdSource.get(FIRST).toString());

				answerIdSource.remove(FIRST);

				list.add(keymap);
				list.add(valuemap);
			}
		}

		return list;

	}

	public void createManifestXML(ArrayList<HashMap<String,String>> quizIds, String filepath) throws JAXBException {
		
		JAXBContext jc = JAXBContext.newInstance("cs499.qti.package_mapping:cs499.qti.package_mapping.imsmd");
		Marshaller marshaller = jc.createMarshaller();
		
		cs499.qti.package_mapping.ObjectFactory factory = new cs499.qti.package_mapping.ObjectFactory();
		cs499.qti.package_mapping.imsmd.ObjectFactory metafactory = new cs499.qti.package_mapping.imsmd.ObjectFactory();
		
		ManifestType manifest = factory.createManifestType();
		
		MetadataType metadata = factory.createMetadataType();
		metadata.setSchema("IMS Content");
		metadata.setSchemaversion("1.1.3");
		
		LomType lom = metafactory.createLomType();
		lom.setGeneral(metafactory.createGeneralType());
		
		TitleType title = metafactory.createTitleType();
		
		LangstringType text = metafactory.createLangstringType();
		text.setValue("QTI Quiz Export");
		title.getLangstring().add(text);
		
		lom.getGeneral().getContent().add(title);
		
		ResourcesType resources = factory.createResourcesType();
		manifest.setResources(resources);
		
		for(HashMap<String,String> data: quizIds) {
			ResourceType resource = factory.createResourceType();
			resource.setIdentifier(data.get("quiz_id"));
			resource.setType("imsqti_xmlv1p2");
			
			FileType file = factory.createFileType();
			file.setHref(data.get("quiz_id") + "/" + data.get("quiz_id") + ".xml");
			resource.getFile().add(file);
			
			DependencyType dependency = factory.createDependencyType();
			dependency.setIdentifierref(data.get("metadata_id"));
			resource.getDependency().add(dependency);
			
			ResourceType meta = factory.createResourceType();
			meta.setIdentifier(data.get("metadata_id"));
			meta.setType("associatedcontent/imscc_xmlv1p1/learning-application-resource");
			meta.setHref(data.get("quiz_id") + "/" + "assessment_meta.xml");
			
			FileType metafile = factory.createFileType();
			metafile.setHref(data.get("quiz_id") + "/" + "assessment_meta.xml");
			meta.getFile().add(metafile);
			
			resources.getResource().add(resource);
			resources.getResource().add(meta);
		}
		
		marshaller.marshal(factory.createManifest(manifest), new File(filepath + "/imsmanifest.xml"));


	}

	public String createMetadataXML(Quiz quiz, String quizId, String filepath) throws JAXBException {
		
		JAXBContext jc = JAXBContext.newInstance("cs499.qti.metadata_mapping");
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		String folderPath = filepath + "//" + quizId;
		
		cs499.qti.metadata_mapping.ObjectFactory factory = new cs499.qti.metadata_mapping.ObjectFactory();
		
		cs499.qti.metadata_mapping.Quiz docquiz =  factory.createQuiz();
		docquiz.setIdentifier(quizId);
		docquiz.setTitle(quiz.getName());
		docquiz.setDescription(quiz.getDescription());
		docquiz.setPointsPossible(Float.toString(quiz.getPointsPossible()));
		
		File quizFolder = new File(folderPath);
        if (!quizFolder.exists()) {
            quizFolder.mkdir();
        }
        marshaller.marshal(docquiz, new File(folderPath + "//" + "assessment_meta.xml"));

		
		return generateIdString();

	}

	private String generateIdString() {
		String uuid = UUID.randomUUID().toString();
		String fixed = uuid.replace("-", "");
		return fixed;
	}
}
