package restaurant_application;
import restaurant_manager.MenuManager;
import java.util.Scanner;

public class RRPSSApp {

    public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("welcome to rrpss pls input ur choice");
        System.out.println("(1) Manage Menu (2) Manage Order (3) Make reservation");
        int rrpss_menu_choice;
        Scanner sc = new Scanner(System.in);
        do {
            rrpss_menu_choice=sc.nextInt();
    
            switch(rrpss_menu_choice){
            case 1:
		    	System.out.println("Choice:\n1.Create Menu Item\n2.Remove Menu Item\n3.Update Menu Item\n4.Print Main Menu\n");
                    MenuManager.createItemQuery();
		    		break;
		    case 2:
		    	//menuManager.removeItemQuery();
		    		break;
		    case 3:
		    		//MenuManager.updateItemQuery();
		    		break;
		    case 4:
		    		//MenuManager.printMainMenu();
		    		break;
            }
        } while (rrpss_menu_choice < 5);

	}

    


}