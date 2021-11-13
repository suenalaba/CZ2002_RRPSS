package restaurant_manager;
import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import restaurant_entity.Payment;
import restaurant_entity.MenuItem;
import restaurant_database.PaymentDatabase;
/**
 * Stores an ArrayList of payment objects during runtime and methods to manipulate them
 * @author	Joshua
 * @version	4.5
 * @since 	13-11-2021
 */
public class PaymentManager{
	/**
	 * Fixed Member discount
	 */
    private static final double MEMBER = 0.15; 
    /**
     * ArrayList of Payment objects stored during runtime
     */
	private static ArrayList<Payment> paymentInvoices = new ArrayList<Payment>();
	/**
	 * For singleton pattern adherence. This PaymentManager instance persists throughout runtime.
	 */
	private static PaymentManager instance = null;	
	/**
	 * Holds ArrayList of Payment objects that can referenced during runtime
	 */
	ArrayList<Payment> paymentInvoice = new ArrayList<Payment>();
	/**
	 * Default constructor for PaymentManager
	 */
	public PaymentManager() {	
		paymentInvoice=new ArrayList<Payment>();
	}
	/**
	 * For singleton pattern adherence. 
	 * @return instance The static instance that persists throughout runtime.
	 */
	public static PaymentManager getInstance() {
		if (instance == null) {
	    	   instance = new PaymentManager();
	           }
	       return instance;
    }
	/**
	 * Returns paymentInvoices
	 * @return paymentInvoices the Arraylist of Payment Objects
	 */
	public ArrayList<Payment> getPaymentInvoices(){
		return paymentInvoices;
	}    
	/**
	 * Adds to paymentInvoices Arraylist
	 * @param payment the Payment object to add
	 */
    public void addPayment(Payment payment) {
    	paymentInvoices.add(payment);
    } 
    /**
     * prints attributes of payment formatted
     * @param payment Payment object to draw the print attributes from
     */
    public void printReceipt(Payment payment) {


			System.out.printf("                                     Date:                     #%s              \n", payment.getpaymentDate());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.printf("                                     Table Number:             #%d              \n", payment.getTableId());
			System.out.println("---------------------------------------------------------------------------------");
		    System.out.printf("Menu Item                                                                \n");
		    System.out.println("---------------------------------------------------------------------------------");
		    payment.getOrder().viewOrder();
	        System.out.print("Sub-Total:                                                          ");
	        System.out.format("%.2f\n", payment.getSubTotal());
	        System.out.print("Service Charge (10%)                                                ");
	        System.out.format("%.2f\n", payment.getServiceCharge());
	        System.out.print("GST (7%)                                                            ");
	        System.out.format("%.2f\n", payment.getGst());
	        System.out.print("Member Discount (15%)                                               ");
	        System.out.format("-%.2f\n", payment.getMemberDiscount());
	        System.out.println("---------------------------------------------------------------------------------");
	        System.out.printf("Grand Total                                                        ");
	        System.out.format("$%.2f\n", payment.grandTotal());
	        System.out.println("---------------------------------------------------------------------------------");
	        

		}
    /**
     * Gets paymentDateTime at current LocalDateTime
     * @return LocalDateTime now formatted to pattern
     */
    public String getPaymentDateTime() {
	LocalDateTime paymentDate = LocalDateTime.now(); 
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	return paymentDate.format(formatter);
    }
    /**
     * Calculates the subtotal using MenuItem price
     * @param listOfMenuItems The MenuItems to sum up price from
     * @return subtotal before tax
     */
    public double calculateSubtotal(ArrayList<MenuItem> listOfMenuItems) {
    	double subTotal = 0;
    	for(int i= 0; i<listOfMenuItems.size(); i++) {
		subTotal += listOfMenuItems.get(i).getMenuItemPrice();
    	}
    	return subTotal; 
    }
    /**
     * Calculates membershipDiscount to be applied to subtotal
     * @param membershipApplied either true or false. if applied, then the member discount variable MEMBER will be applied to subtotal and returned otherwise return 0
     * @param subtotal the price before discount
     * @return member discount that is either 0 or MEMBER*subtotal
     */
    public double calculateMemberDiscount(boolean membershipApplied, double subtotal) {
    	double memberDiscount = 0; 
    	if(membershipApplied == true) {
    		memberDiscount = subtotal * MEMBER; 
    	}
    	return memberDiscount; 
    }
    /**
	 * Saves the instance's paymentInvoices as string in a text file.
	 * @param textFileName The name of the the text file.
	 */
    public void saveDB(String textFileName)  {
    	PaymentDatabase PayDB=new PaymentDatabase();
      try{
        PayDB.fwrite(textFileName);
      }
      catch(IOException e)
      {
    	  System.out.println("Failed to load "+textFileName);
			return;
      }
      
      
    }
    /**
	 * Loads to instance's paymentInvoices from a text file
	 * @param textFileName The name of the text file.
	 */
    public void loadDB(String textFileName) {
    	PaymentDatabase PayDB=new PaymentDatabase();
      try {
        paymentInvoices = PayDB.fread(textFileName);
      }
      
      catch(IOException e) {
    	  System.out.println("Failed to load "+textFileName);
			return;
      }
      System.out.println("Loaded successfully from "+textFileName);
    }
    
}    