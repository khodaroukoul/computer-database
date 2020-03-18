package fr.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import fr.excilys.formation.cdb.dto.CompanyDTO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.model.Computer;
import org.springframework.stereotype.Component;

@Component
public class ComputerMapper implements RowMapper<Computer> {
    private CompanyMapper companyMapper;

    public ComputerMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    @Override
    public Computer mapRow(ResultSet rst, int rowNum) throws SQLException {
        Company company = new Company.Builder()
                .setName(rst.getString("coName"))
                .setId(rst.getInt("coId")).build();

        Computer computer = new Computer.Builder(rst.getString("name"))
                .setIntroduced(dbDateToLocalDate(rst.getTimestamp("introduced")))
                .setDiscontinued(dbDateToLocalDate(rst.getTimestamp("discontinued")))
                .setCompany(company).build();
        computer.setId(rst.getInt("id"));

        return computer;
    }

    public ComputerDTO FromComputerToComputerDTO(Computer computer) {
        CompanyDTO companyDTO = companyMapper.FromCompanyToCompanyDTO(computer.getCompany());

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

        if (computerDTO.getId() != 0) {
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

    public LocalDate dbDateToLocalDate(Timestamp date) {
        return date != null ? date.toLocalDateTime().toLocalDate() : null;
    }

    public Timestamp localDatetoDbDate(LocalDate date) {
        return date != null ? Timestamp.valueOf(date.atStartOfDay()) : null;
    }
}
