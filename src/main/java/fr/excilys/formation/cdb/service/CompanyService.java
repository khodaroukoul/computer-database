package fr.excilys.formation.cdb.service;

import java.util.List;
import org.springframework.stereotype.Service;

import fr.excilys.formation.cdb.dao.CompanyDAO;
import fr.excilys.formation.cdb.models.Company;
import fr.excilys.formation.cdb.validator.Validator;

@Service
public class CompanyService {

	CompanyDAO coDao; 

	public CompanyService(CompanyDAO coDao) {
		this.coDao = coDao;
	}

	public List<Company> getList(){
		return coDao.getList();
	}

	public List<Company> findById(int id) { 
		return coDao.findById(id);
	}

	public boolean deleteCompany(String idCo) {
		boolean isDeleted = false;
		if (idCo != null && !Validator.isNotValidId(idCo) && !Validator.isNotValidCompany(idCo)) {
			int idCompany = Integer.parseInt(idCo);
			coDao.deleteCompany(idCompany);	
			isDeleted = true;
		}
		
		return isDeleted;
	}
}
