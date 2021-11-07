package RRPSS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import RRPSS.Table.status;

public class ReservationManager {
	
	//Reservaton[] r = {s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12}; //array of reservation objects
	//Reservation[] r = new Reservation[100]; //declare array of reservation objects
	//r = new Reservation[20];  // allocating memory to array
	
	static ArrayList<Reservation> r = new ArrayList<Reservation>();
	//private static int counter = 1;
	public static final String DELIMITER = ",";
	
	
	
	public static void createReservationQuery() {
		System.out.println("\n Generating reservation ID...");
		
		
		//int reservationID = counter;
		//counter = counter + 1;
		//WIP
		//check if reservation ID is new. If not, ask to enter a reservation id that doesn't exist.
		//or generate a new reservation ID (biggest int that is not in reservation id)
		
		System.out.println("\n Enter customerID:");
		Scanner scCustomerID = new Scanner(System.in);
		String customerID = scCustomerID.nextLine();
		
		/*
		System.out.println("\n Enter contact:");
		Scanner scContact = new Scanner(System.in);
		String contact = scContact.nextLine();
		*/
		
		System.out.println("\n Enter pax:"); //find tables that have the available capacity first, then narrow down to available dateTimes.
		Scanner scPax = new Scanner(System.in);
		int pax = scPax.nextInt();
		
		//System.out.println("\n Tables that have that available size"); 
		//return tables that can hold that size.
		
		
		//dateTime works!
		System.out.println("\n Enter start date and time (in the format: yyyy-MM-dd HH):");
		Scanner scDate = new Scanner(System.in);
		String date = scDate.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		
		String sHourlyTime = date.substring(11,13);
		System.out.println(sHourlyTime);
		int HourlyTime = Integer.parseInt(sHourlyTime);
		
		//LocalDateTime LocalDateTime.now();
		String currentDate = LocalDateTime.now().toString().substring(0,10);
		String compareDate = date.substring(0,10);
		String compareHr = date.substring(11,13);
		ArrayList<Integer> tableIDOverlap = new ArrayList<Integer>(); //Checks future date and time reservations against comparison.Stores table
		
		int tableID;
		if (compareDate != currentDate) { //runs if given date is future date.
			for(Reservation s : getUnfinishedReservations()) {
				if(compareDate == s.getReservationStartTime().toString().substring(0,10) ) {
					if(compareHr == s.getReservationStartTime().toString().substring(11,13)) {
						tableIDOverlap.add(s.getTableID());
					}
				}
				
			}
			for (Integer s:TableLayoutManager.getMinTableList(pax)) {
				if (!tableIDOverlap.contains(s)) { //available table in future
					tableID=s;
					createReservation(customerID, pax, dateTime,tableID);
					return;
				}
			}
			
			//if no table in future
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
		

		//TableLayoutManager.checkTable(pax,LocalDateTime starttime) //returns table ID
		//TableLayoutManager.updateTable(tableID, int startHour) //updates table
		
		//System.out.println("Your reservation ID is" + reservationID);
		
		
	}
	
	//reservationID = array list index
	public static void createReservation(String customerID, int pax, LocalDateTime startDateTime, int tableID) { //WIP - tableID
		//Reservation reservation = new Reservation(reservationID); //reservation is local variable
		//System.out.println("Adding new reservation..");
		//int newReservationIndex = r.size() + 1; //size of array list. counting from 1.
		
		
		Reservation newReservationObject = new Reservation();
		r.add(newReservationObject); //add new reservation empty object into arraylist
		
		System.out.println("Creating reservation...");
		
		//set all the user inputs into the reservation
		newReservationObject.setCustomerID(customerID);
		newReservationObject.setPax(pax);
		newReservationObject.setReservationStartTime(startDateTime);
		newReservationObject.setTableID(tableID);
		
		LocalDateTime endDateTime = startDateTime.plusHours(1);
		newReservationObject.setReservationEndTime(endDateTime);
		
		
		//r.set((Reservation.getCounter()-1), newReservationObject);
		
		//r.set(newReservationIndex, newReservationObject); //set new reservation into reservation array list
		
		System.out.println("New reservation created! Your reservation ID is " + newReservationObject.getReservationID());
		//System.out.println("Reservation created! /n Your reservation ID is" + reservationID);
		
		//write to database----
		
		//ReservationDatabase.writeReservationDatabase();
		
		
	}
	
	public ArrayList<Reservation> getR() {
		return r;
	}
	
	
	public void removeReservation(int reservationID) { 
		r.get(reservationID).setIsFinished(true); //no more
		/*
		r.get(reservationID).setReservationStartTime(null);
		r.get(reservationID).setReservationEndTime(null);
		r.get(reservationID).setCustomerID(null);
		r.get(reservationID).setPax(null);
		*/
		
		//r[reservationID] = null;
		System.out.println("Reservation removed successfully!");
		/*
		r[reservationID].setCustomerContactNum(null);
		r[reservationID].setCustomerName(null);
		//r[reservationID].setPax(null);
		r[reservationID].setReservationDate(null);
		r[reservationID].setReservationEndTime(null);
		r[reservationID].setReservationStartTime(null);
		*/
		//r[reservationID].setTableID(null);
		//what to remove in remove reservation?
		//System.out.println("Reservation removed successfully.");
	}
	
	public static void checkReservationQuery(){
		System.out.println("\n Enter your reservationID:");
		Scanner scReservationID = new Scanner(System.in);
		int reservationID = scReservationID.nextInt();
		
		checkReservation(reservationID);
	}
	
	public static void checkReservation(int reservationID) { 
		System.out.println("\n" + "Checking Reservation " + reservationID + "...");
		reservationID = reservationID - 1; //minus 1 to get the correct one on the array
		//r.get(reservationID);
		
		System.out.println("\n ---Current reservation details---");
		System.out.println("ReservationID: " + r.get(reservationID).getReservationID());
		Customer theCustomer = CustomerManager.retrieveCustomerbyIDinput(r.get(reservationID).getCustomerID());
		//theCustomer.getcustomerName();
		System.out.println("ReservationID: " + r.get(reservationID).getCustomerID());
		System.out.println("Name: " + theCustomer.getcustomerName());
		System.out.println("Contact No.: " + theCustomer.getphoneNumber());
		System.out.println("Pax: " + r.get(reservationID).getPax());
		System.out.println("Start date time: " + r.get(reservationID).getReservationStartTime());
		System.out.println("End date time:" + r.get(reservationID).getReservationEndTime());
		System.out.println("---All Reservation Details Displayed--- \n");
		
		/*
		System.out.println("Name: " + r[reservationID].getCustomerName());
		System.out.println("Contact No.: " + r[reservationID].getCustomerContactNum());
		System.out.println("Pax: " + r[reservationID].getPax());
		System.out.println("Date: " + r[reservationID].getReservationDate());
		System.out.println("Start Time: " + r[reservationID].getReservationStartTime());
		System.out.println("End Time: " + r[reservationID].getReservationEndTime());
		System.out.println("Table ID: " + r[reservationID].getTableID());
		*/
		
	}
	
	public Reservation checkReservationByTableID(int tableID, LocalDateTime currentDateTime) {
		/*
		for (Reservation s : r) {
			if (s.getTableID() == tableID) {
				checkReservation(s.getReservationID());
			}
		}
		
		*/
		
		/*
		for (int i = 0; i < 100; i++) { //counts 0-99
			if(r.get(i) == null) { //skip all null reservations
				//System.out.println("Null" + i);
			}
			else {
				if(r.get(i).getTableID() == tableID) {
					checkReservation(i);
					//System.out.println("Checking Reservation");
				}
			}
			
		}
		*/
		
		/*
		 * Check if reservation exists for the table. Then check if the current date is correct with the date given.
		 */
		
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //create new array to store all reservation objects for the given table.
		
		for (Reservation s : r) {
			if(s.getTableID() == tableID) {
				t.add(s); //reservations for given table
			}
		}
		
		/*
		Date start, end;   // assume these are set to something
		Date current;      // the date in question

		return start.compareTo(current) * current.compareTo(end) > 0;
		*/
		
		for (Reservation v : t) {
			if(v.getReservationStartTime().compareTo(currentDateTime)*currentDateTime.compareTo(v.getReservationEndTime()) > 0) { //compare checking out time to booking timeslot to see if it is in the slot
				return v;
			}
		}
		
		return null;
		
		
		
		
	}
	
	public ArrayList<Reservation> checkReservationByDate(LocalDateTime DateTime) {
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //new array of reservation objs to store
		
		for (Reservation s: r) {
			String reservationTime = s.getReservationStartTime().toString().substring(0,10);
			if (DateTime.toString().substring(0,10) == reservationTime) {
				t.add(s);
			}
		}
		
		return t;
	}
	
	public ArrayList<Reservation> checkReservationToday(){
		
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //new array of reservation objs to store
		for (Reservation s : r) {
			String reservationTime = s.getReservationStartTime().toString().substring(0,10);
			
			if ( LocalDateTime.now().toString().substring(0,10) == reservationTime) {
				t.add(s);
			}
		}
		
		return t;
		
	}
	

	
	public static ArrayList<Reservation> getUnfinishedReservations() { //gets arraylist of unfinished reservations
		
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //new array of reservation objs to store
		for (Reservation s : r) {
			if (s.getIsFinished()==false) {
				t.add(s);
			}
		}
		return t;
	}
	
	public static ArrayList<Reservation> getfinishedReservations() { //gets arraylist of unfinished reservations
		
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //new array of reservation objs to store
		for (Reservation s : r) {
			if (s.getIsFinished()==false) {
				t.add(s);
			}
		}
		return t;
	}
	
	public void changeReservationQuery() {
		System.out.println("Whats your Reservation ID?");
		Scanner sc = new Scanner(System.in);
		int reservationID=-1;
		while (reservationID==-1) {
			try {
				reservationID=sc.nextInt();
				sc.nextLine();
				if (!getUnfinishedReservations().contains(reservationID)) {
					reservationID=-1;
					System.out.println("Invalid reservationID. Please Try Again");
				}
			}
			catch(Exception e){
				System.out.println("Invalid Input. Try Again: ");
			}
		}
		System.out.println("What would you like to edit?");
		System.out.println("1. Pax\n2.Change Reservation startDate/startTime\n3.Exit");
		int choice=sc.nextInt();
		int newPax
		sc.nextLine();
		if (choice!=1 && choice!=2) {
			return;
		}
		do {
			switch(choice) {
			case 1:
				
			case 2:
			case 3:
				return;
				break;
			default:
				break;
			}
		}while (choice>=1||choice<=3)
		
		
	}
	
	public static void changeReservation(int reservationID) { //WIP
		
		
		System.out.println("What would you like to edit?");
		
		System.out.println("1 - Pax, 2 - Change Reservation startDate, 3 - Exit");
		
		Scanner sc1 = new Scanner(System.in);
		int userInput = sc1.nextInt();
		
		switch(userInput) {
		
		case 1:
			System.out.println("New pax");
			
			Scanner sc2 = new Scanner(System.in);
			int newName = sc2.nextInt();
			
			for (Reservation s : r) {
				if (s.getReservationID() == reservationID) { //if reservationID obj is correct
					int hour = Integer.parseInt(s.getReservationStartTime().toString().substring(11,13));
					int tableID = (TableLayoutManager.getEmptyTableAtHour(s.getPax(), hour)); //find table
					if (tableID == -1) {
						System.out.println("No tables are available.");
					}
					else { //table is available
						s.setTableID(tableID); //set new tableID
						
						System.out.println("Updated table: " + tableID);
						System.out.println("---New reservation details---");
						ReservationManager.checkReservation(reservationID); //print reservation details
					}
				}
			}
			
			
			break;
		case 2:
			System.out.println("New Reservation startDate:");
			
			Scanner sc3 = new Scanner(System.in);
			String sc3obj = sc3.nextLine();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
			LocalDateTime newDateTime = LocalDateTime.parse(sc3obj, formatter);
			
			for (Reservation s : r) {
				if(s.getReservationID() == reservationID) {
					if(TableLayoutManager.getEmptyTableAtHour(s.getPax(), HourlyTime) == -1) {
						
					}
					
				}
			}
			
			
			break;
		case 3: 
			System.out.println("Exited");
			break;
			
		
		/*
		System.out.println("---Current reservation details---");
		System.out.println("Name: " + r[reservationID].getCustomerName());
		System.out.println("Contact No.: " + r[reservationID].getCustomerContactNum());
		*/
		
	}
	

}
}
