package cpsc4620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * This file is where the front end magic happens.
 * 
 * You will have to write the methods for each of the menu options.
 * 
 * This file should not need to access your DB at all, it should make calls to the DBNinja that will do all the connections.
 * 
 * You can add and remove methods as you see necessary. But you MUST have all of the menu methods (including exit!)
 * 
 * Simply removing menu methods because you don't know how to implement it will result in a major error penalty (akin to your program crashing)
 * 
 * Speaking of crashing. Your program shouldn't do it. Use exceptions, or if statements, or whatever it is you need to do to keep your program from breaking.
 * 
 */

public class Menu {

	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws SQLException, IOException {

		System.out.println("Welcome to Pizzas-R-Us!");
		
		int menu_option = 0;

		// present a menu of options and take their selection
		
		PrintMenu();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String option = reader.readLine();
		menu_option = Integer.parseInt(option);

		while (menu_option != 9) {
			switch (menu_option) {
			case 1:// enter order
				EnterOrder();
				break;
			case 2:// view customers
				viewCustomers();
				break;
			case 3:// enter customer
				EnterCustomer();
				break;
			case 4:// view order
				// open/closed/date
				ViewOrders();
				break;
			case 5:// mark order as complete
				MarkOrderAsComplete();
				break;
			case 6:// view inventory levels
				ViewInventoryLevels();
				break;
			case 7:// add to inventory
				AddInventory();
				break;
			case 8:// view reports
				PrintReports();
				break;
			case 9:
				quit();
			}
			PrintMenu();
			option = reader.readLine();
			menu_option = Integer.parseInt(option);
		}

	}

