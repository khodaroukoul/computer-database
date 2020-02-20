package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.jdbc.ConnectionH2;
import fr.excilys.formation.cli.jdbc.ConnectionMySQL;
import fr.excilys.formation.cli.mapper.ComputerMapper;

public final class ComputerDAO {
	private static final String FIND_ALL_COMPUTERS = "SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id";
	private static final String FIND_ONE_COMPUTER = "SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id WHERE cp.id = ?";
	private static final String NEW_COMPUTER = "INSERT INTO computer"
			+ " (id, name, introduced, discontinued, company_id)"
			+ " SELECT MAX(id)+1, ?, ?, ?, ? FROM computer";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?,"
			+ "discontinued = ?, company_id = ? WHERE id = ?";
	private static final String FIND_PAGE = " LIMIT ?, ?";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "in class ComputerDAO";

	Company company;
	Computer computer;

	private static volatile ComputerDAO instance = null;

	private ComputerDAO() {
	}

	public final static ComputerDAO getInstance() {
		if (ComputerDAO.instance == null) {
			synchronized(ComputerDAO.class) {
				if (ComputerDAO.instance == null) {
					ComputerDAO.instance = new ComputerDAO();
				}
			}
		}
		return ComputerDAO.instance;
	}

	public Computer create(Computer computer) {
		ResultSet rst = null;
		try(Connection connect = (System.getProperty("testCase") != null) ?
				ConnectionH2.getInstance().getConnection() :
					ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(NEW_COMPUTER,Statement.RETURN_GENERATED_KEYS);
				) {
			prepare.setString(1, computer.getName());
			prepare.setTimestamp(2, computer.getIntroduced()!=null?Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(3, computer.getDiscontinued()!=null?Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setInt(4,computer.getCompany().getId());
			prepare.executeUpdate();

			rst = prepare.getGeneratedKeys();
			rst.first();
			int auto_id = rst.getInt(1);
			computer.setId(auto_id);
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "create in class " + CLASS_NAME);
		}finally {
			try {
				rst.close();
			} catch (SQLException e) {
				logger.error(SQL_EXCEPTION + "create(finally) in class " + CLASS_NAME);
			}
		}

		return computer;
	}

	public boolean delete(int id) {
		boolean isDeleted = false;
		try(Connection connect = (System.getProperty("testCase") != null) ?
				ConnectionH2.getInstance().getConnection() :
					ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepareFind = connect.prepareStatement(FIND_ONE_COMPUTER);
				PreparedStatement prepareDelete = connect.prepareStatement(DELETE_COMPUTER);
				) {
			prepareFind.setInt(1, id);
			if(prepareFind.executeQuery().first()) {
				prepareDelete.setInt(1, id);
				prepareDelete.executeUpdate();
				isDeleted = true;
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "delete in class " + CLASS_NAME);
			isDeleted = false;
		}
		return isDeleted;
	}

	public Computer update(Computer computer) {
		try(Connection connect = (System.getProperty("testCase") != null) ?
				ConnectionH2.getInstance().getConnection() :
					ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(UPDATE_COMPUTER);
				) {
			prepare.setString(1, computer.getName());
			prepare.setTimestamp(2, computer.getIntroduced()!=null?Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(3, computer.getDiscontinued()!=null?Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setInt(4, computer.getCompany().getId());
			prepare.setInt(5, computer.getId());
			prepare.executeUpdate();
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "update in class " + CLASS_NAME);
		}

		return computer;
	}

	public Optional<Computer> find(int id) {
		Optional<Computer> computer =  Optional.empty();
		ResultSet rst = null;
		try(Connection connect = (System.getProperty("testCase") != null) ?
				ConnectionH2.getInstance().getConnection() :
					ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ONE_COMPUTER);
				) {
			prepare.setInt(1,id);
			rst = prepare.executeQuery();
			if(rst.first()) {
				computer = ComputerMapper.getInstance().getComputer(rst);
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "find in class " + CLASS_NAME);
		}finally {
			try {
				rst.close();
			} catch (SQLException e) {
				logger.error(SQL_EXCEPTION + "find(finally) in " + CLASS_NAME);
			}
		}
		return computer;
	}

	public Optional<List<Computer>> getList() {
		List<Computer> computers = new ArrayList<Computer>();
		try(Connection connect = (System.getProperty("testCase") != null) ?
				ConnectionH2.getInstance().getConnection() :
					ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPUTERS);
				ResultSet rst = prepare.executeQuery();
				) {

			while (rst.next()) {
				computer = ComputerMapper.getInstance().getComputer(rst).get();
				computers.add(computer);
			}
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "getList in class " + CLASS_NAME);
		}
		return Optional.ofNullable(computers);
	}

	public Optional<List<Computer>> getListPerPage(int noPage, int nbLine) {
		List<Computer> computers = new ArrayList<Computer>();
		ResultSet rst = null;
		try(Connection connect = (System.getProperty("testCase") != null) ?
				ConnectionH2.getInstance().getConnection() :
					ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPUTERS+FIND_PAGE);
				) {

			prepare.setInt(1, (noPage-1)*nbLine);
			prepare.setInt(2, nbLine);
			rst = prepare.executeQuery();
			while (rst.next()) {
				computer = ComputerMapper.getInstance().getComputer(rst).get();
				computers.add(computer);
			}
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "getListPerPage in class " + CLASS_NAME);
		}finally {
			try {
				rst.close();
			} catch (SQLException e) {
				logger.error(SQL_EXCEPTION + "getListPerPage(finally) in " + CLASS_NAME);
			}
		}
		return Optional.ofNullable(computers);
	}
}
