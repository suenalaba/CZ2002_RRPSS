package restaurant_entity;
/**
 * A class defining a table object.
 * @author yize
 * @version 4.5
 * @since 13-11-2021
 */
public class Table {
	private static final int OPENING_TIME=9; //declares integer hour where table starts to be available. 
	private static final int CLOSING_TIME=22; //declares integer hour where table starts to be unavailable.
	private int tableID; 
	private int tableCapacity; 
	private status[] hourBlock; //an array of type enum status. 
	
	/**
	 * Constructor. Creates a table object.
	 * @param int tableID
	 * @param tableCapacity
	 */
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
	/**
	 * Declare constants for enum status.
	 * enum status is used to track if table object is 
	 * currently empty, occupied, reserved, or closed
	 * at a given time. 
	 */
	public enum status{
		EMPTY, OCCUPIED, RESERVED, CLOSED
	}
	/**
	 * Get tableID of table object. Public method.
	 * @return integer tableID
	 */
	public int getTableID() {
		return tableID; 
	}
	/**
	 * Set TableID of table object. Public method.
	 * @param tableID
	 */
	public void setTableID(int tableID) {
		this.tableID = tableID; 
	}
	/**
	 * Get table capacity of table object. Public method.
	 * @return integer tableCapacity.
	 */
	public int getTableCapacity(){
		return tableCapacity; 
	}
	/**
	 * Set Table capacity of table object. Public method.
	 * @param integer tableCapacity
	 */
	public void setTableCapacity(int tableCapacity) {
		this.tableCapacity = tableCapacity; 
	}
	/**
	 * Get hourBlock of table object.
	 * @return an array, hourBlock, of type enum status. 
	 */
	public status[] getHourBlock(){  //hourBlock is a variable with type enum status.
		return this.hourBlock;
	}
	/**
	 * Get enum status hourBlock with corresponding to given integer hour. 
	 * @param hour
	 * @return hour block of table object
	 */
	public status getHourBlock(int hour){
		return this.hourBlock[hour];
	}
	/**
	 * Set enum status hourBlock (empty, occupied, reserved, or closed) of table object,
	 * corresponding to integer hour specified.
	 * @param hour
	 * @param tableStatus
	 */
	public void setHourBlock(int hour,status tableStatus) {
		this.hourBlock[hour]=tableStatus;
	}
}
