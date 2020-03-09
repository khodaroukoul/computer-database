package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.enums.SQLCommands;
import fr.excilys.formation.cli.jdbc.DataSource;
import fr.excilys.formation.cli.mapper.ComputerMapper;
import fr.excilys.formation.cli.models.Computer;

public final class ComputerDAO {

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private static final String SQL_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "IN CLASS ComputerDAO. ";

	CommonMethodsDAO commonMethods = new CommonMethodsDAO();

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

	public void create(Computer computer) {
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.NEW_COMPUTER.getSqlCommands(),
						Statement.RETURN_GENERATED_KEYS);
				) {
			preparedComputer(computer, prepare);
			prepare.executeUpdate();
		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "create " + CLASS_NAME + sql.getMessage());
		}
	}

	public void delete(String ids)  {
		String[] idList = ids.split(",");
		String delete = deleteComputersCommand(idList,
				SQLCommands.DELETE_MULTI_COMPUTERS.getSqlCommands());

		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(delete);
				) {
			for(int i=1; i<=idList.length; i++) {
				prepare.setInt(i, Integer.parseInt(idList[i-1]));
			}
			prepare.executeUpdate();

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "delete " + CLASS_NAME + sql.getMessage());
		}
	}

	public boolean deleteFromConsole(int id) {
		boolean isDeleted = false;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepareFind = connect.prepareStatement(
						SQLCommands.FIND_COMPUTER.getSqlCommands());
				PreparedStatement prepareDelete = connect.prepareStatement(
						SQLCommands.DELETE_COMPUTER.getSqlCommands());
				) {

			if(commonMethods.modelPrepareSelect(id, prepareFind).first()) {
				commonMethods.modelPrepareUpdate(id, prepareDelete);
				isDeleted = true;
			}

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "delete " + CLASS_NAME + sql.getMessage());
			isDeleted = false;
		}
		return isDeleted;
	}

	public void update(Computer computer) {
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.UPDATE_COMPUTER.getSqlCommands());
				) {
			preparedComputer(computer, prepare);
			prepare.setInt(5, computer.getId());
			prepare.executeUpdate();

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "update " + CLASS_NAME + sql.getMessage());
		}
	}

	public Optional<Computer> findById(int id) {
		Optional<Computer> computer = Optional.empty();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.FIND_COMPUTER.getSqlCommands());
				ResultSet rst = commonMethods.modelPrepareSelect(id, prepare)
				) {
			if(rst.first()) {
				computer = ComputerMapper.getComputer(rst);
			}
		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "findById " + CLASS_NAME + sql.getMessage());
		}
		return computer;
	}

	public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy) {
		List<Computer> computers = new ArrayList<>();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.FIND_COMPUTERS_BY_NAME.getSqlCommands()+
						orderBy+SQLCommands.FIND_PAGE.getSqlCommands());
				ResultSet rst = findByNamePerPageResultSet(name, noPage, nbLine, prepare);
				) {

			while (rst.next()) {
				Computer computer = ComputerMapper.getComputer(rst).get();
				computers.add(computer);
			}

			if(rst!=null) {
				rst.close();
			}

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "findByName " + CLASS_NAME + sql.getMessage());
		}
		return computers;
	}

	public List<Computer> getList() {
		List<Computer> computers = new ArrayList<>();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.FIND_ALL_COMPUTERS.getSqlCommands());
				ResultSet rst = prepare.executeQuery();
				) {

			while (rst.next()) {
				Computer computer = ComputerMapper.getComputer(rst).get();
				computers.add(computer);
			}
		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "getList " + CLASS_NAME + sql.getMessage());
		}
		return computers;
	}

	public List<Computer> getListPerPage(int noPage, int nbLine, String orderBy) {
		List<Computer> computers = new ArrayList<>();
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.FIND_ALL_COMPUTERS.getSqlCommands()+
						orderBy+SQLCommands.FIND_PAGE.getSqlCommands());
				ResultSet rst = commonMethods.listPerPageResultSet(noPage, nbLine, prepare);
				) {
			
			while (rst.next()) {
				Computer computer = ComputerMapper.getComputer(rst).get();
				computers.add(computer);
			}

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "getListPerPage " + CLASS_NAME + sql.getMessage());
		}
		return computers;
	}
	
	public int countAll() {
		return commonMethods.countAll(SQLCommands.COUNT_COMPUTERS.getSqlCommands());
	}

	public int FoundByName(String name) {
		int records = 0;
		try(Connection connect = DataSource.getConnection();
				PreparedStatement prepare = connect.prepareStatement(
						SQLCommands.COUNT_COMPUTERS_FOUND_BY_NAME.getSqlCommands());
				ResultSet rst = findByNameResultSet(name, prepare);
				) {
			if(rst.next()) {
				records = rst.getInt("RECORDS");
			}

		} catch (SQLException sql) {
			logger.error(SQL_EXCEPTION + "computersFoundByName " + CLASS_NAME + sql.getMessage());
		}
		return records;
	}

	private void preparedComputer(Computer computer, PreparedStatement prepare) throws SQLException {
		prepare.setString(1, computer.getName());
		prepare.setTimestamp(2, ComputerMapper.localDatetoDbDate(computer.getIntroduced()));
		prepare.setTimestamp(3, ComputerMapper.localDatetoDbDate(computer.getDiscontinued()));
		preparedCompany(computer, prepare);
	}

	private void preparedCompany(Computer computer, PreparedStatement prepare) throws SQLException {
		if(computer.getCompany()!=null) {
			prepare.setInt(4,computer.getCompany().getId());
		} else {
			prepare.setNull(4,java.sql.Types.BIGINT);
		}
	}

	private String deleteComputersCommand(String[] idList, String delete) {
		for(int i=0; i<idList.length-1; i++) {
			delete += " ?, ";
		}
		delete += "? )";
		return delete;
	}
	
	private ResultSet findByNameResultSet(String name, PreparedStatement prepare) throws SQLException {
		prepare.setString(1, '%' + name + '%');
		ResultSet rst = prepare.executeQuery();
		return rst;
	}

	private ResultSet findByNamePerPageResultSet(String name, int noPage, int nbLine, PreparedStatement prepare)
			throws SQLException {
		prepare.setString(1, '%' + name + '%');
		prepare.setInt(2, (noPage-1)*nbLine);
		prepare.setInt(3, nbLine);
		ResultSet rst = prepare.executeQuery();
		return rst;
	}	
}
