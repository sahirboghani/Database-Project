import java.io.*; 
import java.sql.*;
import java.util.*;

public class Project {
	
	static Scanner sc = new Scanner(System.in);
	static String user = "sahir";
	static String pass = "abcd1234";

	public static void main(String[] args) {
		
		Connection sqlcon  = null;
		Statement sqlStatement  = null;
		ResultSet myResultSet  = null;
		
		try { 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			
			// Connect to the database
			sqlcon= DriverManager.getConnection("jdbc:oracle:thin:hr/hr@oracle.cise.ufl.edu:1521:orcl", user, pass);
			
			
			System.out.println("\nConnected to database...\n");
			
			while(true) {

				// Create a Statement
				sqlStatement = sqlcon.createStatement();
			
				String q = "";
			
				int user = getUser();
				
				if(user == 3)
					break;
				
				if(user == 1) { // user is a customer
					
					int choice = getCustomerChoice();
					
					switch(choice) {

						case 1:						
								System.out.print("Search: ");
								String search = sc.next();
								System.out.println();
								q = "select * from DB_Product where name like \'%" + search + "%\' order by price";
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
								break;
				
						case 2:

								break;
						
						case 3:
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
		} 
	} 
	
	public static int getUser() {
		System.out.println("\nMake Selection");
		System.out.println("1. Customer");
		System.out.println("2. Staff");
		System.out.println("3. Exit");
		
		int choice = sc.nextInt();
		
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

		int choice = sc.nextInt();

		if(choice < 1 || choice > 3) {
			System.out.println("\nIncorrect choice, try again\n");
			return getCustomerChoice();
		}

		return choice;
	}
}