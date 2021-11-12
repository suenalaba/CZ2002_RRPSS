package restaurant_entity;


public class Staff {

	private static int runningCount=1;
	private int staffID;
	private String staffName;
	private String staffTitle;
	private Gender staffGender;
	private boolean isWorking; //prevents data loss when printing orders
	
	
	public enum Gender{
		MALE,
		FEMALE
	}
	
	public Staff(String staffName, String staffTitle, Gender staffGender) {
		this.staffID = runningCount;
		runningCount++;
		this.staffName = staffName;
		this.staffTitle = staffTitle;
		this.staffGender = staffGender;
		this.isWorking=true;
	}
	
	public Staff(String staffName, String staffTitle, Gender staffGender,Boolean isWorking) {
		this.staffID = runningCount;
		runningCount++;
		this.staffName = staffName;
		this.staffTitle = staffTitle;
		this.staffGender = staffGender;
		this.isWorking=isWorking;
	}
	
	public static int getRunningCount() {
		return runningCount;
	}
	
	public static void setRunningCount(int newRunningCount) {
		runningCount = newRunningCount;
	}
	
	public int getStaffID() {
		return staffID;
	}
	
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	
	public String getStaffName() {
		return staffName;
	}
	
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	public String getStaffTitle() {
		return staffTitle;
	}
	
	public void setStaffTitle(String staffTitle) {
		this.staffTitle = staffTitle;
	}
	
	
	public Gender getStaffGender() {
		return staffGender;
	}
	
	public void setStaffGender(Gender staffGender) {
		this.staffGender = staffGender;
	}
	
	public boolean getIsWorking() {
		return this.isWorking;
	}
	
	public void setIsWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	
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
