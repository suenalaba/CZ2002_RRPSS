package restaurant_manager;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_database.OrderDatabase;
import restaurant_database.StaffDatabase;
import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.Order;

public class OrderManager {
	
	//private static Order 
	//private static final String filename = "Order.txt";
	private static OrderManager instance = null;
	
	private static ArrayList<Order> orderList = new ArrayList<Order>();
	

	public OrderManager() {
		//empty array list to store orders
		orderList = new ArrayList<Order>();
	}
	
	
	
	//create new instance of OrderManager
//	 public static OrderManager getInstance() {
//	        if (instance == null) {
//	            instance = new OrderManager();
//	        }
//	        return instance;
//	    }
//	    
	 
//	public static Order getOrderInstance() { 
//		return;
//	}
	 //in main menu call create order query, update order query or delete whole order query.
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
		 		MenuItem newItem = MenuManager.getItem();
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
	 			
	 			String staffName=null;
				 
				 for(int k=0; k<StaffManager.getListOfStaffMembers().size(); k++)
				 {
					 if(StaffManager.getListOfStaffMembers().get(k).getStaffID()==orderList.get(i).getStaffId()) {
						 staffName = StaffManager.getListOfStaffMembers().get(k).getStaffName();
					 }
				 }
	 			
	 			System.out.format("orderID: %d          staffID: %d			staffName: %s\n", orderList.get(i).getOrderID(),orderList.get(i).getStaffId(),staffName);
	 		}
	 		
	 		
	 	}
	 
	 	
	 	//method to delete the whole entire order based on the order ID asked from staff input
	 	public static void deleteWholeOrderQuery() {
	 		System.out.println("Enter orderId you want to delete: ");
	 		displayOrderList();
	 		 Scanner sc = new Scanner(System.in);
	 		int orderIdToDelete = -1;
	 		//error handle if orderId entered not an integer. 
	 		
	 		
			
			while (orderIdToDelete==-1)
			{
				try {
					orderIdToDelete = sc.nextInt();
					sc.nextLine();
					
				}
				
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not an Integer. Try Again:");
				}
			}
			
	 		//check if orderId is in system and go to that particular order to delete from orderList (arraylist)
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
		 
		 int orderIdUpdate = -1;
		 //error handling if order id to update entered is not an integer. 
		
			
			while (orderIdUpdate==-1)
			{
				try {
					orderIdUpdate = sc.nextInt();
					sc.nextLine();
					
				}
				
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not an Integer. Try Again:");
				}
			}
		 
		 //check if orderId exists in orderlist and assign orderId to update to that order Id entered
		 for(int i=0; i<orderList.size(); i++ ) {
			 if(orderList.get(i).getOrderID() == orderIdUpdate)
			 {
				 orderIdUpdate = i;
				 break;
			 }
			 
		 }
		 
		 ArrayList<MenuItem> newOrderItems = orderList.get(orderIdUpdate).getOrderItems();
		 
		 System.out.println("Which do you want to update? TableID (1) or orderitems (2)?");
		 int choice = -1;
		 
		 //check for non integer input for choice of updating tableId or orderitems
		 
			
			while (choice==-1)
			{
				try {
					choice = sc.nextInt();
					sc.nextLine();
					
				}
				
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not an Integer. Try Again:");
				}
			}
		 
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
//				 deleteOrAdd = sc.nextInt();
//				 sc.nextLine();
				 
				
					deleteOrAdd=0;
					while (deleteOrAdd==0)
					{
						try {
							deleteOrAdd = sc.nextInt();
							sc.nextLine();
							
						}
						
						catch(InputMismatchException e) {
							sc.nextLine();
							System.out.println("Not an Integer. Try Again:");
						}
					}
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
					 MenuItem newItem = MenuManager.getItem();
					 
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
					break;
					 
				 }
			 }while(deleteOrAdd==1 || deleteOrAdd==2);
			
			
			 
			
		 }
		 
		 
		 else
		 {
			 return;
		 }
		 
		 	if(newTableId==-1)
		 	{
		 		newTableId = orderList.get(orderIdUpdate).getTableID();
		 	}
		 	printParticularOrder(newTableId);
		 	updateOrder(newTableId, newOrderItems, orderIdUpdate);
	 }
	 
	 
	 private static void updateOrder(int newTableId, ArrayList<MenuItem> newOrderItems, int updateIndex)
	 {
		 orderList.get(updateIndex).setTableID(newTableId);
		 orderList.get(updateIndex).setOrderItems(newOrderItems);
		 System.out.println("\n\nOrder details updated");
	 }
	    
	 
	 public static void displayOrderBasedOnTableIdQuery() {
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter tableID of order you want to display:");
		 
		 //check if tableID input valid. check if tableID is an occupied table
		 //check if tableID is occupied. 
		 ArrayList <Integer> occupiedTableId = new ArrayList<Integer>();
		 
		 
		 //check that table Id entered is valid
		 //add all tableIDs of occupied tables into arraylist occipiedTableId
		 for (int i=0; i<TableLayoutManager.getOccupiedTables().size() ; i++)
		 {
			 occupiedTableId.add(TableLayoutManager.getOccupiedTables().get(i).getTableID());
		 }
		 
		 int newTableId = -1;
		 
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
		 
		 printParticularOrder(newTableId);
	 }
	 
	 public static void printParticularOrder(int tableID) {
		 for(int i=0; i<orderList.size();i++)
		 {
			 
			 if(orderList.get(i).getTableID()==tableID && orderList.get(i).getPaidStatus()==false)
			 {
				 String staffName=null;
				 
				 for(int k=0; k<StaffManager.getListOfStaffMembers().size(); k++)
				 {
					 if(StaffManager.getListOfStaffMembers().get(k).getStaffID()==orderList.get(i).getStaffId()) {
						 staffName = StaffManager.getListOfStaffMembers().get(k).getStaffName();
					 }
				 }
				 //print details of order
				 System.out.println("OrderID  		TableNum		StaffId		StaffName");
				 System.out.println("=============================================================================");
				 
				 System.out.format("%d			%d 			%d	   	%s\n", orderList.get(i).getOrderID(), orderList.get(i).getTableID(), orderList.get(i).getStaffId(),staffName);
				 
				 System.out.println("OrderItems");
				 System.out.println("=============================================================================");
				 
				 
				 ArrayList<MenuItem> orderDuplicates = new ArrayList<MenuItem>();
				 System.out.println("Name           Price			Quantity");	
				 for(int j=0;j<orderList.get(i).getOrderItems().size(); j++)
				 {
					 if(orderDuplicates.contains(orderList.get(i).getOrderItems().get(j))){
						 continue;
					 }
					 MenuItem orderItem = orderList.get(i).getOrderItems().get(j);
					 
					 
					 System.out.format("%s		%.02f	 ", orderItem.getMenuItemName(),orderItem.getMenuItemPrice());
					 
					 int counter=1;
					 for(int k=j+1; k<orderList.get(i).getOrderItems().size(); k++)
					 {
						 if(orderItem==orderList.get(i).getOrderItems().get(k))
						 {
							 
							 
							 counter++;
						 }
						 
					 }
					 
					 System.out.format(" 		x " + counter + "\n");
					 
					 orderDuplicates.add(orderItem);
					 
				 }

			 }
		 }
	 }
	
		//return a list of all orders that haven't been paid
		public static ArrayList<Order> getUnpaidOrders() {

			
			ArrayList<Order> unpaidOrderList  = new ArrayList<Order>();
			
			for(int i=0; i<orderList.size(); i++)
			{
				if(orderList.get(i).getPaidStatus()==false)
				{
					unpaidOrderList.add(orderList.get(i));
				}
			}
			
			return unpaidOrderList;
		}
		
		
		
		
		public static int orderIdToIndex(int orderId) {
			
			
			for(int i=0; i<orderList.size(); i++)
			{
				if(orderList.get(i).getOrderID() == orderId)
				{
					return i;
				}
			}
			
			return -1;
			
		}
	 
		public static Order getOrderByTableId(int tableId) {
		     
		     
		     for(int i=0; i<orderList.size(); i++)
		     {
		       if(orderList.get(i).getTableID() == tableId)
		       {
		         return orderList.get(i);
		       }
		     }
		    return null;
		     
		   }

		//set paid status of order to paid given orderId from payment
		public static void updatePaidStatus(int orderId) {
			
			for(int i=0; i<orderList.size(); i++)
			{
				if(orderList.get(i).getOrderID() == orderId)
				{
					orderList.get(i).setPaidStatus(true);
				}
			}
		}

	    
	 	//save orders to order database
		public static void saveOrderDB(String saveFileName)  {
			
			try{
				OrderDatabase.fwrite(saveFileName,orderList);
			}
			catch(IOException e)
			{
				System.out.format("File %s write failed", saveFileName);
				return;
			}
			
			
		}
		//retrieve all orders from order database
		public static void loadOrderDB(String loadFileName) {
			try {
				orderList = OrderDatabase.fread(loadFileName);
			}
			
			catch(IOException e) {
				System.out.format("File %s read failed", loadFileName);
				return;
			}
		}
	    
	
}
