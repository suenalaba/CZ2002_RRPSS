package restaurant_entity;

import restaurant_entity.MenuItem.type;
import java.util.ArrayList;
/**
 * A class defining Menu.
 * @author Jacques
 * @version 4.5
 * @since 13-11-2021
 */
public class Menu {
	private ArrayList<MenuItem> listOfMenuItems;
	/**
	 * Constructor. Make a new arraylist containing MenuItem objects. and assign it to variable listOfMenuItems.
	 */
	public Menu(){
		this.listOfMenuItems=new ArrayList<MenuItem>();
	}
	/**
	 * Constructor. Set menu's items with an arraylist of menuItem objects.
	 * @param listOfMenuItems ArrayList of MenuItem objects.
	 */
	public Menu(ArrayList<MenuItem> listOfMenuItems){ //Constructor with parameters
		this.listOfMenuItems=listOfMenuItems;
	}
	/**
	 * Get ArrayList of menu items in menu. Public method.
	 * @return listOfMenuItems ArrayList of MenuItem objects.
	 */
	public ArrayList<MenuItem> getListOfMenuItems() { //get method for listOfMenuItems ArrayList
		return this.listOfMenuItems;
	}
	/**
	 * Get ArrayList of ala carte menu items in menu. Public method.
	 * @return listOfMenuItems ArrayList of MenuItem objects.
	 */
	public ArrayList<MenuItem> getAlaCarteMenuItems() { //get method for AlaCarte Items ArrayList
		int arrTrack=0;
		ArrayList<MenuItem> alaCarteMenu;
		int[] alaCarteIdentities=new int[listOfMenuItems.size()];
		for (int i=0;i<listOfMenuItems.size();i++) {
			if (listOfMenuItems.get(i).getMenuItemType()!=type.PROMOTION && !listOfMenuItems.get(i).getMenuItemType().toString().substring(0,3).contains("DEL")) {
				alaCarteIdentities[arrTrack]=i;
				arrTrack++;
			}
		}
		alaCarteMenu=new ArrayList<MenuItem>();
		for (int i=0;i<arrTrack;i++) {
			alaCarteMenu.add(listOfMenuItems.get(alaCarteIdentities[i]));
		}
		return alaCarteMenu;
	}
	/**
	 * Call this method to print all menu items in format specified within MenuItems/PromotionPackage class.
	 */
	public void printMenu() {
		System.out.println("Menu Items:");
		for (int i=0;i<listOfMenuItems.size();i++) {
			if (!listOfMenuItems.get(i).getMenuItemType().toString().substring(0,3).contains("DEL")) {
				listOfMenuItems.get(i).printAll();
			}
			else {
				continue;
			}
		}
	}
	/**
	 * Get size(integer) of non-deleted items. Public method.
	 * @return Integer size of non-deleted items
	 */
	public int presentSize() {
		int sizeTrack=0;
		for (int i=0;i<listOfMenuItems.size();i++) {
			if (!listOfMenuItems.get(i).getMenuItemType().toString().substring(0,3).contains("DEL")) {
				sizeTrack++;
			}
			else {
				continue;
			}
		}
		return sizeTrack;
	}
	/**
	 * Set list of menu items in menu.
	 * Used in reading and loading database.
	 * @param newListOfMenuItems ArrayList of MenuItems.
	 */
	public void setListOfMenuItems(ArrayList<MenuItem> newListOfMenuItems) { 
		this.listOfMenuItems=newListOfMenuItems;
	}
	/**
	 * Search ArrayList<MenuItem> for corresponding ID,
	 * then returns MenuItem ArrayList index or -1 if it does not exist.
	 * @param ID integer 
	 * @param allowDeleted boolean will allow for return of deleted index
	 * @return MenuItem ArrayList index or -1 if it does not exist.
	 */
	public int ItemIDToIndex(int ID,Boolean allowDeleted) {
		for (int i=0;i<this.listOfMenuItems.size();i++) {
			if (this.listOfMenuItems.get(i).getMenuItemID()==ID && !listOfMenuItems.get(i).getMenuItemType().toString().substring(0,3).contains("DEL")) {
				return i;
			}
			else if (this.listOfMenuItems.get(i).getMenuItemID()==ID && allowDeleted==true) {
				return i;
			}
		}
		return -1;
	}
}

