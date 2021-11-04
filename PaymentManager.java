package restaurant_manager;
import java.io.*;
import java.util.ArrayList;

//import restaurant_entity.Customer;
//import restaurant_entity.Order;
import restaurant_entity.Payment;
//import restaurant_entity.Reservation;
//import restaurant_database.PaymentDatabase;

public class PaymentManager{
    private static final double serviceCharge = 0.10;
    private static final double GST = 0.07;
	private static final String fileName = "Payment.txt";
	public static PaymentManager instance = null;
    ArrayList<Payment> paymentinvoices = new ArrayList<Payment>();

    public PaymentManager() {
    	paymentinvoices = new ArrayList<Payment>();
    }

    /**
	 * Creating new instance of payment controller
	 * 
	 * @return PaymentManager instance
	 */
    public static PaymentManager getInstance() {
        if (instance == null) instance = new PaymentManager();
        return instance;
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
    
    /**
	 * Retrieving payment id by customerID
	 * 
	 * @param customerID
	 * 				Specifies the reservation number to retrieve payment details
	 * @return Payment id if found. Else will return 0
	 */
    public int retrievepaymentID(int customerID){
    	for (Payment payment : paymentinvoices){
    		if(payment.getCustomer().equals(customerID)) {
    			return payment.getpaymentID();
    		}
    	}
    	return 0;
    }
    

    /**
	 * Retrieving payment subtotal
	 * 
	 * @param payment
	 * 				Specifies the payment
	 * @param orderID
	 * 				Specifies the room id
	 * @return Subtotal
	 */
    public double retrievetotalPayment(Payment payment, int orderID) {
    	double totalPayment = retrieveorderID(orderID);
    	ArrayList<Order> orders = payment.getOrders();
    	if(orders != null) {
            for(Order order : orders) {
            	for (MenuItem menuitem : order.getItems()) {
            		totalPayment += menuitem.getPrice();
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
	 * 				Specifies the payment
	 * @param orderID
	 * 				Specifies the room id
	 * @return total
	 */
    public double retrievepaymentafterTax(Payment payment, String orderID) {
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
	 * @param orderID?
	 * 				Specifies the room id
	 * @param paymentmode
	 * 				Specifies the method of payment
	 * @param cash?
	 * 				Specifies the cash amount
	 */
    
    /**
	 * Checking of payment id
	 * 
	 */
    public void checkinvoice() {
    	int id = 1;
		if(paymentinvoices!=null) {
			for(Payment payment : paymentinvoices){
				if(payment.getPaymentID() > id) id = payment.getPaymentID();
			}
		}
		Payment.setrunningCount(paymentID+1);
    }

    /**
	 * Retrieval of all payments
	 * 
	 */
    public void loadinDB() {
    	PaymentDatabase paymentdatabase = new PaymentDatabase();
        try {
			this.paymentinvoices = paymentdatabase.read(fileName);
			paymentdatabase.save(fileName, paymentinvoices);
			checkinvoice();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Saving of all payments
	 * 
	 */
    public void savetoDB() {
    	PaymentDatabase paymentdb = new PaymentDatabase();
        try {
			paymentdb.save(fileName, paymentinvoices);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }


    
    public void printReceipt(Payment payment, int orderID, int paymentmode, double cash) {
    	double total = retrievetotalPayment(payment, orderID);
    	double service_charge = total * serviceCharge;
    	double goods_service_tax = total * GST;
    	double total_paymentaftertax = retrievepaymentafterTax(payment, orderID);
    	Order o;
		try {
			o = OrderController.retrieveorderID(orderID);
			int customerid = Customer.getCustomerID();
			Customer customer = CustomerManager.retrieveCustomer(customerid);
			//CreditCard credit = CreditController.retrieveCreditByGuestId(gid);
			System.out.printf("                                     Payment                     #%s             \n", payment.getCustomer());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.printf("Customer Name: %s                                                                        \n", Customer.getCustomerName());
	    	System.out.println("---------------------------------------------------------------------------------");
	    	ArrayList<Order> orders = payment.getOrders();
	    	if(orders != null) {
		    	for(int i = 0; i < orders.size(); i++){
		    		System.out.printf("Room Service #%d                                                                 \n", i+1);
		        	System.out.println("---------------------------------------------------------------------------------");
		        	orders.get(i).viewOrder();
		    	}
	    	}
	        System.out.printf("SUBTOTAL                                                                %.2f\n", sTotal);
	        System.out.printf("10%% SVC CHG                                                             %.2f\n", svc);
	        System.out.printf("7%% GST                                                                  %.2f\n", gst);
	        System.out.println("---------------------------------------------------------------------------------");
	        System.out.printf("TOTAL                                                                   %.2f\n", total);
	        if(paymentmode == "cash") {
	        	System.out.printf("CASH                                                                    %.2f\n", cash);
	        	System.out.printf("CHANGE                                                                  %.2f\n", cash - total);
	        	System.out.println("---------------------------------------------------------------------------------");
	        }
	        else if (paymentmode == "creditcard") {
	        	System.out.println("---------------------------------------------------------------------------------");
	            System.out.printf("CARD TYPE                                                               %s\n", credit.getCreditCardType());
	            System.out.printf("CARD NUMBER                                                %s\n", credit.getCreditCardNumber());
	            System.out.printf("CARD EXPIRY                                                             %s\n", credit.getCreditCardExp());
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
