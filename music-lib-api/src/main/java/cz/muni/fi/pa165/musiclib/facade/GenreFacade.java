package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import java.util.List;

/**
 * @author David
 */
public interface GenreFacade {

    /**
    * Creates new genre.
    *
    * @param genre entity to be created
    * @return id of created genre
    */    
    Long create(GenreDTO genre);

    /**
     * Returns all genres.
     *
     * @return list of all genre entities
     */
    List<GenreDTO> getAllGenres();

    /**
     * Returns genre with given id
     * 
     * @param id of searched genre
     * @return genre with given id
     * @throws IllegalArgumentException if musician is null
     */
    GenreDTO getGenreById(Long id);

    /**
     * Returns list of genres with the given title
     *
     * @param title of searched genre
     * @return list of genres with given title
     */
    List<GenreDTO> getGenreByTitle(String title);

    /**
     * Changes title of genre
     * 
     * @param genre to have title changed
     */
    void changeTitle(GenreDTO genre);

    /**
     * Deletes genre 
     * 
     * @param genreId
     */
    void deleteGenre(Long genreId);
}
