package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.UserAuthenticationDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/9/15
 */
@Controller
public class AuthenticationController {

    final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Inject
    private UserFacade userFacade;

    @Inject
    private HttpServletRequest request;

    @Inject
    private HttpServletResponse response;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("user", new UserAuthenticationDTO());
        log.error("----------GET LOGIN");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            //DO NOT CHANGE the order of first two parameters
            @Valid @ModelAttribute("user") UserAuthenticationDTO formBean,
            BindingResult bindingResult,
            Model model,
            @RequestParam(defaultValue = "/home") String redirectTo,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {

        log.error("----------POST LOGIN");
        log.debug("create(formBean={})", formBean);

        String logname = formBean.getEmail();
        String password = formBean.getPassword();

        UserDTO matchingUser = userFacade.findUserByEmail(logname);
//        if (matchingUser == null) {
//            log.warn("no user with email {}", logname);
////            response401(response);
////            return;
//        }
//
//        UserAuthenticationDTO userAuthenticateDTO = new UserAuthenticationDTO();
//        userAuthenticateDTO.setUserId(matchingUser.getId());
//        userAuthenticateDTO.setPassword(password);
//
//        if (!userFacade.authenticate(userAuthenticateDTO)) {
//            log.warn("invalid credentials: user={} password={}", logname, password);
////            response401(response);
////            return;
//        }
//        request.setAttribute("authenticatedUser", matchingUser);
        response.setHeader("Authorization", "asdasd");




//        if (bindingResult.hasErrors()) {
//            for (ObjectError ge : bindingResult.getGlobalErrors()) {
//                log.trace("ObjectError: {}", ge);
//            }
//            for (FieldError fe : bindingResult.getFieldErrors()) {
//                model.addAttribute(fe.getField() + "_error", true);
//                log.trace("FieldError: {}", fe);
//            }
//            return "login";
//        }

//        return "redirect:" + redirectTo;
        return "/home/index";
    }
}
