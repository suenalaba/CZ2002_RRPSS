
package restaurant_application;


//import required java libraries to be updated as we go....    
import java.io.IOException;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
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
		int rrpss_select=0;
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
		final String STAFFFILE = "staffDB.txt";
		StaffManager.loadStaffDB(STAFFFILE);//load staff database
		final String MENUFILE="menuDB.txt";
		MenuManager.loadDB(MENUFILE); //load menu database
		final String TABLEFILE="tableLayoutDB.txt";
		TableLayoutManager.loadDB(TABLEFILE); //Load table database
		final String CUSTOMERFILE="customerDB.txt";
		CustomerManager.loadDB(CUSTOMERFILE);
		final String RESERVATAIONFILE="reservationDB.txt";
		ReservationManager.loadDB(RESERVATAIONFILE); //load reservation database
		final String ORDERFILE = "orderDB.txt";
		OrderManager.loadDB(ORDERFILE);
		final String PAYMENTFILE = "paymentDB.txt";
		PaymentManager.loadDB(PAYMENTFILE);//load Payment database
		
		//Customer Database is probed in classManager
		
		
		//OrderManager.getInstance().loadinDB(); //get instance of ordermanager
		//PaymentManager.getpaymentInstance().retrieveallpaymentdetailsfromdatabase();
		//get instance of customermanager
		//get instance of reservationmanager
		//get instance of tablelayout manager
		
		do {
			try {
			ReservationManager.autoUpdate();

			System.out.println("Welcome to Zhang Jie and Li Fang's Sichuan Delights");

			System.out.println("==================================================");
			System.out.println(" Hi Staff, please select an option 1-6: ");
			System.out.println("==================================================");//manage order -> create remove update print
			System.out.println("(1) Manage Restaurant\t\t(2) Manage Order"); //manage restaurant -> can edit customer, table, menu
			System.out.println("(3) Manage Reservations or Walk in Customer\t\t(4) Manage Payment");
			System.out.println("(5) Generate sales report\t(6) Save and exit application");
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
					System.out.println("(5) Saves Data and return to main menu");
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
								MenuApp.createItemQuery();
								break;
							case 2:
								MenuApp.removeItemQuery();
								break;
							case 3:
								MenuApp.updateItemQuery();
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
								CustomerApp.createCustomer(true,0); //Jacques - false-> solo/walkin/reservation true->multi option.
								break;
							case 2:
								CustomerApp.deleteCustomerdetailsbyID();
								break;
							case 3:
								CustomerApp.updateCustomerdetailsbyID();
								break;
							case 4:
								CustomerManager.printallCustomerDetails();
								break;
							case 5:
								CustomerApp.printCustomerdetailsbyID();
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
								TableLayoutApp.createTableQuery(); // function to create Table
								break;
							case 2:
								TableLayoutApp.removeTableQuery(); // function to remove Table
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
								StaffApp.createStaffMemberQuery();
								break;
							
							case 2:
								//remove staff
								StaffApp.removeStaffMemberQuery();
								break;
							case 3: 
								//update staff
								StaffApp.updateStaffMemberQuery();
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
						OrderApp.createOrderQuery();
						break;
					case 2:
						//remove order here
						OrderApp.deleteWholeOrderQuery();
						break;
					case 3:
						//update order here
						OrderApp.updateOrderQuery();
						break;
					case 4:
						//display all orders
						OrderManager.displayOrderList();
						break;
					case 5:
						//display specific order given a table input
						OrderApp.displayOrderBasedOnTableIdQuery();
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
						ReservationApp.registerCustomerQuery();
						break;
					case 2:
						ReservationApp.removeReservationQuery();
						break;
					case 3:
						ReservationApp.updateReservationQuery();
						break;
					case 4:
						ReservationApp.checkReservationQuery();
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
						PaymentApp.makePayment(); 
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
						PaymentApp.printSaleReport();
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
				TableLayoutManager.saveDB(TABLEFILE);
				OrderManager.saveDB(ORDERFILE);
				CustomerManager.saveDB(CUSTOMERFILE);
				StaffManager.saveStaffDB(STAFFFILE);
				MenuManager.saveDB(MENUFILE);
				ReservationManager.saveDB(RESERVATAIONFILE);
				PaymentManager.saveDB(PAYMENTFILE);
				break;
			default:
				rrpss_select = 0;
				System.out.println("Invalid choice, please try again!");
				break;
			}
			}catch(Exception e) {
				System.out.println("Invalid input. Please key in one of the choices listed.\nTryAgain:");
				System.out.println("Returning to Main Menu.");
				sc.nextLine();
			}
		} while (rrpss_select>=0 && rrpss_select<=5);
		sc.close();
		System.out.println("Data saved, thank you for coming to Li Fang and Zhang Jie restaurant");
	}
}
