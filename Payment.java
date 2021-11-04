package restaurant_entity;

import java.util.Date;
//import restaurant_entity.Order;
//import java.util.Calendar;
//import java.text.SimpleDateFormat;

public class Payment {
	private int paymentID;
	private Date paymentDate;
	private double paymentafterTax;
	private double totalPayment;
	private int customerID;
	private int orderID;
	private ArrayList<Order> orders;
	boolean membershipApplied;
    private static int runningCount = 1;
    //private String date;
    //SimpleDateFormat date_formatting = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
    
    public Payment(int orderID, int CustomerID, boolean membershipApplied) {
    	this.paymentID = runningCount;
    	//Calendar c = Calendar.getInstance();
    	//this.paymentDate = paymentDate;
    	//this.customerID = customerID;
        this.orderID = orderID;
        this.membershipApplied = membershipApplied;
        //String d = sdf.format(c.getTime());
        runningCount++;
    }
    
    public int getpaymentID() {
    	return paymentID;
    }
 // no need to set, settle by running count public void setpaymentID(
    public Date getpaymentDate() {
    	return paymentDate;
    }
    
    public void setpaymentDate(Date paymentDate) {
    	this.paymentDate = paymentDate;
    }
    
    public double getpaymentafterTax() {
    	return paymentafterTax;
    }
    
    public void setpaymentafterTax(double paymentafterTax) {
    	this.paymentafterTax = paymentafterTax;
    }
    
    public double gettotalPayment() {
    	return totalPayment;
    }
    
    public void settotalPayment(double totalPayment) {
    	this.totalPayment = totalPayment;
    }

    public int getcustomerID() {
    	return customerID;
    }
    //no need set customerid, this is not done here, should be in customer class??
    
    public int getorderID() {
    	return orderID;
    }
    
    //no need set order id, this is not done here, should be in order class??
    
    /*public ArrayList<Order> getOrders(){
    	return orders;
    }*/ //need order class
    
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
