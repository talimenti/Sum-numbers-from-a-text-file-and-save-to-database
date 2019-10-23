package java_programming_exam2.FileFunctions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class ReadFile
 * Description : Read file from a path and return and iterator with all the numbers in file
 * @package org.apache.maven.java_programming_exam.FileFunctions
 */
public class ReadFile {
	
	 public static Iterator<Number> read(String path) throws FileNotFoundException {
		
		Scanner scanfile;
		scanfile = new Scanner(new File(path));
		scanfile.useDelimiter("[^.1234567890]"); //states that numbers and dot are not separators
		scanfile.useLocale(Locale.US); //support point as decimal separator
		LinkedList<Number> res = new LinkedList<Number>();

		while(scanfile.hasNext()) { //read all numbers
			
			if (scanfile.hasNextInt())
				res.addLast(scanfile.nextInt());
			else {
				if (scanfile.hasNextFloat())
					res.addLast(scanfile.nextFloat());
				else {
					if (scanfile.hasNextDouble())
						res.addLast(scanfile.nextDouble());	
					else {
						scanfile.next();	//the next item is not of type number
					}
				}
			}
		}
		scanfile.close();
		
		return res.iterator();
	}
}
