package group;

import java.util.ArrayList;

public class TableLayout {
	// attribute name : arrayOfTables
	private ArrayList<Table> arrayOfTables = new ArrayList<>();
	
	public ArrayList<Table> getTableLayout(){
		return arrayOfTables;
	}

	public void setTableLayout(ArrayList<Table> newLayout) {
		this.arrayOfTables = newLayout; 
	}
}
