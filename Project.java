import java.io.*; 
import java.sql.*;
import java.util.*;
import java.text.*;

public class Project {
	
	static Scanner sc = new Scanner(System.in);
	static String user = "sahir";
	static String pass = "abcd1234";
	static Connection sqlcon  = null;
	static Statement sqlStatement  = null;
	static ResultSet myResultSet  = null;

	public static void main(String[] args) throws SQLException {
				
		try { 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			
			// Connect to the database
			sqlcon= DriverManager.getConnection("jdbc:oracle:thin:hr/hr@oracle.cise.ufl.edu:1521:orcl", user, pass);
			
			
			System.out.println("\nConnected to database...\n");
			
			while(true) {

				// Create a Statement
				sqlStatement = sqlcon.createStatement();
			
				int user = getUser();
				
				if(user == 3)
					break;
				
				if(user == 1) { // user is a customer
					
					int choice = getCustomerChoice();
					
					switch(choice) {

						case 1:						
								basicSearch();
								break;
				
						case 2:
								int accountChoice = getAccountChoice();
								
								if(accountChoice == 1)  		// create
									createNewUser();
								else if(accountChoice == 2)  	// update
									updateUser();
								else							// delete
									deleteUser();

								break;
						
						case 3:
								customerOrder();
								break;
					}
				}
				else { // user is a staff member
					if(staffSignIn()) {
						int choice = getStaffChoice();

						switch(choice) {

							case 1:	// edit user
									editUserMenu();
									break; 
							case 2: // edit product
									editProductMenu();
									break;
							case 3: // edit order
									editOrderMenu();
									break;
							case 4: // edit categories
									editCategoriesMenu();
									break;
							case 5: // edit discount
									editDiscountMenu();
									break;
							case 6:	// add supplier
									editSupplierMenu();
									break;
							case 7: // add shelf
									editShelfMenu();
									break;
							case 8: // get total sales
									getTotalSales();
									break;
							case 9: // get shelf locations
									getShelfLocations();
									break;
							case 10: // get low stock alerts
									getAlerts();
									break;
						}
					}
				}
			}
			
			sqlStatement.close();
			sqlcon.close();

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		} finally {
			sqlStatement.close();
			sqlcon.close();			
		}
	} 
	
