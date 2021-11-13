package restaurant_manager;

import java.io.IOException;
import java.util.ArrayList;

import restaurant_database.StaffDatabase;
import restaurant_entity.Staff.Gender;
import restaurant_entity.Staff;


/**
 * Stores an ArrayList of Staff objects during runtime and methods to manipulate them
 * @author Joshua
 * @version	4.5
 * @since 	13-11-2021
 */
public class StaffManager {
	/**
	 * For singleton pattern adherence. This StaffManager instance persists throughout runtime.
	 */
	private static StaffManager instance = null;	
	/**
     * ArrayList of Staff objects stored during runtime
     */
	private ArrayList<Staff> listOfStaffMembers = new ArrayList<Staff>();
	/**
	 * Default constructor for StaffManager
	 */
	public StaffManager() {	
		listOfStaffMembers=new ArrayList<Staff>();
	}
	/**
	 * For singleton pattern adherence. 
	 * @return instance The static instance that persists throughout runtime.
	 */
	public static StaffManager getInstance() {
		if (instance == null) {
	    	   instance = new StaffManager();
	           }
	       return instance;
    }
	/**
	 * Creates and adds new Staff object to listOfStaffMembers
	 * @param staffName new Staff name
	 * @param staffTitle new Staff title
	 * @param staffGender new Staff gender
	 */
	public void createStaffMember(String staffName, String staffTitle, Gender staffGender) {
		Staff staffMember=new Staff(staffName, staffTitle,staffGender);
		listOfStaffMembers.add(staffMember);
		System.out.println("Staff added.");
		staffMember.print();
	}
	/**
	 * Removes staff by labeling isWorking to false
	 * @param staffID the StaffID of corresponding Staff object
	 */
	public void removeStaffMember(int staffID) {
		Staff staffMember=getStaff(staffID);
		staffMember.setIsWorking(false);
		System.out.println("Staff removed.");
		staffMember.print();
	}
	/**
	 * Gets Staff object
	 * @param staffID of Staff object to get
	 * @return Staff Object with corresponding staffID or return null if staff Object with staffID does not exist
	 */
	public Staff getStaff(int staffID) {
		for (Staff o:listOfStaffMembers) {
			if (o.getStaffID()==staffID) {
				return o;
			}
		}
		return null;
	}
	/**
	 * Return list Of Staff Members
	 * @return list Of Staff Members
	 */
	public ArrayList<Staff> getListOfStaffMembers(){
		return this.listOfStaffMembers;
	}
	/**
	 * Update staff member with corresponding staffID
	 * @param updatedStaffName new name or old name
	 * @param updatedStaffTitle new title or old title
	 * @param staffID staffID to reference
	 */
	public void updateStaffMember(String updatedStaffName, String updatedStaffTitle, int staffID) {
		Staff staffMember=getStaff(staffID);
		staffMember.setStaffName(updatedStaffName);
		staffMember.setStaffTitle(updatedStaffTitle);
		System.out.println("Staff details updated.");
		staffMember.print();
	}
	/**
	 * prints list of Staff member names, IDs and job title for operational staff
	 */
	public void displayStaffList() {
 		System.out.println("List of Staffs (by staffID):");
 		for(int i=0; i<listOfStaffMembers.size(); i++) {
 			Staff staffMember=listOfStaffMembers.get(i);
 			if (staffMember.getIsWorking()==true) {
 				System.out.format("staffID: %d    staff Name: %s    staff Job Title:%s 	Status: Working\n ", staffMember.getStaffID(), staffMember.getStaffName(), staffMember.getStaffTitle());
 			}
 		}
 	}
    /**
	 * Saves the instance's listOfStaffMembers as string in a text file.
	 * @param textFileName The name of the the text file.
	 */
	public void saveDB(String textFileName)  {
		try{
			StaffDatabase staffWrite=new StaffDatabase();
			staffWrite.fwrite(textFileName);
		}
		catch(IOException e)
		{
			System.out.println("Failed to save to "+textFileName);
			return;
		}
	}
    /**
	 * Loads to instance's listOfStaffMembers from a text file
	 * @param textFileName The name of the text file.
	 */
	public void loadDB(String textFileName) {
		try {
			StaffDatabase staffLoad=new StaffDatabase();
			this.listOfStaffMembers = staffLoad.fread(textFileName);
		}
		catch(IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}
		System.out.println("Loaded successfully from "+textFileName);
	}
}
