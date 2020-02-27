package fr.excilys.formation.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import fr.excilys.formation.cli.dto.CompanyDTO;
import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.models.Company;
import fr.excilys.formation.cli.models.Computer;

public class ComputerMapper {

	private static volatile ComputerMapper instance = null;

	private ComputerMapper() {
	}

	public final static ComputerMapper getInstance() {
		if (ComputerMapper.instance == null) {
			synchronized(ComputerMapper.class) {
				if (ComputerMapper.instance == null) {
					ComputerMapper.instance = new ComputerMapper();
				}
			}
		}
		return ComputerMapper.instance;
	}

	CompanyMapper coMapperInstance = CompanyMapper.getInstance();

	public Optional<Computer> getComputer(ResultSet rst) throws SQLException {
		Company company = new Company.Builder().setName(rst.getString("coName"))
				.setId(rst.getInt("coId")).build();
		Computer computer = new Computer.Builder(rst.getString("name"))
				.setIntroduced(rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced")
						.toLocalDateTime().toLocalDate():null)
				.setDiscontinued(rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued")
						.toLocalDateTime().toLocalDate():null)
				.setCompany(company).build();
		computer.setId(rst.getInt("id"));

		return Optional.ofNullable(computer);
	}

	public ComputerDTO FromComputerToComputerDTO(Computer computer) {
		CompanyDTO companyDTO = coMapperInstance.FromCompanyToCompanyDTO(computer.getCompany());
		ComputerDTO computerDTO = new ComputerDTO(computer.getName(),
				computer.getIntroduced()!=null?computer.getIntroduced().toString():null,
						computer.getDiscontinued()!=null?computer.getDiscontinued().toString():null,companyDTO);
		computerDTO.setId(computer.getId());

		return computerDTO;
	}

	public Computer fromComputerDTOToComputer(ComputerDTO computerDTO) {
		Company company = null;
		if(computerDTO.getCompany()!=null) {
			company = coMapperInstance.fromCompanyDTOToCompany(computerDTO.getCompany());
		}

		Computer computer = new Computer.Builder(computerDTO.getName())
				.setIntroduced(computerDTO.getIntroduced().isBlank()?null:
					LocalDate.parse(computerDTO.getIntroduced()))				
				.setDiscontinued(computerDTO.getDiscontinued().isBlank()?null:
					LocalDate.parse(computerDTO.getDiscontinued()))
				.setCompany(company)
				.build();

		if(computerDTO.getId()!=0) {
			computer.setId(computerDTO.getId());
		}		

		return computer;
	}
}