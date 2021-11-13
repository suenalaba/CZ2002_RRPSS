package restaurant_manager;


import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;



import restaurant_entity.Customer;
import restaurant_database.CustomerDatabase;
/**
 * Stores Customer object data and has methods called by the Customer App to manipulate Customer related data.
 * @author Joshua
 * @version 4.5
 * @since	13-11-2021
 */
public class CustomerManager {
	/**
	 * For singleton pattern adherence. This CustomerManager instance persists throughout runtime.
	 */
	private static CustomerManager instance=null;
	/**
	 * Holds ArrayList of Customer object that can be referenced to throughout runtime.
	 */
	ArrayList<Customer> customerList=new ArrayList<Customer>();
	/**
	 * Default constructor for Customer Manager.
	 */
	public CustomerManager() {
		customerList=new ArrayList<Customer>();
	}
	/**
	 * For singleton pattern adherence. 
	 * @return instance The static instance that persists throughout runtime.
	 */
	public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }
	/**
	 * Get Customer instance with Customer ID
	 * @param ID The Customer ID that is associated with him/her.
	 * @return Customer or null The Customer associated with Customer ID. Returns null if Customer with specified Customer ID does not exist.
	 */
	public Customer getCustomer(String ID) {
		
		int i;
		
		for (i = 0; i < customerList.size(); i++) {
			Customer validcustomer = (Customer) customerList.get(i);

			if (ID.equals(validcustomer.getcustomerID())) {
				return validcustomer;
			}
		}
		return null;
	}
	/**
	 * A method that queries Customer ID then returns a Customer if within CustomerList records
	 * @return Customer The existing customer within customerList records OR null if customer does not exist with customerList records
	 */
	public Customer retrieveCustomerDetailsbyID() {
		String custID;

		Customer validcustomer = new Customer();

		// Checking validity of Customer ID
		do {

			System.out.println("Please enter Customer ID to update: ");
			Scanner sc = new Scanner(System.in);
			custID = sc.nextLine();
			validcustomer = getCustomer(custID);
			if (validcustomer != null) {
				System.out.printf("Proceeding to update details for CustomerID: %s\n",custID);
				break;
			}
			else {
				System.out.println("Invalid Customer ID entered, please try again. ");
			}
		}while(validcustomer == null);
		return validcustomer;
	}
	/**
	 * prints all customer details in customerList
	 */
	public void printallCustomerDetails(){
		System.out.println("\n==================================================");
		System.out.println(" Customer Details: ");
		System.out.println("==================================================");
		System.out.printf("%-8s %-15s %-8s %-10s %-5s %-5s\n", "CustomerID", "Name",
				"Gender", "Phone Number", "Restaurant Membership", "Partner Membership");
		for (int i = 0; i < customerList.size(); i++) {
			Customer data = customerList.get(i);
			String customerid = String.valueOf(data.getcustomerID());
			String name = String.valueOf(data.getcustomerName());
			String gender = String.valueOf(data.getcustomerGender());
			String phonenumber = String.valueOf(data.getphoneNumber());
			String restaurantmember = String.valueOf(data.getrestaurantMembership());
			String partnermember = String.valueOf(data.getpartnerMembership());
			System.out.printf("%-8s %-15s %-8s %-10s %-5s %-5s\n", customerid, name, gender,phonenumber,
					restaurantmember, partnermember);
		}
	}
	/**
	 * Gets customerList for use as reference list of Customer objects or manipulation
	 * @return customerList
	 */
	public ArrayList<Customer> getCustomerList(){
		return customerList;
	}
	/**
	 * Saves the instance's customerList as string in a text file.
	 * @param textFileName The name of the the text file.
	 */
	public void saveDB(String textFileName) {
		CustomerDatabase cusDB=new CustomerDatabase();
		try {
			cusDB.fwrite(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to save to "+textFileName);
			return;
		}
	}
	/**
	 * Loads to instance's customerList from a text file
	 * @param textFileName The name of the text file.
	 */
	public void loadDB(String textFileName) { 
		ArrayList<Customer> newListOfCustomers = null;
		try {
			CustomerDatabase customerdatabase = new CustomerDatabase();
			newListOfCustomers = customerdatabase.fread(textFileName); 

		} catch (IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}
		customerList=newListOfCustomers;
		System.out.println("Loaded successfully from "+textFileName);
	}
}
