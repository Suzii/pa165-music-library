package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.UserAuthenticationDTO;
import cz.muni.fi.pa165.musiclib.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Martin Stefanko
 * @version 15/12/9
 */
@Controller
public class AuthenticationController extends BaseController{

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
            @RequestParam(value = "error", required = false) String error,
            BindingResult bindingResult,
            Model model,
            @RequestParam(defaultValue = "/home") String redirectTo,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) throws URISyntaxException {

        log.debug("create(formBean={})", formBean);


//        return "redirect:" + redirectTo;
        return "/login";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model) {
        return "error";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        model.addAttribute("user", new UserAuthenticationDTO());
        return "error";
    }

    private void createCookie(HttpServletRequest pHttpRequest, HttpServletResponse pHttpResponse, String pCookieName, String pCookieValue) {
        try {
            Cookie cookie = new Cookie(pCookieName, pCookieValue);
            URL url = new URL(pHttpRequest.getRequestURL().toString());
            cookie.setDomain(url.getHost());
            cookie.setPath("/*");
            cookie.setMaxAge(-1);

            pHttpResponse.addCookie(cookie);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
