package restaurant_application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import restaurant_manager.StaffManager;
import restaurant_entity.Staff;
import restaurant_entity.Staff.Gender;

/**
 * define methods which interacts with the main RRPSS App and StaffManager
 to create a staff, remove a staff and update a staff details
 * @author jiekai
 * @version 4.5
 * @since 13-11-2021
 */

public class StaffApp {
		/**
		 * creates a staff member (requires, name, title and gender)
		 */
		public void createStaffMemberQuery() {
			String alpha = "[a-zA-Z.*\\s+.]+";
			Scanner sc = new Scanner(System.in);
			String staffName;
			String staffTitle;
			do {
				System.out.println("Enter staff Name: ");
				staffName = sc.nextLine();
				if(staffName.matches(alpha) && !staffName.equals("")) {
					break;
				}
				else {
					System.out.print("Please enter a valid name.\n");
				}
			} while (!staffName.matches(alpha) || staffName.equals(""));
			do {
				System.out.println("Enter staff title: ");
				staffTitle = sc.nextLine();
				if(staffTitle.matches(alpha) && !staffTitle.equals("")) {
					break;
				}
				else {
					System.out.print("Please enter a valid title.\n");
				}
			} while (!staffTitle.matches(alpha) || staffTitle.equals(""));
			
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
					System.out.println("Not an option. Try Again:");
				}
			}
			Gender staffGender;
			switch(genderChoice) {
			case 1: 
				staffGender = Gender.MALE;
				break;
			case 2: 
				staffGender = Gender.FEMALE;
				break;
			default: 
				staffGender = Gender.MALE;
				break;
			}
			StaffManager.getInstance().createStaffMember(staffName, staffTitle, staffGender);
		}
		
		/**
		 * removes a staff member
		 */
		public void removeStaffMemberQuery() {
			Scanner sc = new Scanner(System.in);
			StaffManager staffM=StaffManager.getInstance();
			System.out.println("Enter staff ID of staff you want to remove: ");
			staffM.displayStaffList();
			ArrayList <Integer> existingStaffId = new ArrayList<Integer>(); 
			for(int i=0; i<staffM.getListOfStaffMembers().size();i++)
			{
				existingStaffId.add(staffM.getListOfStaffMembers().get(i).getStaffID());
			}
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
			staffM.removeStaffMember(staffId);
		}
		
		/**
		 * updates a staff member's details (staff's name and job title)
		 */
		public void updateStaffMemberQuery() {
			String alpha = "[a-zA-Z.*\\s+.]+";
			Scanner sc = new Scanner(System.in);
			StaffManager staffM=StaffManager.getInstance();
			System.out.println("Enter the staff ID of the staff that you want to update details to:");
			staffM.displayStaffList();
			ArrayList <Integer> existingStaffId = new ArrayList<Integer>();
			for(int i=0; i<staffM.getListOfStaffMembers().size();i++)
			 {
				 existingStaffId.add(staffM.getListOfStaffMembers().get(i).getStaffID());
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
			Staff targetStaff=staffM.getStaff(staffIdUpdate);
			String staffName=targetStaff.getStaffName();
			String staffTitle=targetStaff.getStaffTitle();
			int selection=0;
			while (selection==0) {
				System.out.println("What would you like to update?\n1.Staff Name\n2.Staff Title");
				try {
					selection=sc.nextInt();
					sc.nextLine();
					if (selection!=1 && selection!=2) {
						selection=0;
						System.out.println("Not a valid option. Try again");
					}
					else {
						break;
					}
				}catch(InputMismatchException e){
					sc.nextLine();
					System.out.println("Please input either 1 or 2.");
				}
			}
			switch(selection) {
			case 1:
				do {
					System.out.println("Enter updated staff name:");
					staffName = sc.nextLine();
					if(staffName.matches(alpha) && !staffName.equals("")) {
						break;
					}
					else {
						System.out.print("Please enter a valid name.\n");
					}
				} while (!staffName.matches(alpha) || staffName.equals(""));
				break;
			case 2:
				do {
					System.out.println("Enter updated staff title:");
					staffTitle = sc.nextLine();
					if(staffTitle.matches(alpha) && !staffTitle.equals("")) {
						break;
					}
					else {
						System.out.print("Please enter a valid title.\n");
					}
				} while (!staffTitle.matches(alpha) || staffTitle.equals(""));
				break;
			}
			staffM.updateStaffMember(staffName, staffTitle, staffIdUpdate);
		}
}
