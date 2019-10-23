package java_programming_exam2.DB;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java_programming_exam2.ConsoleFunctions.ReadFromConsole;
import java_programming_exam2.FileFunctions.CheckTypeOfFile;


/**
 * Class ReadXml
 * Description : Obtain the connection data with the MySql database (server, user and password) from an .xml file 
 * @package org.apache.maven.java_programming_exam.DB
 */
class ReadXml {


	protected static String[] getXmlDates (String path) throws SAXException, IOException {

            File file = new File(path);
            String[] dates = new String [3];
        	ReadFromConsole rc = new ReadFromConsole();
            boolean correctselect = false;
            
            while (! correctselect) {
            	
	            if (! (file.exists())) {
	            	String newpath = rc.choosefile(".xml file with data base configuration"); //get the path to the xml if it is not in root
	            	
	            	if (CheckTypeOfFile.check(newpath,"text/xml")) {  //return true if the file is xml text
		            	file = new File (newpath);
		            	dates = XmlDates(file);
						correctselect = true; //in order to end while
					}
					else 
						System.out.println("[Error] It only allows .xml files to start data base conecction.\n");
	            }
	            else {
	            	dates = XmlDates(file);
	            	correctselect = true;
	            }
            }
            
            return dates;
	}        
            
    protected static String[] XmlDates (File file) throws SAXException, IOException, NullPointerException{
            
        NodeList nList = null;
        Node nNode = null;
        Element eElement = null;
        String root = null;
        String server = null;
        String user = null;
        String password = null;
        String[] dates = new String [3];
    	
	            try {
	            	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            	org.w3c.dom.Document doc = dBuilder.parse(file);
	            	doc.getDocumentElement().normalize();
	            	root = doc.getDocumentElement().getNodeName(); //get root of xml file
	            	nList = doc.getElementsByTagName(root);
	            	nNode = nList.item(0);

	            	if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
	            		eElement = (Element) nNode;
	            		
	            		//GET SERVER, USERNAME AND PASSWORD OF .XML FILE AND KEEP IT INTO AN ARRAY
	            		server = eElement.getElementsByTagName("server").item(0).getTextContent(); 
	            		user = eElement.getElementsByTagName("username").item(0).getTextContent();
	            		password = eElement.getElementsByTagName("password").item(0).getTextContent();
	            		dates[0] = "jdbc:mysql://"+server+"?serverTimezone="; //TimeZone is required to connect
	            		dates[1] = user;
	            		dates[2] = password;     			
	            	}
	            } catch (ParserConfigurationException e) {
	            	e.printStackTrace();
	            }          
            
			return dates;
    }
}
