package java_programming_exam2.DB;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TimeZone;
import org.xml.sax.SAXException;


/**
 * Class InitializeDataBase
 * Description : Get the connection data from the .xml file
 * 				 and encapsulates the connection with it attribute of type ConexionBD
 * 				 it also provides a method to insert dates into the DataBase
 * @package org.apache.maven.java_programming_exam.DB
 */
public class InitializeDataBase {
	
	protected ConexionBD con;
	String[] dates;
	String connection = null;
	String user = null;
	String password = null;
	
    public InitializeDataBase() {
    	
    	boolean XmlFormatOk = false;
    	String XmlPath = "src\\main\\resources\\[MySql]Data-base-configuration.xml";
 
		while (!XmlFormatOk) {
			//Run correctInitialize until the .xml is the correct one
			
			XmlFormatOk = CorrectXmlFormat(XmlPath);
			
	    	try {
	    		System.out.println ("Please wait while the program tries to connects to the database...");
				con = new ConexionBD (connection, user, password);
			} catch (SQLException e) {
				System.out.println ("\n[Error] Invalid connection. The data was not saved.");
				System.out.println ("Please check the .xml connection file and insert a valid connection.\n");
				XmlFormatOk = false;
				XmlPath = "";
			} catch (ClassNotFoundException e1) {
				System.out.println ("\n[Error] Invalid connection. The data was not saved.");
				System.out.println ("Please check the .xml connection file and insert a valid connection.\n");
				XmlFormatOk = false;
				XmlPath = "";
			}
		}
    }
    
    private boolean CorrectXmlFormat (String XmlPath) {
    	
    	boolean correct = false;
    	
		try {
			dates = ReadXml.getXmlDates(XmlPath);
	    	connection = dates[0]+TimeZone.getDefault().getID();
	    	user = dates[1];
	    	password = dates[2];
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
        	System.out.println("The .xml file does not have the appropriate format, please select another one.");
        	return correct;
		}
		correct = true;
	
		return correct;
    	
    }
    
	public boolean insertDates(String name, float total, Timestamp timestamp) {
		
		boolean exist = true;
		
		try {
			con.insertDates(name, total, timestamp);
		} catch (SQLException e) {
			con.createTable();	
			try {
				con.insertDates(name, total, timestamp);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (NullPointerException e2) {
			e2.printStackTrace();
		}
		
		return exist;
	} 
}
