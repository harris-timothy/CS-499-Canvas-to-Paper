package cs499.api_client.data;

import static cs499.data_classes.Tables.COURSE;
import static cs499.data_classes.Tables.INSTRUCTOR;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.Record;

import cs499.Instructor;

public class Course {
	
	private int id;
	
	private String name;
	
	private int user_id;
	
	public Course (int id, int user_id) {
		this.id = id;
		this.user_id = user_id;
		loadCourseInfo();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void loadCourseInfo() {
		String url = "jdbc:sqlite:./db/canvas2paper.db";
		try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            Record result = create.select()
            .from(COURSE)
            .where(COURSE.ID.eq(id))
            .fetchOne();
            
            if(result != null) {
            	setName(result.getValue(COURSE.NAME));
                setUser_id(result.getValue(COURSE.INSTRUCTOR_ID));  
                	
            }            
        }
		catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	public void saveCourseInfo() {
		String url = "jdbc:sqlite:./db/canvas2paper.db";
		try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            Record course_exists = create.select()
                    .from(COURSE)
                    .where(COURSE.ID.eq(id))
                    .fetchOne();
            
             Record instructor_exists = create.select(INSTRUCTOR.ID)
            		.from(INSTRUCTOR)
            		.where(INSTRUCTOR.ID.eq(user_id))
            		.fetchOne();
             
            if(instructor_exists == null) {
            	Instructor instructor = new Instructor(user_id);
            	instructor.saveInstructor();
            }
            
            if(course_exists == null) {
            	create.insertInto(
            		COURSE, 
                    COURSE.NAME,
                    COURSE.API_ID,
                    COURSE.INSTRUCTOR_ID)
                  .values(name, id, user_id)
                  .execute();
            }
            else {
            	create.update(COURSE)
            	.set(COURSE.NAME, name)
            	.set(COURSE.INSTRUCTOR_ID, user_id)
            	.where(COURSE.ID.eq(id))
            	.execute();
            }
   		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
