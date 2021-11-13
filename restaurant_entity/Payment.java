package restaurant_entity;
/**
 * A class defining a payment object.
 * @author Josh
 * @version 4.5
 * @since 13-11-2021
 */
public class Payment {
	
	private int paymentID;
	private String paymentDate;
	private double subTotal; // sum of menuItem price
	private double gst;
	private double serviceCharge; 
	private double memberDiscount; // only applicable to members and partners
	private double grandTotal; // total to be paid
	private Order order; //Each payment is for a specific order. This stores the order object the payment is for.
	private int reservationNumber;
	private int tableId;
	boolean membershipApplied;
	
    private static int runningCount = 1;
    final double GST = 0.07;
    final double SERVICE_CHARGE = 0.10; 
    final double DISCOUNT = 0.15; 

    /**
     * Constructor. Used to create a payment object.
     * @param paymentDate
     * @param subTotal
     * @param gst
     * @param serviceCharge
     * @param memberDiscount
     * @param grandTotal
     * @param order
     * @param reservationNumber
     * @param tableId
     * @param membershipApplied
     */
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
    /**
     * Get paymentID of payment object. Public method.
     * @return integer paymentID
     */
    public int getpaymentID() {
    	return paymentID;
    }
    /**
     * Get paymentDate of payment object. Public method.
     * @return String paymentDate
     */
    public String getpaymentDate() {
    	return paymentDate;
    }
    /**
     * Get subTotal of payment object. Public method.
     * @return double subTotal
     */
    public double getSubTotal() {
    	return subTotal;
    }
    /**
     * Get gst of payment object. Public method.
     * @return double gst
     */
    public double getGst() {
    	return gst;
    }
    /**
     * Get serviceCharge of payment object. Public method.
     * @return double 
     */
    public double getServiceCharge() {
    	return serviceCharge;
    }
    /**
     * Get memberDiscount of payment object. Public method.
     * @return double memberDiscount
     */
    public double getMemberDiscount() {
    	return memberDiscount; 
    }
    /**
     * Get grandTotal of payment object. Public method.
     * @return double grandTotal
     */
    public double grandTotal() {
    	return grandTotal;
    }
    /**
     * Get order object.
     * @return Order object
     */
    public Order getOrder(){
    	return order;
    } //need order class
    /**
     * Get Order of payment object. Public method.
     * @param order object
     */
    public void setOrder(Order order) {
    	this.order = order; 
    }
    /**
     * Get reservationNumber of payment object. Public method.
     * @return integer reservationNumber
     */
    public int getreservationNumber() {
    	return reservationNumber;
    }//need reservation class
    /**
     * Get tableId of payment object. Public method.
     * @return integer tableId
     */
    public int getTableId() {
    	return tableId; 
    }
    /**
     * Get membershipApplied of payment object. Public method.
     * @return membershipApplied 
     */
    public boolean getmembershipApplied () {
    	return membershipApplied;
    }
    /**
     * Set membershipApplied of payment object. Public method.
     * @param membershipApplied
     */
    public void setmembershipApplied(boolean membershipApplied) {
    	this.membershipApplied = membershipApplied;
    }
	/**
	 * Get existing runningCount(Integer). Public method.
	 * @return integer runningCount.
	 */
    public static int getrunningCount() {
    	return runningCount;
    }
	/**
	 * Update existing runningCount. Public method.
	 * @param integer runningCount
	 */
    public static void setrunningCount(int paymentID) {
    	runningCount = paymentID;
    }

}