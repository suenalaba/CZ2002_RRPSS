package restaurant_entity;

public class Customer {
	private String customerID;
	private String customerName;
	private String customerGender;
	private String phoneNumber;
	private boolean restaurantMembership;
	private boolean partnerMembership;
	
	public Customer() {
		
	}
	
	public Customer(String customerID, String customerName, String customerGender, String phoneNumber, boolean restaurantMembership,
			boolean partnerMembership) {
		this.customerID = customerID;
		this.customerName = customerName;
		this.customerGender = customerGender;
		this.phoneNumber = phoneNumber;
		this.restaurantMembership = restaurantMembership;
		this.partnerMembership = partnerMembership;
	}

	public String getcustomerID() {
		return customerID;
	}
	
	public void setcustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	public String getcustomerName() {
		return customerName;
	}

	public void setcustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getcustomerGender() {
		return customerGender;
	}

	public void setcustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}
	
	public String getphoneNumber() {
		return phoneNumber;
	}
	
	public void setphoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public boolean getrestaurantMembership() {
		return restaurantMembership;
	}
	
	public void setrestaurantMembership(boolean restaurantMembership) {
		this.restaurantMembership = restaurantMembership;
	}
	
	public boolean getpartnerMembership() {
		return partnerMembership;
	}
	
	public void setpartnerMembership(boolean partnerMembership) {
		this.partnerMembership = partnerMembership;
	}
}