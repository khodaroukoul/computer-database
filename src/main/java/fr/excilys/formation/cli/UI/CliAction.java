package fr.excilys.formation.cli.UI;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.dao.ComputerDAO;
import fr.excilys.formation.cli.models.Company;
import fr.excilys.formation.cli.models.Computer;

public class CliAction {
	private static Logger logger = LoggerFactory.getLogger(CliAction.class);
	
	private static final String SHOW_COMPANIES = "ERROR IN SHOW COMPANIES";
	private static final String SHOW_COMPUTERS = "ERROR IN SHOW COMPUTERS";
	private static final String SHOW_COMPUTERS_PER_PAGE = "ERROR IN SHOW COMPUTERS PER PAGE";
	private static final String SHOW_COMPUTER = "ERROR IN FIND A COMPUTER";
	private static final String CREATE_COMPUTER = "ERROR IN CREATE A COMPUTER";
	private static final String UPDATE_COMPUTER = "ERROR IN UPDATE A COMPUTER";
	private static final String DELETE_COMPUTER = "ERROR IN DELETE A COMPUTER";
	private static final String COMPUTER_NOT_FOUND = "Computer is not found";

	public static void showCompanies(Scanner scanner) {
		List<Company> companies = CompanyDAO.getInstance().getList();
		companies.forEach(System.out::println);

		System.out.println("----------------------------------------");
		System.out.println("****************************************");
		System.out.println("Press enter to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			logger.error(SHOW_COMPANIES);
		}

	}

	public static void showComputers(Scanner scanner) {
		List<Computer> computers = ComputerDAO.getInstance().getList();
		computers.forEach(System.out::println);

		System.out.println("----------------------------------------");
		System.out.println("****************************************");
		System.out.println("Press enter to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			logger.error(SHOW_COMPUTERS);
		}
	}
	
	public static void showComputersPerPage(Scanner scanner) {
		System.out.println("Please select page number:");
		int pageNb = scanner.nextInt();
		System.out.println("Please select number of computers to show:");
		int lineNb = scanner.nextInt();
		// Displaying lineNb computers from page pageNb
		List<Computer> computersPerPage = ComputerDAO.getInstance().getListPerPage(pageNb,lineNb);
		computersPerPage.forEach(System.out::println);
		System.out.println("----------------------------------------");
		System.out.println("****************************************");
		System.out.println("Press enter to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			logger.error(SHOW_COMPUTERS_PER_PAGE);
		}
	}

	public static void findComputer(Scanner scanner) {
		System.out.println("Please select computer id to display:");
		int computerId = scanner.nextInt();
		if(ComputerDAO.getInstance().find(computerId).isPresent()) {
			Computer computer = ComputerDAO.getInstance().find(computerId).get();
			System.out.println(computer.toString());
		} else {
			logger.info(COMPUTER_NOT_FOUND);
		}
		System.out.println("Press enter to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			logger.error(SHOW_COMPUTER);
		}		
	}

	public static void createComputer(Scanner scanner) {
		System.out.println("Please enter the following info to create a computer:");
		System.out.println("Please enter computer name:");
		String computerName = scanner.next();
		Computer computer = new Computer.Builder(computerName).build();
		System.out.println("Please enter computer introduced date (yyyy-MM-dd) or null for nothing:");
		String computerIntro = scanner.next();
		computer.setIntroduced(computerIntro.equals("null")?null:LocalDate.parse(computerIntro));
		System.out.println("Please enter computer discontinued date (yyyy-MM-dd) or null for nothing:");
		String computerDisc = scanner.next();
		computer.setDiscontinued(computerDisc.equals("null")?null:LocalDate.parse(computerDisc));
		System.out.println("Please enter company id");
		int companyId = scanner.nextInt();
		Company company = new Company.Builder().setId(companyId).build();
		computer.setCompany(company);
		ComputerDAO.getInstance().create(computer);
		computer = ComputerDAO.getInstance().find(computer.getId()).get();
		System.out.println(computer.toString() + " is created");
		System.out.println("Press enter to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			logger.error(CREATE_COMPUTER);
		}		
	}

	public static void updateComputer(Scanner scanner) {
		System.out.println("Please enter computer id to update:");
		int computerId = scanner.nextInt();
		if(ComputerDAO.getInstance().find(computerId).isPresent()) {
			System.out.println("Computer you want to update is:");
			Computer computer = ComputerDAO.getInstance().find(computerId).get();
			System.out.println(computer.toString());

			System.out.println("To change the computer name press 1 or 0 to skip");
			if(scanner.nextInt()==1) {
				System.out.println("Please enter computer name to update:");
				String computerName = scanner.next();
				computer.setName(computerName);
			}

			System.out.println("To change the computer introduced date press 2 or 0 to skip");
			if(scanner.nextInt()==2) {
				System.out.println("Please enter the computer introduced date (yyyy-mm-dd) or null for nothing:");
				String computerIntro = scanner.next();
				computer.setIntroduced(computerIntro.equals("null")?null:LocalDate.parse(computerIntro));
			}


			System.out.println("To change the computer discontinued date press 3 or 0 to skip");
			if(scanner.nextInt()==3) {
				System.out.println("Please enter the computer introduced date (yyyy-mm-dd) or null for nothing:");
				String computerDisc = scanner.next();
				computer.setDiscontinued(computerDisc.equals("null")?null:LocalDate.parse(computerDisc));
			}

			System.out.println("To change the company id press 4 or 0 to skip");
			if(scanner.nextInt()==4) {
				System.out.println("Please enter the company id:");
				int companyId = scanner.nextInt();
				computer.setCompany(new Company.Builder().setId(companyId).build());
			}						

			ComputerDAO.getInstance().update(computer);
			computer = ComputerDAO.getInstance().find(computerId).get();
			System.out.println(computer.toString());

		} else {
			logger.info(COMPUTER_NOT_FOUND);
		}
		System.out.println("Press enter to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			logger.error(UPDATE_COMPUTER);
		}
	}

	public static void deleteComputer(Scanner scanner) {
		System.out.println("Please enter computer id to delete:");
		int computerId = scanner.nextInt();
		if(ComputerDAO.getInstance().find(computerId).isPresent()) {
			Computer computer = ComputerDAO.getInstance().find(computerId).get();
			ComputerDAO.getInstance().delete(computerId);
			System.out.println(computer.toString());
		} else {
			System.out.println("Computer is not found");
		}
		System.out.println("Press enter to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			logger.error(DELETE_COMPUTER);
		}
	}
}