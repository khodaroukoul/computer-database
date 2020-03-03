package fr.excilys.formation.cli.service;

import java.util.List;

import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.models.Company;

public class CompanyService {
	private static volatile CompanyService instance = null;

	private CompanyService() {
	}

	public final static CompanyService getInstance() {
		if (CompanyService.instance == null) {
			synchronized(CompanyService.class) {
				if (CompanyService.instance == null) {
					CompanyService.instance = new CompanyService();
				}
			}
		}
		return CompanyService.instance;
	}

	CompanyDAO coDaoInstance = CompanyDAO.getInstance(); 

	public List<Company> getList(){
		return coDaoInstance.getList();
	}

	public List<Company> getListPerPage(int noPage, int nbLine){
		return coDaoInstance.getListPerPage(noPage, nbLine);
	}

}
