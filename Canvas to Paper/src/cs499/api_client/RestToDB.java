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
	
	private static final String url = "jdbc:sqlite:db/canvas2paper.db";
	
	
	public void mapCourse(JSONObject course) {
		
		
		
	}
	
	
	
}