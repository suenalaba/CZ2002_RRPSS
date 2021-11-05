package restaurant_entity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import OOP_Project_Package1.Staff.Gender;
public class Order {
	private static int runningCount = 1;
	
	private int orderID;
	private int tableID;
	private ArrayList<MenuItem> orderitems;
	private String orderTime;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	
	//staff ID of staff who created order????
	private int createdBy;
	
	//Staff staff  = new Staff();
	//createdBy = staff.getStaffID();
	
	public Order(int tableID, ArrayList<MenuItem> orderitems,int createdBy)
	{
		this.orderID = runningCount;
		this.tableID = tableID;
		this.orderitems = orderitems;
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
	

    public void viewOrder() {
        System.out.println("ID  		TableNum 	 Date");
        System.out.println(toString());
        System.out.println("===========================================================================");
        System.out.println("ID   Name of Item                          Description                          Price(S$)");
        System.out.println("===========================================================================");
        
        //review conversion of arraylist to array (may have problem). 
        MenuItem[] items = (MenuItem[]) this.orderitems.toArray();
        
        for (int i=0; i<items.length; i++) {
        	items[i].printAll();
        }
        System.out.println("=================================================================================");
    }
    
    public String toString() {
    	Table order = new Table(); 
    	int tableID = order.getTableID();
        return (String.format("%-5d%-5d%-30s", orderID,tableID, orderTime));
    }
	
	
}
