package fr.excilys.formation.cli.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.formation.cli.dto.ComputerDTO;
import fr.excilys.formation.cli.enums.ShowMessages;
import fr.excilys.formation.cli.models.Pagination;
import fr.excilys.formation.cli.service.CompanyService;
import fr.excilys.formation.cli.service.ComputerService;
import fr.excilys.formation.cli.service.PageCreator;
import fr.excilys.formation.cli.service.QuerySQL;

/**
 * Servlet implementation class DashboardCli
 */
@WebServlet("/dashboardCli")
public class DashboardCli extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String dashboard = "/WEB-INF/views/dashboard.jsp";
	ComputerService pcService = ComputerService.getInstance();
	CompanyService coService = CompanyService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addComputerMsg = request.getParameter("successMsg");
		if(addComputerMsg!=null && !addComputerMsg.isBlank()) {
			request.setAttribute("successMsg",addComputerMsg);
		}

		List<ComputerDTO> computersDTO = new ArrayList<>();

		int noOfComputers = 0;
		int[] pageData = PageCreator.pageData(request.getParameter("currentPage"),
				request.getParameter("pcsPerPage"));
		int currentPage = pageData[0];
		int computersPerPage = pageData[1];

		String order = request.getParameter("order");
		String orderBy = QuerySQL.sortSQL(order);

		coService.deleteCompany(request.getParameter("searchCompany"));

		String searchPcByName = request.getParameter("search");
		computersDTO = pcService.listComputerDTO(currentPage, computersPerPage, orderBy, searchPcByName);
		noOfComputers = pcService.noOfComputers(searchPcByName);

		Pagination myPage = PageCreator.pageCreate(currentPage, computersPerPage, noOfComputers);

		dispatcherForward(request, response, computersDTO, noOfComputers, currentPage, 
				computersPerPage, myPage, order, searchPcByName);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("selection").isBlank()) {
			pcService.delete(request.getParameter("selection"));
			request.setAttribute("successMsg",ShowMessages.SUCCESS_MSG_DELETE.getMsg());
		}
		doGet(request, response);
	}

	private void dispatcherForward(HttpServletRequest request, HttpServletResponse response,
			List<ComputerDTO> computersDTO, int noOfComputers, int currentPage, int computersPerPage,
			Pagination myPage, String order, String searchPcByName)	throws ServletException, IOException {

		request.setAttribute("previousPage", myPage.getPreviousPage());
		request.setAttribute("nextPage", myPage.getNextPage());
		request.setAttribute("pageBegin", myPage.getPageBegin());
		request.setAttribute("pageEnd", myPage.getPageEnd());
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pcsPerPage", computersPerPage);
		request.setAttribute("noOfPages", myPage.getNoOfPages());
		request.setAttribute("noOfPcs", noOfComputers);
		request.setAttribute("order", order);
		request.setAttribute("search", searchPcByName);
		request.setAttribute("computers", computersDTO);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(dashboard);
		dispatcher.forward(request,response);
	}
}
