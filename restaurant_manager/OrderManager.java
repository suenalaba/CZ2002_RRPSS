package restaurant_manager;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.Order;

public class OrderManager {
	
	//private static Order 
	//private static final String filename = "Order.txt";
	//private static OrderManager instance = null;
	//private static int runningCount = 0;
	private static ArrayList<Order> orderList = new ArrayList<Order>();
	
	
	public OrderManager() {
		//empty array list to store orders
		orderList = new ArrayList<Order>();
	}
	
	/*
	//create new instance of OrderManager
	 public static OrderManager getInstance() {
	        if (instance == null) {
	            instance = new OrderManager();
	        }
	        return instance;
	    }
	    
	 */
	 
	 //in main menu call create order query, updateorderquery or delete whole order query.
	 public static void createOrderQuery() {
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter table ID: ");
		 
		 int tableId = -1;
		 ArrayList <Integer> occupiedTableId = new ArrayList<Integer>();
		 
		 //add all tableIDs of occupied tables into arraylist occupiedTableId
		 for (int i=0; i<TableLayoutManager.getOccupiedTables().size() ; i++)
		 {
			 occupiedTableId.add(TableLayoutManager.getOccupiedTables().get(i).getTableID());
		 }
		 
		 //ensure input of tableID matches an occupiedtable.. 
		 while (tableId==-1) {
	            try {
	            	
	              tableId=sc.nextInt();
	              sc.nextLine();
	              
	              if(occupiedTableId.contains(tableId))
	              {
	            	  break;
	              }
	              
	              else
	              {
	            	  tableId=-1;
	            	  System.out.println("Table ID entered is NOT an occupied table. Input again: ");
	            	  
	              }
	              
	            }
	            catch(InputMismatchException e) {
	              sc.nextLine();
	              System.out.println("Not an Integer. Try Again:");
	            }
	          }
		 
		 	
		 	int choiceStop;
	 		
		 	
	 		ArrayList<MenuItem> orderItems = new ArrayList<MenuItem>();
	 		
	 		//add item to orderitems list
		 	do{
		 		System.out.println("Add item to order");
		 		MenuItem newItem = MenuManager.getItemQuery();
		 		orderItems.add(newItem);
		 		
		 		System.out.println("Want to add another item? 1 - yes, 2 - No ");
		 		choiceStop  = sc.nextInt();
		 		sc.nextLine();
		 		
		 		
		 	}while(choiceStop==1);
		 
		 	
		 	//check if staffID exists based on staffID staff inputs
		 	System.out.println("Enter your staffID: ");
		 int staffId =-1;
		 ArrayList <Integer> staffIdList = new ArrayList<Integer>();
		 
		 for(int i=0; i<StaffManager.getListOfStaffMembers().size(); i++)
		 {
			 staffIdList.add(StaffManager.getListOfStaffMembers().get(i).getStaffID());
		 }
		 while (staffId==-1) {
	            try {
	            	
	              staffId=sc.nextInt();
	              sc.nextLine();
	              
	              if(staffIdList.contains(staffId))
	              {
	            	  break;
	              }
	              
	              else
	              {
	            	  staffId=-1;
	            	  System.out.println("Staff ID entered is NOT in system. Input again: ");
	            	  
	              }
	              
	            }
	            catch(InputMismatchException e) {
	              sc.nextLine();
	              System.out.println("Not an Integer. Try Again:");
	            }
	          }
		 
		 
		 createOrder(tableId, orderItems, staffId);
	 }
	 //method create new order item 
	 private static void createOrder(int tableId, ArrayList<MenuItem> orderItems, int staffId) {
		 
		 	
		 	orderList.add(new Order(tableId, orderItems,staffId));
		 	
	      
	    }	 
	 
	 	public static void displayOrderList() {
	 		System.out.println("List of orders (by orderID):");
	 		
	 		for(int i=0; i<orderList.size(); i++) {
	 			System.out.format("orderID:  %d \n", orderList.get(i).getOrderID());
	 		}
	 		
	 		
	 	}
	 
	 	
	 	//method to delete the whole entire order based on the order ID asked from staff input
	 	public static void deleteWholeOrderQuery() {
	 		System.out.println("Enter orderId you want to delete: ");
	 		displayOrderList();
	 		 Scanner sc = new Scanner(System.in);
	 		int orderIdToDelete = sc.nextInt();
	 		
	 		sc.nextLine();
	 		
	 		for(int i=0; i<orderList.size(); i++)
	 		{
	 			if(orderList.get(i).getOrderID()==orderIdToDelete)
	 			{
	 				int removalIndex = i;
	 				deleteWholeOrder(removalIndex);
	 				return;
	 			}
	 		}
	 		
	 		
	 	}
	 	
	 	
	 	
