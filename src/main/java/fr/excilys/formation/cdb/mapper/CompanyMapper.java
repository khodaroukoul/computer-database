package fr.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.excilys.formation.cdb.dto.CompanyDTO;
import fr.excilys.formation.cdb.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements RowMapper<Company> {

    public CompanyDTO FromCompanyToCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());

        return companyDTO;
    }

    public Company fromCompanyDTOToCompany(CompanyDTO companyDTO) {
        Company company = new Company.Builder().setId(companyDTO.getId()).build();

        return company;
    }

    @Override
    public Company mapRow(ResultSet rst, int rowNum) throws SQLException {
        Company company = new Company.Builder().build();
        company.setId(rst.getInt("id"));
        company.setName(rst.getString("name"));

        return company;
    }
}