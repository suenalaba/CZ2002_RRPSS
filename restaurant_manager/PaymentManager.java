package restaurant_manager;
import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//import restaurant_entity.Customer;
//import restaurant_entity.Order;
import restaurant_entity.Payment;
import restaurant_entity.Order;
import restaurant_entity.MenuItem;
import restaurant_database.PaymentDatabase;
import restaurant_entity.Customer;
import restaurant_manager.CustomerManager;
import restaurant_entity.Table;
import restaurant_entity.Table.status;
import restaurant_manager.ReservationManager;
import restaurant_entity.Reservation;
//import restaurant_entity.Reservation;
//import restaurant_database.PaymentDatabase;

//import other restaurant classes...
import restaurant_manager.TableLayoutManager;
public class PaymentManager{
    private static final double serviceCharge = 0.10;
    private static final double GST = 0.07;
	private static final String textfilename = "Payment.txt";
	public static PaymentManager paymentmanager = null;
    ArrayList<Payment> paymentinvoices = new ArrayList<Payment>();

    /**
	 * Creating new instance of payment Manager
	 * 
	 * @return PaymentManager instance
	 */
    public static PaymentManager getInstance() {
    	if (paymentmanager != null) {
    		return paymentmanager;
    	}
    	else if (paymentmanager == null) {
    		paymentmanager = new PaymentManager();
    	}
    	return paymentmanager;
    }

    
    
    public PaymentManager() {
    	paymentinvoices = new ArrayList<Payment>();
    }

    /**
	 * Creating new payment
	 * 
	 * @param payment
	 * 				Specifies the payment to be made
	 */
    public void createPayment(Payment payment) {
        paymentinvoices.add(payment);
    }
    //TODO
    /**
	 * Retrieving payment id by reservation number
	 * 
	 * @param reservationNum
	 * 				Specifies the reservation number to retrieve payment details
	 * @return Payment id if found. Else will return 0
	 */
    public int retrievePaymentId(String reservationNum){
    	for (Payment payment : paymentinvoices){
    		if(String.valueOf(payment.getreservationNumber()).equals(reservationNum)) {
    			return payment.getpaymentID();
    		}
    	}
    	return 0;
    }

    /**
	 * Retrieving total payment before taxes
	 * 
	 * @param payment
	 * 				Specifies the payment
	 * @param orderid
	 * 				Specifies the orderid
	 * @return total payment before tax
	 */
    public double retrievetotalPayment(Payment payment, int orderID) {
    	double paymentbeforeTax = 0;
    	ArrayList<Order> orderlist = payment.getOrderList();
    	if(orderlist != null) {
            for(Order order : orderlist) {
            	for (MenuItem menuitem : order.getOrderItems()) { //i need a function to  get the list of order item
            		paymentbeforeTax += menuitem.getMenuItemPrice();
            	}
            }
    	}
        payment.setpaymentsbeforeTax(paymentbeforeTax);
        return paymentbeforeTax;
    }
    
    /**
	 * Retrieving payment total price
	 * 
	 * @param payment
	 * 				Specifies the payment excluding tax
	 * @param roomId
	 * 				Specifies the order id
	 * @return total amount of payment after tax
	 */
    public double retrievepaymentafterTax(Payment payment, int orderID) {
    	double totalPayment = retrievetotalPayment(payment, orderID);
        double paymentafterTax = (1+ GST + serviceCharge) *totalPayment;
    	payment.setpaymentafterTax(paymentafterTax);
        return paymentafterTax;
    }
    
    /**
	 * Retrieving payment details for printing
	 * 
	 * @param payment
	 * 				Specifies the payment
	 * @param roomId
	 * 				Specifies the room id
	 * @param paymentmode
	 * 				Specifies the method of payment
	 * @param cash
	 * 				Specifies the cash amount
	 */
    
    /**
	 * Checking of payment id
	 * 
	 */
    public void checkinvoice() {
    	int paymentid = 1;
		if(paymentinvoices!=null) {
			for(Payment payment : paymentinvoices){
				if(payment.getpaymentID() > paymentid) paymentid = payment.getpaymentID();
			}
		}
		Payment.setrunningCount(paymentid+1);
    }

