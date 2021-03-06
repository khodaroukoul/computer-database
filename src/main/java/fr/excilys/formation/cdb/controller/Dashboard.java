package fr.excilys.formation.cdb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.model.Pagination;
import fr.excilys.formation.cdb.service.CompanyService;
import fr.excilys.formation.cdb.service.ComputerService;
import fr.excilys.formation.cdb.service.PageCreator;

@Controller
public class Dashboard {

	private String dashboard = "dashboard";
	private ComputerService pcService;
	private CompanyService coService;

	public Dashboard(ComputerService pcService, CompanyService coService) {
		this.pcService = pcService;
		this.coService = coService;
	}

	@GetMapping(value = "/dashboard")
	public ModelAndView dashboard(@RequestParam(required = false, value = "currentPage") String currentPageJsp,
			@RequestParam(required = false, value = "computersPerPage") String computersPerPage,
			@RequestParam(required = false, value = "order") String order,
			@RequestParam(required = false, value = "successMsg") String successMsg,
			@RequestParam(required = false, value = "errorMsg") String errorMsg,
			@RequestParam(required = false, value = "search") String searchByName,
			@RequestParam(required = false, value = "searchCompany") String searchCompany) {

		ModelAndView modelAndView = new ModelAndView(dashboard);

		setMessage("successMsg", successMsg, modelAndView);
		setMessage("errorMsg", errorMsg, modelAndView);

		int[] pageData = PageCreator.pageData(currentPageJsp,computersPerPage);
		int currentPage = pageData[0];
		int listComputersPerPage = pageData[1];

		coService.deleteCompany(searchCompany);

		List<ComputerDTO> computersDTO = pcService.listComputerDTO(currentPage, listComputersPerPage, order, searchByName);

		int noOfComputers = pcService.noOfComputers(searchByName);

		Pagination myPage = PageCreator.pageCreate(currentPage, listComputersPerPage, noOfComputers);

		setDashboardAttribute(order, searchByName, modelAndView, currentPage,
				listComputersPerPage, computersDTO,	noOfComputers, myPage);
		
		return modelAndView;
	}

	@PostMapping(value="/deleteComputer")
	public ModelAndView deleteComputer(@RequestParam(value = "selection") String deletePcList) {

		ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
		if(!deletePcList.isBlank()) {
			pcService.delete(deletePcList);
			modelAndView.addObject("successMsg", ShowMessages.SUCCESS_MSG_DELETE.getMsg());
		}

		return modelAndView;
	}

	private void setDashboardAttribute(String order, String searchByName, ModelAndView modelAndView, int currentPage,
			int computersPerPage, List<ComputerDTO> computersDTO, int noOfComputers, Pagination myPage) {
		modelAndView.addObject("previousPage", myPage.getPreviousPage());
		modelAndView.addObject("nextPage", myPage.getNextPage());
		modelAndView.addObject("pageBegin", myPage.getPageBegin());
		modelAndView.addObject("pageEnd", myPage.getPageEnd());
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("computersPerPage", computersPerPage);
		modelAndView.addObject("noOfPages", myPage.getNoOfPages());
		modelAndView.addObject("noOfComputers", noOfComputers);
		modelAndView.addObject("order", order);
		modelAndView.addObject("search", searchByName);
		modelAndView.addObject("computers", computersDTO);
	}

	public static void setMessage(String messageName, String message, ModelAndView modelAndView) {
		if (message != null && !message.isBlank()) {
			modelAndView.addObject(messageName, message);
		}
	}
}
