package restaurant_database;

//importing relevant java libraries to be updated as we go...
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;

public class FileRead {



	/**
	 * For DB classes to read data from text file.
	 * 
	 * @param textfilename
	 *        Defines the textfilename records are read from
	 *            
	 *            
	 * @return the repsective records to read from textfilename
	 * 
	 * 
	 */
	public static ArrayList<String> fread(String textfilename) throws IOException {
		ArrayList<String> records = new ArrayList<String>();
	
		Scanner sc = new Scanner(new FileInputStream(textfilename));
		try {  
			while (sc.hasNextLine() == true){
		        records.add(sc.nextLine()); //keep reading until EOF
		      }
		    }
		    finally{
		      sc.close();
		    }
		return records;
	}
	
	
	/**
	 * For DB classes to write data into text file.
	 * 
	 * @param records
	 * 		The respective records to fwrite into textfilename.
	 * 
	 * @param textfilename
	 *            To determine the file to write into.
	 *            Name of textfile for write
	 */
	
	
	public static void fwrite(ArrayList<String> records, String textfilename) throws IOException {
		PrintWriter writing = new PrintWriter(new FileWriter(textfilename));

        try {
    		for (int i = 0; i < records.size() ; i++) {
    			writing.println((String)records.get(i));
    		}
        }
        finally {
        	writing.close();
        }
	}
}
