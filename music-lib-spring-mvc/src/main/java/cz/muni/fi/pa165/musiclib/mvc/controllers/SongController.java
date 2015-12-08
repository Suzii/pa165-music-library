package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.dto.SongAddYoutubeLinkDTO;
import cz.muni.fi.pa165.musiclib.dto.SongCreateDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.facade.GenreFacade;
import cz.muni.fi.pa165.musiclib.facade.MusicianFacade;
import cz.muni.fi.pa165.musiclib.facade.SongFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * TODO
 *
 * @author Zuzana Dankovcikova
 */
@Controller
@RequestMapping(value = {"/song"})
public class SongController {

    final static Logger log = LoggerFactory.getLogger(SongController.class);
    
    @Inject
    private SongFacade songFacade;
    
    @Inject
    private MusicianFacade musicianFacade;
    
    @Inject
    private GenreFacade genreFacade;
    
    @Inject
    private MessageSource messageSource;

    /**
     * Lists all songs from library. 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Songs");
        
        // create faked song
        List<SongDTO> songs = songFacade.findAll();
                
        model.addAttribute("songs", songs);
        return "song/index";
    }
    
    /**
     * Prepares an empty song create form.
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("songCreate", new SongCreateDTO());
        model.addAttribute("musicians", musicianFacade.getAllMusicians());
        model.addAttribute("genres", genreFacade.getAllGenres());
        return "song/create";
    }
    
    /**
     * Handles submit of song create form.
     * @param formBean
     * @param model
     * @param bindingResult
     * @param redirectAttributes
     * @param uriComponentsBuilder
     * @return 
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            //DO NOT CHANGE the order of first two parameters
            @Valid @ModelAttribute("songCreate") SongCreateDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {
        
        log.debug("create(formBean={})", formBean);

        //if there are any validation errors forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.error("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.error("FieldError: {}", fe);
            }

            model.addAttribute("musicians", musicianFacade.getAllMusicians());
            model.addAttribute("genres", genreFacade.getAllGenres());
            return "/song/create";
        }
        
        //TODO: handle ablumId
        Long albumId = 1l;
        
        //store youtube linnk for song
        Long id = songFacade.create(formBean, albumId);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Song with id " + id + " created");
        return "redirect:" + uriComponentsBuilder.path("/song/detail/{id}").buildAndExpand(id).encode().toUriString();
    }
    
    /**
     * Prepares an empty song create form.
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/addYoutubeLink/{id}"}, method = RequestMethod.GET)
    public String addYoutubeLink(@PathVariable long id, Model model) {
        
        SongDTO song = songFacade.findById(id);
        String title = song.getTitle();
        SongAddYoutubeLinkDTO songModel = new SongAddYoutubeLinkDTO(id);
        songModel.setYoutubeLink(song.getYoutubeLink());
        model.addAttribute("songAddYoutubeLink", songModel);
        model.addAttribute("songTitle", title);
        return "song/addYoutubeLink";
    }
    
    @RequestMapping(value = "/addYoutubeLink/{id}", method = RequestMethod.POST)
    public String addYoutubeLink(
            @Valid @ModelAttribute("songAddYoutubeLink") SongAddYoutubeLinkDTO formBean,
            Model model,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {
        
        log.debug("create(formBean={})", formBean);
        
        //if there are any validation errors forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "/song/addYoutubeLink";
        }
        
        songFacade.addYoutubeLink(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Youtube link successfully added");
        return "redirect:" + uriComponentsBuilder.path("/song/detail/{id}").buildAndExpand(formBean.getSongId()).encode().toUriString();
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        
        SongDTO song = songFacade.findById(id);
        model.addAttribute("song", song);
        return "song/detail";
    }

    private SongDTO getDefaultSongDTOModel() {
        SongDTO defaultSong = new SongDTO(42l);
        defaultSong.setTitle("Default song");
        defaultSong.setCommentary("This song is created in HomeController and serves for testing purposes.");
        defaultSong.setAlbum(null);
        defaultSong.setMusician(null);
        defaultSong.setBitrate(new Double("14.2"));
        defaultSong.setGenre(new GenreDTO());
        defaultSong.setPositionInAlbum(1);
        defaultSong.setYoutubeLink("lp-EO5I60KA");
        return defaultSong;
    }
}
