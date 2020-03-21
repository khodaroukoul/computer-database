package fr.excilys.formation.cdb.validator;

public enum ShowMessages {
	ERROR_MSG_COMPUTER_ID("Invalid computer id !!! Please choose a valid computer id."),
	ERROR_MSG_NAME("Invalid Name !!! Please enter the computer name."),
	ERROR_MSG_DATE_INTRODUCED("Invalid introduced date format !!!"),
	ERROR_MSG_DATE_DISCONTINUED("Invalid discontinued date format !!!"),
	ERROR_MSG_DATE("Invalid Date !!! Introduced date is not before discontinued date."),
	ERROR_MSG_COMPANY("Invalid company !!! Please choose a valid company."),
	SUCCESS_MSG_UPDATE("The computer is updated successfully."),
    SUCCESS_MSG_DELETE("The computers are deleted successfully."),
    SUCCESS_MSG_CREATE("New computer is added successfully.");

	private String msg;
	 
	ShowMessages(String msg) {
        this.msg = msg;
    }
 
    public String getMsg() {
        return msg;
    }

}
