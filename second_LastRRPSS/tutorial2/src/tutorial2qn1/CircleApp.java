package tutorial2qn1;
import java.util.Scanner;
public class CircleApp {
	public static void main(String[] args) {
		System.out.println(
				"==== Circle Computation ====\n"+ 
				"|1. Create a new circle    |\n"+
				"|2. Print Area             |\n"+
				"|3. Print Circumference    |\n"+
				"|4. Quit                   |");

		
		System.out.println("Choose option (1-4):");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		Circle circle1 = new Circle(); 
		while(choice!=4) {
			switch (choice) {
			case 1:
				System.out.println("Enter the radius to compute the area and circumference");
				double radius = sc.nextDouble();
				circle1.setRadius(radius);
				System.out.println("Choose option (1-4):");
				choice = sc.nextInt();
				break;
				
			case 2:
				if (circle1 != null)
				circle1.printArea();
				System.out.println("Choose option (1-4):");
				choice = sc.nextInt(); 
				break;
			
			case 3:
				circle1.printCircumference();
				System.out.println("Choose option (1-4):");
				choice = sc.nextInt();
				break;
			
			case 4:
				break;
			}
		}
		System.out.println("Thank you!!"); 
		sc.close();
	}
}
				