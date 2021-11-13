package restaurant_application;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//import restaurant_entity.Customer;
//import restaurant_entity.Order;
import restaurant_entity.Payment;
import restaurant_entity.Order;
import restaurant_entity.MenuItem;
import restaurant_database.PaymentDatabase;
import restaurant_entity.Customer;
import restaurant_manager.CustomerManager;
import restaurant_manager.MenuManager;
import restaurant_manager.OrderManager;
import restaurant_manager.PaymentManager;
import restaurant_entity.Table;
import restaurant_entity.Table.status;
import restaurant_manager.ReservationManager;
import restaurant_manager.StaffManager;
import restaurant_entity.Reservation;
//import restaurant_entity.Reservation;
//import restaurant_database.PaymentDatabase;

//import other restaurant classes...
import restaurant_manager.TableLayoutManager;
/**
 * PaymentApp used to make payment and to generate sales report
 * @author Joshua Khoo 
 * @version 4.5
 * @since 2021-11-13
 */
public class PaymentApp {
    final static double GST = 0.07;
    final static double SERVICE_CHARGE = 0.10; 
	
	/**
	 * Retrieves unpaid orders before matching tableID to unpaid orders
	 * Calculates gst, subtotal, memberdiscount, service charge before creating a new payment 
	 * Prints out receipt
	 * Frees table after payment
	 * Sets reservation to finish
	 * Update order paid status
	 * 
	 */
	public void makePayment() {
		TableLayoutManager tableLayoutM=TableLayoutManager.getInstance();
		 OrderManager orderM=OrderManager.getInstance();
		 StaffManager staffM=StaffManager.getInstance();
		 MenuManager menuM=MenuManager.getInstance();
		 ReservationManager reservationM=ReservationManager.getInstance();
		 CustomerManager customerM=CustomerManager.getInstance();
		 PaymentManager paymentM=PaymentManager.getInstance();
    	Scanner sc = new Scanner(System.in); 
    	ArrayList<Order> unpaidOrders = new ArrayList<>();
    	unpaidOrders = orderM.getUnpaidOrders(); // need to print unpaid orders
    	if (unpaidOrders.size()==0) {
    		System.out.println("No unpaid orders at the moment.");
    		return;
    	}
    	for(int i= 0; i<unpaidOrders.size(); i++) {
    		System.out.format("Table ID:  %d		Paid Status: %b", unpaidOrders.get(i).getTableID() , unpaidOrders.get(i).getPaidStatus());
    		System.out.println();
    	}
    	System.out.println("\nEnter tableID to make payment"); 	
    	int tableId = 0;
		while (tableId==0)
		{
			try {
				tableId = sc.nextInt();
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not an Integer. Try Again:");
			}catch(IndexOutOfBoundsException c) {
				System.out.println("Table not found.");
				return;
			}
		}
		Order order = orderM.getOrderByTableId(tableId);
		if (order==null) {
			System.out.println("Table has no order attached to it. Returning to main menu.");
			return;
		}
		if (tableLayoutM.getTable(tableId)==null) {
			System.out.println("Table has no order attached to it. Returning to main Menu");
			return;
		}
		if (tableLayoutM.getTable(tableId).getHourBlock()[LocalDateTime.now().getHour()]!=status.OCCUPIED) {
			System.out.println("Table has no order attached to it. Returning to main menu.");
			return;
		}
		String customerID = reservationM.getUnfinishedReservationByTableIDNow(tableId).getCustomerID();
		Customer customer = customerM.getCustomer(customerID);
		double subtotal = paymentM.calculateSubtotal(order.getOrderItems());
		boolean membershipApplied = false; 
		if(customer.getpartnerMembership() || customer.getrestaurantMembership()) {
			membershipApplied = true; 
		}
		String paymentDate = paymentM.getPaymentDateTime(); 
		double memberDiscount = paymentM.calculateMemberDiscount(membershipApplied, subtotal);
		double gst = subtotal * GST; 
		double serviceCharge = subtotal * SERVICE_CHARGE; 
		double grandTotal = subtotal + gst + serviceCharge - memberDiscount; 
		int reservationNumber = reservationM.getUnfinishedReservationByTableIDNow(tableId).getReservationID();

		Payment payment = new Payment(paymentDate, subtotal, gst, serviceCharge,
						memberDiscount, grandTotal, order, reservationNumber, tableId, 
						membershipApplied );
		paymentM.printReceipt(payment);
		tableLayoutM.freeTableStatus(tableId);
		reservationM.setIsFinishedByTableID(tableId);
		orderM.updatePaidStatus(payment.getOrder().getOrderID()); 
		paymentM.addPayment(payment);
	}
	
