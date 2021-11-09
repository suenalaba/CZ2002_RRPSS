package restaurant_database;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import restaurant_entity.MenuItem;
import restaurant_entity.Order;
import restaurant_manager.MenuManager;



public class OrderDatabase {
	public static final String DELIMITER = ";";
	
	public static ArrayList<Order> fread(String textfilename) throws IOException {

		ArrayList fileasstring = (ArrayList) FileRead.fread(textfilename);
		
		//array to store table
		ArrayList<Order> listOfOrders = new ArrayList<>();
		int biggestOrderID=0;
		for (int i = 0; i < fileasstring.size(); i++) {
			String data = (String) fileasstring.get(i);
			// get individual 'fields' of the string separated by the delimiter ';'
			
			StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); // pass in the string to the string tokenizer
			int orderId = Integer.parseInt(str_tokenizer.nextToken().trim());
			int tableId = Integer.parseInt(str_tokenizer.nextToken().trim());
			
			
			//orderitems arraylist?????????????????????
			
			StringTokenizer order_items_tokenizer = new StringTokenizer(str_tokenizer.nextToken().trim());
			ArrayList<MenuItem> orderItems=new ArrayList<MenuItem>();
			
			while (order_items_tokenizer.hasMoreTokens()){
				
				orderItems.add(MenuManager.getItemFromAll(Integer.parseInt(order_items_tokenizer.nextToken().trim())));
			}
			
			
			//ordertime
			//DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss")
			//Date orderTime = dateFormat.parse(str_tokenizer.nextToken().trim());
			String orderTime = str_tokenizer.nextToken().trim();
			
			int staffId = Integer.parseInt(str_tokenizer.nextToken().trim());
			boolean isPaid = Boolean.parseBoolean(str_tokenizer.nextToken().trim());
			Order.setRunningCount(orderId);
			if (orderId>biggestOrderID) {
				biggestOrderID=orderId;
			}
			Order order = new Order(tableId,orderItems,staffId);
			order.setPaidStatus(isPaid);
			order.setOrderTime(orderTime);
			listOfOrders.add(order); 
		}
		Order.setRunningCount(biggestOrderID+1);
		return listOfOrders; 
	}
	
	public static void fwrite(String textfilename, ArrayList<Order> listOfOrders) throws IOException {

		List orderlist = new ArrayList();// array list to store staffdata
		for (int i = 0; i < listOfOrders.size(); i++) {
			Order order = (Order) listOfOrders.get(i);
			StringBuilder orderstring = new StringBuilder();
			
			//orderId
			orderstring.append(Integer.toString(order.getOrderID()));
			orderstring.append(DELIMITER);
			
			//tableId
			orderstring.append(Integer.toString(order.getTableID()));
			orderstring.append(DELIMITER);
			
			//orderitems????????????????????????????????
			ArrayList<Integer> orderItemId = new ArrayList<Integer>();
			
			for(int j=0; j<order.getOrderItems().size();j++)
			{
				orderstring.append(Integer.toString(order.getOrderItems().get(j).getMenuItemID()));
				orderstring.append(",");
				
			}
			
			orderstring.append(DELIMITER); 
			
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
			
			
			
			
			
			orderlist.add(orderstring.toString());
		}
		FileRead.fwrite(orderlist,textfilename);
	}
}
