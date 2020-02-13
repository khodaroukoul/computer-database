package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.jdbc.ConnectionMySQL;

public class ComputerDAO {
	//list is working as a database
	List<Computer> computers = new ArrayList<Computer>();
	Computer computer;
	Company company;

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
				PreparedStatement prepare = connect.prepareStatement(
						"INSERT INTO computer (id, name, introduced, discontinued, company_id)"+
						"VALUES(?, ?, ?, ?, ?)");
				) {


			prepare.setInt(1, obj.getId());
			prepare.setString(2, obj.getName());
			prepare.setTimestamp(3, obj.getIntroduced()!=null?Timestamp.valueOf(obj.getIntroduced().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(4, obj.getDiscontinued()!=null?Timestamp.valueOf(obj.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setInt(5,obj.getCompany().getId());
			prepare.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public boolean delete(int id) {
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement("DELETE FROM computer WHERE id = ?");
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
				PreparedStatement prepare = connect.prepareStatement("UPDATE computer SET name = ?, introduced = ?,"
						+ "discontinued = ?, company_id = ? WHERE id = ?");
				) {

			prepare.setString(1, obj.getName());
			prepare.setTimestamp(2, obj.getIntroduced()!=null?Timestamp.valueOf(obj.getIntroduced().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setTimestamp(3, obj.getDiscontinued()!=null?Timestamp.valueOf(obj.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null );
			prepare.setInt(4,obj.getCompany().getId());
			prepare.setInt(5, obj.getId());
			prepare.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public Computer find(int id) {
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				ResultSet rst = connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE, 
						ResultSet.CONCUR_UPDATABLE).
						executeQuery("Select cp.id, cp.name, cp.introduced, cp.discontinued, co.id as coId, co.name as coName"
								+ " from computer as cp left join company as co on cp.company_id = co.id"
								+ " where cp.id="+id);
				) {

			rst.first();
			company = new Company(rst.getInt("coId"),rst.getString("coName"));
			computer = new Computer(
					rst.getInt("id"), 
					rst.getString("name"),
					rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced").toLocalDateTime().toLocalDate():null,
							rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null,
									company);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public List<Computer> getList() {

		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement("Select * From computer");
				ResultSet rst = prepare.executeQuery();
				) {

			while (rst.next()) {
				company = new Company(rst.getInt("company_id"));
				computer = new Computer(
						rst.getInt("id"), 
						rst.getString("name"),
						rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced").toLocalDateTime().toLocalDate():null,
								rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null,
										company);

				computers.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	public List<Computer> getListPerPage(int noPage, int nbLine) {
		ResultSet rst = null;
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement("Select * From computer limit ?, ?");
				) {

			prepare.setInt(1, (noPage-1)*nbLine);
			prepare.setInt(2, nbLine);
			rst = prepare.executeQuery();
			while (rst.next()) {
				company = new Company(rst.getInt("company_id"));
				computer = new Computer(
						rst.getInt("id"), 
						rst.getString("name"),
						rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced").toLocalDateTime().toLocalDate():null,
								rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null,
										company);

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
		return computers;
	}

}
