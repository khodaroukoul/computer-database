package fr.excilys.formation.cdb.dao;

public enum SQLCommands {
    FIND_COMPUTERS("SELECT pc FROM Computer pc LEFT JOIN pc.company co "),
    FIND_COMPUTER("SELECT pc FROM Computer pc LEFT JOIN pc.company co WHERE pc.id = :computerId"),
    DELETE_COMPUTER("DELETE FROM Computer WHERE id = :computerId"),
    DELETE_COMPUTERS("DELETE FROM Computer WHERE id IN (:computerIds)"),
    UPDATE_COMPUTER("UPDATE Computer SET name = :computerName, introduced = :introduced,"
            + " discontinued = :discontinued, company_id = :companyId WHERE id = :computerId"),
    FIND_COMPUTERS_BY_NAME("SELECT pc FROM Computer pc LEFT JOIN pc.company co WHERE pc.name LIKE :computerName "),
    COUNT_COMPUTERS_FOUND_BY_NAME("SELECT COUNT(pc.id) FROM Computer pc WHERE pc.name LIKE :computerName"),
    COUNT_COMPUTERS("SELECT COUNT(pc.id) FROM Computer pc"),
    FIND_COMPANIES("SELECT co FROM Company co"),
    DELETE_COMPANY("DELETE FROM Company co WHERE co.id = :companyId"),
    DELETE_COMPUTERS_BY_ID_COMPANY("DELETE FROM Computer pc WHERE pc.company.id = :companyId"),
    FIND_COMPANY("SELECT co FROM Company co WHERE id = :companyId"),
    ORDER_BY(" ORDER BY  ");

    private String sqlCommands;

    SQLCommands(String sqlCommands) {
        this.sqlCommands = sqlCommands;
    }

    public String getSqlCommands() {
        return sqlCommands;
    }

}