package restaurant_database;

//import java libraries to be updated as we go...
import java.util.ArrayList;
//import java.util.Date;
import java.io.IOException;
import java.util.StringTokenizer;
//import java.util.List;
//import other relevant classes..
import restaurant_entity.Payment;
import restaurant_manager.OrderManager;
import restaurant_manager.PaymentManager;
import restaurant_entity.Order;
//import restaurant_entity.MenuItem;
//import restaurant_entity.MenuItem.type;


/**
 * Subclass of DatabaseFunction
 * PaymentDatabase reads and write to paymentDB.txt
 * @author Joshua Khoo
 * @version 4.5
 * @since 2021-11-13
 *
 */
public class PaymentDatabase implements DatabaseFunction {
	
	/**
	 * DELIMITER to split tokens
	 */
	public static final String delimiter = ";";

	/**
	 * Reads data from paymentDB.txt to ArrayList<Payment>
	 * @param textfilename paymentDB.txt
	 * @retun paymentlist Arraylist of Payment class 
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	@Override
	public ArrayList<Payment> fread(String textfilename) throws IOException {
		OrderManager orderM=OrderManager.getInstance();
		ArrayList<String> fileasstring = (ArrayList<String>) FileRead.fread(textfilename);
		ArrayList<Payment> paymentlist = new ArrayList<Payment>();
		
		
		for (int i = 0; i < fileasstring.size(); i++) {
			String stringtoken = fileasstring.get(i);
			StringTokenizer str_tokenizer = new StringTokenizer(stringtoken, delimiter);
			Order orderTarget = null; //initialized, assigned values later.
			//paymentdate;subtotal;gst;servicecharge;memberdiscount;grandtotal;orderid;reservationnumber;tableid;membershipApplied
			
			String paymentdate = str_tokenizer.nextToken().trim();
			double subtotal = Double.valueOf(str_tokenizer.nextToken().trim());
			double gst = Double.valueOf(str_tokenizer.nextToken().trim());
			double servicecharge = Double.valueOf(str_tokenizer.nextToken().trim());
			double memberdiscount = Double.valueOf(str_tokenizer.nextToken().trim());
			double grandtotal = Double.valueOf(str_tokenizer.nextToken().trim());
			int orderid = Integer.valueOf(str_tokenizer.nextToken().trim());
			int reservationnumber = Integer.valueOf(str_tokenizer.nextToken().trim());
			int tableid = Integer.valueOf(str_tokenizer.nextToken().trim());
			boolean membershipapplied = Boolean.parseBoolean(str_tokenizer.nextToken().trim());

	    	
	    	/*
	    	ArrayList<MenuItem> menuitemlist = new ArrayList<MenuItem>();
	    	while(str_tokenizer.hasMoreTokens()) {
			    //int menuitemid = Integer.parseInt(str_tokenizer.nextToken().trim());
			    //if (menuitemid == -100) break;
	    		//input name;dex;price;type
			    String menuitemname = str_tokenizer.nextToken().trim();
			    String menuitemdesc = str_tokenizer.nextToken().trim();
			    double menuitemprice = Double.parseDouble(str_tokenizer.nextToken().trim());
			    type menuitemtype = type.valueOf(str_tokenizer.nextToken().trim());
			    MenuItem menuitem = new MenuItem(menuitemname, menuitemdesc, menuitemtype, menuitemprice);
			    //(String name,String description, type itemType,double price)
			    menuitemlist.add(menuitem); //need create new constructor in menuitem
			}
	    	Order order = new Order(tableid,menuitemlist,staffID); //(int tableID, ArrayList<MenuItem> orderItems,int staffId)
			orderlist.add(order);
			*/
	    	for(Order o : orderM.getOrderList()) {
	    		if(o.getOrderID() == orderid) {
	    			orderTarget = o;
	    		}
	    	}
	   
	    	Payment payment = new Payment(paymentdate, subtotal, gst, servicecharge, memberdiscount, grandtotal, orderTarget,
	        		reservationnumber, tableid, membershipapplied);
		//(Order order, boolean membershipApplied, int tableId, int reservationNumber)
		  paymentlist.add(payment);
		}
		return paymentlist;
	}
	
	
	/**
	 * Writes data from paymentM to paymentDB.txt
	 * @param textfilename paymentDB.txt
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	@Override
	public void fwrite(String textfilename) throws IOException {
		PaymentManager paymentM=PaymentManager.getInstance();
		ArrayList<String> fwritepayment = new ArrayList<String>();
		ArrayList<Payment> paymentlist = paymentM.getPaymentInvoices();
		
		
		
		for (int i = 0; i < paymentlist.size(); i++) { //amt of payment list size
			Payment payment = (Payment) paymentlist.get(i); //each payment get payment obj
			StringBuilder stringtoken = new StringBuilder(); //sequence of characters; mutable. Create a mutable string ready to store details
			//Order orderlist = payment.getOrder(); //get order for that payment
			//StringTokenizer st = new StringTokenizer("this is a test");
			
			
			//paymentdate;subtotal;gst;servicecharge;memberdiscount;grandtotal;orderid;reservationnumber;tableid;membershipApplied
			stringtoken.append(payment.getpaymentDate().toString());
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getSubTotal()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getGst()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getServiceCharge()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getMemberDiscount()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.grandTotal()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getOrder().getOrderID()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getreservationNumber()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getTableId()));
			stringtoken.append(delimiter);
			stringtoken.append(String.valueOf(payment.getmembershipApplied()));
			stringtoken.append(delimiter);

			fwritepayment.add(stringtoken.toString()); //convert stringbuilder obj to string
			
			
		}
		
		FileRead.fwrite(fwritepayment,textfilename);
		
		
	}
}