package restaurant_manager;
import restaurant_entity.MenuItem.type;
import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.PromotionPackage;
import restaurant_entity.TableLayout;

import java.util.Scanner;

import restaurant_database.MenuDatabase;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter; 
import java.io.FileNotFoundException;

public class MenuManager {
	private static MenuManager instance=null;
	Menu mainMenu = new Menu();
	
	public MenuManager() {
		mainMenu=new Menu();
	}
	
	public static MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }
	
	public Menu getMainMenu() {
		return mainMenu;
	}
	public void createItem(String name,String description,type itemType,double price) { //Append AlaCarte item to mainMenu
		System.out.println("------------------------------------");
		mainMenu.getListOfMenuItems().add(new MenuItem(name,description,itemType,price));
		mainMenu.getListOfMenuItems().get(mainMenu.getListOfMenuItems().size()-1).printAll();
	}
	public void createItem(String name,String description,type itemType,double price,ArrayList<MenuItem> promoPackItems) { //Append promoPack to mainMenu
		System.out.println("------------------------------------");
		System.out.println("New Promotion Package added to Menu:");
		mainMenu.getListOfMenuItems().add(new PromotionPackage(name,description,itemType,price,promoPackItems));
		mainMenu.getListOfMenuItems().get(mainMenu.getListOfMenuItems().size()-1).printAll();
	}
	public void removeItem(int removalIndex) { //removes item by marking in enum type DELETED prefix
		type typeCheck=mainMenu.getListOfMenuItems().get(removalIndex).getMenuItemType();
		switch(typeCheck){
			case APPETIZER:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDAPPETIZER);
				break;
			case MAIN:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDMAIN);
				break;
			case SIDE:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDSIDE);
				break;
			case DESSERT:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDDESSERT);
				break;
			case DRINKS:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDDRINKS);
				break;
			case PROMOTION:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDPROMOTION);
				break;
			case UNCATEGORIZED:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDUNCATEGORIZED);
				break;
			default:
				mainMenu.getListOfMenuItems().get(removalIndex).setMenuItemType(type.DELETEDUNCATEGORIZED);
				break;
		}
	}
	public Menu getMenuInstance() { //returns a Menu object of the current mainMenu
		return mainMenu;
	}

	public void updateItem(int updateIndex,String Name,String Description,type itemType,double price) {
		System.out.println("Item Update");
		System.out.println("From:");
		mainMenu.getListOfMenuItems().get(updateIndex).printAll();
		mainMenu.getListOfMenuItems().get(updateIndex).setMenuItemName(Name);
		mainMenu.getListOfMenuItems().get(updateIndex).setMenuItemDescription(Description);
		mainMenu.getListOfMenuItems().get(updateIndex).setMenuItemType(itemType);
		mainMenu.getListOfMenuItems().get(updateIndex).setMenuItemPrice(price);
		System.out.println("To:");
		mainMenu.getListOfMenuItems().get(updateIndex).printAll();
	}
	public void updateItem(int updateIndex,String Name,String Description,type itemType,double price,ArrayList<MenuItem> promoPackItems) {
		System.out.println("###Item Update###");
		System.out.println("From:");
		mainMenu.getListOfMenuItems().get(updateIndex).printAll();
		PromotionPackage Promo=(PromotionPackage) mainMenu.getListOfMenuItems().get(updateIndex);
		Promo.setMenuItemName(Name);
		Promo.setMenuItemDescription(Description);
		Promo.setMenuItemType(itemType);
		Promo.setMenuItemPrice(price);
		Promo.setPromotionPackageItems(promoPackItems);
		System.out.println("To:");
		mainMenu.getListOfMenuItems().get(updateIndex).printAll();
	}
	public MenuItem getItem() { 
		Scanner sc=new Scanner(System.in);
		int choice;
		System.out.println("Please select a menu item (By ItemId):");
		mainMenu.printMenu();
		mainMenu.getListOfMenuItems();
		ArrayList<Integer> menuItems=new ArrayList<Integer>();
		for (MenuItem o:mainMenu.getListOfMenuItems()) {
			menuItems.add(o.getMenuItemID());
		}
		try {
			choice=sc.nextInt();
			if(!menuItems.contains(choice)) {
				System.out.println("Menu Item ID does not exist.");
				return null;
			}
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input.");
			return null;
		}
		choice=mainMenu.ItemIDToIndex(choice,false);
		return mainMenu.getListOfMenuItems().get(choice);
	}
	
	public MenuItem getItemFromAll(int menuItemID) { 
		int choice=mainMenu.ItemIDToIndex(menuItemID,true); //get MenuItem from deleted too
		return mainMenu.getListOfMenuItems().get(choice);
	}
	
	public void printMainMenu() {
		for (int i=0;i<mainMenu.getListOfMenuItems().size();i++) {
			if (!mainMenu.getListOfMenuItems().get(i).getMenuItemType().toString().substring(0,3).contains("DEL")){
				mainMenu.printMenu();
				return;
			}
		}
		System.out.println("Nothing to print!");
		return;
	}
	
	public void saveDB(String textFileName) {
		MenuDatabase saver=new MenuDatabase();
		try {
			saver.fwrite(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to save "+textFileName);
			return;
		}
	}
	public void loadDB(String textFileName) {
		MenuDatabase loader=new MenuDatabase();
		ArrayList<MenuItem> loadedMenuItemsList;
		try {
			loadedMenuItemsList=loader.fread(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to load "+textFileName);
			return;
		}
		mainMenu.setListOfMenuItems(loadedMenuItemsList);
		System.out.println("Loaded successfully from "+textFileName);
	}
}

