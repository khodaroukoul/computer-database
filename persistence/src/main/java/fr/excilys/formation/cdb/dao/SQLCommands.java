package fr.excilys.formation.cdb.dao;

public enum SQLCommands {
    FIND_COMPUTERS("SELECT pc FROM Computer pc LEFT JOIN pc.company co"),
    FIND_COMPUTERS_BY_NAME("SELECT pc FROM Computer pc LEFT JOIN pc.company co WHERE pc.name LIKE :computerName "),
    FIND_COMPANIES("SELECT co FROM Company co"),
    NEW_COMPUTER("INSERT INTO computer (id, name, introduced, discontinued, company_id)"
            + " VALUES (:computerId, :computerName, :introduced, :discontinued, :companyId)"),
    DELETE_COMPUTER("DELETE FROM Computer WHERE id = :computerId"),
    DELETE_COMPUTERS("DELETE FROM Computer WHERE id IN (:computerIds)"),
    DELETE_COMPUTERS_BY_ID_COMPANY("DELETE FROM Computer pc WHERE pc.company.id = :companyId"),
    UPDATE_COMPUTER("UPDATE Computer SET name = :computerName, introduced = :introduced,"
            + " discontinued = :discontinued, company.id = :companyId WHERE id = :computerId"),
    COUNT_COMPUTERS_FOUND_BY_NAME("SELECT COUNT(pc.id) FROM Computer pc WHERE pc.name LIKE :computerName"),
    COUNT_COMPUTERS("SELECT COUNT(pc.id) FROM Computer pc"),
    ORDER_BY(" ORDER BY  "),
    LAST_ID("SELECT MAX(id) FROM computer"),
    ADD_USER("FROM Users WHERE username=:username");

    private final String sqlCommands;

    SQLCommands(String sqlCommands) {
        this.sqlCommands = sqlCommands;
    }

    public String getSqlCommands() {
        return sqlCommands;
    }
}