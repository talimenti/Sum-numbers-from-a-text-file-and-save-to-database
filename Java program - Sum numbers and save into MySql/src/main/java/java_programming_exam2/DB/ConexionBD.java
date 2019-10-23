package java_programming_exam2.DB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * Class ConexionBD
 * Description : Responsible for the connection to the database, creation of tables and data insertion
 * @package org.apache.maven.java_programming_exam.DB
 */
class ConexionBD {
	
	private Connection con;
	private ResultSet rs;
	private String query;
	private PreparedStatement stmt;
	
	protected ConexionBD (String connection, String user, String password) throws SQLException, ClassNotFoundException{
				
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(connection, user, password);
			con.createStatement();
				
			if (! (existTable())) 
				createTable();	
	}
	
	private boolean existTable () throws SQLException {
		
		boolean b = false;

		DatabaseMetaData md= con.getMetaData();
		rs = md.getTables(null, null, "fileoutputs", null); 
		
		if (rs.next()) { 
			b = true; //table fileoutputs exist
		} 
		else
			b = false; //table fileoutput does not exist 
	
		return b;    
	} 
	
	protected void createTable() {
		
		try {
			query = "CREATE TABLE fileoutputs (filename VARCHAR(128) , filevalue FLOAT , processdate TIMESTAMP )";
			stmt = con.prepareStatement(query);  
			stmt.execute(); 
			stmt.close();	
		} catch (SQLException sqle) { 
			 System.out.println("Error en la ejecución: " 
					 + sqle.getErrorCode() + " " + sqle.getMessage());    
		}
	}
	
	public void insertDates(String name, float total, Timestamp timestamp) throws SQLException {
		
			query = "INSERT INTO fileoutputs VALUES (?,?,?)" ;
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setFloat(2, total);
			stmt.setTimestamp(3, timestamp);
			stmt.executeUpdate(); 
			System.out.println("Total amount, name and timestamp were saved in the database.");
	}
}
