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

public class ReservationApp {
	public static void createReservationQuery() { 
		//1. if registered> provide customerID
		//2. if not> create customer and retrieve customerID
		//3. if not> anonymous> assume not a case. Otherwise, input anonymous name in customer creation and ask Joshua for ability to decline providing phone number.
		Scanner sc = new Scanner(System.in);
		System.out.println("Walk-in or making reservation??");
		System.out.println("(1) Walk-in/Complete Reservation");
		System.out.println("(2) Making new reservation ");
		int walkIn=0;
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
		if (walkIn==1&&(Integer.parseInt(LocalDateTime.now().toString().substring(11, 13))>21||Integer.parseInt(LocalDateTime.now().toString().substring(11, 13))<9)){
			System.out.println("Opening hours are 9am to 10pm daily. Cannot accept customers now.");
			System.out.println("Returning to main menu.");
			return;
		}
		System.out.println("Registered with us?(Has CustomerID)\n1.Yes\n2.No");
		int customerCheck=-1;
		String customerID = null;
		while (customerCheck==-1) {
			try {
				customerCheck=sc.nextInt();
				sc.nextLine();
				if(customerCheck==1) {
					break;
				}
				else if(customerCheck==2) {
					CustomerManager.createCustomer(false,walkIn);
					customerID=CustomerManager.retrieveallcustomerdetailsfromdatabase().get(CustomerManager.retrieveallcustomerdetailsfromdatabase().size()-1).getcustomerID();
					break;
				}
				else {
					customerCheck=-1;
					System.out.println("Please select either (1) or (2) only. Try again: ");
				}
			}catch (InputMismatchException e){
				System.out.println("Invalid input. Try again:");
			} catch (IOException e) {
				System.out.println("Invalid input when creating new guest. Try again: ");
				customerCheck=-1;
			}
		}
		if (customerID==null) {
			System.out.println("\n Enter customerID:");
			ArrayList<Customer> allCustomers=CustomerManager.retrieveallcustomerdetailsfromdatabase(); 
			ArrayList<String> allCustomerIds=new ArrayList<String>();
			for (int i=0;i<allCustomers.size();i++) {
				allCustomerIds.add(allCustomers.get(i).getcustomerID());
				}
			while (customerID==null) {
				try {
					customerID=sc.nextLine();
					if(allCustomerIds.contains(customerID)) {
						break;
					}
					else if(Integer.parseInt(customerID)==-1) {
						return;
					}
					else {
						customerID=null;
						System.out.println("customerID is not registered with us. Please try again (-1 to return to main menu): ");
					}
				}catch (InputMismatchException e){
					System.out.println("Invalid input. Try again:");
				}
			}
		}
		if(customerCheck==1&&walkIn==1) {
			ArrayList<Reservation> allReservationsToday=ReservationManager.getListOfReservationsToday();
			ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
			for (int i=0;i<allReservationsToday.size();i++) {
				if (allReservationsToday.get(i).getIsFinished()==false 
						&& allReservationsToday.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13))
						&& (allReservationsToday.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().minusHours(1).toString().substring(11,13))
								&& Integer.parseInt(LocalDateTime.now().toString().substring(14,16))>=45)) { //consider reservation even if customer shows up 15 minutes in advance
					unfinishedReservationsToday.add(allReservationsToday.get(i));
				}
			}
			for (int i=0;i<unfinishedReservationsToday.size();i++) {
				if (unfinishedReservationsToday.get(i).getCustomerID()==customerID) {
					ReservationManager.completeReservation(unfinishedReservationsToday.get(i).getReservationID());
					return;
				}
			}
		}
		
		Customer targetCustomer=CustomerManager.retrieveCustomerbyIDinput(customerID);
		String phonenumber = "\\d{8}";
		if (targetCustomer.getphoneNumber().equals("XXXXXXXX") && walkIn==2) {
			String phone_number;
			do {
				System.out.print("Enter customer's Contact Number (8 Digits): ");
				phone_number = sc.nextLine();
				if (phone_number.matches(phonenumber) && !phone_number.equals("")) {
					targetCustomer.setphoneNumber(phone_number);
					break;
				}
				else {
					System.out.println("Invalid contact number!");
				}
			} while (phone_number.equals("") || !phone_number.matches(phonenumber));
		}
		//find tables that have the available capacity first, then narrow down to available dateTimes.
		System.out.println("\n Enter pax:"); 
		int pax=-1;
		while (pax==-1) {
			try {
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
			ReservationManager.createWalkIn(pax,customerID);
			return;
		}
		//create reservation branch
		System.out.println("\n Enter start date and time (in the format: yyyy-MM-dd HH):");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
		String date;
		LocalDateTime dateTime;
		while (true){
			try {
			date = sc.nextLine();
			dateTime = LocalDateTime.parse(date, formatter);
			if (Integer.parseInt(dateTime.toString().substring(11,13))>21 || Integer.parseInt(dateTime.toString().substring(11,13))<9) {
				throw new Exception("Bad hour input");
			}
			else if(dateTime.isBefore(LocalDateTime.now())) {
				throw new Exception("Date is passed");
			}
			break;
			}catch(Exception e) {
				System.out.println("Invalid input. Try Again, restaurant only opens from 0900-2200. Last booking timeslot is 2100. ");
			}
		}
		String stringHourlyTime = date.substring(11,13);
		int HourlyTime = Integer.parseInt(stringHourlyTime);
		
		//LocalDateTime LocalDateTime.now();
		String currentDate = LocalDateTime.now().toString().substring(0,10);
		System.out.println(currentDate);
		String compareDate = date.substring(0,10);
		System.out.println(compareDate);
		String compareHr = date.substring(11,13);
		ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>(); //Checks future date and time reservations against comparison.Stores table
		
		int tableID;
		if (!compareDate.equals(currentDate) ) { //runs if given date is future date.
			for(Reservation s : ReservationManager.getListOfUnfinishedReservations()) {
				if(compareDate.equals(s.getReservationStartTime().toString().substring(0,10)) ) {
					if(compareHr.equals(s.getReservationStartTime().toString().substring(11,13))) {
						tableIDOverlap.add(s.getTableID());
					}
				}
				
			}
			for (Integer s:TableLayoutManager.getMinTableList(pax)) {
				if (!tableIDOverlap.contains(s)) { //available table at designated timeslot
					tableID=s;
					ReservationManager.createReservation(customerID, pax, dateTime,tableID);
					return;
				}
			}
			//Table unavailable at designated time slot
			System.out.println("No tables are available.");
			return;
		}
		
		tableID = (TableLayoutManager.getEmptyTableAtHour(pax, HourlyTime)); //returns -1 if no available tables are found. if not returns tableid
		
		if (tableID == -1) {
			
			System.out.println("No tables are available.");
			return;
		}
		
		TableLayoutManager.updateTable(tableID, HourlyTime, status.RESERVED); //set table to reserved for the hour block
		
		LocalDateTime endDateTime = dateTime.plusHours(1); //create end time for reservation
		
		System.out.println("\n DateTime requested... \nStart:" + dateTime + "\nEnd: " + endDateTime);
		System.out.println("Table is available! TableID : " + tableID);
		ReservationManager.createReservation(customerID, pax, dateTime,tableID);
	}
	public static void removeReservationQuery() {  //Queries reservation ID for removal. 
		System.out.println("What is the reservation ID you want to remove?");
		Scanner sc=new Scanner(System.in);
		int reservationID;
		int index;
		try {
			reservationID=sc.nextInt();
			sc.nextLine();
			index=ReservationManager.reservationIDToIndex(reservationID);
			if (index==-1) {
				System.out.println("Reservation does not exist. Returning to main menu");
				return;
			}
			else {
				ReservationManager.removeReservation(index);
			}
			return;
		}catch(InputMismatchException e) {
			System.out.println("Invalid input. Returning to main menu.");
			return;
		}
	}
	
	public static void updateReservationQuery() { //pax or date change. Table affected if date change/pax change significantly. 
		Scanner sc = new Scanner(System.in);
		System.out.println("Whats your Reservation ID?");		
		int reservationID=-1;
		ArrayList<Integer> unfinishedReservationIds=new ArrayList<Integer>();
		for (int i=0;i<ReservationManager.getListOfUnfinishedReservations().size();i++) {
			unfinishedReservationIds.add(ReservationManager.getListOfUnfinishedReservations().get(i).getReservationID());
		}
		try {
			reservationID=sc.nextInt();
			sc.nextLine();
			if (!unfinishedReservationIds.contains(reservationID)) {
				System.out.println("Invalid or passed reservationID. Returning to main menu");
				return;
			}
		}catch(Exception e){
			System.out.println("Invalid Input. Returning to main menu. ");
			return;
		}
		System.out.println("What would you like to edit?");
		System.out.println("1. Pax\n2.Change Reservation date & time\n3.Exit");
		int reservationIndex=ReservationManager.reservationIDToIndex(reservationID);
		int oldPax=ReservationManager.getListOfReservations().get(reservationIndex).getPax();
		LocalDateTime oldStartDateTime=ReservationManager.getListOfReservations().get(reservationIndex).getReservationStartTime();
		int newPax=oldPax;
		int newTableID;
		String date=oldStartDateTime.toString();
		LocalDateTime newStartDateTime=oldStartDateTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
		int choice;
		do {
			System.out.println("1. Pax\n2.Change Reservation date & time\n3.Done");
			try{
				choice=sc.nextInt();
				sc.nextLine();
			}catch(InputMismatchException e) {
				System.out.println("Invalid input. Returning to main menu.");
				return;
			}
			switch(choice) {
			case 1:
				System.out.println("What is the new pax?");
				try{
					newPax=sc.nextInt();
					sc.nextLine();
					if (newPax>10||newPax<=0) {
						System.out.println("New pax is out of range. Update Cancelled. Returning to Main Menu.");
						return;
					}
				}catch(InputMismatchException e) {
					System.out.println("Invalid input. Update Cancelled. Returning to main menu.");
					return;
				}
				break;
			case 2:
				System.out.println("Enter new date and time(yyyy-MM-dd HH):");
				while (true){
					try {
						date = sc.nextLine();
						newStartDateTime = LocalDateTime.parse(date, formatter);
					break;
					}catch(Exception e) {
						System.out.println("Invalid input. Try Again:");
					}
				}
				break;
			case 3:
				break;
			default:
				System.out.println("Choice out of range. Update Cancelled. Returning to main menu.");
				return;
			}
			//need to check if update will affect table Id assigned every loop before implementing. Once out of loop. use tableID and commit data in the ReservationManager.updateReservation method.
			String currentDate = LocalDateTime.now().toString().substring(0,10);
			String compareDate = date.substring(0,10);
			String compareHr = date.substring(11,13);
			ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>(); //Checks future date and time reservations against comparison.Stores table
			int oldTableID=ReservationManager.getListOfReservations().get(reservationIndex).getTableID();
			newTableID=0;
			//depending on decrease/increase decrease of pax from mod 2. We can then determine if it traversed a table capacity category. if jumped in either direction. 
			//assign new table for efficiency (when pax decreased). assign new table if pax cannot fit (when pax increased)
			if((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1) || (oldPax-newPax)>=2 || (oldPax%2==1 &&(oldPax-newPax)==1)) { 
				if ((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1)) {
					newTableID=-1;
				}
				if (!compareDate.equals(currentDate)) { //runs if given date is future date.
					for(Reservation s : ReservationManager.getListOfUnfinishedReservations()) {
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
				for (Integer s:TableLayoutManager.getMinTableList(newPax)) {
					if (!tableIDOverlap.contains(s)) { //available table at designated timeslot
						newTableID=s;
						break;
					}
				}

			}
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
		}while (choice>=1||choice<3);
		ReservationManager.updateReservation(reservationIndex,newStartDateTime, newTableID);
	}
}
