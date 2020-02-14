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

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.jdbc.ConnectionMySQL;

public class ComputerDAO {
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
						"INSERT INTO computer (name, introduced, discontinued, company_id)"+
						"VALUES( ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
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
			prepare.setInt(4, obj.getCompany().getId());
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
						executeQuery("SELECT cp.id, cp.name, cp.introduced, cp.discontinued, co.id as coId, co.name AS coName"
								+ " FROM computer AS cp LEFT JOIN company AS co ON cp.company_id = co.id"
								+ " where cp.id="+id);
				) {

			rst.first();
			company = new Company.CompanyBuilder().setId(rst.getInt("coId")).setName(rst.getString("coName")).build();			
			computer = new Computer.ComputerBuilder(rst.getString("name"))
					.setIntroduced(rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced").toLocalDateTime().toLocalDate():null)
					.setDiscontinued(rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null)
					.setCompany(company).build();
			computer.setId(rst.getInt("id"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public List<Computer> getList() {

		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement("SELECT cp.id, cp.name, cp.introduced, cp.discontinued, co.name AS coName"
						+ " FROM computer AS cp LEFT JOIN company AS co ON company_id=co.id");
				ResultSet rst = prepare.executeQuery();
				) {

			while (rst.next()) {
				company = new Company.CompanyBuilder().setName(rst.getString("coName")).build();
				computer = new Computer.ComputerBuilder(rst.getString("name"))
						.setIntroduced(rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced").toLocalDateTime().toLocalDate():null)
						.setDiscontinued(rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null)
						.setCompany(company).build();
				computer.setId(rst.getInt("id"));
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
				PreparedStatement prepare = connect.prepareStatement("SELECT cp.id, cp.name, cp.introduced, cp.discontinued, co.name AS coName "
						+ "FROM computer AS cp LEFT JOIN company AS co ON company_id=co.id LIMIT ?, ?");
				) {

			prepare.setInt(1, (noPage-1)*nbLine);
			prepare.setInt(2, nbLine);
			rst = prepare.executeQuery();
			while (rst.next()) {
				company = new Company.CompanyBuilder().setName(rst.getString("coName")).build();			
				computer = new Computer.ComputerBuilder(rst.getString("name"))
						.setIntroduced(rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced").toLocalDateTime().toLocalDate():null)
						.setDiscontinued(rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null)
						.setCompany(company).build();
				computer.setId(rst.getInt("id"));
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
