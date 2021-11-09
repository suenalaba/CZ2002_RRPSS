package restaurant_application;

import java.io.*;
import java.time.LocalDate;
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
import restaurant_manager.OrderManager;
import restaurant_manager.PaymentManager;
import restaurant_entity.Table;
import restaurant_entity.Table.status;
import restaurant_manager.ReservationManager;
import restaurant_entity.Reservation;
//import restaurant_entity.Reservation;
//import restaurant_database.PaymentDatabase;

//import other restaurant classes...
import restaurant_manager.TableLayoutManager;

public class PaymentApp {
    final static double GST = 0.07;
    final static double SERVICE_CHARGE = 0.10; 
	
	
	public static void makePayment() {
    	Scanner sc = new Scanner(System.in); 
    	ArrayList<Order> unpaidOrders = new ArrayList<>();
    	unpaidOrders = OrderManager.getUnpaidOrders(); // need to print unpaid orders
    	for(int i= 0; i<unpaidOrders.size(); i++) {
    		System.out.format("Table ID:  %d		Paid Status: %b", unpaidOrders.get(i).getTableID() , unpaidOrders.get(i).getPaidStatus());
    	}
    	System.out.println("\nEnter tableID to make payment"); 	
    	int tableId = -1;
		while (tableId==-1)
		{
			try {
				tableId = sc.nextInt();
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not an Integer. Try Again:");
			}
		}
		Order order = OrderManager.getOrderByTableId(tableId);
		String customerName = ReservationManager.getUnfinishedReservationOfTableIDNow(tableId).getCustomerID();
		Customer customer = CustomerManager.retrieveCustomerbyIDinput(customerName);
		double subtotal = PaymentManager.calculateSubtotal(order.getOrderItems());
		boolean membershipApplied = false; 
		if(customer.getpartnerMembership() || customer.getrestaurantMembership()) {
			membershipApplied = true; 
		}
		String paymentDate = PaymentManager.getPaymentDateTime(); 
		double memberDiscount = PaymentManager.calculateMemberDiscount(membershipApplied, subtotal);
		double gst = subtotal * GST; 
		double serviceCharge = subtotal * SERVICE_CHARGE; 
		double grandTotal = subtotal + gst + serviceCharge - memberDiscount; 
		int reservationNumber = ReservationManager.getUnfinishedReservationOfTableIDNow(tableId).getReservationID();

		Payment payment = new Payment(paymentDate, subtotal, gst, serviceCharge,
						memberDiscount, grandTotal, order, reservationNumber, tableId, 
						membershipApplied );
		PaymentManager.printReceipt(payment);
		TableLayoutManager.freeTableStatus(tableId);
		ReservationManager.setIsFinished(payment.getreservationNumber());
		OrderManager.updatePaidStatus(payment.getOrder().getOrderID()); 
		PaymentManager.addPayment(payment);
		
	}
	
	
    public void printSaleReport() {
    	ArrayList<Payment> paymentinvoices = PaymentManager.getPaymentInvoices();
    	System.out.println("(1) Print sale revenue report by day\n(2) Print sale revenue report by month");
    	Scanner sc = new Scanner(System.in); 
    	int choice, i;
    	String period; 
		double totalRevenueBeforeTax = 0;
		double totalRevenueAfterTax = 0;
    	try {
    		choice = sc.nextInt();
    		if(choice != 1 || choice != 2) {
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
    			if(payment.getpaymentDate().regionMatches(3, period, 0, 7)){
    				// prints out payment date and payment ID
    				System.out.println("Date: " + payment.getpaymentDate() + "       " + "Payment ID: " + payment.getpaymentID());
    				Order order = payment.getOrder(); 
    				ArrayList<MenuItem> orderItems = order.getOrderItems();
    				for(i =0; i<orderItems.size(); i++) { // prints out all orders, menu type and price without gst
    					System.out.println("Menu Item: " + orderItems.get(i).getMenuItemName() + "\t" + orderItems.get(i).getMenuItemType().name() +
    							"\t" + orderItems.get(i).getMenuItemPrice());
    				}
    				totalRevenueBeforeTax += payment.getSubTotal();
    				totalRevenueAfterTax += payment.grandTotal(); 
    			}
    		}
    		// if == 0, no payment found
    		if(totalRevenueBeforeTax == 0) {
    			System.out.println("No payment records found");
    		}
    		else {
    			System.out.println("Total revenue before tax and discounts: $" + totalRevenueBeforeTax);
    			System.out.println("Total revenue after tax and discounts: $" + totalRevenueAfterTax);
    		}
    		break; 
    		
    	case 2: 
    		System.out.println("Enter the month and year in the following format MM/yyyy");
    		period = sc.nextLine();
    		DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM/yyyy");
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
    					System.out.println("Menu Item: " + orderItems.get(i).getMenuItemName() + "\t" + orderItems.get(i).getMenuItemType().name() +
    							"\t" + orderItems.get(i).getMenuItemPrice());
    				}
    				totalRevenueBeforeTax += payment.getSubTotal();
    				totalRevenueAfterTax += payment.grandTotal();
    			}
    		}
    		// if == 0, no payment found
    		if(totalRevenueBeforeTax == 0) {
    			System.out.println("No payment records found");
    		}
    		else {
    			System.out.println("Total revenue before tax and discounts: $" + totalRevenueBeforeTax);
    			System.out.println("Total revenue after tax and discounts: $" + totalRevenueAfterTax);
    		}
    		break;   
    	}
    }

}
