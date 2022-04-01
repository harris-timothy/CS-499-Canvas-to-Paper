package cs499.question;

public enum QuestionType {
	MULTIPLE_CHOICE {
		public String toString() {
			return "multiple_choice_question";
		}
	},
	MATCHING {
		public String toString() {
			return "matching_question";
		}
	},
	CALCULATED {
		public String toString() {
			return "calculated_question";
		}
	},
	ESSAY {
		public String toString() {
			return "essay_question";
		}
	},
	MULTIPLE_BLANKS {
		public String toString() {
			return "fill_in_multiple_blanks_question";
		}
	},
	MULTIPLE_ANSWERS {
		public String toString() {
			return "multiple_answers_question";
		}
	},
	MULTIPLE_DROPDOWNS {
		public String toString() {
			return "multiple_dropdowns_question";
		}
	},
	NUMERICAL {
		public String toString() {
			return "numerical_question";
		}
	},
	SHORT_ANSWER {
		public String toString() {
			return "short_answer_question";
		}
	},
	FILE_UPLOAD {
		public String toString() {
			return "file_upload_question";
		}
	},
	TEXT_ONLY {
		public String toString() {
			return "text_only_question";
		}
	},
	TRUE_FALSE {
		public String toString() {
			return "true_false_question";
		}
	}
}