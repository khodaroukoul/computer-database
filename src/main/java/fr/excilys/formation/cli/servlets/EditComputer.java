package fr.excilys.formation.cli.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.formation.cli.dto.CompanyDTO;
import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.enums.ShowMessages;
import fr.excilys.formation.cli.exceptions.ValidationException;
import fr.excilys.formation.cli.mapper.CompanyMapper;
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
	ComputerService pcService = ComputerService.getInstance();
	CompanyService coService = CompanyService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerId = request.getParameter("computerId");
		if(Validator.isNotValidId(computerId) || Validator.isNotValidComputer(computerId)) {
			request.setAttribute("errorMsg", ShowMessages.ERROR_MSG_COMPUTER_ID.getMsg());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboardCli");
			dispatcher.forward(request,response);			
		}
		showComputerForEdit(request, response, computerId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		String computerId = request.getParameter("computerId");
		String computerName = request.getParameter("computerName");
		String introducedDate = request.getParameter("introduced");
		String discontinuedDate = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		try {
			Validator.validateFields(computerName, introducedDate, discontinuedDate, companyId);

			CompanyDTO companyDTO = new CompanyDTO();
			if(!companyId.isBlank()) {	
				companyDTO.setId(Integer.parseInt(companyId));
			}
			ComputerDTO computerDTO = new ComputerDTO(computerName, introducedDate, discontinuedDate, companyDTO);
			computerDTO.setId(Integer.parseInt(computerId));
			Computer computer = ComputerMapper.fromComputerDTOToComputer(computerDTO);
			pcService.update(computer);
			
			message = ShowMessages.SUCCESS_MSG_UPDATE.getMsg();
			response.sendRedirect(request.getContextPath()+"/dashboardCli?successMsg=" + message);
		} catch (ValidationException vld) {
			request.setAttribute("errorMsg", vld.getMessage());
			doGet(request,response);
		}
	}
	
	private void showComputerForEdit(HttpServletRequest request, HttpServletResponse response, String computerId)
			throws ServletException, IOException {
		Optional<Computer> optionalComputer = pcService.findById(Integer.parseInt(computerId));
		Computer computer = optionalComputer.get();
		ComputerDTO computerDTO = ComputerMapper.FromComputerToComputerDTO(computer);
		List<Company> companies = coService.getList();
		List<CompanyDTO> companiesDTO = companies.stream().map(s -> CompanyMapper.FromCompanyToCompanyDTO(s))
				.collect(Collectors.toList());
		request.setAttribute("computer", computerDTO);
		request.setAttribute("companies", companiesDTO);
		request.setAttribute("id",Integer.parseInt(computerId));
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(editComputer);
		dispatcher.forward(request,response);
	}
}
