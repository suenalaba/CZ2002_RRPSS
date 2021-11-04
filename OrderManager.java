package OOP_Project_Package1;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import Database.OrderDB;

public class OrderManager {
	
	private static final String filename = "Order.txt";
	private static OrderManager instance = null;
	ArrayList<Order> orderList = new ArrayList<Order>();
	
	
	public OrderManager() {
		orderList = new ArrayList<Order>();
	}
	
	//create new instance of OrderManager
	 public static OrderManager getInstance() {
	        if (instance == null) {
	            instance = new OrderManager();
	        }
	        return instance;
	    }
	 
	 
	 public void orderChoice() {
		 MenuController.printMainMenu();
		 System.out.println("Select menu item number: ");
		 Scanner sc = new Scanner(System.in);
		 createOrder(order,sc.nextInt());
	 }
	 //method create new order item 
	 public void createOrder(Order order, int itemId) {
		 	//call menucontroller to print item
		 	
		 	
		 	
	        MenuItem menuItemName = MenuController.retrieveItem(itemId);
	        if (menuItemName != null) order.addItem(menuItemName);
	        else System.out.println("This item does not exist");
	    }
	 
	 //updateOrder
	 public void updateOrder(Order orderID) {
		 System.out.println("Key in 'D' to delete or 'A' to add this item to the order: ");
		 Scanner sc = new Scanner(System.in);
		 
		 char choice = sc.next().charAt(0);
		 
		 if(choice=='D')
			 orderList.remove(orderID);
		 //checkID();
		 
		 else if(choice=='A')
	        orderList.add(orderID);
		 else
		 {}
			 
	 }
	 
	 
	 /*
	  public void checkID() {
    	int id = 1;
		if(orderList!=null) {
			for(Order order : orderList){
				if(order.getOrderID() > id) id = order.getOrderID();
			}
		}
		Order.setRunningCount(id+1);
    }
    */
	 
	 
	


	  
	  
	  //view/ retrieve order
	    public Order retrieveOrder(int orderID) {
	        for (Order order : orderList) {
	            if (order.getOrderID() == orderID)
	                return order;
	        }
	        return null;
	    }
	  
	    public void displayOrder(int orderID) {
	        Order order;
	        order = retrieveOrder(orderID);
	        order.viewOrder();
	    }
	    
	    
	   
	    
	    
	 //retrieve all orders from database
	    public void loadinDB() {
	    	OrderDatabase orderdb = new OrderDatabase();
	        try {
				this.orderList = orderdb.read(filename);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    //save all orders to database
	    public void savetoDB() {
	    	OrderDatabase orderdb = new OrderDatabase();
	        try {
	        	orderdb.save(filename, orderList);
	        	
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	    }
	 
	 
	
}
