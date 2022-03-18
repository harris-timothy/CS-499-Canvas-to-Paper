package cs499;

public enum QuestionType {
	SINGLE_ANSWER {
		public String toString() {
			return "single_answer";
		}
	},
	MULTIPLE_CHOICE {
		public String toString() {
			return "multiple_choice";
		}
	},
	MATCHING {
		public String toString() {
			return "matching";
		}
	}
}
