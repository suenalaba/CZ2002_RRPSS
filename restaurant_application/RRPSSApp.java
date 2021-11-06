package restaurant_application;
import restaurant_manager.MenuManager;
import restaurant_manager.OrderManager;
import restaurant_manager.TableLayoutManager;
import java.util.Scanner;

public class RRPSSApp {

    public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("welcome to rrpss pls input ur choice");
        System.out.println("(1) Manage Menu\n (2) Manage Order\n (3) Make reservation\n (4) Manage Table\n");
        int rrpss_main_choice;
        Scanner rrpss_scanner = new Scanner(System.in);
        do {
            rrpss_main_choice=rrpss_scanner.nextInt();
    
            switch(rrpss_main_choice){
            case 1:
            		int menu_choice;    
            		do {
                    	System.out.println("Choice:\n1.Create Menu Item\n2.Remove Menu Item\n3.Update Menu Item\n4.Print Main Menu\n5.Save Menu Data\n6.Load Menu Data");
                    	Scanner menu_scanner = new Scanner(System.in);
                    	menu_choice = menu_scanner.nextInt();
                    	switch(menu_choice) {
                    	case 1:
                    		MenuManager.createItemQuery();
                    		break;
                    	case 2:
                    		MenuManager.removeItemQuery();
                    		break;
                    	case 3: 
                    		MenuManager.updateItemQuery();
                    		break;
                    	case 4:
                    		MenuManager.printMainMenu();
                    		break;
			case 5:
                    		MenuManager.saveDB();
                    		break;
			case 6:
                    		MenuManager.loadDB();
                    		break;
                    	default:
                    		break;
                    	}
                    } while(menu_choice>=1 && menu_choice<=6);
		    		
		    		break;
		    case 2:
		    		int order_choice;
		    		do {
                    	System.out.println("Choice:\n1.Create Order\n2.Update Order\n3.View Orders\n4.Print All Orders\n");
                    	Scanner order_scanner = new Scanner(System.in);
                    	order_choice = order_scanner.nextInt();
                    	switch(order_choice) {
                    	case 1:
                    		OrderManager.createOrder());
                    		break;
                    	case 2:
                    		OrderManager.updateOrder();
                    		break;
                    	case 3: 
                    		OrderManager.viewOrder();
                    		break;
                    	case 4:
                    		OrderManager.printOrder();
                    		break;
                    	default:
                    		break;
                    	}
                    } while(order_choice>=1 && order_choice <=4);
		    		break;
		    case 3:
		    		//MenuManager.updateItemQuery();
		    		break;
		    case 4:
		    		int table_choice;
		    		do {
		    			System.out.println("Choice:\n1.Add Table\n2.Remove Table\n3.View Empty Tables\n4.View Occupied Tables\n");
		    			Scanner table_scanner = new Scanner(System.in);
                    	table_choice = table_scanner.nextInt();
                    	switch(table_choice) {
                    	case 1: 
                    		TableLayoutManager.createTableQuery();
                    		break;
                    	case 2: 
                    		TableLayoutManager.removeTableQuery();
                    		break;
                    	case 3: 
                    		TableLayoutManager.printEmptyTables();
                    		break;
                    	case 4:
                    		TableLayoutManager.printOccupiedTables();
                    		break;
                    	default:
                    		break;
                    	}
		    		} while(table_choice>=1 && table_choice <=4);
		    		break;
            }
        } while (rrpss_main_choice < 5);

	}

    


}
