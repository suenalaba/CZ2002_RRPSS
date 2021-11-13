package restaurant_application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.Order;
import restaurant_manager.MenuManager;
import restaurant_manager.OrderManager;
import restaurant_manager.StaffManager;
import restaurant_manager.TableLayoutManager;

/**
 * define methods that interact with main RRPSS App and OrderManager to 
 create and order, delete an order, update an order details and display an order details
 * @author jiekai
 * @version 4.5
 * @since 13-11-2021
 */

public class OrderApp {

	/**
	 * creates an order
	 * An order should include the tableID, arraylist of order items and staff details(staff Id and name)
	 */
	 public void createOrderQuery() {
		 TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		 OrderManager orderM=OrderManager.getInstance();
		 StaffManager staffM=StaffManager.getInstance();
		 MenuManager menuM=MenuManager.getInstance();
		 Scanner sc = new Scanner(System.in);
		 if (TableLayoutManager.getInstance().getLayout().getTableLayout().size()==0) {
			 System.out.println("No Tables in restaurant for customers to dine. Returning to main menu.");
			 return;
		 }
		 if (tableLayoutM.getOccupiedTables().size()==0) {
			 System.out.println("No Tables are occupied at the moment. Returning to main menu.");
			 return;
		 }
		 
		 
		 TableLayoutManager.getInstance().getLayout().printTableLayout();
		 System.out.println("Enter table ID: ");
		 int tableId = -1;
		 ArrayList <Integer> occupiedTableId = new ArrayList<Integer>();
		 
		 //add all tableIDs of occupied tables into arraylist occupiedTableId
		 for (int i=0; i<tableLayoutM.getOccupiedTables().size() ; i++)
		 {
			 occupiedTableId.add(tableLayoutM.getOccupiedTables().get(i).getTableID());
		 }
		 
		 //ensure input of tableID matches an occupiedtable.. 
		 while (tableId==-1) {
	            try {
	            	
	              tableId=sc.nextInt();
	              sc.nextLine();
	              
	              if(occupiedTableId.contains(tableId) && orderM.getOrderByTableId(tableId)==null)
	              {
	            	  break;
	              }
	              
	              if(occupiedTableId.contains(tableId) && orderM.getOrderByTableId(tableId)!=null)
	              {
	            	  System.out.println("Table has already ordered, update order to change it");
	            	  return;
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
		 		MenuItem newItem = menuM.getItem();
		 		orderItems.add(newItem);
		 		
		 		System.out.println("Want to add another item? 1 - yes, 2 - No ");
		 		choiceStop  = sc.nextInt();
		 		sc.nextLine();
		 		
		 		
		 	}while(choiceStop==1);
		 
		 	
		 	//check if staffID exists based on staffID staff inputs
		 	System.out.println("Enter your staffID: ");
		 int staffId =-1;
		 ArrayList <Integer> staffIdList = new ArrayList<Integer>();
		 
		 for(int i=0; i<staffM.getListOfStaffMembers().size(); i++)
		 {
			 staffIdList.add(staffM.getListOfStaffMembers().get(i).getStaffID());
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
		 
		 orderM.createOrder(tableId, orderItems, staffId);
		 
	 }
	 
	 
	 
	 
	 
    /**
     *Delete a whole order based on the order ID input by staff
     */
	 	public void deleteWholeOrderQuery() {
	 		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
			 OrderManager orderM=OrderManager.getInstance();
	 		 Scanner sc = new Scanner(System.in);
	 		int orderIdToDelete = -1;
	 		//error handle if orderId entered not an integer. 
	 		if (TableLayoutManager.getInstance().getLayout().getTableLayout().size()==0) {
				 System.out.println("No Tables in restaurant for customers to have ordered from. Returning to main menu.");
				 return;
			 }
	 		if (orderM.getOrderList().size()==0) {
	 			System.out.println("No orders so far. Returning to main menu.");
	 			return;
	 		}
	 		orderM.displayOrderList();
	 		System.out.println("Enter orderId you want to delete: ");
			while (orderIdToDelete==-1)
			{
				try {
					orderIdToDelete = sc.nextInt();
					sc.nextLine();
					if (orderM.getOrder(orderIdToDelete)==null) {
						System.out.println("No such order exists. Returning to main menu.");
						return;
					}
					if (orderM.getOrder(orderIdToDelete).getPaidStatus()==true) {
						System.out.println("You may only select outstanding orders. returning to main menu.");
						return;
					}
				}
				
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not an Integer. Try Again:");
				}
			}
			
	 		//check if orderId is in system and go to that particular order to delete from orderList (arraylist)
	 		orderM.deleteOrder(orderIdToDelete);
	 		System.out.println("Order "+orderIdToDelete+" has been deleted. The associated table can order again.");
	 		
	 	}
	 	
	 	
	 	/**
	 	 * update an order details based on order Id and staff's choice to delete or add an item 
	 	 *  
	 	 */
		 public void updateOrderQuery() {
		 OrderManager orderM=OrderManager.getInstance();
		 MenuManager menuM=MenuManager.getInstance();
			 if (orderM.getOrderList().size()==0) {
		 			System.out.println("No orders so far. Returning to main menu.");
		 			return;
		 		}
			orderM.displayOrderList();
			 System.out.println("Enter orderID that you want to update: ");
			 Scanner sc = new Scanner(System.in);
			 
			 //check if orderId entered exists in the system
			 ArrayList <Integer> existingOrderId = new ArrayList<Integer>();
			 
			 //pull out all existing orderId from orderlist and add to existingOrderId
			 for(int i=0; i<orderM.getOrderList().size();i++)
			 {
				 if (orderM.getOrderList().get(i).getPaidStatus()==true) {
					 continue;
				 }
				 existingOrderId.add(orderM.getOrderList().get(i).getOrderID());
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
							System.out.println("Order Id entered is not in system. Returning to main menu.");
							return;
						}
						
					}
					
					catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Not an Integer. Try Again:");
					}
				}
			 
