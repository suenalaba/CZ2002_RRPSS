package restaurant_entity;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import restaurant_manager.MenuManager;

public class OrderManager {
	
	//private static Order 
	//private static final String filename = "Order.txt";
	private static OrderManager instance = null;
	private static int runningCount = 0;
	private static ArrayList<Order> orderList = new ArrayList<Order>();
	
	
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
	 

	 //method create new order item 
	 public void createOrder(Order order, int itemId) {
		 int choiceStop;
	 		Scanner sc = new Scanner(System.in);
	 		ArrayList<MenuItem> tempOrder = new ArrayList<MenuItem>();
	 		
		 	do{
		 		System.out.println("Add item to order");
		 		MenuItem newItem = MenuManager.getItem();
		 		tempOrder.add(newItem);
		 		
		 		System.out.println("Want to add another item? 1 - yes, 2 - No ");
		 		choiceStop  = sc.nextInt();
		 		
		 		
		 	}while(choiceStop==1);
		 	
		 	//***let user key in the tableID for that order but check that the tableID keyed in is an occupied table
		 	System.out.println("Key in the table ID for this new order: ");
		 	
		 	//check if input for tableID is not an integer to force user to input an integer for tableID 
		 	while(!sc.hasNextInt())
		 	{
		 		System.out.println("TableID should be an integer. Please input again: ");
		 		
		 	}
		 	int tableID = sc.nextInt();
		 	int countCheck=0;
		 	//check with table manager if tableID keyed in is an occupied table, if it is NOT return
		 	/*
		 	 * 
		 	 do{
		 	 for(int i=0; i< TableManager.occupiedTables.size(); i++)
		 	 {
		 	 	if(TableManager.occupiedTables.get(i).getTableID() == tableID)
		 	 	{
		 	 	 	
		 	 	   //if table num keyed in exist in list of occupied tables. it should enter this if loop once.
		 	 	    int countCheck = 1;
		 	 	    break;
		 	 	    
		 	 	}
		 	 }
		 	 if(countCheck!=1)
		 	 {
		 	  	i=0;
		 	  	System.out.println("TableID entered is not an occupied table. Please enter again:");
		 	  	tableID = sc.nextInt();
		 	  	
		 	 }
		 	 
		 	}while(countCheck!=1)
		 	
		 	
		 	 */
		 	
		 	
		 	//?????????????????????????????????????????????????????????????????????????????????????????
		 	
		 	int createdBy = order.getCreatedBy();
		 	orderList.add(new Order(tableID, tempOrder,createdBy));
		 	
	      
	    }	 
	 
	 	public void displayOrderList() {
	 		System.out.println("List of orders (by orderID):");
	 		
	 		for(int i=0; i<orderList.size(); i++) {
	 			System.out.format("orderID:  %d \n", orderList.get(i).getOrderID());
	 		}
	 		
	 		
	 	}
	 
	 //updateOrder
	 public void updateOrder(Order orderID) {
		 
		 displayOrderList();
		 
		 System.out.println("Enter orderID that you want to update: ");
		 Scanner sc = new Scanner(System.in);
		 
		 int orderIdUpdate = sc.nextInt();
		 
		 for(int i=0; i<orderList.size(); i++ ) {
			 if(orderList.get(i).getOrderID() == orderIdUpdate)
			 {
				 orderIdUpdate = i;
				 break;
			 }
			 
			 
		 }
		 
		 System.out.println("Which do you want to update? TableID (1) or orderitems (2)?");
		 int choice = sc.nextInt();
		 
		 if(choice==1)
		 {
			 System.out.format("Your current tableID is %d.\n What is the new tableID you want to update to?\n", orderList.get(orderIdUpdate).getTableID());
		
			 
			 orderList.get(orderIdUpdate).setTableID(sc.nextInt());
			 
			 orderList.get(orderIdUpdate).viewOrder();
		 }
		 
		 else if(choice==2){
			 
			 do {
				 System.out.format("Make a choice:\n 1. Delete Item\n 2. Add Item");
				 int deleteOrAdd = sc.nextInt();
				 MenuItem[] tempconvert=(MenuItem[]) orderList.get(orderIdUpdate).getMenuItemID().toArray();
				 Menu orderItems=new Menu(tempconvert);
				 orderItems.printMenu();
				 
				 
				 switch(deleteOrAdd) {
				 case 1:
					 System.out.println("Enter index of order you want to remove: ");
					 int removeID = orderItems.ItemIDToIndex(sc.nextInt());
					 orderList.get(orderIdUpdate).getMenuItemID().remove(removeID);
					 
					 break;
				 case 2:
					 System.out.println("Enter index of order you want to add items to: ");
					 MenuItem newItem = MenuManager.getItem();
					 
					
					 for (int i=0; i<orderList.get(orderIdUpdate).getMenuItemID().size(); i++)
					 {
						 if(orderList.get(orderIdUpdate).getMenuItemID().get(i) == newItem)
						 {
							 int addIndex = i;
							 orderList.get(orderIdUpdate).getMenuItemID().add(addIndex,newItem);
						 }
					 }
					 
					 
						 
					 break;
				default:
					
					 
				 }
			 }while(choice==1 || choice==2);
			
			
			 orderList.get(orderIdUpdate).viewOrder();
			
		 }
		 
		 
		 else
		 {
			 return;
		 }
		 
		
			 
	 }
	 
	 
	 
	    
	    //retrieve all orders from database
	    public void loadinDB() {
	    	
	    }
	    
	    //save all orders to database
	    public void savetoDB() {
	    	
			
	    }
	 
	 
	
}
