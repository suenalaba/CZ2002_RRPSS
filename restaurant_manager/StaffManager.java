package restaurant_manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_database.StaffDatabase;
import restaurant_entity.Staff.Gender;
import restaurant_entity.MenuItem;
import restaurant_entity.Staff;



public class StaffManager {
	
	//public static final String delimiter = ";";

	
	private static ArrayList<Staff> listOfStaffMembers = new ArrayList<Staff>();
	
	public void setListOfStaffMembers(ArrayList<Staff> listOfStaffMembers) {
		this.listOfStaffMembers = listOfStaffMembers;
	}
	
	public static ArrayList<Staff> getListOfStaffMembers(){
		return listOfStaffMembers;
	}
	
	
	//create a staff member and add to system
	public static void createStaffMemberQuery() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter staff ID: ");
		
		//check if staffId already exists as staff Id is unique. 
		 ArrayList <Integer> existingStaffId = new ArrayList<Integer>();
		 
		 //pull out all existing staff Id from listofstaffmembers and add to existingstaffId
		 for(int i=0; i<listOfStaffMembers.size();i++)
		 {
			 existingStaffId.add(listOfStaffMembers.get(i).getStaffID());
		 }
		 
		int staffId = -1;
				
				while (staffId==-1)
				{
					try {
						staffId = sc.nextInt();
						//sc.nextLine();
						
						//check if existing list of staffId contains the staffId entered. 
						if(existingStaffId.contains(staffId))
						{
							staffId=-1;
							System.out.println("Staff Id entered already exists. Please enter another one.");
						}
						
						else
						{
							break;
						}
						
					}
					
					catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Not an Integer. Try Again:");
					}
				}
				
		
		
		System.out.println("Enter staff Name: ");
		String staffName = sc.nextLine();
		
		System.out.println("Enter staff title: ");
		String staffTitle = sc.nextLine();
		
		System.out.println("Enter staff gender: Enter 1 for MALE, 2 for FEMALE: ");
		int genderChoice = -1;
		
		while (genderChoice==-1)
		{
			try {
				genderChoice = sc.nextInt();
				sc.nextLine();
				
				if(genderChoice!=1 || genderChoice!=2)
				{
					genderChoice=-1;
					System.out.println("Gender choice invalid. Choose either 1 for MALE or 2 for FEMALE: ");
				}
				
				else
				{
					break;
				}
				
			}
			
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not an Integer. Try Again:");
			}
		}
		
		Gender staffGender;
		switch(genderChoice) {
		
		case 1: staffGender = Gender.MALE;
		break;
		
		case 2: staffGender = Gender.FEMALE;
		break;
		
		default: 
			staffGender = Gender.MALE;
			break;
		}
		
		
