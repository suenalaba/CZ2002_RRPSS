package groupProject;

import groupProject.MenuItem.type;

public class Menu {
	private MenuItem[] listOfMenuItems;
	public Menu(){
		this.listOfMenuItems=new MenuItem[0];
	}
	public Menu(MenuItem[] listOfMenuItems){
		this.listOfMenuItems=listOfMenuItems;
	}
	public MenuItem[] getListOfMenuItems() {
		return this.listOfMenuItems;
	}
	public MenuItem[] getAlaCarteMenuItems() {
		int arrTrack=0;
		MenuItem[] alaCarteMenu;
		int[] alaCarteIdentities=new int[listOfMenuItems.length];
		for (int i=0;i<listOfMenuItems.length;i++) {
			if (listOfMenuItems[i].getMenuItemType()!=type.PROMOTION) {
				alaCarteIdentities[arrTrack]=i;
				arrTrack++;
			}
		}
		alaCarteMenu=new MenuItem[arrTrack];
		arrTrack=0;
		for (int i=0;i<alaCarteMenu.length;i++) {
			alaCarteMenu[i]=listOfMenuItems[alaCarteIdentities[i]];
		}
		return alaCarteMenu;
	}
	public void printMenu() {
		for (int i=0;i<listOfMenuItems.length;i++) {
			System.out.format("%03d%s",listOfMenuItems[i].getMenuItemID(),listOfMenuItems[i].getMenuItemName());
		}
	}
	public void setListOfMenuItems(MenuItem[] newListOfMenuItems) {
		this.listOfMenuItems=newListOfMenuItems;
	}
	public int ItemIDToIndex(int ID) {//returns MenuItem array index otherwise -1
		for (int i=0;i<this.listOfMenuItems.length;i++) {
			if (this.listOfMenuItems[i].getMenuItemID()==ID) {
				return i;
			}
		}
		return -1;
	}
}
