package cs499;

import static cs499.data_classes.Tables.INSTRUCTOR;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.Record;
import org.jooq.SQLDialect;

import cs499.utils.DataHelper;

public class Instructor {
	
	//instructor info screen
	
	private String fname;
	
	private String lname;
	
	private String title;
	
	private String email;
	
	private int id;
	
	public Instructor(int id) {
		this.id = id;
		loadInstructor();
	}
	
	public String getName() {
		return title + " " + lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void loadInstructor() {		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     
            
            Record result = create.select()
            		.from(INSTRUCTOR)
            		.where(INSTRUCTOR.ID.eq(id))
            		.fetchOne();
            if(result != null) {
            	setFname(result.getValue(INSTRUCTOR.FNAME));
            	setLname(result.getValue(INSTRUCTOR.LNAME));
            	setTitle(result.getValue(INSTRUCTOR.TITLE));
            	setEmail(result.getValue(INSTRUCTOR.EMAIL));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void saveInstructor() {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     
            
            Record exists = create.select()
            		.from(INSTRUCTOR)
            		.where(INSTRUCTOR.ID.eq(id))
            		.fetchOne();
            
            if(exists == null) {
            	create.insertInto(INSTRUCTOR,
            			INSTRUCTOR.ID,
            			INSTRUCTOR.FNAME,
            			INSTRUCTOR.LNAME,
            			INSTRUCTOR.TITLE,
            			INSTRUCTOR.EMAIL)
            		.values(id, fname, lname, title, email)
            		.execute();
            }
            else {
            	create.update(INSTRUCTOR)
            	.set(INSTRUCTOR.FNAME, fname)
            	.set(INSTRUCTOR.LNAME, lname)
            	.set(INSTRUCTOR.TITLE, title)
            	.set(INSTRUCTOR.EMAIL, email)
            	.where(INSTRUCTOR.ID.eq(id))
            	.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
