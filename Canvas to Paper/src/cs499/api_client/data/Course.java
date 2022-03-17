package cs499.api_client.data;

import static cs499.data_classes.Tables.COURSE;
import static cs499.data_classes.Tables.INSTRUCTOR;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class Course {
	
	private int id;
	
	private String name;
	
	private int user_id;
	
	public Course (int id, String name, int user_id) {
		this.id = id;
		this.name = name;
		this.user_id = user_id;
	}
	
	public void storeCourseInfo() {
		String url = "jdbc:sqlite:./db/canvas2paper.db";
		try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            int instructor_id = create.select(INSTRUCTOR.ID)
            		.from(INSTRUCTOR)
            		.where(INSTRUCTOR.API_ID.eq(user_id))
            		.fetchOne(INSTRUCTOR.ID, int.class);    
            
            create.insertInto(
            		COURSE, 
                    COURSE.NAME,
                    COURSE.API_ID,
                    COURSE.INSTRUCTOR_ID)
                  .values(name, id, instructor_id)
                  .execute();
   		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
