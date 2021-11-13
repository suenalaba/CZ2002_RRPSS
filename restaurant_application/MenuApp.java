package restaurant_application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant_entity.Menu;
import restaurant_entity.MenuItem;
import restaurant_entity.PromotionPackage;
import restaurant_entity.MenuItem.type;
import restaurant_manager.MenuManager;
/**
 * The Menu Boundary class for user interaction
 * @author	Jacques
 * @version	version 4.5
 * @since	13-11-2021
 */
public class MenuApp {
	/**
	 * The create item query that interacts with user
	 * Uses MenuManager methods
	 * If there is an Alacarte MenuItem object in records that is not deleted in records, then Promotion Package can be made.
	 * A Menu Item has the creation stages of naming, describing,item typing and pricing
	 * A Promotion Package has the creation stages of naming, describing, pricing and which menu items to include. Item Typing for Promotion Packages defaults to PROMOTION
	 * Extensive Research and Development is done before creating new Menu Item object
	 */
	public void createItemQuery() { 
		MenuManager menuM=MenuManager.getInstance();
		Scanner sc=new Scanner(System.in);
		Menu alaCarteMenu=new Menu(menuM.getMainMenu().getAlaCarteMenuItems());
		ArrayList<MenuItem> promoPackItems;
		int pcheck=-1,typeChoice=-1,pSize=-1,pChoice=-1;
		String name,description;
		double price=-1;
		type itemType;
		Menu alacarteMenu=new Menu(menuM.getMainMenu().getAlaCarteMenuItems());
		if(alacarteMenu.presentSize()!=0) { 
			System.out.println("Do you want to make a promotional package?\n1.Yes\n2.No");
			while (pcheck==-1) {
				try {
					pcheck=sc.nextInt();
					sc.nextLine();
				}
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not a valid option. Try Again:");
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
		if (pcheck==2) { 
			System.out.println("What is the type of the item?\n1.Appetizer\n2.Main\n3.Side\n4.Dessert\n5.Drinks");
			while (typeChoice==-1) {
				try {
					typeChoice=sc.nextInt();
					sc.nextLine();
				}
				catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Not a valid option. Try Again:");
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
				if (price<=0) {
					System.out.println("Price must be positive.");
					price=-1;
				}
			}
			catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not a number. Try Again:");
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
					System.out.println("Not a whole number. Try Again:");
				}
			}
			promoPackItems=new ArrayList<MenuItem>();  
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
						System.out.println("Not a whole number. Try Again:");
					}
				}
				promoPackItems.add(alaCarteMenu.getListOfMenuItems().get(pChoice));
			}
			menuM.createItem(name,description,itemType,price,promoPackItems);
		}
		else{
			menuM.createItem(name,description,itemType,price);
		}
	}
	/**
	 * Removes Item based on query of Item ID by prefixing the itemType with "DELETE"
	 * The list of items will be printed for ease of selection.
	 * There will be a cascade deletion if the item deleted is part of a PromotionPackage as the PromotionPackage cannot exist without the AlaCarte Menu Item.
	 * A confirmation must be given if a cascade deletion will occur.
	 * Careful thought is done for deletion. It will not happen during operating hours.
	 */
	public void removeItemQuery() {
		MenuManager menuM=MenuManager.getInstance();
		int removalIndex=-1;
		int[] cascadeRemove=new int[menuM.getMainMenu().getListOfMenuItems().size()-menuM.getMainMenu().getAlaCarteMenuItems().size()];
		int cRTrack=0;
		if (menuM.getMainMenu().presentSize()<1) {
			System.out.println("Nothing to remove");
			return;
		}
		Scanner sc=new Scanner(System.in);
		menuM.getMainMenu().printMenu();
		System.out.println("Which item should be removed?");
		while (removalIndex==-1) {
			try {
				removalIndex=menuM.getMainMenu().ItemIDToIndex(sc.nextInt(),false);
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
				System.out.println("Not a whole number. Try Again:");
			}
		}
		for (int i=0;i<menuM.getMainMenu().getListOfMenuItems().size();i++) {
			if (menuM.getMainMenu().getListOfMenuItems().get(i).getMenuItemType()==type.PROMOTION) {
				PromotionPackage promotionPackageTemp= (PromotionPackage) menuM.getMainMenu().getListOfMenuItems().get(i);
				for (int k=0;k<promotionPackageTemp.getPromotionPackageItems().size();k++) {
					if (promotionPackageTemp.getPromotionPackageItems().get(k)==menuM.getMainMenu().getListOfMenuItems().get(removalIndex)) {
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
		menuM.removeItem(removalIndex);
		System.out.println("Item removed and any promotion package containing it.");
		for (int i=0;i<cRTrack;i++) {
			menuM.removeItem(cascadeRemove[i]);
		}
	}
	/**
	 * Updates Item based on Item ID
	 * List of available items will be displayed
	 * For Menu Item or AlaCarte Items, these can be changed: Name, Description,ItemType & Price
	 * For PromotionPackages, these can be change: Name, Description, Price & AlaCarte MenuItems under PromotionPackage
	 */
	public void updateItemQuery() { 
		MenuManager menuM=MenuManager.getInstance();
		int updateIndex=-1,choice=-1,typeChoice=-1,pSize=-1,pChoice=-1;
		if (menuM.getMainMenu().presentSize()<1) {
			System.out.println("Nothing to update");
			return;
		}
		Scanner sc=new Scanner(System.in);
		menuM.getMainMenu().printMenu();
		System.out.println("Which item should be updated?");
		while (updateIndex==-1) {
			try {
				updateIndex=menuM.getMainMenu().ItemIDToIndex(sc.nextInt(),false);
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
				System.out.println("Not a whole number. Try Again:");
			}
		}
		if (menuM.getMainMenu().getListOfMenuItems().get(updateIndex).getMenuItemType()!=type.PROMOTION) {
			MenuItem toUpdate=menuM.getMainMenu().getListOfMenuItems().get(updateIndex);
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
						System.out.println("Not a valid choice. Try Again:");
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
							System.out.println("Not a valid choice. Try Again:");
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
							System.out.println("Not a number. Try Again:");
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
			menuM.updateItem(updateIndex,name,description,itemType,price);
		}
		else {
			PromotionPackage toUpdate=(PromotionPackage) menuM.getMainMenu().getListOfMenuItems().get(updateIndex);
			String name=toUpdate.getMenuItemName();
			String description=toUpdate.getMenuItemDescription();
			type itemType=toUpdate.getMenuItemType();
			Menu alaCarteMenu=new Menu(menuM.getMainMenu().getAlaCarteMenuItems());
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
						System.out.println("Not a valid choice. Try Again:");
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
							System.out.println("Not a number. Try Again:");
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
								System.out.println("Not a whole number. Try Again:");
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
			menuM.updateItem(updateIndex,name,description,itemType,price,promoPackItems); //atomic
		}
	}
}
