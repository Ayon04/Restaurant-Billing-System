package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.Drink;
import Entity.Food;
import Entity.Item;

public abstract class Restaurant {
	
	static final File fItem = new File("Items.txt");
	static final File fLogin = new File("LoginCredentials.txt");
	static final File fBill = new File("Bills.txt");
	static Scanner scan = new Scanner(System.in);
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public static void createFiles() {

		if(!fItem.exists()) {
			try {
				fItem.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!fLogin.exists()) {
			try {
				fLogin.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!fBill.exists()) {
			try {
				fBill.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void loadProducts() throws IOException {
		
		FileInputStream fis = new FileInputStream(fItem);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		if(fItem.length() > 0) {
			String line = br.readLine();
			String[] line_info;
			
			while(line != null) {
				line_info = line.split(":");
				String type = line_info[0];
				if(type.equals(Food.class.getSimpleName())){
					Item item = new Food(line_info[1], Double.parseDouble(line_info[2]));
					items.add(item);
				}
				else if(type.equals(Drink.class.getSimpleName())) {
					Item item = new Drink(line_info[1], Double.parseDouble(line_info[2]));
					items.add(item);
				}
				else {
					return;
				}

				line = br.readLine();
			}
			
			
		}
	}
	
	public static boolean verifyLogin(String uname, String upass) {
		
		try {
			if(fLogin.length() > 0 && fLogin.exists()) {
				FileInputStream fis = new FileInputStream(fLogin);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				
				String line = br.readLine();	//line format - | 12345:12345 | (Username:Password)
				while(line != null) {
					String name = line.split(":")[0];
					String pass = line.split(":")[1];
					
					if(name.equals(uname) && pass.equals(upass)) {
						return true;
					}
					else {
						line = br.readLine();
						continue;
					}
					
				}
				
			}
			else {
				return false;
			}
		}
		catch(Exception ex) {
			return false;
		}
		return false;
		
	}
	
	public static boolean addProduct() {
		String name;
		double price;
		int type;
		
		try {
			System.out.print("\nProduct Type-");
			System.out.print(" 1.Food, 2. Drink\n");
			System.out.print("Enter product type: ");
			type = scan.nextInt();
			scan.nextLine();
			System.out.print("Enter product name: ");
			name = scan.nextLine();
			System.out.print("Enter product price: ");
			price = scan.nextDouble();
			scan.nextLine();
		}
		catch(Exception ex) {
			System.out.println("\nInvalid product parameters!");
			scan.nextLine();
			return false;
		}
		
		Item item = null;
		try {
			if(type == 1) {
				item = new Food(name, price);
				items.add(item);
			}
			else if(type == 2) {
				item = new Drink(name, price);
				items.add(item);
			}
			else {
				System.out.println("\nInvalid product type!");
				return false;
			}
			
			writeItems();
			System.out.println("Product added successfully!");
		}
		catch(Exception ex) {
			items.remove(item);
			return false;
		}

		return true;
	}
	
	public static boolean removeProduct() throws IOException {
		
		int count = 1;
		System.out.println("\nList of available products-");
		for(Item item: Restaurant.items) {
			System.out.println(count++ + "Type: " + item.getClass().getSimpleName() + ", Name: " + item.getName());
		}
		
		try {
			System.out.println("\n\nEnter choice to remove product: ");
			int choice = scan.nextInt();
			
			items.remove(choice-1);
			writeItems();
		}
		catch(InputMismatchException ex) {
			scan.nextLine();
			System.out.println("\nInvalid input!");
			return false;
		}
		catch(Exception ex) {
			System.out.println("\nInput out of range!");
			return false;
		}
		return true;
	}

	public static boolean searchProduct(String name) {
		boolean found = false;
		for(Item item:items) {
			if(item.getName().equals(name)) {
				System.out.println("\nType: " + item.getClass().getSimpleName() + ", Name: " + item.getName() + ", Price: " + item.getPrice());
				found = true;
			}
		}
		
		if(!found) {
			return false;
		}
		return true;
	}
	
	public static void orderProduct() throws IOException {
		
		ArrayList<Item> cart = new ArrayList<Item>();
		boolean isDone = false;
		String choice;
		int c;
		
		if(items.size() > 0) {
			listItems();
			System.out.println("Enter 0 to cancel order, X to proceed if item adding is done.");
			while(!isDone) {
				try {
					System.out.print("Enter choice for ordering: ");
					choice = scan.nextLine();
					
					String l = String.valueOf(choice.charAt(0));

					if(l.equals("X") || l.equals("x")) {
						isDone = true;
						break;
					}
					else if(l.equals("0")) {
						System.out.println("Order canceled! Returning to main menu...");
					}
					else{
						
						c = Integer.parseInt(l);
						
						if(!cart.contains(items.get(c-1)));
							cart.add(items.get(c-1));
							System.out.println("Product added to cart.\n");
					}
					
				}
				catch(Exception ex) {
					cart = null;
					System.out.println("Order canceled due to invalid input! Returning to main menu...");
					return;
				}
				
			}
			
			String name, number;
			int[] quantity = new int[cart.size()];
			int q;
			
			System.out.println("Cart Items-");
			for(Item item:cart) {
				System.out.println((cart.indexOf(item)+1) + ". " + item.getName());
			}
			
			try {
				for(int i=0; i<cart.size(); i++) {
					System.out.print("Enter quantity of " + cart.get(i).getName() + ": ");
					q = scan.nextInt();
					scan.nextLine();
					
					quantity[i] = q;
				}
			}
			catch(Exception ex) {
				cart = null;
				System.out.println("Order canceled due to invalid input! Returning to main menu...");
				return;
			}
			
			System.out.print("\nEnter customer name: ");
			name = scan.nextLine();
			System.out.print("Enter customer number: ");
			number = scan.nextLine();
			
			String bill = makeBill(name, number, cart, quantity);
			System.out.println(bill);
			writeBill(bill);
			cart = null;
		}
		else {
			System.out.println("No product available currently!");
			return;
		}
		
	}
	
	private static String makeBill(String cname, String cnumber, ArrayList<Item> cart, int[] quantity) {

		String bill = " _______________________________________\n" + 
					  "|                  Bill                 |\n" +
					  " ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾" +
					  "\n\tCustomer name: " + cname + 
					  "\n\tCustomer number: " + cnumber +
					  "\n\tOrdered Items-";
		int i=0;
		double totalBill = 0;
		double totalBillFoods = 0;
		double totalBillDrinks = 0;
		double FoodTax = Food.getTax(), DrinkTax = Drink.getTax(); 
		
		for(Item item:cart) {
			bill += ("\n\t" + item.getName() + ", Price: " + item.getPrice() + ", Quantity: " + quantity[i]);
			if(item instanceof Food) {
				totalBillFoods += (item.getPrice() * quantity[i]);
			}
			if(item instanceof Drink) {
				totalBillDrinks += (item.getPrice() * quantity[i]);
			}
		}
		
		totalBill = (totalBillFoods + (totalBillFoods*FoodTax)) + (totalBillDrinks + (totalBillDrinks*DrinkTax));
		
		bill += "\n\tBill-" + 
				"\n\tFood Tax: " + FoodTax*100 + "%" +
				"\n\tDrink Tax: " + DrinkTax*100 + "%" +
				"\n\tTotal Bill: " + totalBill + "\n";
		
		return bill;
	}
	
	private static void listItems() {
		
		System.out.println("List of available products-");
		int count = 0;
		for(Item item:items) {
			System.out.println(++count + ". " + item.getName() + ", Price: " + item.getPrice());
		}
		
	}
	
	private static void writeBill(String bill) throws IOException {
		FileWriter fw = new FileWriter(fBill);
		BufferedWriter br = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(br);
		
		pw.println(bill);
		
		pw.close();
	}
	
	private static void writeItems() throws IOException {
		FileWriter fw = new FileWriter(fItem);
		BufferedWriter br = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(br);
		
		pw.write("");
		
		for(Item item:items) {
			pw.println(item.getClass().getSimpleName() + ":" + item.getName() + ":" + item.getPrice());
		}
		
		pw.close();
	}

}
