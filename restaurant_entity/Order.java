package restaurant_entity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import restaurant_manager.TableLayoutManager;
//import OOP_Project_Package1.Staff.Gender;
public class Order {
	private static int runningCount = 1;
	
	private int orderID;
	private int tableID;
	private ArrayList<MenuItem> orderItems;
	private String orderTime;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	
	//staff ID of staff who created order????
	private int staffId;
	
	//Staff staff  = new Staff();
	//staffId = staff.getStaffID();
	
	private boolean isPaid; 
	
	

	
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
	
	
	public static int getRunningCount() {
		return runningCount;
	}
	
	public static void setRunningCount(int count) {
		runningCount = count;
	}
	
	//get array list of menu items inside the order
	public ArrayList<MenuItem> getOrderItems(){
		return orderItems;
		
	}
	
	public void setOrderItems(ArrayList<MenuItem> orderItems)
	{
		this.orderItems = orderItems;
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
    
    

    //get staffId who created the order.
    public int getStaffId() {
		return staffId;
	}
	    
	public void setStaffId(int staffId) {
	    this.staffId= staffId;
	}
	
	
	
	public boolean getPaidStatus() {
		return isPaid;
	}
	
	public void setPaidStatus(boolean isPaid) {
		this.isPaid = isPaid;
	}

//    public void viewOrder() {
//        System.out.println("ID  		TableNum 	 Date");
//        System.out.println(toString());
//        System.out.println("============================================================================================");
//        System.out.println("ID   Name of Item                          Description                          Price(S$)");
//        System.out.println("============================================================================================");
//        
//       //print all items in items arraylist
//        ArrayList<MenuItem> items = this.orderItems;
//        
//        for (int i=0; i<items.size(); i++) {
//        	items.get(i).printAll();
//        }
//        System.out.println("=============================================================================================");
//    }
//    
//    public String toString() {
//    	//TableLayoutManager.getOccupiedTables().
//        return (String.format("%-5d%-5d%-30s", orderID,tableID, orderTime));
//    }
	
	
}
