package fr.excilys.formation.cli.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.mapper.ComputerMapper;
import fr.excilys.formation.cli.models.Computer;
import fr.excilys.formation.cli.service.ComputerService;

/**
 * Servlet implementation class DashboardCli
 */
@WebServlet("/dashboardCli")
public class DashboardCli extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String dashboard = "/WEB-INF/views/dashboard.jsp";
	private static final String SUCCESS_MSG = "The computer is deleted successfully.";
	ComputerService pcService = ComputerService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addComputerMsg = request.getParameter("successMsg");
		if(addComputerMsg!=null && !addComputerMsg.isBlank()) {
			request.setAttribute("successMsg",addComputerMsg);
		}
		
		List<Computer> computers = new ArrayList<>();
		List<ComputerDTO> computersDTO = new ArrayList<>();
		
		int noOfRecords = 0;
		int page = 1;
		int recordsPerPage = 10;

		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		if(request.getParameter("recordsPerPage") != null) {
			recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
		}
		
		if(request.getParameter("search")!=null && !request.getParameter("search").isBlank()) {
			String computerName = request.getParameter("search");
			computers = pcService.findByName(computerName,page,recordsPerPage);
			request.setAttribute("search", computerName);

			if(!computers.isEmpty()) {
				computersDTO = computers.stream().map(s -> ComputerMapper.getInstance()
						.FromComputerToComputerDTO(s)).collect(Collectors.toList());
				noOfRecords = pcService.findByNameAll(computerName).size();
			}
		} else {
			computers = pcService.getListPerPage(page,recordsPerPage);
			computersDTO = computers.stream().map(s -> ComputerMapper.getInstance()
					.FromComputerToComputerDTO(s)).collect(Collectors.toList());
			noOfRecords = pcService.getList().size();
		}

		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
		request.setAttribute("noOfRecords", noOfRecords);
		request.setAttribute("noOfPages", noOfPages);		
		request.setAttribute("currentPage", page);
		request.setAttribute("recordsPerPage", recordsPerPage);
		request.setAttribute("computers", computersDTO);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(dashboard);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("selection").isBlank()) {
			pcService.delete(request.getParameter("selection"));
			request.setAttribute("successMsg",SUCCESS_MSG);
		}
		doGet(request, response);
	}
}
