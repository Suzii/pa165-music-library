package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
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

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Genres");
        
        // create faked genre
        List<GenreDTO> genres = new ArrayList<>();
        GenreDTO defaultGenre = new GenreDTO(42l);
        defaultGenre.setTitle("Default gene");
        genres.add(defaultGenre);
        
        model.addAttribute("genres", genres);
        return "genre/list";
    }
}