	 	//delete whole order
	 	private static void deleteWholeOrder(int removalIndex) {
	 		orderList.remove(removalIndex);
	 	}
	 	
	 	
	 //updateOrder
	 public static void updateOrderQuery() {
		 
		 displayOrderList();
		 int newTableId=-1;
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
		 
		 ArrayList<MenuItem> newOrderItems = orderList.get(orderIdUpdate).getOrderItems();
		 
		 System.out.println("Which do you want to update? TableID (1) or orderitems (2)?");
		 int choice = sc.nextInt();
		 
		 if(choice==1)
		 {
			 System.out.format("Your current tableID is %d.\n What is the new tableID you want to update to?\n", orderList.get(orderIdUpdate).getTableID());
		
			 //check if tableID is occupied. 
			 ArrayList <Integer> occupiedTableId = new ArrayList<Integer>();
			 
			 
			 //check that table Id entered is valid
			 //add all tableIDs of occupied tables into arraylist occipiedTableId
			 for (int i=0; i<TableLayoutManager.getOccupiedTables().size() ; i++)
			 {
				 occupiedTableId.add(TableLayoutManager.getOccupiedTables().get(i).getTableID());
			 }
			 
			 newTableId = -1;
			 
			 while (newTableId==-1) {
		            try {
		            	
		              newTableId=sc.nextInt();
		              sc.nextLine();
		              
		              if(occupiedTableId.contains(newTableId))
		              {
		            	  break;
		              }
		              
		              else
		              {
		            	  newTableId=-1;
		            	  System.out.println("table Id entered is not occupied. Input again: ");
		            	  
		              }
		              
		            }
		            catch(InputMismatchException e) {
		              sc.nextLine();
		              System.out.println("Not an Integer. Try Again:");
		            }
		          }
		 }
		 
		 else if(choice==2){
			 
			 int deleteOrAdd = 0;
			 do {
				 System.out.format("Make a choice:\n 1. Delete Item\n 2. Add Item\n 3.Quit");
				 deleteOrAdd = sc.nextInt();
				 sc.nextLine();
				 ArrayList<MenuItem> tempconvert= orderList.get(orderIdUpdate).getOrderItems();
				 Menu orderItems=new Menu(tempconvert);
				 orderItems.printMenu();
				 
				 
				 
				
				 
				 switch(deleteOrAdd) {
				 case 1:
					 System.out.println("Enter index of order you want to remove: ");
					 
					 //run through array of menu list and check for item Id match before returning index of itemID
					 //false==>wont check deleted items in the menu
					 int removeID = orderItems.ItemIDToIndex(sc.nextInt(), false);
					 newOrderItems.remove(removeID);
					 
					 
					 
					 break;
				 case 2:
					 System.out.println("Enter index of order you want to add items to: ");
					 //returns menu items from Menu
					 MenuItem newItem = MenuManager.getItemQuery();
					 
					 //
					 int addedCheck=0;
					 //check if new item is already inside the order. 
					 for (int i=0; i<newOrderItems.size(); i++)
					 {
						 if(newOrderItems.get(i) == newItem)
						 {
							 //if item already exists, add another one beside each other
							 //add(index, item)
							 addedCheck=1;
							 int addIndex = i;
							 newOrderItems.add(addIndex,newItem);
							 break;
						 }
						 
					 }
					 
					 if(addedCheck==0)
					 {
						 //item not inside, append item to the end.
						 newOrderItems.add(newItem);
					 }
					 
					 
						 
					 break;
				default:
					
					 
				 }
			 }while(deleteOrAdd==1 || deleteOrAdd==2);
			
			
			 orderList.get(orderIdUpdate).viewOrder();
			
		 }
		 
		 
		 else
		 {
			 return;
		 }
		 
		 	if(newTableId==-1)
		 	{
		 		newTableId = orderList.get(orderIdUpdate).getTableID();
		 	}
			 updateOrder(newTableId, newOrderItems, orderIdUpdate);
	 }
	 
	 
	 private static void updateOrder(int newTableId, ArrayList<MenuItem> newOrderItems, int updateIndex)
	 {
		 orderList.get(updateIndex).setTableID(newTableId);
		 orderList.get(updateIndex).setOrderItems(newOrderItems);
	 }
	    
	    //retrieve all orders from database
	    public void loadinDB() {
	    	
	    }
	    
	    //save all orders to database
	    public void savetoDB() {
	    	
			
	    }
	 
	 
	
}
