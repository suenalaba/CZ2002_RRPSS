package restaurant_application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import restaurant_manager.StaffManager;
import restaurant_entity.Staff.Gender;

public class StaffApp {
	//create a staff member and add to system
		public static void createStaffMemberQuery() {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter staff ID: ");
			
			//check if staffId already exists as staff Id is unique. 
			 ArrayList <Integer> existingStaffId = new ArrayList<Integer>();
			 
			 //pull out all existing staff Id from listofstaffmembers and add to existingstaffId
			 for(int i=0; i<StaffManager.getListOfStaffMembers().size();i++)
			 {
				 existingStaffId.add(StaffManager.getListOfStaffMembers().get(i).getStaffID());
			 }
			 
			int staffId = -1;
					
					while (staffId==-1)
					{
						try {
							staffId = sc.nextInt();
							sc.nextLine();
							
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
					
					if(genderChoice!=1 && genderChoice!=2)
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
			
			
//			listOfStaffMembers.set(genderChoice, null)
			
			StaffManager.createStaffMember(staffId, staffName, staffTitle, staffGender);
		}
		
		
		

		//remove staff
		public static void removeStaffMemberQuery() {
			Scanner sc = new Scanner(System.in);
			
			
			//find staffID inside array list
			System.out.println("Enter staff ID of staff you want to remove: ");
			StaffManager.displayStaffList();
			
			//check if staffId entered exists in stafflist. else ask user enter again. 
			 ArrayList <Integer> existingStaffId = new ArrayList<Integer>();
			 
			 //pull out all existing staff Id from listofstaffmemebers and add to existingstaffId
			 for(int i=0; i<StaffManager.getListOfStaffMembers().size();i++)
			 {
				 existingStaffId.add(StaffManager.getListOfStaffMembers().get(i).getStaffID());
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
			for(int i=0;i<StaffManager.getListOfStaffMembers().size(); i++)
			{
				if(StaffManager.getListOfStaffMembers().get(i).getStaffID() == staffId)
				{
					int removalIndex = i;
					StaffManager.removeStaffMember(removalIndex);
					return;
				}
			}
			
			
		}
		
		
		
		//update staff details (staffName and staffTitle)
		public static void updateStaffMemberQuery() {
			
			
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter the staff ID of the staff that you want to update details to:");
			StaffManager.displayStaffList();
			
			//check if staffId entered exists in stafflist. else ask user enter again. 
			 ArrayList <Integer> existingStaffId = new ArrayList<Integer>();
			 
			 //pull out all existing staff Id from listofstaffmemebers and add to existingstaffId
			 for(int i=0; i<StaffManager.getListOfStaffMembers().size();i++)
			 {
				 existingStaffId.add(StaffManager.getListOfStaffMembers().get(i).getStaffID());
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
			for(int i=0; i<StaffManager.getListOfStaffMembers().size(); i++) {
				 if(StaffManager.getListOfStaffMembers().get(i).getStaffID() == staffIdUpdate)
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
			
			
			
			StaffManager.updateStaffMember(updatedStaffName, updatedStaffTitle, staffIdUpdate);
		}
}
