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
	public void registerCustomerQuery() { 
		//1. if registered> provide customerID
		//2. if not> create customer and retrieve customerID
		//3. if not> anonymous> assume not a case. Otherwise, input anonymous name in customer creation and ask Joshua for ability to decline providing phone number.
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		CustomerManager customerM=CustomerManager.getInstance();
		ReservationManager reservationM=ReservationManager.getInstance();
		CustomerApp customerA=new CustomerApp();
		if (tableLayoutM.getLayout().getTableLayout().size()==0) {
			 System.out.println("No Tables in restaurant for customers to dine. Returning to main menu.");
			 return;
		 }
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Walk-in or making reservation??");
		System.out.println("(1) Walk-in/Check in reservation");
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
					customerA.createCustomer(false,walkIn);
					customerID=customerM.getCustomerList().get(customerM.getCustomerList().size()-1).getcustomerID();
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
			System.out.println("\nEnter customerID:");
			ArrayList<Customer> allCustomers=customerM.getCustomerList(); 
			ArrayList<String> allCustomerIds=new ArrayList<String>();
			for (int i=0;i<allCustomers.size();i++) {
				allCustomerIds.add(allCustomers.get(i).getcustomerID());
				System.out.println(allCustomers.get(i).getcustomerID() + "\t" + allCustomers.get(i).getcustomerName());
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
			ArrayList<Reservation> allReservationsToday=reservationM.getListOfReservationsToday();
			ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
			for (int i=0;i<allReservationsToday.size();i++) {
		        if (allReservationsToday.get(i).getIsFinished()==false 
		            && (allReservationsToday.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13))
		            || (allReservationsToday.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().plusHours(1).toString().substring(11,13))
		                && Integer.parseInt(LocalDateTime.now().toString().substring(14,16))>=45))) { //consider reservation even if customer shows up 15 minutes in advance
		          unfinishedReservationsToday.add(allReservationsToday.get(i));
		        }
		      }
			for (int i=0;i<unfinishedReservationsToday.size();i++) {
				if (unfinishedReservationsToday.get(i).getCustomerID().equals(customerID)) {
					reservationM.completeReservation(unfinishedReservationsToday.get(i).getReservationID());
					return;
				}
			}
		}
		
		Customer targetCustomer=customerM.getCustomer(customerID);
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
		System.out.println("\nEnter pax:"); 
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
		String stringHourlyTime = date.substring(11,13);
		int HourlyTime = Integer.parseInt(stringHourlyTime);
		
		//LocalDateTime LocalDateTime.now();
		String currentDate = LocalDateTime.now().toString().substring(0,10);
		String compareDate = date.substring(0,10);
		String compareHr = date.substring(11,13);
		ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>(); //Checks future date and time reservations against comparison.Stores table
		
		int tableID=-1;
		if (!compareDate.equals(currentDate) ) { //runs if given date is future date.
			for(Reservation s : reservationM.getListOfUnfinishedReservations()) {
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
		
		ArrayList<Integer> possibleTables=tableLayoutM.getMinTableList(pax);
		ArrayList<Integer> takenTables=new ArrayList<Integer>();
		for (Reservation o:reservationM.getListOfUnfinishedReservationsToday()) {
			if (dateTime.getHour()==o.getReservationStartTime().getHour()) {
				takenTables.add(o.getTableID());
			}
			else if(dateTime.isAfter(o.getReservationStartTime())&&dateTime.isBefore(o.getReservationEndTime())) {
				takenTables.add(o.getTableID());
			}
		}
		
		for(Integer o:possibleTables) {
			if (!takenTables.contains(o)) {
				tableID=o;
				break;
			}
		}
		
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
		System.out.println("What is the reservation ID you want to remove?");
		Scanner sc=new Scanner(System.in);
		int reservationID;
		int index;
		try {
			reservationID=sc.nextInt();
			sc.nextLine();
			index=reservationM.reservationIDToIndex(reservationID);
			Reservation target=reservationM.getReservation(reservationID);
			if (index==-1) {
				System.out.println("Reservation does not exist. Returning to main menu");
				return;
			}
			else if(target.getIsAppeared()==true||target.getIsFinished()==true) {
				System.out.println("Can only remove outstanding reservations.");
				return;
			}
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
		Scanner sc = new Scanner(System.in);
		reservationM.printAllUnfinishedReservation();
		System.out.println("Which ReservationID should be updated?");
		int reservationID=-1;
		ArrayList<Integer> unfinishedReservationIds=new ArrayList<Integer>();
		for (int i=0;i<reservationM.getListOfUnfinishedReservations().size();i++) {
			if (reservationM.getListOfUnfinishedReservations().get(i).getIsAppeared()==false) {
				unfinishedReservationIds.add(reservationM.getListOfUnfinishedReservations().get(i).getReservationID());
			}
		}
		try {
			reservationID=sc.nextInt();
			sc.nextLine();
			if (!unfinishedReservationIds.contains(reservationID)) {
				System.out.println("Invalid or completed reservationID. Returning to main menu");
				return;
			}
		}catch(Exception e){
			System.out.println("Invalid Input. Returning to main menu. ");
			return;
		}
		System.out.println("What would you like to edit?");
		int reservationIndex=reservationM.reservationIDToIndex(reservationID);
		int oldPax=reservationM.getListOfReservations().get(reservationIndex).getPax();
		LocalDateTime oldStartDateTime=reservationM.getListOfReservations().get(reservationIndex).getReservationStartTime();
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
			case 3:
				break;
			default:
				System.out.println("Choice out of range. Update Cancelled. Returning to main menu.");
				return;
			}
			String currentDate = LocalDateTime.now().toString().substring(0,10);
			String compareDate = date.substring(0,10);
			String compareHr = date.substring(11,13);
			ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>(); //Checks future date and time reservations against comparison.Stores table
			int oldTableID=reservationM.getListOfReservations().get(reservationIndex).getTableID();
			newTableID=0;
			if((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1) || (oldPax-newPax)>=2 || (oldPax%2==1 &&(oldPax-newPax)==1)) { 
				if ((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1)) {
					newTableID=-1;
				}
				if (!compareDate.equals(currentDate)) { //runs if given date is future date.
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
		}while (choice>=1 && choice<3);
		reservationM.updateReservation(reservationID,newStartDateTime, newTableID);
	}
	
	public void checkReservationQuery(){
		ReservationManager reservationM=ReservationManager.getInstance();
		System.out.println("\n Enter your reservationID:");
		int reservationIndex;
		Scanner sc= new Scanner(System.in);
		try {
			int reservationID = sc.nextInt();
			reservationIndex=reservationM.reservationIDToIndex(reservationID);
			if (reservationIndex==-1) {
				System.out.println("Reservation ID not in system. Returning to main menu.");
				return;
			}
			else {
				reservationM.printReservation(reservationID);
			}
			}catch(InputMismatchException e) {
				System.out.println("Invalid input. Returning to main menu.");
				return;
			}
	}
}
