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
import restaurant_entity.Reservation;
import restaurant_entity.Staff;

public class OrderManager {
	//Attributes
	private static OrderManager instance = null;	
	ArrayList<Order> orderList = new ArrayList<Order>();
	
	//Constructor
	public OrderManager() {	
		orderList=new ArrayList<Order>();
	}
	
	//Get Instance
	public static OrderManager getInstance() {
		if (instance == null) {
	    	   instance = new OrderManager();
	           }
	       return instance;
    }
	
	public ArrayList<Order> getOrderList(){
		return orderList;
	}
	public void setOrderList(ArrayList<Order> wholeOrderList) {
		orderList = wholeOrderList;
	}

	 //method create new order item 
	 public void createOrder(int tableId, ArrayList<MenuItem> orderItems, int staffId) {
		 
		 	
		 	orderList.add(new Order(tableId, orderItems,staffId));
		 	
	      
	    }	 
	 
	 public void displayOrderList() {
	 		System.out.println("List of orders (by orderID):");
	 		StaffManager staffM=StaffManager.getInstance();
	 		ArrayList<Order> unpaidOrders=getUnpaidOrders();
	 		for(int i=0; i<unpaidOrders.size(); i++) {
	 			
	 			String staffName=null;
				 
				 for(int k=0; k<staffM.getListOfStaffMembers().size(); k++)
				 {
					 if(staffM.getListOfStaffMembers().get(k).getStaffID()==unpaidOrders.get(i).getStaffId()) {
						 staffName = staffM.getListOfStaffMembers().get(k).getStaffName();
					 }
				 }
	 			
	 			System.out.format("orderID: %d          staffID: %d			staffName: %s\n", unpaidOrders.get(i).getOrderID(),unpaidOrders.get(i).getStaffId(),staffName);
	 		}
	 		
	 		
	 	}
	 
	 	//get order from order ID
	 public Order getOrder(int orderID) {
		 for (Order o:orderList) {
			 if (o.getOrderID()==orderID) {
				 return o;
			 }
		 }
		 return null;
	 }
	 	
	 public void deleteOrder(int orderID) {
		 int toDeleteIndex=orderIdToIndex(orderID);
		 orderList.remove(toDeleteIndex);
	 }
	 
	 //update Order
	 public void updateOrder(ArrayList<MenuItem> newOrderItems, int orderID)
	 {
		 Order targetOrder=getOrder(orderID);
		 targetOrder.setOrderItems(newOrderItems);
		 System.out.println("\n\nOrder details updated");
	 }
	    
	 public void printParticularOrder(int tableID) {
		 StaffManager staffM=StaffManager.getInstance();
		 for(int i=0; i<orderList.size();i++)
		 {
			 
			 if(orderList.get(i).getTableID()==tableID && orderList.get(i).getPaidStatus()==false)
			 {
				 String staffName=null;
				 
				 for(int k=0; k<staffM.getListOfStaffMembers().size(); k++)
				 {
					 if(staffM.getListOfStaffMembers().get(k).getStaffID()==orderList.get(i).getStaffId()) {
						 staffName = staffM.getListOfStaffMembers().get(k).getStaffName();
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
		public ArrayList<Order> getUnpaidOrders() {

			
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
		
		
		
		
		public int orderIdToIndex(int orderId) {
			
			
			for(int i=0; i<orderList.size(); i++)
			{
				if(orderList.get(i).getOrderID() == orderId)
				{
					return i;
				}
			}
			
			return -1;
			
		}
	 
		public Order getOrderByTableId(int tableId) {
		     
		     
		     for(int i=0; i<orderList.size(); i++)
		     {
		       if(orderList.get(i).getTableID() == tableId && orderList.get(i).getPaidStatus()==false)
		       {
		         return orderList.get(i);
		       }
		     }
		    return null;
		     
		   }

		//set paid status of order to paid given orderId from payment
		public void updatePaidStatus(int orderId) {
			
			for(int i=0; i<orderList.size(); i++)
			{
				if(orderList.get(i).getOrderID() == orderId)
				{
					orderList.get(i).setPaidStatus(true);
				}
			}
		}

	   
	 	//save orders to order database
		public void saveDB(String textFileName)  {
			
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
		public void loadDB(String textFileName) {
			try {
				OrderDatabase loader=new OrderDatabase();
				orderList = loader.fread(textFileName);
			}
			
			catch(IOException e) {
				System.out.println("Failed to load "+textFileName);
				return;
			}
			System.out.println("Loaded successfully from "+textFileName);
		}
	    
	
}
