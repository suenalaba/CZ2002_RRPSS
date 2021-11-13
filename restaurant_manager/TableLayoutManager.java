package restaurant_manager;
import restaurant_entity.Table;
import restaurant_entity.Table.status;
import restaurant_entity.TableLayout;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import restaurant_database.TableLayoutDatabase;
/**
 * Stores a TableLayout object during runtime and methods to manipulate TableLayout and Table objects
 * @author	Yi Ze
 * @version 4.5
 * @since	13-11-2021
 */
public class TableLayoutManager {
	/**
	 * For singleton pattern adherence. This TableLayoutManager instance persists throughout runtime.
	 */
	private static TableLayoutManager instance=null;
	/**
	 * TableLayout that holds ArrayList of Table objects that can referenced during runtime
	 */
	TableLayout layout = new TableLayout();
	/**
	 * Default TableLayoutManager constructor
	 */
	public TableLayoutManager() {
		layout=new TableLayout();
	}
	/**
	 * For singleton pattern adherence. 
	 * @return instance The static instance that persists throughout runtime.
	 */
	public static TableLayoutManager getInstance() {
        if (instance == null) {
            instance = new TableLayoutManager();
        }
        return instance;
    }
	/**
	 * Gets the TableLayoutObject of instance
	 * @return layout which is TableLayout of instance
	 */
	public TableLayout getLayout(){
			return layout;
		}
	/**
	 * get Table Index in tableLayout Array
	 * @param tableID the TableID of table to index
	 * @return
	 */
	public int getTableIndex(int tableID) {
		ArrayList<Table> arr = new ArrayList<Table>();
		arr = layout.getTableLayout();
		for(int i =0; i<arr.size(); i++) {
			if(arr.get(i).getTableID() == tableID) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Gets Table object from TableLayout which corresponds to TableID
	 * @param tableID of the Table to get
	 * @return Table object or null if no Table with TableID given in TableLayout exists
	 */
	public Table getTable(int tableID) {
		for (Table table:layout.getTableLayout()) {
			if (table.getTableID()==tableID) {
				return table;
			}
		}
		return null;
	}
	/**
	 * Creates and adds table to TableLayout Table ArrayList
	 * @param tableID of the new Table
	 * @param tableCapacity of the new Table
	 */
	public void createTable(int tableID, int tableCapacity) {
		layout.getTableLayout().add(new Table(tableID,tableCapacity));
		System.out.println("Table " + tableID + " with capacity of "+tableCapacity+" added");
	}
	/**
	 * Removes table with corresponding tableID
	 * @param tableID of Table object to remove from TableLayout Table ArrayList
	 */
	public void removeTable(int tableID) {
		int index = getTableIndex(tableID); 
		if(index == -1) {
			System.out.println("Table does not exist");
		}
		else {
			layout.getTableLayout().remove(index);
			System.out.println("Table " + tableID + " removed");
		}
	}
	/**
	 * Occupies the table with corresponding tableID and at time provided
	 * @param tableID of Table to occupy
	 * @param time reservation Start Date Time of occupancy
	 */
	public void occupyTable(int tableID,LocalDateTime time) {
		Table table=getTable(tableID);
		int year=time.getYear();
		int day=time.getDayOfYear();
		int hour=time.getHour();
		int minute=time.getMinute();
		if (day==LocalDateTime.now().getDayOfYear() && year==LocalDateTime.now().getYear()) {
			if (table.getHourBlock(hour)==status.RESERVED) {
				table.setHourBlock(hour, status.OCCUPIED);
			}
			if (table.getHourBlock(hour)!=status.CLOSED) {
				table.setHourBlock(hour,status.OCCUPIED);
			}
			if (minute>0 && table.getHourBlock(hour+1)!=status.CLOSED) {
				table.setHourBlock(hour+1, status.OCCUPIED);
			}
		}
	}
	/**
	 * Updates the Table hourBlock status element at specified time
	 * @param tableID of Table to update hourBlock of
	 * @param time Time inside houBlock that needs the update
	 * @param newStatus element status to update to inside hourBlock array 
	 */
	public void updateTableStatus(int tableID, LocalDateTime time,status newStatus) {
		Table table=getTable(tableID);
		int year=time.getYear();
		int day=time.getDayOfYear();
		int hour=time.getHour();
		if (day==LocalDateTime.now().getDayOfYear() && year==LocalDateTime.now().getYear()) {
			table.setHourBlock(hour,newStatus);
		}
	}
	/**
	 * Free table of all occupied status but leaves reserved status alone
	 * @param tableID of Table to free
	 */
	public void freeTableStatus(int tableID) {
		Table table=getTable(tableID);
		for (int i=0;i<24;i++) {
			if (table.getHourBlock(i)==status.OCCUPIED) {
				table.setHourBlock(i,status.EMPTY);
			}
		}
	}
	/**
	 * Gets Table Layout ArrayList of Tables of current instance
	 * @return tableLayout ArrayList of Tables
	 */
	public ArrayList<Table> getAllTables(){
		return layout.getTableLayout();
	}
	/**
	 * Gets ArrayList of Tables that are occupied at current time
	 * @return ArrayList of Tables from TableLayout that are occupied
	 */
	public ArrayList<Table> getOccupiedTables(){
		ArrayList<Table> tables = new ArrayList<Table>(); 
		ArrayList<Table> outputTables = new ArrayList<Table>();
		tables = layout.getTableLayout(); 
		int hour=LocalDateTime.now().getHour();
		for(int i = 0; i<tables.size(); i++) {
			if(tables.get(i).getHourBlock(hour)==status.OCCUPIED) {
				outputTables.add(tables.get(i)); 
			}
		}
		return outputTables; 
	}
	/**
	 * Gets Sorted ArrayList of Tables that can fit pax. Sorted by capacity ascending
	 * @param pax to fit in table
	 * @return sorted ArrayList of tables that can fit pax
	 */
	public ArrayList<Integer> getMinTableList(int pax) { 
		ArrayList<Integer> capList=new ArrayList<Integer>();
		for (int i=2;i<=10;i+=2) {
			if (i>=pax) {
				capList.add(i);
			}
		}
 		ArrayList<Integer> minTables=new ArrayList<Integer>();
 		for (int k=0;k<capList.size();k++) {
 			for (int i=0;i<layout.getTableLayout().size();i++) {
 				if (layout.getTableLayout().get(i).getTableCapacity()==capList.get(k)) {//to sort
 					minTables.add(layout.getTableLayout().get(i).getTableID());
 				}
 			}
 		}
 		return minTables;
	}
    /**
	 * Saves the instance's tableLayout as string in a text file.
	 * @param textFileName The name of the the text file.
	 */
	public void saveDB(String textFileName){
		TableLayoutDatabase saver=new TableLayoutDatabase();
		try {
			saver.fwrite(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to save to "+textFileName);
			return;
		}
	}
    /**
	 * Loads to instance's tableLayout from a text file
	 * @param textFileName The name of the text file.
	 */
	public void loadDB(String textFileName){
		TableLayoutDatabase loader=new TableLayoutDatabase();
		try {
			this.layout.setTableLayout(loader.fread(textFileName));
		} catch (IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}
		System.out.println("Loaded successfully from "+textFileName);
	}
	
}
