package restaurant_entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {
	
	//Attributes
	private static int counter = 0;
	private int reservationID;
	private int tableID; 
	private int pax;
	private String customerID;
	private LocalDateTime reservationStartTime;
	private LocalDateTime reservationEndTime;
	private boolean isFinished;
	
	
	//Constructors
	public Reservation() {
		this.reservationID = counter;
		counter++;
		this.isFinished=false;
	}
	
	public Reservation(String customerID,int pax, LocalDateTime startTime,int tableID) {
		this.reservationID = counter;
		counter++;
		this.customerID=customerID;
		this.pax=pax;
		this.reservationStartTime=startTime;
		this.reservationEndTime=startTime.plusHours(1);
		this.tableID=tableID;
		this.isFinished=false;
	}
	
	//methods
	public int getReservationID(){
		return reservationID;
	}
	
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	
	public int getTableID(){
		return tableID;
	}
	
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	
	public int getPax(){
		return pax;
	}
	
	public void setPax(int pax) {
		this.pax = pax;
	}
	
	public String getCustomerID() {
		return customerID;
	}


	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	public LocalDateTime getReservationStartTime(){
		return reservationStartTime;
	}
	
	public void setReservationStartTime(LocalDateTime startDateTime) {
		this.reservationStartTime = startDateTime;
	}
	
	public LocalDateTime getReservationEndTime(){
		return reservationEndTime;
	}
	
	public void setReservationEndTime(LocalDateTime reservationEndTime) {
		this.reservationEndTime = reservationEndTime;
	}

	public boolean getIsFinished() {
		return isFinished;
	}


	public void setIsFinished(boolean status) {
		this.isFinished = status;
	}
	
	public static int getCounter() {
		return counter;
	}
	
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
