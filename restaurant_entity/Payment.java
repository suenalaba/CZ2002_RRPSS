package restaurant_entity;

public class Payment {
	
	private int paymentID;
	private String paymentDate;
	private double subTotal; // sum of menuItem price
	private double gst;
	private double serviceCharge; 
	private double memberDiscount; // only applicable to members and partners
	private double grandTotal; // total to be paid
	private Order order;
	private int reservationNumber;
	private int tableId;
	boolean membershipApplied;
	
    private static int runningCount = 1;
    final double GST = 0.07;
    final double SERVICE_CHARGE = 0.10; 
    final double DISCOUNT = 0.15; 

    
    public Payment(String paymentDate, double subTotal, double gst,
    		double serviceCharge, double memberDiscount, double grandTotal, Order order,
    		int reservationNumber, int tableId, boolean membershipApplied) {
    	
    	this.paymentID = runningCount;
    	runningCount++; 
    	this.paymentDate = paymentDate; 
    	this.subTotal = subTotal; 
    	this.gst = gst; 
    	this.serviceCharge = serviceCharge; 
    	this.memberDiscount = memberDiscount; 
    	this.grandTotal = grandTotal; 
    	this.order = order;
    	this.reservationNumber = reservationNumber; 
    	this.tableId = tableId; 
    	this.membershipApplied = membershipApplied; 
    	
    }

    public int getpaymentID() {
    	return paymentID;
    }
    // no need to set, settle by running count public void setpaymentID(
    public String getpaymentDate() {
    	return paymentDate;
    }
    
    public double getSubTotal() {
    	return subTotal;
    }
    
    public double getGst() {
    	return gst;
    }
    
    public double getServiceCharge() {
    	return serviceCharge;
    }
    
    public double getMemberDiscount() {
    	return memberDiscount; 
    }
    
    public double grandTotal() {
    	return grandTotal;
    }
    
    public Order getOrder(){
    	return order;
    } //need order class
    
    public void setOrder(Order order) {
    	this.order = order; 
    }
    
    public int getreservationNumber() {
    	return reservationNumber;
    }//need reservation class
    
    public int getTableId() {
    	return tableId; 
    }
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