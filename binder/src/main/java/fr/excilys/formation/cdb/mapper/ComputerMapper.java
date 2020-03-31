package fr.excilys.formation.cdb.mapper;

import fr.excilys.formation.cdb.dto.CompanyDTO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.model.Computer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ComputerMapper {
    private final CompanyMapper companyMapper;

    public ComputerMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }


    public ComputerDTO fromComputerToComputerDTO(Computer computer) {
        CompanyDTO companyDTO = (computer.getCompany() != null) ?
                companyMapper.fromCompanyToCompanyDTO(computer.getCompany()) : null;

        ComputerDTO computerDTO = new ComputerDTO(computer.getName(),
                localDateToDto(computer.getIntroduced()),
                localDateToDto(computer.getDiscontinued()),
                companyDTO);
        computerDTO.setId(computer.getId());

        return computerDTO;
    }

    public Computer fromComputerDTOToComputer(ComputerDTO computerDTO) {
        Company company = (computerDTO.getCompany() != null) ?
                companyMapper.fromCompanyDTOToCompany(computerDTO.getCompany()) : null;

        Computer computer = new Computer.Builder(computerDTO.getName())
                .setIntroduced(dtoToLocalDate(computerDTO.getIntroduced()))
                .setDiscontinued(dtoToLocalDate(computerDTO.getDiscontinued()))
                .setCompany(company)
                .build();

        if (computerDTO.getId() > 0) {
            computer.setId(computerDTO.getId());
        }

        return computer;
    }

    public LocalDate dtoToLocalDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return date.isBlank() ? null : LocalDate.parse(date, dtf);
    }

    public String localDateToDto(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }
}