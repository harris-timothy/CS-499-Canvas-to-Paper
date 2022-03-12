package cs499.api_client.data;

import static cs499.data_classes.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.data_classes.tables.records.InstructorRecord;

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
		int instructor_id;
		try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            instructor_id = create.select(INSTRUCTOR.ID)
            		.from(INSTRUCTOR)
            		.where(INSTRUCTOR.ID.eq(user_id)
            		.fetch(InstructorRecord::getId);
            
            
            
            
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
