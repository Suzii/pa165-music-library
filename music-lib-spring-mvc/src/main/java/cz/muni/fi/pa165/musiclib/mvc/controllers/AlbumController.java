package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
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
@RequestMapping(value = {"/album"})
public class AlbumController {

    final static Logger log = LoggerFactory.getLogger(AlbumController.class);
    
    @Inject
    private MessageSource messageSource;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Albums");
        
        // create faked album
        List<AlbumDTO> albums = new ArrayList<>();
        AlbumDTO defaultAlbum = new AlbumDTO();
        defaultAlbum.setId(42l);
        defaultAlbum.setTitle("Default album");
        defaultAlbum.setCommentary("This album is created in controller and serves for testing purposes.");
        albums.add(defaultAlbum);
        
        model.addAttribute("albums", albums);
        return "album/index";
    }
}
