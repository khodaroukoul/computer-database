package fr.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.formation.cdb.dto.CompanyDTO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.enums.ShowMessages;
import fr.excilys.formation.cdb.exceptions.ValidationException;
import fr.excilys.formation.cdb.mapper.CompanyMapper;
import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.models.Company;
import fr.excilys.formation.cdb.models.Computer;
import fr.excilys.formation.cdb.service.CompanyService;
import fr.excilys.formation.cdb.service.ComputerService;
import fr.excilys.formation.cdb.validator.Validator;

@WebServlet("/editComputer")
@Controller
public class EditComputer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String editComputer = "/WEB-INF/views/editComputer.jsp";

	@Autowired
	ComputerService pcService;
	@Autowired
	CompanyService coService;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerId = request.getParameter("computerId");
		try {
			Validator.validateComputerId(computerId);
			showComputerForEdit(request, response, computerId);
		} catch (ValidationException vld) {
			request.setAttribute("errorMsg", vld.getMessage());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard");
			dispatcher.forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerId = request.getParameter("computerId");
		String computerName = request.getParameter("computerName");
		String introducedDate = request.getParameter("introduced");
		String discontinuedDate = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		try {
			Validator.validateFields(computerName, introducedDate, discontinuedDate, companyId);

			CompanyDTO companyDTO = new CompanyDTO();
			if(companyId.isBlank()) {
				companyDTO = null;
			} else {
				companyDTO.setId(Integer.parseInt(companyId));
			}

			ComputerDTO computerDTO = new ComputerDTO(computerName, introducedDate, discontinuedDate, companyDTO);
			computerDTO.setId(Integer.parseInt(computerId));
			Computer computer = ComputerMapper.fromComputerDTOToComputer(computerDTO);
			pcService.update(computer);

			String message = ShowMessages.SUCCESS_MSG_UPDATE.getMsg();
			response.sendRedirect(request.getContextPath()+"/dashboard?successMsg=" + message);
		} catch (ValidationException vld) {
			request.setAttribute("errorMsg", vld.getMessage());
			doGet(request,response);
		}
	}

	private void showComputerForEdit(HttpServletRequest request, HttpServletResponse response, String computerId)
			throws ServletException, IOException {
		List<Computer> computer = pcService.findById(Integer.parseInt(computerId));
		ComputerDTO computerDTO = ComputerMapper.FromComputerToComputerDTO(computer.get(0));
		List<Company> companies = coService.getList();
		List<CompanyDTO> companiesDTO = companies.stream().map(CompanyMapper::FromCompanyToCompanyDTO)
				.collect(Collectors.toList());
		request.setAttribute("computer", computerDTO);
		request.setAttribute("companies", companiesDTO);
		request.setAttribute("id",Integer.parseInt(computerId));

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(editComputer);
		dispatcher.forward(request,response);
	}
}
