package cs499;

import java.io.File;

public class ExportWord {
	
	private File document;
	
	private File template;
	
	public ExportWord(File document, File template) {
		this.setDocument(document);
		this.setTemplate(template);
	}
	
	public void createFile() {
		
	}
	
	public void format() {
		
	}

	public File getDocument() {
		return document;
	}

	public void setDocument(File document) {
		this.document = document;
	}

	public File getTemplate() {
		return template;
	}

	public void setTemplate(File template) {
		this.template = template;
	}

}
