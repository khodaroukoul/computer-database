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

import fr.excilys.formation.cli.jdbc.DataSource;
import fr.excilys.formation.cli.mapper.ComputerMapper;
import fr.excilys.formation.cli.models.Computer;

public final class ComputerDAO {

	private static final String FIND_ALL_COMPUTERS = "SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id ORDER BY ";
	private static final String FIND_ONE_COMPUTER = "SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id WHERE cp.id = ?";
	private static final String NEW_COMPUTER = "INSERT INTO computer"
			+ " (id, name, introduced, discontinued, company_id)"
			+ " SELECT MAX(id)+1, ?, ?, ?, ? FROM computer";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private static final String DELETE_MULTI_COMPUTERS = "DELETE FROM computer WHERE id IN ( ";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?,"
			+ " discontinued = ?, company_id = ? WHERE id = ?";
	private static final String FIND_PAGE = " LIMIT ?, ?;";

	private static final String FIND_COMPUTERS_BY_NAME = "SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id"
			+ " WHERE cp.name LIKE ? " 
			+ " ORDER BY ";
	private static final String COUNT_COMPUTERS_FOUND_BY_NAME = "SELECT COUNT(cp.id) AS RECORDS FROM computer AS cp"
			+ "  WHERE cp.name LIKE ?;";
	private static final String COUNT_COMPUTERS = "SELECT COUNT(id) AS RECORDS FROM computer;";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "IN CLASS ComputerDAO. ";

	ComputerMapper pcMapperInstance = ComputerMapper.getInstance();

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

	public boolean create(Computer computer) {
		boolean isCreated = false;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(NEW_COMPUTER,Statement.RETURN_GENERATED_KEYS);
				) {
			prepare.setString(1, computer.getName());
			prepare.setTimestamp(2, computer.getIntroduced()!=null?Timestamp
					.valueOf(computer.getIntroduced()
							.atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(3, computer.getDiscontinued()!=null?Timestamp
					.valueOf(computer.getDiscontinued()
							.atTime(LocalTime.MIDNIGHT)):null );

			if(computer.getCompany()!=null) {
				prepare.setInt(4,computer.getCompany().getId());
			} else {
				prepare.setNull(4,java.sql.Types.BIGINT);
			}
			prepare.executeUpdate();

			ResultSet rst = prepare.getGeneratedKeys();
			if(rst.first()) {
				int auto_id = rst.getInt(1);
				computer.setId(auto_id);
				rst.close();
			}
			isCreated = true;
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "create " + CLASS_NAME + e.getMessage());
			isCreated = false;
		}
		return isCreated;
	}


	public boolean delete(String ids)  {
		boolean isDeleted = false;
		String[] listIds = ids.split(",");

		String delete = DELETE_MULTI_COMPUTERS;

		for(int i=0; i<listIds.length-1; i++) {
			delete += " ?, ";
		}
		delete += "? )";

		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(delete);
				) {
			for(int i=1;i<=listIds.length;i++) {
				prepare.setInt(i, Integer.parseInt(listIds[i-1]));
			}
			prepare.executeUpdate();
			isDeleted = true;

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "delete " + CLASS_NAME + e.getMessage());
			isDeleted = false;
		}
		return isDeleted;
	}

	public boolean deleteComputerFromConsole(int id) {
		boolean isDeleted = false;
		try(Connection connect = DataSource.getConnection();
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
			logger.error(SQL_EXCEPTION + "delete " + CLASS_NAME + e.getMessage());
			isDeleted = false;
		}
		return isDeleted;
	}

	public boolean update(Computer computer) {
		boolean isUpdated = false;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(UPDATE_COMPUTER);
				) {
			prepare.setString(1, computer.getName());
			prepare.setTimestamp(2, computer.getIntroduced()!=null?Timestamp
					.valueOf(computer.getIntroduced()
							.atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(3, computer.getDiscontinued()!=null?Timestamp
					.valueOf(computer.getDiscontinued()
							.atTime(LocalTime.MIDNIGHT)):null );
			if(computer.getCompany()!=null) {
				prepare.setInt(4,computer.getCompany().getId());
			} else {
				prepare.setNull(4,java.sql.Types.BIGINT);
			}
			prepare.setInt(5, computer.getId());
			prepare.executeUpdate();
			isUpdated = true;

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "update " + CLASS_NAME + e.getMessage());
			isUpdated = false;
		}
		return isUpdated;
	}

	public Optional<Computer> findById(int id) {
		Optional<Computer> computer = Optional.empty();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ONE_COMPUTER);
				) {
			prepare.setInt(1,id);
			ResultSet rst = prepare.executeQuery();
			if(rst.first()) {
				computer = pcMapperInstance.getComputer(rst);
				rst.close();
			}
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "find " + CLASS_NAME + e.getMessage());
		}
		return computer;
	}

	public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy) {
		List<Computer> computers = new ArrayList<>();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_COMPUTERS_BY_NAME+orderBy+FIND_PAGE);
				) {
			prepare.setString(1, '%' + name + '%');
			prepare.setInt(2, (noPage-1)*nbLine);
			prepare.setInt(3, nbLine);
			ResultSet rst = prepare.executeQuery();

			while (rst.next()) {
				Computer computer = pcMapperInstance.getComputer(rst).get();
				computers.add(computer);
			}

			if(rst!=null) {
				rst.close();
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "findByName " + CLASS_NAME + e.getMessage());
		}
		return computers;
	}

	public List<Computer> getList() {
		List<Computer> computers = new ArrayList<>();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPUTERS);
				ResultSet rst = prepare.executeQuery();
				) {

			while (rst.next()) {
				Computer computer = pcMapperInstance.getComputer(rst).get();
				computers.add(computer);
			}
		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "getList " + CLASS_NAME + e.getMessage());
		}
		return computers;
	}

	public List<Computer> getListPerPage(int noPage, int nbLine, String orderBy) {
		List<Computer> computers = new ArrayList<>();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPUTERS+orderBy+FIND_PAGE);
				) {

			prepare.setInt(1, (noPage-1)*nbLine);
			prepare.setInt(2, nbLine);
			ResultSet rst = prepare.executeQuery();

			while (rst.next()) {
				Computer computer = pcMapperInstance.getComputer(rst).get();
				computers.add(computer);
			}

			if(rst!=null) {
				rst.close();
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "getListPerPage " + CLASS_NAME + e.getMessage());
		}
		return computers;
	}



	public int recordsFoundByName(String name) {
		int records = 0;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(COUNT_COMPUTERS_FOUND_BY_NAME);
				) {

			prepare.setString(1, '%' + name + '%');
			ResultSet rst = prepare.executeQuery();

			if(rst.next()) {
				records = rst.getInt("RECORDS");
			}

			if(rst!=null) {
				rst.close();
			}

		} catch (SQLException e) {
			logger.error(SQL_EXCEPTION + "recordsFoundByName " + CLASS_NAME + e.getMessage());
		}
		return records;
	}

	public int allRecords() {
		int records = 0;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(COUNT_COMPUTERS);
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
}
