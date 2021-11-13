package restaurant_database;

//importing relevant java libraries to be updated as we go...
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
/**
 * For reading and writing methods
 * @author Joshua Khoo 
 * @version 4.5
 * @since 2021-11-13
 * 
 */
public class FileRead {



	/**
	 * For DB classes to read data from text file.
	 * 
	 * @param textfilename
	 *        Defines the textfilename records are read from
	 *            
	 *            
	 * @return the repsective records to read from textfilename
	 * @throws IOException Signals that an I/O exception of some sort has occurred
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
	 * @throws IOException Signals that an I/O exception of some sort has occurred
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
