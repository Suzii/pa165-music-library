package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.facade.UserFacade;
import static cz.muni.fi.pa165.musiclib.mvc.controllers.SongController.log;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Base controller containing common logic for all controllers.
 *
 * @author Zuzana Dankovcikova
 * @version 12/12/2015
 */
public abstract class BaseController {

    final static Logger log = LoggerFactory.getLogger(BaseController.class);
    
    @Inject
    UserFacade userFacade;

    protected void addValidationErrors(BindingResult bindingResult, Model model) {
        for (ObjectError ge : bindingResult.getGlobalErrors()) {
            log.error("ObjectError: {}", ge);
        }
        for (FieldError fe : bindingResult.getFieldErrors()) {
            model.addAttribute(fe.getField() + "_error", true);
            log.error("FieldError: {}", fe);
        }
    }

    @ModelAttribute("user")
    public UserDTO user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()){
            return null;
        }
        
        String name = authentication.getName();
        
        if (name == null || name.equals("anonymousUser")){
            return null;
        }
        
        UserDTO user = userFacade.findUserByEmail(name);
        return user;
    }

    @ModelAttribute("isAdmin")
    public boolean userIsAdmin(HttpServletRequest request) {
        return request.isUserInRole("ROLE_ADMIN");
    }
}
