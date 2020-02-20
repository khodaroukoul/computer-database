package fr.excilys.formation.cli.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionMySQL{
	private static Logger logger = LoggerFactory.getLogger(ConnectionMySQL.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String IO_EXCEPTION = "IO EXCEPTION ERROR IN ";
	private static final String CLASS_NOT_FOUND = "CLASS_NOT_FOUND EXCEPTION ERROR IN ";
	private static final String METHOD_NAME = "getConncetion in class ConnectionMySQL";

	private static ConnectionMySQL connection = null;
	Properties conProp;
	String driverClassName;
	String connectionUrl;
	String dbUser;
	String dbPwd;

	private ConnectionMySQL() {
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conProp = new Properties ();
			conProp.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties"));
			driverClassName = conProp.getProperty("driverClassName");
			connectionUrl = conProp.getProperty("connectionUrl");
			dbUser = conProp.getProperty("dbUser");
			dbPwd = conProp.getProperty("dbPwd");
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + METHOD_NAME);
		} catch (IOException e) {
			logger.error(IO_EXCEPTION + METHOD_NAME);
		} catch (ClassNotFoundException e) {
			logger.error(CLASS_NOT_FOUND + METHOD_NAME);
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