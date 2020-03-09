package fr.excilys.formation.cli.service;

public class QuerySQL {

	public static String sortSQL(String order) {
		String orderBy = "null";
		if (order != null) {
			if (order.equals("computer")) {
				orderBy = "cp.name";
			} else if (order.equals("introduced")) {
				orderBy = "cp.introduced";
			} else if (order.equals("discontinued")) {
				orderBy = "cp.discontinued";
			} else if(order.equals("company")) {
				orderBy = "co.name";
			}
		}
		return orderBy;
	}
}
