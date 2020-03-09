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
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String addComputer = "/WEB-INF/views/addComputer.jsp";
	ComputerService pcService = ComputerService.getInstance();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = CompanyService.getInstance().getList();
		List<CompanyDTO> companiesDTO = companies.stream().map(s -> CompanyMapper.FromCompanyToCompanyDTO(s))
				.collect(Collectors.toList());
		request.setAttribute("companies", companiesDTO);
		RequestDispatcher dispatcher = request.getRequestDispatcher(addComputer);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
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
			Computer computer = ComputerMapper.fromComputerDTOToComputer(computerDTO);
			pcService.create(computer);
			
			message = ShowMessages.SUCCESS_MSG_UPDATE.getMsg();
			response.sendRedirect(request.getContextPath()+"/dashboardCli?successMsg=" + message);
		} catch (ValidationException vld) {
			request.setAttribute("errorMsg", vld.getMessage());
			doGet(request,response);			
		}
	}
}
