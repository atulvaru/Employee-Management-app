package EmployeeData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeeDetails {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String option = "";
		String resultMessage = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "atul");
			System.out.println("Welcome to Employee Management Application.");
			while (!"0".equals(option)) {
				System.out.println("\nChoose an option:");
				System.out.println("1. Get all data");
				System.out.println("2. Update data");
				System.out.println("3. Insert data");
				System.out.println("4. Delete data");
				System.out.println("0. Exit");
				System.out.print("Enter your choice: ");

				option = scanner.nextLine();

				switch (option) {
				case "1":
					resultMessage = getAllData(con);
					break;
				case "2":
					resultMessage = getUpdateData(con, scanner);
					break;
				case "3":
					resultMessage = insertData(con, scanner);
					break;
				case "4":
					resultMessage = deleteData(con, scanner);
					break;
				case "0":
					resultMessage = "Exiting...";
					break;
				default:
					resultMessage = "Invalid option.";
				}

				System.out.println(resultMessage);
			}

			con.close();
			scanner.close();

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static String deleteData(Connection con, Scanner sc) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM employee WHERE id=?");

			System.out.print("Enter the employee id: ");
			int id = sc.nextInt();
			sc.nextLine(); // Clear the buffer

			ps.setInt(1, id);

			int x = ps.executeUpdate();
			return x + " row(s) deleted";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error deleting data: " + e.getMessage();
		}
	}

	private static String insertData(Connection con, Scanner sc) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO employee VALUES(?,?,?,?,?)");

			System.out.print("Enter the employee id: ");
			int id = sc.nextInt();
			sc.nextLine(); // Clear the buffer

			System.out.print("Enter the employee name: ");
			String name = sc.nextLine();

			System.out.print("Enter the employee age: ");
			int age = sc.nextInt();
			sc.nextLine(); // Clear the buffer

			System.out.print("Enter the employee department: ");
			String depart = sc.nextLine();

			System.out.print("Enter the employee salary: ");
			int sal = sc.nextInt();
			sc.nextLine(); // Clear the buffer

			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setInt(3, age);
			ps.setString(4, depart);
			ps.setInt(5, sal);

			int x = ps.executeUpdate();
			return x + " row(s) inserted";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error inserting data: " + e.getMessage();
		}
	}

	private static String getUpdateData(Connection con, Scanner sc) {
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("UPDATE employee SET name=?, age=?, department=?, salary=? WHERE id=?");

			System.out.print("Enter the Employee id: ");
			int id = sc.nextInt();
			sc.nextLine(); // Clear the buffer

			System.out.print("Enter the Employee name: ");
			String name = sc.nextLine();

			System.out.print("Enter the Employee age: ");
			int age = sc.nextInt();
			sc.nextLine(); // Clear the buffer

			System.out.print("Enter the Employee department: ");
			String depart = sc.nextLine();

			System.out.print("Enter the Employee salary: ");
			int sal = sc.nextInt();
			sc.nextLine(); // Clear the buffer

			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, depart);
			ps.setInt(4, sal);
			ps.setInt(5, id);

			int x = ps.executeUpdate();
			return x + " row(s) Updated";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error updating data: " + e.getMessage();
		}
	}

	private static String getAllData(Connection con) {
		Statement st = null;
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM employee");

			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4)
						+ " " + rs.getInt(5));
			}

			return "Data retrieval successful";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error retrieving data: " + e.getMessage();
		}
	}
}
