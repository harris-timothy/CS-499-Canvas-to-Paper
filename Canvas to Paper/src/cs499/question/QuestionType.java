package cs499.question;

import java.util.HashMap;
import java.util.Map;

public enum QuestionType {
	
	MULTIPLE_CHOICE ("multiple_choice_question"),		
	MATCHING ("matching_question"),		
	CALCULATED ("calculated_question"),
	ESSAY ("essay_question"),
	MULTIPLE_BLANKS ("fill_in_multiple_blanks_question"),
	MULTIPLE_ANSWERS ("multiple_answers_question"),
	MULTIPLE_DROPDOWNS ("multiple_dropdowns_question"),
	NUMERICAL ("numerical_question"),
	SHORT_ANSWER ("short_answer_question"),
	FILE_UPLOAD ("file_upload_question"),
	TEXT_ONLY ("text_only_question"),
	TRUE_FALSE ("true_false_question");
	
	private final String type;
	
	QuestionType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	private static final Map<String,QuestionType> BY_TYPE = new HashMap<>();
	
	static {
		for (QuestionType q: values()) {
			BY_TYPE.put(q.type, q);
		}
	}
	
	public static QuestionType valueOfType(String type) {
		return BY_TYPE.get(type);
	}
}