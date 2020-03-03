package fr.excilys.formation.cli.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

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
	
	public static boolean idValidator(String id) {
		boolean isNumber = true;
		if(!StringUtils.isNumeric(id)) {
			isNumber = false;
		} else if(Integer.parseInt(id)<=0) {
			isNumber = false;
		}
		return isNumber;
	}
}
