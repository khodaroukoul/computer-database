package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.enums.SQLCommands;
import fr.excilys.formation.cli.jdbc.DataSource;
import fr.excilys.formation.cli.mapper.CompanyMapper;
import fr.excilys.formation.cli.models.Company;

public final class CompanyDAO{

	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "IN CLASS CompanyDAO. ";

	List<Company> companies = new ArrayList<>();
	CommonMethodsDAO commonMethods = new CommonMethodsDAO();

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
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.FIND_ALL_COMPANIES.getSqlCommands());
				ResultSet rst = prepare.executeQuery();
				) {
			while (rst.next()) {
				Company company = new Company.Builder().setId(rst.getInt("id"))
						.setName(rst.getString("name")).build();
				companies.add(company);
			}

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "getList " + CLASS_NAME + sql.getMessage());
		}

		return companies;
	}

	public List<Company> getListPerPage(int noPage, int nbLine) {
		try(Connection connect =  DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.FIND_ALL_COMPANIES.getSqlCommands() 
						+ SQLCommands.FIND_PAGE.getSqlCommands());
				ResultSet rst = commonMethods.listPerPageResultSet(noPage, nbLine, prepare);
				) {
			while (rst.next()) {
				Company company = new Company.Builder().setId(rst.getInt("id"))
						.setName(rst.getString("name")).build();
				companies.add(company);
			}
		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "getListPerPage " + CLASS_NAME + sql.getMessage());
		}
		return companies;
	}

	public void deleteCompany(int idCompany)  {
		try{
			Connection connect = DataSource.getConnection();
			deleteCompanyTransaction(idCompany, connect);
		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "deleteCompany " + CLASS_NAME + sql.getMessage());
		}		
	}

	public int countAll() {
		return commonMethods.countAll(SQLCommands.COUNT_COMPANIES.getSqlCommands());
	}

	private void deleteCompanyTransaction(int idCompany, Connection connect) throws SQLException {
		try(PreparedStatement prepareFind = connect.prepareStatement(
				SQLCommands.FIND_COMPANY.getSqlCommands());
				PreparedStatement prepareDelete = connect.prepareStatement(
						SQLCommands.DELETE_COMPANY.getSqlCommands());
				PreparedStatement prepareDeleteComputer = connect.prepareStatement(
						SQLCommands.DELETE_COMPUTERS_BY_ID_COMPANY.getSqlCommands());
				ResultSet rst = commonMethods.modelPrepareSelect(idCompany, prepareFind);
				) {
			connect.setAutoCommit(false);
			deleteCompanyCommit(idCompany, connect, prepareDelete, prepareDeleteComputer, rst);

		} catch (SQLException sql) {
			connect.rollback();
			logger.error(SQL_EXCEPTION + "deleteCompanyTransaction " + CLASS_NAME + sql.getMessage());
		} finally {
			connect.setAutoCommit(true);
		}
	}

	private void deleteCompanyCommit(int idCompany, Connection connect, PreparedStatement prepareCompany,
			PreparedStatement prepareComputer, ResultSet rst) throws SQLException {
		if(rst.first()) {
			int computersDeleted = commonMethods.modelPrepareUpdate(idCompany, prepareComputer);
			if(computersDeleted>0) {
				int deletedCompanies = commonMethods.modelPrepareUpdate(idCompany, prepareCompany);
				if (deletedCompanies <= 0) {
					connect.rollback();
				}
				connect.commit();
			} else {
				connect.rollback();
			}
		}
	}

	public Optional<Company> findById(int id) {
		Optional<Company> company = Optional.empty();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.FIND_COMPANY.getSqlCommands());
				ResultSet rst = commonMethods.modelPrepareSelect(id, prepare)
				) {
			if(rst.first()) {
				company = CompanyMapper.getCompany(rst);
			}
		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "find " + CLASS_NAME + sql.getMessage());
		}
		return company;
	}
}
