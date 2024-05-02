package persistance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class not found");
		} catch (SQLException e) {
			System.out.println("Database Connection not found");
		}
		return con;
	}
}
