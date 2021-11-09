package restaurant_manager;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import restaurant_entity.Staff;
//import other restaurant classes...
import restaurant_manager.TableLayoutManager;
public class PaymentManager{

    private static final double MEMBER = 0.15; 
	//private static final String textfilename = "Payment.txt";
	//public static PaymentManager paymentmanager = null;
    //ArrayList<Payment> paymentinvoices = new ArrayList<Payment>();

    
	private static ArrayList<Payment> paymentInvoices = new ArrayList<Payment>();
	
	public static ArrayList<Payment> getPaymentInvoices(){
		return paymentInvoices;
	}    
    public static void addPayment(Payment payment) {
    	paymentInvoices.add(payment);
    } 
    
    public static void printReceipt(Payment payment) {


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
    
    public static String getPaymentDateTime() {
	LocalDateTime paymentDate = LocalDateTime.now(); 
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	return paymentDate.format(formatter);
    }
    
    public static double calculateSubtotal(ArrayList<MenuItem> listOfMenuItems) {
    	double subTotal = 0;
    	for(int i= 0; i<listOfMenuItems.size(); i++) {
		subTotal += listOfMenuItems.get(i).getMenuItemPrice();
    	}
    	return subTotal; 
    }
    
    public static double calculateMemberDiscount(boolean membershipApplied, double subtotal) {
    	double memberDiscount = 0; 
    	if(membershipApplied == true) {
    		memberDiscount = subtotal * MEMBER; 
    	}
    	return memberDiscount; 
    }
    
  //save orders to order database
    public static void saveDB(String saveFileName)  {
    	PaymentDatabase PayDB=new PaymentDatabase();
      try{
        PayDB.fwrite(saveFileName);
      }
      catch(IOException e)
      {
    	  System.out.println("Failed to load "+saveFileName);
			return;
      }
      
      
    }
    //retrieve all orders from order database
    public static void loadDB(String loadFileName) {
    	PaymentDatabase PayDB=new PaymentDatabase();
      try {
        paymentInvoices = PayDB.fread(loadFileName);
      }
      
      catch(IOException e) {
    	  System.out.println("Failed to load "+loadFileName);
			return;
      }
    }
    
}    