package cs499;

import static cs499.data_classes.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.*;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
		

	}

}
