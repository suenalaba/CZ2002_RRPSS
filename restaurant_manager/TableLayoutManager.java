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
	private static TableLayout mainLayout = new TableLayout();
	
	public static int findTableIndex(int tableID) { //returns array index according to TableIndex
		ArrayList<Table> arr = new ArrayList<Table>();
		arr = mainLayout.getTableLayout();
		for(int i =0; i<arr.size(); i++) {
			if(arr.get(i).getTableID() == tableID) {
				return i;
			}
		}
		return -1;
	}
	
	public static int hourlyTimeToIndex(int Hourlytime) {
		int[] hours=new int[13];
		int hourInc=9;
		for (int i=0;i<hours.length;i++) { //09 00 to 2100
			hours[i]=hourInc;
			hourInc++;
		}
		for (int i=0;i<hours.length;i++) {
			if (Hourlytime==hours[i]) {
				return i;
			}
		}
		return -1;
	}
	
	public static void createTableQuery() { //creates table with tableID and pax of 2 increment
		Scanner sc=new Scanner(System.in);
		int tableID, tableCapacity; 
		System.out.println("Enter table ID of new table: ");
		tableID = sc.nextInt(); 
		sc.nextLine();
		if(findTableIndex(tableID) == -1) { //if no existing tableID
			System.out.println("Select desired table capacity: ");
			System.out.println("2/4/6/8/10");
			try {
			tableCapacity = sc.nextInt();
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input");
				return;
			}
			if(tableCapacity == 2 || tableCapacity == 4 || tableCapacity == 6 || tableCapacity == 8 || tableCapacity ==10) {
				createTable(tableID, tableCapacity); 
				System.out.println("Table " + tableID + " with table capacity of " + tableCapacity + " created");
				return;
			}
			else {
				System.out.println("Invalid table capacity");
				return;
			}
		}
		else{
			System.out.println("Table already exists!");
		}
	} 
	
	private static void createTable(int tableID, int tableCapacity) {
		mainLayout.getTableLayout().add(new Table(tableID,tableCapacity));
	}
	
	public static void removeTableQuery() {
		Scanner sc = new Scanner(System.in); 
		int tableID; 
		System.out.println("Enter table ID of table to be removed");
		try {
			tableID = sc.nextInt();
			sc.nextLine();
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		removeTable(tableID);
		
	}

	private static void removeTable(int tableID) {
		int index = findTableIndex(tableID); 
		if(index == -1) {
			System.out.println("Table does not exist");
		}
		else {
			mainLayout.getTableLayout().remove(index);
			System.out.println("Table " + tableID + " removed");
		}
	}
	
	public static void updateTable(int tableID, int hourlyTime,status newStatus) { //future status
		int updateIndex=findTableIndex(tableID);
		int Hour=hourlyTimeToIndex(hourlyTime);
		mainLayout.getTableLayout().get(updateIndex).getHourBlock()[Hour]=newStatus;
	}
	public static void updateTable(int tableID, status newStatus) { //current status
		LocalDateTime timeHolder=LocalDateTime.now();
		String time = timeHolder.toString().substring(11,13);
		int hour=Integer.parseInt(time);
		hour=TableLayoutManager.hourlyTimeToIndex(hour);
		int updateIndex=findTableIndex(tableID);
		mainLayout.getTableLayout().get(updateIndex).setTableStatus(newStatus);
		if (hour==-1) {
			return;
		}
		else {
			mainLayout.getTableLayout().get(updateIndex).getHourBlock()[hour]=newStatus;
		}
	}
	
	//Get methods
	public static ArrayList<Table> getAllTables(){
		return mainLayout.getTableLayout();
	}
	
	public static TableLayout getInstance() {
		return mainLayout;
	}
	
	public static ArrayList<Table> getOccupiedTables(){
		ArrayList<Table> tables = new ArrayList<>(); 
		ArrayList<Table> occupiedTables = new ArrayList<>();
		tables = mainLayout.getTableLayout(); 
		for(int i = 0; i<tables.size(); i++) {
			if(tables.get(i).getTableStatus() == status.OCCUPIED) {
				occupiedTables.add(tables.get(i)); 
			}
		}
		return occupiedTables; 
	}
	
	
	
	public static ArrayList<Table> getReservedTables(){
		ArrayList<Table> tables = new ArrayList<>(); 
		ArrayList<Table> reservedTables = new ArrayList<>();
		tables = mainLayout.getTableLayout(); 
		for(int i = 0; i<tables.size(); i++) {
			if(tables.get(i).getTableStatus() == status.RESERVED) {
				reservedTables.add(tables.get(i)); 
			}
		}
		return reservedTables; 
	}
	
	public static ArrayList<Table> getReservedTables(int hour){//overloading for specific time slot
		int hourBlock=hourlyTimeToIndex(hour);
		ArrayList<Table> ReservedTables = new ArrayList<>();
		for (int i=0;i<mainLayout.getTableLayout().size();i++) {
			if (mainLayout.getTableLayout().get(i).getHourBlock()[hourBlock]==status.RESERVED)
				ReservedTables.add(mainLayout.getTableLayout().get(i));
		}
		return ReservedTables; 
	}
	

	public static ArrayList<Table> getEmptyTables(){
		ArrayList<Table> tables = new ArrayList<>(); 
		ArrayList<Table> emptyTables = new ArrayList<>();
		tables = mainLayout.getTableLayout(); 
		for(int i = 0; i<tables.size(); i++) {
			if(tables.get(i).getTableStatus() == status.EMPTY) {
				emptyTables.add(tables.get(i)); 
			}
		}
		return emptyTables; 
	}
	
	public static ArrayList<Table> getEmptyTables(int hour){//overloading for specific time slot
		int hourBlock=hourlyTimeToIndex(hour);
		ArrayList<Table> emptyTables = new ArrayList<>();
		for (int i=0;i<mainLayout.getTableLayout().size();i++) {
			if (mainLayout.getTableLayout().get(i).getHourBlock()[hourBlock]==status.EMPTY)
				emptyTables.add(mainLayout.getTableLayout().get(i));
		}
		return emptyTables; 
	}
	public static int getEmptyTableAtHour(int pax, int Hourlytime) { //return tableID of with apt start time and pax otherwise -1
		int Hour=hourlyTimeToIndex(Hourlytime);
		ArrayList<Table> emptyTables=getEmptyTables(Hourlytime);
		ArrayList<Table> emptyPaxApt=new ArrayList<Table>();
		for (int i=0;i<emptyTables.size();i++) {
			if (emptyTables.get(i).getTableCapacity()>=pax) {
				emptyPaxApt.add(emptyTables.get(i));
			}
		}
		int smallestCap=10;
		int tableID=-1;
		for (int i=0;i<emptyPaxApt.size();i++) {
			if (emptyPaxApt.get(i).getTableCapacity()<=smallestCap) {
				smallestCap=emptyPaxApt.get(i).getTableCapacity();
				tableID=emptyPaxApt.get(i).getTableID();
			}
		}
		return tableID; //defaults to -1 if no empty table
	}
	
	public static void freeTableStatus(int tableID) { //update current table to empty
		LocalDateTime timeHolder=LocalDateTime.now();
		String time = timeHolder.toString().substring(11,13);
		int hour=Integer.parseInt(time);
		hour=TableLayoutManager.hourlyTimeToIndex(hour);
		int updateIndex=findTableIndex(tableID);
		mainLayout.getTableLayout().get(updateIndex).setTableStatus(status.EMPTY);
		if (hour==-1) {
			return;
		}
		else {
			mainLayout.getTableLayout().get(updateIndex).getHourBlock()[hour]=status.EMPTY;
		}
	}
	
	public static status getTableStatusNow(int tableID) {
		int checkIndex=findTableIndex(tableID);
		return mainLayout.getTableLayout().get(checkIndex).getTableStatus();
	}
	
	
	public static ArrayList<Integer> getMinTableList(int pax) { //sorted list of tables with minimum capacity for pax
		int leastCap=11;
		ArrayList<Integer> capList=new ArrayList<Integer>();
		for (int i=2;i<=10;i+=2) {
			if (i>=pax) {
				capList.add(i);
			}
		}
 		ArrayList<Integer> minTables=new ArrayList<Integer>();
 		for (int k=0;k<capList.size();k++) {
 			for (int i=0;i<mainLayout.getTableLayout().size();i++) {
 				if (mainLayout.getTableLayout().get(i).getTableCapacity()==capList.get(k)) {//to sort
 					minTables.add(mainLayout.getTableLayout().get(i).getTableID());
 				}
 			}
 		}
 		return minTables;
	}
	
	public static void printOccupiedTables() {
		ArrayList<Table> occupiedTables = new ArrayList<>();
		occupiedTables = getOccupiedTables();
		System.out.println("Occupied Tables: ");
		for(int i = 0; i<occupiedTables.size(); i++) {
			System.out.println("TableID : " + occupiedTables.get(i).getTableID() + " Table Capacity : " + occupiedTables.get(i).getTableCapacity());
		}
	}
	
	public static void printEmptyTables() {
		ArrayList<Table> emptyTables = new ArrayList<>();
		emptyTables = getEmptyTables();
		System.out.println("Empty Tables: ");
		for(int i = 0; i<emptyTables.size(); i++) {
			System.out.println("TableID : " + emptyTables.get(i).getTableID() + " Table Capacity : " + emptyTables.get(i).getTableCapacity());
		}
	}
	public static void saveDB(String saveFile) throws IOException{
		if (mainLayout.getTableLayout().size()==0) {
			System.out.println("Nothing to save!");
			return;
		}
		TableLayoutDatabase.fwrite(saveFile,mainLayout.getTableLayout());
		System.out.println("Saved successfully to "+saveFile);
	}
	public static void loadDB(String loadFile) throws IOException{
		mainLayout.setTableLayout(TableLayoutDatabase.fread(loadFile));
		System.out.println("Loaded successfully from "+loadFile);
	}
	
}
