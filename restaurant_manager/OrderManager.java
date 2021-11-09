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
import restaurant_entity.Staff;

public class OrderManager {
	
	//private static Order 
	//private static final String filename = "Order.txt";
	private static OrderManager instance = null;
	
	private static ArrayList<Order> orderList = new ArrayList<Order>();
	
	
	public static  ArrayList<Order> getOrderList(){
		return orderList;
	}
	public static void setOrderList(ArrayList<Order> wholeOrderList) {
		orderList = wholeOrderList;
	}

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
	 

	 //method create new order item 
	 public static void createOrder(int tableId, ArrayList<MenuItem> orderItems, int staffId) {
		 
		 	
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
	 
	 	
	 	
	 	
	 	
	 	
	 	//delete whole order
	 	public static void deleteWholeOrder(int removalIndex) {
	 		orderList.remove(removalIndex);
	 	}
	 	
	 	
	 
	 
	 //update Order
	 public static void updateOrder(int newTableId, ArrayList<MenuItem> newOrderItems, int updateIndex)
	 {
		 orderList.get(updateIndex).setTableID(newTableId);
		 orderList.get(updateIndex).setOrderItems(newOrderItems);
		 System.out.println("\n\nOrder details updated");
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
		public static void saveDB(String textFileName)  {
			
			try{
				OrderDatabase saver=new OrderDatabase();
				saver.fwrite(textFileName);
			}
			catch(IOException e)
			{
				System.out.println("Failed to save to "+textFileName);
				return;
			}
			
			
		}
		//retrieve all orders from order database
		public static void loadDB(String textFileName) {
			try {
				OrderDatabase loader=new OrderDatabase();
				orderList = loader.fread(textFileName);
			}
			
			catch(IOException e) {
				System.out.println("Failed to load "+textFileName);
				return;
			}
		}
	    
	
}
