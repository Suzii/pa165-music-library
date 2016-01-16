package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.facade.AlbumFacade;
import cz.muni.fi.pa165.musiclib.facade.MusicianFacade;
import cz.muni.fi.pa165.musiclib.facade.SongFacade;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private MessageSource messageSource; //resource bundle provided by Spring;
    
    @Inject
    private AlbumFacade albumFacade;
    
    @Inject
    private MusicianFacade musicianFacade;

    @Inject
    private SongFacade songFacade;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Music library");
        
        List<AlbumDTO> albums = albumFacade.getAllAlbums();
        Collections.shuffle(albums);
        model.addAttribute("albums", albums.subList(0, 5));
        
        List<SongDTO> songs = songFacade.findAll();
        Collections.shuffle(songs);
        model.addAttribute("songs", songs.subList(0, 10));
        
        List<MusicianDTO> musicians = musicianFacade.getAllMusicians();
        Collections.shuffle(musicians);
        model.addAttribute("musicians", musicians.subList(0, 10));
        
        return "home/index";
    }
}
