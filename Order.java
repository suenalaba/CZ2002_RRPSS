package OOP_Project_Package1;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



import OOP_Project_Package1.Staff.Gender;
public class Order {
	private static int runningCount = 1;
	
	private int orderID;
	private int tableID;
	private ArrayList<MenuItem> orderitems = new ArrayList<MenuItem>();
	private int reservationID;
	private String orderTime;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	
	//staff ID of staff who created order????
	private int createdBy;
	
	//Staff staff  = new Staff();
	//createdBy = staff.getStaffID();
	
	public Order(int orderID, int tableID, ArrayList<MenuItem> orderitems, int reservationID,int createdBy)
	{
		this.orderID = orderID;
		this.tableID = tableID;
		this.orderitems = orderitems;
		this.reservationID = reservationID;
		Calendar c = Calendar.getInstance();
        String d = sdf.format(c.getTime());
        this.orderTime = d;
        this.createdBy = createdBy;
		runningCount++;
	}
	
	
	public static int getRunningCount() {
		return runningCount;
	}
	
	public static void setRunningCount(int count) {
		runningCount = count;
	}
	
	public ArrayList<MenuItem> getMenuItemID(){
		return orderitems;
		
	}
	
	public int getOrderID() {
		return orderID;
	}
	    
	public void setOrderID(int orderID) {
	    this.orderID = orderID;
	}
	
	public int getTableID() {
		return tableID;
	}
	    
	public void setTableID(int tableID) {
	    this.tableID = tableID;
	}
	
	public int getReservationID() {
		return reservationID;
	}
	    
	public void setReservationID(int reservationID) {
	    this.reservationID = reservationID;
	}
	
	
	public String getOrderTime() {
        return orderTime;
    }
    
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    
    

    public int getCreatedBy() {
		return createdBy;
	}
	    
	public void setCreatedBy(int createdBy) {
	    this.createdBy= createdBy;
	}
	
	public void addItem(MenuItem item) {
        this.orderitems.add(item);
    }

    public boolean removeItem(MenuItem item) {
        for (MenuItem it : orderitems) {
            if (it.getItemId() == item.getItemId()) {
                this.orderitems.remove(it);
                return true;
            }
        }
        return false;
    }
    public void viewOrder() {
        System.out.println("ID  		TableNum 	 Date");
        System.out.println(toString());
        System.out.println("=================================================================================");
        System.out.println("ID   Name of Item                          Description                          Price(S$)");
        System.out.println("=================================================================================");
        for (MenuItem menuitem : orderitems) {
        	System.out.println(menuitem.toString());
        }
        System.out.println("=================================================================================");
    }
    
    public String toString() {

        return (String.format("%-5d%-30s", orderID, orderTime));
    }
	
	
}
