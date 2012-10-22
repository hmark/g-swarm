package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	static public String convertFileToString(String filename){
		try
		{
		    BufferedReader br = new BufferedReader(new FileReader(filename));
		    StringBuffer str = new StringBuffer();
		    String line = br.readLine();
		    
		    while (line != null){
		        str.append(line + "\n");
		        line = br.readLine();
		    }

		    return str.toString();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return null;
	}
}
