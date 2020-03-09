package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.jdbc.DataSource;

public class CommonMethodsDAO {

	private static Logger logger = LoggerFactory.getLogger(CommonMethodsDAO.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "IN CLASS CommonMethodsDAO. ";

	protected int modelPrepareUpdate(int id, PreparedStatement prepare) throws SQLException {
		prepare.setInt(1, id);
		return prepare.executeUpdate();
	}

	protected ResultSet modelPrepareSelect(int id, PreparedStatement prepare) throws SQLException {
		prepare.setInt(1, id);
		return prepare.executeQuery();
	}


	protected ResultSet listPerPageResultSet(int noPage, int nbLine, PreparedStatement prepare) throws SQLException {
		prepare.setInt(1, (noPage-1)*nbLine);
		prepare.setInt(2, nbLine);
		ResultSet rst = prepare.executeQuery();
		return rst;
	}

	public int countAll(String command) {
		int records = 0;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(command);
				ResultSet rst = prepare.executeQuery();
				) {

			if(rst.next()) {
				records = rst.getInt("RECORDS");
			}

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "countAll " + CLASS_NAME + sql.getMessage());
		}
		return records;
	}
}
