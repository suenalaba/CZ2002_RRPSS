package restaurant_manager;
import restaurant_entity.MenuItem.type;
import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.PromotionPackage;

import java.util.Scanner;

import restaurant_database.MenuDatabase;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.IOException;

/**
 * Stores a Menu Object that carries an array of MenuItem objects. The methods in the MenuManager are used to manipulate the Menu and and Menu Items.
 * @author	Jacques
 * @version 4.5
 * @since	13-11-2021
 */
public class MenuManager {
	/**
	 * For singleton pattern adherence. This MenuManager instance persists throughout runtime.
	 */
	private static MenuManager instance=null;
	/**
	 * Menu Object that holds an arrayList of MenuItems.
	 */
	Menu mainMenu = new Menu();
	/**
	 * Default Constructor for MenuManager.
	 */
	public MenuManager() {
		mainMenu=new Menu();
	}
	/**
	 * For singleton pattern adherence. 
	 * @return instance The static instance that persists throughout runtime.
	 */
	public static MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }
	/**
	 * Gives the Menu object stored in MenuManager instance.
	 * @return Menu Object of instance
	 */
	public Menu getMainMenu() {
		return mainMenu;
	}
	/**
	 * Adds MenuItem to mainMenu ArrayList of MenuItem
	 * @param name Name of new MenuItem
	 * @param description Description of new MenuItem
	 * @param itemType ItemType of new MenuItem
	 * @param price Price of new MenuItem
	 */
	public void createItem(String name,String description,type itemType,double price) { //Append AlaCarte item to mainMenu
		System.out.println("------------------------------------");
		mainMenu.getListOfMenuItems().add(new MenuItem(name,description,itemType,price));
		mainMenu.getListOfMenuItems().get(mainMenu.getListOfMenuItems().size()-1).printAll();
	}
	/**
	 * Method overloaded for adding PromotionPackage to mainMenu ArrayList of MenuItem
	 * @param name Name of new PromotionPackage
	 * @param description Description of new PromotionPackage
	 * @param itemType ItemType of new PromotionPackage
	 * @param price Price of new PromotionPackage
	 * @param promoPackItems ArrayList of MenuItem in PromotionPackage
	 */
	public void createItem(String name,String description,type itemType,double price,ArrayList<MenuItem> promoPackItems) { //Append promoPack to mainMenu
		System.out.println("------------------------------------");
		System.out.println("New Promotion Package added to Menu:");
		mainMenu.getListOfMenuItems().add(new PromotionPackage(name,description,itemType,price,promoPackItems));
		mainMenu.getListOfMenuItems().get(mainMenu.getListOfMenuItems().size()-1).printAll();
	}
	/**
	 * Sets the Item as deleted according to index
	 * @param removalIndex the index of the MenuItem inside the mainMenu ArrayList of MenuItem
	 */
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
	/**
	 * Returns mainMenuInstance
	 * @return mainMenu the Menu object of the MenuManager instance
	 */
	public Menu getMenuInstance() { //returns a Menu object of the current mainMenu
		return mainMenu;
	}
	/**
	 * Updates Items according to parameters by using set methods of the MenuItem object
	 * @param updateIndex The index of the MenuItem relative to ArrayList inside the mainMenu
	 * @param Name The new name of the MenuItem
	 * @param Description The new Description of the MenuItem
	 * @param itemType The new itemType of the MenuItem
	 * @param price The new price of the MenuItem
	 */
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
	/**
	 * Method overloaded to cater to PromotionPackage generalization
	 * @param updateIndex The index of the PromotionPackage relative to ArrayList inside the mainMenu
	 * @param Name The new name of the PromotionPackage
	 * @param Description The new description of the PromotionPackage
	 * @param itemType The new itemType of the PromotionPackage
	 * @param price The new price of the PromotionPackage
	 * @param promoPackItems The new ArrayList of MenuItem inside the PromotionPackage
	 */
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
	/**
	 * Returns the MenuItem object according to a query on itemID
	 * @return MenuItem object of with itemID corresponding to query input otherwise return null if query input is invalid or out of range
	 */
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
	/**
	 * Get MenuItem from all MenuItems inclusive of deleted ones
	 * @param menuItemID The ID of MenuITem or PromotionPackage
	 * @return MenuItem object
	 */
	public MenuItem getItemFromAll(int menuItemID) { 
		int choice=mainMenu.ItemIDToIndex(menuItemID,true); //get MenuItem from deleted too
		return mainMenu.getListOfMenuItems().get(choice);
	}
	/**
	 * Print mainMenu MenuItem objects that are not DELETED
	 */
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
	/**
	 * Saves the instance's Menu as string in a text file.
	 * @param textFileName The name of the the text file.
	 */
	public void saveDB(String textFileName) {
		MenuDatabase saver=new MenuDatabase();
		try {
			saver.fwrite(textFileName);
		} catch (IOException e) {
			System.out.println("Failed to save "+textFileName);
			return;
		}
	}
	/**
	 * Loads to instance's Menu from a text file
	 * @param textFileName The name of the text file.
	 */
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

