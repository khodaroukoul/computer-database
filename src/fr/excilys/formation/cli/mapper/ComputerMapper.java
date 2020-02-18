package fr.excilys.formation.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;

public class ComputerMapper {
	private Company company;
	private Computer computer;
	private static volatile ComputerMapper instance = null;
	private ComputerMapper() {
		super();
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
		company = new Company.CompanyBuilder().setName(rst.getString("coName")).build();
		computer = new Computer.ComputerBuilder(rst.getString("name"))
				.setIntroduced(rst.getTimestamp("introduced")!=null?rst.getTimestamp("introduced").toLocalDateTime().toLocalDate():null)
				.setDiscontinued(rst.getTimestamp("discontinued")!=null?rst.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null)
				.setCompany(company).build();
		computer.setId(rst.getInt("id"));
		return Optional.ofNullable(computer);
	}
}