			 Order targetOrder=orderM.getOrder(orderIdUpdate);
			 
			 ArrayList<MenuItem> newOrderItems = targetOrder.getOrderItems();
			 
				 
				 int deleteOrAdd = 0;
				 do {
					 System.out.format("Make a choice:\n 1.Delete Item\n 2.Add Item\n 3.Quit");
					 
					
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
					 ArrayList<MenuItem> tempconvert= targetOrder.getOrderItems();
					 Menu orderItems=new Menu(tempconvert);
					 System.out.println("Current order:");
					 orderItems.printMenu();
					 
					 switch(deleteOrAdd) {
					 case 1:
						 System.out.println("Enter item ID of order you want to remove: ");
						 
						 //run through array of menu list and check for item Id match before returning index of itemID
						 //false==>wont check deleted items in the menu
						 int removeID = orderItems.ItemIDToIndex(sc.nextInt(), false);
						 if (removeID==-1) {
							 System.out.println("Item ID does not exist in your order.");
							 break;
						 }
						 newOrderItems.remove(removeID);
						 
						 break;
					 case 2:
						 System.out.println("Enter index of order you want to add items to: ");
						 //returns menu items from Menu
						 MenuItem newItem = menuM.getItem();
						 if (newItem==null) {
							 break;
						 }
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
				
			 	orderM.printParticularOrder(targetOrder.getTableID());
			 	orderM.updateOrder(newOrderItems, orderIdUpdate);
		 }
		 
		 
		 
		 
		 
		 
		 
		 /**
		  * display details of an order based on tableId input by staff
		  * Details of order include orderID, orderitems, price, quantity and details of staff who created the order
		  */
		 public void displayOrderBasedOnTableIdQuery() {
			 TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
			 OrderManager orderM=OrderManager.getInstance();
			 StaffManager staffM=StaffManager.getInstance();
			 MenuManager menuM=MenuManager.getInstance();
			 if (TableLayoutManager.getInstance().getLayout().getTableLayout().size()==0) {
				 System.out.println("No Tables in restaurant for customers to have ordered from. Returning to main menu.");
				 return;
			 }
			 if (orderM.getOrderList().size()==0) {
		 			System.out.println("No orders so far. Returning to main menu.");
		 			return;
		 		}
			 Scanner sc = new Scanner(System.in);
			 TableLayoutManager.getInstance().getLayout().printTableLayout();
			 System.out.println("Enter tableID of order you want to display:");
			 
			 //check if tableID input valid. check if tableID is an occupied table
			 //check if tableID is occupied. 
			 ArrayList <Integer> occupiedTableId = new ArrayList<Integer>();
			 
			 
			 //check that table Id entered is valid
			 //add all tableIDs of occupied tables into arraylist occipiedTableId
			 for (int i=0; i<tableLayoutM.getOccupiedTables().size() ; i++)
			 {
				 occupiedTableId.add(tableLayoutM.getOccupiedTables().get(i).getTableID());
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
		              else if (orderM.getOrderByTableId(newTableId)==null) {
		            	System.out.println("Table has not ordered yet. Returning to main menu.");
		            	return;
		              }
		              else
		              {
		            	  System.out.println("table Id entered is not occupied. Returning to main menu. ");
		            	  return;
		            	  
		              }
		              
		            }
		            catch(InputMismatchException e) {
		              sc.nextLine();
		              System.out.println("Not an Integer. Try Again:");
		            }
		          }
			 
			 orderM.printParticularOrder(newTableId);
		 }
}
