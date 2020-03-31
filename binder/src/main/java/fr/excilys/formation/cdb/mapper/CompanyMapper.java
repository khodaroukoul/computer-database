package fr.excilys.formation.cdb.mapper;

import fr.excilys.formation.cdb.dto.CompanyDTO;
import fr.excilys.formation.cdb.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyDTO fromCompanyToCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());

        return companyDTO;
    }

    public Company fromCompanyDTOToCompany(CompanyDTO companyDTO) {
        return new Company.Builder()
                .setId(companyDTO.getId())
                .setName(companyDTO.getName()).build();
    }
}