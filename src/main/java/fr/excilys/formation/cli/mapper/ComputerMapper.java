package fr.excilys.formation.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.dto.CompanyDTO;
import fr.excilys.formation.cli.dto.ComputerDTO;

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

	public Optional<Computer> getComputer(ResultSet rst) throws SQLException {
		Company company = new Company.CompanyBuilder().setName(rst.getString("coName"))
				.setId(rst.getInt("coId")).build();
		Computer computer = new Computer.ComputerBuilder(rst.getString("name"))
				.setIntroduced(rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced")
						.toLocalDateTime().toLocalDate():null)
				.setDiscontinued(rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued")
						.toLocalDateTime().toLocalDate():null)
				.setCompany(company).build();
		computer.setId(rst.getInt("id"));
		
		return Optional.ofNullable(computer);
	}
	
	public ComputerDTO FromComputerToComputerDTO(Computer computer) {
		CompanyDTO companyDTO = CompanyMapper.getInstance().FromCompanyToCompanyDTO(computer.getCompany());
		ComputerDTO computerDTO = new ComputerDTO(computer.getName(),
				computer.getIntroduced()==null?null:computer.getIntroduced().toString(),
				computer.getDiscontinued()==null?null:computer.getDiscontinued().toString(),companyDTO);
		computerDTO.setId(computer.getId());
		
		return computerDTO;
	}
	
	public Computer fromComputerDTOToComputer(ComputerDTO computerDTO) {		
		Company company = CompanyMapper.getInstance().fromCompanyDTOToCompany(computerDTO.getCompany());
		Computer computer = new Computer.ComputerBuilder(computerDTO.getName())
				.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()))				
				.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()))
				.setCompany(company)
				.build();
		computer.setId(computerDTO.getId());
		
		return computer;
	}
}
