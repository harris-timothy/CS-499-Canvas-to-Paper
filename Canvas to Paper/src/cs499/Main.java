package cs499;

import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;

import static cs499.data_classes.Tables.*;
// import static org.jooq.impl.DSL.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.*;


import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.Document;


public class Main {

	public static void main(String[] args) {
		
		String main_frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String main_frame_icon_path = "Canvas to Paper/lib/images/main_frame_icon.png";

		//Initialize the main frame of the application
		JFrame main_frame = new JFrame(main_frame_title);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EXIT_ON_CLOSE terminates the entire program, not just the window
		main_frame.setSize(600, 400);

		//Create an image of the icon path and set the icon of the main frame
		Image main_frame_icon = Toolkit.getDefaultToolkit().createImage(main_frame_icon_path);
		main_frame.setIconImage(main_frame_icon);
		
		main_frame.setVisible(true);

        String url = "jdbc:sqlite:db/testdb.sqlite";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            /*create.insertInto(INSTRUCTOR,
                    INSTRUCTOR.ID, INSTRUCTOR.NAME, INSTRUCTOR.EMAIL)
                  .values(3, "Prof. John Smith", "jsmith@fakeemail.com")
                  .execute();
            */
            
            Result<Record> result = create.select().from(INSTRUCTOR).fetch();

            for (Record r : result) {
                Integer id = r.getValue(INSTRUCTOR.ID);
                String name = r.getValue(INSTRUCTOR.NAME);
                String email = r.getValue(INSTRUCTOR.EMAIL);

                System.out.println("ID: " + id + " name: " + name + " email: " + email);
            }
        } 

        // exception handling
        catch (Exception e) {
            e.printStackTrace();
        }
		
		
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
			e.printStackTrace();
		}
	}

}
