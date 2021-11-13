package restaurant_entity;

import java.time.LocalDateTime;
/**
 * A class defining a reservation object.
 * @author Xin Pei
 * @version 4.5
 * @since 13-11-2021
 */
public class Reservation {
	
	//Attributes
	private static int counter = 0; //keep tracks of the amount of reservations.
	private int reservationID;
	private int tableID; //track which table the reservation has been made for.
	private int pax;
	private String customerID;
	private LocalDateTime reservationStartTime;
	private LocalDateTime reservationEndTime;
	private boolean isFinished;
	private boolean isAppeared; //whether the person who made the reservation has arrived.
	
	
	/**
	 * Constructor. Used for reservation Manager. 
	 * Increases counter when called.
	 */
	public Reservation() {
		this.reservationID = counter;
		counter++;
		this.isFinished=false;
	}
	/*
	 * Constructor. Used for reading database and walk-ins.
	 * Increases counter when called.
	 */
	public Reservation(int tableID,int pax,String customerID, LocalDateTime startTime,LocalDateTime endTime,Boolean isFinished,Boolean isAppeared) {
		this.reservationID = counter;
		counter++;
		this.tableID=tableID;
		this.pax=pax;
		this.customerID=customerID;
		this.reservationStartTime=startTime;
		this.reservationEndTime=endTime;
		this.isFinished=isFinished;
		this.isAppeared=isAppeared;
	}
	/**
	 * Get reservationID of the reservation object. Public method.
	 * @return reservationID integer
	 */
	public int getReservationID(){
		return reservationID;
	}
	/**
	 * Set reservationID of the reservation object. Public method.
	 * @param reservationID
	 */
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	/**
	 * Get tableID of the reservation object. Public method.
	 * @return tableID integer
	 */
	public int getTableID(){
		return tableID;
	}
	/**
	 * Set tableID of the reservation object. Public method.
	 * @param tableID integer
	 */
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	/**
	 * Get pax of the reservation object. Public method.
	 * @return pax integer
	 */
	public int getPax(){
		return pax;
	}
	/**
	 * Set pax of the reservation object. Public method.
	 * @param pax integer
	 */
	public void setPax(int pax) {
		this.pax = pax;
	}
	/**
	 * Get customerID of the reservation object. Public method.
	 * @return customerID String
	 */
	public String getCustomerID() {
		return customerID;
	}
	/**
	 * Set customerID of the reservation object. Public method.
	 * @param customerID String
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	/**
	 * Get reservationStartTime of the reservation object. Public method.
	 * @return reservationStartTime LocalDateTime
	 */
	public LocalDateTime getReservationStartTime(){
		return reservationStartTime;
	}
	/**
	 * Set startDateTime of the reservation object. Public method.
	 * @param startDateTime LocalDateTime
	 */
	public void setReservationStartTime(LocalDateTime startDateTime) {
		this.reservationStartTime = startDateTime;
	}
	/**
	 * Get reservationEndTime of the reservation object. Public method.
	 * @return reservationEndTime LocalDateTime
	 */
	public LocalDateTime getReservationEndTime(){
		return reservationEndTime;
	}
	/**
	 * Set reservationEndTime of the reservation object. Public method.
	 * @param reservationEndTime LocalDateTime
	 */
	public void setReservationEndTime(LocalDateTime reservationEndTime) {
		this.reservationEndTime = reservationEndTime;
	}
	/**
	 * Get isFinished (boolean) of the reservation object. Public method.
	 * @return isFinished boolean
	 */
	public boolean getIsFinished() {
		return isFinished;
	}
	/**
	 * Set isFinished (boolean) of the reservation object. Public method.
	 * @param status boolean
	 */
	public void setIsFinished(boolean status) {
		this.isFinished = status;
	}
	/**
	 * Get isAppeared (boolean) of the reservation object. Public method.
	 * @return isAppeared boolean
	 */
	public boolean getIsAppeared() {
		return isAppeared;
	}
	/**
	 * Set isAppeared (boolean) of the reservation object. Public method.
	 * @param status boolean
	 */
	public void setIsAppeared(boolean status) {
		this.isAppeared = status;
	}
	/**
	 * Get reservation counter. Public method.
	 * @return counter integer
	 */
	public static int getCounter() {
		return counter;
	}
	/**
	 * Set reservation counter. Public method.
	 * @param newCounter integer
	 */
	public static void setCounter(int newCounter) {
		counter = newCounter;
	}
	
}
// public class Reservation {
	
// 	private static int counter = 0;
	
// 	private int reservationID; //auto generated in system.
// 	private int tableID; 
// 	private int pax;
	
// 	private String customerID;
// 	private String customerName;
// 	private String customerContactNum;
	
// 	//private LocalDateTime reservationDate;
// 	private LocalDateTime reservationStartTime;
// 	private LocalDateTime reservationEndTime;
	
// 	private boolean isFinished; //status finished 
	
	
// 	public Reservation() {
// 		this.reservationID = counter;
// 		counter++;
// 		/*
// 		this.reservationID = reservationID;
// 		this.customerName = name;
// 		this.customerContactNum = contact;
// 		this.pax = pax;
// 		this.reservationDate = date;
// 		this.reservationStartTime = startTime;
// 		/* add one hour to start time to signify end time: 
// 		this.reservationEndTime = startTime.plusHours(1);
// 		*/
// 	}
	
	
// 	public int getReservationID(){
// 		return reservationID;
// 	}
	
// 	public void setReservationID(int reservationID) {
// 		this.reservationID = reservationID;
// 	}
	
// 	public String getCustomerName(){
// 		return customerName;
// 	}
	
// 	public void setCustomerName(String customerName) {
// 		this.customerName = customerName;
// 	}
	
// 	public String getCustomerContactNum(){
// 		return customerContactNum;
// 	}
	
// 	public void setCustomerContactNum(String customerContactNum) {
// 		this.customerContactNum = customerContactNum;
// 	}
	
	
// 	public LocalDateTime getReservationStartTime(){
// 		return reservationStartTime;
// 	}
	
// 	public void setReservationStartTime(LocalDateTime startDateTime) {
// 		this.reservationStartTime = startDateTime;
// 	}
	
// 	public LocalDateTime getReservationEndTime(){
// 		return reservationEndTime;
// 	}
	
// 	public void setReservationEndTime(LocalDateTime reservationEndTime) {
// 		this.reservationEndTime = reservationEndTime;
// 	}
	
// 	public int getTableID(){
// 		return tableID;
// 	}
	
// 	public void setTableID(int tableID) {
// 		this.tableID = tableID;
// 	}
	
// 	public int getPax(){
// 		return pax;
// 	}
	
// 	public void setPax(int pax) {
// 		this.pax = pax;
// 	}


// 	public boolean getIsFinished() {
// 		return isFinished = false;
// 	}


// 	public void setIsFinished(boolean status) {
// 		this.isFinished = status;
// 	}
	
// 	public int getCounter() {
// 		return counter;
// 	}
	
// 	public static void setCounter(int newCounter) {
// 		counter = newCounter;
// 	}


// 	public String getCustomerID() {
// 		return customerID;
// 	}


// 	public void setCustomerID(String customerID) {
// 		this.customerID = customerID;
// 	}
	
	
	
// }
