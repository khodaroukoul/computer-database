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
	CompanyMapper companyMapper;

	public CompanyDAO(DataSource dataSource, CompanyMapper companyMapper) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.companyMapper = companyMapper;
	}

	public List<Company> getList() {
		return namedParameterJdbcTemplate.query(SQLCommands.FIND_COMPANIES.getSqlCommands(), companyMapper);
	}

	public List<Company> findById(int companyId) { 
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("companyId", companyId);

		return namedParameterJdbcTemplate.query(SQLCommands.FIND_COMPANY.getSqlCommands(), mapParam, companyMapper);
	}

	@Transactional
	public void deleteCompany(int companyId) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("companyId", companyId);

		namedParameterJdbcTemplate.update(SQLCommands.DELETE_COMPUTERS_BY_ID_COMPANY.getSqlCommands(), mapParam);
		namedParameterJdbcTemplate.update(SQLCommands.DELETE_COMPANY.getSqlCommands(), mapParam);
	}
}
