package fr.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.dto.CompanyDTO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rst, int rowNum) throws SQLException {
        Company company = new Company.Builder().build();
        company.setId(rst.getInt("id"));
        company.setName(rst.getString("name"));

        return company;
    }

    public CompanyDTO fromCompanyToCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());

        return companyDTO;
    }

    public Company fromCompanyDTOToCompany(CompanyDTO companyDTO) {

        return new Company.Builder().setId(companyDTO.getId()).setName(companyDTO.getName()).build();
    }
}