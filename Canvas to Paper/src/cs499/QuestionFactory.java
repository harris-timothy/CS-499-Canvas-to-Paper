package cs499;

import static cs499.data_classes.Tables.QUESTION;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class QuestionFactory {
	
	private static final String delimiter = "@@@";

	public static Question build(int id) {

		String url = "jdbc:sqlite:./db/canvas2paper.db";

		try (Connection conn = DriverManager.getConnection(url)) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record result = create.select()
					.from(QUESTION)
					.where(QUESTION.ID.eq(id))
					.fetchOne();

			String allAnswers = result.getValue(QUESTION.ANSWERS);
			String[] answerArray = allAnswers.split(delimiter, 10);
			
			if(answerArray.length == 1) {
				return new SingleAnswerQuestion(id);
			}
			else {
				if(answerArray[0].contains("correct")) {
					return new MultipleChoiceQuestion(id);
				}
				else {
					return new MatchingQuestion(id);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


}
