public class Circle{
	private double radius; 
	private static final double PI = 3.14159;
	private double circumferenceOfCircle; 
	private double areaOfCircle;
	
	//constructor
	public Circle(double rad) {
		radius = rad; 
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
		System.out.println("The area is" + areaOfCircle); 
	}
	public void printCircumference() {
		System.out.println("The circumference is" + circumferenceOfCircle);
	}
}