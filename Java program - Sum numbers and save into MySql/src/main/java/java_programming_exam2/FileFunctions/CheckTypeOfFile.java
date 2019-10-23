package java_programming_exam2.FileFunctions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Class CheckTypeOfFile
 * Description : Check whether the mime type of the file is equal to the one passed by parameter or not
 * @package org.apache.maven.java_programming_exam.FileFunctions
 */
public class CheckTypeOfFile {
	
	public static boolean check (String path, String type) throws IOException {
		
		boolean ok = false;
				
		if (new File(path).exists()) {
			Path file = new File(path).toPath();
			String ext = Files.probeContentType(file);
			
			if (! (ext == null) )	
				if ( ext.equals(type) ) 
					ok = true;
		}
		
		return ok; //return true if file type is plain text, if not return false
	}
}

