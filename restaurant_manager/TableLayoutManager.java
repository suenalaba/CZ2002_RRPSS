package restaurant_manager;
import restaurant_entity.Table;
import restaurant_entity.Table.status;
import restaurant_entity.TableLayout;
import restaurant_manager.ReservationManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import restaurant_entity.Reservation;

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
	


	public static void createReservation() {
		ArrayList<Table> tables = new ArrayList<>();
		tables = manager.getTableLayout();		
		System.out.println("Enter reservation date and time in the following format dd/MM/yyyy HH"); 
		Scanner sc = new Scanner(System.in);
		String reservationDate = sc.next(); 
		LocalDateTime resDate = toDateTimeFormat(reservationDate); 
		if(resDate.isBefore(LocalDateTime.now())) {
			System.out.println("Enter date after today!");
			return; 
		}
		System.out.println("Enter number of people"); 
		int numPax = sc.nextInt(); 
		if(numPax >10) {
			System.out.println("Group size too large");
			return;
		}
		for(int i = 0; i<tables.size(); i++) {
			HashMap<LocalDateTime, Reservation> tableReservation = new HashMap<>();
			tableReservation = tables.get(i).getReservations();
			if(tables.get(i).getTableCapacity()>= numPax && tableReservation.containsKey(resDate) == false) {
				System.out.println("Enter name of customer");
				String cusName = sc.next(); 
				System.out.println("Enter contact number");
				String cusPhoneNumber = sc.next(); 
				tableReservation.put(resDate, new Reservation(tables.get(i).getTableID(), numPax, cusName, cusPhoneNumber, resDate));
				tables.get(i).setReservations(tableReservation); 
				manager.setTableLayout(tables); 
				System.out.println("Table " + tables.get(i).getTableID() + " reserved for " + resDate);
				return;
			}
		}
		System.out.println("No available tables"); 
	}


	public static void removeReservation() {
		Scanner sc = new Scanner(System.in); 
		ArrayList <Table> tables = new ArrayList<>(); 
		tables = manager.getTableLayout(); 
		System.out.println("Enter tableID "); 
		Scanner sc = new Scanner(System.in);
		

		
		
		
	}
	
	
    private static LocalDateTime toDateTimeFormat(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
        return LocalDateTime.parse(dateString, formatter);
    }
}

/*
	private bool tableFree(LocalDateTime key) {
		
	}
}

/*
	
	public static void reserveTableQueury() {
		Scanner sc = new Scanner(System.in); 
		int tableID; 
		System.out.println("Enter table ID to be reserved"); 
		tableID = sc.nextInt(); 
		reserveTable(tableID); 
			
	}
	
	public static void reserveTable(int tableID) {
		int index = findTableIndex(tableID);
		if(index == -1) {
			System.out.println("Table does not exist");
		}
		else {
			ArrayList <Table> arr = new ArrayList<>();
			arr = manager.getTableLayout(); 
			if(arr.get(index).getReservationStatus() == false) {
				arr.get(index).setReservationStatus(true);
				manager.setTableLayout(arr); 
				System.out.println("Table " + tableID + " successfully reserved!");
			}
			else {
				System.out.println("Table " + tableID + " is already reserved" ); 
			}
		}
	}
	public static void changeReservationQuery() {
		Scanner sc = new Scanner(System.in); 
		int tableID; 
		System.out.println("Enter table ID of choice: ");
		tableID = sc.nextInt();
		int index = findTableIndex(tableID);
		if(index == -1) {
			System.out.println("Table does not exist!"); 
			return; 
		}
		else {
			changeReservationStatus(tableID); 
		}
		
	}
	
	public static void changeReservationStatus(int tableID) {
		ArrayList<Table> arr = new ArrayList<>();
		int index; 
		boolean status; 
		arr = manager.getTableLayout(); 
		index = findTableIndex(tableID); 
		status = arr.get(index).getReservationStatus(); 
		if(status == true) {
			arr.get(index).setReservationStatus(false);
			System.out.println("Table " + arr.get(index).getTableID() + " set to not reserved");
		}
		else {
			arr.get(index).setReservationStatus(true);
			System.out.println("Table " + arr.get(index).getTableID() + "set to reserved"); 
		}
		manager.setTableLayout(arr);; 
		
	}
	
	/*public void walkIn(int groupSize) {
		ArrayList<Table> availableTables = new ArrayList<>();
		ArrayList<Table> arr = new ArrayList<>();
		arr = manager.getTableLayout(); 
		Calendar c = new GregorianCalendar();
		c.add(Calendar.MINUTE, 60);
		int tableID, index;
		availableTables = getAvailableTables(); 
		if(availableTables.isEmpty() == true) {
			System.out.println("No available tables at the moment"); 
		}
		else {
			for(int i = 0; i<availableTables.size(); i++) {
				tableID = availableTables.get(i).getTableID(); 
				if( c.getTime() < resManager.checkReservation(tableID)) {
					index = findTableIndex(tableID); 
					arr.get(index).setReservationStatus(true);
					manager.setTableLayout(arr); 
					System.out.println("Assigned to table " + arr.get(index).getTableID()); 
					return ; 
				}
			}
			System.out.println("No available tables at the moment");		
			
			
		}
	}
	

}
*/