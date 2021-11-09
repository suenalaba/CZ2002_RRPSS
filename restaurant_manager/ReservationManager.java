package restaurant_manager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_database.ReservationDatabase;
import restaurant_entity.Customer;
import restaurant_entity.Reservation;
import restaurant_entity.Table;
import restaurant_entity.Table.status;

public class ReservationManager {
	
	//Attribute
	private static ArrayList<Reservation> listOfReservations = new ArrayList<Reservation>();
	
	//Query Methods and Implements
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
			ArrayList<Reservation> allReservationsToday=getListOfReservationsToday();
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
					completeReservation(unfinishedReservationsToday.get(i).getReservationID());
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
			createWalkIn(pax,customerID);
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
			for(Reservation s : getListOfUnfinishedReservations()) {
				if(compareDate.equals(s.getReservationStartTime().toString().substring(0,10)) ) {
					if(compareHr.equals(s.getReservationStartTime().toString().substring(11,13))) {
						tableIDOverlap.add(s.getTableID());
					}
				}
				
			}
			for (Integer s:TableLayoutManager.getMinTableList(pax)) {
				if (!tableIDOverlap.contains(s)) { //available table at designated timeslot
					tableID=s;
					createReservation(customerID, pax, dateTime,tableID);
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
		createReservation(customerID, pax, dateTime,tableID);
	}

	private static void createReservation(String customerID, int pax, LocalDateTime startDateTime, int tableID) { //Creates Reservation and updates the database
		Reservation newReservationObject = new Reservation();
		listOfReservations.add(newReservationObject); //add new reservation empty object into arraylist
		System.out.println("Creating reservation...");
		//set all the user inputs into the reservation
		newReservationObject.setCustomerID(customerID);
		newReservationObject.setPax(pax);
		newReservationObject.setReservationStartTime(startDateTime);
		newReservationObject.setTableID(tableID);
		LocalDateTime endDateTime = startDateTime.plusHours(1);
		newReservationObject.setReservationEndTime(endDateTime);
		System.out.println("New reservation created! Your reservation ID is " + newReservationObject.getReservationID());
		saveDB("ReservationDB.txt");	
	}
	
	private static void createWalkIn(int pax,String customerID) {//gives table with buffer of 1 hour for reservations ahead
		ArrayList<Reservation> allReservationsToday=getListOfReservationsToday();
		ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
		ArrayList<Reservation> protectedReservationsToday=new ArrayList<Reservation>();
		ArrayList<Integer> possibleTables=TableLayoutManager.getMinTableList(pax);
		int tableId;
		LocalDateTime nowDate=LocalDateTime.now();
		for (int i=0;i<allReservationsToday.size();i++) {
			if(allReservationsToday.get(i).getIsFinished()==false) {
				unfinishedReservationsToday.add(allReservationsToday.get(i));
			}
		}
		for (int i=0;i<unfinishedReservationsToday.size();i++) {
			if (unfinishedReservationsToday.get(i).getReservationStartTime().minusMinutes(60).isBefore(nowDate)) {
				protectedReservationsToday.add(unfinishedReservationsToday.get(i));
			}
		}
		for (int i=0;i<protectedReservationsToday.size();i++) {
			if (possibleTables.contains(protectedReservationsToday.get(i).getTableID())) {
				possibleTables.remove(protectedReservationsToday.get(i).getTableID());
			}
		}
		if (possibleTables.size()>0) {
			tableId=possibleTables.get(0);
		}
		else {
			System.out.println("No available table at the moment");
			return;
		}
		listOfReservations.add(new Reservation(tableId,pax,customerID,LocalDateTime.now(),LocalDateTime.now().plusHours(1),false,true));
		TableLayoutManager.updateTable(tableId, Integer.parseInt(LocalDateTime.now().toString().substring(11,13)), status.OCCUPIED);
		if (Integer.parseInt(LocalDateTime.now().toString().substring(11,13))!=21) {
			TableLayoutManager.updateTable(tableId, Integer.parseInt(LocalDateTime.now().plusHours(1).toString().substring(11,13)), status.OCCUPIED);
		}
		System.out.println("Walk-in fulfilled.\n"
				+ "TableID:"+tableId+"\n"
				+ "StartTime: "+LocalDateTime.now().toString().substring(11,16)+ "\n"
				+ "EndTime: " +LocalDateTime.now().plusHours(1).toString().substring(11,16));
	}
	
	private static void completeReservation(int reservationID) {
		ArrayList<Reservation> allReservationsToday=getListOfReservationsToday();
		ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
		ArrayList<Reservation> protectedReservationsToday=new ArrayList<Reservation>();
		Reservation toComplete = null;
		for (int i=0;i<allReservationsToday.size();i++) {
			if(allReservationsToday.get(i).getIsFinished()==false) {
				unfinishedReservationsToday.add(allReservationsToday.get(i));
			}
		}
		for (int i=0;i<unfinishedReservationsToday.size();i++) {
			if (unfinishedReservationsToday.get(i).getReservationID()==reservationID){
				toComplete=unfinishedReservationsToday.get(i);
			}
		}
		toComplete.setIsAppeared(true);
		TableLayoutManager.updateTable(toComplete.getTableID(), Integer.parseInt(toComplete.getReservationStartTime().toString().substring(11,13)), status.OCCUPIED);
		System.out.println("Reservation fulfilled.\n"
				+ "TableID:"+toComplete.getTableID()+"\n"
				+ "StartTime: "+toComplete.getReservationStartTime().toString().substring(11,16)+ "\n"
				+ "EndTime: " +toComplete.getReservationStartTime().plusHours(1).toString().substring(11,16));
		
	}
	
	public static void removeReservationQuery() {  //Queries reservation ID for removal. 
		System.out.println("What is the reservation ID you want to remove?");
		Scanner sc=new Scanner(System.in);
		int reservationID;
		int index;
		try {
			reservationID=sc.nextInt();
			sc.nextLine();
			index=reservationIDToIndex(reservationID);
			if (index==-1) {
				System.out.println("Reservation does not exist. Returning to main menu");
				return;
			}
			else {
				removeReservation(index);
			}
			return;
		}catch(InputMismatchException e) {
			System.out.println("Invalid input. Returning to main menu.");
			return;
		}
	}
	
	private static void removeReservation(int reservationID) {  //sets isFinished status. Frees corresponding table on same day.
		listOfReservations.get(reservationID).setIsFinished(true); //no more
		int tableId=listOfReservations.get(reservationID).getTableID();
		int hourBlock=Integer.parseInt(listOfReservations.get(reservationID).getReservationStartTime().toString().substring(11,13));
		String dateBlock=listOfReservations.get(reservationID).getReservationStartTime().toString().substring(0,10);
		if (LocalDateTime.now().toString().substring(0,10).equals(dateBlock)) {
			TableLayoutManager.updateTable(tableId, hourBlock, status.EMPTY);
		}
		System.out.println("Reservation removed successfully!");
	}
	
	public static void updateReservationQuery() { //pax or date change. Table affected if date change/pax change significantly. 
		Scanner sc = new Scanner(System.in);
		System.out.println("Whats your Reservation ID?");		
		int reservationID=-1;
		ArrayList<Integer> unfinishedReservationIds=new ArrayList<Integer>();
		for (int i=0;i<getListOfUnfinishedReservations().size();i++) {
			unfinishedReservationIds.add(getListOfUnfinishedReservations().get(i).getReservationID());
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
		int reservationIndex=reservationIDToIndex(reservationID);
		int oldPax=listOfReservations.get(reservationIndex).getPax();
		LocalDateTime oldStartDateTime=listOfReservations.get(reservationIndex).getReservationStartTime();
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
			//need to check if update will affect table Id assigned every loop before implementing. Once out of loop. use tableID and commit data in the updateReservation method.
			String currentDate = LocalDateTime.now().toString().substring(0,10);
			String compareDate = date.substring(0,10);
			String compareHr = date.substring(11,13);
			ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>(); //Checks future date and time reservations against comparison.Stores table
			int oldTableID=listOfReservations.get(reservationIndex).getTableID();
			newTableID=0;
			//depending on decrease/increase decrease of pax from mod 2. We can then determine if it traversed a table capacity category. if jumped in either direction. 
			//assign new table for efficiency (when pax decreased). assign new table if pax cannot fit (when pax increased)
			if((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1) || (oldPax-newPax)>=2 || (oldPax%2==1 &&(oldPax-newPax)==1)) { 
				if ((newPax-oldPax)>=2 || (newPax%2==1 && (newPax-oldPax)==1)) {
					newTableID=-1;
				}
				if (!compareDate.equals(currentDate)) { //runs if given date is future date.
					for(Reservation s : getListOfUnfinishedReservations()) {
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
		updateReservation(reservationIndex,newStartDateTime, newTableID);
	}
	
	private static void updateReservation(int reservationIndex,LocalDateTime newStartDateTime, int newTableID) {  //updates all relevant details. Updates table status if current day affected
		LocalDateTime oldStartDateTime=listOfReservations.get(reservationIndex).getReservationStartTime();
		int oldHourBlock=Integer.parseInt(oldStartDateTime.toString().substring(11,13));
		String oldDate=oldStartDateTime.toString().substring(0,10);
		int oldTableID=listOfReservations.get(reservationIndex).getTableID(); //reservation attribute update (start)
		listOfReservations.get(reservationIndex).setTableID(newTableID);
		listOfReservations.get(reservationIndex).setReservationStartTime(newStartDateTime);
		LocalDateTime newEndStartDateTime=newStartDateTime.plusHours(1);
		listOfReservations.get(reservationIndex).setReservationEndTime(newEndStartDateTime); //reservation attribute update (end)
		String currentDate = LocalDateTime.now().toString().substring(0,10);
		String compareDate = newStartDateTime.toString().substring(0,10);
		int newHourBlock = Integer.parseInt(newStartDateTime.toString().substring(11,13));
		if (currentDate.equals(oldDate)) {
			TableLayoutManager.updateTable(oldTableID, oldHourBlock, status.EMPTY); //if old date and current date is today. free it as updated
		}
		if (currentDate.equals(compareDate)) {
			TableLayoutManager.updateTable(newTableID, newHourBlock, status.RESERVED); //if new date and current date is today. update status
			}
		}
	
	public static void autoUpdate(){//updates periodically 15 minutes grace period to appear
		LocalDateTime nowDateTime=LocalDateTime.now();
		ArrayList<Reservation> allReservations=getListOfReservations();
		ArrayList<Reservation> allUnfinished=new ArrayList<Reservation>();
		for (int i=0;i<allReservations.size();i++) {
			if (allReservations.get(i).getIsFinished()==false) {
				allUnfinished.add(allReservations.get(i));
			}
		}
		for (int i=0;i<allUnfinished.size();i++) {
			if (LocalDateTime.now().minusMinutes(15).isAfter(allUnfinished.get(i).getReservationStartTime())) {
				if (TableLayoutManager.getTableStatusNow(allUnfinished.get(i).getTableID())==status.RESERVED && allUnfinished.get(i).getReservationStartTime().getDayOfYear()==LocalDateTime.now().getDayOfYear()
						&& allUnfinished.get(i).getReservationStartTime().getYear()==LocalDateTime.now().getYear()) {
					TableLayoutManager.updateTable(allUnfinished.get(i).getTableID(), status.EMPTY);
					allUnfinished.get(i).setIsFinished(true);
				}
				else if(TableLayoutManager.getTableStatusNow(allUnfinished.get(i).getTableID())==status.OCCUPIED && allUnfinished.get(i).getReservationStartTime().getDayOfYear()==LocalDateTime.now().getDayOfYear()
						&& allUnfinished.get(i).getReservationStartTime().getYear()==LocalDateTime.now().getYear()) {
					continue;
				}
				else {
					allUnfinished.get(i).setIsFinished(true);
				}
			}
		}
	}
	
	public static void setIsFinished(int tableID) {
		ArrayList<Reservation> unfinishedToday=getListOfUnfinishedReservationsToday();
		for (Reservation o:unfinishedToday) {
			if (o.getTableID()==tableID && o.getIsAppeared()==true) {
				o.setIsFinished(true);
			}
		}
	}
	
	
	//check methods
	public static void checkReservationQuery(){ //Queries for correct reservation ID then passes to checkReservation
		System.out.println("\n Enter your reservationID:");
		int reservationIndex;
		Scanner sc= new Scanner(System.in);
		try {
			int reservationID = sc.nextInt();
			reservationIndex=reservationIDToIndex(reservationID);
			if (reservationIndex==-1) {
				System.out.println("Reservation ID not in system. Returning to main menu.");
				return;
			}
			else {
			checkReservation(reservationID,reservationIndex);
			}
			}catch(InputMismatchException e) {
				System.out.println("Invalid input. Returning to main menu.");
				return;
			}
	}
	
	private static void checkReservation(int reservationID,int reservationIndex) {  //Prints reservation details
		System.out.println("\n" + "Checking Reservation " + reservationID + "...");
		System.out.println("\n ---Current reservation details---");
		System.out.println("ReservationID: " + listOfReservations.get(reservationIndex).getReservationID());
		Customer theCustomer = CustomerManager.retrieveCustomerbyIDinput(listOfReservations.get(reservationIndex).getCustomerID());
		System.out.println("ReservationID: " + listOfReservations.get(reservationIndex).getCustomerID());
		System.out.println("Name: " + theCustomer.getcustomerName());
		System.out.println("Contact No.: " + theCustomer.getphoneNumber());
		System.out.println("Pax: " + listOfReservations.get(reservationIndex).getPax());
		System.out.println("Start date time: " + listOfReservations.get(reservationIndex).getReservationStartTime());
		System.out.println("End date time:" + listOfReservations.get(reservationIndex).getReservationEndTime());
		System.out.println("---All Reservation Details Displayed--- \n");
	}
	
	//print method
	public static void printAllUnfinishedReservation() {
		System.out.println("Standing Reservation(s):");
		System.out.println("=============================================================");
		ArrayList<Reservation> standingReservationList=getListOfUnfinishedReservations();
		if (standingReservationList.size()>0) {
		for (Reservation o:standingReservationList) {
			System.out.println("Reservation ID:"+o.getReservationID());//reservation ID
			System.out.println("Customer ID:"+o.getCustomerID());
			System.out.println("Table ID:"+o.getTableID());
			System.out.println("Pax:"+o.getPax());
			System.out.println("Reservation timeSlot:" + o.getReservationStartTime().toString().substring(0,10)+"|"+o.getReservationStartTime().toString().substring(11,13)+":00");
			System.out.println("=============================================================");
		}
		}
	}
	
	//Utility method
	public static int reservationIDToIndex(int reservationID) { //reservation ID to listOfReservations array index. Only works on the static array.
		for (int i=0;i<listOfReservations.size();i++) {
			if(listOfReservations.get(i).getReservationID()==reservationID) {
				return i;
			}
		}
		return -1;
	}
	
	//Get methods
	public static ArrayList<Reservation> getListOfReservations() {
		return listOfReservations;
	}
	
	public static ArrayList<Reservation> getListOfUnfinishedReservations() { //gets arraylist of unfinished reservations
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //new array of reservation objs to store
		for (Reservation s : listOfReservations) {
			if (s.getIsFinished()==false) {
				t.add(s);
			}
		}
		return t;
	}
	
	public static ArrayList<Reservation> getListOfUnfinishedReservationsToday() { //gets arraylist of unfinished reservations today
		ArrayList<Reservation> t = new ArrayList<Reservation>(); 
		for (Reservation s : listOfReservations) {
			if (s.getIsFinished()==false && s.getReservationStartTime().getDayOfYear()==LocalDateTime.now().getDayOfYear() && s.getReservationStartTime().getYear()==LocalDateTime.now().getYear()) {
				t.add(s);
			}
		}
		return t;
	}
	
	public static ArrayList<Reservation> getListOfFinishedReservations() { //gets arraylist of finished reservations
		ArrayList<Reservation> t = new ArrayList<Reservation>(); 
		for (Reservation s : listOfReservations) {
			if (s.getIsFinished()==true) {
				t.add(s);
			}
		}
		return t;
	}
	
	public static ArrayList<Reservation> getListOfReservationsToday(){ //Returns reservation arraylist for current day. finished and unfinished
		ArrayList<Reservation> t = new ArrayList<Reservation>(); 
		for (Reservation s : listOfReservations) {
			String reservationTime = s.getReservationStartTime().toString().substring(0,10);
			
			if ( LocalDateTime.now().toString().substring(0,10).equals(reservationTime)) {
				t.add(s);
			}
		}
		if (t.size()>0) {
			return t;
			}
		else {
			return t;
		}
	}
	
	public static Reservation getUnfinishedReservationOfTableIDNow(int tableID) { //give tableID, return Reservation now
		ArrayList<Reservation> unfinished=getListOfUnfinishedReservations();
		LocalDateTime timeNow=LocalDateTime.now();
		String hourBlock=timeNow.toString().substring(11,13);
		String dateBlock=timeNow.toString().substring(0,10);
		if (unfinished.size()>0) {
			for (Reservation o:unfinished) {
				if (tableID==o.getTableID()&&o.getReservationStartTime().toString().substring(11,13).equals(hourBlock)&&o.getReservationStartTime().toString().substring(11,13).equals(dateBlock)) {
					return o;
				}
			}
		}
		return null; //no reservation found for tableID now.
	}
	
	public static ArrayList<Reservation> getUnfinishedReservationByDate(String dateBlock) { //takes yyyy-MM-dd. Provide dateblock and returns array of unfinished reservations. Might be a redundant method.
		ArrayList<Reservation> t = new ArrayList<Reservation>(); 
		for (Reservation s: listOfReservations) {
			String reservationTime = s.getReservationStartTime().toString().substring(0,10);
			if (dateBlock.equals( reservationTime) && s.getIsFinished()==false) {
				t.add(s);
			}
		}
		if (t.size()>0) {
			return t;
		}
		else {
			return null;
		}
	}
	
	//Database methods
	public static void saveDB(String fileName) { //writes to file Name by calling fwrite from ReservationDatabase
		ReservationDatabase.fwrite(fileName);
	}
	
	public static void loadDB(String fileName) { //passes file name to read to ReservationDataBase. Check table Id validility. Reserves table for the day. Replaces listOfReservations.
		ArrayList<Reservation> databaseReservations=ReservationDatabase.fread(fileName); //loaded reservations
		ArrayList<Integer> allTableIds=new ArrayList<Integer>(); //loaded reservations
		for (int i=0;i<TableLayoutManager.getAllTables().size();i++) {
			allTableIds.add(TableLayoutManager.getAllTables().get(i).getTableID());
		}
		for (int i=0;i<databaseReservations.size();i++) { //check for corresponding tableIds in reservatios loaded. Indicates Table was not loaded otherwise.
			if (!allTableIds.contains(databaseReservations.get(i).getTableID())) {
				System.out.println("Error. Not all orders have a corresponding TableID.\n Please load Table DataBase before loading Reservations. Loading aborted.");
				return;
			}
		}
		String nowDate=LocalDateTime.now().toString().substring(0, 10);
		String dataHour;
		for (int i=0;i<databaseReservations.size();i++) { //reserve tables for the day
			if (!databaseReservations.get(i).getReservationStartTime().toString().substring(0,10).equals(nowDate)) {
				continue;
			}
			else if(!databaseReservations.get(i).getReservationEndTime().toString().substring(14,16).equals("00")&&
					databaseReservations.get(i).getReservationEndTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13))) {
				dataHour=databaseReservations.get(i).getReservationEndTime().toString().substring(11, 13);
				TableLayoutManager.updateTable(databaseReservations.get(i).getTableID(), Integer.parseInt(dataHour), status.OCCUPIED);
			}
			else if (databaseReservations.get(i).getReservationEndTime().plusSeconds(1).isBefore(LocalDateTime.now())) {
				continue;
			}
			else if (databaseReservations.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13)) && databaseReservations.get(i).getIsAppeared()==true) {
				dataHour=databaseReservations.get(i).getReservationStartTime().toString().substring(11, 13);
				TableLayoutManager.updateTable(databaseReservations.get(i).getTableID(), Integer.parseInt(dataHour), status.OCCUPIED);
				if (!databaseReservations.get(i).getReservationEndTime().toString().substring(14,16).equals("00")) {
					dataHour=databaseReservations.get(i).getReservationEndTime().toString().substring(11, 13);
					TableLayoutManager.updateTable(databaseReservations.get(i).getTableID(), Integer.parseInt(dataHour), status.OCCUPIED);
				}
			}
			else if (databaseReservations.get(i).getReservationStartTime().isAfter(LocalDateTime.now())) {
				
				if (databaseReservations.get(i).getIsAppeared()==true) {
					dataHour=LocalDateTime.now().toString().substring(11, 13);
					TableLayoutManager.updateTable(databaseReservations.get(i).getTableID(), Integer.parseInt(dataHour), status.OCCUPIED);
					dataHour=databaseReservations.get(i).getReservationStartTime().toString().substring(11, 13);
					TableLayoutManager.updateTable(databaseReservations.get(i).getTableID(), Integer.parseInt(dataHour), status.OCCUPIED);
				}
				
				else{
					dataHour=databaseReservations.get(i).getReservationStartTime().toString().substring(11, 13);
					TableLayoutManager.updateTable(databaseReservations.get(i).getTableID(), Integer.parseInt(dataHour), status.RESERVED);
				}
				
			}
			
		}
		listOfReservations=ReservationDatabase.fread(fileName);
	}
	

	
}
