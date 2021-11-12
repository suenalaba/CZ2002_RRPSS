
package restaurant_entity;

import java.util.ArrayList;
import java.util.List;

import restaurant_entity.Table.status;

public class TableLayout {
	//attribute
	private ArrayList<Table> arrayOfTables;
	
	public TableLayout() {
		this.arrayOfTables=new ArrayList<>();
	}
	
	
	public TableLayout(ArrayList<Table> arrayOfTables) {
		this.arrayOfTables=arrayOfTables;
	}

	//method
	public ArrayList<Table> getTableLayout(){
		return arrayOfTables;
	}

	public void setTableLayout(ArrayList<Table> newLayout) {
		this.arrayOfTables = newLayout; 
	}
	
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
	
	public int tableIDToIndex(int ID) {//return index of TableID in TableLayout arrayOfTables
		for (int i=0;i<this.arrayOfTables.size();i++) {
			if (this.arrayOfTables.get(i).getTableID()==ID) {
				return i;
				}
			}
		return -1;
		}
}
