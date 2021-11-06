package restaurant_entity;

import restaurant_entity.MenuItem.type;
import java.util.ArrayList;

public class Menu {
	private ArrayList<MenuItem> listOfMenuItems;
	public Menu(){ //Constructor with no parameters
		this.listOfMenuItems=new ArrayList<MenuItem>();
	}
	public Menu(ArrayList<MenuItem> listOfMenuItems){ //Constructor with parameters
		this.listOfMenuItems=listOfMenuItems;
	}
	public ArrayList<MenuItem> getListOfMenuItems() { //get method for listOfMenuItems ArrayList
		return this.listOfMenuItems;
	}
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
	public void printMenu() { //prints all menu items
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
	public int presentSize() { //size of non-deleted item array
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
	public void setListOfMenuItems(ArrayList<MenuItem> newListOfMenuItems) { //set method for listOfMenuItems ArrayList
		this.listOfMenuItems=newListOfMenuItems;
	}
	public int ItemIDToIndex(int ID,Boolean allowDeleted) {//searches ArrayList for corresponding ID then returns MenuItem ArrayList index otherwise -1 // allowDeleted will allow for return of deleted index
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
