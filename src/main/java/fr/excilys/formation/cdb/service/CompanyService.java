package fr.excilys.formation.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.formation.cdb.dao.CompanyDAO;
import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.validator.Validator;

@Service
public class CompanyService {

    CompanyDAO companyDao;

    public CompanyService(CompanyDAO companyDao) {
        this.companyDao = companyDao;
    }

    public List<Company> getList() {
        return companyDao.getList();
    }

    public List<Company> findById(int id) {
        return companyDao.findById(id);
    }

    public boolean deleteCompany(String idCompany) {
        boolean isDeleted = false;
        if (idCompany != null && !Validator.isNotValidId(idCompany) && !Validator.isNotValidCompany(idCompany)) {
            companyDao.deleteCompany(Integer.parseInt(idCompany));
            isDeleted = true;
        }

        return isDeleted;
    }
}
