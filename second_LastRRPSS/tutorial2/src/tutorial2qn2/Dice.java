package tutorial2qn2;
import java.util.Random;
public class Dice {
	Random rn = new Random();
	
	private int valueOfDice; 
	public Dice() {
		valueOfDice = 0;
	}
	public void setDiceValue(){
		int roll = rn.nextInt(7);
		System.out.println("Current value: " + roll); 
		valueOfDice += roll;
	}
	public int getDiceValue() {
		return valueOfDice;
	}
	public void printDiceValue(){
		System.out.println("Current value is " + valueOfDice);
	}
}
