package cs499;


import static cs499.data_classes.Tables.*;
import java.sql.*;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.*;


import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.Document;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        String url = "jdbc:sqlite:./db/canvas2paper.db";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            create.insertInto(INSTRUCTOR,
                    INSTRUCTOR.FNAME, INSTRUCTOR.LNAME, INSTRUCTOR.EMAIL, INSTRUCTOR.TITLE)
                  .values("Tim", "Jones", "tjones@test.com", "Doctor")
                  .execute();
            
            
            Result<Record> result = create.select().from(INSTRUCTOR).fetch();

            for (Record r : result) {
                String fname = r.getValue(INSTRUCTOR.FNAME);
                String lname = r.getValue(INSTRUCTOR.LNAME);
                String email = r.getValue(INSTRUCTOR.EMAIL);
                String title = r.getValue(INSTRUCTOR.TITLE);

                System.out.println("Name: " + title +" "+ fname +" "+ lname + " email: " + email);
            }
        } 

        // exception handling
        catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}

}
