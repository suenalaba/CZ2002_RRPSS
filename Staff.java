

public class Staff {

	private int staffID;
	private String staffName;
	private String staffTitle;
	
	//default gender
	private Gender staffGender = Gender.MALE;
	
	
	enum Gender{
		MALE,
		FEMALE
	}
	

	public Staff() {
		
	}
	
	public Staff(int staffID, String staffName, String staffTitle, Gender staffGender) {
		this.staffID = staffID;
		this.staffName = staffName;
		this.staffTitle = staffTitle;
		this.staffGender = staffGender;

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
	
	
	
}
