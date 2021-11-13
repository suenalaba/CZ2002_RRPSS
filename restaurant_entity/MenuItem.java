package restaurant_entity;
/**
 * A class defining a MenuItem object.
 * @author Jacques
 * @version 4.5
 * @since 13-11-2021
 */
public class MenuItem {
	/**
	 * Declare constants for enum menu item type (enum type). 
	 * Used to track what type of food category the item is.
	 */
	public enum type{
		APPETIZER,
		DELETEDAPPETIZER,
		MAIN,
		DELETEDMAIN,
		SIDE,
		DELETEDSIDE,
		DESSERT,
		DELETEDDESSERT,
		DRINKS,
		DELETEDDRINKS,
		PROMOTION,
		DELETEDPROMOTION,
		UNCATEGORIZED,
		DELETEDUNCATEGORIZED
	}
	private int menuItemID;
	private String menuItemName;
	private String menuItemDescription;
	private type menuItemType;
	private double menuItemPrice;
	private static int runningCount=1; //used to track the number to Menu items.
	public MenuItem(String name,String description, type itemType,double price) { //ADD THIS TO CLASS DIAGRAM (CONSTRUCTOR))
		this.menuItemName=name;
		this.menuItemDescription=description;
		this.menuItemType=itemType;
		this.menuItemPrice=price;
		this.menuItemID=runningCount;
		runningCount++;
	}
	/**
	 * Get menuItemID of the menu item object. Public method.
	 * @return integer menuItemID
	 */
	public int getMenuItemID() {
		return this.menuItemID;
	}
	/**
	 * Set MenuItemID of menu item object.
	 * @param integer menuItemID
	 */
	public void setMenuItemID(int menuItemID) {
		this.menuItemID=menuItemID;
	}
	/**
	 * Get menuItemName of the menu item object. Public method.
	 * @return String menuItemName
	 */
	public String getMenuItemName() {
		return this.menuItemName;
	}
	/**
	 * Set MenuItemID of menu item object. Public method.
	 * @param String menuItemName
	 */
	public void setMenuItemName(String menuItemName) {
		this.menuItemName=menuItemName;
	}
	/**
	 * Get String menuItemDescription for menuItem object. Public method.
	 * @return String menuItemDesciption
	 */
	public String getMenuItemDescription() {
		return this.menuItemDescription;	
	}
	/**
	 * Set menu item description for menu item object. Public method.
	 * @param String menuItemDescription
	 */
	public void setMenuItemDescription(String menuItemDescription) {
		this.menuItemDescription=menuItemDescription;
	}
	/**
	 * Get enum type for menuItem object. Public method.
	 * @return enum type menuItemType
	 */
	public type getMenuItemType() {
		return this.menuItemType;
	}
	/**
	 * Set menu item type for menu item object. Public method.
	 * @param enum type itemType
	 */
	public void setMenuItemType(type itemType) {
		this.menuItemType=itemType;
	}
	/**
	 * Get price of menuItem object. Public method.
	 * @return double menuItemPrice
	 */
	public double getMenuItemPrice() {
		return this.menuItemPrice;
	}
	/**
	 * Set menu item price for menu item object. Public method.
	 * @param double MenuItemPrice
	 */
	public void setMenuItemPrice(Double MenuItemPrice) {
		this.menuItemPrice=MenuItemPrice;
	}
	/**
	 * Print all menu items and their details.
	 */
	public void printAll() {
		System.out.format("ItemID: %03d\n",this.menuItemID);
		System.out.format("Name: %s\n", this.menuItemName.substring(0,1).toUpperCase()+this.menuItemName.substring(1).toLowerCase());
		System.out.format("Item Type: %s\n",this.getMenuItemType().toString().substring(0,1).toUpperCase()+this.getMenuItemType().toString().substring(1).toLowerCase());
		System.out.format("Price: $%.02f\n",this.menuItemPrice);
		System.out.format("Description: %s\n", this.menuItemDescription.substring(0,1).toUpperCase()+this.menuItemDescription.substring(1).toLowerCase());
		System.out.println("-----------------------------------");
	}
	/**
	 * Get existing runningCount(Integer). Public method.
	 * @return integer runningCount.
	 */
	public static int getRunningCount() {
		return runningCount;
	}
	/**
	 * Update existing runningCount. Public method.
	 * @param integer runningCount
	 */
	public static void setRunningCount(int rc) {
		runningCount=rc;
	}
}

