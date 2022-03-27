package cs499.question;

import static cs499.data_classes.Tables.QUESTION;

import java.sql.Connection;
import java.sql.DriverManager;

import org.json.JSONArray;

import cs499.DataHelper;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class QuestionFactory {

	public static Question build(int id) {

		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record result = create.select()
					.from(QUESTION)
					.where(QUESTION.ID.eq(id))
					.fetchOne();
			
			String allAnswers = result.getValue(QUESTION.ANSWERS);
			JSONArray answerArray = new JSONArray(allAnswers);
			
			if(answerArray.length() == 1) {
				return new SingleAnswerQuestion(id);
			}
			else if(answerArray.getJSONObject(0).has("left")) {
				return new MatchingQuestion(id);
			}
			else {
				return new MultipleChoiceQuestion(id);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


}
