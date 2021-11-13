package restaurant_manager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import restaurant_database.ReservationDatabase;
import restaurant_entity.Customer;
import restaurant_entity.Reservation;
import restaurant_entity.Table;
import restaurant_entity.Table.status;
/**
 * Stores an ArrayList of Reservation objects during runtime and methods to manipulate them
 * @author	Xin Pei
 * @version 4.5
 * @since	13-11-2021
 */
public class ReservationManager {
	/**
	 * For singleton pattern adherence. This ReservationManager instance persists throughout runtime.
	 */
	private static ReservationManager instance = null;
	/**
	 * ArrayList of Reservation objects during runtime
	 */
	ArrayList<Reservation> listOfReservations = new ArrayList<Reservation>();
	/**
	 * Default constructor for ReservationManager
	 */
	public ReservationManager() {
		listOfReservations=new ArrayList<Reservation>();
	}
	/**
	 * For singleton pattern adherence. 
	 * @return instance The static instance that persists throughout runtime.
	 */
	public static ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }
        return instance;
    }
	/**
	 * Creates and adds new Reservation object to listOfReservations
	 * @param customerID The CustomerID of Customer coming for Reservation
	 * @param pax The number of people coming to fill a table for the reservation
	 * @param startDateTime The StartDateTime of the reservation booked.
	 * @param tableID The tableID of the Table object that has the capacity to fit pax and was free during Reservation startDateTime.
	 */
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
	/**
	 * Creates a Reservation object for walk-in Customer and adds to listOfReservations
	 * @param pax The number of people coming to fill a table for the walk-in
	 * @param customerID The CustomerID of Customer coming for Reservation
	 */
	public void createWalkIn(int pax,String customerID) {
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		ArrayList<Reservation> allReservationsToday=getListOfReservationsToday();
		ArrayList<Reservation> unfinishedReservationsToday=new ArrayList<Reservation>();
		ArrayList<Reservation> protectedReservationsToday=new ArrayList<Reservation>();
		TableLayoutManager tableManager=TableLayoutManager.getInstance();
		ArrayList<Integer> possibleTables=tableManager.getMinTableList(pax); 
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
	/**
	 * Gets Reservation object from listOfReservations using ReservationID as reference
	 * @param reservationID reservationID of target Reservation object
	 * @return Reservation object if it exists in listOfReservations otherwise returns null
	 */
	public Reservation getReservation(int reservationID) { 
		for(int i=0;i<listOfReservations.size();i++) {
			if (listOfReservations.get(i).getReservationID()==reservationID) {
				return listOfReservations.get(i);
			}
		}
		return null;
	}
	/**
	 * Completes reservation by setting isAppeared attribute to true
	 * @param reservationID the targetReservation's ReservationID, used to retrieve from listOfReservations
	 */
	public void completeReservation(int reservationID) { 
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		Reservation toComplete=getReservation(reservationID);
		toComplete.setIsAppeared(true);
		tableLayoutM.occupyTable(toComplete.getTableID(), LocalDateTime.now());
		System.out.println("Reservation fulfilled.\n"
				+ "TableID:"+toComplete.getTableID()+"\n"
				+ "StartTime: "+toComplete.getReservationStartTime().toString().substring(11,16)+ "\n"
				+ "EndTime: " +toComplete.getReservationStartTime().plusHours(1).toString().substring(11,16));
	}
	/**
	 * Removes reservation by setting isFinished attribute to true
	 * @param reservationID the targetReservation's ReservationID, used to retrieve from listOfReservations
	 */
	public void removeReservation(int reservationID) {
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		Reservation toRemove=getReservation(reservationID);
		toRemove.setIsFinished(true);
		tableLayoutM.updateTableStatus(toRemove.getTableID(), toRemove.getReservationStartTime(), status.EMPTY);
		System.out.println("Reservation removed successfully!");
	}
	/**
	 * Updates Reservation with new startDateTime and TableID
	 * @param reservationID Used to get ReservationObject from listOfReservations by reference
	 * @param newStartDateTime New StartDateTime indicated by customer otherwise otherwise the value would be old startDateTime if not changed
	 * @param newTableID new tableID if change in pax or StartDateTime causes table change otherwise this value will be old tableID
	 */
	public void updateReservation(int reservationID,LocalDateTime newStartDateTime, int newTableID) { 
		Reservation toUpdate=getReservation(reservationID);
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		LocalDateTime oldStartDateTime=toUpdate.getReservationStartTime();
		int oldTableID=toUpdate.getTableID(); 
		toUpdate.setTableID(newTableID);
		toUpdate.setReservationStartTime(newStartDateTime);
		LocalDateTime newEndStartDateTime=newStartDateTime.plusHours(1);
		toUpdate.setReservationEndTime(newEndStartDateTime); 
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
	/**
	 * Checks if Reservation is today or future or past
	 * Only concerned with Reservation on current day
	 * Checks if Reservation has expired(15 minutes after Reservation start time and isAppeared==false)
	 * Expired reservation will be removed Table Object associated's hourBlock array
	 * Table objects houBlock Arrays with current days reservations and occupancy after day change(12am strikes)
	 */
	public void autoUpdate(){
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
		String nowDate=LocalDateTime.now().toString().substring(0, 10);
		LocalDateTime targetTime;
		for (int i=0;i<listOfReservations.size();i++) { 
			if (!listOfReservations.get(i).getReservationStartTime().toString().substring(0,10).equals(nowDate)) {
				continue;
			}
			else if (listOfReservations.get(i).getIsFinished()==true) {
				continue;
			}
			else if(!listOfReservations.get(i).getReservationEndTime().toString().substring(14,16).equals("00")&&
					listOfReservations.get(i).getReservationEndTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13))) {
				targetTime=listOfReservations.get(i).getReservationEndTime();
				tableLayoutM.updateTableStatus(listOfReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
			}
			else if (listOfReservations.get(i).getReservationEndTime().plusSeconds(1).isBefore(LocalDateTime.now())) {
				continue;
			}
			else if (listOfReservations.get(i).getReservationStartTime().toString().substring(11,13).equals(LocalDateTime.now().toString().substring(11,13)) && listOfReservations.get(i).getIsAppeared()==true && listOfReservations.get(i).getIsFinished()==false) {
				targetTime=listOfReservations.get(i).getReservationStartTime();
				tableLayoutM.updateTableStatus(listOfReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
				if (!listOfReservations.get(i).getReservationEndTime().toString().substring(14,16).equals("00")) {
					targetTime=listOfReservations.get(i).getReservationEndTime();
					tableLayoutM.updateTableStatus(listOfReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
				}
			}
			else if (listOfReservations.get(i).getReservationStartTime().isAfter(LocalDateTime.now().minusSeconds(899))) {
				
				if (listOfReservations.get(i).getIsAppeared()==true && listOfReservations.get(i).getIsFinished()==false && Integer.parseInt(LocalDateTime.now().toString().substring(11,13))>=9 && 
						Integer.parseInt(LocalDateTime.now().toString().substring(11,13))<=22) {
					targetTime=LocalDateTime.now();
					tableLayoutM.updateTableStatus(listOfReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
					targetTime=listOfReservations.get(i).getReservationStartTime();
					tableLayoutM.updateTableStatus(listOfReservations.get(i).getTableID(), targetTime, status.OCCUPIED);
				}
				
				else if(listOfReservations.get(i).getIsFinished()==false){
					targetTime=listOfReservations.get(i).getReservationStartTime();
					tableLayoutM.updateTableStatus(listOfReservations.get(i).getTableID(), targetTime, status.RESERVED);
				}
			}
		}
	}
	/**
	 * Looks for Reservation ID that is unfinished with associated table ID. sets isFinished boolean to true
	 * @param tableID is the tableID that has paid for order and is associated to Reservation object
	 */
	public void setIsFinishedByTableID(int tableID) {
		Reservation unfinishedToday=getUnfinishedReservationByTableIDNow(tableID);
		unfinishedToday.setIsFinished(true);
	}
	/**
	 * Prints details of Reservation
	 * @param reservationID of the Reservation object to look for in listOfReservations
	 */
	public void printReservation(int reservationID) {  
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
	/**
	 * Prints all Unfinished Reservations
	 */
	public void printAllUnfinishedReservation() {
		System.out.println("Standing Reservation(s):");
		System.out.println("=============================================================");
		ArrayList<Reservation> standingReservationList=getListOfUnfinishedReservations();
		if (standingReservationList.size()>0) {
		for (Reservation o:standingReservationList) {
			System.out.println("Reservation ID:"+o.getReservationID());
			System.out.println("Customer ID:"+o.getCustomerID());
			System.out.println("Table ID:"+o.getTableID());
			System.out.println("Pax:"+o.getPax());
			System.out.println("Reservation timeSlot:" + o.getReservationStartTime().toString().substring(0,10)+"|"+o.getReservationStartTime().toString().substring(11,13)+":00");
			System.out.println("=============================================================");
		}
		}
	}
	/**
	 * From reservationID, get an int index relative to listOfReservations
	 * @param reservationID of Reservation looking for
	 * @return reservation index in listOfReservations or -1 if not existing in listOfReservations
	 */
	public int reservationIDToIndex(int reservationID) { 
		for (int i=0;i<listOfReservations.size();i++) {
			if(listOfReservations.get(i).getReservationID()==reservationID) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Get Array List of Reservations of instance
	 * @return listOfReservations
	 */
	public ArrayList<Reservation> getListOfReservations() {
		return listOfReservations;
	}
	/**
	 * get Array List Of Reservations but only if Reservation objects isFInished attribute is false
	 * @return list Of Unfinished Reservations
	 */
	public ArrayList<Reservation> getListOfUnfinishedReservations() { 
		ArrayList<Reservation> t = new ArrayList<Reservation>(); 
		for (Reservation s : listOfReservations) {
			if (s.getIsFinished()==false) {
				t.add(s);
			}
		}
		return t;
	}
	/**
	 * Get Array list of Unfinished Reservations which contains Reservations which Start Date Time is on current day and isFinsihed attribute is false
	 * @return list of Unfinished Reservations today 
	 */
	public ArrayList<Reservation> getListOfUnfinishedReservationsToday() { 
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
	/**
	 * Get Array list of Reservations which contain contain Reservations which start Start Date Time is on current day
	 * @return list of Reservations today
	 */
	public ArrayList<Reservation> getListOfReservationsToday(){ 
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
	/**
	 * Get unfinished Reservations by table ID provided
	 * @param tableID TableID with associated Reservation
	 * @return Reservation object if there exists a reservation which isFinished attribute is false,isAppeared is true and tableID is the one provided
	 */
	public Reservation getUnfinishedReservationByTableIDNow(int tableID) { 
		ArrayList<Reservation> unfinished=getListOfUnfinishedReservations();
		if (unfinished.size()>0) {
			for (Reservation o:unfinished) {
				if (tableID==o.getTableID() && o.getIsAppeared()==true) {
					return o;
				}
			}
		}
		return null; 
	}
    /**
	 * Saves the instance's listOfReservations as string in a text file.
	 * @param textFileName The name of the the text file.
	 */
	public void saveDB(String textFileName) { 
		ReservationDatabase saver=new ReservationDatabase();
		try {
			saver.fwrite(textFileName);
		}
		catch(IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}

	}
    /**
	 * Loads to instance's listOfReservations from a text file
	 * @param textFileName The name of the text file.
	 */
	public void loadDB(String textFileName) { 
		ReservationDatabase loader=new ReservationDatabase();
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		ArrayList<Reservation> databaseReservations;
		try {
			databaseReservations = loader.fread(textFileName);
		} catch (IOException e1) {
			System.out.println("Failed to load "+textFileName);
			return;
		} 
		ArrayList<Integer> allTableIds=new ArrayList<Integer>(); 
		for (int i=0;i<tableLayoutM.getAllTables().size();i++) {
			allTableIds.add(tableLayoutM.getAllTables().get(i).getTableID());
		}
		for (int i=0;i<databaseReservations.size();i++) {
			if (!allTableIds.contains(databaseReservations.get(i).getTableID())&&databaseReservations.get(i).getIsFinished()==false) {
				System.out.println("Error. Not all Reservations have a corresponding TableID.\n Please load Table DataBase before loading Reservations. Loading aborted.");
				return;
			}
		}
		String nowDate=LocalDateTime.now().toString().substring(0, 10);
		LocalDateTime targetTime;
		for (int i=0;i<databaseReservations.size();i++) { 
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
			listOfReservations=loader.fread(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}
		System.out.println("Loaded successfully from "+textFileName);
	}
}
