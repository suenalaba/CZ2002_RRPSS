package restaurant_database;

import java.io.IOException;
//import java.util.Date;
//import java.text.DateFormat;
//import java.text.Format;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.List;
import java.util.StringTokenizer;

import restaurant_entity.MenuItem;
import restaurant_entity.Order;
import restaurant_manager.MenuManager;
import restaurant_manager.OrderManager;



public class OrderDatabase implements DatabaseFunction {
	public static final String DELIMITER = ";";
	
	public ArrayList<Order> fread(String textfilename) throws IOException {
		ArrayList<String> fileasstring = (ArrayList<String>) FileRead.fread(textfilename);
		ArrayList<Order> listofOrders = new ArrayList<Order>();
		
		int biggestOrderID=0;
		for (int i = 0; i < fileasstring.size(); i++) {
			String data = (String) fileasstring.get(i);
			// get individual 'fields' of the string separated by the delimiter ';'
			StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); // pass in the string to the string tokenizer
			
			//orderid, tableid(int),orderItems(ArrayList<MenuItem>),orderTime(String),staffId(int),isPaid(Boolean)
			
			int orderId = Integer.parseInt(str_tokenizer.nextToken().trim());
			int tableId = Integer.parseInt(str_tokenizer.nextToken().trim());
			
			//orderItems arraylist
			StringTokenizer order_items_tokenizer = new StringTokenizer(str_tokenizer.nextToken().trim(),",");
			ArrayList<MenuItem> orderItems = new ArrayList<MenuItem>(); //an array list containing menuitem objs.
			
			while (order_items_tokenizer.hasMoreTokens()){
				//MenuManager.getItemFromAll() returns a menuitem obj
				//add each menu item obj into orderItem ArrayList<MenuItem>
				orderItems.add(MenuManager.getItemFromAll(Integer.parseInt(order_items_tokenizer.nextToken().trim()))); //get items all items from
			}
			
			//ordertime
			//DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss")
			//Date orderTime = dateFormat.parse(str_tokenizer.nextToken().trim());
			String orderTime = str_tokenizer.nextToken().trim();
			int staffId = Integer.parseInt(str_tokenizer.nextToken().trim());
			boolean isPaid = Boolean.parseBoolean(str_tokenizer.nextToken().trim());
			
			Order.setRunningCount(orderId);
			if (orderId>biggestOrderID) { //make sure order id is the biggest order id.
				biggestOrderID=orderId;
			}
			Order order = new Order(tableId,orderItems,staffId);
			order.setPaidStatus(isPaid);
			order.setOrderTime(orderTime);
			listofOrders.add(order); //add new order obj into existing arraylist<Order>
		}
		Order.setRunningCount(biggestOrderID+1);
		return listofOrders; 
	}
	
	public void fwrite(String textfilename) throws IOException {
		ArrayList<String> fwritepayment = new ArrayList<String>(); //Initialize new arraylist of String type. Used to store list of order objects in string.
		ArrayList<Order> orderlist = OrderManager.getOrderList();// get and store existing array list of Order objects 
		for (int i = 0; i < orderlist.size(); i++) {
			Order order = (Order) orderlist.get(i);
			StringBuilder orderstring = new StringBuilder(); //create mutable string
			
			//orderid, tableid(int),orderItems(ArrayList<MenuItem>),orderTime(String),staffId(int),isPaid(Boolean)
			
			//orderId
			orderstring.append(Integer.toString(order.getOrderID()));
			orderstring.append(DELIMITER);
			
			//tableid
			orderstring.append(Integer.toString(order.getTableID()));
			orderstring.append(DELIMITER);
			
			//orderitems
			//ArrayList<Integer> orderItemId = new ArrayList<Integer>(); //creates an arraylist with Integer type.
			for(int j=0; j<order.getOrderItems().size();j++) //for each item inside order, get orderID(integer) and convert to string.
			{
				orderstring.append(Integer.toString(order.getOrderItems().get(j).getMenuItemID()));
				orderstring.append(",");
				
			}
			
			//ordertime
			//Format formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
			orderstring.append(order.getOrderTime());
			orderstring.append(DELIMITER); 
			
			//staffId
			orderstring.append(Integer.toString(order.getStaffId()));
			orderstring.append(DELIMITER); 
			
			//isPaid (if order paid)
			orderstring.append(Boolean.toString(order.getPaidStatus()));
			orderstring.append(DELIMITER); 
			
			fwritepayment.add(orderstring.toString());
		}
		FileRead.fwrite(fwritepayment,textfilename);
	}
}
