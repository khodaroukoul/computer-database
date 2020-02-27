package fr.excilys.formation.cli.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.formation.cli.dao.ComputerDAO;

public class Validator {

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private static final String PARSE_EXCEPTION = "SQL EXCEPTION ERROR IN ";
	private static final String CLASS_NAME = "IN CLASS Validator. ";

	public static boolean dateValidator(String firstDate, String secondDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean isAfter = false;
		try {
			isAfter = sdf.parse(secondDate).after(sdf.parse(firstDate));
		} catch (ParseException e) {
			logger.error(PARSE_EXCEPTION + "dateValidator " + CLASS_NAME + e.getMessage());
		}
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
