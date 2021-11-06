package restaurant_manager;
import java.io.*;
import java.util.ArrayList;

//import restaurant_entity.Customer;
//import restaurant_entity.Order;
import restaurant_entity.Payment;
import restaurant_entity.Order;
import restaurant_entity.MenuItem;
import restaurant_database.PaymentDatabase;
import restaurant_entity.Customer;
import restaurant_manager.CustomerManager;
//import restaurant_entity.Reservation;
//import restaurant_database.PaymentDatabase;

//import other restaurant classes...

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
    	double totalPayment = 0;
    	ArrayList<Order> orderlist = payment.getOrderList();
    	if(orderlist != null) {
            for(Order order : orderlist) {
            	for (MenuItem menuitem : order.getMenuItemID()) { //i need a function to  get the list of order item
            		totalPayment += menuitem.getMenuItemPrice();
            	}
            }
    	}
        payment.settotalPayment(totalPayment);
        return totalPayment;
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


    
    public void printReceipt(Payment payment, int tableID, int orderID, String customerid, double amount_tendered) {
    	//double total = retrievetotalPayment(payment, orderID);
    	//double service_charge = total * serviceCharge;
    	//double goods_service_tax = total * GST;
    	double total_paymentaftertax = retrievepaymentafterTax(payment, orderID);
    	double total_paymentbeforetax = retrievetotalPayment(payment,orderID);
    	double service_charge = total_paymentbeforetax * serviceCharge;
    	double goods_service_tax = total_paymentbeforetax * GST;
    	//Table t;
		try {
			//o = OrderManager.retrieveorderID(orderID);
			//String customerid = Customer.getcustomerID();
			Customer customer = CustomerManager.retrieveCustomerbyIDinput(customerid);
			//CreditCard credit = CreditController.retrieveCreditByGuestId(gid);
			System.out.printf("                                     Date                      #%s              \n", payment.getpaymentDate());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.printf("                                     Payment                   #%d              \n", payment.gettableNumber());
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
	        System.out.printf("SUBTOTAL                                                                 %.2f\n", total_paymentbeforetax);
	        System.out.printf("10%% SVC CHG                                                             %.2f\n", service_charge);
	        System.out.printf("7%% GST                                                                  %.2f\n", goods_service_tax);
	        System.out.println("---------------------------------------------------------------------------------");
	        System.out.printf("TOTAL                                                                   %.2f\n", total_paymentaftertax);
	        System.out.printf("AMOUNT TENDERED                                                                    %.2f\n", amount_tendered);
	        System.out.printf("CHANGE                                                                  %.2f\n", amount_tendered - total_paymentaftertax);
	        System.out.println("---------------------------------------------------------------------------------");
		}
		finally {
			System.out.println("Payment Transaction Completed.");
		}	
			/*catch (IOException e) {
		}
		}*/
    }
    
    
} 