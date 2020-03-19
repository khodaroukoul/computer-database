package fr.excilys.formation.cdb.dao;

import java.util.List;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import fr.excilys.formation.cdb.mapper.CompanyMapper;
import fr.excilys.formation.cdb.model.Company;

@Repository
public class CompanyDAO{

	private SessionFactory sessionFactory;
	CompanyMapper companyMapper;

	public CompanyDAO(SessionFactory sessionFactory, CompanyMapper companyMapper) {
		this.sessionFactory = sessionFactory;
		this.companyMapper = companyMapper;
	}

	public List<Company> getList() {
		String sqlCommand = SQLCommands.FIND_COMPANIES.getSqlCommands();

		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Company> query = session.createQuery(sqlCommand);

		return query.getResultList();
	}

	public List<Company> findById(int companyId) {
		String sqlCommand = SQLCommands.FIND_COMPANY.getSqlCommands();

		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Company> query = session.createQuery(sqlCommand);
		query.setParameter("companyId", companyId);

		return query.getResultList();
	}

	public void deleteCompany(int companyId) {
		String sqlCommandDeleteComputer = SQLCommands.DELETE_COMPUTERS_BY_ID_COMPANY.getSqlCommands();
		String sqlCommandDeleteCompany = SQLCommands.DELETE_COMPANY.getSqlCommands();

		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery queryComputer = session.createQuery(sqlCommandDeleteComputer);
		queryComputer.setParameter("companyId", companyId);
		TypedQuery queryCompany = session.createQuery(sqlCommandDeleteCompany);
		queryCompany.setParameter("companyId", companyId);

		queryComputer.executeUpdate();
		queryCompany.executeUpdate();
	}
}