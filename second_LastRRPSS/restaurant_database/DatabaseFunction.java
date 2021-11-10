package restaurant_database;

import java.io.IOException;
import java.util.ArrayList;

public interface DatabaseFunction {
	
	/* For writing to database */
	public void fwrite(String textfilename) throws IOException;
		
	/* For reading to database */
	public ArrayList<?> fread(String textfilename) throws IOException;

}