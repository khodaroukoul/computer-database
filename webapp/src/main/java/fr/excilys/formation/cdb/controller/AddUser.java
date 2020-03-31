package fr.excilys.formation.cdb.controller;

import fr.excilys.formation.cdb.dao.UserDAO;
import fr.excilys.formation.cdb.service.ComputerUserDetailsService;
import fr.excilys.formation.cdb.validator.ShowMessages;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/addUser")
public class AddUser {

    private final ComputerUserDetailsService userService;

    public AddUser(UserDAO userDao) {
        this.userService = new ComputerUserDetailsService(userDao);
    }

    @GetMapping
    public ModelAndView register() {
        return new ModelAndView("addUser");
    }

    @PostMapping
    @Transactional
    public ModelAndView register(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("role") String role) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        boolean isAdded = userService.addNewUser(username, password, role);

        setMessage(modelAndView, isAdded);

        return modelAndView;
    }

    private void setMessage(ModelAndView modelAndView, boolean isAdded) {
        String message;
        if (isAdded) {
            message = ShowMessages.SUCCESS_MSG_CREATE_USER.getMsg();
            modelAndView.addObject("successMsg", message);
        } else {
            message = ShowMessages.ERROR_MSG_CREATE_USER.getMsg();
            modelAndView.addObject("errorMsg", message);
        }
    }
}