package fr.excilys.formation.cdb.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class DataSource {

	private static HikariConfig config;
	private static HikariDataSource ds;

	static {
		config = new HikariConfig("/hikari.properties");
		ds = new HikariDataSource( config );
	}

	private DataSource() {}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}