package cs499.api_client;

import org.jooq.tools.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import static cs499.data_classes.Tables.*;
import java.sql.*;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.*;

public class RestToDB {
	
	public static ObjectMapper mapper;
	
	
	public void mapCourse(JSONObject course) {
		String url = "jdbc:sqlite:db/testdb.sqlite";
		
		try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            create.insertInto(
            		COURSE,
                    COURSE.ID, 
                    COURSE.TITLE,
                    COURSE.CANVAS_ID)
                  .values(3, "Prof. John Smith", "jsmith@fakeemail.com")
                  .execute();
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}