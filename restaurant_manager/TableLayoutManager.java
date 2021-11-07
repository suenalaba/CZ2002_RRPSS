package restaurant_manager;
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

package restaurant_manager;
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
	
	
	public static ArrayList<Table> getAllTables(){
		return mainLayout.getTableLayout();
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
	

	//For payment mainLayout
	public static void freeTableStatus(int tableID) {
		ArrayList<Table> tables = new ArrayList<>(); 
		tables = mainLayout.getTableLayout();
		int index = findTableIndex(tableID);
		tables.get(index).setTableStatus(status.EMPTY);
		mainLayout.setTableLayout(tables);
		System.out.println("Table " + tables.get(index).getTableID() + " set to empty"); 
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
			if (emptyPaxApt.get(i).getTableCapacity()<smallestCap) {
				smallestCap=emptyPaxApt.get(i).getTableCapacity();
				tableID=emptyPaxApt.get(i).getTableID();
			}
		}
		return tableID; //defaults to -1 if no empty table
	}
	
	public static void updateTable(int tableID, int hourlyTime,status newStatus) { //future status
		int updateIndex=findTableIndex(tableID);
		int Hour=hourlyTimeToIndex(hourlyTime);
		mainLayout.getTableLayout().get(updateIndex).getHourBlock()[Hour]=newStatus;
	}
	public static void updateTable(int tableID, status newStatus) { //current status
		int updateIndex=findTableIndex(tableID);
		mainLayout.getTableLayout().get(updateIndex).setTableStatus(newStatus);
	}
	
	public static status getTableStatusNow(int tableID) {
		int checkIndex=findTableIndex(tableID);
		return mainLayout.getTableLayout().get(checkIndex).getTableStatus();
	}
}



// public class TableLayoutManager {
// 	private static TableLayout manager = new TableLayout();
	
// 	public static int findTableIndex(int tableID) {
// 		ArrayList<Table> arr = new ArrayList<>();
// 		arr = manager.getTableLayout();
// 		for(int i =0; i<arr.size(); i++) {
// 			if(arr.get(i).getTableID() == tableID) {
// 				return i;
// 			}
// 		}
// 		return -1;
// 	}
// 	public static void createTableQuery() {
// 		Scanner sc=new Scanner(System.in);
// 		int tableID, tableCapacity; 
// 		System.out.println("Enter table ID of new table: ");
// 		tableID = sc.nextInt(); 
// 		if(findTableIndex(tableID) == -1) {
// 			System.out.println("Select desired table capacity: ");
// 			System.out.println("2/4/6/8/10");
// 			try {
// 			tableCapacity = sc.nextInt();
// 			}
// 			catch(InputMismatchException e) {
// 				System.out.println("Invalid input");
// 				return;
// 			}
// 			if(tableCapacity == 2 || tableCapacity == 4 || tableCapacity == 6 || tableCapacity == 8 || tableCapacity ==10) {
// 				createTable(tableID, tableCapacity); 
// 				System.out.println("Table " + tableID + " with table capacity of " + tableCapacity + " created");
// 				return;
// 			}
// 			else {
// 				System.out.println("Invalid table capacity");
// 				return;
// 			}
// 		}
// 		else{
// 			System.out.println("Table already exists!");
// 		}
// 	} 
	
// 	public static void createTable(int tableID, int tableCapacity) {
// 		ArrayList<Table> arr = new ArrayList<>();
// 		arr = manager.getTableLayout();
// 		arr.add(new Table(tableID, tableCapacity));
// 		manager.setTableLayout(arr);
// 	}
	
// 	public static void removeTableQuery() {
// 		Scanner sc = new Scanner(System.in); 
// 		int tableID; 
// 		System.out.println("Enter table ID of table to be removed");
// 		try {
// 		tableID = sc.nextInt();
// 		}
// 		catch(InputMismatchException e) {
// 			System.out.println("Invalid input");
// 			return;
// 		}
// 		removeTable(tableID); 
		
