package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Genre service that provides all business logic for genre entities.
 *
 * @author Zuzana Dankovcikova
 * @version 15/11/16
 */
@Service
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
     * Removes genre and all songs associated with it.
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
     * Returns all genres containing given title fragment
     * 
     * @param title title fragment of genres to look for
     * @return list of all genres entities containing given title fragment, empty if no such genre exists
     */
    List<Genre> searchByTitle(String title);

    /**
     * Get all genres
     *
     * @return list of all genres
     */
    List<Genre> findAll();

    /**
     * Changes title of given genre
     *
     * @param genre object to have title changed
     * @param title new title
     */
    void changeTitle(Genre genre, String title);
}
