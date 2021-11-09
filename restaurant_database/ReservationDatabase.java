package restaurant_database;


//import relevant java libraries to be updated as we go..
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter; 
import java.io.FileNotFoundException;

import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
//import Customer class
import restaurant_entity.MenuItem.type;
import restaurant_entity.PromotionPackage;
import restaurant_entity.Reservation;
import restaurant_manager.MenuManager;
import restaurant_manager.ReservationManager;

public class ReservationDatabase{
	public static final String DELIMITER = ";";

	public static void fwrite(String saveFileName) {
		try {
			if (ReservationManager.getListOfReservations().size()==0) {
				throw new Exception("Nothing to save.");
			}
			FileWriter myWriter = new FileWriter(saveFileName);
			String pusher="";
			for (int i=0;i<ReservationManager.getListOfReservations().size();i++) {
				Reservation txMedium=ReservationManager.getListOfReservations().get(i); //object
				pusher+=String.valueOf(txMedium.getReservationID())+DELIMITER; //reservationID
				pusher+=String.valueOf(txMedium.getTableID())+DELIMITER; //tableID
				pusher+=String.valueOf(txMedium.getPax())+DELIMITER; //pax
				pusher+=txMedium.getCustomerID()+DELIMITER; //customerID
				pusher+=txMedium.getReservationStartTime().toString()+DELIMITER;
				pusher+=txMedium.getReservationEndTime().toString()+DELIMITER;
				pusher+=String.valueOf(txMedium.getIsFinished())+DELIMITER;
				pusher+=String.valueOf(txMedium.getIsAppeared())+DELIMITER;
				pusher+="\n";
			}
			myWriter.write(pusher);
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		}catch (IOException e) {
			System.out.println("An error occured when writing to file "+saveFileName);
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("No data to save!");
		}
	}
	public static ArrayList<Reservation> fread(String loadFileName) {
		try {
		      File menuDB = new File(loadFileName);
		      Scanner myReader = new Scanner(menuDB);
		      if (!myReader.hasNextLine()) {
		    	  myReader.close();
		    	  throw new Exception("Empty File");
		      }
		      ArrayList<Reservation> loadedReservations=new ArrayList<Reservation>();
		      while (myReader.hasNextLine()) {
		        String[] data = myReader.nextLine().split(DELIMITER);
		        int ReservationID=Integer.parseInt(data[0]);
		        int tableID=Integer.parseInt(data[1]);
		        int pax=Integer.parseInt(data[2]);
		        String customerID=data[3];
		        LocalDateTime reservationStartTime=LocalDateTime.parse(data[4]);
		        LocalDateTime reservationEndTime=LocalDateTime.parse(data[5]);
		        Boolean isFinished=Boolean.valueOf(data[6]);
		        Boolean isAppeared=Boolean.valueOf(data[7]);
		        Reservation.setCounter(ReservationID);
		        loadedReservations.add(new Reservation(tableID,pax,customerID,reservationStartTime,reservationEndTime,isFinished,isAppeared));
		      }
		      myReader.close();
		      if (loadedReservations.size()==0) {
		    	  throw new Exception("Empty File");
		      }
		      ArrayList <Reservation> sortedReservations=new ArrayList<Reservation>();
		      ArrayList <Integer> sortedItemId=new ArrayList<Integer>();
		      for (int i=0;i<loadedReservations.size();i++) {
		    	  if (sortedItemId.contains(loadedReservations.get(i).getReservationID())) {
		    		  continue;
		    	  }
		    	  sortedItemId.add(loadedReservations.get(i).getReservationID());
		      }
		      sortedItemId.sort(null);
		      for(int i=0;i<sortedItemId.size();i++) {
		    	  for (int k=0;k<loadedReservations.size();k++) {
		    		  if (sortedItemId.get(i)==loadedReservations.get(k).getReservationID()) {
		    			  	sortedReservations.add(loadedReservations.get(k));
		    			  break;
		    		  }
		    	  }
		      }
		      Reservation.setCounter(sortedItemId.get(sortedItemId.size()-1)+1);
		      System.out.println(loadFileName+ " file contents loaded successfully.");
		      return sortedReservations;
		    } catch (FileNotFoundException e) {
		      System.out.println("File is missing.");
		      e.printStackTrace();
		    } catch (Exception e) {
				System.out.println("File is empty. Save data to DataBase before loading.");
				e.printStackTrace();
			}
		System.out.println("Error, File was not loaded.");
		return ReservationManager.getListOfReservations();
	}


}