// 	}

// 	public static void removeTable(int tableID) {
// 		int index = findTableIndex(tableID); 
// 		if(index == -1) {
// 			System.out.println("Table does not exist");
// 		}
// 		else {
// 			ArrayList<Table> arr = new ArrayList<>();
// 			arr = manager.getTableLayout();
// 			arr.remove(index);
// 			manager.setTableLayout(arr);
// 			System.out.println("Table " + tableID + " removed");
// 		}
// 	}
// 	public static ArrayList<Table> getOccupiedTables(){
// 		ArrayList<Table> tables = new ArrayList<>(); 
// 		ArrayList<Table> occupiedTables = new ArrayList<>();
// 		tables = manager.getTableLayout(); 
// 		for(int i = 0; i<tables.size(); i++) {
// 			if(tables.get(i).getTableStatus() == status.OCCUPIED) {
// 				occupiedTables.add(tables.get(i)); 
// 			}
// 		}
// 		return occupiedTables; 
// 	}
	
// 	public static ArrayList<Table> getReservedTables(){
// 		ArrayList<Table> tables = new ArrayList<>(); 
// 		ArrayList<Table> reservedTables = new ArrayList<>();
// 		tables = manager.getTableLayout(); 
// 		for(int i = 0; i<tables.size(); i++) {
// 			if(tables.get(i).getTableStatus() == status.RESERVED) {
// 				reservedTables.add(tables.get(i)); 
// 			}
// 		}
// 		return reservedTables; 
// 	}

// 	public static ArrayList<Table> getEmptyTables(){
// 		ArrayList<Table> tables = new ArrayList<>(); 
// 		ArrayList<Table> emptyTables = new ArrayList<>();
// 		tables = manager.getTableLayout(); 
// 		for(int i = 0; i<tables.size(); i++) {
// 			if(tables.get(i).getTableStatus() == status.EMPTY) {
// 				emptyTables.add(tables.get(i)); 
// 			}
// 		}
// 		return emptyTables; 
// 	}
	
// 	public static ArrayList<Table> getAllTables(){
// 		  ArrayList<Table> tables = new ArrayList<>(); 
// 		  ArrayList<Table> allTables = new ArrayList<>();
// 		  tables = manager.getTableLayout(); 
// 		  for(int i = 0; i<tables.size(); i++) {
// 		    allTables.add(tables.get(i));  
// 		  }
// 		  return allTables; 
// 	}
	
// 	public static void printOccupiedTables() {
// 		ArrayList<Table> occupiedTables = new ArrayList<>();
// 		occupiedTables = getOccupiedTables();
// 		System.out.println("Occupied Tables: ");
// 		for(int i = 0; i<occupiedTables.size(); i++) {
// 			System.out.println("TableID : " + occupiedTables.get(i).getTableID() + " Table Capacity : " + occupiedTables.get(i).getTableCapacity());
// 		}
// 	}
	
// 	public static void printEmptyTables() {
// 		ArrayList<Table> emptyTables = new ArrayList<>();
// 		emptyTables = getEmptyTables();
// 		System.out.println("Empty Tables: ");
// 		for(int i = 0; i<emptyTables.size(); i++) {
// 			System.out.println("TableID : " + emptyTables.get(i).getTableID() + " Table Capacity : " + emptyTables.get(i).getTableCapacity());
// 		}
// 	}
	
// 	//For payment manager
// 	public static void changeTableStatus(int tableID) {
// 		ArrayList<Table> tables = new ArrayList<>(); 
// 		tables = manager.getTableLayout();
// 		int index = findTableIndex(tableID);
// 		tables.get(index).setTableStatus(status.EMPTY);
// 		manager.setTableLayout(tables);
// 		System.out.println("Table " + tables.get(index).getTableID() + " set to empty"); 
// 	}


