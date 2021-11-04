package group;

public class Table {
	private int tableID; 
	private int tableCapacity; 
	private boolean reservationStatus; 
	
	public Table() {
	}
	
	public Table(int tableID, int tableCapacity, boolean reservationStatus) {
		this.tableID = tableID; 
		this.tableCapacity = tableCapacity; 
		this.reservationStatus = false; 
	}
	
	public int getTableID() {
		return tableID; 
	}
	public void setTableID(int tableID) {
		this.tableID = tableID; 
	}
	public int getTableCapacity(){
		return tableCapacity; 
	}
	public void setTableCapacity(int tableCapacity) {
		this.tableCapacity = tableCapacity; 
	}
	public boolean getReservationStatus() {
		return reservationStatus; 
	}
	public void setReservationStatus(boolean reservationStatus) {
		this.reservationStatus = reservationStatus; 
	}

}

