package cs499.qti;

import java.io.File;

public class ExportQTI {
	
	private File document;
	
	private File schema;
	
	public ExportQTI(File document, File schema) {
		this.setDocument(document);
		this.setSchema(schema);
	}

	public File getDocument() {
		return document;
	}

	public void setDocument(File document) {
		this.document = document;
	}

	public File getSchema() {
		return schema;
	}

	public void setSchema(File schema) {
		this.schema = schema;
	}
	
	

}
