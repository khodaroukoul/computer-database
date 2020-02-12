package fr.excilys.formation.cli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.jdbc.ConnectionMySQL;

public final class CompanyDAO extends DAO<Company> {
	//list is working as a database
	   List<Company> companies = new ArrayList<Company>();

	   private static volatile CompanyDAO instance = null;
	   private CompanyDAO() {
	         super();
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
	     
@Override
public Company create(Company obj) {
	return null;
}


@Override
public boolean delete(int id) {
	return false;
}

@Override
public Company update(Company obj) {
	return null;
}

@Override
public Company find(int id) {
	return null;
}

@Override
public List<Company> getList() {
	
	try(Connection connect =  ConnectionMySQL.getInstance().getConnection();
			ResultSet rst = connect.createStatement(
					 ResultSet.TYPE_SCROLL_INSENSITIVE,
					 ResultSet.CONCUR_UPDATABLE).executeQuery("Select * From company");
			) {
		 while (rst.next()) {
			 Company company = new Company(rst.getInt("id"),rst.getString("name"));
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
			PreparedStatement prepare = connect.prepareStatement("Select * From company limit ?, ?");
			 ) {

		 prepare.setInt(1, (noPage-1)*nbLine);
		 prepare.setInt(2, nbLine);
		 rst = prepare.executeQuery();
		 while (rst.next()) {
			Company company = new Company(
					 rst.getInt("id"), 
					 rst.getString("name"));
			 
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
