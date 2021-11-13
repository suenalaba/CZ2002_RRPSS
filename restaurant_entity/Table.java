package restaurant_entity;

public class Table {
	private static int OPENING_TIME=9;
	private static int CLOSING_TIME=22;
	private int tableID; 
	private int tableCapacity; 
	private status[] hourBlock;
	
	public Table() {
	}
	
	public Table(int tableID, int tableCapacity) {
		this.tableID = tableID; 
		this.tableCapacity = tableCapacity; 
		this.hourBlock = new status[24];
		for (int i=0;i<hourBlock.length;i++) {
			hourBlock[i]=status.EMPTY;
		}
		for (int i=OPENING_TIME-1;i>=0;i--) {
			hourBlock[i]=status.CLOSED;
		}
		for (int i=CLOSING_TIME;i<24;i++) {
			hourBlock[i]=status.CLOSED;
		}
	}
	
	public Table(Table tableCopy) {
		this.tableID = tableCopy.getTableID(); 
		this.tableCapacity = tableCopy.getTableCapacity(); 
		this.hourBlock = tableCopy.getHourBlock();
		for (int i=0;i<hourBlock.length;i++) {
			hourBlock[i]=status.EMPTY;
		}
		for (int i=OPENING_TIME-1;i>=0;i--) {
			hourBlock[i]=status.CLOSED;
		}
		for (int i=CLOSING_TIME;i<24;i++) {
			hourBlock[i]=status.CLOSED;
		}
	}
	
	public enum status{
		EMPTY, OCCUPIED, RESERVED, CLOSED
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
	public status[] getHourBlock(){
		return this.hourBlock;
	}
	public status getHourBlock(int hour){
		return this.hourBlock[hour];
	}
	public void setHourBlock(status[] hourBlock) {
		this.hourBlock= hourBlock; 
	}
	public void setHourBlock(int hour,status tableStatus) {
		this.hourBlock[hour]=tableStatus;
	}
}
