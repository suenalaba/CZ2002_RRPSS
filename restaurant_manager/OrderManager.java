package restaurant_manager;
import java.io.*;
import java.util.ArrayList;

import restaurant_database.OrderDatabase;
import restaurant_entity.MenuItem;
import restaurant_entity.Order;
/**
 * Stores Order Obejct data in ArrayList and has methods called by OrderApp to manipulate Order objects
 * @author	Jie Kai
 * @version 4.5
 * @since	13-11-2021
 */
public class OrderManager {
	/**
	 * For singleton pattern adherence. This OrderManager instance persists throughout runtime.
	 */
	private static OrderManager instance = null;
	/**
	 * Holds ArrayList of Order objects that can be referenced to throughout runtime.
	 */
	ArrayList<Order> orderList = new ArrayList<Order>();
	/**
	 * Default constructor for Order Manager.
	 */
	public OrderManager() {	
		orderList=new ArrayList<Order>();
	}
	/**
	 * For singleton pattern adherence. 
	 * @return instance The static instance that persists throughout runtime.
	 */
	public static OrderManager getInstance() {
		if (instance == null) {
	    	   instance = new OrderManager();
	           }
	       return instance;
    }
	/**
	 * Gets orderList Obect of OrderManager instance
	 * @return orderList which is an ArrayList of Order object.
	 */
	public ArrayList<Order> getOrderList(){
		return orderList;
	}
	/**
	 * Creates new order object added to orderList
	 * @param tableId tableID associated with order ordering items
	 * @param orderItems ArrayList of MenuItem objects 
	 * @param staffId The staffID of creator
	 */
	 public void createOrder(int tableId, ArrayList<MenuItem> orderItems, int staffId) {
		 	orderList.add(new Order(tableId, orderItems,staffId));
	    }	 
	 /**
	  * Prints all outstanding orders
	  */
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
	 /**
	  * Get Order object from orderID
	  * @param orderID the orderID of the Order object from orderList
	  * @return Order object if in orderList or null if does not exist in orderList
	  */
	 public Order getOrder(int orderID) {
		 for (Order o:orderList) {
			 if (o.getOrderID()==orderID) {
				 return o;
			 }
		 }
		 return null;
	 }
	 /**
	  * Deletes order according to orderID
	  * @param orderID OrderID of order to delete in orderList
	  */
	 public void deleteOrder(int orderID) {
		 int toDeleteIndex=orderIdToIndex(orderID);
		 orderList.remove(toDeleteIndex);
	 }
	 /**
	  * Updates order in orderList
	  * @param newOrderItems ArrayList of MenuItem under order
	  * @param orderID Identifies order to delete in orderList
	  */
	 public void updateOrder(ArrayList<MenuItem> newOrderItems, int orderID)
	 {
		 Order targetOrder=getOrder(orderID);
		 targetOrder.setOrderItems(newOrderItems);
		 System.out.println("\n\nOrder details updated");
	 }
	 /**
	  * prints Order attributes of outstanding Order object with corresponding tableID
	  * @param tableID the occupied table that has an outstanding order
	  */
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
	 /**
	  * Gets outstanding Orders
	  * @return unpaidOrderList ArrayList of Outstanding Orders
	  */
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
		/**
		 * Uses orderID to return index of Order in orderList that corresponds to orderId
		 * @param orderId the orderID of desired Order object inside orderList
		 * @return index of Order inside orderList or -1 if Order does not exist in orderList
		 */
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
		/**
		 * Uses tableID to get oustanding order object associated with it
		 * @param tableId of occupied table with order
		 * @return Order if there is an outstanding one associated with tableID otherwise null
		 */
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
		/**
		 * Updates isPaid attribute of Order object using OrderID as reference
		 * @param orderId of Order to update isPaid attribute of
		 */
		public void updatePaidStatus(int orderId) {
			for(int i=0; i<orderList.size(); i++)
			{
				if(orderList.get(i).getOrderID() == orderId)
				{
					orderList.get(i).setPaidStatus(true);
				}
			}
		}
		/**
		 * Saves the instance's orderList as string in a text file.
		 * @param textFileName The name of the the text file.
		 */
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
		/**
		 * Loads to instance's orderList from a text file
		 * @param textFileName The name of the text file.
		 */
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
