package cs499;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ParseQTI {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

	private static ArrayList<Document> parsedFiles = new ArrayList<Document>();
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));

        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
                System.out.println(filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                System.out.println(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    
    public ArrayList<Document> xmlLoop(String filePath)
    {
    	  File dir = new File(filePath);
    	  File[] directoryListing = dir.listFiles();
    	  for (File child : directoryListing) {
    		  if(child.isDirectory())
    		  {
    			  xmlLoop(child.getPath());
    		  }
    		  else
    		  {
    			  String ext = "";
        		  String fileName = child.getName();
        		  int i = fileName.lastIndexOf('.');
        		  if (i >= 0) { 
        			  ext = fileName.substring(i+1); 
        		  }
        		  
        		  if (ext.equals("xml"))
        		  {
        			  // CHANGE THIS TO THE DATABASE ONCE READY
        			  parsedFiles.add(xmlParse(child.getPath()));
        		  }
    		  }
    	  }
    	  return(parsedFiles);
    }
    
    private Document xmlParse(String filePath)
    {
    	Document doc = null;
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            // Combine with XPath? Look into later
            // Might want to look at this later:
            /*NodeList nList = doc.getElementsByTagName("student");
            System.out.println("----------------------------");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
               Node nNode = nList.item(temp);
               System.out.println("\nCurrent Element :" + nNode.getNodeName());
               
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  System.out.println("Student roll no : " 
                     + eElement.getAttribute("rollno"));
                  System.out.println("First Name : " 
                     + eElement
                     .getElementsByTagName("firstname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Last Name : " 
                     + eElement
                     .getElementsByTagName("lastname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Nick Name : " 
                     + eElement
                     .getElementsByTagName("nickname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Marks : " 
                     + eElement
                     .getElementsByTagName("marks")
                     .item(0)
                     .getTextContent());
               }
            }*/
         } catch (Exception e) {
            e.printStackTrace();
         }
        
        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		return doc;
    }
    
}
