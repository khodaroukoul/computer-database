package fr.excilys.formation.cdb.dao;

import fr.excilys.formation.cdb.model.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unchecked")
public class CompanyDAO {

    private final SessionFactory sessionFactory;

    public CompanyDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Company> getList() {
        String sqlCommand = SQLCommands.FIND_COMPANIES.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Company> query = session.createQuery(sqlCommand);

        return query.getResultList();
    }

    public Optional<Company> findById(int companyId) {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Company.class, companyId));
    }

    public void deleteCompany(int companyId) {
        String sqlCommandDeleteComputer = SQLCommands.DELETE_COMPUTERS_BY_ID_COMPANY.getSqlCommands();

        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<?> queryComputer = session.createQuery(sqlCommandDeleteComputer);
        queryComputer.setParameter("companyId", companyId);
        queryComputer.executeUpdate();

        Company company = findById(companyId).orElse(null);
        session.delete(company);
    }
}