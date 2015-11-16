package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.Genre;
import java.util.List;

/**
 * Genre service that provides all business logic for genre entities. 
 * 
 * @author zdank
 * @version 16/11/2015
 */
public interface GenreService {
    
    /**
     * Creates genre new genre.
     * 
     * @param genre entity to be created
     */
    void create(Genre genre);

    /**
     * Updates genre.
     * 
     * @param genre entity to be updated
     * @return updated entity 
     */
    Genre update(Genre genre);

    /**
     * Removes genre.
     * 
     * @param genre entity to be removed
     * @throws IllegalArgumentException if passed genre is null or is not stored
     */
    void remove(Genre genre);

    /**
     * Get genre by unique id
     * 
     * @param id of entity
     * @return entity with given id
     */
    Genre findById(Long id);

    /**
     * Get genre with given name
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
