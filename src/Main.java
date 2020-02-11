import java.util.List;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.dao.ComputerDAO;

public class Main {

	public static void main(String[] args) {
		
		CompanyDAO companyDao = new CompanyDAO();
		// Displaying Data
		List<Company> company = companyDao.getList();
		for(Company comp : company) {
			System.out.println(comp.getName());			
		}
		
		ComputerDAO computerDao = new ComputerDAO();
		Computer computer = new Computer();
		computer.setId(1000);
		computer.setName("ChromeBook");
		computer.setIntroduced(null);
		computer.setDiscontinued(null);
		computer.setCompany_id(1);
		//computerDao.create(computer);
		computer.setName("my chrome");
		computerDao.update(computer);
		//computerDao.delete(computer);
		
		// Displaying Data
		/*List<Computer> computer = computerDao.getList();
		for(Computer comp : computer) {
			System.out.println(comp.getName());			
		}*/
		
	}
}
