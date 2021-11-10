package restaurant_application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_manager.MenuManager;
import restaurant_manager.OrderManager;
import restaurant_manager.StaffManager;
import restaurant_manager.TableLayoutManager;

public class OrderApp {

	 public static void createOrderQuery() {
		 Scanner sc = new Scanner(System.in);
		 if (TableLayoutManager.getInstance().getTableLayout().size()==0) {
			 System.out.println("No Tables in restaurant for customers to dine. Returning to main menu.");
			 return;
		 }
		 if (TableLayoutManager.getOccupiedTables().size()==0) {
			 System.out.println("No Tables are occupied at the moment. Returning to main menu.");
			 return;
		 }
		 
		 TableLayoutManager.getInstance().printTableLayout();
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
	              
	              if(occupiedTableId.contains(tableId) && OrderManager.getOrderByTableId(tableId)!=null)
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
		 
		 OrderManager.createOrder(tableId, orderItems, staffId);
		 
	 }
	 
	 
	 
	 
	 
	//method to delete the whole entire order based on the order ID asked from staff input
	 	public static void deleteWholeOrderQuery() {
	 		 Scanner sc = new Scanner(System.in);
	 		int orderIdToDelete = -1;
	 		//error handle if orderId entered not an integer. 
	 		if (OrderManager.getOrderList().size()==0) {
	 			System.out.println("No orders so far. Returning to main menu.");
	 			return;
	 		}
	 		OrderManager.displayOrderList();
	 		System.out.println("Enter orderId you want to delete: ");
	 		
	 		
			
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
	 		for(int i=0; i<OrderManager.getOrderList().size(); i++)
	 		{
	 			if(OrderManager.getOrderList().get(i).getOrderID()==orderIdToDelete)
	 			{
	 				int removalIndex = i;
	 				OrderManager.deleteWholeOrder(removalIndex);
	 				return;
	 			}
	 		}
	 		
	 		
	 	}
	 	
	 	
	 	//updateOrder
		 public static void updateOrderQuery() {
			 if (OrderManager.getOrderList().size()==0) {
		 			System.out.println("No orders so far. Returning to main menu.");
		 			return;
		 		}
			 OrderManager.displayOrderList();
			 System.out.println("Enter orderID that you want to update: ");
			 Scanner sc = new Scanner(System.in);
			 
			 //check if orderId entered exists in the system
			 ArrayList <Integer> existingOrderId = new ArrayList<Integer>();
			 
			 //pull out all existing orderId from orderlist and add to existingOrderId
			 for(int i=0; i<OrderManager.getOrderList().size();i++)
			 {
				 existingOrderId.add(OrderManager.getOrderList().get(i).getOrderID());
			 }
			 
			 int orderIdUpdate = -1;
			 //error handling if order id to update entered is not an integer. 
			
			 
				
				while (orderIdUpdate==-1)
				{
					try {
						orderIdUpdate = sc.nextInt();
						sc.nextLine();
						
						if(existingOrderId.contains(orderIdUpdate))
						{
							break;
							
						}
						
						else
						{
							orderIdUpdate=-1;
							System.out.println("Order Id entered is not in system. Please enter another one.");
						}
						
					}
					
					catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Not an Integer. Try Again:");
					}
				}
			 
			 // assign orderId to update to that order Id entered
			 for(int i=0; i<OrderManager.getOrderList().size(); i++ ) {
				 if(OrderManager.getOrderList().get(i).getOrderID() == orderIdUpdate)
				 {
					 orderIdUpdate = i;
					 break;
				 }
				 
			 }
			 
			 ArrayList<MenuItem> newOrderItems = OrderManager.getOrderList().get(orderIdUpdate).getOrderItems();
			 
				 
				 int deleteOrAdd = 0;
				 do {
					 System.out.format("Make a choice:\n 1. Delete Item\n 2. Add Item\n 3.Quit");
//					 deleteOrAdd = sc.nextInt();
//					 sc.nextLine();
					 
					
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
					 ArrayList<MenuItem> tempconvert= OrderManager.getOrderList().get(orderIdUpdate).getOrderItems();
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
				
				
				 
				int newTableId = OrderManager.getOrderList().get(orderIdUpdate).getOrderID();
			 
			 	OrderManager.printParticularOrder(newTableId);
			 	OrderManager.updateOrder(newTableId, newOrderItems, orderIdUpdate);
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 public static void displayOrderBasedOnTableIdQuery() {
			 if (OrderManager.getOrderList().size()==0) {
		 			System.out.println("No orders so far. Returning to main menu.");
		 			return;
		 		}
			 Scanner sc = new Scanner(System.in);
			 TableLayoutManager.getInstance().printTableLayout();
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
			 
			 OrderManager.printParticularOrder(newTableId);
		 }
}
