package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.facade.GenreFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;

/**
 * 
 *
 * @author
 */
@Controller
@RequestMapping(value = {"/genre"})
public class GenreController {

    final static Logger log = LoggerFactory.getLogger(GenreController.class);
    
    @Inject
    private MessageSource messageSource;
    
    @Inject
    private GenreFacade genreFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String getGenres(Model model) {
        log.debug("getGenres()");
        model.addAttribute("genres", genreFacade.getAllGenres());
        return "genre/list";
    }
}
