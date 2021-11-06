package restaurant_database;

// import relevant java libraries to be updated as we go..
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//import Customer class
import restaurant_entity.Customer;

public class CustomerDatabase implements DatabaseFunction {
	public static final String DELIMITER = ",";

	@Override
	public ArrayList fread(String textfilename) throws IOException {

		ArrayList fileasstring = (ArrayList) FileRead.fread(textfilename);
		
		//array to store customer data
		ArrayList customerlist = new ArrayList();

		for (int i = 0; i < fileasstring.size(); i++) {

			String data = (String) fileasstring.get(i);
			// get individual 'fields' of the string separated by the delimiter ','
			StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); // pass in the string to the string tokenizer
																		

			String customerid = str_tokenizer.nextToken().trim();
			String name = str_tokenizer.nextToken().trim();
			String gender = str_tokenizer.nextToken().trim();

			String phonenumber = str_tokenizer.nextToken().trim();
			boolean restaurantmembership = Boolean.parseBoolean(str_tokenizer.nextToken().trim());
			boolean partnermembership = Boolean.parseBoolean(str_tokenizer.nextToken().trim());

			

			// create customer object imported from database
			Customer customer = new Customer(customerid, name, gender, phonenumber, restaurantmembership,
					partnermembership);
			// add customer object to the list of customers
			customerlist.add(customer);
		}
		return customerlist;

	}

	@Override
	public void fwrite(String textfilename, List arraylist) throws IOException {

		List customerlist = new ArrayList();// array list to store customer data

		for (int i = 0; i < customerlist.size(); i++) {
			Customer customer = (Customer) customerlist.get(i);
			StringBuilder customerstring = new StringBuilder();
			customerstring.append(customer.getcustomerID().trim());
			customerstring.append(DELIMITER);
			customerstring.append(customer.getcustomerName().trim());
			customerstring.append(DELIMITER);
			customerstring.append(customer.getcustomerGender());
			customerstring.append(DELIMITER);

			customerstring.append(customer.getphoneNumber());
			customerstring.append(DELIMITER);
			customerstring.append(Boolean.toString(customer.getrestaurantMembership()));
			customerstring.append(DELIMITER);
			customerstring.append(Boolean.toString(customer.getpartnerMembership()));
			customerstring.append(DELIMITER);

			customerlist.add(customerstring.toString());
		}
		FileRead.fwrite(customerlist,textfilename);
	}


}
