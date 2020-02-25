package fr.excilys.formation.cli.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.dao.ComputerDAO;

/**
 * Servlet implementation class DashboardCli
 */
@WebServlet("/dashboardCli")
public class DashboardCli extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int recordsPerPage = 10;
	private String nextPage = "/WEB-INF/views/dashboard.jsp";
	private int page = 1;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("pageJsp") != null) {
			page = Integer.parseInt(request.getParameter("pageJsp"));
		}
		
		if(request.getParameter("recordsPerPageJsp") != null) {
			recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPageJsp"));
		}

		List<Computer> computers = ComputerDAO.getInstance().getList().get();
		int noOfRecords = computers.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        
        request.setAttribute("noOfPages", noOfPages);		
		request.setAttribute("currentPage", page);
		request.setAttribute("recordsPerPageJsp", recordsPerPage);
		
		List<Computer> computersPerpage = ComputerDAO.getInstance().getListPerPage(page,recordsPerPage).get();
		request.setAttribute("computers", computers);
		request.setAttribute("computersPage", computersPerpage);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
