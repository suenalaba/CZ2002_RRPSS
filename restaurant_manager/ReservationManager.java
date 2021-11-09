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
	

	public static void createReservation(String customerID, int pax, LocalDateTime startDateTime, int tableID) { //Creates Reservation and updates the database
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
	
	public static void createWalkIn(int pax,String customerID) {//gives table with buffer of 1 hour for reservations ahead
		ArrayList<Reservation> allReservationsToday=getListOfReservationsToday();
		ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
		ArrayList<Reservation> protectedReservationsToday=new ArrayList<Reservation>();
		ArrayList<Integer> possibleTables=TableLayoutManager.getMinTableList(pax); //table id list of sorted minimum tables
		int tableId;
		LocalDateTime nowDate=LocalDateTime.now();
		for (int i=0;i<allReservationsToday.size();i++) {
			if(allReservationsToday.get(i).getIsFinished()==false) {
				unfinishedReservationsToday.add(allReservationsToday.get(i));
			}
		}
		for (int i=0;i<unfinishedReservationsToday.size();i++) {
			if (unfinishedReservationsToday.get(i).getReservationStartTime().minusMinutes(60).isBefore(nowDate)) {
				protectedReservationsToday.add(unfinishedReservationsToday.get(i)); //table list of reserved tables
			}
		}
		for (int i=0;i<protectedReservationsToday.size();i++) {
			if (possibleTables.contains(protectedReservationsToday.get(i).getTableID())) {
				int removeIndex=possibleTables.indexOf(protectedReservationsToday.get(i).getTableID());
				possibleTables.remove(removeIndex);
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
	
	public static void completeReservation(int reservationID) {
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
	

	
	public static void removeReservation(int reservationID) {  //sets isFinished status. Frees corresponding table on same day.
		listOfReservations.get(reservationID).setIsFinished(true); //no more
		int tableId=listOfReservations.get(reservationID).getTableID();
		int hourBlock=Integer.parseInt(listOfReservations.get(reservationID).getReservationStartTime().toString().substring(11,13));
		String dateBlock=listOfReservations.get(reservationID).getReservationStartTime().toString().substring(0,10);
		if (LocalDateTime.now().toString().substring(0,10).equals(dateBlock)) {
			TableLayoutManager.updateTable(tableId, hourBlock, status.EMPTY);
		}
		System.out.println("Reservation removed successfully!");
	}
	

	
	public static void updateReservation(int reservationIndex,LocalDateTime newStartDateTime, int newTableID) {  //updates all relevant details. Updates table status if current day affected
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
	public static void checkReservation(int reservationID,int reservationIndex) {  //Prints reservation details
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
			if (s.getIsFinished()==false && s.getIsAppeared()==true) {
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
				if (tableID==o.getTableID()&&o.getReservationStartTime().toString().substring(11,13).equals(hourBlock)&&o.getReservationStartTime().toString().substring(0,10).equals(dateBlock)) {
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
		ReservationDatabase saver=new ReservationDatabase();
		try {
			saver.fwrite(fileName);
		}
		catch(IOException e) {
			System.out.println("Failed to load "+fileName);
			return;
		}

	}
	
	public static void loadDB(String fileName) { //passes file name to read to ReservationDataBase. Check table Id validility. Reserves table for the day. Replaces listOfReservations.
		ReservationDatabase loader=new ReservationDatabase();
		
		ArrayList<Reservation> databaseReservations;
		try {
			databaseReservations = loader.fread(fileName);
		} catch (IOException e1) {
			System.out.println("Failed to load "+fileName);
			return;
		} 
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
		try {
			listOfReservations=loader.fread(fileName);
		} catch (IOException e) {
			System.out.println("Failed to load "+fileName);
			return;
		}
	}
}
