package restaurant_database;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import restaurant_entity.Staff;
import restaurant_manager.OrderManager;
import restaurant_manager.StaffManager;

import restaurant_entity.Staff.Gender;



//public class StaffDatabase implements DatabaseFunction{
//	public static final String DELIMITER = ";";
//	
//	
//
//		
//		@Override
//		public ArrayList fread(String textfilename) throws IOException {
//
//			ArrayList fileasstring = (ArrayList) FileRead.fread(textfilename);
//			
//			//array to store staff data
//			ArrayList staffList = new ArrayList();
//		
//			for (int i = 0; i < fileasstring.size(); i++) {
//				String data = (String) fileasstring.get(i);
//				// get individual 'fields' of the string separated by the delimiter ','
//				StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); // pass in the string to the string tokenizer
//				
//				int staffId = Integer.parseInt(str_tokenizer.nextToken().trim());															
//				String staffName = str_tokenizer.nextToken().trim();
//				String staffTitle = str_tokenizer.nextToken().trim();
//				
//				//enum Gender = str_tokenizer.nextToken().trim();????????????????????
//				Staff.Gender staffGender = Staff.Gender.valueOf(str_tokenizer.nextToken().trim());
//				
//				
//				
//				//??????????
//				
//				
//				// create staff object imported from database???????????????
//				Staff staff = new Staff(staffId, staffName,staffTitle,staffGender);
//				// add customer object to the list of customers
//				staffList.add(staff);
//				
//			}
//			return staffList;
//		
//	
//	}
//		
//		@Override
//		public void fwrite(String textfilename, List arraylist) throws IOException {
//
//			List staffList = new ArrayList();// array list to store staff data
//			//Staff.Gender staffGender;
//			for (int i = 0; i < arraylist.size(); i++) {
//				Staff staff = (Staff) arraylist.get(i);
//				StringBuilder staffString = new StringBuilder();
//				staffString.append(Integer.toString(staff.getStaffID()));
//				staffString.append(DELIMITER);
//				staffString.append(staff.getStaffName().trim());
//				staffString.append(DELIMITER);
//				staffString.append(staff.getStaffTitle().trim());
//				staffString.append(DELIMITER);
//
//				
//				//???????????????????????????????????????????????????????enum to string conversion doesnt work
//				staffString.append(staff.getStaffGender().name());
//				staffString.append(DELIMITER);
//				
//
//				staffList.add(staffString.toString());
//			}
//			FileRead.fwrite(staffList,textfilename);
//		
//		
//}
//	
//}	
//
//













public class StaffDatabase {
	public static final String DELIMITER = ",";
	
	public static ArrayList<Staff> fread(String textfilename) throws IOException {

		ArrayList fileasstring = (ArrayList) FileRead.fread(textfilename);
		
		//array to store table
		ArrayList<Staff> listOfStaffMembers = new ArrayList<>();
		for (int i = 0; i < fileasstring.size(); i++) {
			String data = (String) fileasstring.get(i);
			// get individual 'fields' of the string separated by the delimiter ','
			
			StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); // pass in the string to the string tokenizer
			int staffID = Integer.parseInt(str_tokenizer.nextToken().trim());
			String staffName = str_tokenizer.nextToken().trim();
			String staffTitle = str_tokenizer.nextToken().trim(); 
			Gender staffGender = Gender.valueOf(str_tokenizer.nextToken().trim().toUpperCase()); 
			Staff staff = new Staff(staffID, staffName, staffTitle, staffGender);
			listOfStaffMembers.add(staff); 
		}
		return listOfStaffMembers; 
	}
	
	public static void fwrite(String textfilename, ArrayList<Staff> listOfStaffMembers) throws IOException {

		List stafflist = new ArrayList();// array list to store staffdata
		for (int i = 0; i < listOfStaffMembers.size(); i++) {
			Staff staff = (Staff) listOfStaffMembers.get(i);
			StringBuilder staffstring = new StringBuilder();
			staffstring.append(Integer.toString(staff.getStaffID()));
			staffstring.append(DELIMITER);
			staffstring.append(staff.getStaffName());
			staffstring.append(DELIMITER);
			staffstring.append(staff.getStaffTitle());
			staffstring.append(DELIMITER); 
			staffstring.append(staff.getStaffGender().name());
			staffstring.append(DELIMITER); 
			stafflist.add(staffstring.toString());
		}
		FileRead.fwrite(stafflist,textfilename);
	}
}

