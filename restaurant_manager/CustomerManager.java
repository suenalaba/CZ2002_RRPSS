package restaurant_manager;

//import java packages.... to be updated as we go
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;


//import other class.....
import restaurant_entity.Customer;
import restaurant_entity.TableLayout;
import restaurant_database.FileRead;
import restaurant_database.CustomerDatabase;

public class CustomerManager {
	private static CustomerManager instance=null;
	ArrayList<Customer> customerList=new ArrayList<Customer>();
	
	public CustomerManager() {
		customerList=new ArrayList<Customer>();
	}
	
	public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

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
			
	public void printallCustomerDetails() throws IOException {
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
	
	public ArrayList<Customer> getCustomerList(){
		return customerList;
	}
	
	public void saveDB(String textFileName) {
		CustomerDatabase cusDB=new CustomerDatabase();
		try {
			cusDB.fwrite(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to save to "+textFileName);
			return;
		}
	}
	public void loadDB(String textFileName) { 
		ArrayList<Customer> newListOfCustomers = null;
		try {
			// read file containing Guest records
			CustomerDatabase customerdatabase = new CustomerDatabase();
			newListOfCustomers = customerdatabase.fread(textFileName); //create read function from database

		} catch (IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}
		customerList=newListOfCustomers;
		System.out.println("Loaded successfully from "+textFileName);
	}
}
