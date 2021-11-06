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
			if (listOfMenuItems.get(i).getMenuItemType()!=type.PROMOTION) {
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
			listOfMenuItems.get(i).printAll();
		}
	}
	public void setListOfMenuItems(ArrayList<MenuItem> newListOfMenuItems) { //set method for listOfMenuItems ArrayList
		this.listOfMenuItems=newListOfMenuItems;
	}
	public int ItemIDToIndex(int ID) {//searches ArrayList for corresponding ID then returns MenuItem ArrayList index otherwise -1
		for (int i=0;i<this.listOfMenuItems.size();i++) {
			if (this.listOfMenuItems.get(i).getMenuItemID()==ID) {
				return i;
			}
		}
		return -1;
	}
}
