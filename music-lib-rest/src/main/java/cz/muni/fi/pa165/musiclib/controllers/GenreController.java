package cz.muni.fi.pa165.musiclib.controllers;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.musiclib.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.musiclib.exceptions.ResourceNotModifiedException;
import cz.muni.fi.pa165.musiclib.facade.GenreFacade;
import cz.muni.fi.pa165.musiclib.rest.ApiUris;
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
 * @author Milan Seman
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_GENRES)
public class GenreController {

    final static Logger log = LoggerFactory.getLogger(GenreController.class);

    @Inject
    private GenreFacade genreFacade;

    /**
     * Get list of all Genres 
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/genres
     *
     * @return List<GenreDTO>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<GenreDTO> getGenres(@RequestParam(value = "title", required = false) String title) {

        List<GenreDTO> genres;

        if (title != null) {
            log.debug("rest getGenres() with title {}", title);
            genres = genreFacade.getGenreByTitle(title);
        } else {
            log.debug("rest getGenres()");
            genres = genreFacade.getAllGenres();
        }
        return genres;
    }

    /**
     * Get single Genre by identifier ID 
     * curl -i -X GET 
     * http://localhost:8080/pa165/rest/genres/1
     *
     * @param id genre id
     * @return GenreDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final GenreDTO getGenre(@PathVariable("id") long id) throws Exception {

        log.debug("rest getGenre({})", id);

        try {
            GenreDTO genreDTO = genreFacade.getGenreById(id);
            return genreDTO;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Creates new Genre by POST method
     * curl -i -X POST
     * http://localhost:8080/pa165/rest/genres/1
     *
     * @param id genre id
     * @param genre GenreDTO with required fields for creation
     * @return GenreDTO created genre
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final GenreDTO createGenre(@RequestBody GenreDTO genre) throws Exception {

        log.debug("rest createGenre()");

        try {
            Long id = genreFacade.create(genre);
            return genreFacade.getGenreById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }
    
    /**
     * Updates genre by PUT method
     * curl -i -X PUT -H "Content-Type: application/json" 
     * --data '{"title":"Zmena nazvu"}' 
     * http://localhost:8080/pa165/rest/genres/4
     *
     * @param id genre id
     * @param genre GenreDTO with required fields for update
     * @throws ResourceNotFoundException
     * @throws ResourceNotModifiedException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final GenreDTO updateGenre(@PathVariable("id") long id, @RequestBody GenreDTO editedGenre) throws Exception {

        log.debug("rest editGenre()");

        try {
            GenreDTO genre = genreFacade.getGenreById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
        
        try{
            editedGenre.setId(id);
            genreFacade.changeTitle(editedGenre);
        }catch(Exception ex){
            throw new ResourceNotModifiedException();
        }
        
        return editedGenre;
    }

    /**
     * Deletes genre by ID
     * curl -i -X DELETE 
     * http://localhost:8080/pa165/rest/genres/4
     *
     * @param id genre id
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void removeGenre(@PathVariable("id") long id) throws Exception {

        log.debug("rest removeGenre({})", id);

        try {
            genreFacade.deleteGenre(id);
        } catch (Exception ex) {
            log.debug(ex.toString());
            throw new ResourceNotFoundException();
        }
    }

}

