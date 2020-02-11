package fr.excilys.formation.cli.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.cli.beans.Company;

public class CompanyDAO extends DAO<Company> {
	//list is working as a database
	   List<Company> companies = new ArrayList<Company>();

@Override
public List<Company> getList() {
	try {
		 ResultSet rst = this.connect.createStatement(
				 ResultSet.TYPE_SCROLL_INSENSITIVE,
				 ResultSet.CONCUR_UPDATABLE).executeQuery("Select * From company");
		 while (rst.next()) {
			 Company company = new Company(rst.getInt("id"),rst.getString("name"));
			 companies.add(company);
		 }
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return companies;
}

@Override
public Company create(Company obj) {
	return null;
}


@Override
public void delete(Company obj) {
}

@Override
public Company update(Company obj) {
	return null;
}
 
}
