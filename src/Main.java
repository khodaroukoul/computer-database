import java.time.LocalDateTime;
import java.util.List;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.dao.ComputerDAO;

public class Main {

	public static void main(String[] args) {
		
		// Displaying company Data
		List<Company> company = CompanyDAO.getInstance().getList();
		for(Company co : company) {
			System.out.println(co.toString());			
		}

		System.out.println("----------------------------------------");
		System.out.println("****************************************");
/*
		// Displaying computer Data
		List<Computer> computers = ComputerDAO.getInstance().getList();
		for(Computer cp : computers) {
			System.out.println(cp.toString());			
		}
		*/
		System.out.println("----------------------------------------");
		System.out.println("****************************************");
		
		// Displaying 10 computer Data in page 2
		List<Computer> computers = ComputerDAO.getInstance().getListPerPage(2,10);
		for(Computer cp : computers) {
			System.out.println(cp.toString());
			}
		System.out.println("----------------------------------------");
		System.out.println("****************************************");
		
		Computer computer = new Computer();
		
		computer.setId(1000);
		computer.setName("ChromeBook");
		computer.setIntroduced(LocalDateTime.now());
		computer.setDiscontinued(null);
		computer.setCompany(new Company(1));
		ComputerDAO.getInstance().delete(1000);
		ComputerDAO.getInstance().create(computer);
		computer.setName("ChromeM");
		ComputerDAO.getInstance().update(computer);
		computer = ComputerDAO.getInstance().find(1000);
		System.out.println(computer.toString());		
	}
}
