package cs499.qti;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cs499.question.AnswerFormatter;
import cs499.question.QuestionType;

public class ParseUtils {
	
	private static final int FIRST = 0;
	private static final String REFERENCE_DIRECTORY = "D:\\black\\Documents\\GitHub\\CS-499-Canvas-to-Paper\\Canvas to Paper\\references";


	public static ArrayList<String> getReferenceTitles(String text){
		ArrayList<String> array = new ArrayList<String>();
		
		String pt1 = text.replace("&lt;", "<");
		String pt2 = pt1.replace("&gt;", ">");
		String pt3 = pt2.replace("&amp;", "&");
		String pt4 = pt3.replace("&nbsp;", " ");
		
		for(Element e: Jsoup.parse(pt4).getElementsByClass("instructure_file_link")) {
			array.add(e.attr("title"));
		}
		
		return array;
	}
	
	public static String fixText(String text) {
		String pt1 = text.replace("&lt;", "<");
		String pt2 = pt1.replace("&gt;", ">");
		String pt3 = pt2.replace("&amp;", "&");
		String pt4 = pt3.replace("&nbsp;", " ");
		return Jsoup.parse(pt4).wholeText();
	}
	
	/**
	 * Helper method to remove duplicate items from an arraylist
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list){
		Set<T> set = new LinkedHashSet<>();
		set.addAll(list);
		list.clear();
		list.addAll(set);		
		return list;
	}
	
	/**
	 * Helper method to remove null or whitespace only entries from lists
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> List<T> removeNull(List<T> list) {
		
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
	
	/**
	 * Helper method to fix namespace and schema validation errors in Canvas provided manifest files
	 * @param file
	 */
	public static void fixManifest(String filepath) {
		try {
			File file = new File(filepath);
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = content.replaceAll("imsccv1p1//imscp_v1p1","imscp_v1p1");
			content = content.replaceAll("imsmd:string", "imsmd:langstring");
			content = content.replaceAll("lomimscc:string", "lomimscc:langstring");
			FileUtils.writeStringToFile(file, content, "UTF-8");
			
		}catch (IOException e) {
			
		}
		
	}
	
