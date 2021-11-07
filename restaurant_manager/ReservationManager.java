package RRPSS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
		
		int tableID = (TableLayoutManager.getEmptyTableAtHour(pax, HourlyTime));
		
		if (tableID == -1) {
			System.out.println("No tables are available.");
			return;
		}
		
		LocalDateTime endDateTime = dateTime.plusHours(1); //create end time for reservation
		
		System.out.println("\n DateTime requested... \nStart:" + dateTime + "\nEnd: " + endDateTime);
		System.out.println("Table is available! TableID : " + tableID);
		

		//TableLayoutManager.checkTable(pax,LocalDateTime starttime) //returns table ID
		//TableLayoutManager.updateTable(tableID, int startHour) //updates table
		
		//System.out.println("Your reservation ID is" + reservationID);
		
		createReservation(customerID, pax, dateTime);
	}
	
	//reservationID = array list index
	public static void createReservation(String customerID, int pax, LocalDateTime startDateTime) { //WIP - tableID
		//Reservation reservation = new Reservation(reservationID); //reservation is local variable
		//System.out.println("Adding new reservation..");
		int newReservationIndex = r.size() + 1; //size of array list. counting from 1.
		
		
		
		
		Reservation newReservationObject = new Reservation();
		r.add(newReservationObject); //add new reservation empty object into arraylist
		
		//set all the user inputs into the reservation
		newReservationObject.setCustomerID(customerID);
		newReservationObject.setPax(pax);
		newReservationObject.setReservationStartTime(startDateTime);
		
		LocalDateTime endDateTime = startDateTime.plusHours(1);
		newReservationObject.setReservationEndTime(endDateTime);
		
		r.set(newReservationIndex, newReservationObject); //set new reservation into reservation array list
		
		System.out.println("New reservation created! Your reservation ID is " + newReservationObject.getCounter());
		//System.out.println("Reservation created! /n Your reservation ID is" + reservationID);
		
		//write to database----
		
		//ReservationDatabase.writeReservationDatabase();
		
		
	}
	
	public ArrayList<Reservation> getR() {
		return r;
	}
	
	
	public void removeReservation(int reservationID) { 
		r.get(reservationID).setIsFinished(false);
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
	
	public static void checkReservation(int reservationID) { 
		System.out.println("\n" + "Checking Reservation " + reservationID + "...");
		reservationID = reservationID - 1; //minus 1 to get the correct one on the array
		//r.get(reservationID);
		
		System.out.println("\n ---Current reservation details---");
		System.out.println("ReservationID: " + r.get(reservationID).getReservationID());
		System.out.println("Name: " + r.get(reservationID).getCustomerName());
		System.out.println("Contact No.: " + r.get(reservationID).getCustomerContactNum());
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
	
	public ArrayList<Reservation> checkReservationByTableID(int tableID) {
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
		
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //create new array to store all reservation objects that match.
		
		for (Reservation s : r) {
			if(s.getTableID() == tableID) {
				t.add(s);
			}
		}
		
		return t;
		
		
		
	}
	
	public void changeReservation(int reservationID) { //WIP
		
		
		System.out.println("What would you like to edit?");
		
		System.out.println("1 - Pax, 2 - Change Reservation startDate, 3 - Exit");
		
		Scanner sc1 = new Scanner(System.in);
		int userInput = sc1.nextInt();
		
		switch(userInput) {
		
		case 1:
			System.out.println("Enter new name:");
			
			Scanner sc2 = new Scanner(System.in);
			String newName = sc2.next();
			r.get(reservationID).setCustomerName(newName);
			
			System.out.println("Updated name: " + newName);
			break;
		case 2:
			System.out.println("Enter contact number:");
			
			Scanner sc3 = new Scanner(System.in);
			String newContact = sc3.next();
			r.get(reservationID).setCustomerContactNum(newContact);
			System.out.println("Updated Contact No.: " + newContact);
			break;
		case 3: //WIP
			System.out.println("Enter new pax:");
			
			Scanner sc4 = new Scanner(System.in);
			int newPax = sc4.nextInt();
			
			break;
			
		
		/*
		System.out.println("---Current reservation details---");
		System.out.println("Name: " + r[reservationID].getCustomerName());
		System.out.println("Contact No.: " + r[reservationID].getCustomerContactNum());
		*/
		
	}
	

}
}
