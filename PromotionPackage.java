package groupProject;

public class PromotionPackage extends MenuItem{
	private MenuItem[] listOfPromotionPackageItems;
	public PromotionPackage(String name,String description, type itemType,double price,MenuItem[] listOfPromotionPackageItems) {
		super(name,description,itemType,price);
		this.listOfPromotionPackageItems=listOfPromotionPackageItems;
	}
	public MenuItem[] getPromotionPackageItems() {
		return this.listOfPromotionPackageItems;
	}
	public void setPromotionPackageItems(MenuItem[] listOfPromotionPackageItems) {
		this.listOfPromotionPackageItems=listOfPromotionPackageItems;
	}
	public void printAll() {
		System.out.format("ItemID: %03d\n",this.getMenuItemID());
		System.out.format("Name: %s\n", this.getMenuItemName());
		System.out.format("Item Type: %s\n",this.getMenuItemType().toString());
		System.out.format("Price: %.02f\n",this.getMenuItemPrice());
		System.out.format("Description: %s\n", this.getMenuItemDescription());
		System.out.println("This package contains:-----------------------");
		for (int i=0;i<this.listOfPromotionPackageItems.length;i++) {
			System.out.format("ItemID: %03d\n",this.listOfPromotionPackageItems[i].getMenuItemID());
			System.out.format("Name: %s\n", this.listOfPromotionPackageItems[i].getMenuItemName());
		}
		System.out.println("---------------------------------------------");
	}
}
