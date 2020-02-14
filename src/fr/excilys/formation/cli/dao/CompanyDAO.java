package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.jdbc.ConnectionMySQL;

public final class CompanyDAO{

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
				ResultSet rst = connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT id, name FROM company");
				) {
			while (rst.next()) {
				Company company = new Company.CompanyBuilder().setId(rst.getInt("id")).setName(rst.getString("name")).build();
				companies.add(company);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return companies;
	}

	public List<Company> getListPerPage(int noPage, int nbLine) {
		ResultSet rst = null;
		try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
				PreparedStatement prepare = connect.prepareStatement("SELECT id, name FROM company LIMIT ?, ?");
				) {

			prepare.setInt(1, (noPage-1)*nbLine);
			prepare.setInt(2, nbLine);
			rst = prepare.executeQuery();
			while (rst.next()) {
				Company company = new Company.CompanyBuilder().setId(rst.getInt("id")).setName(rst.getString("name")).build();
				companies.add(company);
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
		return companies;
	}

}
