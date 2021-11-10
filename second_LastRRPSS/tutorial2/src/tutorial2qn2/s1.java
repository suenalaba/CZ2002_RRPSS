package tutorial2qn2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import group.Table;

public class s1 {

}

//return index if in array, else -1
public int findTableIndex(int tableID) {
	for(int i =0; i<arrayOfTables.size();i++) {
		if(arrayOfTables.get(i).getTableID() ==tableID) {
			return i;
		}
	}
	return -1; 
}

public void createTable(int tableID, int tableCapacity, boolean reservationStatus) {
	if(findTableIndex(tableID) == -1) {
		arrayOfTables.add(new Table(tableID, tableCapacity, reservationStatus));
	}
	else {
		System.out.println("Table already exists!"); 
	}
	
}

public void removeTable(int tableID) {
	int index = findTableIndex(tableID); 
	if(index == -1) {
		System.out.println("Table does not exist");
	}
	else {
		arrayOfTables.remove(index);
		System.out.println("Table " + tableID + " removed");
	}
}

public void getReservedTables() {
	System.out.println("Reserved Tables:");
	for(int i = 0; i<arrayOfTables.size(); i++) {
		if(arrayOfTables.get(i).getReservationStatus() == true) {
			System.out.println(arrayOfTables.get(i).getTableID()); 
		}
	}
	/*ArrayList<Table> reservedTables = new ArrayList<>(); 
	for(int i = 0; i<arrayOfTables.size(); i++) {
		if(arrayOfTables.get(i).getReservationStatus() == true) {
			reservedTables.add(arrayOfTables.get(i));
		}
	}
	return reservedTables; */
}

public void getAvailableTables() {
	System.out.println("Available Tables:");
	for(int i = 0; i<arrayOfTables.size(); i++) {
		if(arrayOfTables.get(i).getReservationStatus() == false) {
			System.out.println(arrayOfTables.get(i).getTableID());
		}
	}
	/*ArrayList<Table> availableTables = new ArrayList<>(); 
	for(int i = 0; i<arrayOfTables.size(); i++) {
		if(arrayOfTables.get(i).getReservationStatus() == false) {
			availableTables.add(arrayOfTables.get(i));
		}
	} 
	return availableTables;
	*/
}

public void reserveTable(int tableID) {
	int index = findTableIndex(tableID);
	if(index == -1) {
		System.out.println("Table does not exist");
	}
	else {
		arrayOfTables.get(index).setReservationStatus(true);
		System.out.println("Table " + tableID + " successfully reserved!");
	}
}


// check reservation
// number of pax 

public void walkIn(int groupSize) {
	ArrayList<Table> availableTables = new ArrayList<>();
	Calendar c = new GregorianCalendar();
	c.add(Calendar.MINUTE, 60);
	availableTables = getAvailableTables(); 
	if(availableTables.isEmpty() == true) {
		System.out.println("No available tables at the moment"); 
	}
	else {
		for(int i = 0; i<availableTables.size(); i++) {
			availableTables.ge
	}
	//check whether available tables have reservation 
		
		
	}

}