// 	public static void createReservation() {
// 		ArrayList<Table> tables = new ArrayList<>();
// 		tables = manager.getTableLayout();		
// 		System.out.println("Enter reservation date and time in the following format dd/MM/yyyy HH"); 
// 		Scanner sc = new Scanner(System.in);
// 		String reservationDate = sc.nextLine(); 
// 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
// 		int numPax;
// 		try {
// 			LocalDateTime resDate = LocalDateTime.parse(reservationDate, formatter);
// 		}
// 		catch (DateTimeParseException exc) {
// 			System.out.println("Invalid date time format");
// 		}
// 		LocalDateTime resDate = LocalDateTime.parse(reservationDate, formatter);
// 		if(resDate.isBefore(LocalDateTime.now().plusDays(1))) {
// 			System.out.println("Enter date time 24hours later");
// 			return; 
// 		}
// 		System.out.println("Enter number of people"); 
// 		try {
// 			numPax = sc.nextInt();
// 		}
// 		catch(InputMismatchException e) {
// 			System.out.println("Invalid input");
// 			return;
// 		}
// 		if(numPax >10) {
// 			System.out.println("Group size too large");
// 			return;
// 		}
// 		for(int i = 0; i<tables.size(); i++) {
// 			HashMap<LocalDateTime, Reservation> tableReservation = new HashMap<>();
// 			tableReservation = tables.get(i).getReservations();
// 			if(tables.get(i).getTableCapacity()>= numPax && tableReservation.containsKey(resDate) == false) {
// 				System.out.println("Enter customer ID to continue ");
// 				String customerID = sc.nextLine(); 
// 				if(CustomerManager.retrieveGuestWithString(customerID) != null) {
// 					Customer customer = new Customer();
// 					customer = CustomerManager.retrieveGuestWithString(customerID);
// 					tableReservation.put(resDate, new Reservation(tables.get(i).getTableID(), numPax, customer.getCustomerName(), customer.getCustomerPhoneNumber, resDate));
// 					tables.get(i).setReservations(tableReservation);
// 					manager.setTableLayout(tables);
// 					System.out.println("Table " + tables.get(i).getTableID() + " reserved for " + resDate);
// 					return;
// 				}
// 				else {
// 					System.out.println("Customer ID not found!"); 
// 				}
// 			}
// 		}
// 		System.out.println("No available tables"); 
// 	}


// 	public static void removeReservation() {
// 		Scanner sc = new Scanner(System.in); 
// 		int tableID;
// 		int index;
// 		ArrayList <Table> tables = new ArrayList<>(); 
// 		tables = manager.getTableLayout(); 
// 		System.out.println("Enter tableID ");
// 		try {
// 		tableID = sc.nextInt();
// 		}
// 		catch(InputMismatchException e) {
// 			System.out.println("Invalid input");
// 			return;
// 		}
// 		try {
// 		index = findTableIndex(tableID);
// 		}
// 		catch(InputMismatchException e) {
// 			System.out.println("Invalid input");
// 			return;
// 		}
// 		if(index == -1) {
// 			System.out.println("Table does not exist");
// 		}
// 		else {
// 			HashMap<LocalDateTime, Reservation> reservations = new HashMap<>();
// 			reservations = tables.get(index).getReservations();
// 			System.out.println("Enter reservation date and time in the following format dd/MM/yyyy HH");
// 			String reservationDate = sc.nextLine(); 
// 			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
// 			try {
// 				LocalDateTime resDate = LocalDateTime.parse(reservationDate, formatter);
// 			}
// 			catch (DateTimeParseException exc) {
// 				System.out.println("Invalid date time format");
// 			}
// 			LocalDateTime resDate = LocalDateTime.parse(reservationDate, formatter);
// 			if(reservations.containsKey(resDate)) {
// 				reservations.remove(resDate);
// 				tables.get(index).setReservations(reservations);
// 				manager.setTableLayout(tables);
// 				System.out.println("Reservation removed");
// 			}
// 			else {
// 				System.out.println("Reservation not found");
// 			}
// 		}
		
// 	}
// }
	
// }
