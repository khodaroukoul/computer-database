package fr.excilys.formation.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import fr.excilys.formation.cli.dto.CompanyDTO;
import fr.excilys.formation.cli.models.Company;

public class CompanyMapper {

	public static Optional<Company> getCompany(ResultSet res) throws SQLException {
		Company company = new Company.Builder().setName(res.getString("name"))
				.setId(res.getInt("id")).build();
		return Optional.ofNullable(company);
	}

	public static CompanyDTO FromCompanyToCompanyDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		return companyDTO;
	}

	public static Company fromCompanyDTOToCompany(CompanyDTO companyDTO) {
		Company company = new Company.Builder().setId(companyDTO.getId()).build();
		return company;
	}
}