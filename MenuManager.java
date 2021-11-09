package restaurant_manager;
import restaurant_entity.MenuItem.type;
import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.PromotionPackage;
import java.util.Scanner;

import restaurant_database.MenuDatabase;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter; 
import java.io.FileNotFoundException;

public class MenuManager {
	private static Menu mainMenu=new Menu();
	public static Menu getMainMenu() {
		return mainMenu;
	}
	public static void createItem(String name,String description,type itemType,double price) { //Append AlaCarte item to mainMenu
		System.out.println("------------------------------------");
		mainMenu.getListOfMenuItems().add(new MenuItem(name,description,itemType,price));
		mainMenu.getListOfMenuItems().get(mainMenu.getListOfMenuItems().size()-1).printAll();
	}
	public static void createItem(String name,String description,type itemType,double price,ArrayList<MenuItem> promoPackItems) { //Append promoPack to mainMenu
		System.out.println("------------------------------------");
		System.out.println("New Promotion Package added to Menu:");
		mainMenu.getListOfMenuItems().add(new PromotionPackage(name,description,itemType,price,promoPackItems));
		mainMenu.getListOfMenuItems().get(mainMenu.getListOfMenuItems().size()-1).printAll();
	}
	public static void removeItem(int removalIndex) { //removes item by marking in enum type DELETED prefix
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
	public static Menu getMenuInstance() { //returns a Menu object of the current mainMenu
		return mainMenu;
	}
	/*public static void createItemQuery() { //Queries user for item to create. either alacarte item or promo pack
		Scanner sc=new Scanner(System.in);
		Menu alaCarteMenu=new Menu(mainMenu.getAlaCarteMenuItems());
		ArrayList<MenuItem> promoPackItems;
		int pcheck=-1,typeChoice=-1,pSize=-1,pChoice=-1;
		String name,description;
		double price=-1;
		type itemType;
		Menu alacarteMenu=new Menu(mainMenu.getAlaCarteMenuItems());
		if(alacarteMenu.presentSize()!=0) { //if no ala carte items then don't branch here
			System.out.println("Do you want to make a promotional package?\n1.Yes\n2.No");
			while (pcheck==-1) {
				try {
					pcheck=sc.nextInt();
					sc.nextLine();
				}
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not an Integer. Try Again:");
				}
			}
		}
		else {
			pcheck=2;
		}
		if (pcheck!=1 && pcheck!=2) {
			System.out.println("Invalid choice");
			return;
		}
		System.out.println("What is the name of item to create?");
		name=sc.nextLine();
		System.out.println("What is the description?");
		description=sc.nextLine();
		if (pcheck==2) { //enum type with uncategorized as default
			System.out.println("What is the type of the item?\n1.Appetizer\n2.Main\n3.Side\n4.Dessert\n5.Drinks");
			while (typeChoice==-1) {
				try {
					typeChoice=sc.nextInt();
					sc.nextLine();
				}
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not an Integer. Try Again:");
				}
			}
			switch (typeChoice){
			case 1: itemType=type.APPETIZER;
			break;
			case 2: itemType=type.MAIN;
			break;
			case 3: itemType=type.SIDE;
			break;
			case 4: itemType=type.DESSERT;
			break;
			case 5: itemType=type.DRINKS;
			break;
			default:itemType=type.UNCATEGORIZED;
			break;
			}
		}
		else {
			itemType=type.PROMOTION;
		}
		System.out.println("What will be the price of the item?");
		while (price==-1) {
			try {
				price=sc.nextDouble();
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not a Double. Try Again:");
			}
		}
		if (pcheck==1) {
			System.out.println("How many ala carte items are in the package?");
			while (pSize==-1) {
				try {
					pSize=sc.nextInt();
					sc.nextLine();
					if (pSize<0) {
						System.out.println("Invalid Amount. Try Again:");
					}
					else {
						break;
					}
				}
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not an Integer. Try Again:");
				}
			}
			promoPackItems=new ArrayList<MenuItem>();  //ArrayList of Menu item to hold promo package items before creation of PromotioPackage object in mainMenu arraylist
			for (int i=0;i<pSize;i++) {
				alaCarteMenu.printMenu();
				System.out.println("Which alaCarte item should be included? (type itemID then enter):");
				pChoice=-1;
				while (pChoice==-1) {
					try {
						pChoice=sc.nextInt();
						sc.nextLine();
						pChoice=alaCarteMenu.ItemIDToIndex(pChoice,false);
						if (pChoice!=-1) {
							break;
						}
						else {
							System.out.println("Invalid choice. Try Again:");
						}
					}
					catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Not an Integer. Try Again:");
					}
				}
				promoPackItems.add(alaCarteMenu.getListOfMenuItems().get(pChoice));
			}
			createItem(name,description,itemType,price,promoPackItems);
		}
		else{
			createItem(name,description,itemType,price);
		}
	}
	public static void removeItemQuery() {// Remove item based on itemid. print statement to show user before making selection. Removal of alacarte also removes promopack containing it
		int removalIndex=-1;
		int[] cascadeRemove=new int[mainMenu.getListOfMenuItems().size()-mainMenu.getAlaCarteMenuItems().size()];
		int cRTrack=0;//tracker for promo packs to remove if alacarte item is contained within
		if (mainMenu.presentSize()<1) {
			System.out.println("Nothing to remove");
			return;
		}
		Scanner sc=new Scanner(System.in);
		mainMenu.printMenu();
		System.out.println("Which item should be removed?");
		while (removalIndex==-1) {
			try {
				removalIndex=mainMenu.ItemIDToIndex(sc.nextInt(),false);
				sc.nextLine();
				if (removalIndex!=-1) {
					break;
				}
				else {
					System.out.println("Invalid choice. Try Again:");
				}
			}
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not an Integer. Try Again:");
			}
		}
		for (int i=0;i<mainMenu.getListOfMenuItems().size();i++) {
			if (mainMenu.getListOfMenuItems().get(i).getMenuItemType()==type.PROMOTION) {
				PromotionPackage promotionPackageTemp= (PromotionPackage) mainMenu.getListOfMenuItems().get(i);
				for (int k=0;k<promotionPackageTemp.getPromotionPackageItems().size();k++) {
					if (promotionPackageTemp.getPromotionPackageItems().get(k)==mainMenu.getListOfMenuItems().get(removalIndex)) {
						cascadeRemove[cRTrack]=i;
						cRTrack++;
						break;
					}
				}
			}
		}
		if (cRTrack!=0) {
			System.out.format("Warning: Removing this item will also remove %d Promotional Packages.\nContinue?\n1.Yes\n2.No\n",cRTrack);
			int cascadeRemoveChoice=-1;
			while (cascadeRemoveChoice==-1) {
				try {
					cascadeRemoveChoice=sc.nextInt();
					sc.nextLine();
					if (cascadeRemoveChoice!=1 &&cascadeRemoveChoice!=2) {
						cascadeRemoveChoice=-1;
						throw new Exception("Bad input.");
					}
					else if (cascadeRemoveChoice==1) {
						break;
					}
					else {
						System.out.println("Removal aborted. Returning...");
						return;
					}
				}catch (Exception e) {
					System.out.println("Invalid input. Try Again: ");
				}
			}
		}
		removeItem(removalIndex);
		for (int i=0;i<cRTrack;i++) {
			removeItem(cascadeRemove[i]);
		}
	}
	public static void updateItemQuery() { //Updates either name/price/description/type or if promo pack
		int updateIndex=-1,choice=-1,typeChoice=-1,pSize=-1,pChoice=-1;
		if (mainMenu.presentSize()<1) {
			System.out.println("Nothing to update");
			return;
		}
		Scanner sc=new Scanner(System.in);
		mainMenu.printMenu();
		System.out.println("Which item should be updated?");
		while (updateIndex==-1) {
			try {
				updateIndex=mainMenu.ItemIDToIndex(sc.nextInt(),false);
				sc.nextLine();
				if (updateIndex!=-1) {
					break;
				}
				else {
					System.out.println("Invalid choice. Try Again:");
				}
			}
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not an Integer. Try Again:");
			}
		}
		if (mainMenu.getListOfMenuItems().get(updateIndex).getMenuItemType()!=type.PROMOTION) {
			MenuItem toUpdate=mainMenu.getListOfMenuItems().get(updateIndex);
			String name=toUpdate.getMenuItemName();
			String description=toUpdate.getMenuItemDescription();
			type itemType=toUpdate.getMenuItemType();
			double price=toUpdate.getMenuItemPrice();
			while (choice<5) {
				System.out.println("What would you like to update?\n1.Name\n2.Description\n3.Item Type\n4.Price\n5.Done");
				choice=-1;
				while (choice==-1) {
					try {
						choice=sc.nextInt();
						sc.nextLine();
						if (choice>0 && choice<=5) {
							break;
						}
						else {
							System.out.println("Invalid choice. Try Again:");
						}
					}
					catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Not an Integer. Try Again:");
					}
				}
				switch (choice) {
				case 1:
					System.out.println("What is the new name?");
					name=sc.nextLine();
					break;
				case 2:
					System.out.println("What is the new description?");
					description=sc.nextLine();
					break;
				case 3:
					System.out.println("What is the type of the item?\n1.Appetizer\n2.Main\n3.Side\n4.Dessert\n5.Drinks");
					while (typeChoice==-1) {
						try {
							typeChoice=sc.nextInt();
							sc.nextLine();
						}
						catch(InputMismatchException e) {
							sc.nextLine();
							System.out.println("Not an Integer. Try Again:");
						}
					}
					switch (typeChoice){
					case 1: itemType=type.APPETIZER;
					break;
					case 2: itemType=type.MAIN;
					break;
					case 3: itemType=type.SIDE;
					break;
					case 4: itemType=type.DESSERT;
					break;
					case 5: itemType=type.DRINKS;
					break;
					default:
					break;
					}
					break;
				case 4:
					System.out.println("What is the new price?");
					price=-1;
					while (price==-1) {
						try {
							price=sc.nextDouble();
							sc.nextLine();
						}
						catch(InputMismatchException e) {
							sc.nextLine();
							System.out.println("Not a Double. Try Again:");
						}
					}
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid choice. Update Aborted.");
					return;
				}
			}
			updateItem(updateIndex,name,description,itemType,price);
		}
		else {
			PromotionPackage toUpdate=(PromotionPackage) mainMenu.getListOfMenuItems().get(updateIndex);
			String name=toUpdate.getMenuItemName();
			String description=toUpdate.getMenuItemDescription();
			type itemType=toUpdate.getMenuItemType();
			Menu alaCarteMenu=new Menu(mainMenu.getAlaCarteMenuItems());
			ArrayList<MenuItem> promoPackItems=toUpdate.getPromotionPackageItems();
			ArrayList<MenuItem> promoPackItemsReplace=new ArrayList<MenuItem>();//store new
			double price=toUpdate.getMenuItemPrice();
			while (choice<5) {
				System.out.println("What would you like to update?\n1.Name\n2.Description\n3.Item Type\n4.Promotion Pack Items\n5.Done");
				choice=-1;
				while (choice==-1) {
					try {
						choice=sc.nextInt();
						sc.nextLine();
						if (choice>0 && choice<=5) {
							break;
						}
						else {
							System.out.println("Invalid choice. Try Again:");
						}
					}
					catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Not an Integer. Try Again:");
					}
				}
				switch (choice) {
				case 1:
					System.out.println("What is the new name?");
					name=sc.nextLine();
					break;
				case 2:
					System.out.println("What is the new description?");
					description=sc.nextLine();
					break;
				case 3:
					System.out.println("What is the new price?");
					price=-1;
					while (price==-1) {
						try {
							price=sc.nextDouble();
							sc.nextLine();
						}
						catch(InputMismatchException e) {
							sc.nextLine();
							System.out.println("Not a Double. Try Again:");
						}
					}
					break;
				case 4:
					System.out.println("How many ala carte items are in the package?");
					while (pSize<0) {
						try {
							pSize=sc.nextInt();
							sc.nextLine();
							if (pSize<0) {
								System.out.println("Invalid Amount. Try Again:");
							}
							else {
								break;
							}
						}
						catch(InputMismatchException e) {
							sc.nextLine();
							System.out.println("Not an Integer. Try Again:");
						}
					}
					for (int i=0;i<pSize;i++) {
						System.out.println("Which alaCarte item should be included? (type itemID then enter):");
						alaCarteMenu.printMenu();
						pChoice=-1;
						while (pChoice==-1) {
							try {
								pChoice=sc.nextInt();
								sc.nextLine();
								pChoice=alaCarteMenu.ItemIDToIndex(pChoice,false);
								if (pChoice!=-1) {
									break;
								}
								else {
									System.out.println("Invalid choice. Try Again:");
								}
							}
							catch(InputMismatchException e) {
								sc.nextLine();
								System.out.println("Not an Integer. Try Again:");
							}
						}
						promoPackItemsReplace.add(alaCarteMenu.getListOfMenuItems().get(pChoice));
					}
					promoPackItems=promoPackItemsReplace;
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid choice. Update Aborted.");
					return;
				}
			}
			updateItem(updateIndex,name,description,itemType,price,promoPackItems); //atomic
		}
	}*/
	public static void updateItem(int updateIndex,String Name,String Description,type itemType,double price) {
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
	public static void updateItem(int updateIndex,String Name,String Description,type itemType,double price,ArrayList<MenuItem> promoPackItems) {
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
	public static MenuItem getItem() { 
		Scanner sc=new Scanner(System.in);
		int choice;
		System.out.println("Please select a menu item (By ItemId):");
		mainMenu.printMenu();
		choice=mainMenu.ItemIDToIndex(sc.nextInt(),false);
		return mainMenu.getListOfMenuItems().get(choice);
	}
	
	public static MenuItem getItemFromAll(int menuItemID) { 
		int choice=mainMenu.ItemIDToIndex(menuItemID,true); //get MenuItem from deleted too
		return mainMenu.getListOfMenuItems().get(choice);
	}
	
	public static void printMainMenu() {
		for (int i=0;i<mainMenu.getListOfMenuItems().size();i++) {
			if (!mainMenu.getListOfMenuItems().get(i).getMenuItemType().toString().substring(0,3).contains("DEL")){
				mainMenu.printMenu();
				return;
			}
		}
		System.out.println("Nothing to print!");
		return;
	}
	
	public static void saveToDB(String textFileName) {
		MenuDatabase.fwrite(textFileName);
	}
	public static void loadDB(String textFileName) {
		ArrayList<MenuItem> loadedMenuItemsList;
		loadedMenuItemsList=MenuDatabase.fread(textFileName);
		mainMenu.setListOfMenuItems(loadedMenuItemsList);
	}
}

