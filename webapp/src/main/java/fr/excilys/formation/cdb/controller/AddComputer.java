package fr.excilys.formation.cdb.controller;

import fr.excilys.formation.cdb.dto.CompanyDTO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.exception.ValidationException;
import fr.excilys.formation.cdb.mapper.CompanyMapper;
import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.model.Computer;
import fr.excilys.formation.cdb.service.CompanyService;
import fr.excilys.formation.cdb.service.ComputerService;
import fr.excilys.formation.cdb.validator.ShowMessages;
import fr.excilys.formation.cdb.validator.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/addComputer")
public class AddComputer {

    private final ComputerService computerService;
    private final CompanyService companyService;
    private final ComputerMapper computerMapper;
    private final CompanyMapper companyMapper;

    public AddComputer(ComputerService computerService, CompanyService companyService, ComputerMapper computerMapper,
                       CompanyMapper companyMapper) {
        this.computerService = computerService;
        this.companyService = companyService;
        this.computerMapper = computerMapper;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public ModelAndView companyList(@RequestParam(required = false, value = "errorMsg") String errorMsg) {
        String addComputer = "addComputer";
        ModelAndView modelAndView = new ModelAndView(addComputer);

        Dashboard.setMessage("errorMsg", errorMsg, modelAndView);

        List<Company> companies = companyService.getList();
        List<CompanyDTO> companiesDTO = companies.stream().map(companyMapper::fromCompanyToCompanyDTO)
                .collect(Collectors.toList());

        modelAndView.addObject("companies", companiesDTO);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView addComputer(@RequestParam(value = "computerName") String computerName,
                                    @RequestParam(required = false, value = "introduced") String introduced,
                                    @RequestParam(required = false, value = "discontinued") String discontinued,
                                    @RequestParam(required = false, value = "company") String[] company) {
        ModelAndView modelAndView = new ModelAndView();
        String message;

        String companyId = company[0];
        try {
            Validator.validateFields(computerName, introduced, discontinued, companyId);

            CompanyDTO companyDTO = setCompanyDTO(company);
            ComputerDTO computerDTO = new ComputerDTO(computerName, introduced, discontinued, companyDTO);
            Computer computer = computerMapper.fromComputerDTOToComputer(computerDTO);

            computerService.create(computer);

            message = ShowMessages.SUCCESS_MSG_CREATE.getMsg();
            modelAndView.addObject("successMsg", message);
            modelAndView.setViewName("redirect:/dashboard");
        } catch (ValidationException vld) {
            modelAndView.addObject("errorMsg", vld.getMessage());
            modelAndView.setViewName("redirect:/addComputer");
        }

        return modelAndView;
    }

    private CompanyDTO setCompanyDTO(String[] company) {
        CompanyDTO companyDTO = new CompanyDTO();
        if (!company[0].isBlank()) {
            String companyName = company[1];
            companyDTO.setId(Integer.parseInt(company[0]));
            companyDTO.setName(companyName);
        }

        return companyDTO;
    }
}