package fr.excilys.formation.cdb.service;

public class QuerySQL {

	public static String sortSQL(String order) {
		String orderBy = "null";
		if(order != null) {
			switch (order) {
			case "computer":
				orderBy = "cp.name";
				break;
			case "introduced":
				orderBy = "cp.introduced";
				break;
			case "discontinued":
				orderBy = "cp.discontinued";
				break;
			case "co.name":
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