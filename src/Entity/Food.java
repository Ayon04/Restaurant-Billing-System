package Entity;

public class Food extends Item {

	private static final double tax =.15;
	
	public Food(String name, double price) {
		super(name, price);
	}
	
	public static double getTax() {
		return tax;
	}
	
}
