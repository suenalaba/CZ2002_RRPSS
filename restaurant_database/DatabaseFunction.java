package restaurant_database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface DatabaseFunction {
	
	/* For writing to database */
	public void fwrite(String textfilename, List arraylist) throws IOException;
		
	
	/* For reading to database */
	public ArrayList fread(String textfilename) throws IOException;

}