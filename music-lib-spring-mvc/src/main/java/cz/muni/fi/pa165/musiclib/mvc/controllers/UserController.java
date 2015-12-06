package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/6/15
 */
@Controller
@RequestMapping("/user")
public class UserController {

    final static Logger log = LoggerFactory.getLogger(UserController.class);

//    @Inject
//    private UserFacade userFacade;
//
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String list(Model model) {
//        model.addAttribute("users", userFacade.getAllUsers());
//        return "user/list";
//    }

}
