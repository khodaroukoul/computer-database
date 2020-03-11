package fr.excilys.formation.cdb.ui;
import java.util.Scanner;

import fr.excilys.formation.cdb.ui.CliAction;

public class CliUi {

	public enum CLI {
		COMPANY_LIST(1),
		COMPUTER_LIST(2),
		COMPUTER_LIST_PER_PAGE(3),
		SHOW_COMPUTER(4),
		CREATE_COMPUTER(5),
		UPDATE_COMPUTER(6),
		DELETE_COMPUTER(7),
		EXIT_CLI(8);

		private int cliChoice;

		CLI(int cliChoice) {
			this.cliChoice = cliChoice;
		}

		public int getCliChoice() {
			return cliChoice;
		}
	}

	public static void main(String[] args) {
		int cli = 0;
		boolean stayCli = true;
		Scanner scanner = new Scanner(System.in);

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

			switch(CLI.values()[cli-1]) {

			case COMPANY_LIST:
				CliAction.showCompanies(scanner);
				break;

			case COMPUTER_LIST:
				CliAction.showComputers(scanner);
				break;

			case COMPUTER_LIST_PER_PAGE:
				CliAction.showComputersPerPage(scanner);
				break;

			case SHOW_COMPUTER:
				CliAction.findComputer(scanner);
				break;
			case CREATE_COMPUTER:
				CliAction.createComputer(scanner);
				break;
			case UPDATE_COMPUTER:
				CliAction.updateComputer(scanner);
				break;
			case DELETE_COMPUTER:
				CliAction.deleteComputer(scanner);
				break;
			case EXIT_CLI:
				System.out.println("End of CLI");
				stayCli = false;
			default:
				break;
			}
		}
		scanner.close();
	}
}
