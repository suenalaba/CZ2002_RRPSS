package restaurant_manager;
import restaurant_entity.Table;
import restaurant_entity.Table.status;
import restaurant_entity.TableLayout;
import restaurant_manager.ReservationManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_database.TableLayoutDatabase;
import restaurant_entity.Reservation;

import restaurant_entity.Table;
import restaurant_entity.Table.status;
import restaurant_entity.TableLayout;
import restaurant_manager.ReservationManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import restaurant_entity.Reservation;

public class TableLayoutManager {
	//Attributes
	private static TableLayoutManager instance=null;
	TableLayout layout = new TableLayout();
	
	//Constructor
	public TableLayoutManager() {
		layout=new TableLayout();
	}
	
	public TableLayoutManager(TableLayout tableSetter) {
		layout=tableSetter;
	}
	
	//Get Instance
	public static TableLayoutManager getInstance() {
        if (instance == null) {
            instance = new TableLayoutManager();
        }
        return instance;
    }
	
	//get layout
	public TableLayout getLayout(){
			return layout;
		}
	
	//returns table array index based on tableID
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
	
	//returns table based on tableID
	public Table getTable(int tableID) {
		for (Table table:layout.getTableLayout()) {
			if (table.getTableID()==tableID) {
				return table;
			}
		}
		return null;
	}
	
	//create table with capacity
	public void createTable(int tableID, int tableCapacity) {
		layout.getTableLayout().add(new Table(tableID,tableCapacity));
		System.out.println("Table " + tableID + " with capacity of "+tableCapacity+" added");
	}
	
	//remove table
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
	
	//occupies table
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
	
	//check table occupancy availability at current time
	public boolean isOccupiable(int tableID) {
		Table table=getTable(tableID);
		LocalDateTime time=LocalDateTime.now();
		int hour=time.getHour();
		int minute=time.getMinute();
		if(table.getHourBlock(hour)==status.EMPTY && minute==0) {
			return true;
		}
		if (table.getHourBlock(hour)==status.EMPTY && table.getHourBlock(hour+1)==status.EMPTY) {
			return true;
		}
		return false;
	}
	
	//updateTableStatus
	public void updateTableStatus(int tableID, LocalDateTime time,status newStatus) {
		Table table=getTable(tableID);
		int year=time.getYear();
		int day=time.getDayOfYear();
		int hour=time.getHour();
		if (day==LocalDateTime.now().getDayOfYear() && year==LocalDateTime.now().getYear()) {
			table.setHourBlock(hour,newStatus);
		}
	}
	
	//frees table of occupancy
	public void freeTableStatus(int tableID) {
		Table table=getTable(tableID);
		for (int i=0;i<24;i++) {
			if (table.getHourBlock(i)==status.OCCUPIED) {
				table.setHourBlock(i,status.EMPTY);
			}
		}
	}
	
	//get all tables
	public ArrayList<Table> getAllTables(){
		return layout.getTableLayout();
	}
	
	//get occupied tables
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
	
	//sorted list of tableIDs with minimum capacity for pax
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

	//saves instance to db
	public void saveDB(String saveFile){
		TableLayoutDatabase saver=new TableLayoutDatabase();
		try {
			saver.fwrite(saveFile);
		} catch (IOException e) {
			System.out.println("Failed to save to "+saveFile);
			return;
		}
	}
	
	//load db to array
	public void loadDB(String loadFile){
		TableLayoutDatabase loader=new TableLayoutDatabase();
		try {
			this.layout.setTableLayout(loader.fread(loadFile));
		} catch (IOException e) {
			System.out.println("Failed to load "+loadFile);
			return;
		}
		System.out.println("Loaded successfully from "+loadFile);
	}
	
}
