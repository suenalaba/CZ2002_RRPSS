package group;

import java.util.ArrayList;

public class TableLayoutManager {
	private TableLayout manager; 
	
	public TableLayoutManager(TableLayout tableLayout) {
		this.manager = tableLayout;
	}
	
	public int findTableIndex(int tableID) {
		ArrayList<Table> arr = new ArrayList<>();
		arr = manager.getTableLayout();
		for(int i =0; i<arr.size(); i++) {
			if(arr.get(i).getTableID() == tableID) {
				return i;
			}
		}
		return -1;
	}
	
	public void createTable(int tableID, int tableCapacity, boolean reservationStatus) {
		if(findTableIndex(tableID) == -1) {
			ArrayList<Table> arr = new ArrayList<>();
			arr = manager.getTableLayout();
			arr.add(new Table(tableID, tableCapacity, reservationStatus));
			manager.setTableLayout(arr);
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
			ArrayList<Table> arr = new ArrayList<>();
			arr = manager.getTableLayout();
			arr.remove(index);
			manager.setTableLayout(arr);
			System.out.println("Table " + tableID + " removed");
		}
	}
	
	public ArrayList<Table> getReservedTables() {
		ArrayList<Table> reservedTables = new ArrayList<>();
		reservedTables = manager.getTableLayout();
		for(int i = 0; i<reservedTables.size(); i++) {
			if(reservedTables.get(i).getReservationStatus() == false) {
				reservedTables.remove(i);
				i--;
			}
		}
		return reservedTables; 
	}
	
	public ArrayList<Table> getAvailableTables() {
		ArrayList<Table> availableTables = new ArrayList<>(); 
		availableTables = manager.getTableLayout(); 
		for(int i = 0; i<availableTables.size(); i++) {
			if(availableTables.get(i).getReservationStatus() == true) {
				availableTables.remove(i);
				i--;
			}
		} 
		return availableTables;
	}
	
	public void reserveTable(int tableID) {
		int index = findTableIndex(tableID);
		if(index == -1) {
			System.out.println("Table does not exist");
		}
		else {
			ArrayList <Table> arr = new ArrayList<>();
			arr.get(index).setReservationStatus(true);
			manager.setTableLayout(arr); 
			System.out.println("Table " + tableID + " successfully reserved!");
		}
	}
	
	//public void walkIn() {
	//	
	//}
	
	

}