	public static List<String> findReference(String directory, String filename) {
		List<String> fileResults = new ArrayList<String>();
		String filterName = FilenameUtils.removeExtension(filename);
		
		try(Stream<Path> walk = Files.walk(Paths.get(directory))){
			
			fileResults.addAll(filteredResult(directory,filterName));
			
			List<String> folders = walk
					.filter(Files::isDirectory)
					.map(x->x.toString())
					.collect(Collectors.toList());
			
			
			
			for(String f: folders) {
				fileResults.addAll(filteredResult(f,filterName));
			}
						
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return removeNull(fileResults);
		
	}
	
	private static List<String> filteredResult(String directory, String filename){
		try(Stream<Path> walk = Files.walk(Paths.get(directory))){

			List<String> files = walk
					.map(x->x.toString())
					.filter(f->f.contains(filename))
					.collect(Collectors.toList());
			return files;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
public static String parseAnswers(ArrayList<HashMap<String,String>> correctResultsList, ArrayList<HashMap<String,String>> responseChoicesList, HashMap<String, String> questionInfo) {
		
		QuestionType type = QuestionType.valueOfType(questionInfo.get("question_type"));
		
		switch(type) {
		case MATCHING:
		case MULTIPLE_ANSWERS:
		case MULTIPLE_DROPDOWNS:
			return findMatches(responseChoicesList, correctResultsList);
		case MULTIPLE_CHOICE:
		case TRUE_FALSE:
			String correct = findCorrect(correctResultsList.get(FIRST), responseChoicesList);
			ArrayList<String> choices = findChoices(responseChoicesList, correct);
			return AnswerFormatter.answerJSONString(correct, choices);			
		case CALCULATED:
		case NUMERICAL:
			for(HashMap<String,String> num: correctResultsList) {
				if(num.containsKey("floor")) {
					return AnswerFormatter.answerJSONString(num);
				}
			}
			break;			
		case MULTIPLE_BLANKS:				
			ArrayList<String> answers = new ArrayList<String>();
			for(HashMap<String,String> i: correctResultsList) {
				answers.add(findCorrect(i, responseChoicesList));
			}
			return AnswerFormatter.answerJSONString(answers);
		case ESSAY:
			ArrayList<String> essay = new ArrayList<String>();
			for(HashMap<String,String> e: correctResultsList) {
				if(e.containsKey("answer_value")) {
					essay.add(e.get("answer_value"));
				}
			}
			return AnswerFormatter.answerJSONString(essay);
			
		case SHORT_ANSWER:
			ArrayList<String> shortAnswer = new ArrayList<String>();
			for(HashMap<String,String> sa: correctResultsList) {
				shortAnswer.add(sa.get("answer_ident"));
			}
			return AnswerFormatter.answerJSONString(shortAnswer);
		case TEXT_ONLY:
		case FILE_UPLOAD:
			// no answers for these		
		default:
			break;		
		}		
		return null;
	}

	public static String findNumRange(HashMap<String,String> correct) {
		
		String range = "Greater than " + correct.get("floor") + ", less than " + correct.get("ceiling");
		
		return range;
	}
	
	
	private static String findCorrect(HashMap<String,String> correct, ArrayList<HashMap<String, String>> allChoices) {
		//find correct answer
		String ident = correct.get("answer_ident");
		String answer = "";
		for(HashMap<String,String> o: allChoices) {
			if(o.containsValue(ident)) {
				answer = o.get("answer_value");
			}
		}
		return answer;
	}
	
	private static ArrayList<String> findChoices(ArrayList<HashMap<String, String>> allChoices, String correct) {
		ArrayList<String> choices = new ArrayList<String>();
		for(HashMap<String,String> o: allChoices) {
			if(!o.get("answer_value").equals(correct)) {
				choices.add(o.get("answer_value"));
			}
				
		}
		return choices;		
	}
	
	
	private static String findMatches(ArrayList<HashMap<String, String>> allChoices, ArrayList<HashMap<String,String>> correctAnswers) {
		HashMap<String,String> matches = new HashMap<String,String>();
		ArrayList<String> keys = new ArrayList<String>();
		for(HashMap<String,String> map: correctAnswers) {
			HashMap<String,String> temp = new HashMap<String,String>();
			for(HashMap<String,String> all:allChoices) {
				if(all.containsValue(map.get("response_ident"))) {
					temp.put("left", all.get("answer_name"));
				}
				if(all.containsValue(map.get("answer_ident"))) {
					temp.put("right", all.get("answer_value"));
				}
			}
			matches.put(temp.get("left"), temp.get("right"));
		}
		keys.addAll(matches.keySet());
		
		return AnswerFormatter.answerJSONString(keys,matches);
	}
	
	public static String moveReference(String filepath, String filename) throws IOException {
		Path currentPath = Paths.get(filepath);
		Path newPath = Paths.get(REFERENCE_DIRECTORY);
		
		Path newFile = newPath.resolve(filename);
		
		Files.copy(currentPath,newFile);
		
		return newFile.toString();
		
	}
	
	
	public static void deleteDirectory(String path) throws IOException{
		Path directory = Paths.get(path);
		
		Files.walk(directory)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
	}
	
	public static void deleteDirectory(Path path) throws IOException{
		Files.walk(path)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
	}
	
	public static void imsccCleanup(String filepath) {

		try(Stream<Path> walk = Files.walk(Paths.get(filepath))){

			List<Path> files = walk
					.filter(Files::isRegularFile)
					.collect(Collectors.toList());

			for(Path p: files) {
				if(p.toString().endsWith(".xml") && !p.toString().contains("imsmanifest.xml")) {
					Files.deleteIfExists(p);
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
