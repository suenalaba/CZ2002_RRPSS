
package restaurant_entity;

import java.util.ArrayList;
import java.util.List;

import restaurant_entity.Table.status;
/**
 * A class managing the table objects.
 * @author yize
 * @version 4.5
 * @since 13-11-2021
 */
public class TableLayout {
	private ArrayList<Table> arrayOfTables; //An ArrayList storing Table objects.
	/**
	 * Declare new array list for variable, arrayOfTables.
	 * ArrayList arrayOfTable has table object as its type.
	 */
	public TableLayout() {
		this.arrayOfTables=new ArrayList<>();
	}
	/**
	 * Call this method to get ArrayList containing all existing table objects.
	 * Public method.
	 * @return an ArrayList containing table objects.
	 */
	public ArrayList<Table> getTableLayout(){
		return arrayOfTables;
	}
	/**
	 * Set variable arrayOfTables.
	 * @param newLayout ArrayList containing table objects.
	 */
	public void setTableLayout(ArrayList<Table> newLayout) {
		this.arrayOfTables = newLayout; 
	}
	/**
	 * Call this method to sort existing array of tables by tableID. Public method.
	 */
	public void sortArrayOfTables() { //sorts by tableID
		if (this.arrayOfTables.size()==0) {
			return;
		}
		ArrayList<Table> toSort=arrayOfTables;
		ArrayList<Table> sorted=new ArrayList<Table>();
		ArrayList<Integer> tableIdList=new ArrayList<Integer>();
		for (int i=0;i<toSort.size();i++) {
			tableIdList.add(toSort.get(i).getTableID());
		}
		tableIdList.sort(null);
		for (int i=0;i<tableIdList.size();i++) {
			for (int k=0;i<toSort.size();k++) {
				if (tableIdList.get(i)==toSort.get(k).getTableID()) {
					sorted.add(toSort.get(k));
					break;
				}
			}
		}
		this.arrayOfTables=sorted;
	}
	/**
	 * Call this method to print existing tables and reveal their status for each hour block.
	 */
	public void printTableLayout() { //prints table layout and blocks of time
		System.out.println("Table no. (Px)/Time|09|10|11|12|13|14|15|16|17|18|19|20|21|");
		List<Table> subsetOfTables=arrayOfTables;
		for (int i=0;i<subsetOfTables.size();i++) {
			System.out.format("Table %03d (%02d)     |",subsetOfTables.get(i).getTableID(),subsetOfTables.get(i).getTableCapacity());
			for (int k=0;k<subsetOfTables.get(i).getHourBlock().length;k++) {
				if (k<9||k>=22) {
					continue;
				}
				if (subsetOfTables.get(i).getHourBlock()[k]==status.RESERVED) {
					System.out.print("RS|");
				}
				else if (subsetOfTables.get(i).getHourBlock()[k]==status.OCCUPIED) {
					System.out.print("OC|");
				}
				else if (subsetOfTables.get(i).getHourBlock()[k]==status.CLOSED) {
					System.out.print("CL|");
				}
				else {
					System.out.print("  |");
				}
			}
			System.out.println("");
		}
	}
}
