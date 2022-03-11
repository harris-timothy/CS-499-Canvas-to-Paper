package cs499;

import static cs499.data_classes.Tables.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParseQTI qti = new ParseQTI();
		ArrayList<Document> tempArray = new ArrayList<Document>();
		CreateQTI newQTI = new CreateQTI();
		newQTI.CreateQuiz();
		
		try {
			qti.unzip("C:/Users/gamin/Desktop/499/cs-214-03-fa21-intro-discrete-structure-quiz-export.zip", "C:/Users/gamin/Desktop/499/QTITest");
			tempArray = qti.xmlLoop("C:/Users/gamin/Desktop/499/QTITest");
			System.out.println(tempArray.size());
			for(Document doc : tempArray)
			{
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = "jdbc:sqlite:./db/canvas2paper.db";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);

            create.insertInto(COURSE,
                    COURSE.NAME, COURSE.INSTRUCTOR_ID)
                  .values("CS499", 1)
                  .execute();


            Result<Record> result = create.select().from(COURSE).fetch();

            for (Record r : result) {
                String cname = r.getValue(COURSE.NAME);
                int insID = r.getValue(COURSE.INSTRUCTOR_ID);

                System.out.println("Name: " + cname +" "+ insID);
            }
        } 

        // exception handling
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}
