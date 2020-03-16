package fr.excilys.formation.cdb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
			@RequestParam(required = false, value = "pcsPerPage") String pcsPerPage,
			@RequestParam(required = false, value = "order") String order,
			@RequestParam(required = false, value = "successMsg") String successMsg,
			@RequestParam(required = false, value = "errorMsg") String errorMsg,
			@RequestParam(required = false, value = "search") String searchPcByName,
			@RequestParam(required = false, value = "searchCompany") String searchCompany) {

		ModelAndView modelAndView = new ModelAndView(dashboard);

		if(successMsg!=null && !successMsg.isBlank()) {
			modelAndView.addObject("successMsg", successMsg);
		}
		
		if(errorMsg!=null && !errorMsg.isBlank()) {
			modelAndView.addObject("errorMsg", errorMsg);
		}

		int[] pageData = PageCreator.pageData(currentPageJsp,pcsPerPage);
		int currentPage = pageData[0];
		int computersPerPage = pageData[1];

		coService.deleteCompany(searchCompany);

		List<ComputerDTO> computersDTO = pcService.listComputerDTO(currentPage, computersPerPage, order, searchPcByName);
		int noOfComputers = pcService.noOfComputers(searchPcByName);

		Pagination myPage = PageCreator.pageCreate(currentPage, computersPerPage, noOfComputers);

		setDashboardAttribute(order, searchPcByName, modelAndView, currentPage,
				computersPerPage, computersDTO,	noOfComputers, myPage);
		
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

	private void setDashboardAttribute(String order, String searchPcByName, ModelAndView modelAndView, int currentPage,
			int computersPerPage, List<ComputerDTO> computersDTO, int noOfComputers, Pagination myPage) {
		modelAndView.addObject("previousPage", myPage.getPreviousPage());
		modelAndView.addObject("nextPage", myPage.getNextPage());
		modelAndView.addObject("pageBegin", myPage.getPageBegin());
		modelAndView.addObject("pageEnd", myPage.getPageEnd());
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("pcsPerPage", computersPerPage);
		modelAndView.addObject("noOfPages", myPage.getNoOfPages());
		modelAndView.addObject("noOfPcs", noOfComputers);
		modelAndView.addObject("order", order);
		modelAndView.addObject("search", searchPcByName);
		modelAndView.addObject("computers", computersDTO);
	}

}
