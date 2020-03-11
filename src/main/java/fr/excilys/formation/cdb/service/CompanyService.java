package fr.excilys.formation.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.formation.cdb.dao.CompanyDAO;
import fr.excilys.formation.cdb.models.Company;
import fr.excilys.formation.cdb.validator.Validator;

@Service
public class CompanyService {
	
	CompanyDAO coDao; 
	
	@Autowired
	public CompanyService(CompanyDAO coDao) {
		this.coDao = coDao;
	}

	public List<Company> getList(){
		return coDao.getList();
	}

	public List<Company> getListPerPage(int noPage, int nbLine){
		return coDao.getListPerPage(noPage, nbLine);
	}

	public int countAll() {
		return coDao.countAll();		
	}
	
	public Optional<Company> findById(int id) {
		return coDao.findById(id);
	}

	public void deleteCompany(String idCo) {
		if (idCo != null && !Validator.isNotValidId(idCo) && !Validator.isNotValidCompany(idCo)) {
			int idCompany = Integer.parseInt(idCo);
			coDao.deleteCompany(idCompany);	
		}	
	}
}
