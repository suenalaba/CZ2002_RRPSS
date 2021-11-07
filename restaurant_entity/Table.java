package restaurant_entity;
import java.time.LocalDateTime;
import java.util.*;
public class Table {
	
	private int tableID; 
	private int tableCapacity; 
	private status tableStatus;
	private status[] hourBlock;
	
	public Table() {
	}
	
	public Table(int tableID, int tableCapacity) {
		this.tableID = tableID; 
		this.tableCapacity = tableCapacity; 
		this.tableStatus = status.EMPTY; 
		this.hourBlock = new status[13];
		for (int i=0;i<hourBlock.length;i++) {
			hourBlock[i]=status.EMPTY;
		}
	}
	
	public enum status{
		EMPTY, OCCUPIED, RESERVED
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
	public status getTableStatus(){
		LocalDateTime timeHolder=LocalDateTime.now();
		String time = timeHolder.toString().substring(11,13);
		int hour=Integer.parseInt(time);
		tableStatus=this.hourBlock[hour];
		return tableStatus; 
	}
	public void setTableStatus(status tableStatus) {
		this.tableStatus = tableStatus; 
	}
	public status[] getHourBlock(){
		return this.hourBlock;
	}
	public void setHourBlock(status[] hourBlock) {
		this.hourBlock= hourBlock; 
	}
}
// public class Table {
	
// 	private int tableID; 
// 	private int tableCapacity; 
// 	private status tableStatus;
// 	private HashMap<LocalDateTime, Reservation> reservations; 
	
// 	public Table() {
// 	}
	
// 	public Table(int tableID, int tableCapacity) {
// 		this.tableID = tableID; 
// 		this.tableCapacity = tableCapacity; 
// 		this.tableStatus = status.EMPTY; 
// 		this.reservations = new HashMap<>();
// 	}
	
// 	public enum status{
// 		EMPTY, OCCUPIED, RESERVED
// 	}
	
// 	public int getTableID() {
// 		return tableID; 
// 	}
// 	public void setTableID(int tableID) {
// 		this.tableID = tableID; 
// 	}
// 	public int getTableCapacity(){
// 		return tableCapacity; 
// 	}
// 	public void setTableCapacity(int tableCapacity) {
// 		this.tableCapacity = tableCapacity; 
// 	}
// 	public status getTableStatus(){
// 		return tableStatus; 
// 	}
// 	public void setTableStatus(status tableStatus) {
// 		this.tableStatus = tableStatus; 
// 	}
// 	public HashMap<LocalDateTime, Reservation> getReservations(){
// 		return this.reservations;
// 	}
// 	public void setReservations(HashMap<LocalDateTime, Reservation> reservations) {
// 		this.reservations= reservations; 
// 	}

// }
