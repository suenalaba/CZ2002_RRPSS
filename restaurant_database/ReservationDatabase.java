package restaurant_database;

//import relevant java libraries to be updated as we go..
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter; 

//import Reservation Classes
import restaurant_entity.Reservation;
import restaurant_manager.ReservationManager;
/**
 * Subclass of DatabaseFunction
 * ReservationDatabase reads and write to reservationDB.txt
 * @author Wong Xin Pei
 * @version 4.5
 * @since 2021-11-13
 *
 */

public class ReservationDatabase implements DatabaseFunction{
	/**
	 * DELIMITER to split tokens
	 */
	public static final String DELIMITER = ";";
	
	/**
	 * Writes data from reservationM to reservationDB.txt
	 * @param saveFileName reservationDB.txt
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	@Override
	public void fwrite(String saveFileName) throws IOException {
			ReservationManager reservationM=ReservationManager.getInstance();
			FileWriter myWriter = new FileWriter(saveFileName);
			String pusher="";
			for (int i=0;i<reservationM.getListOfReservations().size();i++) {
				Reservation txMedium=reservationM.getListOfReservations().get(i); //object
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
	}
	
	/**
	 * Reads data from reservationDB.txt to ArrayList<Reservation>
	 * @param loadFileName reservationDB.txt
	 * @return sortedReservation Arraylist of sorted Reservation class
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 */
	@Override
	public ArrayList<Reservation> fread(String loadFileName) throws IOException {
		      File menuDB = new File(loadFileName);
		      Scanner myReader = new Scanner(menuDB);
		      if (!myReader.hasNextLine()) {
		    	  myReader.close();
		    	  throw new IOException("Empty File");
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
		    	  throw new IOException("Empty File");
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
		      return sortedReservations;
	}
}