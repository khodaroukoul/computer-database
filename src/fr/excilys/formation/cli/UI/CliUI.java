package fr.excilys.formation.cli.UI;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.dao.ComputerDAO;

public class CliUI {

	public static void main(String[] args) {
		int cli = 0;
		boolean stayCli = true;
		Computer computer = null;
		Company company = null;

		Scanner scanner = new Scanner(System. in);

		while(stayCli) {
			System.out.println("Please choose your choice");
			System.out.println("///////////////////////////");
			System.out.println(" Company list ----> 1");
			System.out.println("---------------------------");
			System.out.println(" Computer list ----> 2");
			System.out.println("---------------------------");
			System.out.println(" Computer list per page ----> 3");
			System.out.println("---------------------------");
			System.out.println(" Show a computer ----> 4");
			System.out.println("---------------------------");
			System.out.println(" Create a computer ----> 5");
			System.out.println("---------------------------");
			System.out.println(" Update a computer ----> 6");
			System.out.println("---------------------------");
			System.out.println(" Delete a computer ----> 7");
			System.out.println("---------------------------");
			System.out.println(" Exit ----> 8");
			System.out.println("///////////////////////////");
			cli = scanner.nextInt();

			switch(cli) {

			case 1:
				List<Company> companies = CompanyDAO.getInstance().getList();
				for(Company co : companies) {
					System.out.println(co.toString());			
				}

				System.out.println("----------------------------------------");
				System.out.println("****************************************");
				System.out.println("Press enter to continue");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case 2:
				List<Computer> computers = ComputerDAO.getInstance().getList();
				for(Computer cp : computers) {
					System.out.println(cp.toString());			
				}

				System.out.println("----------------------------------------");
				System.out.println("****************************************");
				System.out.println("Press enter to continue");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case 3:
				System.out.println("Please select page number:");
				int pageNb = scanner.nextInt();
				System.out.println("Please select number of computers to show:");
				int lineNb = scanner.nextInt();
				// Displaying lineNb computers in page pageNb
				List<Computer> computersPerPage = ComputerDAO.getInstance().getListPerPage(pageNb,lineNb);
				for(Computer cp : computersPerPage) {
					System.out.println(cp.toString());
				}
				System.out.println("----------------------------------------");
				System.out.println("****************************************");
				System.out.println("Press enter to continue");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case 4:
				System.out.println("Please select computer id to display:");
				int computerId = scanner.nextInt();
				computer = ComputerDAO.getInstance().find(computerId);
				System.out.println(computer.toString());
				System.out.println("Press enter to continue");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.println("Please enter the following info to create a computer:");
				System.out.println("Please enter computer name:");
				String computerName = scanner.next();
				computer = new Computer.ComputerBuilder(computerName).build();
				System.out.println("Please enter computer introduced date (yyyy-MM-dd) or null for nothing:");
				String computerIntro = scanner.next();
				computer.setIntroduced(computerIntro.equals("null")?null:LocalDate.parse(computerIntro));
				System.out.println("Please enter computer discontinued date (yyyy-MM-dd) or null for nothing:");
				String computerDisc = scanner.next();
				computer.setDiscontinued(computerDisc.equals("null")?null:LocalDate.parse(computerDisc));
				System.out.println("Please enter company id");
				int companyId = scanner.nextInt();
				company = new Company.CompanyBuilder().setId(companyId).build();
				computer.setCompany(company);
				ComputerDAO.getInstance().create(computer);
				computer = ComputerDAO.getInstance().find(computer.getId());
				System.out.println(computer.toString() + " is created");
				System.out.println("Press enter to continue");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				System.out.println("Please enter computer id to update:");
				computerId = scanner.nextInt();
				System.out.println("Computer you want to update is:");
				computer = ComputerDAO.getInstance().find(computerId);
				System.out.println(computer.toString());

				System.out.println("To change the computer name press 1 or 0 to skip");
				if(scanner.nextInt()==1) {
					System.out.println("Please enter computer name to update:");
					computerName = scanner.next();
					computer.setName(computerName);
				}

				System.out.println("To change the computer introduced date press 2 or 0 to skip");
				if(scanner.nextInt()==2) {
					System.out.println("Please enter the computer introduced date (yyyy-mm-dd) or null for nothing:");
					computerIntro = scanner.next();
					computer.setIntroduced(computerIntro.equals("null")?null:LocalDate.parse(computerIntro));
				}


				System.out.println("To change the computer discontinued date press 3 or 0 to skip");
				if(scanner.nextInt()==3) {
					System.out.println("Please enter the computer introduced date (yyyy-mm-dd) or null for nothing:");
					computerDisc = scanner.next();
					computer.setDiscontinued(computerDisc.equals("null")?null:LocalDate.parse(computerDisc));
				}

				System.out.println("To change the company id press 4 or 0 to skip");
				if(scanner.nextInt()==4) {
					System.out.println("Please enter the company id:");
					companyId = scanner.nextInt();
					computer.setCompany(new Company.CompanyBuilder().setId(companyId).build());
				}						

				ComputerDAO.getInstance().update(computer);
				computer = ComputerDAO.getInstance().find(computerId);
				System.out.println(computer.toString());
				System.out.println("Press enter to continue");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 7:
				System.out.println("Please enter computer id to delete:");
				computerId = scanner.nextInt();
				computer = ComputerDAO.getInstance().find(computerId);
				ComputerDAO.getInstance().delete(computerId);
				System.out.println(computer.toString() + " is deleted");
				System.out.println("Press enter to continue");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 8:
				System.out.println("End of CLI");
				stayCli = false;
			default:
				break;
			}
		}
		scanner.close();
	}
}
