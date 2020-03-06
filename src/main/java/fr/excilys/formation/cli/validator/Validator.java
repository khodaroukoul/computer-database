package fr.excilys.formation.cli.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

import fr.excilys.formation.cli.service.CompanyService;

public class Validator {

	public static boolean isNotValidDateFormat(String strDate) {
		boolean isNotValidDate = false;
		if (strDate.isBlank())
		{
			return isNotValidDate;
		}
		else
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			try {
				LocalDate.parse(strDate,dtf);	
			}catch(DateTimeParseException e){
				isNotValidDate = true;
				return isNotValidDate;
			}
			return isNotValidDate;
		}
	}

	public static boolean isFirstDateAfterSecond(String firstDate, String secondDate) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		boolean isAfter = LocalDate.parse(firstDate,dtf).isAfter(LocalDate.parse(secondDate,dtf));
		return isAfter;
	}

	public static boolean isNameEmpty(String computerName) {
		boolean isNameEmpty = false;
		if(computerName.isBlank()) {
			isNameEmpty = true;
		}
		return isNameEmpty;
	}

	public static boolean isNotValidId(String id) {
		boolean isNotValid = false;
		if(!StringUtils.isNumeric(id)) {
			isNotValid = true;
		} else if(Integer.parseInt(id)<=0) {
			isNotValid = true;
		}
		return isNotValid;
	}
	
	public static boolean isNotValidCompany(String id) {
		return Integer.parseInt(id)>CompanyService.getInstance().allRecords();
	}
}