package restaurant_database;



import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import restaurant_entity.Staff;

import restaurant_entity.Staff.Gender;
import restaurant_manager.StaffManager;

/**
 * Subclass of DatabaseFunction
 * StaffDatabase reads and write to staffDB.txt
 * @author Lek Jie Kai
 * @version 4.5
 * @since 2021-11-13
 *
 */
public class StaffDatabase implements DatabaseFunction{
	/**
	 * DELIMITER to split tokens
	 */
	public static final String DELIMITER = ";";
	
	
	/**
	 * Reads data from staffDB.txt into ArrayList<Staff>
	 * @param textfilename staffDB.txt
	 * @return listOfStaffMembers Arraylist of Staff class
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	public ArrayList<Staff> fread(String textfilename) throws IOException {

		ArrayList<String> fileasstring = FileRead.fread(textfilename);
		
		//array to store table
		ArrayList<Staff> listOfStaffMembers = new ArrayList<>();
		int biggest=0;
		for (int i = 0; i < fileasstring.size(); i++) {
			String data = (String) fileasstring.get(i);
			// get individual 'fields' of the string separated by the delimiter ';'
			
			StringTokenizer str_tokenizer = new StringTokenizer(data, DELIMITER); // pass in the string to the string tokenizer
			int staffID = Integer.parseInt(str_tokenizer.nextToken().trim());
			String staffName = str_tokenizer.nextToken().trim();
			String staffTitle = str_tokenizer.nextToken().trim(); 
			Gender staffGender = Gender.valueOf(str_tokenizer.nextToken().trim().toUpperCase()); 
			Boolean isWorking = Boolean.valueOf(str_tokenizer.nextToken().trim());
			Staff staff = new Staff(staffName, staffTitle, staffGender,isWorking);
			if (staffID>biggest) {
				biggest=staffID;
			}
			Staff.setRunningCount(staffID);
			listOfStaffMembers.add(staff); 
		}
		Staff.setRunningCount(biggest+1);
		return listOfStaffMembers; 
	}
	
	/**
	 * Writes data from listOfStaffMembers to staffDB.txt
	 * @param textfilename
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	public void fwrite(String textfilename) throws IOException {

		ArrayList<String> stafflist = new ArrayList<String>();// array list to store staffdata
		ArrayList<Staff> listOfStaffMembers=StaffManager.getInstance().getListOfStaffMembers();
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
			staffstring.append(String.valueOf(staff.getIsWorking()));
			staffstring.append(DELIMITER); 
			stafflist.add(staffstring.toString());
		}
		FileRead.fwrite(stafflist,textfilename);
	}
}





