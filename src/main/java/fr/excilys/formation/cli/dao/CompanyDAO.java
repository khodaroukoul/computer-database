package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.jdbc.ConnectionMySQL;

public final class CompanyDAO{
	private static final String FIND_ALL_COMPANIES = "SELECT id, name FROM company";
	private static final String FIND_PAGE = " LIMIT ?, ?";

	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "in class CompanyDAO";

	List<Company> companies = new ArrayList<Company>();

	private static volatile CompanyDAO instance = null;
	
	private CompanyDAO() {
	}

	public final static CompanyDAO getInstance() {
		if (CompanyDAO.instance == null) {
			synchronized(CompanyDAO.class) {
				if (CompanyDAO.instance == null) {
					CompanyDAO.instance = new CompanyDAO();
				}
			}
		}
		return CompanyDAO.instance;
	}

	public List<Company> getList() {

		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPANIES);
				ResultSet rst = prepare.executeQuery();
				) {
			while (rst.next()) {
				Company company = new Company.CompanyBuilder().setId(rst.getInt("id")).setName(rst.getString("name")).build();
				companies.add(company);
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION+"getList in class "+CLASS_NAME);
		}

		return companies;
	}

	public List<Company> getListPerPage(int noPage, int nbLine) {
		ResultSet rst = null;
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPANIES+FIND_PAGE);
				) {

			prepare.setInt(1, (noPage-1)*nbLine);
			prepare.setInt(2, nbLine);
			rst = prepare.executeQuery();
			while (rst.next()) {
				Company company = new Company.CompanyBuilder().setId(rst.getInt("id")).setName(rst.getString("name")).build();
				companies.add(company);
			}
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION+"getListPerPage in class "+CLASS_NAME);
		}finally {
			try {
				rst.close();
			} catch (SQLException e) {
				logger.error(SQL_EXCEPTION+"getListPerPage(finally) in class "+CLASS_NAME);
			}
		}
		return companies;
	}

}
