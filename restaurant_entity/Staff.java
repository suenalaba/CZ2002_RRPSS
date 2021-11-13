package restaurant_entity;

/**
 * A class defining a staff object.
 * @author JieKai
 * @version 4.5
 * @since 13-11-2021
 */
public class Staff {

	private static int runningCount=1; //used to track number of Staff objects.
	private int staffID;
	private String staffName;
	private String staffTitle;
	private Gender staffGender;
	private boolean isWorking; //prevents data loss when printing orders
	
	/**
	 * Declare constants for enum Gender. MALE or FEMALE.
	 */
	public enum Gender{
		MALE,
		FEMALE
	}
	/**
	 * Constructor used by restaurant manager to create new staff.
	 * @param staffName
	 * @param staffTitle
	 * @param staffGender
	 */
	public Staff(String staffName, String staffTitle, Gender staffGender) {
		this.staffID = runningCount;
		runningCount++;
		this.staffName = staffName;
		this.staffTitle = staffTitle;
		this.staffGender = staffGender;
		this.isWorking=true;
	}
	/**
	 * Constructor to create new staff. (Used when reading files)
	 * @param staffName
	 * @param staffTitle
	 * @param staffGender
	 * @param isWorking
	 */
	public Staff(String staffName, String staffTitle, Gender staffGender,Boolean isWorking) {
		this.staffID = runningCount;
		runningCount++;
		this.staffName = staffName;
		this.staffTitle = staffTitle;
		this.staffGender = staffGender;
		this.isWorking=isWorking;
	}
	/**
	 * Get existing runningCount(Integer). Public method.
	 * @return integer running count.
	 */
	public static int getRunningCount() {
		return runningCount;
	}
	/**
	 * Update existing variable runningCount to newRunningCount. Public method.
	 * @param newRunningCount
	 */
	public static void setRunningCount(int newRunningCount) {
		runningCount = newRunningCount;
	}
	/**
	 *  Public method.
	 * @return integer StaffID
	 */
	public int getStaffID() {
		return staffID;
	}
	/**
	 * Set staffID of staff object. Public method.
	 * @param integer staffID
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	/**
	 *  Public method.
	 * @return String staffName
	 */
	public String getStaffName() {
		return staffName;
	}
	/**
	 * Set staffName of staff object. Public method.
	 * @param String staffName
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	/**
	 * Get staffTitle. Public method.
	 * @return String staffTitle
	 */
	public String getStaffTitle() {
		return staffTitle;
	}
	/**
	 * Set staffTitle of staff object. Public method.
	 * @param String staffTitle
	 */
	public void setStaffTitle(String staffTitle) {
		this.staffTitle = staffTitle;
	}
	/**
	 * Get gender of Staff. Public method.
	 * @return enum Gender
	 */
	public Gender getStaffGender() {
		return staffGender;
	}
	/**
	 * Set staff's gender. Public method.
	 * @param enum Gender staffGender
	 */
	public void setStaffGender(Gender staffGender) {
		this.staffGender = staffGender;
	}
	/**
	 * Check if staff is working. Public method.
	 * @return boolean isWorking
	 */
	public boolean getIsWorking() {
		return this.isWorking;
	}
	/**
	 * Set if staff is working. Public method.
	 * @param boolean isWorking
	 */
	public void setIsWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	/**
	 * Call this method to print staff details. Public method.
	 */
	public void print() {
		System.out.println("Staff ID: "+this.staffID);
		System.out.println("Name: "+this.staffName);
		System.out.println("Title: "+this.staffTitle);
		System.out.println("Gender: "+this.staffGender);
		if(this.isWorking==true) {
			System.out.println("Current Status: Working");
		}
		else {
			System.out.println("Current Status: Removed");
		}
	}

}