	public static void editUserMenu() throws SQLException {
		System.out.println("\n1. Create User");
		System.out.println("2. Edit User");
		System.out.println("3. Delete User\n");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice == 1) {
			createNewUser();
		} else if(choice == 2) {
			updateUserStaffStyle();
		} else if(choice == 3) {
			deleteUserStaffStyle();
		} else {
			System.out.println("Invalid choice");
			return;
		}
	}

	public static void editProductMenu() throws SQLException {
		System.out.println("\n1. Create Product");
		System.out.println("2. Edit Product");
		System.out.println("3. Delete Product");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice == 1) {
			createNewProduct();
		} else if(choice == 2) {
			updateProduct();
		} else if(choice == 3) {
			deleteProduct();
		} else {
			System.out.println("Invalid choice");
			return;
		}
	}

	public static void editOrderMenu() throws SQLException {
		System.out.println("\n1. Create Order");
		System.out.println("2. Edit Order");
		System.out.println("3. Delete Order");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice == 1) {
			createNewOrder();
		} else if(choice == 2) {
			updateOrder();
		} else if(choice == 3) {
			deleteOrder();
		} else {
			System.out.println("Invalid choice");
			return;
		}
	}

	public static void editCategoriesMenu() throws SQLException {
		System.out.println("\n1. Create Category");
		System.out.println("2. Edit Category");
		System.out.println("3. Delete Category");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice == 1) {
			createCategory();
		} else if(choice == 2) {
			updateCategory();
		} else if(choice == 3) {
			deleteCategory();
		} else {
			System.out.println("Invalid choice");
			return;
		}
	}

	public static void editDiscountMenu() throws SQLException {
		System.out.println("\n1. Create Discount");
		System.out.println("2. Edit Discount");
		System.out.println("3. Delete Discount");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice == 1) {
			createDiscount();
		} else if(choice == 2) {
			updateDiscount();
		} else if(choice == 3) {
			deleteDiscount();
		} else {
			System.out.println("Invalid choice");
			return;
		}
	}

	public static void editSupplierMenu() throws SQLException {
		System.out.println("\n1. Create Supplier");
		System.out.println("2. Attach Product");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice == 1) {
			createSupplier();
		} else if(choice == 2) {
			attachProduct();
		} else {
			System.out.println("Invalid choice");
			return;
		}
	}

	public static void editShelfMenu() throws SQLException {
		System.out.println("\n1. Create Shelf");
		System.out.println("2. Attach Shelf");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice == 1) {
			createShelf();
		} else if(choice == 2) {
			attachShelf();
		} else {
			System.out.println("Invalid choice");
			return;
		}
	}
	
	public static void createNewUser() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(verifyExists(id, "DB_User")) {
				System.out.println("\nThat ID is already taken");
				return;
			}

			System.out.print("\nEnter name: ");
			String usern = sc.nextLine();
			System.out.print("\nEnter password: ");
			String passw = sc.nextLine();
			System.out.print("\nEnter address: ");
			String addr = sc.nextLine();
			System.out.print("\nEnter email: ");
			String email = sc.nextLine();

			String quer = "insert into DB_User values (" + id + ", \'" + usern + "\', \'" + addr + "\', \'" + email + "\', \'" + passw + "\', \'n\')";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nInsertion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void createNewProduct() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(verifyExists(id, "DB_Product")) {
				System.out.println("\nThat ID is already taken");
				return;
			}

			System.out.print("\nEnter name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter description: ");
			String desc = sc.nextLine();
			System.out.print("\nEnter quantity: ");
			String quan = sc.nextLine();
			System.out.print("\nEnter price: ");
			String price = sc.nextLine();

			String quer = "insert into DB_Product values (" + id + ", \'" + name + "\', \'" + desc + "\', " + quan + ", " + price + ", \'y\')";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nInsertion Complete...\n");

			System.out.print("Does this product belong in a category? (\'y\' or \'n\') ");
			boolean hasCat = sc.nextLine().equals("y");

			if(!hasCat) {
				System.out.println();
				return;
			}

			System.out.print("\nEnter the category ID: ");
			String catID = sc.nextLine();

			if(!verifyExists(catID, "DB_Category")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			quer = "insert into DB_In values (" + catID + ", " + id + ")";
			sqlStatement.executeQuery(quer);

			System.out.println("Product-category relationship inserted"); 

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void createNewOrder() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(verifyExists(id, "DB_Order")) {
				System.out.println("\nThat ID is already taken");
				return;
			}

			System.out.print("\nEnter name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter price: ");
			String price = sc.nextLine();
			System.out.print("\nEnter data in (DD-MMM-YY) format: ");
			String date = sc.nextLine();
			System.out.print("\nEnter customer ID for this order: ");
			String customerid = sc.nextLine();

			if(!verifyExists(customerid, "DB_User")) {
				System.out.println("ID is not in the database");
				return;
			}

			String quer = "insert into DB_Order values (" + id + ", " + price + ", \'" + date + "\', \'n\')";
			sqlStatement.executeQuery(quer);
			quer = "insert into DB_Orders values (" + customerid + ", " + id + ")";
			sqlStatement.executeQuery(quer);

			System.out.println("\nInsertion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void createCategory() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(verifyExists(id, "DB_Category")) {
				System.out.println("\nThat ID is already taken");
				return;
			}

			System.out.print("\nEnter name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter description: ");
			String desc = sc.nextLine();
		
			String quer = "insert into DB_Category values (" + id + ", \'" + name + "\', \'" + desc + "\')";
			sqlStatement.executeQuery(quer);

			System.out.println("\nInsertion Complete...\n");

			System.out.print("Does this category have a parent? (\'y\' or \'n\') ");
			boolean parent = sc.nextLine().equals("y");

			if(parent) {
				System.out.print("\nEnter the parent category's ID: ");
				String id2 = sc.nextLine();

				if(id.equals(id2)) {
					System.out.println("\nCan't be your own parent");
					return;
				}

				if(!verifyExists(id, "DB_Category")) {
					System.out.println("\nTaht ID is not in the database");
					return;
				}

				quer = "insert into DB_Parent values (" + id2 + ", " + id + ")";
				sqlStatement.executeQuery(quer);

				System.out.println("Parent-child relationship inserted");
			}

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void createDiscount() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(verifyExists(id, "DB_Discount")) {
				System.out.println("\nThat ID is already in the database");
				return;
			}

			System.out.print("\nEnter name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter value: $");
			double value = Double.parseDouble(sc.nextLine());

			String quer = "insert into DB_Discount values (" + id + ", \'" + name + "\', " + value + ")";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nInsertion Complete...\n");

			System.out.println("Does this discount apply to a\n1. Product\n2. Category");
			String temp = sc.nextLine().equals("1") ? "DB_Product" : "DB_Category";
			String table = temp.equals("DB_Product") ? "DB_Discount_Available" : "DB_Category_Available";
		
			System.out.print("Enter product/category ID: ");
			String id2 = sc.nextLine();

			if(!verifyExists(id2, temp)) {
				System.out.println("That ID is not in the database");
				return;
			}

			quer = "insert into " + table + " values (" + id + ", " + id2 + ")";			
			sqlStatement.executeQuery(quer);

			System.out.println("Discount - product/category relationship created");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void createSupplier() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(verifyExists(id, "DB_Supplier")) {
				System.out.println("\nThat ID is already taken");
				return;
			}

			System.out.print("\nEnter name: ");
			String name = sc.nextLine();

			String quer = "insert into DB_Supplier values (" + id + ", \'" + name + "\')";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nInsertion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void createShelf() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(verifyExists(id, "DB_Shelf")) {
				System.out.println("\nThat ID is already taken");
				return;
			}

			System.out.print("\nEnter name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter available quantity: ");
			String quan = sc.nextLine();

			String quer = "insert into DB_Shelf values (" + id + ", \'" + name + "\', " + quan + ")";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nInsertion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void updateUser() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_User")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			System.out.print("Enter password: ");

			if(!verifyPass(id, sc.nextLine())) {
				System.out.println("\nInvalid password");
				return;
			} 

			System.out.print("\nEnter new name: ");
			String usern = sc.nextLine();
			System.out.print("\nEnter new password: ");
			String passw = sc.nextLine();
			System.out.print("\nEnter new address: ");
			String addr = sc.nextLine();
			System.out.print("\nEnter new email: ");
			String email = sc.nextLine();

			String quer = "update DB_User set name = \'" + usern + "\', address = \'" + addr + "\', email = \'" + email + "\', password = \'" + passw + "\' where ID = \'" + id + "\'";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nUpdate Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void updateOrder() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Order")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			System.out.print("\nEnter new price: ");
			String price = sc.nextLine();
			System.out.print("\nEnter new date in (DD-MMM-YY) format: ");
			String date = sc.nextLine();
			
			String quer = "update DB_Order set total_price = " + price + ", order_date = \'" + date + "\' where ID = " + id;
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nUpdate Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void updateCategory() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "Category")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			System.out.print("\nEnter new name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter new description: ");
			String desc = sc.nextLine();
		
			String quer = "update DB_Category set name = \'" + name + "\', description = \'" + desc + "\' where ID = " + id;
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nUpdate Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void updateUserStaffStyle() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_User")) {
				System.out.println("\nThat ID is not in the database");
				return;
			} 

			System.out.print("\nEnter new name: ");
			String usern = sc.nextLine();
			System.out.print("\nEnter new password: ");
			String passw = sc.nextLine();
			System.out.print("\nEnter new address: ");
			String addr = sc.nextLine();
			System.out.print("\nEnter new email: ");
			String email = sc.nextLine();

			String quer = "update DB_User set name = \'" + usern + "\', address = \'" + addr + "\', email = \'" + email + "\', password = \'" + passw + "\' where ID = \'" + id + "\'";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nUpdate Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void updateProduct() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Product")) {
				System.out.println("\nThat ID is not in the database");
				return;
			} 
			
			System.out.print("\nEnter new name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter new description: ");
			String desc = sc.nextLine();
			System.out.print("\nEnter new quantity: ");
			String quan = sc.nextLine();
			System.out.print("\nEnter new price: ");
			String price = sc.nextLine();
			System.out.print("\nActive? (y) or (n): ");
			String active = sc.nextLine().equals("n") ? "n" : "y";

			String quer = "update DB_Product set name = \'" + name + "\', description = \'" + desc + "\', stock_quantity = " + quan + ", price = " + price + ", active = \'" + active + "\' where ID = " + id;
		
			sqlStatement.executeQuery(quer);

			System.out.print("Does this product belong in a category? (\'y\' or \'n\') ");
			boolean hasCat = sc.nextLine().equals("y");

			if(!hasCat) {
				System.out.println("\nUpdate Complete...\n");
				return;
			}

			System.out.print("\nEnter the category ID: ");
			String catID = sc.nextLine();

			if(!verifyExists(catID, "DB_Category")) {
				System.out.println("\nThat ID is not in the database. Rest of update completed...\n");
				return;
			}

			quer = "insert into DB_In values (" + catID + ", " + id + ")";
			sqlStatement.executeQuery(quer);

			System.out.println("Product-category relationship inserted"); 

			System.out.println("\nUpdate Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void updateDiscount() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Discount")) {
				System.out.println("\nThat ID is not in the database");
				return;
			} 
			
			System.out.print("\nEnter new name: ");
			String name = sc.nextLine();
			System.out.print("\nEnter new value: ");
			double price = Double.parseDouble(sc.nextLine());

			String quer = "update DB_Discount set name = \'" + name + "\', price = " + price + " where ID = " + id;
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nUpdate Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void attachProduct() throws SQLException {
		try {
			System.out.print("\nEnter Supplier ID: ");
			String id = sc.nextLine();

			System.out.print("\nEnter Product ID: ");
			String id2 = sc.nextLine();
			
			if(!verifyExists(id, "DB_Supplier") || !verifyExists(id2, "DB_Product")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			String quer = "insert into DB_Supplys values (" + id + ", " + id2 + ")";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nProduct Attached...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void attachShelf() throws SQLException {
		try {
			System.out.print("\nEnter Product ID: ");
			String pid = sc.nextLine();

			System.out.print("\nEnter Shelf ID: ");
			String sid = sc.nextLine();
			
			if(!verifyExists(pid, "DB_Product") || !verifyExists(sid, "DB_Shelf")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			String quer = "insert into DB_Location values (" + pid + ", " + sid + ")";
		
			sqlStatement.executeQuery(quer);

			System.out.println("\nShelf Attached...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void getTotalSales() throws SQLException {
		try {

			String quer = "select DB_Supplier.name, sum(DB_Product.price) from DB_Contains join DB_Supplys on DB_Contains.Product_ID = DB_Supplys.Product_ID join DB_Product on DB_Contains.Product_ID = DB_Product.ID join DB_Supplier on DB_Supplys.Supplier_ID = DB_Supplier.ID group by DB_Supplier.name";
		
			myResultSet = sqlStatement.executeQuery(quer);

			System.out.println();

			while(myResultSet.next()) {
				System.out.println(myResultSet.getObject(1).toString() + "\t\t$" + myResultSet.getObject(2).toString());
			}

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void deleteUser() throws SQLException {
		try {

			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_User")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			System.out.print("Enter password: ");

			if(!verifyPass(id, sc.nextLine())) {
				System.out.println("\nInvalid password");
				return;
			} 

			String quer = "delete from DB_User where ID = \'" + id + "\'";

			sqlStatement.executeQuery(quer);

			System.out.println("\nDeletion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void deleteProduct() throws SQLException {
		try {

			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Product")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			if(interestExpressed(id)) {
				System.out.println("\nCan't delete this product. Interest has been expressed in the past month!");
				return;
			}

			String quer = "delete from DB_Product where ID = " + id;

			sqlStatement.executeQuery(quer);

			System.out.println("\nDeletion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void deleteCategory() throws SQLException {
		try {

			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Category")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			String quer = "delete from DB_Category where ID = " + id;

			sqlStatement.executeQuery(quer);

			System.out.println("\nDeletion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void deleteUserStaffStyle() throws SQLException {
		try {

			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_User")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			String quer = "delete from DB_User where ID = \'" + id + "\'";

			sqlStatement.executeQuery(quer);

			System.out.println("\nDeletion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void deleteOrder() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Order")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			String quer = "delete from DB_Order where ID = \'" + id + "\'";

			sqlStatement.executeQuery(quer);

			System.out.println("\nDeletion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void deleteDiscount() throws SQLException {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Discount")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			String quer = "delete from DB_Discount where ID = " + id;

			sqlStatement.executeQuery(quer);

			System.out.println("\nDeletion Complete...\n");

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void getShelfLocations() throws SQLException {
		try {
			System.out.print("\nEnter Order ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_Order")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			String quer = "select Product_ID from DB_Contains where Order_ID = " + id;

			myResultSet = sqlStatement.executeQuery(quer);

			List<String> list = new ArrayList<String>();

			while(myResultSet.next())
				list.add(myResultSet.getObject(1).toString());
		
			System.out.println();

			for(String s: list) {

				quer = "select DB_Product.name, DB_Shelf.name from DB_Location join DB_Product on DB_Location.Product_ID = DB_Product.ID join DB_Shelf on DB_Location.Shelf_ID = DB_Shelf.ID where DB_Location.Product_ID = " + s;
				myResultSet = sqlStatement.executeQuery(quer);
				while(myResultSet.next()) 
					System.out.println(myResultSet.getObject(1).toString() + "\t\t" + myResultSet.getObject(2).toString());
			}

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static int getUser() throws SQLException {
		System.out.println("\nMake Selection");
		System.out.println("1. Customer");
		System.out.println("2. Staff");
		System.out.println("3. Exit");
		
		int choice = Integer.parseInt(sc.nextLine());
		
		if(choice < 1 || choice > 3) {
			System.out.println("\nIncorrect choice, try again\n");
			return getUser();
		}
		
		return choice;
	}

	public static int getCustomerChoice() throws SQLException {
		System.out.println("\nMake Selection");
		System.out.println("1. Basic Search");
		System.out.println("2. Create/Update/Delete Account");
		System.out.println("3. Sign In");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice < 1 || choice > 3) {
			System.out.println("\nIncorrect choice, try again\n");
			return getCustomerChoice();
		}

		return choice;
	}

	public static int getStaffChoice() throws SQLException {
		System.out.println("\nMake Selection");
		System.out.println("1.  Edit User Account");
		System.out.println("2.  Edit Product");
		System.out.println("3.  Edit Order");
		System.out.println("4.  Edit Categories");
		System.out.println("5.  Edit Discount");
		System.out.println("6.  Edit Supplier Menu");
		System.out.println("7.  Edit Shelf Menu");
		System.out.println("8.  Get Total Sales");
		System.out.println("9.  Get Shelf Locations");
		System.out.println("10. Get Low Stock Alerts");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice < 1 || choice > 10) {
			System.out.println("\nIncorrect choice, try again\n");
			return getStaffChoice();
		}
		return choice;
	}

	public static int getAccountChoice() throws SQLException {
		System.out.println("\n1. Create Account");
		System.out.println("2. Update Account");
		System.out.println("3. Delete Account");

		int choice = Integer.parseInt(sc.nextLine());

		if(choice < 1 || choice > 3) {
			System.out.println("\nIncorrect choice, try again\n");
			return getAccountChoice();
		}
		return choice;
	}

	public static void basicSearch() throws SQLException {
		try {

			System.out.print("Search: ");
			String search = sc.nextLine();
			System.out.print("Sort (a)scending or (d)escending? ");
			String sortOrder = sc.nextLine().equals("a") ? "asc" : "desc";
			System.out.println();
			String q = "select * from DB_Product where name like \'%" + search + "%\' order by price " + sortOrder;
			myResultSet = sqlStatement.executeQuery(q);
			
			while(myResultSet.next()) {
				System.out.println("\n-------------------------------------------------------");
				System.out.println("ID:            \t\t\t" + myResultSet.getObject(1).toString());
				System.out.println("Name:          \t\t\t" + myResultSet.getObject(2).toString());
				System.out.println("Description:   \t\t\t" + myResultSet.getObject(3).toString());
				System.out.println("Stock Quantity:\t\t\t" + myResultSet.getObject(4).toString());
				System.out.println("Price:         \t\t\t" + myResultSet.getObject(5).toString());
				System.out.println("Active:        \t\t\t" + myResultSet.getObject(6).toString());					 
			}

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void getAlerts() throws SQLException {
		try {

			String q = "select * from DB_Alerts";
			myResultSet = sqlStatement.executeQuery(q);
			
			String title = null;

			while(myResultSet.next()) {
				title = "\nOut of Stock Products\n";
				break;
			}

			if(title == null) {
				System.out.println("\nNo alerts at this time");
				return;
			}

			System.out.println(title);
	
			myResultSet = sqlStatement.executeQuery(q);

			while(myResultSet.next()) 
				System.out.println("Product ID: " + myResultSet.getObject(1).toString() + "\t\tName: " + myResultSet.getObject(2).toString());

			System.out.print("\nClear alerts? 'y' or 'n' ");

			boolean clear = sc.nextLine().equals("y");

			if(clear) {
				q = "delete from DB_Alerts";
				sqlStatement.executeQuery(q);
			}

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static boolean staffSignIn() throws SQLException {
		System.out.print("\nEnter ID: ");
		String id = sc.nextLine();
		String p = verifyStaffExists(id);

		if(p == null) {
			System.out.println("\nStaff ID not in database");
			return false;
		} 

		System.out.print("Enter password: ");

		if(!sc.nextLine().equals(p)) {
			System.out.println("\nIncorrect password");
			return false;
		}
	
		return true;
	}

	public static String verifyStaffExists(String id) throws SQLException {
		String quer = "select password from DB_User where is_staff = \'y\' and ID = " + id;
		myResultSet = sqlStatement.executeQuery(quer);
			
		while(myResultSet.next())
			return myResultSet.getObject(1).toString();
			
		return null;
	}

	public static void customerOrder() throws SQLException {
		try {

			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			
			if(!verifyExists(id, "DB_User")) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			System.out.print("Enter password: ");
			String passw = sc.nextLine();

			boolean valid = false;

			String quer = "select ID from DB_User where ID = \'" + id + "\' and password = \'" + passw + "\'";
			myResultSet = sqlStatement.executeQuery(quer);

			while(myResultSet.next()) {
				valid = true;
			}

			if(!valid) {
				System.out.println("\nIncorrect password");
				return;
			}

			System.out.println("\n1. Place order\n2. Pay");
			boolean letsOrder = sc.nextLine().equals("1");

			if(letsOrder) {

				System.out.print("\nToday's date is " + getTodaysDate());

				String date = getTodaysDate();

				System.out.print("\nEnter a 3 digit ID for this order: ");

				String orderid;

				while(true) {
					orderid = sc.nextLine();
					if(!verifyExists(orderid, "DB_Order"))
						break;
					System.out.print("\nAlready taken, try another 3 digit order ID: ");
				}

				createOrderID(orderid, date);

				System.out.println("\nLet's begin your order!");

				System.out.println("\nEnter the Product ID you want to add. Enter \'done\' when finished");

				double total = 0.0;

				while(true) {
					String in = sc.nextLine();

					if(in.toLowerCase().equals("done"))
						break;

					if(!productExists(in)) {
						System.out.println("Product doesn't exist or is out of stock");
						continue;
					}

					total += addToOrder(orderid, in);
					
					System.out.println("Added to order!");
				}	

				orderHelper(id, orderid, total);
			}

			payOrder(id);	

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static boolean verifyExists(String id, String table) throws SQLException {
		String quer = "select ID from " + table + " where ID = " + id;
		myResultSet = sqlStatement.executeQuery(quer);
	
		while(myResultSet.next())
			return true;

		return false;
	}

	public static boolean verifyPass(String userid, String p) throws SQLException {
		String quer = "select password from DB_User where ID = " + userid;
		myResultSet = sqlStatement.executeQuery(quer);

		while(myResultSet.next())
			return myResultSet.getObject(1).toString().equals(p);

		return false;
	}

	public static boolean productExists(String id) throws SQLException {
		String quer = "select ID, stock_quantity from DB_Product where ID = " + id;
		myResultSet = sqlStatement.executeQuery(quer);
		
		while(myResultSet.next()) {
			if(myResultSet.getObject(2).toString().equals("0"))
				return false;
			return true;
		}

		return false;
	}

	public static void createOrderID(String id, String date) throws SQLException {
		try {
			String quer = "insert into DB_Order values (" + id + ", 0.0, \'" + date + "\', \'n\')";
			sqlStatement.executeQuery(quer);
		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static double addToOrder(String orderid, String productid) throws SQLException {
		String quer = "update DB_Product set stock_quantity = stock_quantity-1 where ID = " + productid;
		sqlStatement.executeQuery(quer);
		quer = "insert into DB_Contains values (" + orderid + ", " + productid + ")";
		sqlStatement.executeQuery(quer);
		quer = "select price from DB_Product where id = " + productid;
		myResultSet = sqlStatement.executeQuery(quer);
		
		while(myResultSet.next()) {
			return Double.parseDouble(myResultSet.getObject(1).toString());
		}
		
		return 0.0;
	}

	public static void orderHelper(String userid, String orderid, double total) throws SQLException {
		try {
			String quer = "update DB_Order set total_price = " + total + " where ID = " + orderid;
			sqlStatement.executeQuery(quer);
		 	quer = "insert into DB_Orders values (\'" + userid + "\', \'" + orderid + "\')";
		 	sqlStatement.executeQuery(quer);
		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void payOrder(String userid) throws SQLException {
		try {
			String quer = "select Order_ID from DB_Orders where User_ID = \'" + userid + "\'";
			myResultSet = sqlStatement.executeQuery(quer);
			List<String> list = new ArrayList<String>();
			boolean once = true;
			
			while(myResultSet.next()) {
				list.add(myResultSet.getObject(1).toString());
			}
			
			for(int j = 0; j < list.size(); ++j) {
				String s = list.get(j);
				quer = "select total_price from DB_Order where ID = " + s + "and paid = \'n\'";
				myResultSet = sqlStatement.executeQuery(quer);
				while(myResultSet.next()) {
					if(once) {
						System.out.println("\nLets pay your order(s)");	
						System.out.println("Which order would you like to pay?\nSelect the ID or \'0\' if you don't want to pay right now\n");
						once = false;
					}
					System.out.println(s + ":\t$" + Double.parseDouble(myResultSet.getObject(1).toString()));
				}
			}

			if(once) {
				System.out.println("\nNo unpaid orders");
				return;
			}

			String selectedOrder = sc.nextLine();

			if(selectedOrder.equals("0"))
				return;

			if(!list.contains(selectedOrder)) {
				System.out.println("Invalid selection");
				return;
			}

			System.out.println("\nLet's check for discounts...\n");

			quer = "select Product_ID from DB_Contains where Order_ID = \'" + selectedOrder + "\'";
			myResultSet = sqlStatement.executeQuery(quer);
				
			list.clear();

			while(myResultSet.next()) 
				list.add(myResultSet.getObject(1).toString());	// all product IDs

			discountHelper(list, selectedOrder);
		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void discountHelper(List<String> productids, String selectedOrder) throws SQLException {
		try {
			double totalDiscount = 0.0;
			List<String> list2 = new ArrayList<String>();
			String quer;

			for(int i = 0; i < productids.size(); ++i) {
				list2.add(null);
				String s = productids.get(i);
				quer = "select Discount_ID from DB_Discount_Available where Product_ID = " + s;
				myResultSet = sqlStatement.executeQuery(quer);
				while(myResultSet.next())
					list2.set(i, myResultSet.getObject(1).toString());
			}

			System.out.println("Here is the discount available for each product\n");

			for(int i = 0; i < list2.size(); ++i) {
				String s = list2.get(i);
				double currDiscount = 0.0;
				double catDiscount = 0.0;

				if(s != null) {
					quer = "select value from DB_Discount where ID = " + s;
					myResultSet = sqlStatement.executeQuery(quer);
					while(myResultSet.next())
						currDiscount = Double.parseDouble(myResultSet.getObject(1).toString());
				}

				currDiscount += categoryDiscount(productids.get(i));
				totalDiscount += currDiscount;

				System.out.println(getProductName(productids.get(i)) + ": \t$" + currDiscount);
			}

			System.out.println("\nThe total available discount is:        \t$" + totalDiscount);
			System.out.print("That means you will be paying a total of: \t$");
			quer = "select total_price from DB_Order where ID = " + selectedOrder;
			myResultSet = sqlStatement.executeQuery(quer);

			while(myResultSet.next())
				System.out.println(Double.parseDouble(myResultSet.getObject(1).toString()) - totalDiscount);

			quer = "update DB_Order set paid = \'y\' where ID = " + selectedOrder;
			sqlStatement.executeQuery(quer);

			System.out.println("Thank you for your payment!");
		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static double categoryDiscount(String productid) throws SQLException {
		String quer = "select Category_ID from DB_In where Product_ID = " + productid;
		myResultSet = sqlStatement.executeQuery(quer);

		String categoryID = null;
		while(myResultSet.next()) 
			categoryID = myResultSet.getObject(1).toString();
		
		if(categoryID == null)
			return 0;
		
		quer = "select Discount_ID from DB_Category_Available where Category_ID = " + categoryID;

		myResultSet = sqlStatement.executeQuery(quer);

		String discountID = null;
	
		while(myResultSet.next()) 
			discountID = myResultSet.getObject(1).toString();
		
		if(discountID == null)
			return 0;
		
		quer = "select value from DB_Discount where ID = " + discountID;

		myResultSet = sqlStatement.executeQuery(quer);
		
		while(myResultSet.next()) 
			return Double.parseDouble(myResultSet.getObject(1).toString());
	
		return 0.0;
	}

	public static String getProductName(String productid) throws SQLException {
		String quer = "select name from DB_Product where ID = " + productid;
		myResultSet = sqlStatement.executeQuery(quer);

		while(myResultSet.next())
			return myResultSet.getObject(1).toString();

		return null;
	}

	public static boolean interestExpressed(String productID) throws Exception {
		String quer = "select Order_ID from DB_Contains where Product_ID = " + productID;
		List<String> orders = new ArrayList<String>();	
		myResultSet = sqlStatement.executeQuery(quer);

		while(myResultSet.next())
			orders.add(myResultSet.getObject(1).toString());

		List<String> dates = new ArrayList<String>();

		for(String order: orders) {
			quer = "select order_date from DB_Order where ID = " + order;
			myResultSet = sqlStatement.executeQuery(quer);

			while(myResultSet.next())
				dates.add(myResultSet.getObject(1).toString());
		}

		for(String date: dates) 
			if(withinOneMonth(date))
				return true;

		return false; 
	}

	public static boolean withinOneMonth(String date) throws ParseException {
		
		date = convertFormat(date);

		System.out.println("here1");
		System.out.println(date);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("here2");

		java.util.Date past = format.parse(date);
		System.out.println("here3");

		java.util.Date date_today = format.parse(format.format(new java.util.Date()));
		System.out.println("here4");

		Calendar calendar_today = Calendar.getInstance();
		System.out.println("here5");
	
		calendar_today.setTime(date_today);

		calendar_today.add(Calendar.MONTH, -1);

		java.util.Date monthAgo = calendar_today.getTime();

		return past.after(monthAgo);
	}

	public static String getTodaysDate() {
		DateFormat d = new SimpleDateFormat("dd/MMM/yy");
		java.util.Date date = new java.util.Date();
		return d.format(date);
	}

	public static String convertFormat(String date) {
		String[] temp = date.split(" ");
		return temp[0];
	}
}