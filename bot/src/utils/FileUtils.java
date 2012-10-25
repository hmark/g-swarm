package utils;

import java.io.*;

public class FileUtils {

	static public String convertFileToString(String filename){
		try {
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
	
	static public void saveStringToFile(String filepath, String data){
		try {
			File file = new File(filepath);
			file.getParentFile().mkdirs();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath));
			out.write(data);
			out.close();
		} 
		catch (IOException e) { 
			System.err.println("Error: " + e.getMessage());
		}
	}
}
