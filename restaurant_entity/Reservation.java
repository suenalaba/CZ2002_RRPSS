package RRPSS;

import java.util.Date;

public class Reservation {
	
	private int reservationID;
	private int tableID;
	private int pax;
	
	private String customerName;
	private String customerContactNum;
	
	private Date reservationDate;
	private Date reservationStartTime;
	private Date reservationEndTime;
	
	
	public Reservation(int reservationID) {
		/*
		this.reservationID = reservationID;
		this.customerName = name;
		this.customerContactNum = contact;
		this.pax = pax;
		this.reservationDate = date;
		this.reservationStartTime = startTime;
		/* add one hour to start time to signify end time: 
		this.reservationEndTime = startTime.plusHours(1);
		*/
	}
	
	
	public int getReservationID(){
		return reservationID;
	}
	
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	
	public String getCustomerName(){
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerContactNum(){
		return customerContactNum;
	}
	
	public void setCustomerContactNum(String customerContactNum) {
		this.customerContactNum = customerContactNum;
	}
	
	public Date getReservationDate(){
		return reservationDate;
	}
	
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	
	public Date getReservationStartTime(){
		return reservationStartTime;
	}
	
	public void setReservationStartTime(Date reservationStartTime) {
		this.reservationStartTime = reservationStartTime;
	}
	
	public Date getReservationEndTime(){
		return reservationEndTime;
	}
	
	public void setReservationEndTime(Date reservationEndTime) {
		this.reservationEndTime = reservationEndTime;
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
	
}
