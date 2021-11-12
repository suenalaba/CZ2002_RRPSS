package restaurant_manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_database.StaffDatabase;
import restaurant_entity.Staff.Gender;
import restaurant_entity.MenuItem;
import restaurant_entity.Order;
import restaurant_entity.Staff;



public class StaffManager {
	//Attributes
	private static StaffManager instance = null;	
	private ArrayList<Staff> listOfStaffMembers = new ArrayList<Staff>();
	
	//Constructor
	public StaffManager() {	
		listOfStaffMembers=new ArrayList<Staff>();
	}
	
	//Get Instance
	public static StaffManager getInstance() {
		if (instance == null) {
	    	   instance = new StaffManager();
	           }
	       return instance;
    }
	
	//creation
	public void createStaffMember(String staffName, String staffTitle, Gender staffGender) {
		Staff staffMember=new Staff(staffName, staffTitle,staffGender);
		listOfStaffMembers.add(staffMember);
		System.out.println("Staff added.");
		staffMember.print();
	}
	
	//removal
	public void removeStaffMember(int staffID) {
		Staff staffMember=getStaff(staffID);
		staffMember.setIsWorking(false);
		System.out.println("Staff removed.");
		staffMember.print();
	}
	
	//get staff using ID
	public Staff getStaff(int staffID) {
		for (Staff o:listOfStaffMembers) {
			if (o.getStaffID()==staffID) {
				return o;
			}
		}
		return null;
	}
	
	//get staff
	public ArrayList<Staff> getListOfStaffMembers(){
		return this.listOfStaffMembers;
	}
	
	//update staff name or title
	public void updateStaffMember(String updatedStaffName, String updatedStaffTitle, int staffID) {
		Staff staffMember=getStaff(staffID);
		staffMember.setStaffName(updatedStaffName);
		staffMember.setStaffTitle(updatedStaffTitle);
		System.out.println("Staff details updated.");
		staffMember.print();
	}
	
	//display staffs in the system
	public void displayStaffList() {
 		System.out.println("List of Staffs (by staffID):");
 		for(int i=0; i<listOfStaffMembers.size(); i++) {
 			Staff staffMember=listOfStaffMembers.get(i);
 			if (staffMember.getIsWorking()==true) {
 				System.out.format("staffID: %d    staff Name: %s    staff Job Title:%s 	Status: Working\n ", staffMember.getStaffID(), staffMember.getStaffName(), staffMember.getStaffTitle());
 			}
 		}
 	}
	
	//save instance array
	public void saveDB(String saveFileName)  {
		try{
			StaffDatabase staffWrite=new StaffDatabase();
			staffWrite.fwrite(saveFileName);
		}
		catch(IOException e)
		{
			System.out.println("Failed to save to "+saveFileName);
			return;
		}
	}
	
	//load DB to array
	public void loadDB(String loadFileName) {
		try {
			StaffDatabase staffLoad=new StaffDatabase();
			this.listOfStaffMembers = staffLoad.fread(loadFileName);
		}
		catch(IOException e) {
			System.out.println("Failed to load "+loadFileName);
			return;
		}
		System.out.println("Loaded successfully from "+loadFileName);
	}
}
