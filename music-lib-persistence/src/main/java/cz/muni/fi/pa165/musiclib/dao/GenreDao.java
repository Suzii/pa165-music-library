package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Genre;
import java.util.List;

/**
 *
 * @author David Boron
 */
public interface GenreDao {

    /**
     * Creates genre and persists it to database
     * 
     * @param genre entity to be persisted
     */
    void create(Genre genre);

    /**
     * Updates genre in database
     * 
     * @param genre entity to be updated
     * @return updated persisted entity 
     */
    Genre update(Genre genre);

    /**
     * Removes entity from persistence
     * 
     * @param genre entity to be removed
     * @throws IllegalArgumentException if passed genre is null or is not stored in DB
     */
    void remove(Genre genre);

    /**
     * Get genre by unique id
     * 
     * @param id of entity
     * @return object with given id
     */
    Genre findById(Long id);

    /**
     * Get genres with given name
     * 
     * @param title of genre to be found
     * @return list of genre with given title
     */
    List<Genre> findByTitle(String title);

    /**
     * Get all genres
     * 
     * @return list of all genres
     */
    List<Genre> findAll();

}