//		listOfStaffMembers.set(genderChoice, null)
		createStaffMember(staffId, staffName,staffTitle,staffGender);
	}
	
	private static void createStaffMember(int staffId, String staffName, String staffTitle, Gender staffGender) {
		listOfStaffMembers.add(new Staff(staffId,staffName, staffTitle,staffGender));
		
		
	}
	
	//display staffs in the system
	public static void displayStaffList() {
 		System.out.println("List of Staffs (by staffID):");
 		
 		for(int i=0; i<listOfStaffMembers.size(); i++) {
 			System.out.format("staffID: %d    staff Name: %s    staff Job Title:%s 	\n", listOfStaffMembers.get(i).getStaffID(), listOfStaffMembers.get(i).getStaffName(), listOfStaffMembers.get(i).getStaffTitle());
 		}
 		
 		
 	}
	//remove staff
	public static void removeStaffMemberQuery() {
		Scanner sc = new Scanner(System.in);
		
		
		//find staffID inside array list
		System.out.println("Enter staff ID of staff you want to remove: ");
		displayStaffList();
		
		//check if staffId entered exists in stafflist. else ask user enter again. 
		 ArrayList <Integer> existingStaffId = new ArrayList<Integer>();
		 
		 //pull out all existing staff Id from listofstaffmemebers and add to existingstaffId
		 for(int i=0; i<listOfStaffMembers.size();i++)
		 {
			 existingStaffId.add(listOfStaffMembers.get(i).getStaffID());
		 }
		
		//error handling if input for staffId not an integer
		int staffId = -1;
		
		while (staffId==-1)
		{
			try {
				staffId = sc.nextInt();
				sc.nextLine();
				
				if(existingStaffId.contains(staffId))
				{
					break;
				}
				
				else
				{
					
					staffId=-1;
					System.out.println("Staff Id entered does not exists in system. Please enter another one.");
				}
				
			}
			
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not an Integer. Try Again:");
			}
		}
		
		
		//check if staffID exists in listOfStaffMembers
		for(int i=0;i<listOfStaffMembers.size(); i++)
		{
			if(listOfStaffMembers.get(i).getStaffID() == staffId)
			{
				int removalIndex = i;
				removeStaffMember(removalIndex);
				return;
			}
		}
		
		
	}
	private static void removeStaffMember(int removalStaffId) {
		//list ofstaffmembers.remove(staffID) 
		
		listOfStaffMembers.remove(removalStaffId);
		System.out.println("Staff removed. ");
	}
	
	
	
	//update staff details (staffName and staffTitle)
	public static void updateStaffMemberQuery() {
		
		
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the staff ID of the staff that you want to update details to:");
		displayStaffList();
		
		//check if staffId entered exists in stafflist. else ask user enter again. 
		 ArrayList <Integer> existingStaffId = new ArrayList<Integer>();
		 
		 //pull out all existing staff Id from listofstaffmemebers and add to existingstaffId
		 for(int i=0; i<listOfStaffMembers.size();i++)
		 {
			 existingStaffId.add(listOfStaffMembers.get(i).getStaffID());
		 }
		
		 
		int staffIdUpdate = -1;
		
		while (staffIdUpdate==-1)
		{
			try {
				staffIdUpdate = sc.nextInt();
				sc.nextLine();
				
				if(existingStaffId.contains(staffIdUpdate))
				{
					break;
				}
				
				else
				{
					
					staffIdUpdate=-1;
					System.out.println("Staff Id entered does not exists in system. Please enter another one.");
				}
				
				
			}
			
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not an Integer. Try Again:");
			}
		}
		
		
		//check if staff Id entered exists in the list of staff members
		for(int i=0; i<listOfStaffMembers.size(); i++) {
			 if(listOfStaffMembers.get(i).getStaffID() == staffIdUpdate)
			 {
				 staffIdUpdate= i;
				 break;
			 }
			 
		 }
		
		//ArrayList<Staff> newListOfStaffMembers = listOfStaffMembers.get(staffIdUpdate).
		
		//change staffName, title
		
		
		System.out.println("Enter updated staff name:");
		String updatedStaffName = sc.nextLine();
		
		System.out.println("Enter updated staff title:");
		String updatedStaffTitle = sc.nextLine();
		
		
		
		updateStaffMember(updatedStaffName, updatedStaffTitle, staffIdUpdate);
	}
	
	private static void updateStaffMember(String updatedStaffName, String updatedStaffTitle, int staffIdUpdate) {
		
		//listOfStaffMembers.set(staffIdUpdate, updatedStaffName);
		
		listOfStaffMembers.get(staffIdUpdate).setStaffName(updatedStaffName);
		listOfStaffMembers.get(staffIdUpdate).setStaffTitle(updatedStaffTitle);
		System.out.println("Staff details updated");
	}
	
	public static void saveStaffDB(String saveFileName)  {
		
		try{
			StaffDatabase.fwrite(saveFileName,listOfStaffMembers);
		}
		catch(IOException e)
		{
			System.out.format("File %s write failed", saveFileName);
			return;
		}
		
		
	}
	
	public static void loadStaffDB(String loadFileName) {
		try {
			listOfStaffMembers = StaffDatabase.fread(loadFileName);
		}
		
		catch(IOException e) {
			System.out.format("File %s read failed", loadFileName);
			return;
		}
	}
	

	
	
}
