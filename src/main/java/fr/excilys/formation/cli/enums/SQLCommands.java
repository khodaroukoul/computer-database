package fr.excilys.formation.cli.enums;


public enum SQLCommands {
	FIND_ALL_COMPUTERS("SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id ORDER BY "),
	FIND_COMPUTER("SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id WHERE cp.id = ?"),
	NEW_COMPUTER("INSERT INTO computer"
			+ " (id, name, introduced, discontinued, company_id)"
			+ " SELECT MAX(id)+1, ?, ?, ?, ? FROM computer"),
	DELETE_COMPUTER("DELETE FROM computer WHERE id = ?"),
	DELETE_MULTI_COMPUTERS("DELETE FROM computer WHERE id IN ( "),
	UPDATE_COMPUTER("UPDATE computer SET name = ?, introduced = ?,"
			+ " discontinued = ?, company_id = ? WHERE id = ?"),
	FIND_COMPUTERS_BY_NAME("SELECT cp.id, cp.name, cp.introduced,"
			+ " cp.discontinued, co.id as coId, co.name AS coName"
			+ " FROM computer AS cp LEFT JOIN company AS co"
			+ " ON cp.company_id = co.id"
			+ " WHERE cp.name LIKE ? " 
			+ " ORDER BY "),
	COUNT_COMPUTERS_FOUND_BY_NAME("SELECT COUNT(cp.id) AS RECORDS FROM computer AS cp"
			+ "  WHERE cp.name LIKE ?;"),
	COUNT_COMPUTERS("SELECT COUNT(id) AS RECORDS FROM computer;"),

	FIND_ALL_COMPANIES("SELECT id, name FROM company"),
	COUNT_COMPANIES("SELECT COUNT(id) AS RECORDS FROM company;"),
	DELETE_COMPANY("DELETE FROM company WHERE id = ?"),
	DELETE_COMPUTERS_BY_ID_COMPANY("DELETE FROM computer WHERE company_id = ?"),
	FIND_COMPANY("SELECT id, name FROM company WHERE id=?"),
	FIND_PAGE(" LIMIT ?, ?");

	private String sqlCommands;
	 
	SQLCommands(String sqlCommands) {
        this.sqlCommands = sqlCommands;
    }
 
    public String getSqlCommands() {
        return sqlCommands;
    }

}
