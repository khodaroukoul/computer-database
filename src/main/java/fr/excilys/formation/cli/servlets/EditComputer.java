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

import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.dao.ComputerDAO;
import fr.excilys.formation.cli.dto.CompanyDTO;
import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.mapper.ComputerMapper;
import fr.excilys.formation.cli.models.Company;
import fr.excilys.formation.cli.models.Computer;
import fr.excilys.formation.cli.validator.Validator;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String editComputer = "/WEB-INF/views/editComputer.jsp";
	private static final String ERROR_MSG_DATE = "Invalid Date !!! Introduced date is not before discontinued date.";
	private static final String SUCCESS_MSG = "The computer is updated successfully.";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("computerId"));		
		Computer computer = ComputerDAO.getInstance().find(id).get();
		ComputerDTO computerDTO = ComputerMapper.getInstance().FromComputerToComputerDTO(computer);
		List<Company> companies = CompanyDAO.getInstance().getList();
		List<CompanyDTO> companiesDTO = companies.stream().map(s -> new CompanyDTO(s.getId(),s.getName()))
				.collect(Collectors.toList());

		request.setAttribute("id",id);
		request.setAttribute("computer", computerDTO);
		request.setAttribute("companies", companiesDTO);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(editComputer);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int computerId =Integer.parseInt(request.getParameter("computerId"));
		String computerName = request.getParameter("computerName");
		String introducedDate = request.getParameter("introduced");
		String discontinuedDate = request.getParameter("discontinued");
		CompanyDTO companyDTO = (request.getParameter("companyId").isBlank()) ? null :	
				new CompanyDTO(Integer.parseInt(request.getParameter("companyId")));

		if(!introducedDate.isBlank() && !discontinuedDate.isBlank()) {
			boolean isAfter = Validator.dateValidator(introducedDate, discontinuedDate);
			if(!isAfter) {
				request.setAttribute("errorMsg",ERROR_MSG_DATE);
				doGet(request, response);
				return;
			}
		}
		
		ComputerDTO computerDTO = new ComputerDTO(computerName, introducedDate, discontinuedDate, companyDTO);
		computerDTO.setId(computerId);
		Computer computer = ComputerMapper.getInstance().fromComputerDTOToComputer(computerDTO);
		ComputerDAO.getInstance().update(computer);

		response.sendRedirect(request.getContextPath()+"/dashboardCli?successMsg="+SUCCESS_MSG);
	}
}
