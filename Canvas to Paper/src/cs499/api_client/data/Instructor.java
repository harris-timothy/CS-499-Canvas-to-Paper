package cs499.api_client.data;

import static cs499.data_classes.Tables.INSTRUCTOR;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

public class Instructor {
	
	private int id;
	
	private String email;
	
	public Instructor(int id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public void storeInstructorInfo() {
		String url = "jdbc:sqlite:./db/canvas2paper.db";
		try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            try {
            	create.insertInto(
            		INSTRUCTOR, 
                    INSTRUCTOR.API_ID,
                    INSTRUCTOR.EMAIL)
                  .values(id, email)
                  .execute();
            } catch(DataAccessException e) {
            	create.update(INSTRUCTOR)
            	.set(INSTRUCTOR.API_ID, id)
            	.where(INSTRUCTOR.EMAIL.eq(email))
            	.execute();
            }
   		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
