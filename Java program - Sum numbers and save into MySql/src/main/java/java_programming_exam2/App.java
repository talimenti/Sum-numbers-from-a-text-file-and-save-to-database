package java_programming_exam2;

import java_programming_exam2.ConsoleFunctions.ReadFromConsole;
import java_programming_exam2.DB.InitializeDataBase;
import java_programming_exam2.FileFunctions.CheckTypeOfFile;
import java_programming_exam2.PrintFunctions.Header;
import java_programming_exam2.PrintFunctions.PrintResult;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * App name:  	 Java Programming exam Problem 1
 * Description:  Java exam for Commviva
 * Version:      1.0.0
 * Author:       Alimenti Bel Traian <traian.alimentibel@gmail.com>
 */
public class App 
{
	
    public static void main( String[] args )
    {

		String path; //path of the choosen file
		String name; //store the name of the file
		Boolean correctselect=false; //is false until user choose a valid file
		ReadFromConsole rc = new ReadFromConsole(); //start reading by console
		Timestamp timestamp;
		float total = 0; //store the sum of all numbers in the file
		
		Header.printHeader("2");
		
		while (!correctselect) {
			path = rc.choosefile("Text file");
			path = path.trim(); //remove white spaces at the beginning and end
			
			if ( ! (path.isEmpty())) { //if user did not enter only blank spaces
	
				try {
					
					if (CheckTypeOfFile.check(path, "text/plain")) {  //return true if the file is plain text
						total = PrintResult.print(path); //method that print the result
						correctselect = true; //in order to end while
						timestamp = new Timestamp(System.currentTimeMillis()); //get timeStamp to save in database
						name = new File(path).getName(); //get name to save in database
						InitializeDataBase db = new InitializeDataBase(); //create connection with database
						db.insertDates(name, total, timestamp); //insert dates in the data base
					}
					else 
						System.out.println("It only allows plaint text files as input file.\n");
					
				} catch (IOException e) {
					System.out.println ("File not found. ");
				}
			}
		}
    } 
}
