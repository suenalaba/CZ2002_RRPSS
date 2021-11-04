package restaurant_manager;

import java.util.Scanner;
import restaurant_entity.MenuItem.type;
import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.PromotionPackage;

public class MenuManager {
	private static Menu mainMenu=new Menu();
	private static void createItem(String name,String description,type itemType,double price) {
		int mmsize=mainMenu.getListOfMenuItems().length;
		MenuItem[] newMenuArr=new MenuItem[mmsize+1];
		for (int i=0;i<mmsize;i++) {
			newMenuArr[i]=mainMenu.getListOfMenuItems()[i]; 
		}
		MenuItem createMI=new MenuItem(name,description,itemType,price);
		newMenuArr[mmsize]=createMI;
		System.out.println("New Ala Carte Items added to Menu:");
		System.out.format("Name:%s\n",name);
		System.out.format("Description:%s\n",description);
		System.out.format("Type:%s\n",itemType.toString().toLowerCase());
		System.out.format("Price:%f\n",price);
		mainMenu.setListOfMenuItems(newMenuArr);
	}
	private static void createItem(String name,String description,type itemType,double price,MenuItem[] promoPackItems) {
		int mmsize=mainMenu.getListOfMenuItems().length;
		MenuItem[] newMenuArr=new MenuItem[mmsize+1];
		for (int i=0;i<mmsize;i++) {
			newMenuArr[i]=mainMenu.getListOfMenuItems()[i]; 
		}
		PromotionPackage createPP=new PromotionPackage(name,description,itemType,price,promoPackItems);
		newMenuArr[mmsize]=createPP;
		System.out.println("New Promotion Package added to Menu:");
		System.out.format("Name:%s\n",name);
		System.out.format("Description:%s\n",description);
		System.out.format("Type:%s\n",itemType.toString().toLowerCase());
		System.out.format("Price:%f\n",price);
		System.out.println("Package includes:");
		Menu tempMenu=new Menu(promoPackItems);
		tempMenu.printMenu();
		mainMenu.setListOfMenuItems(newMenuArr);
	}
	private static void removeItem(int removalIndex) {
		int arrTrack=0;
		MenuItem[] newMenu=new MenuItem[mainMenu.getListOfMenuItems().length-1];
		for (int i=0;i<mainMenu.getListOfMenuItems().length;i++) {
			if (i==removalIndex) {
				continue;
			}
			else {
				newMenu[arrTrack]=mainMenu.getListOfMenuItems()[i];
			}
		}
		mainMenu.setListOfMenuItems(newMenu);
	}
	public static void createItemQuery() {
		Scanner sc=new Scanner(System.in);
		Menu alaCarteMenu=new Menu(mainMenu.getAlaCarteMenuItems());
		MenuItem[] promoPackItems;
		int pcheck,psize,pchoice;
		String name,description;
		double price;
		type itemType;
		System.out.println("Do you want to make a promotional package?\n1.YES\n2.NO");
		pcheck=sc.nextInt();
		if (pcheck!=1 && pcheck!=2) {
			System.out.println("Invalid choice");
			return;
		}
		sc.nextLine();
		System.out.println("What is the name of item to create?");
		name=sc.nextLine();
		System.out.println("What is the description?");
		description=sc.nextLine();
		if (pcheck==2) {
			System.out.println("What is the type of the item?\n1.Appetizer\n2.Main\n3.Side\n4.Dessert\n5.Drinks");
			switch (sc.nextInt()){
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
			default:itemType=type.MAIN;
			break;
			}
		}
		else {
			itemType=type.PROMOTION;
		}
		System.out.println("What will be the price of the item?");
		price=sc.nextDouble();
		if (pcheck==1) {
			System.out.println("How many ala carte items are in the package?");
			psize=sc.nextInt();
			promoPackItems=new MenuItem[psize]; 
			for (int i=0;i<psize;i++) {
				System.out.println("Which alaCarte item should be included? (type itemID then enter):");
				alaCarteMenu.printMenu();
				pchoice=sc.nextInt();
				pchoice=alaCarteMenu.ItemIDToIndex(pchoice);
				promoPackItems[i]=alaCarteMenu.getListOfMenuItems()[pchoice]; //end of query stage
			}
			createItem(name,description,itemType,price,promoPackItems);
		}
		else{
			createItem(name,description,itemType,price);
		}
	}
	public static void removeItemQuery() {
		int removalIndex;
		Scanner sc=new Scanner(System.in);
		System.out.println("Which item should be removed?");
		mainMenu.printMenu();
		removalIndex=mainMenu.ItemIDToIndex(sc.nextInt());
		removeItem(removalIndex);
	}
	public static void updateItemQuery() {
		int updateIndex;
		int choice=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("Which item should be updated?");
		mainMenu.printMenu();
		updateIndex=mainMenu.ItemIDToIndex(sc.nextInt());
		if (mainMenu.getListOfMenuItems()[updateIndex].getMenuItemType()!=type.PROMOTION) {
			MenuItem toUpdate=mainMenu.getListOfMenuItems()[updateIndex];
			String name=toUpdate.getMenuItemName();
			String description=toUpdate.getMenuItemDescription();
			type itemType=toUpdate.getMenuItemType();
			double price=toUpdate.getMenuItemPrice();
			while (choice<5) {
				System.out.println("What would you like to update?\n1.Name\n2.Description\n3.Item Type\n4.Price\n5.Done");
				choice=sc.nextInt();
				sc.nextLine();
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
					switch (sc.nextInt()){
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
					default:itemType=type.MAIN;
					break;
					}
					break;
				case 4:
					System.out.println("What is the new price?");
					price=sc.nextDouble();
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
			PromotionPackage toUpdate=(PromotionPackage) mainMenu.getListOfMenuItems()[updateIndex];
			String name=toUpdate.getMenuItemName();
			String description=toUpdate.getMenuItemDescription();
			type itemType=toUpdate.getMenuItemType();
			Menu alaCarteMenu=new Menu(mainMenu.getAlaCarteMenuItems());
			MenuItem[] promoPackItems=toUpdate.getPromotionPackageItems();
			MenuItem[] promoPackItems2;//store new
			double price=toUpdate.getMenuItemPrice();
			while (choice<5) {
				System.out.println("What would you like to update?\n1.Name\n2.Description\n3.Item Type\n4.Price\n5.Done");
				choice=sc.nextInt();
				sc.nextLine();
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
					price=sc.nextDouble();
					break;
				case 4:
					System.out.println("How many ala carte items are in the package?");
					int psize=sc.nextInt();
					int pchoice;
					promoPackItems2=new MenuItem[psize]; 
					for (int i=0;i<psize;i++) {
						System.out.println("Which alaCarte item should be included? (type itemID then enter):");
						alaCarteMenu.printMenu();
						pchoice=sc.nextInt();
						pchoice=alaCarteMenu.ItemIDToIndex(pchoice);
						promoPackItems2[i]=alaCarteMenu.getListOfMenuItems()[pchoice];
					}
					promoPackItems=promoPackItems2;
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid choice. Update Aborted.");
					return;
				}
			}
			updateItem(updateIndex,name,description,itemType,price,promoPackItems);
		}
	}
	private static void updateItem(int updateIndex,String Name,String Description,type itemType,double price) {
		mainMenu.getListOfMenuItems()[updateIndex].setMenuItemName(Name);
		mainMenu.getListOfMenuItems()[updateIndex].setMenuItemDescription(Description);
		mainMenu.getListOfMenuItems()[updateIndex].setMenuItemType(itemType);
		mainMenu.getListOfMenuItems()[updateIndex].setMenuItemPrice(price);
	}
	private static void updateItem(int updateIndex,String Name,String Description,type itemType,double price,MenuItem[] promoPackItems) {
		PromotionPackage Promo=(PromotionPackage) mainMenu.getListOfMenuItems()[updateIndex];
		Promo.setMenuItemName(Name);
		Promo.setMenuItemDescription(Description);
		Promo.setMenuItemType(itemType);
		Promo.setMenuItemPrice(price);
		Promo.setPromotionPackageItems(promoPackItems);
		mainMenu.getListOfMenuItems()[updateIndex]=Promo;
	}
	public static void printMainMenu() {
		mainMenu.printMenu();
		}
	public static void loadDB() {}
	public static void saveToDB() {}
}
