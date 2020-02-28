package fr.excilys.formation.cli.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validator {

	public static boolean dateValidator(String firstDate, String secondDate) {
		boolean isAfter = false;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		isAfter = LocalDate.parse(secondDate,dtf).isAfter(LocalDate.parse(firstDate,dtf));
		return isAfter;
	}

	public static boolean computerNameValidator(String computerName) {
		boolean isNameEmpty = false;
		if(computerName.isBlank()) {
			isNameEmpty = true;
		}
		return isNameEmpty;
	}
}
