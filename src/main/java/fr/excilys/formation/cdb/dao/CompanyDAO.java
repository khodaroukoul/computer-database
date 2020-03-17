package fr.excilys.formation.cdb.dao;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.formation.cdb.mapper.CompanyMapper;
import fr.excilys.formation.cdb.model.Company;

@Repository
public class CompanyDAO{

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	CompanyMapper companyMapper = new CompanyMapper(); 

	public CompanyDAO(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);		
	}

	public List<Company> getList() {

		List<Company> companies = namedParameterJdbcTemplate.query(SQLCommands.FIND_COMPANIES.getSqlCommands(), 
				companyMapper);

		return companies;
	}

	public List<Company> findById(int companyId) { 
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("companyId", companyId);

		List<Company> company = namedParameterJdbcTemplate.query(SQLCommands.FIND_COMPANY.getSqlCommands(), 
				mapParam, companyMapper);

		return company;
	}

	@Transactional
	public void deleteCompany(int companyId) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("companyId", companyId);

		namedParameterJdbcTemplate.update(SQLCommands.DELETE_COMPUTERS_BY_ID_COMPANY.getSqlCommands(), mapParam);
		namedParameterJdbcTemplate.update(SQLCommands.DELETE_COMPANY.getSqlCommands(), mapParam);
	}
}
