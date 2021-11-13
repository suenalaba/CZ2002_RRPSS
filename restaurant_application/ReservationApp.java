package restaurant_application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_entity.Customer;
import restaurant_entity.Reservation;
import restaurant_entity.Table.status;
import restaurant_manager.CustomerManager;
import restaurant_manager.ReservationManager;
import restaurant_manager.TableLayoutManager;
/**
 * An app for managing reservations.
 * @author Xin Pei
 * @version 4.5
 * @since 13-11-2021
 */
public class ReservationApp {
	public void registerCustomerQuery() { 
		//1. if registered> provide customerID
		//2. if not> create customer and retrieve customerID
		//3. if not> anonymous> assume not a case. Otherwise, input anonymous name in customer creation and ask Joshua for ability to decline providing phone number.
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		CustomerManager customerM=CustomerManager.getInstance();
		ReservationManager reservationM=ReservationManager.getInstance();
		CustomerApp customerA=new CustomerApp();
		
		// if no tables, print message and return to main menu.
		if (tableLayoutM.getLayout().getTableLayout().size()==0) { 
			 System.out.println("No Tables in restaurant for customers to dine. Returning to main menu.");
			 return;
		 }
		
		//Start of reservation APP display.
		Scanner sc = new Scanner(System.in);
		System.out.println("Walk-in or making reservation??");
		System.out.println("(1) Walk-in/Check in reservation");
		System.out.println("(2) Making new reservation ");
		int walkIn=0; //variable to store user's input choice.
		
		//invalid input response.
		while (walkIn==0) {
			try {
				walkIn=sc.nextInt();
				sc.nextLine();
				if(walkIn!=1&&walkIn!=2) {
					walkIn=0;
					System.out.println("Invalid Input. Try Again: ");
				}
			}catch(InputMismatchException e) {
				System.out.println("Invalid Input. Try Again: ");
			}
		}
		
		//valid input but store is closed.
		if (walkIn==1&&(Integer.parseInt(LocalDateTime.now().toString().substring(11, 13))>21||Integer.parseInt(LocalDateTime.now().toString().substring(11, 13))<9)){
			System.out.println("Opening hours are 9am to 10pm daily. Cannot accept customers now.");
			System.out.println("Returning to main menu.");
			return;
		}
		
		//valid input and store is open.
		System.out.println("Registered with us?(Has CustomerID)\n1.Yes\n2.No");
		int customerCheck=-1; //declare integer for loop and if statement checking.
		String customerID = null; //create variable to store customerID.
		while (customerCheck==-1) {
			try { //get input
				customerCheck=sc.nextInt();
				sc.nextLine();
				//if customer declares that they have registered with the store already.
				if(customerCheck==1) {
					break; //Exit while loop. 
				}
				//if customer declares that they have not registered with the store.
				else if(customerCheck==2) {
					customerA.createCustomer(false,walkIn);
					//assign customerID for non-registered customer.
					customerID=customerM.getCustomerList().get(customerM.getCustomerList().size()-1).getcustomerID();
					break; //Exit while loop.
				}
				//if input is invalid.
				else {
					customerCheck=-1;
					System.out.println("Please select either (1) or (2) only. Try again: ");
				}
				//if input is invalid.
			}catch (InputMismatchException e){
				System.out.println("Invalid input. Try again:");
			} catch (IOException e) {
				System.out.println("Invalid input when creating new guest. Try again: ");
				customerCheck=-1;
			}
		}
		//if will execute whether customer has declared they have or have not registered.
		if (customerID==null) {
			//Store ArrayList of existing customers. ArrayList of Customer objects.
			ArrayList<Customer> allCustomers=customerM.getCustomerList(); 
			//store ArrayList of customerIds. ArrayList of strings.
			ArrayList<String> allCustomerIds=new ArrayList<String>();
			System.out.println("\nEnter customerID:");			
			for (int i=0;i<allCustomers.size();i++) { //get all existing customer ids.
				allCustomerIds.add(allCustomers.get(i).getcustomerID()); //Store all existing customer ids into new ArrayList of type String.
				}
			while (customerID==null) {
				try {
					//get customer id from user (input)
					customerID=sc.nextLine();
					//if customer ID is in existing records
					if(allCustomerIds.contains(customerID)) { 
						break; //exit while loop.
					}
					else if(Integer.parseInt(customerID)==-1) { //user input -1, return to main menu.
						return;
					}
					//if customer ID is not in existing database.
					else {
						customerID=null;
						System.out.println("customerID is not registered with us. Please try again (-1 to return to main menu): ");
					}
				}catch (InputMismatchException e){ //invalid input
					System.out.println("Invalid input. Try again:");
				}
			}
		}
		//if customer is registered with store and has a reservtion.
		if(customerCheck==1&&walkIn==1) {
			//Store ArrayList of existing reservations for today. ArrayList of Reservation objects.
			ArrayList<Reservation> allReservationsToday=reservationM.getListOfReservationsToday();
			//Declare new ArrayList to store existing, unfinished reservations,for today. ArrayList of Reservation objects.
			ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
			for (int i=0;i<allReservationsToday.size();i++) { //for all reservations today,
		        if (allReservationsToday.get(i).getIsFinished()==false  //if reservation is not finished, add to unfinishedReservationsToday ArrayList<Reservation>.
		            && (allReservationsToday.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13))
		            || (allReservationsToday.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().plusHours(1).toString().substring(11,13))
		                && Integer.parseInt(LocalDateTime.now().toString().substring(14,16))>=45))) { //consider reservation even if customer shows up 15 minutes in advance
		          unfinishedReservationsToday.add(allReservationsToday.get(i));
		        }
		      }
			//loop through today's unfinished reservations.
			for (int i=0;i<unfinishedReservationsToday.size();i++) {
				//find the reservation of current customer and mark isAppeared as true. (Signifying customer for that reservation has arrived.)
				if (unfinishedReservationsToday.get(i).getCustomerID().equals(customerID)) {
					reservationM.completeReservation(unfinishedReservationsToday.get(i).getReservationID());
					return; //exit registerReservationQuery()
				}
			}
		}
		//Store this user's Customer object.
		Customer targetCustomer=customerM.getCustomer(customerID);
		String phonenumber = "\\d{8}";
		if (targetCustomer.getphoneNumber().equals("XXXXXXXX") && walkIn==2) { //walkIn == 2, means customer wants to make a new reservation.
			String phone_number;
			do { //get user input for phone number
				System.out.print("Enter customer's Contact Number (8 Digits): ");
				phone_number = sc.nextLine();
				if (phone_number.matches(phonenumber) && !phone_number.equals("")) { //phonenumber given is in correct format.
					targetCustomer.setphoneNumber(phone_number);
					break;
				}
				else {
					System.out.println("Invalid contact number!");
				}
			} while (phone_number.equals("") || !phone_number.matches(phonenumber)); //loop while input (phone number) is wrong
		}
		//find tables that have the available capacity first, then narrow down to available dateTimes.
		System.out.println("\nEnter pax:"); 
		int pax=-1;
		while (pax==-1) {
			try {
				//get pax input from user
				pax=sc.nextInt();
				sc.nextLine();
				if(pax<1) {
					System.out.println("Invalid input. Returning to main menu.");
					return;
				}
				else if(pax>10) {
					System.out.println("Number of people too large. Returning to main menu.");
					return;
				}
				else {
					break;
				}
			}catch (InputMismatchException e){
				System.out.println("Invalid input. Try again:");
			} 
		}
		//walk in branch
		if (walkIn==1) {
			reservationM.createWalkIn(pax,customerID);
			return;
		}
		//create reservation branch
		System.out.println("\nEnter start date and time (in the format: yyyy-MM-dd HH):");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
		String date;
		LocalDateTime dateTime;
		while (true){
			try {
				//get input for reservation datetime
			date = sc.nextLine();
			dateTime = LocalDateTime.parse(date, formatter);
			if (Integer.parseInt(dateTime.toString().substring(11,13))>21 || Integer.parseInt(dateTime.toString().substring(11,13))<9) {
				throw new Exception("Time indicated is not during opening hours");
			}
			else if(dateTime.isBefore(LocalDateTime.now())) {
				throw new Exception("Date is passed");
			}
			break;
			}catch(Exception e) {
				System.out.println("Invalid input. Try Again, restaurant only opens from 0900-2200. Last booking timeslot is 2100. ");
			}
		}
		String stringHourlyTime = date.substring(11,13); //take only the hour part from the LocalDateTime String.
		//store hour in integer
		int HourlyTime = Integer.parseInt(stringHourlyTime);
		
		//LocalDateTime LocalDateTime.now();
		//store current date, user's reservation date and hour, in string.
		String currentDate = LocalDateTime.now().toString().substring(0,10);
		String compareDate = date.substring(0,10);
		String compareHr = date.substring(11,13);
		//Create new ArrayList for storing integers of table IDs. These are tables that have been reserved during requested datetime. User cannot reserve those.
		//for checking future date and time reservations against given date and time.
		ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>();
		
		int tableID=-1;
		if (!compareDate.equals(currentDate) ) { //runs if given date is future date.
			for(Reservation s : reservationM.getListOfUnfinishedReservations()) { //get list of unfinished reservations
				//if start date,time and hour equals to given date, time and hour, add this tableID into array of integer.
				if(compareDate.equals(s.getReservationStartTime().toString().substring(0,10)) ) { 
					if(compareHr.equals(s.getReservationStartTime().toString().substring(11,13))) {
						tableIDOverlap.add(s.getTableID());
					}
				}
				
			}
			for (Integer s:tableLayoutM.getMinTableList(pax)) {
				if (!tableIDOverlap.contains(s)) { //available table at designated timeslot
					tableID=s;
					reservationM.createReservation(customerID, pax, dateTime,tableID);
					return;
				}
			}
			//Table unavailable at designated time slot
			System.out.println("No tables are available.");
			return;
		}
		//Store ArrayList of tableIDs of all possible tables.
		ArrayList<Integer> possibleTables=tableLayoutM.getMinTableList(pax);
		//Store ArrayList of tableIDs of all taken tables.
		ArrayList<Integer> takenTables=new ArrayList<Integer>();
		for (Reservation o:reservationM.getListOfUnfinishedReservationsToday()) {
			//Store tableIDs that are taken in user given timeslot.
			if (dateTime.getHour()==o.getReservationStartTime().getHour()) {
				takenTables.add(o.getTableID());
			}
			else if(dateTime.isAfter(o.getReservationStartTime())&&dateTime.isBefore(o.getReservationEndTime())) {
				takenTables.add(o.getTableID());
			}
		}
		//if possible table found.
		for(Integer o:possibleTables) {
			if (!takenTables.contains(o)) {
				tableID=o;
				break;
			}
		}
		//if no tables available.
		if (tableID == -1) {
			
			System.out.println("No tables are available.");
			return;
		}
		
		tableLayoutM.updateTableStatus(tableID, dateTime, status.RESERVED); //set table to reserved for the hour block
		
		LocalDateTime endDateTime = dateTime.plusHours(1); //create end time for reservation
		
		System.out.println("\n DateTime requested... \nStart:" + dateTime + "\nEnd: " + endDateTime);
		System.out.println("Table is available! TableID : " + tableID);
		reservationM.createReservation(customerID, pax, dateTime,tableID);
	}
	
	public void removeReservationQuery() {  //Queries reservation ID for removal. 
		ReservationManager reservationM=ReservationManager.getInstance();
		reservationM.printAllUnfinishedReservation();
		//User input
		System.out.println("What is the reservation ID you want to remove?");
		Scanner sc=new Scanner(System.in);
		int reservationID;
		int index;
		try {
			reservationID=sc.nextInt();
			sc.nextLine();
			index=reservationM.reservationIDToIndex(reservationID);
			Reservation target=reservationM.getReservation(reservationID);
			//if reservation does not exist
			if (index==-1) {
				System.out.println("Reservation does not exist. Returning to main menu");
				return;
			}
			//if reservation is already finished
			else if(target.getIsAppeared()==true||target.getIsFinished()==true) {
				System.out.println("Can only remove outstanding reservations.");
				return;
			}
			//reservation available for removal
			else {
				reservationM.removeReservation(reservationID);
			}
			return;
		}catch(InputMismatchException e) {
			System.out.println("Invalid input. Returning to main menu.");
			return;
		}
	}
	
	public void updateReservationQuery() { //pax or date change. Table affected if date change/pax change significantly. 
		ReservationManager reservationM=ReservationManager.getInstance();
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		Scanner sc = new Scanner(System.in); //scanner object
		reservationM.printAllUnfinishedReservation();
		System.out.println("Which ReservationID should be updated?");
		int reservationID=-1;
		//Create ArrayList of integers to store reservationIds of unfinished reservations.
		ArrayList<Integer> unfinishedReservationIds=new ArrayList<Integer>();
		//loop through unfinished reservations and add store their reservation ids.
		for (int i=0;i<reservationM.getListOfUnfinishedReservations().size();i++) {
			if (reservationM.getListOfUnfinishedReservations().get(i).getIsAppeared()==false) {
				unfinishedReservationIds.add(reservationM.getListOfUnfinishedReservations().get(i).getReservationID());
			}
		}
		try {
			//get user input for reservationID.
			reservationID=sc.nextInt();
			sc.nextLine();
			//if reservation is found but completed.
			if (!unfinishedReservationIds.contains(reservationID)) {
				System.out.println("Invalid or completed reservationID. Returning to main menu");
				return;
			}
			//invalid input
		}catch(Exception e){
			System.out.println("Invalid Input. Returning to main menu. ");
			return;
		}
		//if reservation is found
		System.out.println("What would you like to edit?");
		//store details of reservation
		int reservationIndex=reservationM.reservationIDToIndex(reservationID);
		int oldPax=reservationM.getListOfReservations().get(reservationIndex).getPax();
		LocalDateTime oldStartDateTime=reservationM.getListOfReservations().get(reservationIndex).getReservationStartTime();
		int newPax=oldPax;
		int newTableID;
		String date=oldStartDateTime.toString();
		LocalDateTime newStartDateTime=oldStartDateTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
		//get user input for action (what they want to edit about their reservation.
		int choice;
		do {
			//choice: 1 - Pax, 2 - Change Reservation date & time, 3 - Done.
			System.out.println("1. Pax\n2.Change Reservation date & time\n3.Done");
			try{
				choice=sc.nextInt();
				sc.nextLine();
				//if input is not an int. Invalid.
			}catch(InputMismatchException e) {
				System.out.println("Invalid input. Returning to main menu.");
				return;
			}
			switch(choice) {
			case 1: //pax update
				System.out.println("What is the new pax?");
				try{
					newPax=sc.nextInt();
					sc.nextLine();
					//user input
					if (newPax>10||newPax<=0) {
						System.out.println("New pax is out of range. Update Cancelled. Returning to Main Menu.");
						return;
					}
				}catch(InputMismatchException e) {
					System.out.println("Invalid input. Update Cancelled. Returning to main menu.");
					return;
				}
				break;
			case 2: //datetime update
				System.out.println("Enter new date and time(yyyy-MM-dd HH):");
				while (true){
					try {
						//user input
						date = sc.nextLine();
						newStartDateTime = LocalDateTime.parse(date, formatter);
						if (newStartDateTime.getHour()>=22 || newStartDateTime.getHour()<9) {
							System.out.println("Invalid input. Try Again:");
							newStartDateTime=oldStartDateTime;
						}
					break;
					}catch(Exception e) {
						System.out.println("Invalid input. Try Again:");
					}
				}
				break;
			case 3: //done, user choose not to edit
				break;
			default:
				System.out.println("Choice out of range. Update Cancelled. Returning to main menu.");
				return;
			}
			//below is to check if new updated reservation details is allowed.
			//assign table to match their needs.
			String currentDate = LocalDateTime.now().toString().substring(0,10);
			String compareDate = date.substring(0,10);
			String compareHr = date.substring(11,13);
			ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>(); //Checks future date and time reservations against comparison.Stores table
			int oldTableID=reservationM.getListOfReservations().get(reservationIndex).getTableID();
			newTableID=0;
			//checks the new pax and determineif it is more, less, or same as original pax.
			if((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1) || (oldPax-newPax)>=2 || (oldPax%2==1 &&(oldPax-newPax)==1)) { 
				if ((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1)) {
					newTableID=-1;
				}
				//if given date is future date.
				if (!compareDate.equals(currentDate)) { 
					for(Reservation s : reservationM.getListOfUnfinishedReservations()) {
						if(compareDate.equals( s.getReservationStartTime().toString().substring(0,10)) ) {
							if(compareHr.equals( s.getReservationStartTime().toString().substring(11,13))) { 
								if(s.getReservationID()==reservationID) { //discount the current table ID as it still needs to be considered. When only pax changes
									continue;
								}
								else {
								tableIDOverlap.add(s.getTableID());
								}
							}
						}
						
					}
				}
				for (Integer s:tableLayoutM.getMinTableList(newPax)) {
					if (!tableIDOverlap.contains(s)) { //available table at designated timeslot
						newTableID=s; //assign new table ID
						break;
					}
				}

			}	
				//if new table id fitting the new requirements is the same as the old table id.
				if (newTableID==oldTableID || newTableID==0) {
					System.out.println("TableID remains the same");
					newTableID=oldTableID;
				}
				else if (newTableID==-1) {
					System.out.println("No table available with updated pax and date time. Update Cancelled. Returning to main menu.");
					return;
				}
				else {
					System.out.println("New Table ID: "+newTableID);
				}
		}while (choice>=1 && choice<3);
		reservationM.updateReservation(reservationID,newStartDateTime, newTableID);
	}
	
	public void checkReservationQuery(){ //check details of reservation.
		ReservationManager reservationM=ReservationManager.getInstance();
		System.out.println("\n Enter your reservationID:");
		int reservationIndex;
		Scanner sc= new Scanner(System.in);
		try {
			int reservationID = sc.nextInt();
			reservationIndex=reservationM.reservationIDToIndex(reservationID);
			//if reservation not in database
			if (reservationIndex==-1) {
				System.out.println("Reservation ID not in system. Returning to main menu.");
				return;
			}
			//if reservation in database
			else {
				reservationM.printReservation(reservationID); //print reservation details
			}
			//invalid input
			}catch(InputMismatchException e) {
				System.out.println("Invalid input. Returning to main menu.");
				return;
			}
	}
}
