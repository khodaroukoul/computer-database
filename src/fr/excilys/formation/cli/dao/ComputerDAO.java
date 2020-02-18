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

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
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
			+ " ON cp.company_id = co.id where cp.id =";
	private static final String NEW_COMPUTER = "INSERT INTO computer"
			+ " (id, name, introduced, discontinued, company_id)"
			+ " SELECT MAX(id)+1, ?, ?, ?, ? FROM computer";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?,"
			+ "discontinued = ?, company_id = ? WHERE id = ?";
	private static final String FIND_PAGE = " LIMIT ?, ?";

	Company company;
	Computer computer;

	private static volatile ComputerDAO instance = null;
	private ComputerDAO() {
		super();
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

	public Computer create(Computer obj) {
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(NEW_COMPUTER,Statement.RETURN_GENERATED_KEYS);
				) {
			prepare.setString(1, obj.getName());
			prepare.setTimestamp(2, obj.getIntroduced()!=null?Timestamp.valueOf(obj.getIntroduced().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(3, obj.getDiscontinued()!=null?Timestamp.valueOf(obj.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setInt(4,obj.getCompany().getId());
			prepare.executeUpdate();

			ResultSet rst = prepare.getGeneratedKeys();
			rst.first();
			int auto_id = rst.getInt(1);
			obj.setId(auto_id);
			rst.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public boolean delete(int id) {
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(DELETE_COMPUTER);
				) {
			prepare.setInt(1, id);
			prepare.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public Computer update(Computer obj) {
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(UPDATE_COMPUTER);
				) {

			prepare.setString(1, obj.getName());
			prepare.setTimestamp(2, obj.getIntroduced()!=null?Timestamp.valueOf(obj.getIntroduced().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(3, obj.getDiscontinued()!=null?Timestamp.valueOf(obj.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setInt(4, obj.getCompany().getId());
			prepare.setInt(5, obj.getId());
			prepare.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public Optional<Computer> find(int id) {
		Optional<Computer> computer =  Optional.empty();
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ONE_COMPUTER + id);
				ResultSet rst = prepare.executeQuery();
				) {
			if(rst.first()) {
				computer = ComputerMapper.getInstance().getComputer(rst);	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public Optional<List<Computer>> getList() {
		List<Computer> computers = new ArrayList<Computer>();
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement(FIND_ALL_COMPUTERS);
				ResultSet rst = prepare.executeQuery();
				) {

			while (rst.next()) {
				computer = ComputerMapper.getInstance().getComputer(rst).get();
				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(computers);
	}

	public Optional<List<Computer>> getListPerPage(int noPage, int nbLine) {
		List<Computer> computers = new ArrayList<Computer>();
		ResultSet rst = null;
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
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
			e.printStackTrace();
		}finally {
			try {
				rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Optional.ofNullable(computers);
	}

}
