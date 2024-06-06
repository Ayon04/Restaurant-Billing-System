import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.Drink;
import Entity.Food;
import Entity.Item;
import Models.Restaurant;

public class Main {

	static Scanner scan = new Scanner(System.in);
	
	static {
		Restaurant.createFiles();
		try {
			Restaurant.loadProducts();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws IOException {

		String username, password;
		
		while(true) {
			System.out.println("\t\t\tLogin to the system");
			System.out.print("\t\tEnter Username: ");
			username = scan.nextLine();
			
			System.out.print("\t\tEnter Password: ");
			password = scan.nextLine();
			
			if(Restaurant.verifyLogin(username, password)) {
				break;
			}
			else {
				System.out.println("\n\t\tCredentials did not match!\nTry Again.");
				continue;
			}
		}
		
		System.out.println("Intializing Database...");
		//TODO: Set restaurant name
		System.out.println("\t\t\t________________________________");
		System.out.println("\t\t\t| Welcome to {name} Restaurant |");
		System.out.println("\t\t\t‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
		System.out.println();
		
		int choice;
		
		while(true) {
			System.out.println("\n1. Add product");
			System.out.println("2. Remove product");
			System.out.println("3. Search product");
			System.out.println("4. Order");
			System.out.println("5. Exit");
			
			try {
				System.out.print("Enter choice: ");
				choice = scan.nextInt();
				scan.nextLine();
			}
			catch(Exception ex){
				System.out.println("\nInvalid Input.\nTryAgain.");
				continue;
			}
			
			switch(choice){
			
			case 1:
				if(!Restaurant.addProduct()) {
					System.out.println("\nSystem failed to add the product!");
				}
				break;
				
			case 2:
				if(!Restaurant.removeProduct()) {
					System.out.println("\nSystem failed to remove the product!");
				}
				break;
				
			case 3:
				System.out.print("\nEnter product name: ");
				String name = scan.nextLine();
				if(!Restaurant.searchProduct(name)) {
					System.out.println("No such product found!");
				}
				break;
				
			case 4:
				Restaurant.orderProduct();
				break;
				
			case 5:
				System.exit(0);
				
			default:
				System.exit(0);
			}
			
		}
		
	}
}
