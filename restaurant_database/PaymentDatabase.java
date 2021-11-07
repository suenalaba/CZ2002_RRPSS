package restaurant_database;

//import java libraries to be updated as we go...
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
//import other relevant classes..
import restaurant_entity.Payment;
import restaurant_entity.Order;
import restaurant_entity.MenuItem;
import restaurant_entity.MenuItem.type;

public class PaymentDatabase implements DatabaseFunction {
	
	public static final String delimiter = ";";


	@Override
	public ArrayList<Payment> fread(String textfilename) throws IOException {
		ArrayList<String> fileasstring = (ArrayList<String>) FileRead.fread(textfilename);
		ArrayList<Payment> paymentlist = new ArrayList<Payment>();
		
		
		for (int i = 0; i < fileasstring.size(); i++) {
			String stringtoken = fileasstring.get(i);
			StringTokenizer str_tokenizer = new StringTokenizer(stringtoken, delimiter);
			ArrayList<Order> orderlist = new ArrayList<Order>();
			
			
			int paymentid = Integer.parseInt(str_tokenizer.nextToken().trim());
	    	int reservationnumber = Integer.parseInt(str_tokenizer.nextToken().trim());
	    	String paymentdate = str_tokenizer.nextToken().trim();
	    	boolean membershipapplied = Boolean.parseBoolean(str_tokenizer.nextToken().trim());
	    	int orderid = Integer.parseInt(str_tokenizer.nextToken().trim());
	    	int tableid = Integer.parseInt(str_tokenizer.nextToken().trim());

	    	ArrayList<MenuItem> menuitemlist = new ArrayList<MenuItem>();
	    	while(str_tokenizer.hasMoreTokens()) {
			    int menuitemid = Integer.parseInt(str_tokenizer.nextToken().trim());
			    //if (menuitemid == -100) break;
			    String menuitemname = str_tokenizer.nextToken().trim();
			    String menuitemdesc = str_tokenizer.nextToken().trim();
			    double menuitemprice = Double.parseDouble(str_tokenizer.nextToken().trim());
			    type menuitemtype = type.valueOf(str_tokenizer.nextToken().trim());
			    MenuItem menuitem = new MenuItem(menuitemid, menuitemname, menuitemdesc, menuitemprice, menuitemtype);
			    menuitemlist.add(menuitem); //need create new constructor in menuitem
			}
	    	Order order = new Order(tableid,menuitemlist); //need create in menu
			orderlist.add(order);
		  Payment payment = new Payment(paymentid, orderlist, membershipapplied, paymentdate);
		  paymentlist.add(payment);
		}
		System.out.println(fileasstring.size() + " File for Payment Database read");
		return paymentlist;
	}
	
	@Override
	public void fwrite(String textfilename, List paymentlist) throws IOException {
		ArrayList<String> fwritepayment = new ArrayList<String>();
		for (int i = 0; i < paymentlist.size(); i++) {
			Payment payment = (Payment) paymentlist.get(i);
			StringBuilder stringtoken = new StringBuilder();
			ArrayList<Order> orderlist = payment.getOrderList();
			stringtoken.append(Integer.toString(payment.getpaymentID()));
			stringtoken.append(delimiter);
			stringtoken.append(Integer.toString(payment.getreservationNumber()));
			stringtoken.append(delimiter);
			stringtoken.append(payment.getpaymentDate());
			stringtoken.append(delimiter);
			stringtoken.append(Boolean.toString(payment.getmembershipApplied()));
			stringtoken.append(delimiter);
			
			if (orderlist == null) {
				break;
			}
			else if(orderlist != null) {
				//Order order : orderlist;
				for (Order order : orderlist) {
					stringtoken.append(Integer.toString(order.getTableID()));
					stringtoken.append(delimiter);

					ArrayList<MenuItem> menuitemlist = order.getOrderItems();
					for(MenuItem menuitem : menuitemlist) {
						stringtoken.append(Integer.toString(menuitem.getMenuItemID()));
						stringtoken.append(delimiter);
						stringtoken.append(menuitem.getMenuItemName());
						stringtoken.append(delimiter);
						stringtoken.append(menuitem.getMenuItemDescription());
						stringtoken.append(delimiter);
						stringtoken.append(Double.toString(menuitem.getMenuItemPrice()));
						stringtoken.append(delimiter);
						stringtoken.append(menuitem.getMenuItemType().name());
						//check enum conversion
						//stringtoken.append(type.toString(type(menuitem.getMenuItemType())));
						stringtoken.append(delimiter);
					}
					//st.append("-1");
					//st.append(delimiter);
				}
			}

			fwritepayment.add(stringtoken.toString());
		}
		
		FileRead.fwrite(fwritepayment,textfilename);
	}
}