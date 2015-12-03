package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.enums.Sex;
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
 * TODO
 *
 * @author
 */
@Controller
@RequestMapping(value = {"/musician"})
public class MusicianController {

    final static Logger log = LoggerFactory.getLogger(MusicianController.class);
    
    @Inject
    private MessageSource messageSource;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Musicians");
        
        // create faked musician
        List<MusicianDTO> musicians = new ArrayList<>();
        MusicianDTO defaultMusician = new MusicianDTO(42l);
        defaultMusician.setArtistName("Default musician");
        defaultMusician.setSex(Sex.MALE);
        
        musicians.add(defaultMusician);
        
        model.addAttribute("musicians", musicians);
        return "musician/index";
    }
}
