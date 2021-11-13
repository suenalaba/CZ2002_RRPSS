package restaurant_entity;
import java.util.ArrayList;
/**
 * A class defining a PromotionPackage object. It extends MenuItem class.
 * @author Jacques
 * @version 4.5
 * @since 13-11-2021
 */
public class PromotionPackage extends MenuItem{
	private ArrayList<MenuItem> listOfPromotionPackageItems;
	/**
	 * Constructor. Create a promotion package (object).
	 * @param name
	 * @param description
	 * @param itemType
	 * @param price
	 * @param listOfPromotionPackageItems
	 */
	public PromotionPackage(String name,String description, type itemType,double price,ArrayList<MenuItem> listOfPromotionPackageItems) {
		super(name,description,itemType,price);
		this.listOfPromotionPackageItems=listOfPromotionPackageItems;
	}
	/**
	 * Get promotional package items. Public method.
	 * @return listOfPromotionPackageItems ArrayList of MenuItem objects.
	 */
	public ArrayList<MenuItem> getPromotionPackageItems() {
		return this.listOfPromotionPackageItems;
	}
	/**
	 * Set menu items in promotional package object. Public method.
	 * @param listOfPromotionPackageItems ArrayList of MenuItem objects.
	 */
	public void setPromotionPackageItems(ArrayList<MenuItem> listOfPromotionPackageItems) {
		this.listOfPromotionPackageItems=listOfPromotionPackageItems;
	}
	/**
	 *Call this method to print all promotion packages with their details. Public method.
	 */
	public void printAll() {
		System.out.format("ItemID: %03d\n",this.getMenuItemID());
		System.out.format("Name: %s\n", this.getMenuItemName().substring(0,1).toUpperCase()+this.getMenuItemName().substring(1).toLowerCase());
		System.out.format("Item Type: %s\n",this.getMenuItemType().toString().substring(0,1).toUpperCase()+this.getMenuItemType().toString().substring(1).toLowerCase());
		System.out.format("Price: $%.02f\n",this.getMenuItemPrice());
		System.out.format("Description: %s\n", this.getMenuItemDescription().substring(0,1).toUpperCase()+this.getMenuItemDescription().substring(1).toLowerCase());
		System.out.println("This package contains:-------------");
		for (int i=0;i<this.listOfPromotionPackageItems.size();i++) {
			System.out.format("ItemID: %03d\n",this.listOfPromotionPackageItems.get(i).getMenuItemID());
			System.out.format("Name: %s\n", this.listOfPromotionPackageItems.get(i).getMenuItemName().substring(0,1).toUpperCase()+this.listOfPromotionPackageItems.get(i).getMenuItemName().substring(1).toLowerCase());
		}
		System.out.println("-----------------------------------");
	}
}

