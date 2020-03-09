package fr.excilys.formation.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import fr.excilys.formation.cli.dto.CompanyDTO;
import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.models.Company;
import fr.excilys.formation.cli.models.Computer;

public class ComputerMapper {

	public static Optional<Computer> getComputer(ResultSet rst) throws SQLException {
		Company company = new Company.Builder()
				.setName(rst.getString("coName"))
				.setId(rst.getInt("coId")).build();
		Computer computer = new Computer.Builder(rst.getString("name"))
				.setIntroduced(dbDateToLocalDate(rst.getTimestamp("introduced")))
				.setDiscontinued(dbDateToLocalDate(rst.getTimestamp("discontinued")))
				.setCompany(company).build();
		computer.setId(rst.getInt("id"));

		return Optional.ofNullable(computer);
	}

	public static ComputerDTO FromComputerToComputerDTO(Computer computer) {
		CompanyDTO companyDTO = CompanyMapper.FromCompanyToCompanyDTO(computer.getCompany());
		ComputerDTO computerDTO = new ComputerDTO(computer.getName(),
				localDateToDto(computer.getIntroduced()),
				localDateToDto(computer.getDiscontinued()),
				companyDTO);
		computerDTO.setId(computer.getId());

		return computerDTO;
	}

	public static Computer fromComputerDTOToComputer(ComputerDTO computerDTO) {
		
		Company company = (computerDTO.getCompany()!=null) ?
				CompanyMapper.fromCompanyDTOToCompany(computerDTO.getCompany()) : null;
				
		Computer computer = new Computer.Builder(computerDTO.getName())
						.setIntroduced(dtoToLocalDate(computerDTO.getIntroduced()))				
						.setDiscontinued(dtoToLocalDate(computerDTO.getDiscontinued()))
						.setCompany(company)
						.build();

		if (computerDTO.getId()!=0) {
			computer.setId(computerDTO.getId());
		}		

		return computer;
	}
	
	public static LocalDate dtoToLocalDate(String date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.isBlank()?null:LocalDate.parse(date,dtf);
	}
	
	public static String localDateToDto(LocalDate date) {
		return date!=null?date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")):null;
	}
	
	public static LocalDate dbDateToLocalDate(Timestamp date) {
		return date!=null?date.toLocalDateTime().toLocalDate():null;
	}
	
	public static Timestamp localDatetoDbDate(LocalDate date) {
		return date!=null?Timestamp.valueOf(date.atStartOfDay()):null;
	}
}
