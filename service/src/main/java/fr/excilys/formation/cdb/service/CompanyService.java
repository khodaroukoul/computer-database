package fr.excilys.formation.cdb.service;

import fr.excilys.formation.cdb.dao.CompanyDAO;
import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.validator.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    final CompanyDAO companyDao;

    public CompanyService(CompanyDAO companyDao) {
        this.companyDao = companyDao;
    }

    @Transactional
    public List<Company> getList() {
        return companyDao.getList();
    }

    @Transactional
    public Optional<Company> findById(int id) {
        return companyDao.findById(id);
    }

    @Transactional
    public boolean deleteCompany(String idCompany) {
        boolean isDeleted = false;
        if (idCompany != null && !Validator.isNotValidId(idCompany) && !Validator.isNotValidCompany(idCompany)) {
            if (findById(Integer.parseInt(idCompany)).isPresent()) {
                companyDao.deleteCompany(Integer.parseInt(idCompany));
                isDeleted = true;
            }
        }

        return isDeleted;
    }
}
