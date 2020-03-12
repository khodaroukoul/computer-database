package fr.excilys.formation.cdb.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.excilys.formation.cdb.enums.SQLCommands;
import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.models.Computer;

@Repository
public final class ComputerDAO {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	ComputerMapper computerMapper = new ComputerMapper();

	public ComputerDAO(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public void create(Computer computer) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("computerName", computer.getName())
				.addValue("introduced", computer.getIntroduced()).addValue("discontinued", computer.getDiscontinued());

		addValueCompanyId(computer, mapParam);

		namedParameterJdbcTemplate.update(SQLCommands.NEW_COMPUTER.getSqlCommands(), mapParam);
	}

	public void delete(String ids) {
		Pattern pattern = Pattern.compile(",");
		List<Integer> idList = pattern.splitAsStream(ids).map(Integer::valueOf).collect(Collectors.toList());
		Map<String, List<Integer>> namedParameters = Collections.singletonMap("computerIds", idList);

		namedParameterJdbcTemplate.update(SQLCommands.DELETE_COMPUTERS.getSqlCommands(), namedParameters);
	}

	public boolean deleteFromConsole(int computerId) {
		boolean isDeleted = false;

		if (!findById(computerId).isEmpty()) {
			MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("computerId", computerId);

			namedParameterJdbcTemplate.update(SQLCommands.DELETE_COMPUTER.getSqlCommands(), mapParam);
			isDeleted = true;
		}

		return isDeleted;
	}

	public void update(Computer computer) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("computerId", computer.getId())
				.addValue("computerName", computer.getName()).addValue("introduced", computer.getIntroduced())
				.addValue("discontinued", computer.getDiscontinued());

		addValueCompanyId(computer, mapParam);

		namedParameterJdbcTemplate.update(SQLCommands.UPDATE_COMPUTER.getSqlCommands(), mapParam);
	}

	public List<Computer> findById(int computerId) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("computerId", computerId);

		List<Computer> computer = namedParameterJdbcTemplate.query(SQLCommands.FIND_COMPUTER.getSqlCommands(),
				mapParam, computerMapper);

		return computer;
	}

	public List<Computer> findByName(String name, int noPage, int nbLine, String orderBy) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("computerName", '%' + name + '%')
				.addValue("noPage", (noPage - 1) * nbLine).addValue("nbLine", nbLine).addValue("orderBy", orderBy);
		String sqlCommand = SQLCommands.FIND_COMPUTERS_BY_NAME.getSqlCommands() + SQLCommands.ORDER_BY.getSqlCommands()
				+ orderBy + SQLCommands.FIND_PAGE.getSqlCommands();

		List<Computer> computers = namedParameterJdbcTemplate.query(sqlCommand, mapParam, computerMapper);

		return computers;
	}

	public List<Computer> getList() {
		List<Computer> computers = namedParameterJdbcTemplate.query(SQLCommands.FIND_COMPUTERS.getSqlCommands(),
				computerMapper);
		return computers;
	}

	public List<Computer> getListPerPage(int noPage, int nbLine, String orderBy) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("noPage", (noPage - 1) * nbLine)
				.addValue("nbLine", nbLine);
		String sqlCommand = SQLCommands.FIND_COMPUTERS.getSqlCommands() + SQLCommands.ORDER_BY.getSqlCommands()
				+ orderBy + SQLCommands.FIND_PAGE.getSqlCommands();

		List<Computer> computers = namedParameterJdbcTemplate.query(sqlCommand, mapParam, computerMapper);

		return computers;
	}

	public int countAll() {
		MapSqlParameterSource mapParam = new MapSqlParameterSource();
		return namedParameterJdbcTemplate.queryForObject(SQLCommands.COUNT_COMPUTERS.getSqlCommands(), mapParam,
				Integer.class);
	}

	public int foundByName(String name) {
		MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("computerName", '%' + name + '%');

		int noComputers = namedParameterJdbcTemplate
				.queryForObject(SQLCommands.COUNT_COMPUTERS_FOUND_BY_NAME.getSqlCommands(), mapParam, Integer.class);

		return noComputers;
	}

	private void addValueCompanyId(Computer computer, MapSqlParameterSource mapParam) {
		if (computer.getCompany() != null) {
			mapParam.addValue("companyId", computer.getCompany().getId());
		} else {
			mapParam.addValue("companyId", null);
		}
	}
}
