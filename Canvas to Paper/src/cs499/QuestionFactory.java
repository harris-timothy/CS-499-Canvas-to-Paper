package cs499;

import static cs499.data_classes.Tables.QUESTION;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class QuestionFactory {

	public static Question build(int id) {

		String url = "jdbc:sqlite:./db/canvas2paper.db";

		try (Connection conn = DriverManager.getConnection(url)) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			Record result = create.select()
					.from(QUESTION)
					.where(QUESTION.ID.eq(id))
					.fetchOne();

			String allAnswers = result.getValue(QUESTION.ANSWERS);
			String[] answerArray = allAnswers.split("@@@", 10);

			



		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


}
