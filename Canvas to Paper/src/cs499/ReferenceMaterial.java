package cs499;

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
		
	}

}
