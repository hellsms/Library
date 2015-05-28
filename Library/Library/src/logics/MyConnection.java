package logics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

	private static Connection connection;

	public static Connection getConnection() {
		return connection;
	}

	private static void connect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe", "system", "system");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MyConnection.java\n" + e.toString());
		}
	}
	
	public static void disconnect(){
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("MyConnection.java\n" + e.toString());
		}
	}
	
	static {
		connect();
	}
}
