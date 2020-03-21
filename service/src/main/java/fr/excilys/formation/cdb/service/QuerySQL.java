package fr.excilys.formation.cdb.service;

public class QuerySQL {

    public static String sortSQL(String order) {
        String orderBy = "null";
        if (order != null) {
            switch (order) {
                case "computer":
                    orderBy = "pc.name";
                    break;
                case "introduced":
                    orderBy = "pc.introduced";
                    break;
                case "discontinued":
                    orderBy = "pc.discontinued";
                    break;
                case "company":
                    orderBy = "co.name";
                    break;
                default:
                    orderBy = "null";
                    break;
            }
        }

        return orderBy;
    }
}