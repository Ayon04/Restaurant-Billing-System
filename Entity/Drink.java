package Entity;

public class Drink extends Item {
	
	private static final double tax = 0.1;
	
	public Drink(String name, double price) {
		super(name, price);
	}
	
	public static double getTax() {
		return tax;
	}
	
}
