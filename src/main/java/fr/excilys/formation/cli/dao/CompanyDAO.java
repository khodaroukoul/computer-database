package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.jdbc.DataSource;
import fr.excilys.formation.cli.models.Company;

public final class CompanyDAO{
	private static final String FIND_ALL_COMPANIES = "SELECT id, name FROM company";
	private static final String FIND_PAGE = " LIMIT ?, ?";
	private static final String COUNT_COMPANIES = "SELECT COUNT(id) AS RECORDS FROM company;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
	private static final String DELETE_COMPUTERS_BY_ID_COMPANY = "DELETE FROM computer WHERE company_id = ?";
	private static final String FIND_COMPANY = "SELECT id, name FROM company WHERE id=?";

	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "IN CLASS CompanyDAO. ";

	List<Company> companies = new ArrayList<>();

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

		try(Connection connect =  DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPANIES);
				ResultSet rst = prepare.executeQuery();
				) {
			while (rst.next()) {
				Company company = new Company.Builder().setId(rst.getInt("id")).setName(rst.getString("name")).build();
				companies.add(company);
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "getList " + CLASS_NAME + e.getMessage());
		}

		return companies;
	}

	public List<Company> getListPerPage(int noPage, int nbLine) {
		try(Connection connect =  DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPANIES + FIND_PAGE);
				) {

			prepare.setInt(1, (noPage-1)*nbLine);
			prepare.setInt(2, nbLine);
			ResultSet rst = prepare.executeQuery();
			while (rst.next()) {
				Company company = new Company.Builder().setId(rst.getInt("id")).setName(rst.getString("name")).build();
				companies.add(company);
			}

			if(rst!=null) {
				rst.close();
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "getListPerPage " + CLASS_NAME + e.getMessage());
		}

		return companies;
	}

	public int allRecords() {
		int records = 0;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(COUNT_COMPANIES);
				ResultSet rst = prepare.executeQuery();
				) {

			if(rst.next()) {
				records = rst.getInt("RECORDS");
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "allRecord " + CLASS_NAME + e.getMessage());
		}
		return records;
	}

	public void deleteCompany(int idCompany)  {

		try {
			Connection connect = DataSource.getConnection();
			try(PreparedStatement prepareFind = connect.prepareStatement(FIND_COMPANY);
					PreparedStatement prepareDelete = connect.prepareStatement(DELETE_COMPANY);
					PreparedStatement prepareDeleteComputer = connect.prepareStatement(DELETE_COMPUTERS_BY_ID_COMPANY);
					) {
				connect.setAutoCommit(false);
				prepareFind.setInt(1, idCompany);
				ResultSet rst = prepareFind.executeQuery(); 
				
				deleteCompanyTransaction(idCompany, connect, prepareDelete, prepareDeleteComputer, rst);
				
				if(rst!=null) {
					rst.close();					
				}

			} catch (SQLException e) {
				connect.rollback();
				logger.error(SQL_EXCEPTION + "deleteCompany " + CLASS_NAME + e.getMessage());
			} finally {
				connect.setAutoCommit(true);
			}
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "deleteCompanyConnection " + CLASS_NAME + e.getMessage());
		}		
	}

	private void deleteCompanyTransaction(int idCompany, Connection connect, PreparedStatement prepareDelete,
			PreparedStatement prepareDeleteComputer, ResultSet rst) throws SQLException {
		if(rst.first()) {
			int computersDeleted = deleteCompanyPrepared(idCompany, prepareDeleteComputer);
			if(computersDeleted>0) {
				int deletedCompanies = deleteCompanyPrepared(idCompany, prepareDelete);
				if (deletedCompanies > 0) {
					connect.commit();
				} else {
					connect.rollback();
				}
			} else {
				connect.rollback();
			}
		}
	}

	private int deleteCompanyPrepared(int idCompany, PreparedStatement prepareDelete) throws SQLException {
		prepareDelete.setInt(1, idCompany);
		return prepareDelete.executeUpdate();
	}
}
