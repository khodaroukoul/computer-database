package fr.excilys.formation.cdb.dao;


public enum SQLCommands {
    FIND_COMPUTERS("SELECT cp.id, cp.name, cp.introduced,"
            + " cp.discontinued, co.id as coId, co.name AS coName"
            + " FROM computer AS cp LEFT JOIN company AS co"
            + " ON cp.company_id = co.id "),
    FIND_COMPUTER("SELECT cp.id, cp.name, cp.introduced,"
            + " cp.discontinued, co.id as coId, co.name AS coName"
            + " FROM computer AS cp LEFT JOIN company AS co"
            + " ON cp.company_id = co.id WHERE cp.id = :computerId"),
    NEW_COMPUTER("INSERT INTO computer (id, name, introduced, discontinued, company_id)"
            + " SELECT MAX(id)+1, :computerName, :introduced, :discontinued, :companyId FROM computer"),
    DELETE_COMPUTER("DELETE FROM computer WHERE id = :computerId"),
    DELETE_COMPUTERS("DELETE FROM computer WHERE id IN (:computerIds)"),
    UPDATE_COMPUTER("UPDATE computer SET name = :computerName, introduced = :introduced,"
            + " discontinued = :discontinued, company_id = :companyId WHERE id = :computerId"),
    FIND_COMPUTERS_BY_NAME("SELECT cp.id, cp.name, cp.introduced,"
            + " cp.discontinued, co.id as coId, co.name AS coName"
            + " FROM computer AS cp LEFT JOIN company AS co"
            + " ON cp.company_id = co.id WHERE cp.name LIKE :computerName "),
    COUNT_COMPUTERS_FOUND_BY_NAME("SELECT COUNT(cp.id) AS RECORDS FROM computer AS cp"
            + "  WHERE cp.name LIKE :computerName;"),
    COUNT_COMPUTERS("SELECT COUNT(id) AS RECORDS FROM computer;"),

    FIND_COMPANIES("SELECT id, name FROM company"),
    DELETE_COMPANY("DELETE FROM company WHERE id = :companyId;"),
    DELETE_COMPUTERS_BY_ID_COMPANY("DELETE FROM computer WHERE company_id = :companyId"),
    FIND_COMPANY("SELECT id, name FROM company WHERE id = :companyId"),
    FIND_PAGE(" LIMIT :noPage, :nbLine "),
    ORDER_BY(" ORDER BY  ");

    private String sqlCommands;

    SQLCommands(String sqlCommands) {
        this.sqlCommands = sqlCommands;
    }

    public String getSqlCommands() {
        return sqlCommands;
    }

}