import java.io.*; 
import java.sql.*;
import java.util.*;

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
	
	public static int getUser() {
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

	public static int getCustomerChoice() {
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

	public static int getAccountChoice() {
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

	public static void basicSearch() {
		
		try {

			System.out.print("Search: ");
			String search = sc.nextLine();
			System.out.println();
			String q = "select * from DB_Product where name like \'%" + search + "%\' order by price";
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

	public static void createNewUser() {

		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			String check = id;
			System.out.println();
			String quer = "select ID from DB_User where ID = \'" + check + "\'";
			myResultSet = sqlStatement.executeQuery(quer);
			check = null;

			while(myResultSet.next()) {
				check = myResultSet.getObject(1).toString();
			}

			if(check != null) {
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

			quer = "insert into DB_User values (" + Integer.parseInt(id) + ", \'" + usern + "\', \'" + addr + "\', \'" + email + "\', \'" + passw + "\', \'n\')";

			System.out.println("\nInsertion Complete...\n");
		
			sqlStatement.executeQuery(quer);

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void updateUser() {

		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			String check = id;
			System.out.println();
			String quer = "select ID from DB_User where ID = \'" + check + "\'";
			myResultSet = sqlStatement.executeQuery(quer);
			check = null;

			while(myResultSet.next()) {
				check = myResultSet.getObject(1).toString();
			}

			if(check == null) {
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

			quer = "update DB_User set name = \'" + usern + "\', address = \'" + addr + "\', email = \'" + email + "\', password = \'" + passw + "\' where ID = \'" + id + "\'";

			System.out.println("\nUpdate Complete...\n");
		
			sqlStatement.executeQuery(quer);

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void deleteUser() {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			String check = id;
			System.out.println();
			String quer = "select ID from DB_User where ID = \'" + check + "\'";
			myResultSet = sqlStatement.executeQuery(quer);
			check = null;

			while(myResultSet.next()) {
				check = myResultSet.getObject(1).toString();
			}

			if(check == null) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			quer = "delete from DB_User where ID = \'" + id + "\'";

			System.out.println("\nDeletion Complete...\n");

			sqlStatement.executeQuery(quer);

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}

	public static void customerOrder() {
		try {
			System.out.print("\nEnter ID: ");
			String id = sc.nextLine();
			String check = id;
			System.out.println();
			String quer = "select ID from DB_User where ID = \'" + check + "\'";
			myResultSet = sqlStatement.executeQuery(quer);
			check = null;

			while(myResultSet.next()) {
				check = myResultSet.getObject(1).toString();
			}

			if(check == null) {
				System.out.println("\nThat ID is not in the database");
				return;
			}

			System.out.print("\nEnter password: ");
			String passw = sc.nextLine();

			boolean valid = false;

			quer = "select ID from DB_User where ID = \'" + id + "\' and password = \'" + passw + "\'";
			myResultSet = sqlStatement.executeQuery(quer);

			while(myResultSet.next()) {
				valid = true;
			}

			if(!valid) {
				System.out.println("\nIncorrect password");
				return;
			}

			System.out.println("\nLet's begin your order!");

			System.out.println("Enter the Product ID you want to add. Enter \'done\' when finished\n");

			while(true) {
				String in = sc.nextLine();

				if(in.toLowerCase().equals("done"))
					break;

				String quer = "select ID from DB_User where ID = \'" + check + "\'";
				myResultSet = sqlStatement.executeQuery(quer);
				check = null;

				while(myResultSet.next()) {
					check = myResultSet.getObject(1).toString();
				}
			}

			

		} catch(SQLException e) {
			System.out.println("SQLException:" + e.getMessage() + " <BR>");
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage() + " <BR>");
		}
	}
}