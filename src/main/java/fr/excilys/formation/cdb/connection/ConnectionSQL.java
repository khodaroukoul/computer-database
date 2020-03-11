package fr.excilys.formation.cdb.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConnectionSQL{
	
	private static Logger logger = LoggerFactory.getLogger(ConnectionSQL.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String IO_EXCEPTION = "IO EXCEPTION ERROR IN ";
	private static final String CLASS_NOT_FOUND = "CLASS_NOT_FOUND EXCEPTION ERROR IN ";
	private static final String METHOD_NAME = "getConncetion IN CLASS ConnectionSQL. ";

	public ConnectionSQL() {
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			Properties conProp = new Properties ();
			conProp.load(Thread.currentThread().getContextClassLoader().
					getResourceAsStream("connection.properties"));
			String driverClassName = conProp.getProperty("driverClassName");
			String connectionUrl = conProp.getProperty("connectionUrl");
			String dbUser = conProp.getProperty("dbUser");
			String dbPwd = conProp.getProperty("dbPwd");
			Class.forName(driverClassName);
			connection = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + METHOD_NAME + sql.getMessage());
		} catch (IOException io) {
			logger.error(IO_EXCEPTION + METHOD_NAME + io.getMessage());
		} catch (ClassNotFoundException cls) {
			logger.error(CLASS_NOT_FOUND + METHOD_NAME + cls.getMessage());
		}
		return connection;
	}
}