    /**
	 * Retrieval of all payments
	 * 
	 */
    public void retrieveallpaymentdetailsfromdatabase() {
    	PaymentDatabase paymentdatabase = new PaymentDatabase();
        try {
			this.paymentinvoices = paymentdatabase.fread(textfilename);
			paymentdatabase.fwrite(textfilename, paymentinvoices);
			checkinvoice();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Write all payment details to database
	 * 
	 */
    public void writeallpaymentdetailstodatabase() {
    	PaymentDatabase paymentdatabase= new PaymentDatabase();
        try {
			paymentdatabase.fwrite(textfilename, paymentinvoices);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }


    
    public void printReceipt(Payment payment, int tableID, int orderID,double amount_tendered) {

    	double total_paymentaftertax = retrievepaymentafterTax(payment, orderID);
    	double total_paymentbeforetax = retrievetotalPayment(payment,orderID);
    	double service_charge = total_paymentbeforetax * serviceCharge;
    	double goods_service_tax = total_paymentbeforetax * GST;
    	double discount = 0.00; //default no discount
		try {
			Reservation reservation = ReservationManager.checkReservationByTableID(tableID,payment.getpaymentDate());
			/*if (TableLayoutManager.getTableStatusNow(tableID) != status.OCCUPIED) {
				System.out.println("Payment for this table has already been made!");
				return;
			}*/
			String customerid = reservation.getCustomerID();
			Customer customer = CustomerManager.retrieveCustomerbyIDinput(customerid);
			System.out.printf("                                     Date:                     #%s              \n", payment.getpaymentDate());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.printf("                                     Table Number:             #%d              \n", reservation.getReservationID());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.printf("Customer Name: %s                                                               \n", customer.getcustomerName());
	    	System.out.println("---------------------------------------------------------------------------------");
	    	ArrayList<Order> orderlist = payment.getOrderList();
	    	if(orderlist != null) {
		    	for(int i = 0; i < orderlist.size(); i++){
		    		System.out.printf("Menu Item #%d                                                                 \n", i+1);
		        	System.out.println("---------------------------------------------------------------------------------");
		        	orderlist.get(i).viewOrder();
		    	}
	    	}
	        System.out.printf("TOTAL BEFORE TAX:                                                        %.2f\n", total_paymentbeforetax);
	        System.out.printf("10%% SVC CHG                                                             %.2f\n", service_charge);
	        System.out.printf("7%% GST                                                                  %.2f\n", goods_service_tax);
	        System.out.println("---------------------------------------------------------------------------------");
	        if (payment.getmembershipApplied() == true) {
	        	discount = 0.10 * total_paymentaftertax; //if membership discount present, 10% discount
	        	System.out.printf("10% MEMBERSHIP DISCOUNT                                              %.2f\n",discount);
	        }
	        System.out.printf("TOTAL                                                                   %.2f\n", total_paymentaftertax-discount);
	        System.out.printf("AMOUNT TENDERED                                                                    %.2f\n", amount_tendered);
	        System.out.printf("CHANGE                                                                  %.2f\n", amount_tendered - total_paymentaftertax);
	        System.out.println("---------------------------------------------------------------------------------");
		}
		finally {
			System.out.println("Payment Transaction Completed.");
			TableLayoutManager.freeTableStatus(tableID);
		}	
			/*catch (IOException e) {
		}
		}*/
    }
    
    
    public void printSaleReport() {
    	retrieveallpaymentdetailsfromdatabase();
    	System.out.println("(1) Print sale revenue report by day\n(2) Print sale revenue report by month");
    	Scanner sc = new Scanner(System.in); 
    	int choice, i;
    	String period; 
		double totalRevenueBeforeTax = 0;
		double totalRevenueAfterTax = 0;
    	try {
    		choice = sc.nextInt();
    		if(choice != 1 || choice != 2) {
    			System.out.println("Invalid input");
    			return; 
    		}
    	}
		catch(InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
    	switch(choice) {
    	case 1:
    		System.out.println("Enter day in following format dd/MM/yyyy"); 
    		period = sc.nextLine();
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    		try {
    			LocalDate date = LocalDate.parse(period, formatter);
    			if(LocalDate.now().isBefore(date)) {
    				System.out.println("Future date!");
    				break; 
    			}
    			
    		}
    		catch(DateTimeParseException exe) {
    			System.out.println("Invalid date"); 
    			break; 
    		} 
    		for (Payment payment : paymentinvoices) {
    			if(payment.getpaymentDate().equals(period)){
    				// prints out payment date and payment ID
    				System.out.println("Date: " + payment.getpaymentDate() + "       " + "Payment ID: " + payment.getpaymentID());
    				Order order = payment.getOrder(); 
    				ArrayList<MenuItem> orderItems = order.getOrderItems();
    				for(i =0; i<orderItems.size(); i++) { // prints out all orders, menu type and price without gst
    					System.out.println("Menu Item: " + orderItems.get(i).getMenuItemName() + "\t" + orderItems.get(i).getMenuItemType().name() +
    							"\t" + orderItems.get(i).getMenuItemPrice());
    				}
    				totalRevenueBeforeTax += payment.getpaymentsbeforeTax();
    				totalRevenueAfterTax += payment.getpaymentafterTax();  
    			}
    		}
    		// if == 0, no payment found
    		if(totalRevenueBeforeTax == 0) {
    			System.out.println("No payment records found");
    		}
    		else {
    			System.out.println("Total revenue before tax: $" + totalRevenueBeforeTax);
    			System.out.println("Total revenue after tax: $" + totalRevenueBeforeTax);
    		}
    		break; 
    		
    	case 2: 
    		System.out.println("Enter the month and year in the following format MM/yyyy");
    		period = sc.nextLine();
    		DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM/yyyy");
    		try {
    			YearMonth date = YearMonth.parse(period, formatterMonth);
    			if(YearMonth.now().isBefore(date)) {
    				System.out.println("Future date!");
    				break; 
    			}
    			
    		}
    		catch(DateTimeParseException exe) {
    			System.out.println("Invalid date"); 
    			break; 
    		}
    		for (Payment payment : paymentinvoices) {
    			if(payment.getpaymentDate().startsWith(period, 3)){
    				// prints out payment date and payment ID
    				System.out.println("Date: " + payment.getpaymentDate() + "       " + "Payment ID: " + payment.getpaymentID());
    				Order order = payment.getOrder(); 
    				ArrayList<MenuItem> orderItems = order.getOrderItems();
    				for(i =0; i<orderItems.size(); i++) { // prints out all orders, menu type and price without gst
    					System.out.println("Menu Item: " + orderItems.get(i).getMenuItemName() + "\t" + orderItems.get(i).getMenuItemType().name() +
    							"\t" + orderItems.get(i).getMenuItemPrice());
    				}
    				totalRevenueBeforeTax += payment.getpaymentsbeforeTax();
    				totalRevenueAfterTax += payment.getpaymentafterTax();  
    			}
    		}
    		// if == 0, no payment found
    		if(totalRevenueBeforeTax == 0) {
    			System.out.println("No payment records found");
    		}
    		else {
    			System.out.println("Total revenue before tax: $" + totalRevenueBeforeTax);
    			System.out.println("Total revenue after tax: $" + totalRevenueBeforeTax);
    		}
    		break;     		
    		
    		
    		
    	}
    	
    }
    
    
} 
