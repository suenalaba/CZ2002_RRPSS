package restaurant_database;

// import relevant java libraries to be updated as we go..
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

//import Table class
import restaurant_entity.Table;
import restaurant_manager.TableLayoutManager;

/** 
 * Sub class of DatabaseFunction
 * TableLayoutDatabase reads and write to customerDB.txt
 * @author Loh Yi Ze
 * @version 4.5
 * @since 2021-11-13
 */
public class TableLayoutDatabase implements DatabaseFunction{
	/**
	 * DELIMITER to split tokens
	 */
	public static final String DELIMITER = ";";
	
	
	/**
	 * Reads data from tableLayoutDB.txt into ArrayList<Table>
	 * @param textfilename tableLayoutDB.txt
	 * @return mainLayout Arraylist of Table class
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	public ArrayList<Table> fread(String textfilename) throws IOException {

		ArrayList<String> fileasstring = FileRead.fread(textfilename);
		
		//array to store table
		ArrayList<Table> mainLayout = new ArrayList<>();

		for (int i = 0; i < fileasstring.size(); i++) {
			String data = (String) fileasstring.get(i);
			StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); 
			int tableID = Integer.parseInt(str_tokenizer.nextToken().trim());
			int tableCapacity = Integer.parseInt(str_tokenizer.nextToken().trim());
			Table table = new Table(tableID, tableCapacity);
			mainLayout.add(table);

		}
		return mainLayout;

	}
	
	/**
	 * Writes data from mainLayout to tableLayoutDB.txt
	 * @param textfilename tableLayoutDB.txt
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	public void fwrite(String textfilename) throws IOException {
		ArrayList<String> tablelist = new ArrayList<String>();// array list to store table data
		ArrayList<Table> mainLayout=TableLayoutManager.getInstance().getLayout().getTableLayout();
		for (int i = 0; i < mainLayout.size(); i++) {
			Table table = (Table) mainLayout.get(i);
			StringBuilder tablestring = new StringBuilder();
			tablestring.append(Integer.toString(table.getTableID()));
			tablestring.append(DELIMITER);
			tablestring.append(Integer.toString(table.getTableCapacity()));
			tablestring.append(DELIMITER);
			tablelist.add(tablestring.toString());
		}
		FileRead.fwrite(tablelist,textfilename);
	}
}

