package cs499;

import static cs499.data_classes.Tables.METADATA;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.jooq.Record;
import org.jooq.Result;

import cs499.utils.DataHelper;
import cs499.question.*;

public class Reporting {
	
	private ArrayList<String> metaTitles = new ArrayList<String>();
	
	public ArrayList<String> getMetaTitles() {
		return metaTitles;
	}

	public ArrayList<HashMap<String, String>> getMetaList() {
		return metaList;
	}

	private ArrayList<HashMap<String,String>> metaList = new ArrayList<HashMap<String,String>>();
	
	public Reporting() {
		loadMetadata();
	}
		
	public void loadMetadata() {
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            
            Result<Record> result = create.select()
            		.from(METADATA)
            		.fetch();
            
            for(Record r: result) {
            	parseMetaJSON(r.getValue(METADATA.DATA));
            }
                        
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private ArrayList<Question> loadQuestions(HashMap<String, String> meta) {
		ArrayList<Integer> questionList = new ArrayList<Integer>();
		ArrayList<Question> questionArray = new ArrayList<Question>();
		
		for(String string: meta.keySet()) {
			try{
				questionList.add(Integer.parseInt(string));
			}catch(NumberFormatException e) {
				continue;
			}
		}
		
		for(int id: questionList) {
			questionArray.add(QuestionFactory.build(id));
		}
		
		return questionArray;
	}
	
	public ArrayList<String> getQuestionUsageData(Integer questionId) {
		
		ArrayList<String> usesArray = new ArrayList<String>();
		
		for(HashMap<String,String> map: metaList) {
			if(map.containsKey(questionId.toString())) {
				usesArray.add(map.get("name") + " " + map.get("date"));
			}
		}
		return usesArray;
	}
	
	public ArrayList<Question> getDetails(HashMap<String,String> meta) {
		return loadQuestions(meta);
	}
	
	
	
	private void parseMetaJSON(String metaJSON) {
		HashMap<String,String> map = new HashMap<String,String>();
		JSONArray metaJSONArray = new JSONArray(metaJSON);
		
		for(int i = 0; i < metaJSONArray.length();i++) {
			if(metaJSONArray.getJSONObject(i).has("name")) {
				String name = metaJSONArray.getJSONObject(i).getString("name");
				String date = metaJSONArray.getJSONObject(i).getString("date");
				metaTitles.add(name + " " + date);
				map.put("name", name);
				map.put("date", date);
			}
			else {
				Integer questionId = metaJSONArray.getJSONObject(i).getInt("question_id");
				map.put(questionId.toString(),"true");
			}
		}
		metaList.add(map);
		
	}
	
	

}
