package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.facade.AlbumFacade;
import cz.muni.fi.pa165.musiclib.facade.MusicianFacade;
import cz.muni.fi.pa165.musiclib.facade.SongFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Home controller of whole application. 
 *
 * @author Zuzana Dankovcikova
 * @version 15/12/12
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
        
        List<AlbumDTO> albums = albumFacade.getAlbumSample(5);
        model.addAttribute("albums", albums);

        List<SongDTO> songs = songFacade.findAll();
        Collections.shuffle(songs);
        int toIndexSongs = Math.min(10, songs.size());
        model.addAttribute("songs", songs.subList(0, toIndexSongs));
        
        List<MusicianDTO> musicians = musicianFacade.getAllMusicians();
        Collections.shuffle(musicians);
        int toIndexMusicians = Math.min(10, musicians.size());
        model.addAttribute("musicians", musicians.subList(0, toIndexMusicians));
        
        return "home/index";
    }
}
