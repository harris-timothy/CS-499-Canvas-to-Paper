package cs499;

import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParseQTI qti = new ParseQTI();
		ArrayList<Document> tempArray = new ArrayList<Document>();
		
		try {
			qti.unzip("C:/Users/gamin/Desktop/499/cs-214-03-fa21-intro-discrete-structure-quiz-export.zip", "C:/Users/gamin/Desktop/499/QTITest");
			tempArray = qti.xmlLoop("C:/Users/gamin/Desktop/499/QTITest");
			System.out.println(tempArray.size());
			for(Document doc : tempArray)
			{
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
