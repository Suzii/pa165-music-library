package cz.muni.fi.pa165.musiclib.controllers;

import cz.muni.fi.pa165.musiclib.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.musiclib.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.facade.AlbumFacade;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author David Boron
 */
@RestController
@RequestMapping("/albums")
public class AlbumsController {

    final static Logger log = LoggerFactory.getLogger(AlbumsController.class);

    @Inject
    private AlbumFacade albumFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AlbumDTO> getAlbums(@RequestParam(value="title", required=false) String title) {
        if (title != null){
            log.debug("rest getAlbums() with title {}", title);
            return albumFacade.getAlbumByTitle(title);
        }
        log.debug("rest getAlbums()");
        return albumFacade.getAllAlbums();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO getAlbum(@PathVariable("id") long id) throws Exception {

        log.debug("rest getAlbum({})", id);

        //TODO: known bug - songs are listed twice
        
        try {
            AlbumDTO albumDTO = albumFacade.getAlbumById(id);
            return albumDTO;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO createAlbum(@RequestBody AlbumDTO album) throws Exception {

        log.debug("rest createAlbum()");

        try {
            Long id = albumFacade.createAlbum(album);
            return albumFacade.getAlbumById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }
    
    
    
}
