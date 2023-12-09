package cpsc4620;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 * 
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 * 
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 * 
 * 
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery, and sizes and crusts
	public final static String pickup = "pickup";
	public final static String delivery = "delivery";
	public final static String dine_in = "dinein";

	public final static String size_s = "Small";
	public final static String size_m = "Medium";
	public final static String size_l = "Large";
	public final static String size_xl = "XLarge";

	public final static String crust_thin = "Thin";
	public final static String crust_orig = "Original";
	public final static String crust_pan = "Pan";
	public final static String crust_gf = "Gluten-Free";


	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			return false;
		}

	}


	public static void addOrder(Order o) throws SQLException, IOException {
		connect_to_db();


		/*
		 * add code to add the order to the DB. Remember that we're not just
		 * adding the order to the order DB table, but we're also recording
		 * the necessary data for the delivery, dinein, and pickup tables
		 *
		 */
		String insertOrderSQL = "INSERT INTO `Order` (OrderID, CustID, OrderType, Date, CustPrice, BusPrice, isComplete) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = conn.prepareStatement(insertOrderSQL)) {
			preparedStatement.setInt(1, o.getOrderID());
			preparedStatement.setInt(2, o.getCustID());
			preparedStatement.setString(3, o.getOrderType());
			preparedStatement.setString(4, o.getDate());
			preparedStatement.setDouble(5, o.getCustPrice());
			preparedStatement.setDouble(6, o.getBusPrice());
			preparedStatement.setInt(7, o.getIsComplete());
			preparedStatement.executeUpdate();
		}
		// Handle specific data for delivery, dinein, and pickup tables
		if (o instanceof DeliveryOrder) {
			// Code to insert into DeliveryOrder table
			//is a delivery order -> need to cast
			DeliveryOrder c = (DeliveryOrder) o;
			//now can call DeliveryOrder method on o
			DeliveryOrder deliveryOrder = (DeliveryOrder) o;
			String deliveryAddress = deliveryOrder.getAddress();
			System.out.println(deliveryAddress);
		} else if (o instanceof DineinOrder) {
			// Code to insert into DineinOrder table
			DineinOrder c = (DineinOrder) o;
			DineinOrder DineinOrder = (DineinOrder) o;
			int tableNum = DineinOrder.getTableNum();
			System.out.println(tableNum);
		} else if (o instanceof PickupOrder) {
			// Code to insert into PickupOrder table
			PickupOrder c = (PickupOrder) o;
			PickupOrder PickupOrder = (PickupOrder) o;
			int isPickedUp = PickupOrder.getIsPickedUp();
			System.out.println(isPickedUp);
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void addPizza(Pizza p) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Add the code needed to insert the pizza into the database.
		 * Keep in mind adding pizza discounts and toppings associated with the pizza,
		 * there are other methods below that may help with that process.
		 *
		 */
		try {
			String insertPizzaQuery = "INSERT INTO Pizza (Size, CrustType, OrderID, PizzaState, PizzaDate, CustPrice, BusPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insertPizzaStmt = conn.prepareStatement(insertPizzaQuery, Statement.RETURN_GENERATED_KEYS);

			insertPizzaStmt.setString(1, p.getSize());
			insertPizzaStmt.setString(2, p.getCrustType());
			insertPizzaStmt.setInt(3, p.getOrderID());
			insertPizzaStmt.setString(4, p.getPizzaState());
			insertPizzaStmt.setString(5, p.getPizzaDate());
			insertPizzaStmt.setDouble(6, p.getCustPrice());
			insertPizzaStmt.setDouble(7, p.getBusPrice());

			int affectedRows = insertPizzaStmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Inserting pizza failed, no rows affected.");
			}


			// Insert pizza details into Pizza table
			for (Topping topping : p.getToppings()) {
				String addToppingQuery = "INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, IsDoubled) VALUES (?, ?, ?)";
				PreparedStatement addToppingStmt = conn.prepareStatement(addToppingQuery);

				addToppingStmt.setInt(1, p.getPizzaID());
				addToppingStmt.setInt(2, topping.getTopID());
				addToppingStmt.setBoolean(3, p.getIsDoubleArray()[topping.getTopID() - 1]);

				addToppingStmt.executeUpdate();
			}
			for (Discount discount : p.getDiscounts()) {
				String addDiscountQuery = "INSERT INTO PIZZA_DISCOUNT (PizzaID, DiscountID) VALUES (?, ?)";
				PreparedStatement addDiscountStmt = conn.prepareStatement(addDiscountQuery);

				addDiscountStmt.setInt(1, p.getPizzaID());
				addDiscountStmt.setInt(2, discount.getDiscountID());

				addDiscountStmt.executeUpdate();
			}
		}
		catch (SQLException e) {
			e.printStackTrace(); // Handle or log the exception as needed
		} finally {
			// DO NOT FORGET TO CLOSE YOUR CONNECTION
			conn.close();
		}
	}



	public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException //this method will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
	{
		connect_to_db();
		/*
		 * This method should do 2 two things.
		 * - update the topping inventory every time we use t topping (accounting for extra toppings as well)
		 * - connect the topping to the pizza
		 *   What that means will be specific to your implementatinon.
		 *
		 * Ideally, you should't let toppings go negative....but this should be dealt with BEFORE calling this method.
		 *
		 */
		try {
			double usedQuantity = isDoubled ? t.getMedAMT() * 2 : t.getMedAMT();
			double newQuantity = t.getCurINVT() - usedQuantity;

			// Ensure the inventory doesn't go negative
			newQuantity = Math.max(newQuantity, 0);

			// Update the inventory in the Topping table
			String updateQuery = "UPDATE Topping SET CurINVT = ? WHERE TopID = ?";
			PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
			updateStmt.setDouble(1, newQuantity);
			updateStmt.setInt(2, t.getTopID());
			updateStmt.executeUpdate();

			String insertQuery = "INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, IsDoubled) VALUES (?, ?, ?)";
			PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
			insertStmt.setInt(1, p.getPizzaID());
			insertStmt.setInt(2, t.getTopID());
			insertStmt.setBoolean(3, isDoubled);
			insertStmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		// DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
}

	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This method connects a discount with a Pizza in the database.
		 *
		 * What that means will be specific to your implementatinon.
		 */


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This method connects a discount with an order in the database
		 *
		 * You might use this, you might not depending on where / how to want to update
		 * this information in the database
		 */


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	public static void addCustomer(Customer c) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This method adds a new customer to the database.
		 *
		 */

		String query = "INSERT INTO Customer (CustID, FName, LName, Phone) VALUES (?, ?, ?, ?);";

		PreparedStatement os;
		os = conn.prepareStatement(query);
		try (os) {
			os.setInt(1, c.getCustID());
			os.setString(2, c.getFName());
			os.setString(3, c.getLName());
			os.setString(4, c.getPhone());
			os.executeUpdate();
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void completeOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Find the specifed order in the database and mark that order as complete in the database.
		 *
		 */


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}


	public static ArrayList<Order> getOrders(boolean openOnly) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Return an arraylist of all of the orders.
		 * 	openOnly == true => only return a list of open (ie orders that have not been marked as completed)
		 *           == false => return a list of all the orders in the database
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 *
		 * Don't forget to order the data coming from the database appropriately.
		 *
		 */
			ArrayList<Order> orders = new ArrayList<>();
			try {
				connect_to_db();
				String query;

				if (openOnly) {
					query = "SELECT * FROM ORDER WHERE isComplete = 0 ORDER BY OrderID;";
				} else {
					query = "SELECT * FROM ORDER ORDER BY OrderID;";
				}

				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rset = stmt.executeQuery();

				while (rset.next()) {
					int orderID = rset.getInt("OrderID");
					int custID = rset.getInt("CustID");
					String orderType = rset.getString("OrderType");
					String orderDate = rset.getString("OrderDate");
					double custPrice = rset.getDouble("CustPrice");
					double busPrice = rset.getDouble("BusPrice");
					int isComplete = rset.getInt("isComplete");

					Order order = new Order(orderID, custID, orderType, orderDate, custPrice, busPrice, isComplete);
					orders.add(order);
				}

			} finally {
				try {
					conn.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		return orders;
	}

	public static Order getLastOrder() {
		/*
		 * Query the database for the LAST order added
		 * then return an Order object for that order.
		 * NOTE...there should ALWAYS be a "last order"!
		 */
		try {
			ArrayList<Order> orders = getOrders(false); // Get all orders
			if (orders != null && !orders.isEmpty()) {
				// Return the last order in the list (assuming the list is ordered by OrderID)
				return orders.get(orders.size() - 1);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Order> getOrdersByDate(String date) {
		/*
		 * Query the database for ALL the orders placed on a specific date
		 * and return a list of those orders.
		 *
		 */


		return null;
	}

	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database for all the available discounts and
		 * return them in an arrayList of discounts.
		 *
		 */
		ArrayList<Discount> discounts = new ArrayList<>();
		String query = "SELECT * FROM Discount;";

		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rset = stmt.executeQuery();

		while (rset.next()) {
			int discountID = rset.getInt("DiscountID");
			String discountName = rset.getString("DiscountName");
			double amount = rset.getDouble("Amount");
			boolean isPercent = rset.getBoolean("isPercent");

			Discount discount = new Discount(discountID, discountName, amount, isPercent);
			discounts.add(discount);
		}

		// DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		if (discounts.size() != 0) {
			return discounts;
		}
		else{
			return null;
		}
	}

	public static Discount findDiscountByName(String name) {
		/*
		 * Query the database for a discount using it's name.
		 * If found, then return an OrderDiscount object for the discount.
		 * If it's not found....then return null
		 *
		 */
		try {

		ArrayList<Discount> discountList = getDiscountList();

		// Check if the list is not null and not empty
		if (discountList != null && !discountList.isEmpty()) {
			for (Discount discount : discountList) {
				if (discount.getDiscountName().equalsIgnoreCase(name)) {
					return discount;
				}
			}
		}
	} catch (SQLException | IOException e) {
		e.printStackTrace(); // Handle or log the exception as needed
	}
		return null;
	}


	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
		connect_to_db();

		/*
		 * Query the data for all the customers and return an arrayList of all the customers.
		 * Don't forget to order the data coming from the database appropriately.
		 *
		 */
		ArrayList<Customer> customers = new ArrayList<>();
		String query = "SELECT * FROM Customer ORDER BY CustID;";

		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rset = stmt.executeQuery(query);

		// Check if there are no customers
		if (!rset.next()) {
			System.out.println("No customers found in the table.");
			conn.close();
			return customers;
		}

		do {
			int custID = rset.getInt("CustID");
			String fName = rset.getString("FName");
			String lName = rset.getString("LName");
			String phone = rset.getString("Phone");

			Customer customer = new Customer(custID, fName, lName, phone);

			customers.add(customer);
		} while (rset.next());
		//DO NOT FORGET TO CLOSE YOUR CONNECTION

		// Check if the list is empty before returning
		if (customers.isEmpty()) {
			System.out.println("Error: The function retrieved no customers.");
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return customers;
	}


	public static Customer findCustomerByPhone(String phoneNumber) {
		/*
		 * Query the database for a customer using a phone number.
		 * If found, then return a Customer object for the customer.
		 * If it's not found....then return null
		 *
		 */
		try {
			connect_to_db();

			String query = "SELECT * FROM Customer WHERE Phone= " + phoneNumber + ";";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, phoneNumber);

			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				int custID = resultSet.getInt("CustID");
				String name = resultSet.getString("Name");
				String address = resultSet.getString("Address");
				phoneNumber = resultSet.getString("Phone");
				// Assuming you have a Customer class with a constructor
				Customer customer = new Customer(custID, name, phoneNumber, address);

				// Don't forget to close the connection
				conn.close();

				return customer;
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();  // Handle the exception according to your needs
		} finally {
			try {
				// Close the connection in the finally block to ensure it's always closed
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}


	public static ArrayList<Topping> getToppingList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database for the available toppings and
		 * return an arrayList of all the available toppings.
		 * Don't forget to order the data coming from the database appropriately.
		 *
		 */
		ArrayList<Topping> toppings = new ArrayList<>();
		String query = "SELECT * FROM Topping ORDER BY TopID;";

		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rset = stmt.executeQuery(query);

		// Check if there are no toppings
		if (!rset.next()) {
			System.out.println("No toppings found in the table.");
			conn.close();
			return toppings;
		}

		do {
			int topID = rset.getInt("TopID");
			String topName = rset.getString("TopName");
			Double perAMT = rset.getDouble("PerAMT");
			Double medAMT = rset.getDouble("MedAMT");
			Double lgAMT = rset.getDouble("lgAMT");
			Double xlAMT = rset.getDouble("PerAMT");
			Double custPrice = rset.getDouble("CustPrice");
			Double busPrice = rset.getDouble("BusPrice");
			int minINVT = rset.getInt("MinINVT");
			int curINVT = rset.getInt("CurINVT");

			Topping topping = new Topping(topID, topName, perAMT, medAMT, lgAMT, xlAMT, custPrice, busPrice, minINVT, curINVT);
			toppings.add(topping);
		} while (rset.next());

		// Check if the list is empty before returning
		if (toppings.isEmpty()) {
			System.out.println("Error: The function retrieved no toppings.");
			//DO NOT FORGET TO CLOSE YOUR CONNECTION
			conn.close();
			return null;
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return toppings;
	}


	public static Topping findToppingByName(String name) throws SQLException, IOException {
		/*
		 * Query the database for the topping using it's name.
		 * If found, then return a Topping object for the topping.
		 * If it's not found....then return null
		 *
		 */
		ArrayList<Topping> toppingList = getToppingList();

		// Check if the list is not null and not empty
		if (toppingList != null && !toppingList.isEmpty()) {
			for (Topping topping : toppingList) {
				if (topping.getTopName().equalsIgnoreCase(name)) {
					// Use equalsIgnoreCase for case-insensitive comparison
					return topping;
				}
			}
		}
		return null;
	}

	public static Topping findToppingByID(int topID) throws SQLException, IOException {
		/*
		 * Query the database for the topping using it's topID.
		 * If found, then return a Topping object for the topping.
		 * If it's not found....then return null
		 *
		 */
		ArrayList<Topping> toppingList = getToppingList();

		// Check if the list is not null and not empty
		if (toppingList != null && !toppingList.isEmpty()) {
			for (Topping topping : toppingList) {
				if (topping.getTopID() == topID) {
					System.out.println("Found topping with ID " + topID);
					return topping;
				}
			}
		}
		return null;
	}


	public static void addToInventory(Topping t, double quantity) throws SQLException, IOException {
		Topping topping;

		connect_to_db();
		/*
		 * Updates the quantity of the topping in the database by the amount specified.
		 *
		 * */
		try {
			topping = findToppingByName(t.getTopName());

			// Check if the topping exists in the database
			if (topping == null) {
				System.out.println("Error: Topping does not exist in the database.");
				return;
			}

			double currentQuantity = topping.getCurINVT();
			double newQuantity = currentQuantity + quantity;

			String query = "UPDATE Topping SET CurINVT = ? WHERE TopID = ?;";

			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setDouble(1, newQuantity);
			stmt.setInt(2, topping.getTopID());

		} finally {
			//DO NOT FORGET TO CLOSE YOUR CONNECTION
			conn.close();
		}
	}

	public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
		Double baseCustPrice;

		connect_to_db();
		/*
		 * Query the database for the base customer price for that size and crust pizza.
		 *
		 */
		String query = "SELECT CustPrice FROM Pizza WHERE Size=" + size + "AND Crust=" + crust + ";";

		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rset = stmt.executeQuery(query);

		// Check if there are no pizzas of that size and crustType
		if (!rset.next()) {
			System.out.println("No pizza found with Size " + size + " and CrustType " + crust);
			conn.close();
			return 0.0;
		}

		do {
			baseCustPrice = rset.getDouble("CustPrice");
			System.out.println(baseCustPrice);
		} while (rset.next());

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return baseCustPrice;
	}

	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		Double baseBusPrice;

		connect_to_db();
		/*
		 * Query the database fro the base business price for that size and crust pizza.
		 *
		 */
		String query = "SELECT BusPrice FROM Pizza WHERE Size=" + size + "AND CrustType=" + crust + ";";

		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rset = stmt.executeQuery(query);

		// Check if there are no pizzas of that size and crustType
		if (!rset.next()) {
			System.out.println("No pizza found with Size " + size + " and CrustType " + crust);
			conn.close();
			return 0.0;
		}

		do {
			baseBusPrice = rset.getDouble("BusPrice");
			System.out.println(baseBusPrice);
		} while (rset.next());

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return baseBusPrice;
	}


	public static void printInventory() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Queries the database and prints the current topping list with quantities.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		String query = "SELECT * FROM Topping ORDER BY TopID;";

		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rset = stmt.executeQuery(query);

		// Check if there are no toppings
		if (!rset.next()) {
			System.out.println("Inventory is empty");
			conn.close();
		}

		do {
			int topID = rset.getInt("TopID");
			String topName = rset.getString("TopName");
			Double perAMT = rset.getDouble("PerAMT");
			Double medAMT = rset.getDouble("MedAMT");
			Double lgAMT = rset.getDouble("lgAMT");
			Double xlAMT = rset.getDouble("PerAMT");
			Double custPrice = rset.getDouble("CustPrice");
			Double busPrice = rset.getDouble("BusPrice");
			int minINVT = rset.getInt("MinINVT");
			int curINVT = rset.getInt("CurINVT");

			Topping topping = new Topping(topID, topName, perAMT, medAMT, lgAMT, xlAMT, custPrice, busPrice, minINVT, curINVT);
			String InventoryEntry = topping.toString();

			System.out.println(InventoryEntry);
		} while (rset.next());

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void printToppingPopReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ToppingPopularity view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		String query = "SELECT * FROM ToppingPopularity ORDER BY ToppingCount DESC;";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		System.out.printf("%-20s%-20s%n", "Topping", "Topping Count");

		while (rs.next()) {
			String topping = rs.getString("Topping");
			int count = rs.getInt("ToppingCount");
			System.out.printf("%-20s%-20d%n", topping, count);
		}

		// DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void printProfitByPizzaReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByPizza view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		String query = "SELECT * FROM ProfitByPizza ORDER BY Profit DESC;";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		System.out.printf("%-15s%-15s%-15s%-15s%n", "Pizza Size", "Pizza Crust", "Profit", "LastOrderDate");

		while (rs.next()) {
			String size = rs.getString("Size");
			String crustType = rs.getString("CrustType");
			double profit = rs.getDouble("Profit");
			int orderMonth = rs.getInt("OrderMonth");

			System.out.printf("%-15s%-15s%-15.2f%-15d%n", size, crustType, profit, orderMonth);
		}

		// DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void printProfitByOrderType() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByOrderType view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		String query = "SELECT * FROM ProfitByOrderType ORDER BY CustomerType, OrderMonth;";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		System.out.printf("%-20s%-20s%-20s%-20s%n", "Order Type", "Order Month", "TotalOrderCost", "Profit");

		while (rs.next()) {
			String orderType = rs.getString("Order Type");
			int orderMonth = rs.getInt("Order Month");
			double totalOrderPrice = rs.getDouble("TotalOrderCost");
			double profit = rs.getDouble("Profit");

			System.out.printf("%-20s%-20d%-20.2f%-20.2f%n", orderType, orderMonth, totalOrderPrice, profit);
		}

		// DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}



	public static String getCustomerName(int CustID) throws SQLException, IOException
	{
	/*
		 * This is a helper method to fetch and format the name of a customer
		 * based on a customer ID. This is an example of how to interact with
		 * your database from Java.  It's used in the model solution for this project...so the code works!
		 *
		 * OF COURSE....this code would only work in your application if the table & field names match!
		 *
		 */

		 connect_to_db();

		/*
		 * an example query using a constructed string...
		 * remember, this style of query construction could be subject to sql injection attacks!
		 *
		 */
		String cname1 = "";
		String query = "Select FName, LName From Customer WHERE CustID=" + CustID + ";";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		while(rset.next())
		{
			cname1 = rset.getString(1) + " " + rset.getString(2);
		}

		/*
		* an example of the same query using a prepared statement...
		*
		*/
		String cname2 = "";
		PreparedStatement os;
		ResultSet rset2;
		String query2;
		query2 = "Select FName, LName From Customer WHERE CustID=?;";
		os = conn.prepareStatement(query2);
		os.setInt(1, CustID);
		rset2 = os.executeQuery();
		while(rset2.next())
		{
			cname2 = rset2.getString("FName") + " " + rset2.getString("LName"); // note the use of field names in the getSting methods
		}

		conn.close();
		return cname1; // OR cname2
	}

	/*
	 * The next 3 private methods help get the individual components of a SQL datetime object.
	 * You're welcome to keep them or remove them.
	 */
	private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(0,4));
	}
	private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(8, 10));
	}

	public static boolean checkDate(int year, int month, int day, String dateOfOrder)
	{
		if(getYear(dateOfOrder) > year)
			return true;
		else if(getYear(dateOfOrder) < year)
			return false;
		else
		{
			if(getMonth(dateOfOrder) > month)
				return true;
			else if(getMonth(dateOfOrder) < month)
				return false;
			else
			{
				if(getDay(dateOfOrder) >= day)
					return true;
				else
					return false;
			}
		}
	}

	public static int getNextOrderID() {
		int nextOrderID;
		Order lastOrder = getLastOrder();

		if (lastOrder == null) {
			nextOrderID = 1;
		}
		else {
			nextOrderID = lastOrder.getOrderID() + 1;
		}
		System.out.println("Next Order ID: " + nextOrderID);
		return nextOrderID;
	}
}