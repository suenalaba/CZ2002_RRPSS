package RRPSS;

import java.util.Date;

public class ReservationManager {
	
	/*
	public void createReservation(int tableID) { 
		//Reservation is made by providing details like date, time, #pax, name, contact, etc.
		Reservation reservation = new Reservation();
		reservation.setTableID(tableID);
	}
	*/
	
	//Reservaton[] r = {s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12}; //array of reservation objects
	Reservation[] r = new Reservation[100]; //declare array of reservation objects
	//r = new Reservation[20];  // allocating memory to array
	
	
	public void createReservation(int reservationID, String name, String contact, int pax, Date date, Date startTime) { 
		//Reservation reservation = new Reservation(reservationID); //reservation is local vaeriable
		//System.out.println("Adding new reservation..");
		r[reservationID] = new Reservation(reservationID); //add new reservation object into reservation list
		//System.out.println("New reservation obj added!");
		r[reservationID].setCustomerName(name);
		r[reservationID].setCustomerContactNum(contact);
		r[reservationID].setPax(pax);
		r[reservationID].setReservationDate(date);
		r[reservationID].setReservationStartTime(startTime);
		//reservation.setReservationEndTime(startTime+1hour); need to find format to add 1hr to start time.
		System.out.println("New reservation created! Reservation ID: " + reservationID);
		
	}
	
	
	public void removeReservation(int reservationID) { //WIP
		r[reservationID].setCustomerContactNum(null);
		r[reservationID].setCustomerName(null);
		//r[reservationID].setPax(null);
		r[reservationID].setReservationDate(null);
		r[reservationID].setReservationEndTime(null);
		r[reservationID].setReservationStartTime(null);
		//r[reservationID].setTableID(null);
		//what to remove in remove reservation?
		//System.out.println("Reservation removed successfully.");
	}
	
	public void checkReservation(int reservationID) { //change parameter to reservation instead
		System.out.println("\n" + "Checking Reservation " + reservationID + "...");
		
		System.out.println("\n ---Current reservation details---");
		System.out.println("Name: " + r[reservationID].getCustomerName());
		System.out.println("Contact No.: " + r[reservationID].getCustomerContactNum());
		System.out.println("Pax: " + r[reservationID].getPax());
		System.out.println("Date: " + r[reservationID].getReservationDate());
		System.out.println("Start Time: " + r[reservationID].getReservationStartTime());
		System.out.println("End Time: " + r[reservationID].getReservationEndTime());
		System.out.println("---All Reservation Details Displayed--- \n");
		
		
	}
	
	public void changeReservation(int reservationID) { //parameter change from table ID to resrevationID
		
		
		System.out.println("What would you like to edit?");
		
		/*
		System.out.println("---Current reservation details---");
		System.out.println("Name: " + r[reservationID].getCustomerName());
		System.out.println("Contact No.: " + r[reservationID].getCustomerContactNum());
		*/
		
	}

}
