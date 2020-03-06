package fr.excilys.formation.cli.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.formation.cli.dto.CompanyDTO;
import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.mapper.ComputerMapper;
import fr.excilys.formation.cli.models.Company;
import fr.excilys.formation.cli.models.Computer;
import fr.excilys.formation.cli.service.CompanyService;
import fr.excilys.formation.cli.service.ComputerService;
import fr.excilys.formation.cli.validator.Validator;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String addComputer = "/WEB-INF/views/addComputer.jsp";
	private static final String ERROR_MSG_NAME = "Invalid Name !!! Please enter the computer name.";
	private static final String ERROR_MSG_DATE_INTRODUCED = "Invalid intorduced date format !!!";
	private static final String ERROR_MSG_DATE_DISCONTINUED = "Invalid discontinued date format !!!";
	private static final String ERROR_MSG_DATE = "Invalid Date !!! Introduced date is not before discontinued date.";
	private static final String ERROR_MSG_COMPANY = "Invalid Company !!! Please choose company.";
	private static final String SUCCESS_MSG = "New computer is added successfully.";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = CompanyService.getInstance().getList();
		List<CompanyDTO> companiesDTO = companies.stream().map(s -> new CompanyDTO(s.getId(),s.getName()))
				.collect(Collectors.toList());
		request.setAttribute("companies", companiesDTO);
		RequestDispatcher dispatcher = request.getRequestDispatcher(addComputer);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		if(Validator.isNameEmpty(computerName)) {
			request.setAttribute("errorMsg",ERROR_MSG_NAME);
			doGet(request, response);
			return;
		}

		String introducedDate = request.getParameter("introduced");
		if(Validator.isNotValidDateFormat(introducedDate)) {
			request.setAttribute("errorMsg",ERROR_MSG_DATE_INTRODUCED);
			doGet(request, response);
			return;
		}
		
		String discontinuedDate = request.getParameter("discontinued");
		if(Validator.isNotValidDateFormat(discontinuedDate)) {
			request.setAttribute("errorMsg",ERROR_MSG_DATE_DISCONTINUED);
			doGet(request, response);
			return;
		}
		
		if(!introducedDate.isBlank() && !discontinuedDate.isBlank()) {
			if(Validator.isFirstDateAfterSecond(introducedDate, discontinuedDate)) {
				request.setAttribute("errorMsg",ERROR_MSG_DATE);
				doGet(request, response);
				return;
			}
		}

		String companyId = request.getParameter("companyId");
		if(companyId!=null) {
			if(Validator.isNotValidId(companyId) || Validator.isNotValidCompany(companyId)) {
				request.setAttribute("errorMsg",ERROR_MSG_COMPANY);
				doGet(request, response);
				return;
			}
		}

		CompanyDTO companyDTO = (request.getParameter("companyId")==null) ? null :	
			new CompanyDTO(Integer.parseInt(request.getParameter("companyId")));
		ComputerDTO computerDTO = new ComputerDTO(computerName, introducedDate, discontinuedDate, companyDTO);
		Computer computer = ComputerMapper.getInstance().fromComputerDTOToComputer(computerDTO);
		ComputerService.getInstance().create(computer);

		response.sendRedirect(request.getContextPath()+"/dashboardCli?successMsg="+SUCCESS_MSG);
	}
}
