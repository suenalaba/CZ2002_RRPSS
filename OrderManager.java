package OOP_Project_Package1;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import Database.OrderDB;

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
		 	
		 	
		 	int tableID = TableManager.getTableID();
		 	int reservationID = ReservationManager.getReservationID();
		 	int createdBy = order.getCreatedBy();
		 	orderList.add(new Order(tableID, tempOrder, reservationID,createdBy));
		 	
	      
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
					 
					
					 for (int i; i<orderList.get(orderIdUpdate).getMenuItemID().size(); i++)
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
