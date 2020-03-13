package fr.excilys.formation.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import fr.excilys.formation.cdb.service.CompanyService;
import fr.excilys.formation.cdb.service.ComputerService;
import fr.excilys.formation.cdb.controller.ShowMessages;
import fr.excilys.formation.cdb.exceptions.ValidationException;

@Component
public class Validator {

	static ComputerService pcService;
	static CompanyService coService;

	public Validator(ComputerService pcService, CompanyService coService) {
		Validator.pcService = pcService;
		Validator.coService = coService;		
	}

	public static void validateComputerId(String computerId) throws ValidationException {
		if(Validator.isNotValidId(computerId) || Validator.isNotValidComputer(computerId)) {
			String message = ShowMessages.ERROR_MSG_COMPUTER_ID.getMsg();
			throw new ValidationException(message);
		}
	}

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
		if (strDate==null || strDate.isBlank()) {
			return isNotValidDate;
		} else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			try {
				LocalDate.parse(strDate,dtf);	
			} catch(DateTimeParseException e){
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
		return coService.findById(Integer.parseInt(id)).isEmpty();
	}

	public static boolean isNotValidComputer(String id) {
		return pcService.findById(Integer.parseInt(id)).isEmpty();
	}
}