	/**
	 * Prints sales report by day or by month 
	 * 
	 */
    public void printSaleReport() {
    	PaymentManager paymentM=PaymentManager.getInstance();
    	ArrayList<Payment> paymentinvoices = paymentM.getPaymentInvoices();
    	System.out.println("(1) Print sale revenue report by day\n(2) Print sale revenue report by month");
    	Scanner sc = new Scanner(System.in); 
    	int choice, i;
    	String period; 
		double totalRevenueBeforeTax = 0;
		double totalRevenueAfterTax = 0;
    	try {
    		choice = sc.nextInt();
    		sc.nextLine();
    		if(choice != 1 && choice != 2) {
    			System.out.println("Invalid input");
    			return; 
    		}
    	}
		catch(InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
    	switch(choice) {
    	case 1:
    		System.out.println("Enter day in following format dd-MM-yyyy"); 
    		period = sc.nextLine();
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    		try {
    			LocalDate date = LocalDate.parse(period, formatter);
    			if(LocalDate.now().isBefore(date)) {
    				System.out.println("Future date!");
    				break; 
    			}
    			
    		}
    		catch(DateTimeParseException exe) {
    			System.out.println("Invalid date"); 
    			break; 
    		} 
    		for (Payment payment : paymentinvoices) {
    			if(payment.getpaymentDate().regionMatches(0, period, 0, 10)){
    				// prints out payment date and payment ID
    				System.out.println("Date: " + payment.getpaymentDate() + "       " + "Payment ID: " + payment.getpaymentID());
    				Order order = payment.getOrder(); 
    				ArrayList<MenuItem> orderItems = order.getOrderItems();
    				for(i =0; i<orderItems.size(); i++) { // prints out all orders, menu type and price without gst
    					String itemName=orderItems.get(i).getMenuItemName();
    					while(itemName.length()<=31) {
    						itemName+=" ";
    					}
    					System.out.print("Menu Item: " + itemName + "\t" + orderItems.get(i).getMenuItemType().name() +
    							"\t");
    					System.out.format("%.02f", orderItems.get(i).getMenuItemPrice());
    					System.out.println("");
    				}
    				totalRevenueBeforeTax += payment.getSubTotal();
    				totalRevenueAfterTax += payment.grandTotal(); 
    				System.out.println("=========================================================");
    			}
    		}
    		// if == 0, no payment found
    		if(totalRevenueBeforeTax == 0) {
    			System.out.println("No payment records found");
    		}
    		else {
    			System.out.print("Total revenue before tax and discounts: $");
    			System.out.format("%.02f\n", totalRevenueBeforeTax);
    			System.out.print("Total revenue after tax and discounts: $");
    			System.out.format("%.02f\n", totalRevenueAfterTax);
    		}
    		break; 
    		
    	case 2: 
    		System.out.println("Enter the month and year in the following format MM-yyyy");
    		period = sc.nextLine();
    		DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM-yyyy");
    		try {
    			YearMonth date = YearMonth.parse(period, formatterMonth);
    			if(YearMonth.now().isBefore(date)) {
    				System.out.println("Future date!");
    				break; 
    			}
    			
    		}
    		catch(DateTimeParseException exe) {
    			System.out.println("Invalid date"); 
    			break; 
    		}
    		for (Payment payment : paymentinvoices) {
    			if(payment.getpaymentDate().startsWith(period, 3)){
    				// prints out payment date and payment ID
    				System.out.println("Date: " + payment.getpaymentDate() + "       " + "Payment ID: " + payment.getpaymentID());
    				Order order = payment.getOrder(); 
    				ArrayList<MenuItem> orderItems = order.getOrderItems();
    				for(i =0; i<orderItems.size(); i++) { // prints out all orders, menu type and price without gst
    					String itemName=orderItems.get(i).getMenuItemName();
    					while(itemName.length()<=31) {
    						itemName+=" ";
    					}
    					System.out.print("Menu Item: " + itemName + "\t" + orderItems.get(i).getMenuItemType().name() +
    							"\t");
    					System.out.format("%.02f", orderItems.get(i).getMenuItemPrice());
    					System.out.println("");
    				}
    				totalRevenueBeforeTax += payment.getSubTotal();
    				totalRevenueAfterTax += payment.grandTotal(); 
    				System.out.println("=========================================================");
    			}
    		}
    		// if == 0, no payment found
    		if(totalRevenueBeforeTax == 0) {
    			System.out.println("No payment records found");
    		}
    		else {
    			System.out.print("Total revenue before tax and discounts: $");
    			System.out.format("%.02f\n", totalRevenueBeforeTax);
    			System.out.print("Total revenue after tax and discounts: $");
    			System.out.format("%.02f\n", totalRevenueAfterTax);
    		}
    		break;   
    	}
    }

}
