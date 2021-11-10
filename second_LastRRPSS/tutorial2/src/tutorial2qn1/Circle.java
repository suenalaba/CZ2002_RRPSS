package tutorial2qn1;

public class Circle{
	private double radius; 
	private static final double PI = 3.14159;
	private double circumferenceOfCircle; 
	private double areaOfCircle;
	
	// default constructor
	//public Circle() {}
	//constructor
	public Circle(double rad) {
		setRadius(rad);
		System.out.println("A new circle is created");
	}
	//mutator method - set radius
	public void setRadius(double rad) {
		radius =rad; 
	}
	//accessor method - get radius 
	public double getRadius() {
		return radius;
	}
	//calculate area
	public double area() {
		areaOfCircle = PI * radius * radius; 
		return areaOfCircle; 
	}
	//calculate circumference
	public double circumference() {
		circumferenceOfCircle = 2 * PI * radius; 
		return circumferenceOfCircle; 
	}
	public void printArea() {
		System.out.println("Area of circle"); 
		System.out.println("Radius: " + radius); 
		System.out.println("Area: " + area()); 
	}
	public void printCircumference() {
		System.out.println("Circumference of circle"); 
		System.out.println("Radius: " + radius); 
		System.out.println("Circumference: " + circumference());
	}
}