package groupProject;

public class MenuItem {
	public enum type{
		APPETIZER,
		MAIN,
		SIDE,
		DESSERT,
		DRINKS,
		PROMOTION
	}
	private int menuItemID;
	private String menuItemName;
	private String menuItemDescription;
	private type menuItemType;
	private double menuItemPrice;
	private static int runningCount=0;
	public MenuItem(String name,String description, type itemType,double price) { //ADD THIS TO CLASS DIAGRAM (CONSTRUCTOR))
		this.menuItemName=name;
		this.menuItemDescription=description;
		this.menuItemType=itemType;
		this.menuItemPrice=price;
		this.menuItemID=runningCount;
		runningCount++;
	}
	public int getMenuItemID() {
		return this.menuItemID;
	}
	public void setMenuItemID(int menuItemID) {
		this.menuItemID=menuItemID;
	}
	public String getMenuItemName() {
		return this.menuItemName;
	}
	public void setMenuItemName(String menuItemName) {
		this.menuItemName=menuItemName;
	}
	public String getMenuItemDescription() {
		return this.menuItemDescription;
	}
	public void setMenuItemDescription(String menuItemDescription) {
		this.menuItemDescription=menuItemDescription;
	}
	public type getMenuItemType() {
		return this.menuItemType;
	}
	public void setMenuItemType(type itemType) {
		this.menuItemType=itemType;
	}
	public double getMenuItemPrice() {
		return this.menuItemPrice;
	}
	public void setMenuItemPrice(Double MenuItemPrice) {
		this.menuItemPrice=MenuItemPrice;
	}
	public void printAll() {
		System.out.format("ItemID: %03d\n",this.menuItemID);
		System.out.format("Name: %s\n", this.menuItemName);
		System.out.format("Item Type: %s\n",this.menuItemType.toString());
		System.out.format("Price: %.02f\n",this.menuItemPrice);
		System.out.format("Description: %s\n", this.menuItemDescription);
	}
	public static int getRunningCount() {
		return runningCount;
	}
	public static void setRunningCount(int rc) {
		runningCount=rc;
	}
}
