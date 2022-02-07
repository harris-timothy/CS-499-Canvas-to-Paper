package cs499;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParseQTI qti = new ParseQTI();
		
		try {
			qti.unzip("C:/Users/gamin/Desktop/499/cs-214-03-fa21-intro-discrete-structure-quiz-export.zip", "C:/Users/gamin/Desktop/499/QTITest");
			qti.xmlParse("C:/Users/gamin/Desktop/499/QTITest/g2fffa9a2e3da438e3f049ef93576c8c2/assessment_meta.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
