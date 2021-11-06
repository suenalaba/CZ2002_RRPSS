package group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import restaurant_entity.Table;
import restaurant_entity.TableLayout;
import restaurant_entity.Table.status;

public class TableLayoutManager {
	private static TableLayout manager = new TableLayout();
	
	public static int findTableIndex(int tableID) {
		ArrayList<Table> arr = new ArrayList<>();
		arr = manager.getTableLayout();
		for(int i =0; i<arr.size(); i++) {
			if(arr.get(i).getTableID() == tableID) {
				return i;
			}
		}
		return -1;
	}
	public static void createTableQuery() {
		Scanner sc=new Scanner(System.in);
		int tableID, tableCapacity; 
		System.out.println("Enter table ID of new table: ");
		tableID = sc.nextInt(); 
		if(findTableIndex(tableID) == -1) {
			System.out.println("Select desired table capacity: ");
			System.out.println("2/4/6/8/10");
			tableCapacity = sc.nextInt(); 
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
	
	public static void createTable(int tableID, int tableCapacity) {
		ArrayList<Table> arr = new ArrayList<>();
		arr = manager.getTableLayout();
		arr.add(new Table(tableID, tableCapacity));
		manager.setTableLayout(arr);
	}
	
	public static void removeTableQuery() {
		Scanner sc = new Scanner(System.in); 
		int tableID; 
		System.out.println("Enter table ID of table to be removed"); 
		tableID = sc.nextInt(); 
		removeTable(tableID); 
		
	}

	public static void removeTable(int tableID) {
		int index = findTableIndex(tableID); 
		if(index == -1) {
			System.out.println("Table does not exist");
		}
		else {
			ArrayList<Table> arr = new ArrayList<>();
			arr = manager.getTableLayout();
			arr.remove(index);
			manager.setTableLayout(arr);
			System.out.println("Table " + tableID + " removed");
		}
	}
	public static ArrayList<Table> getOccupiedTables(){
		ArrayList<Table> tables = new ArrayList<>(); 
		ArrayList<Table> occupiedTables = new ArrayList<>();
		tables = manager.getTableLayout(); 
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
		tables = manager.getTableLayout(); 
		for(int i = 0; i<tables.size(); i++) {
			if(tables.get(i).getTableStatus() == status.RESERVED) {
				reservedTables.add(tables.get(i)); 
			}
		}
		return reservedTables; 
	}

	public static ArrayList<Table> getEmptyTables(){
		ArrayList<Table> tables = new ArrayList<>(); 
		ArrayList<Table> emptyTables = new ArrayList<>();
		tables = manager.getTableLayout(); 
		for(int i = 0; i<tables.size(); i++) {
			if(tables.get(i).getTableStatus() == status.EMPTY) {
				emptyTables.add(tables.get(i)); 
			}
		}
		return emptyTables; 
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
	
}