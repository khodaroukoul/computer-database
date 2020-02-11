package fr.excilys.formation.cli.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.cli.beans.Computer;

public class ComputerDAO extends DAO<Computer> {
//list is working as a database
List<Computer> computers = new ArrayList<Computer>();
PreparedStatement prepare;

@Override
public List<Computer> getList() {
	 try {
		 ResultSet rst = this.connect.createStatement(
			       	ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_UPDATABLE).executeQuery("Select * From computer");
		 while (rst.next()) {
			 Computer computer = new Computer(
					 rst.getInt("id"), 
					 rst.getString("name"),
					 rst.getDate("introduced"),
					 rst.getDate("discontinued"),
					 rst.getInt("company_id"));
			 
			 computers.add(computer);
		    }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return computers;
}


public Computer create(Computer obj) {
	try {
		prepare = this.connect.prepareStatement(
				"INSERT INTO computer (id, name, introduced, discontinued, company_id)"+
		                          			"VALUES(?, ?, ?, ?, ?)");

		prepare.setInt(1, obj.getId());
		prepare.setString(2, obj.getName());
		prepare.setDate(3,(Date) obj.getIntroduced());
		prepare.setDate(4,(Date) obj.getDiscontinued());
		prepare.setInt(5,obj.getCompany_id());
		prepare.executeUpdate();
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
    return obj;
}

public void delete(Computer obj) {
	try {
		prepare = this.connect.prepareStatement("DELETE FROM computer WHERE id = ?");

		prepare.setInt(1, obj.getId());
		/*prepare.setString(2, obj.getName());
		prepare.setDate(3,(Date) obj.getIntroduced());
		prepare.setDate(4,(Date) obj.getDiscontinued());
		prepare.setInt(5,obj.getCompany_id());*/
		prepare.executeUpdate();

    } catch (SQLException e) {
            e.printStackTrace();
    }
}
 
public Computer update(Computer obj) {
try {
	prepare = this.connect.prepareStatement("UPDATE computer SET name = ?, introduced = ?,"
			+ "discontinued = ?, company_id = ? WHERE id = ?");
	prepare.setInt(5, obj.getId());
	prepare.setString(1, obj.getName());
	prepare.setDate(2,(Date) obj.getIntroduced());
	prepare.setDate(3,(Date) obj.getDiscontinued());
	prepare.setInt(4,obj.getCompany_id());
	prepare.executeUpdate();
	
} catch (SQLException e) {
        e.printStackTrace();
}

return obj;
}
 
}
