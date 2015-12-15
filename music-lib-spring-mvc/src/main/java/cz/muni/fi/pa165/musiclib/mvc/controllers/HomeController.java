package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Home controller of whole application. 
 *
 * @author Zuzana Dankovcikova
 */
@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController  extends BaseController{

    final static Logger log = LoggerFactory.getLogger(HomeController.class);
    
    @Inject
    private MessageSource messageSource; //resource bundle provided by Spring

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Music library");
        
        return "home/index";
    }
}