	public static int quit() {
		return 0;
	}

	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException 
	{
		int OrderID;
		/*
		 * EnterOrder should do the following:
		 *
		 * Ask if the order is delivery, pickup, or dinein
		 */
		System.out.println("Is this order for: \n1.) Dine-in\n2.) Pick-up\n3.) Delivery\nEnter the number of your choice:");
		int userChoice = Integer.parseInt(reader.readLine());

		// Handle different order types
		switch (userChoice) {
			case 1: // Dine-in
				System.out.println("What is the table number for this order?");
				int TableNum = Integer.parseInt(reader.readLine());
				//orderType = "pickup";
				break;
			case 2: // Pick-up
				System.out.println("Is this order for an existing customer? Answer y/n: ");
				char userInput = reader.readLine().charAt(0);
				System.out.println("User choice: " + userInput); // Add this line
				if (userInput == 'y') {
					System.out.println("Here's a list of the current customers: ");
					viewCustomers();
					System.out.println("Which customer is this order for? Enter ID Number:");
					int CustID = Integer.parseInt(reader.readLine());
					System.out.println("Customer ID: " + CustID); // Add this line
					System.out.println("Customer name: " + DBNinja.getCustomerName(CustID)); // Add this line
					break;
				} else if (userInput == 'n') {
					EnterCustomer();
				}
				break;
			case 3: // Delivery
				int CustID;
				//orderType = OrderType;
				//char userInput;
				//System.out.println("Is this order for an existing customer? Answer y/n: ");
				while (true) {
					System.out.println("Is this order for an existing customer? Answer y/n: ");
					userInput = reader.readLine().charAt(0);
					if (userInput == 'y' || userInput == 'n') {
						break;
					} else {
						System.out.println("ERROR: I don't understand your input for: Is this order an existing customer?");
					}
				}
				if (userInput == 'y') {
					System.out.println("Here's a list of the current customers: ");
					viewCustomers();
					System.out.println("Which customer is this order for? Enter ID Number:");
					CustID = Integer.parseInt(reader.readLine());
					DBNinja.getCustomerName(CustID);
				} else if (userInput == 'n') {
					EnterCustomer();
					System.out.println("What is the House/Apt Number for this order? (e.g., 111)");
					String houseNum = reader.readLine();
					System.out.println("What is the Street for this order? (e.g., Smile Street)");
					String street = reader.readLine();
					System.out.println("What is the City for this order? (e.g., Greenville)");
					String city = reader.readLine();
					System.out.println("What is the State for this order? (e.g., SC)");
					String state = reader.readLine();
					System.out.println("What is the Zip Code for this order? (e.g., 29601)");
					String zip = reader.readLine();
					street = houseNum + " " + street;

					//setAddress(street, city, state, zip);
				}
		}
		System.out.println("Let's build a pizza!");
		OrderID = DBNinja.getNextOrderID();
		buildPizza(OrderID);

		/*
		 *   if dine in....ask for table number
		 *   if pickup...
		 *   if delivery...
		 *
		 *
		 /
		 * Then, build the pizza(s) for the order (there's a method for this)
		 *  until there are no more pizzas for the order
		 *  add the pizzas to the order
		 *
		 * Apply order discounts as needed (including to the DB)
		 * 
		 * return to menu
		 * 
		 * make sure you use the prompts below in the correct order!
		 */

		 // User Input Prompts...
		//System.out.println("Is this order for: \n1.) Dine-in\n2.) Pick-up\n3.) Delivery\nEnter the number of your choice:");
		//System.out.println("Is this order for an existing customer? Answer y/n: ");
		//System.out.println("Here's a list of the current customers: ");
		//System.out.println("Which customer is this order for? Enter ID Number:");
		//System.out.println("ERROR: I don't understand your input for: Is this order an existing customer?");
		//System.out.println("What is the table number for this order?");
		//System.out.println("Let's build a pizza!");
		System.out.println("Enter -1 to stop adding pizzas...Enter anything else to continue adding pizzas to the order.");
		System.out.println("Do you want to add discounts to this order? Enter y/n?");
		System.out.println("Which Order Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
		System.out.println("What is the House/Apt Number for this order? (e.g., 111)");
		System.out.println("What is the Street for this order? (e.g., Smile Street)");
		System.out.println("What is the City for this order? (e.g., Greenville)");
		System.out.println("What is the State for this order? (e.g., SC)");
		System.out.println("What is the Zip Code for this order? (e.g., 20605)");
		
		
		System.out.println("Finished adding order...Returning to menu...");
	}
	
	
	public static void viewCustomers() throws SQLException, IOException 
	{
		/*
		 * Simply print out all of the customers from the database. 
		 */
		ArrayList<Customer> customers = DBNinja.getCustomerList();

		if (customers.isEmpty()) {
			System.out.println("No customers found.");
		} else {
			//System.out.println("List of Customers:");
			for (Customer customer : customers) {
				System.out.println(customer.toString());
			}
		}
	}
	

	// Enter a new customer in the database
	public static void EnterCustomer() throws SQLException, IOException 
	{
		/*
		 * Ask for the name of the customer:
		 *   First Name <space> Last Name
		 * 
		 * Ask for the  phone number.
		 *   (##########) (No dash/space)
		 * 
		 * Once you get the name and phone number, add it to the DB
		 */
		
		// User Input Prompts...
		System.out.println("Please Enter the Customer name (First Name <space> Last Name)");
		String[] customerFullName = reader.readLine().split(" ");
		String fName = customerFullName[0];
		String lName = customerFullName.length > 1 ? customerFullName[1] : "";

		System.out.println("What is this customer's phone number (##########) (No dash/space)");
		String phone = reader.readLine().trim();

		// Create a new customer object
		Customer newCustomer = new Customer(0, fName, lName, phone);

		// Add the new customer to the database
		DBNinja.addCustomer(newCustomer);
	}

	// View any orders that are not marked as completed
	public static void ViewOrders() throws SQLException, IOException 
	{
		/*  
		* This method allows the user to select between three different views of the Order history:
		* The program must display:
		* a.	all open orders
		* b.	all completed orders 
		* c.	all the orders (open and completed) since a specific date (inclusive)
		* 
		* After displaying the list of orders (in a condensed format) must allow the user to select a specific order for viewing its details.  
		* The details include the full order type information, the pizza information (including pizza discounts), and the order discounts.
		* 
		*/ 
			
		
		// User Input Prompts...
		System.out.println("Would you like to:\n(a) display all orders [open or closed]\n(b) display all open orders\n(c) display all completed [closed] orders\n(d) display orders since a specific date");
		System.out.println("What is the date you want to restrict by? (FORMAT= YYYY-MM-DD)");
		System.out.println("I don't understand that input, returning to menu");
		System.out.println("Which order would you like to see in detail? Enter the number (-1 to exit): ");
		System.out.println("Incorrect entry, returning to menu.");
		System.out.println("No orders to display, returning to menu.");



	}

	
	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException 
	{
		/*
		 * All orders that are created through java (part 3, not the orders from part 2) should start as incomplete
		 * 
		 * When this method is called, you should print all of the "opoen" orders marked
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 * 
		 */
		// Fetch all open orders
		ArrayList<Order> openOrders = DBNinja.getOrders(true);

		// Check if there are any open orders
		if (openOrders.isEmpty()) {
			System.out.println("There are no open orders currently... returning to menu...");
			return;
		}

		// Print open orders for the user to choose from
		System.out.println("Open Orders:");



		for (Order order : openOrders) {
			String name = DBNinja.getCustomerName(order.getCustID());
			System.out.println("OrderID: " + order.getOrderID() + ", Customer: " + name + ", Date: " + order.getDate());
		}

		// User Input Prompts...
		System.out.println("Which order would you like to mark as complete? Enter the OrderID: ");
	}

	public static void ViewInventoryLevels() throws SQLException, IOException
	{
		/*
		 * Print the inventory. Display the topping ID, name, and current inventory
		 */
		ArrayList<Topping> toppings = DBNinja.getToppingList();

		if (toppings.isEmpty()) {
			System.out.println("No toppings found.");
		} else {
			System.out.println("ID\tName\t\tCurINVT");
			for (Topping topping : toppings) {
				System.out.println(topping.getTopID() + "\t" + topping.getTopName() + "\t\t" + topping.getCurINVT());
			}
		}
	}


	public static void AddInventory() throws SQLException, IOException 
	{
		/*
		 * This should print the current inventory and then ask the user which topping (by ID) they want to add more to and how much to add
		 */
		//viewInventoryLevels();
		
		// User Input Prompts...
		System.out.println("Which topping do you want to add inventory to? Enter the number: ");
		System.out.println("How many units would you like to add? ");
		System.out.println("Incorrect entry, not an option");
	
		
		
		
	}

	// A method that builds a pizza. Used in our add new order method
	public static Pizza buildPizza(int orderID) throws SQLException, IOException {

		/*
		 * This is a helper method for first menu option.
		 *
		 * It should ask which size pizza the user wants and the crustType.
		 *
		 * Once the pizza is created, it should be added to the DB.
		 *
		 * We also need to add toppings to the pizza. (Which means we not only need to add toppings here, but also our bridge table)
		 *
		 * We then need to add pizza discounts (again, to here and to the database)
		 *
		 * Once the discounts are added, we can return the pizza
		 */

		Pizza ret = null;
		Pizza newPizza = new Pizza(0, "", "", orderID, "", "", 0.0, 0.0);

		// User Input Prompts...
		System.out.println("What size is the pizza?");
		System.out.println("1." + DBNinja.size_s);
		System.out.println("2." + DBNinja.size_m);
		System.out.println("3." + DBNinja.size_l);
		System.out.println("4." + DBNinja.size_xl);
		System.out.println("Enter the corresponding number: ");
		String userInput = reader.readLine();
		char userChoice = userInput.charAt(0);

		switch (userChoice) {
			case '1':
				newPizza.setSize(DBNinja.size_s);
				System.out.println(newPizza.getSize());
				break;
			case '2':
				newPizza.setSize(DBNinja.size_m);
				System.out.println(newPizza.getSize());
				break;
			case '3':
				newPizza.setSize(DBNinja.size_l);
				System.out.println(newPizza.getSize());
				break;
			case '4':
				newPizza.setSize(DBNinja.size_xl);
				System.out.println(newPizza.getSize());
				break;
		}

		System.out.println("What crust for this pizza?");
		System.out.println("1." + DBNinja.crust_thin);
		System.out.println("2." + DBNinja.crust_orig);
		System.out.println("3." + DBNinja.crust_pan);
		System.out.println("4." + DBNinja.crust_gf);
		System.out.println("Enter the corresponding number: ");
		userInput = reader.readLine();
		userChoice = userInput.charAt(0);
		switch (userChoice) {
			case '1':
				newPizza.setCrustType(DBNinja.crust_thin);
				System.out.println(newPizza.getCrustType());
				break;
			case '2':
				newPizza.setCrustType(DBNinja.crust_orig);
				System.out.println(newPizza.getCrustType());
				break;
			case '3':
				newPizza.setCrustType(DBNinja.crust_pan);
				System.out.println(newPizza.getCrustType());
				break;
			case '4':
				newPizza.setCrustType(DBNinja.crust_gf);
				System.out.println(newPizza.getCrustType());
				break;
		}

		System.out.println("Available Toppings:");
		ViewInventoryLevels();
		//System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings: ");
		int toppingChoice;
		boolean isDoubled = false;
		while (true) {
			System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings: ");
			try {
				userInput = reader.readLine();
				toppingChoice = Integer.parseInt(userInput);
				Topping newPizzaTopping = DBNinja.findToppingByID(toppingChoice);

				// Check if the user wants to stop adding toppings
				if (toppingChoice == -1) {
					break;
				}
				System.out.println("Do you want to add extra topping? Enter y/n");
				userInput = reader.readLine();
				userChoice = userInput.charAt(0);
				if (userChoice == 'y') {
					if (newPizzaTopping.getCurINVT() != newPizzaTopping.getMinINVT() && newPizzaTopping.getCurINVT() > 0) {
						isDoubled = true;
					} else {
						System.out.println("We don't have enough of that topping to add it...");
					}
					DBNinja.useTopping(newPizza, newPizzaTopping, isDoubled);
				}
			} catch (NumberFormatException e) {
				System.out.println("Incorrect entry, returning to menu.");
			}

			//System.out.println("Do you want to add extra topping? Enter y/n");
			//System.out.println("We don't have enough of that topping to add it...");
			//System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings: ");


			System.out.println("Do you want to add discounts to this Pizza? Enter y/n?");
			userInput = reader.readLine();
			userChoice = userInput.charAt(0);
			if (userChoice == 'y') {
				DBNinja.getDiscountList();
				System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
				userInput = reader.readLine();
				userChoice = userInput.charAt(0);
				while (userChoice != -1) {
					System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
					userInput = reader.readLine();
					userChoice = userInput.charAt(0);
				}
			}
			if (userChoice == 'n') {
					break;
			}
			System.out.println("Do you want to add more discounts to this Pizza? Enter y/n?");

			if (userChoice == 'y') {
				DBNinja.getDiscountList();
				System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
				userInput = reader.readLine();
				userChoice = userInput.charAt(0);
				while (userChoice != -1) {
					userInput = reader.readLine();
					userChoice = userInput.charAt(0);
					System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
					switch (userChoice) {
						case '1': {
							String discountName = "Employee";
							DBNinja.findDiscountByName(discountName);
						}
						case '2': {
							String discountName = "Lunch Special Medium";
							DBNinja.findDiscountByName(discountName);
						}
						case '3': {
							String discountName = "Employee";
							DBNinja.findDiscountByName(discountName);
						}
						case '4': {
							String discountName = "Lunch Special Medium";
							DBNinja.findDiscountByName(discountName);
						}
						case '5': {
							String discountName = "Happy Hour";
							DBNinja.findDiscountByName(discountName);
						}
						case '6': {
							String discountName = "Gameday Special";
							DBNinja.findDiscountByName(discountName);
						}
					}
				}
			}
			if (userChoice == 'n') {
				break;
			}
		}
		DBNinja.addPizza(newPizza);
		return ret;
	}

	
	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		/*
		 * This method asks the use which report they want to see and calls the DBNinja method to print the appropriate report.
		 * 
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// User Input Prompts...
		System.out.println("Which report do you wish to print? Enter\n(a) ToppingPopularity\n(b) ProfitByPizza\n(c) ProfitByOrderType:");
		System.out.print("Enter your choice: ");
		String userInput = reader.readLine();

		switch (userInput.toLowerCase()) {
			case "a":
				DBNinja.printToppingPopReport();
				break;
			case "b":
				DBNinja.printProfitByPizzaReport();
				break;
			case "c":
				DBNinja.printProfitByOrderType();
				break;
			default:
				System.out.println("I don't understand that input... returning to menu...");
				break;
		}
	}

	//Prompt - NO CODE SHOULD TAKE PLACE BELOW THIS LINE
	// DO NOT EDIT ANYTHING BELOW HERE, THIS IS NEEDED TESTING.
	// IF YOU EDIT SOMETHING BELOW, IT BREAKS THE AUTOGRADER WHICH MEANS YOUR GRADE WILL BE A 0 (zero)!!

	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}

	/*
	 * autograder controls....do not modiify!
	 */

	public final static String autograder_seed = "6f1b7ea9aac470402d48f7916ea6a010";

	
	private static void autograder_compilation_check() {

		try {
			Order o = null;
			Pizza p = null;
			Topping t = null;
			Discount d = null;
			Customer c = null;
			ArrayList<Order> alo = null;
			ArrayList<Discount> ald = null;
			ArrayList<Customer> alc = null;
			ArrayList<Topping> alt = null;
			double v = 0.0;
			String s = "";

			DBNinja.addOrder(o);
			DBNinja.addPizza(p);
			DBNinja.useTopping(p, t, false);
			DBNinja.usePizzaDiscount(p, d);
			DBNinja.useOrderDiscount(o, d);
			DBNinja.addCustomer(c);
			DBNinja.completeOrder(o);
			alo = DBNinja.getOrders(false);
			o = DBNinja.getLastOrder();
			alo = DBNinja.getOrdersByDate("01/01/1999");
			ald = DBNinja.getDiscountList();
			d = DBNinja.findDiscountByName("Discount");
			alc = DBNinja.getCustomerList();
			c = DBNinja.findCustomerByPhone("0000000000");
			alt = DBNinja.getToppingList();
			t = DBNinja.findToppingByName("Topping");
			DBNinja.addToInventory(t, 1000.0);
			v = DBNinja.getBaseCustPrice("size", "crust");
			v = DBNinja.getBaseBusPrice("size", "crust");
			DBNinja.printInventory();
			DBNinja.printToppingPopReport();
			DBNinja.printProfitByPizzaReport();
			DBNinja.printProfitByOrderType();
			s = DBNinja.getCustomerName(0);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}


}


