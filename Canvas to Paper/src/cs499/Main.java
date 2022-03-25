package cs499;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import cs499.qti.ParseQTI;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filepath = "C:/Users/black/Downloads/cs-214-03-fa21-intro-discrete-structure-quiz-export.zip";
		String destination = "./qti/";
		
		ParseQTI parser = new ParseQTI();
		
//		try {
//			parser.unzip(filepath, destination);
//			System.out.println("unzipped");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			parser.xmlParse("./qti/gd360ceb1d866eef9962487e2be79b4b8.xml");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
        	
		
	}

}
