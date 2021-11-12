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
	private static ReservationManager instance = null;
	ArrayList<Reservation> listOfReservations = new ArrayList<Reservation>();
	
	//Constructor
	public ReservationManager() {
		listOfReservations=new ArrayList<Reservation>();
	}
	
	//Get Instance
	public static ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }
        return instance;
    }
	
	//Adds new Reservation
	public void createReservation(String customerID, int pax, LocalDateTime startDateTime, int tableID) {
		Reservation newReservationObject = new Reservation();
		listOfReservations.add(newReservationObject); 
		System.out.println("Creating reservation...");
		newReservationObject.setCustomerID(customerID);
		newReservationObject.setPax(pax);
		newReservationObject.setReservationStartTime(startDateTime);
		newReservationObject.setTableID(tableID);
		LocalDateTime endDateTime = startDateTime.plusHours(1);
		newReservationObject.setReservationEndTime(endDateTime);
		System.out.println("New reservation created! Your reservation ID is " + newReservationObject.getReservationID());
		System.out.println("TableID:"+newReservationObject.getTableID()+"\n"
				+ "StartTime: "+newReservationObject.getReservationStartTime().toString().substring(11,16)+ "\n"
				+ "EndTime: " +newReservationObject.getReservationStartTime().plusHours(1).toString().substring(11,16));
	}
	
	public void createWalkIn(int pax,String customerID) {//creates reservation, occupies table
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		ArrayList<Reservation> allReservationsToday=getListOfReservationsToday();
		ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
		ArrayList<Reservation> protectedReservationsToday=new ArrayList<Reservation>();
		TableLayoutManager tableManager=TableLayoutManager.getInstance();
		ArrayList<Integer> possibleTables=tableManager.getMinTableList(pax); //table id list of sorted minimum tables
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
		tableLayoutM.occupyTable(tableId,LocalDateTime.now());
		System.out.println("Walk-in Registered.\n"
				+ "TableID:"+tableId+"\n"
				+ "StartTime: "+LocalDateTime.now().toString().substring(11,16)+ "\n"
				+ "EndTime: " +LocalDateTime.now().plusHours(1).toString().substring(11,16));
	}
	
	public Reservation getReservation(int reservationID) { //returns Reservation for Reservation ID
		for(int i=0;i<listOfReservations.size();i++) {
			if (listOfReservations.get(i).getReservationID()==reservationID) {
				return listOfReservations.get(i);
			}
		}
		return null;
	}
	
	//completes reservation
	public void completeReservation(int reservationID) { //set isappeared for the reservation to true and occupies table
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		Reservation toComplete=getReservation(reservationID);
		toComplete.setIsAppeared(true);
		tableLayoutM.occupyTable(toComplete.getTableID(), LocalDateTime.now());
		System.out.println("Reservation fulfilled.\n"
				+ "TableID:"+toComplete.getTableID()+"\n"
				+ "StartTime: "+toComplete.getReservationStartTime().toString().substring(11,16)+ "\n"
				+ "EndTime: " +toComplete.getReservationStartTime().plusHours(1).toString().substring(11,16));
		
	}
	

	
	public void removeReservation(int reservationID) { //updates reservation and table
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		Reservation toRemove=getReservation(reservationID);
		toRemove.setIsFinished(true);//reservation cleared
		tableLayoutM.updateTableStatus(toRemove.getTableID(), toRemove.getReservationStartTime(), status.EMPTY);
		System.out.println("Reservation removed successfully!");
	}
	

	
	public void updateReservation(int reservationID,LocalDateTime newStartDateTime, int newTableID) {  //updates all relevant details. Updates table status if current day affected
		Reservation toUpdate=getReservation(reservationID);
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		LocalDateTime oldStartDateTime=toUpdate.getReservationStartTime();
		int oldTableID=toUpdate.getTableID(); //reservation attribute update (start)
		toUpdate.setTableID(newTableID);
		toUpdate.setReservationStartTime(newStartDateTime);
		LocalDateTime newEndStartDateTime=newStartDateTime.plusHours(1);
		toUpdate.setReservationEndTime(newEndStartDateTime); //reservation attribute update (end)
		String currentDate = LocalDateTime.now().toString().substring(0,10);
		String oldDate =oldStartDateTime.toString().substring(0,10);
		String compareDate = newStartDateTime.toString().substring(0,10);
		if (currentDate.equals(oldDate)) {
			tableLayoutM.updateTableStatus(oldTableID, oldStartDateTime, status.EMPTY); //if old date and current date is today. free it as updated
		}
		if (currentDate.equals(compareDate)) {
			tableLayoutM.updateTableStatus(newTableID, newStartDateTime, status.RESERVED); //if new date and current date is today. update status
			}
		}
	
	public void autoUpdate(){//updates periodically 15 minutes grace period to appear
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		LocalDateTime nowDateTime=LocalDateTime.now();
		ArrayList<Reservation> allReservations=getListOfReservations();
		ArrayList<Reservation> allUnfinished=new ArrayList<Reservation>();
		for (int i=0;i<allReservations.size();i++) {
			if (allReservations.get(i).getIsFinished()==false && allReservations.get(i).getIsAppeared()==false) {
				allUnfinished.add(allReservations.get(i));
			}
		}
		for (int i=0;i<allUnfinished.size();i++) {
			if (LocalDateTime.now().minusMinutes(15).isAfter(allUnfinished.get(i).getReservationStartTime())) {
				int hour=allUnfinished.get(i).getReservationStartTime().getHour();
				Table aTable=tableLayoutM.getTable(allUnfinished.get(i).getTableID());
				if (aTable.getHourBlock()[hour]==status.RESERVED && allUnfinished.get(i).getReservationStartTime().getDayOfYear()==LocalDateTime.now().getDayOfYear()
						&& allUnfinished.get(i).getReservationStartTime().getYear()==LocalDateTime.now().getYear()) {
					tableLayoutM.updateTableStatus(allUnfinished.get(i).getTableID(),LocalDateTime.now(), status.EMPTY);
					allUnfinished.get(i).setIsFinished(true);
				}
				else if(aTable.getHourBlock()[hour]==status.OCCUPIED && allUnfinished.get(i).getReservationStartTime().getDayOfYear()==LocalDateTime.now().getDayOfYear()
						&& allUnfinished.get(i).getReservationStartTime().getYear()==LocalDateTime.now().getYear()) {
					continue;
				}
				else {
					allUnfinished.get(i).setIsFinished(true);
				}
			}
		}
	}
	
	public void setIsFinishedByTableID(int tableID) {
		Reservation unfinishedToday=getUnfinishedReservationByTableIDNow(tableID);
		unfinishedToday.setIsFinished(true);
	}
	
	
	//check methods
	public void printReservation(int reservationID) {  //Prints reservation details
		Reservation toPrint=getReservation(reservationID);
		CustomerManager customerM=CustomerManager.getInstance();
		System.out.println("\n" + "Checking Reservation " + reservationID + "...");
		System.out.println("\n ---Current reservation details---");
		System.out.println("ReservationID: " + toPrint.getReservationID());
		Customer theCustomer = customerM.getCustomer(toPrint.getCustomerID());
		System.out.println("ReservationID: " + toPrint.getCustomerID());
		System.out.println("Name: " + theCustomer.getcustomerName());
		System.out.println("Contact No.: " + theCustomer.getphoneNumber());
		System.out.println("Pax: " + toPrint.getPax());
		System.out.println("Start date time: " + toPrint.getReservationStartTime());
		System.out.println("End date time:" + toPrint.getReservationEndTime());
		System.out.println("---All Reservation Details Displayed--- \n");
	}
	
	//print method
	public void printAllUnfinishedReservation() {
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
	public int reservationIDToIndex(int reservationID) { //reservation ID to listOfReservations array index. Only works on the static array.
		for (int i=0;i<listOfReservations.size();i++) {
			if(listOfReservations.get(i).getReservationID()==reservationID) {
				return i;
			}
		}
		return -1;
	}
	
	//Get methods
	public ArrayList<Reservation> getListOfReservations() {
		return listOfReservations;
	}
	
	public ArrayList<Reservation> getListOfUnfinishedReservations() { //gets arraylist of unfinished reservations
		ArrayList<Reservation> t = new ArrayList<Reservation>(); //new array of reservation objs to store
		for (Reservation s : listOfReservations) {
			if (s.getIsFinished()==false) {
				t.add(s);
			}
		}
		return t;
	}
	
	public ArrayList<Reservation> getListOfUnfinishedReservationsToday() { //gets arraylist of unfinished reservations today
		ArrayList<Reservation> t = new ArrayList<Reservation>(); 
		LocalDateTime today=LocalDateTime.now();
		int todayDay=today.getDayOfYear();
		int todayYear=today.getYear();
		for (Reservation s : listOfReservations) {
			if (s.getIsFinished()==false && todayDay==s.getReservationStartTime().getDayOfYear() && todayYear==s.getReservationStartTime().getYear()) {
				t.add(s);
			}
		}
		return t;
	}
	
	public ArrayList<Reservation> getListOfFinishedReservations() { //gets arraylist of finished reservations
		ArrayList<Reservation> t = new ArrayList<Reservation>(); 
		for (Reservation s : listOfReservations) {
			if (s.getIsFinished()==true) {
				t.add(s);
			}
		}
		return t;
	}
	
	public ArrayList<Reservation> getListOfReservationsToday(){ //Returns reservation arraylist for current day. finished and unfinished
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
	
	public Reservation getUnfinishedReservationByTableIDNow(int tableID) { //give tableID, return Reservation now
		ArrayList<Reservation> unfinished=getListOfUnfinishedReservations();
		if (unfinished.size()>0) {
			for (Reservation o:unfinished) {
				if (tableID==o.getTableID() && o.getIsAppeared()==true) {
					return o;
				}
			}
		}
		return null; //no reservation found for tableID now.
	}
	
	public ArrayList<Reservation> getUnfinishedReservationByDate(String dateBlock) { //takes yyyy-MM-dd. Provide dateblock and returns array of unfinished reservations. Might be a redundant method.
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
	public void saveDB(String fileName) { //writes to file Name by calling fwrite from ReservationDatabase
		ReservationDatabase saver=new ReservationDatabase();
		try {
			saver.fwrite(fileName);
		}
		catch(IOException e) {
			System.out.println("Failed to load "+fileName);
			return;
		}

	}
	
	public void loadDB(String fileName) { //passes file name to read to ReservationDataBase. Check table Id validility. Reserves table for the day. Replaces listOfReservations.
		ReservationDatabase loader=new ReservationDatabase();
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		ArrayList<Reservation> databaseReservations;
		try {
			databaseReservations = loader.fread(fileName);
		} catch (IOException e1) {
			System.out.println("Failed to load "+fileName);
			return;
		} 
		ArrayList<Integer> allTableIds=new ArrayList<Integer>(); //loaded reservations
		for (int i=0;i<tableLayoutM.getAllTables().size();i++) {
			allTableIds.add(tableLayoutM.getAllTables().get(i).getTableID());
		}
		for (int i=0;i<databaseReservations.size();i++) { //check for corresponding tableIds in reservatios loaded. Indicates Table was not loaded otherwise.
			if (!allTableIds.contains(databaseReservations.get(i).getTableID())&&databaseReservations.get(i).getIsFinished()==false) {
				System.out.println("Error. Not all Reservations have a corresponding TableID.\n Please load Table DataBase before loading Reservations. Loading aborted.");
				return;
			}
		}
		String nowDate=LocalDateTime.now().toString().substring(0, 10);
		LocalDateTime targetTime;
		for (int i=0;i<databaseReservations.size();i++) { //reserve tables for the day
			if (!databaseReservations.get(i).getReservationStartTime().toString().substring(0,10).equals(nowDate)) {
				continue;
			}
			else if (databaseReservations.get(i).getIsFinished()==true) {
				continue;
			}
			else if(!databaseReservations.get(i).getReservationEndTime().toString().substring(14,16).equals("00")&&
					databaseReservations.get(i).getReservationEndTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13))) {
				targetTime=databaseReservations.get(i).getReservationEndTime();
				tableLayoutM.updateTableStatus(databaseReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
			}
			else if (databaseReservations.get(i).getReservationEndTime().plusSeconds(1).isBefore(LocalDateTime.now())) {
				continue;
			}
			else if (databaseReservations.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13)) && databaseReservations.get(i).getIsAppeared()==true && databaseReservations.get(i).getIsFinished()==false) {
				targetTime=databaseReservations.get(i).getReservationStartTime();
				tableLayoutM.updateTableStatus(databaseReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
				if (!databaseReservations.get(i).getReservationEndTime().toString().substring(14,16).equals("00")) {
					targetTime=databaseReservations.get(i).getReservationEndTime();
					tableLayoutM.updateTableStatus(databaseReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
				}
			}
			else if (databaseReservations.get(i).getReservationStartTime().isAfter(LocalDateTime.now().minusSeconds(899))) {
				
				if (databaseReservations.get(i).getIsAppeared()==true && databaseReservations.get(i).getIsFinished()==false && Integer.parseInt(LocalDateTime.now().toString().substring(11,13))>=9 && 
						Integer.parseInt(LocalDateTime.now().toString().substring(11,13))<=22) {
					targetTime=LocalDateTime.now();
					tableLayoutM.updateTableStatus(databaseReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
					targetTime=databaseReservations.get(i).getReservationStartTime();
					tableLayoutM.updateTableStatus(databaseReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
				}
				
				else if(databaseReservations.get(i).getIsFinished()==false){
					targetTime=databaseReservations.get(i).getReservationStartTime();
					tableLayoutM.updateTableStatus(databaseReservations.get(i).getTableID(), targetTime, status.RESERVED);
				}
				
			}
			
		}
		try {
			listOfReservations=loader.fread(fileName);
		} catch (IOException e) {
			System.out.println("Failed to load "+fileName);
			return;
		}
		System.out.println("Loaded successfully from "+fileName);
	}
}
