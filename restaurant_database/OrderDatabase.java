package restaurant_database;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import restaurant_entity.Order;



public class OrderDatabase {
	public static final String DELIMITER = ";";
	
	public static ArrayList<Order> fread(String textfilename) throws IOException {

		ArrayList fileasstring = (ArrayList) FileRead.fread(textfilename);
		
		//array to store table
		ArrayList<Order> listOfOrders = new ArrayList<>();
		for (int i = 0; i < fileasstring.size(); i++) {
			String data = (String) fileasstring.get(i);
			// get individual 'fields' of the string separated by the delimiter ';'
			
			StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); // pass in the string to the string tokenizer
			int orderId = Integer.parseInt(str_tokenizer.nextToken().trim());
			int tableId = Integer.parseInt(str_tokenizer.nextToken().trim());
			
			
			//orderitems arraylist?????????????????????
			
			
			
			
			//ordertime
			//DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss")
			//Date orderTime = dateFormat.parse(str_tokenizer.nextToken().trim());
			String orderTime = str_tokenizer.nextToken().trim();
			
			int staffId = Integer.parseInt(str_tokenizer.nextToken().trim());
			boolean isPaid = Boolean.parseBoolean(str_tokenizer.nextToken().trim());
			Order order = new Order(orderId, tableId, , orderTime,staffId, isPaid);
			listOfOrders.add(order); 
		}
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
			orderstring.append();
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
