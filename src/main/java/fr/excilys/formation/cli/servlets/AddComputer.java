package fr.excilys.formation.cli.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.formation.cli.beans.Company;
import fr.excilys.formation.cli.beans.Computer;
import fr.excilys.formation.cli.dao.CompanyDAO;
import fr.excilys.formation.cli.dao.ComputerDAO;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String addComputer = "/WEB-INF/views/addComputer.jsp";
	private static final String ERROR_MSG = "Invalid Date! Introduced date is after discontinued date.";
	private static final String SUCCESS_MSG = "The computer is added successfully.";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = CompanyDAO.getInstance().getList();
		request.setAttribute("companies", companies);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(addComputer);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		String introDate = request.getParameter("introduced");
		LocalDate introduced = introDate.isEmpty()?null:LocalDate.parse(introDate);
		String discDate = request.getParameter("discontinued");
		LocalDate discontinued = discDate.isEmpty()?null:LocalDate.parse(discDate);
		boolean isAfter = discontinued.isAfter(introduced);
		int idCompany = Integer.parseInt(request.getParameter("companyId"));
		
		if(introduced!=null && discontinued!=null) {
			if(!isAfter) {
				request.setAttribute("errorMsg",ERROR_MSG);
				doGet(request, response);
				return;
			}
		}
		Company company = new Company.CompanyBuilder().setId(idCompany).build();
		Computer computer = new Computer.ComputerBuilder(computerName).build();
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);
		ComputerDAO.getInstance().create(computer);
		request.setAttribute("successMsg",SUCCESS_MSG);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboardCli");
		dispatcher.forward(request,response);
		//response.sendRedirect(request.getContextPath() + "/dashboardCli");
	}

}
