package fr.excilys.formation.cdb.validator;

import fr.excilys.formation.cdb.exception.ValidationException;
import fr.excilys.formation.cdb.service.CompanyService;
import fr.excilys.formation.cdb.service.ComputerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class Validator {

    static ComputerService computerService;
    static CompanyService companyService;

    public Validator(ComputerService computerService, CompanyService companyService) {
        Validator.computerService = computerService;
        Validator.companyService = companyService;
    }

    public static void validateComputerId(String computerId) throws ValidationException {
        if (Validator.isNotValidId(computerId) || Validator.isNotValidComputer(computerId)) {
            String message = ShowMessages.ERROR_MSG_COMPUTER_ID.getMsg();
            throw new ValidationException(message);
        }
    }

    public static void validateFields(String computerName, String introducedDate,
                                      String discontinuedDate, String companyId) throws ValidationException {
        String message;
        if (isNameEmpty(computerName)) {
            message = ShowMessages.ERROR_MSG_NAME.getMsg();
            throw new ValidationException(message);
        }

        if (isNotValidDateFormat(introducedDate)) {
            message = ShowMessages.ERROR_MSG_DATE_INTRODUCED.getMsg();
            throw new ValidationException(message);
        }

        if (isNotValidDateFormat(discontinuedDate)) {
            message = ShowMessages.ERROR_MSG_DATE_DISCONTINUED.getMsg();
            throw new ValidationException(message);
        }

        if (!introducedDate.isBlank() && !discontinuedDate.isBlank()) {
            if (isFirstDateAfterSecond(introducedDate, discontinuedDate)) {
                message = ShowMessages.ERROR_MSG_DATE.getMsg();
                throw new ValidationException(message);
            }
        }

        if (!companyId.isBlank()) {
            if (isNotValidId(companyId) || isNotValidCompany(companyId)) {
                message = ShowMessages.ERROR_MSG_COMPANY.getMsg();
                throw new ValidationException(message);
            }
        }
    }

    public static boolean isNotValidDateFormat(String strDate) {
        boolean isNotValidDate = false;
        if (strDate != null && !strDate.isBlank()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate.parse(strDate, dtf);
                isNotValidDate = false;
            } catch (DateTimeParseException e) {
                isNotValidDate = true;
            }
        }

        return isNotValidDate;
    }

    public static boolean isFirstDateAfterSecond(String firstDate, String secondDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(firstDate, dtf).isAfter(LocalDate.parse(secondDate, dtf));
    }

    public static boolean isNameEmpty(String computerName) {
        boolean isNameEmpty = false;
        if (computerName.isBlank()) {
            isNameEmpty = true;
        }

        return isNameEmpty;
    }

    public static boolean isNotValidId(String id) {
        boolean isNotValid = false;
        if (!StringUtils.isNumeric(id)) {
            isNotValid = true;
        } else if (Integer.parseInt(id) < 1) {
            isNotValid = true;
        }

        return isNotValid;
    }

    public static boolean isNotValidCompany(String id) {
        return companyService.findById(Integer.parseInt(id)).isEmpty();
    }

    public static boolean isNotValidComputer(String id) {
        return computerService.findById(Integer.parseInt(id)).isEmpty();
    }
}