package restaurant_entity;
/**
 * A class defining a customer object.
 * @author Josh
 * @version 4.5
 * @since 13-11-2021
 */
public class Customer {
	private String customerID;
	private String customerName;
	private String customerGender;
	private String phoneNumber;
	private boolean restaurantMembership; //record if customer has restaurant membership
	private boolean partnerMembership; //record is customer has partner membership
	
	/**
	 * Constructor used by customer manager to create new staff.
	 */
	public Customer() {
		
	}
	/**
	 * Constructor to create new customer. (Used when reading files)
	 * @param customerID
	 * @param customerName
	 * @param customerGender
	 * @param phoneNumber
	 * @param restaurantMembership
	 * @param partnerMembership
	 */
	public Customer(String customerID, String customerName, String customerGender, String phoneNumber, boolean restaurantMembership,
			boolean partnerMembership) {
		this.customerID = customerID;
		this.customerName = customerName;
		this.customerGender = customerGender;
		this.phoneNumber = phoneNumber;
		this.restaurantMembership = restaurantMembership; 
		this.partnerMembership = partnerMembership; 
	}
	/**
	 * Get customerID of customer object. Public method.
	 * @return String customerID
	 */
	public String getcustomerID() {
		return customerID;
	}
	/**
	 * Set customerID of customer object.Public method.
	 * @param String customerID
	 */
	public void setcustomerID(String customerID) {
		this.customerID = customerID;
	}
	/**
	 * Get customerName of customer object. Public method.
	 * @return String customerName
	 */
	public String getcustomerName() {
		return customerName;
	}
	/**
	 * Set customerName of customer object. Public method.
	 * @param String customerName
	 */
	public void setcustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * Get customerGender of customer object. Public method.
	 * @return String customerGender
	 */
	public String getcustomerGender() {
		return customerGender;
	}
	/**
	 * Set customerGender of customer object. Public method.
	 * @param String customerGender
	 */
	public void setcustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}
	/**
	 * Get phoneNumber of customer object. Public method.
	 * @return String phoneNumber
	 */
	public String getphoneNumber() {
		return phoneNumber;
	}
	/**
	 * Set phoneNumber of customer object. Public method.
	 * @param String phoneNumber
	 */
	public void setphoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * Get restaurantMembership of customer object. Public method.
	 * @return boolean restaurantMembership
	 */
	public boolean getrestaurantMembership() {
		return restaurantMembership;
	}
	/**
	 * Set restaurantMembership of customer object. Public method.
	 * @param boolean restaurantMembership
	 */
	public void setrestaurantMembership(boolean restaurantMembership) {
		this.restaurantMembership = restaurantMembership;
	}
	/**
	 * Get partnerMembership of customer object. Public method.
	 * @return boolean partnerMembership
	 */
	public boolean getpartnerMembership() {
		return partnerMembership;
	}
	/**
	 * Set partnerMembership of customer object. Public method.
	 * @param boolean partnerMembership
	 */
	public void setpartnerMembership(boolean partnerMembership) {
		this.partnerMembership = partnerMembership;
	}
}