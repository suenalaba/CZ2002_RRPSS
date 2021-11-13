package restaurant_entity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * A class defining an order object.
 * @author JieKai
 * @version 4.5
 * @since 13-11-2021
 */
public class Order {
	private static int runningCount = 1; //used to track number of Order objects.
	
	private int orderID;
	private int tableID; //Each order has a corresponding table (where the order is made).
	private ArrayList<MenuItem> orderItems; //an ArrayList containing a list of menu items objects.
	private String orderTime; //Time order is placed, in String.
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss"); //declare format of LocalDateTime.
	private int staffId; //Each order is made by a staff.
	private boolean isPaid; 

	/**
	 * Constructor. Used to create an order object.
	 * @param integer tableID
	 * @param orderItems ArrayList of menu items
	 * @param integerstaffId
	 */
	public Order(int tableID, ArrayList<MenuItem> orderItems,int staffId)
	{
		this.orderID = runningCount;
		this.tableID = tableID;
		this.orderItems = orderItems;
		Calendar c = Calendar.getInstance();
        String d = sdf.format(c.getTime());
        this.orderTime = d;
        this.staffId = staffId;
        this.isPaid = false;
		runningCount++;
	}
	/**
	 * Get existing runningCount(Integer). Public method.
	 * @return integer running count.
	 */
	public static int getRunningCount() {
		return runningCount;
	}
	/**
	 * Update existing variable runningCount to Count. Public method.
	 * @param integer count
	 */
	public static void setRunningCount(int count) {
		runningCount = count;
	}
	/**
	 * Get array list of menu items inside the order.
	 * @return orderItems ArrayList of menu items objects.
	 */
	public ArrayList<MenuItem> getOrderItems(){
		return orderItems;
	}
	/**
	 * Set array list of menu items inside the order.
	 * @param orderItems ArrayList containing Menu item objects.
	 */
	public void setOrderItems(ArrayList<MenuItem> orderItems)
	{
		this.orderItems = orderItems;
	}
	/**
	 * Get orderID of order object. Public method.
	 * @return integer orderID
	 */
	public int getOrderID() {
		return orderID;
	}
	/**
	 * Set orderID of order object. Public method.
	 * @param integer orderID
	 */
	public void setOrderID(int orderID) {
	    this.orderID = orderID;
	}
	/**
	 * Get tableID of order object. Public method.
	 * @return integer tableID
	 */
	public int getTableID() {
		return tableID;
	}
	/**
	 * Set tableID of order object. Public method.
	 * @param integer tableID
	 */
	public void setTableID(int tableID) {
	    this.tableID = tableID;
	}
	/**
	 * Get order time in string.
	 * @return String orderTime
	 */
	public String getOrderTime() {
        return orderTime;
    }
	/**
	 * Set order time of order object (String).  Public method.
	 * @param String orderTime
	 */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    /**
     * Get staffId of the staff who created the order.
     * @return integer staffId
     */
    public int getStaffId() {
		return staffId;
	}
	/**
	 * Set staffId of the staff who is creating the order.
	 * @param integer staffId
	 */
	public void setStaffId(int staffId) {
	    this.staffId= staffId;
	}
	/**
	 * Check if order is paid.
	 * @return boolean isPaid
	 */
	public boolean getPaidStatus() {
		return isPaid;
	}
	/**
	 * Set paid status of order. (boolean) Public method.
	 * If isPaid is true order is paid. If isPaid is false, order is not paid
	 * @param boolean isPaid
	 */
	public void setPaidStatus(boolean isPaid) {
		this.isPaid = isPaid;
	}
	/**
	 * Call this method to print order object details
	 */
    public void viewOrder() {
        System.out.println("ID  		TableNum 	 Date");
        System.out.println(toString());
        System.out.println("============================================================================================");
        System.out.println("ID   Name of Item                          Description                          Price(S$)");
        System.out.println("============================================================================================");
        
       //print all items in items arraylist
        ArrayList<MenuItem> items = this.orderItems;
        
        for (int i=0; i<items.size(); i++) {
        	items.get(i).printAll();
        }
        System.out.println("=============================================================================================");
    }
    /**
     * Call this method to return 
     * the order object's orderID, tableID, orderTime(LocalDateTime)
     * in a String.
     * Used in Order.viewOrder()
     */
    public String toString() {
    	//TableLayoutManager.getOccupiedTables().
        return (String.format("%-5d%-5d%-30s", orderID,tableID, orderTime));
    }
	
	
}
