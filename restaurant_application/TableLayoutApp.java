package restaurant_application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_entity.Reservation;
import restaurant_manager.ReservationManager;
import restaurant_manager.TableLayoutManager;

/**
 * manages the table layout of the restaurant by creating new tables and removing tables.
 * @author yi ze
 * @version 4.5
 * @since 13-11-2021
 */
public class TableLayoutApp {
	/**
	 * creates table with tableID and the table capacity for the number of pax of each table 
	 * number of pax of each table is of even number (2/4/6/8 or 10)
	 * tableID range from 0 to 100
	 */
	public void createTableQuery() { 
		Scanner sc=new Scanner(System.in);
		int tableID=-1, tableCapacity; 
		TableLayoutManager tableM=TableLayoutManager.getInstance();
		System.out.println("Enter table ID of new table: ");
		while (tableID==-1) {
			try {
				tableID=sc.nextInt();
				sc.nextLine();
				if (tableID>=0&&tableID<=100) {
					break;
				}
				else {
					tableID=-1;
					System.out.println("Table ID must be within the the range of 0 to 100(incl.).");
				}
			}catch(InputMismatchException e) {
				System.out.println("Invalid input. Try again: ");
			}
		}
		if(tableM.getTableIndex(tableID) == -1) { //if no existing tableID
			System.out.println("Select desired table capacity: ");
			System.out.println("2/4/6/8/10");
			try {
				tableCapacity = sc.nextInt();
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input");
				return;
			}
			if(tableCapacity == 2 || tableCapacity == 4 || tableCapacity == 6 || tableCapacity == 8 || tableCapacity ==10) {
				tableM.createTable(tableID, tableCapacity); 
				return;
			}
			else {
				System.out.println("Invalid table capacity");
				return;
			}
		}
		else {
			System.out.println("Table ID already exists. Returning to Table menu.");
			return;
		}
	} 
	
	/**
	 * remove table from system based on table Id
	 * table ID that has a future reservation or is currently occupied will be unable to be removed
	 */
	public void removeTableQuery() {
		TableLayoutManager tableM=TableLayoutManager.getInstance();
		ReservationManager reservationM=ReservationManager.getInstance();
		if (tableM.getLayout().getTableLayout().size()==0) {
			System.out.println("There are no tables yet in the restaurant. Returning to main menu.");
			return;
		}
		Scanner sc = new Scanner(System.in); 
		int tableID; 
		System.out.println("Enter table ID of table to be removed");
		ArrayList<Integer> reservationTables=new ArrayList<Integer>();
		for (Reservation o:reservationM.getListOfUnfinishedReservations()) {
			if (!reservationTables.contains(o.getTableID())) {
				reservationTables.add(o.getTableID());
			}
		}
		try {
			tableID = sc.nextInt();
			sc.nextLine();
			if (reservationTables.contains(tableID)) {
				System.out.println("Cannot remove table with future reservation or if currently occupied");
				return;
			}
			
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input.Returning to main menu.");
			return;
		}
		tableM.removeTable(tableID);
		
	}
	
}
