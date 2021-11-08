
package restaurant_application;


//import required java libraries to be updated as we go....    
import java.io.IOException;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
import java.util.Scanner;
//import java.util.Timer;
//import java.util.TimerTask;

//import respective restaurant managers...
import restaurant_manager.CustomerManager;
import restaurant_manager.MenuManager;
import restaurant_manager.OrderManager;
import restaurant_manager.PaymentManager;
import restaurant_manager.ReservationManager;
import restaurant_manager.TableLayoutManager;

import restaurant_manager.MenuManager;
import restaurant_manager.OrderManager;
import restaurant_manager.ReservationManager;
import restaurant_manager.StaffManager;
import restaurant_manager.TableLayoutManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



public class RRPSSApp {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		//Date d = new Date();
		int rrpss_select;
		LocalDateTime datetimenow = LocalDateTime.now();
		System.out.println(datetimenow);
		Scanner sc = new Scanner(System.in);
		//Timer timer = new Timer();
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.HOUR_OF_DAY, 23);
        //calendar.set(Calendar.MINUTE, 30);
        //calendar.set(Calendar.SECOND, 00);
        //Date time = calendar.getTime();
        //timer.schedule(new checkExpired(), time);
		
		
		//read in all databases.
		final String staffFile = "staffDB.txt";
		StaffManager.loadStaffDB(staffFile);//load staff database
		final String menuFile="menuDB.txt";
		MenuManager.loadDB(menuFile); //load menu database
		final String tableFile="tableLayoutDB.txt";
		TableLayoutManager.loadDB(tableFile); //Load table database
		final String reservationFile="reservationDB.txt";
		ReservationManager.loadDB(reservationFile); //load reservation database
		final String orderFile = "orderDB.txt";
		OrderManager.loadOrderDB(orderFile);
		
		//Customer Database is probed in classManager
		
		
		//OrderManager.getInstance().loadinDB(); //get instance of ordermanager
		//PaymentManager.getpaymentInstance().retrieveallpaymentdetailsfromdatabase();
		//get instance of customermanager
		//get instance of reservationmanager
		//get instance of tablelayout manager
		
		do {

			System.out.println("Welcome to Zhang Jie and Li Fang's Sichuan Delights");

			System.out.println("==================================================");
			System.out.println(" Hi Staff, please select an option 1-6: ");
			System.out.println("==================================================");//manage order -> create remove update print
			System.out.println("(1) Manage Restaurant\t\t(2) Manage Order"); //manage restaurant -> can edit customer, table, menu
			System.out.println("(3) Manage Reservations or Walk in Customer\t\t(4) Manage Payment");
			System.out.println("(5) Generate sales report\t(6) Save and exit");
			System.out.println("\nEnter your choice:");
			rrpss_select = sc.nextInt();
			sc.nextLine();

			switch (rrpss_select) {
			case 1:
				int restaurant_select;
				do {
					System.out.println("\n==================================================");
					System.out.println(" Welcome, you are now managing restaurant details! ");
					System.out.println("==================================================");
					System.out.println("(1) Edit Menu Items\t(2) Edit Customer Details");
					System.out.println("(3) Edit Table Details\t(4) Edit Staff Details"); 
					System.out.println("(5) Sales Report\t(6) Saves Data and exits system.");
					System.out.println("Enter your choice:");
					restaurant_select = sc.nextInt();
					switch (restaurant_select) {
					case 1:
						int menu_select;
						do{
							System.out.println("\n==================================================");
							System.out.println(" Editing Menu Items: ");
							System.out.println("==================================================");
							System.out.println("(1) Add new menu item\t(2) Remove menu item");
							System.out.println("(3) Update menu item\t(4) Print Menu");
							System.out.println("(5) Nothing else to edit in menu");
							System.out.println("Enter your choice:");
							menu_select = sc.nextInt();
							switch(menu_select) {
							case 1: 
								MenuManager.createItemQuery();
								break;
							case 2:
								MenuManager.removeItemQuery();
								break;
							case 3:
								MenuManager.updateItemQuery();
								break;
							case 4:
								MenuManager.printMainMenu();
								break;
							case 5:
								System.out.println("Exiting now, Menu items have been updated!");
								break;
							default:
								menu_select = 0;
								System.out.println("Invalid input, please try again!");
								break;
							}
						} while(menu_select<=4 && menu_select>=0);
						break; //break out of menu_select
					case 2:
						int customer_select;
						do {
							System.out.println("\n==================================================");
							System.out.println(" Editing customer details: ");
							System.out.println("==================================================");
							System.out.println("(1) Create Customer\t(2) Remove customer");
							System.out.println("(3) Update Customer Details\t(4) View All Customer Details");
							System.out.println("(5) View specific customer details\t(6) Nothing else to edit regarding customers");
							System.out.println("Enter your choice:");
							customer_select = sc.nextInt();
							switch (customer_select) {
							case 1:
								// Create new Customer function
								CustomerManager.createCustomer(true,0); //Jacques - false-> solo/walkin/reservation true->multi option.
								break;
							case 2:
								CustomerManager.deleteCustomerdetailsbyID();
								break;
							case 3:
								CustomerManager.updateCustomerdetailsbyID();
								break;
							case 4:
								CustomerManager.printallCustomerDetails();
								break;
							case 5:
								CustomerManager.printCustomerdetailsbyID();
								break;
							case 6:
								System.out.println("Exiting now, Customer Details have been updated!");
								break;
							default:
								customer_select = 0;
								System.out.println("Invalid input, please try again!");
								break;
							}
						} while (customer_select >=0 && customer_select <=5);

						break; //break out of customer select
					case 3:
						//create remove print occupied tables print empty tables
						int table_select;
						do {
							System.out.println("\n==================================================");
							System.out.println(" Editing Table Details: ");
							System.out.println("==================================================");
							System.out.println("(1) Create Table\t\t(2) Remove Table");
							System.out.println("(3) Display Table Status");
							System.out.println("(4) Done editing table details!");
							System.out.println("Enter your choice:");
							table_select = sc.nextInt();
							switch (table_select) {
							case 1:
								TableLayoutManager.createTableQuery(); // function to create Table
								break;
							case 2:
								TableLayoutManager.removeTableQuery(); // function to remove Table
								break;
							case 3:
								TableLayoutManager.getInstance().printTableLayout();
							case 4:
								System.out.println("Exiting now, Table Details have been updated!");
								break;
							default:
								table_select = 0;
								System.out.println("Please enter a valid option.");
								break;
							}
						} while (table_select >=0 && table_select <=3);

						break; //break out of table select
					case 4:
						int staff_select;
						do {
							System.out.println("\n==================================================");
							System.out.println(" Editing Staff Details: ");
							System.out.println("==================================================");
							System.out.println("(1) Create Staff\t\t(2) Remove Staff");
							System.out.println("(3) Update Staff Details\t\t(4) Display All Staff");
							System.out.println("(5) Done editing staff details!");
							System.out.println("Enter your choice:");
							staff_select = sc.nextInt();
							switch(staff_select) {
							case 1:
								//create staff
								StaffManager.createStaffMemberQuery();
								break;
							
							case 2:
								//remove staff
								StaffManager.removeStaffMemberQuery();
								break;
							case 3: 
								//update staff
								StaffManager.updateStaffMemberQuery();
								break;
							case 4: 
								//display all staff
								StaffManager.displayStaffList();
								break;
							case 5:
								System.out.println("Exiting now, Staff Details have been updated!");
								break;
							default:
								staff_select = 0;
								System.out.println("Please enter a valid option.");
								break;
							}
						} while (staff_select >=0 && staff_select <= 4);
						break; //break out of editing staff
					case 5:
						System.out.println("Exiting now, Restaurant Details have been updated!");
						break;
					default:
						restaurant_select = 0;
						System.out.println("Please enter a valid option.");
						break;
					}
				} while (restaurant_select >=0 && restaurant_select <=4);
				break; //break out of managing restaurant 
			case 2: //manage order
				int order_select;
				do {
					System.out.println("\n==================================================");
					System.out.println(" Welcome! You are now editing order details: ");
					System.out.println("==================================================");
					System.out.println("(1) Create Order\t\t(2) Remove Order");
					System.out.println("(3) Update Order (4) Display All Orders");
					System.out.println("(5) Display Order For Table\t\t (6) Done editing order details");
					order_select = sc.nextInt();
					switch (order_select) {
					case 1:
						//create order here
						OrderManager.createOrderQuery();
						break;
					case 2:
						//remove order here
						OrderManager.deleteWholeOrderQuery();
						break;
					case 3:
						//update order here
						OrderManager.updateOrderQuery();
						break;
					case 4:
						//display all orders
						OrderManager.displayOrderList();
						break;
					case 5:
						//display specific order given a table input
						OrderManager.displayOrderBasedOnTableIdQuery();
						break;
					case 6: 
						System.out.println("Exiting now, Order Details have been updated!");
						break;
					default:
						order_select = 0;
						System.out.println("Please enter a valid option.");
						break;
					}
				} while(order_select >= 0 && order_select <=5);
				break; //break out of case 2, break out of editing orders
			case 3:
				// check in guest function
				int reservationwalkin_select;
				do {
					System.out.println("\n==================================================");
					System.out.println(" Welcome! You are now editing reservation/ walk-in details: ");
					System.out.println("==================================================");
					System.out.println("(1) Make Reservation\t\t(2) Remove Reservation");
					System.out.println("(3) Update Reservation (4) Check Reservation based on ID");
					System.out.println("(5) Display all reservations\t\t (6) Done editing reservation details");
					System.out.println("Enter your choice:");
					reservationwalkin_select = sc.nextInt();
					switch (reservationwalkin_select) {
					case 1:
						ReservationManager.createReservationQuery();
						break;
					case 2:
						ReservationManager.removeReservationQuery();
						break;
					case 3:
						ReservationManager.updateReservationQuery();
						break;
					case 4:
						ReservationManager.checkReservationQuery();
						break;
					case 5:
						ReservationManager.printAllUnfinishedReservation();
						break;
					case 6:
						System.out.println("Exiting now, Reservations Details have been updated!");
						break;
					default:
						reservationwalkin_select = 0;
						System.out.println("Please enter a valid option.");
						break;
					} 
				}while(reservationwalkin_select >=0 && reservationwalkin_select <=5);
				break; //break out of editing reservation interface
			case 4:
				int payment_select;
				do {
					System.out.println("\n==================================================");
					System.out.println(" Welcome! This is the payment interface! : ");
					System.out.println("==================================================");
					System.out.println("(1) Make Payment and Generate Invoice\t\t(2) No more payment to be made");
					System.out.println("Enter your choice:");
					payment_select = sc.nextInt();
					switch(payment_select) {
					case 1:
						//make payment and print receipt
						break;
					case 2:
						System.out.println("Exiting now, Payment Details have been updated!");
						break;
					default:
						payment_select = 0;
						System.out.println("Please enter a valid option.");
						break;
					}
				} while(payment_select==0 || payment_select == 1);
				break;
			case 5:
				int salesreport_select;
				do {
					System.out.println("\n==================================================");
					System.out.println(" Welcome! Sales report interface, what do you want? : ");
					System.out.println("==================================================");
					System.out.println("(1) Generale sales report\t\t(2) No more sales report needed!");
					System.out.println("Enter your choice:");
					salesreport_select = sc.nextInt();
					switch(salesreport_select) {
					case 1: 
						//generate sales report query
						break;
					case 2:
						System.out.println("Exiting Sales Report interface");
						break;
					default:
						salesreport_select = 0;
						System.out.println("Please enter a valid option.");
						break;
					}
				} while(salesreport_select == 0 || salesreport_select == 1);
			case 6:
				System.out.println("Have a good day fellow staff!");
				System.out.println("Program terminating, please hold while data is being saved ..."); // Save records into database and textfile
				TableLayoutManager.saveDB(tableFile);
				StaffManager.saveStaffDB(staffFile);
				MenuManager.saveToDB(menuFile);
				ReservationManager.saveDB(reservationFile);
				break;
			default:
				rrpss_select = 0;
				System.out.println("Invalid choice, please try again!");
				break;
			}
		} while (rrpss_select>=0 && rrpss_select<=5);
		
		//writing to all databases when program terminates
		//MenuManager.retrieveInstance().savetoDB(); //get menu manager instance
		//OrderManager.getInstance().savetoDB(); //get order manager instance
		//PaymentManager.getpaymentInstance().writeallpaymentdetailstodatabase();
		//customer manager instance
		//reservation manager instance
		//table layout manager instance
		sc.close();
		System.out.println("Data saved, thank you for coming to Li Fang and Zhang Jie restaurant");
	}
	//function to check if reservation exceeded then u wanna remove reservation and 
	//set table to "vacant"
}
