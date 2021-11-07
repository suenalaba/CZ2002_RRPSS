package restaurant_entity;

import java.util.Date; //remove
import java.util.ArrayList;
import java.text.SimpleDateFormat; //delete
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  
//import restaurant_entity.Order;
//import java.util.Calendar;
//import java.text.SimpleDateFormat;

public class Payment {
	private int paymentID;
	private String paymentDate;
	private double paymentafterTax;
	private double paymentbeforeTax;
	//private int customerID;
	//private int orderID;
	private Order order;
	private int reservationNumber;
	//private int tableNumber;
	boolean membershipApplied;
    private static int runningCount = 1;
    //private String date;
    //SimpleDateFormat date_formatting = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
    //int paymentId, ArrayList<Order> orders, String reservationNum, String date
    
    public Payment(int reservationNumber, Order order) {
    	this.paymentID = runningCount;
    	runningCount++;
    	//this.reservationNumber = reservationNumber;
    	//this.tableNumber = tableNumber;
        this.order = order;
        LocalDateTime datetimenow = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String paymentDate = datetimenow.format(format);
        this.paymentDate = paymentDate;
    }

    
    public Payment(int paymentID, Order order, boolean membershipApplied, String paymentDate) {
    	this.paymentID = runningCount;
    	//Calendar c = Calendar.getInstance();
    	//this.paymentDate = paymentDate;
    	//this.customerID = customerID;
        this.order = order;
        //this.reservationNumber = reservationNumber;
        //this.tableNumber = tableNumber;
        this.membershipApplied = membershipApplied;
        this.paymentDate = paymentDate;
        //String d = sdf.format(c.getTime());
        runningCount++;
    }
    
    public int getpaymentID() {
    	return paymentID;
    }
 // no need to set, settle by running count public void setpaymentID(
    public String getpaymentDate() {
    	return paymentDate;
    }
    
    public void setpaymentDate(String paymentDate) {
    	this.paymentDate = paymentDate;
    }
    
    public double getpaymentafterTax() {
    	return paymentafterTax;
    }
    
    public void setpaymentafterTax(double paymentafterTax) {
    	this.paymentafterTax = paymentafterTax;
    }
    
    public double getpaymentsbeforeTax() {
    	return paymentbeforeTax;
    }
    
    public void setpaymentsbeforeTax(double paymentbeforeTax) {
    	this.paymentbeforeTax = paymentbeforeTax;
    }

    /*public int getcustomerID() {
    	return customerID;
    }*/
    //no need set customerid, this is not done here, should be in customer class??
    
    /*public int getorderID() {
    	return orderID;
    }*/
    
    //no need set order id, this is not done here, should be in order class??
    
    public Order getOrder(){
    	return order;
    } //need order class
    
    public void setOrder(Order order) {
    	this.order = order; 
    }
    
    public int getreservationNumber() {
    	return reservationNumber;
    }//need reservation class
    
    /*public int gettableNumber() {
    	return tableNumber;
    }
    */
    public boolean getmembershipApplied () {
    	return membershipApplied;
    }
    
    public void setmembershipApplied(boolean membershipApplied) {
    	this.membershipApplied = membershipApplied;
    }

    public static int getrunningCount() {
    	return runningCount;
    }
    
    public static void setrunningCount(int paymentID) {
    	runningCount = paymentID;
    }

}
