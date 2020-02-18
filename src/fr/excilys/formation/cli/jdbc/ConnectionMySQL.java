package fr.excilys.formation.cli.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionMySQL {
	String driverClassName = "com.mysql.cj.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost:3306/computer-database-db";
	String dbUser = "admincdb";
	String dbPwd = "qwerty1234";

	private static ConnectionMySQL connection = null;

	private ConnectionMySQL() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static ConnectionMySQL getInstance() {
		if (connection == null) {
			connection = new ConnectionMySQL();
		}
		return connection;
	}
}