package fr.excilys.formation.cli.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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