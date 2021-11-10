package tutorial2qn2;
import java.util.Scanner;
public class DiceApp {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Press <key> to roll the first dice"); 
		sc.nextLine(); 
		Dice dice1 = new Dice(); 
		dice1.setDiceValue();
		
		System.out.println("Press <key> to roll the second dice"); 
		sc.nextLine();
		
		dice1.setDiceValue();
		
		System.out.println("Your total number is: " + dice1.getDiceValue());
		sc.close();
	}

}
