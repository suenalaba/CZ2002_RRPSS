package restaurant_database;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface to facilitate reading and writing
 * @author Joshua Khoo
 * @version 4.5
 * @since 2021-11-13
 *
 */
public interface DatabaseFunction {
	/**
	 * For writing to database
	 * @param textfilename 
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	public void fwrite(String textfilename) throws IOException;
	
	/**
	 * 
	 * @param textfilename
	 * @return ArrayList<?>
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	public ArrayList<?> fread(String textfilename) throws IOException;

}