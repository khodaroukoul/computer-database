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
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String editComputer = "/WEB-INF/views/editComputer.jsp";
	private static final String ERROR_MSG_DATE = "Invalid Date !!! Introduced date is not before discontinued date.";
	private static final String ERROR_MSG_NAME = "Invalid Name !!! Please enter the computer name.";
	private static final String ERROR_MSG_COMPANY = "Invalid company !!! Please choose a valid company.";
	private static final String ERROR_MSG_COMPUTER_ID = "Invalid computer id !!! Please choose a valid computer id.";
	private static final String SUCCESS_MSG = "The computer is updated successfully.";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pcId = request.getParameter("computerId");
		if(!Validator.idValidator(pcId)) {
			request.setAttribute("errorMsg",ERROR_MSG_COMPUTER_ID);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(editComputer);
			dispatcher.forward(request,response);
			return;			
		}
		int computerId =Integer.parseInt(pcId);
		Computer computer = ComputerService.getInstance().findById(computerId).get();
		ComputerDTO computerDTO = ComputerMapper.getInstance().FromComputerToComputerDTO(computer);
		List<Company> companies = CompanyService.getInstance().getList();
		List<CompanyDTO> companiesDTO = companies.stream().map(s -> new CompanyDTO(s.getId(),s.getName()))
				.collect(Collectors.toList());

		request.setAttribute("id",computerId);
		request.setAttribute("computer", computerDTO);
		request.setAttribute("companies", companiesDTO);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(editComputer);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pcId = request.getParameter("computerId");		
		int computerId =Integer.parseInt(pcId);

		String computerName = request.getParameter("computerName");
		if(Validator.computerNameValidator(computerName)) {
			request.setAttribute("errorMsg",ERROR_MSG_NAME);
			doGet(request, response);
			return;
		}

		String introducedDate = request.getParameter("introduced");
		String discontinuedDate = request.getParameter("discontinued");

		if(!introducedDate.isBlank() && !discontinuedDate.isBlank()) {
			boolean isAfter = Validator.dateValidator(introducedDate, discontinuedDate);
			if(!isAfter) {
				request.setAttribute("errorMsg",ERROR_MSG_DATE);
				doGet(request, response);
				return;
			}
		}

		String companyId = request.getParameter("companyId");
		if(companyId!=null) {
			if(!Validator.idValidator(companyId)) {
				request.setAttribute("errorMsg",ERROR_MSG_COMPANY);
				doGet(request, response);
				return;
			}
		}
		CompanyDTO companyDTO = (companyId.isBlank()) ? null :	
			new CompanyDTO(Integer.parseInt(request.getParameter("companyId")));

		ComputerDTO computerDTO = new ComputerDTO(computerName, introducedDate, discontinuedDate, companyDTO);
		computerDTO.setId(computerId);
		Computer computer = ComputerMapper.getInstance().fromComputerDTOToComputer(computerDTO);
		ComputerService.getInstance().update(computer);

		response.sendRedirect(request.getContextPath()+"/dashboardCli?successMsg="+SUCCESS_MSG);
	}
}
