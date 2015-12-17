package cz.muni.fi.pa165.musiclib.controllers;

import cz.muni.fi.pa165.musiclib.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.musiclib.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.exceptions.ResourceNotModifiedException;
import cz.muni.fi.pa165.musiclib.facade.AlbumFacade;
import cz.muni.fi.pa165.musiclib.facade.SongFacade;
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

    @Inject
    private SongFacade songFacade;

    /**
     * Get list of all Albums 
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/albums
     *
     * @return List<AlbumDTO>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AlbumDTO> getAlbums(@RequestParam(value = "title", required = false) String title) {

        List<AlbumDTO> albums;

        if (title != null) {
            log.debug("rest getAlbums() with title {}", title);
            albums = albumFacade.getAlbumByTitle(title);
        } else {
            log.debug("rest getAlbums()");
            albums = albumFacade.getAllAlbums();
        }

        // fix bug: songs twice
        for (AlbumDTO album : albums) {
            album.setSongs(songFacade.findByAlbum(album.getId()));
        }
        return albums;
    }

    /**
     * Get single Album by identifier ID 
     * curl -i -X GET 
     * http://localhost:8080/pa165/rest/albums/1
     *
     * @param id album id
     * @return AlbumDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO getAlbum(@PathVariable("id") long id) throws Exception {

        log.debug("rest getAlbum({})", id);

        try {
            AlbumDTO albumDTO = albumFacade.getAlbumById(id);
        // fix bug: songs twice
            albumDTO.setSongs(songFacade.findByAlbum(id));
            return albumDTO;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Creates new Album by POST method
     * curl -i -X GET 
     * http://localhost:8080/pa165/rest/albums/1
     *
     * @param id album id
     * @param album AlbumDTO with required fields for creation
     * @return AlbumDTO created album
     * @throws ResourceAlreadyExistingException
     */
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
    
    /**
     * Updates album by PUT method
     * curl -i -X PUT -H "Content-Type: application/json" 
     * --data '{"title":"Zmena nazvu","commentary":"Iny komentar k albumu"}' 
     * http://localhost:8080/pa165/rest/albums/4
     *
     * @param id album id
     * @param album AlbumDTO with required fields for update
     * @throws ResourceNotFoundException
     * @throws ResourceNotModifiedException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AlbumDTO updateAlbum(@PathVariable("id") long id, @RequestBody AlbumDTO editedAlbum) throws Exception {

        log.debug("rest editAlbum()");

        try {
            AlbumDTO album = albumFacade.getAlbumById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
        
        try{
            editedAlbum.setId(id);
            albumFacade.update(editedAlbum);
        }catch(Exception ex){
            throw new ResourceNotModifiedException();
        }
        
        return editedAlbum;
    }

    /**
     * Deletes album by ID
     * curl -i -X DELETE 
     * http://localhost:8080/pa165/rest/albums/4
     *
     * @param id album id
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void removeAlbum(@PathVariable("id") long id) throws Exception {

        log.debug("rest removeAlbum({})", id);

        try {
            albumFacade.deleteAlbum(id);
        } catch (Exception ex) {
            log.debug(ex.toString());
            throw new ResourceNotFoundException();
        }
    }

}
