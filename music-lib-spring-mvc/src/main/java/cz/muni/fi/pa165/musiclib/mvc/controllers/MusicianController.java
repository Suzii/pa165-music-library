package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.facade.MusicianFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 * TODO
 *
 * @author Milan Seman
 */
@Controller
@RequestMapping(value = {"/musician"})
public class MusicianController {

    final static Logger log = LoggerFactory.getLogger(MusicianController.class);
    
    private MusicianFacade musicianFacade;
    
    @Inject
    private MessageSource messageSource;
    
    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Musicians");
        
        // create faked musician
        List<MusicianDTO> musicians = new ArrayList<>();
        MusicianDTO defaultMusician = getDefaultMusicianDTOModel();
        
        musicians.add(defaultMusician);
        
        model.addAttribute("musicians", musicians);
        return "musician/index";
    }
    
    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("musicianCreate", new MusicianDTO());
        return "musician/create";
    }
    
    private MusicianDTO getDefaultMusicianDTOModel() {
        MusicianDTO defMusician = new MusicianDTO(90l);
        defMusician.setArtistName("Default name");
        defMusician.setDateOfBirth(new Date());
        defMusician.setSex(Sex.MALE);
        defMusician.setSongs(null);
        return defMusician;
        
    }
}
