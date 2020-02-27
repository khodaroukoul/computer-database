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

import fr.excilys.formation.cli.dao.ComputerDAO;
import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.mapper.ComputerMapper;
import fr.excilys.formation.cli.models.Computer;

/**
 * Servlet implementation class DashboardCli
 */
@WebServlet("/dashboardCli")
public class DashboardCli extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String dashboard = "/WEB-INF/views/dashboard.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addComputerMsg = (String) request.getAttribute("successMsg");
		request.setAttribute("successMsg",addComputerMsg);
		
		int page = 1;
		int recordsPerPage = 10;
		
		if(request.getParameter("pageJsp") != null) {
			page = Integer.parseInt(request.getParameter("pageJsp"));
		}
		
		if(request.getParameter("recordsPerPageJsp") != null) {
			recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPageJsp"));
		}		
		
		int noOfRecords = ComputerDAO.getInstance().getList().size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        
        request.setAttribute("noOfRecords", noOfRecords);
        request.setAttribute("noOfPages", noOfPages);		
		request.setAttribute("currentPage", page);
		request.setAttribute("recordsPerPageJsp", recordsPerPage);
		
		List<Computer> computersPerpage = ComputerDAO.getInstance().getListPerPage(page,recordsPerPage);
		List<ComputerDTO> computersDTO = computersPerpage.stream().map(s -> ComputerMapper.getInstance()
				.FromComputerToComputerDTO(s)).collect(Collectors.toList());
		
		request.setAttribute("computersPage", computersDTO);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(dashboard);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
