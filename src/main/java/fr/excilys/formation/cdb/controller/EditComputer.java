package fr.excilys.formation.cdb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.formation.cdb.dto.CompanyDTO;
import fr.excilys.formation.cdb.dto.ComputerDTO;
import fr.excilys.formation.cdb.exceptions.ValidationException;
import fr.excilys.formation.cdb.mapper.CompanyMapper;
import fr.excilys.formation.cdb.mapper.ComputerMapper;
import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.model.Computer;
import fr.excilys.formation.cdb.service.CompanyService;
import fr.excilys.formation.cdb.service.ComputerService;
import fr.excilys.formation.cdb.validator.Validator;

@Controller
@RequestMapping(value = "/editComputer")
public class EditComputer {

    private String editComputer = "editComputer";
    private ComputerService computerService;
    private CompanyService companyService;
    private ComputerMapper computerMapper;
    private CompanyMapper companyMapper;

    public EditComputer(ComputerService computerService, CompanyService companyService, ComputerMapper computerMapper,
                        CompanyMapper companyMapper) {
        this.computerService = computerService;
        this.companyService = companyService;
        this.computerMapper = computerMapper;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public ModelAndView showComputerForEdit(@RequestParam(value = "computerId") String computerId,
                                            @RequestParam(required = false, value = "errorMsg") String errorMsg) {
        ModelAndView modelAndView = new ModelAndView();

        Dashboard.setMessage("errorMsg", errorMsg, modelAndView);

        try {
            Validator.validateComputerId(computerId);
            List<Computer> computer = computerService.findById(Integer.parseInt(computerId));
            ComputerDTO computerDTO = computerMapper.fromComputerToComputerDTO(computer.get(0));

            List<Company> companies = companyService.getList();
            List<CompanyDTO> companiesDTO = companies.stream().map(companyMapper :: fromCompanyToCompanyDTO)
                    .collect(Collectors.toList());

            modelAndView.addObject("computer", computerDTO);
            modelAndView.addObject("companies", companiesDTO);
            modelAndView.addObject("computerId", Integer.parseInt(computerId));
            modelAndView.setViewName(editComputer);

        } catch (ValidationException vld) {
            modelAndView.addObject("errorMsg", vld.getMessage());
            modelAndView.setViewName("redirect:/dashboard");
        }

        return modelAndView;
    }

    @PostMapping
    public ModelAndView editComputer(@RequestParam(value = "computerId") String computerId,
                                     @RequestParam(value = "computerName") String computerName,
                                     @RequestParam(required = false, value = "introduced") String introduced,
                                     @RequestParam(required = false, value = "discontinued") String discontinued,
                                     @RequestParam(required = false, value = "companyId") String companyId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("computerId", Integer.parseInt(computerId));
        try {
            Validator.validateFields(computerName, introduced, discontinued, companyId);

            CompanyDTO companyDTO = new CompanyDTO();
            if (!companyId.isBlank()) {
                companyDTO.setId(Integer.parseInt(companyId));
            }
            ComputerDTO computerDTO = new ComputerDTO(computerName, introduced, discontinued, companyDTO);
            computerDTO.setId(Integer.parseInt(computerId));
            Computer computer = computerMapper.fromComputerDTOToComputer(computerDTO);

            computerService.update(computer);

            String message = ShowMessages.SUCCESS_MSG_UPDATE.getMsg();
            modelAndView.addObject("successMsg", message);
            modelAndView.setViewName("redirect:/dashboard");
        } catch (ValidationException vld) {
            modelAndView.addObject("errorMsg", vld.getMessage());
            modelAndView.setViewName("redirect:/editComputer");
        }

        return modelAndView;
    }
}