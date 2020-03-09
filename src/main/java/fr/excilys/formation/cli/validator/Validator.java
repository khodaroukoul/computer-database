package fr.excilys.formation.cli.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

import fr.excilys.formation.cli.enums.ShowMessages;
import fr.excilys.formation.cli.exceptions.ValidationException;
import fr.excilys.formation.cli.service.CompanyService;
import fr.excilys.formation.cli.service.ComputerService;

public class Validator {

	public static void validateFields(String computerName, String introducedDate, 
			String discontinuedDate, String companyId) throws ValidationException {
		String message = "";
		if(isNameEmpty(computerName)) {
			message = ShowMessages.ERROR_MSG_NAME.getMsg();
			throw new ValidationException(message);
		}

		if(isNotValidDateFormat(introducedDate)) {
			message = ShowMessages.ERROR_MSG_DATE_INTRODUCED.getMsg();
			throw new ValidationException(message);
		}

		if(isNotValidDateFormat(discontinuedDate)) {
			message = ShowMessages.ERROR_MSG_DATE_DISCONTINUED.getMsg();
			throw new ValidationException(message);
		}

		if(!introducedDate.isBlank() && !discontinuedDate.isBlank()) {
			if(isFirstDateAfterSecond(introducedDate, discontinuedDate)) {
				message = ShowMessages.ERROR_MSG_DATE.getMsg();
				throw new ValidationException(message);
			}
		}
		if(!companyId.isBlank()) {
			if(isNotValidId(companyId) || isNotValidCompany(companyId)) {
				message = ShowMessages.ERROR_MSG_COMPANY.getMsg();
				throw new ValidationException(message);
			}
		}
	}

	public static boolean isNotValidDateFormat(String strDate) {
		boolean isNotValidDate = false;
		if (strDate.isBlank())
		{
			return isNotValidDate;
		} else {
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
		return CompanyService.getInstance().findById(Integer.parseInt(id)).isEmpty();
	}

	public static boolean isNotValidComputer(String id) {
		return ComputerService.getInstance().findById(Integer.parseInt(id)).isEmpty();
	}
}