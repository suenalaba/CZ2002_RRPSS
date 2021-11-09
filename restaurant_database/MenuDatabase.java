package restaurant_database;


//import relevant java libraries to be updated as we go..
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter; 
import java.io.FileNotFoundException;

import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
//import Customer class
import restaurant_entity.MenuItem.type;
import restaurant_entity.PromotionPackage;
import restaurant_manager.MenuManager;

public class MenuDatabase implements DatabaseFunction{
	public static final String DELIMITER = ";";

	public void fwrite(String saveFileName) {
		File menuDB=new File(saveFileName);
		try {
			if (MenuManager.getMenuInstance().getListOfMenuItems().size()==0) {
				throw new Exception("Nothing to save.");
			}
			FileWriter myWriter = new FileWriter(saveFileName);
			String pusher="";
			for (int i=0;i<MenuManager.getMenuInstance().getListOfMenuItems().size();i++) {
				MenuItem txMedium=MenuManager.getMenuInstance().getListOfMenuItems().get(i);
				if (txMedium.getMenuItemType()==type.PROMOTION || txMedium.getMenuItemType()==type.DELETEDPROMOTION) {
					PromotionPackage txPMedium=(PromotionPackage) txMedium;
					pusher+=String.valueOf(txPMedium.getMenuItemID())+DELIMITER;
					pusher+=txPMedium.getMenuItemName()+DELIMITER;
					pusher+=String.valueOf(txPMedium.getMenuItemType())+DELIMITER;
					pusher+=String.valueOf(txPMedium.getMenuItemPrice())+DELIMITER;
					pusher+=txPMedium.getMenuItemDescription()+DELIMITER;
					for (int k=0;k<txPMedium.getPromotionPackageItems().size();k++) {
						pusher+=String.valueOf(txPMedium.getPromotionPackageItems().get(k).getMenuItemID())+DELIMITER;
					}
					pusher+="\n";
				}
				else {
					pusher+=String.valueOf(txMedium.getMenuItemID())+DELIMITER;
					pusher+=txMedium.getMenuItemName()+DELIMITER;
					pusher+=String.valueOf(txMedium.getMenuItemType())+DELIMITER;
					pusher+=String.valueOf(txMedium.getMenuItemPrice())+DELIMITER;
					pusher+=txMedium.getMenuItemDescription()+DELIMITER;
					pusher+="\n";
				}
			}
			myWriter.write(pusher);
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		}catch (IOException e) {
			System.out.println("An error occured when writing to file "+menuDB.getName());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("No data to save!");
		}
	}
	public ArrayList<MenuItem> fread(String saveFileName) {
		try {
		      File menuDB = new File(saveFileName);
		      Scanner alacarteReader = new Scanner(menuDB);
		      if (!alacarteReader.hasNextLine()) {
		    	  alacarteReader.close();
		    	  throw new Exception("Empty File");
		      }
		      ArrayList<MenuItem> loadedMenu=new ArrayList<MenuItem>();
		      while (alacarteReader.hasNextLine()) {
		        String[] data = alacarteReader.nextLine().split(DELIMITER);
		        int itemID=Integer.parseInt(data[0]);
		        String itemName=data[1];
		        type itemType=type.valueOf(data[2]);
		        if (itemType==type.PROMOTION || itemType==type.DELETEDPROMOTION) {
		        	continue;
		        }
		        double itemPrice=Double.parseDouble(data[3]);
		        String itemDescription=data[4];
		        MenuItem.setRunningCount(itemID);
		        loadedMenu.add(new MenuItem(itemName,itemDescription,itemType,itemPrice));
		      }
		      alacarteReader.close();
		      Scanner promoReader = new Scanner(menuDB);
		      while (promoReader.hasNextLine()) {
		        String[] data = promoReader.nextLine().split(DELIMITER);
		        int puller=0;
		        int itemID=Integer.parseInt(data[0]);
		        String itemName=data[1];
		        type itemType=type.valueOf(data[2]);
		        if (itemType!=type.PROMOTION || itemType!=type.DELETEDPROMOTION) {
		        	continue;
		        }
		        double itemPrice=Double.parseDouble(data[3]);
		        String itemDescription=data[4];
		        ArrayList<MenuItem> promoPackItems=new ArrayList<MenuItem>();
		        Menu loadedAlacarteMenu=new Menu(loadedMenu);
		        for (int i=5;i<data.length;i++) {
		        	try {
		        		promoPackItems.add(loadedAlacarteMenu.getListOfMenuItems().get(loadedAlacarteMenu.ItemIDToIndex(Integer.parseInt(data[i]),true)));
		        	}
		        	catch (Exception e) {
		        		puller=1;
		        	}
		        }
		        MenuItem.setRunningCount(itemID);
		        if (puller==0) {
		        	loadedMenu.add(new PromotionPackage(itemName,itemDescription,itemType,itemPrice,promoPackItems));
		        }
		        else {
		        	continue;
		        }
		      }
		      promoReader.close();
		      Menu menuHolder=new Menu(MenuManager.getMenuInstance().getListOfMenuItems());
		      if (loadedMenu.size()>0) {
		    	  menuHolder.setListOfMenuItems(loadedMenu);
		      }
		      else {
		    	  throw new Exception("Empty File");
		      }
		      ArrayList <MenuItem> sortedMenu=new ArrayList<MenuItem>();
		      ArrayList <Integer> sortedItemId=new ArrayList<Integer>();
		      for (int i=0;i<menuHolder.getListOfMenuItems().size();i++) {
		    	  if (sortedItemId.contains(menuHolder.getListOfMenuItems().get(i).getMenuItemID())) {
		    		  continue;
		    	  }
		    	  sortedItemId.add(menuHolder.getListOfMenuItems().get(i).getMenuItemID());
		      }
		      sortedItemId.sort(null);
		      for(int i=0;i<sortedItemId.size();i++) {
		    	  sortedMenu.add(menuHolder.getListOfMenuItems().get(menuHolder.ItemIDToIndex(sortedItemId.get(i),true)));
		      }
		      MenuItem.setRunningCount(sortedItemId.get(sortedItemId.size()-1)+1);
		      System.out.println("MenuDB.txt file contents loaded successfully.");
		      return sortedMenu;
		    } catch (FileNotFoundException e) {
		      System.out.println("File is missing.");
		      e.printStackTrace();
		    } catch (Exception e) {
				System.out.println("File is empty. Save data to DataBase before loading.");
				e.printStackTrace();
			}
		System.out.println("Error, File was not loaded.");
		return MenuManager.getMenuInstance().getListOfMenuItems();
	}


}
