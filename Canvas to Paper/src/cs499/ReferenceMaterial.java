package cs499;

import static cs499.data_classes.Tables.REFERENCE_MATERIAL;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class ReferenceMaterial {
	
	private String name;
	
	private String filepath;
	
	public ReferenceMaterial(String name, String filepath) {
		this.name = name;
		this.filepath = filepath;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFilepath() {
		return this.filepath;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public void loadReference() {
		
		String url = "jdbc:sqlite:./db/canvas2paper.db";
		
		try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     
            
            Record result = create.select()
            		.from(REFERENCE_MATERIAL)
            		.where(null).fetch();

            for (Record r : result) {
                String name = r.getValue(REFERENCE_MATERIAL.NAME);
                String description = r.getValue(REFERENCE_MATERIAL.DESCRIPTION);
                byte[] content = r.getValue(REFERENCE_MATERIAL.CONTENT);

                System.out.println("Name: " + name + " description: " + description);
                System.out.println("Content: " + content);
            }
        } 

        // exception handling
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}
