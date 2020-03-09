package fr.excilys.formation.cli.service;

import java.util.List;
import java.util.Optional;

import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.models.Company;
import fr.excilys.formation.cli.validator.Validator;

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

	public int countAll() {
		return coDaoInstance.countAll();		
	}
	
	public Optional<Company> findById(int id) {
		return coDaoInstance.findById(id);
	}

	public void deleteCompany(String idCo) {
		if (idCo != null && !Validator.isNotValidId(idCo) && !Validator.isNotValidCompany(idCo)) {
			int idCompany = Integer.parseInt(idCo);
			coDaoInstance.deleteCompany(idCompany);	
		}	
	}
}
