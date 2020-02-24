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
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String nextPage = "/WEB-INF/views/dashboard.jsp";
        List<Computer> computers = ComputerDAO.getInstance().getList().get();
	    request.setAttribute("computers", computers);
	    int page = 1;
        int recordsPerPage = 10;
        List<Computer> computersPerpage = ComputerDAO.getInstance().getListPerPage(page,recordsPerPage).get();
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
