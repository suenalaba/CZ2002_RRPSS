package restaurant_manager;

import java.util.ArrayList;
import java.util.Scanner;
import restaurant_entity.Staff.Gender;
import restaurant_entity.Staff;


public class StaffManager {

	private static ArrayList<Staff> listOfStaffMembers = new ArrayList<Staff>();
	
	public static ArrayList<Staff> getListOfStaffMembers(){
		return listOfStaffMembers;
	}
	
	
	public static void createStaffMemberQuery() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter staff ID: ");
		int staffId = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Enter staff Name: ");
		String staffName = sc.nextLine();
		
		
		System.out.println("Enter staff title: ");
		String staffTitle = sc.nextLine();
		
		System.out.println("Enter staff gender: (1) MALE, (2) FEMALE: ");
		
		int genderChoice = sc.nextInt();
		sc.nextLine();
		
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
		
		
		
		createStaffMember(staffId, staffName,staffTitle,staffGender);
	}
	
	private static void createStaffMember(int staffId, String staffName, String staffTitle, Gender staffGender) {
		listOfStaffMembers.add(new Staff(staffId,staffName, staffTitle,staffGender));
	}
	
	
	public static void removeStaffMemberQuery() {
		
		//find staffID inside array list
		
		
	}
	private static void removeStaffMember(int removalStaffId) {
		//list ofstaffmembers.remove(staffID) 
	}
	
	
	public static void updateStaffMemberQuery() {
		
		//change staffName, title
		
	}
	
	private static void updateStaffMember(String staffName, String staffTitle) {
		//use set methods of the staff class. 
	}
	
	public static void saveStaffDB() {
		
	}
	
	public static void loadStaffDB() {
		
	}
	
	
	
}
