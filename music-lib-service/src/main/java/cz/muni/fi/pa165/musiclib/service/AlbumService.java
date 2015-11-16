package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.Album;
import java.util.List;

/**
 * Album service that provides all business logic for album entities. 
 * 
 * @author zdank
 * @version 16/11/2015
 */
public interface AlbumService {
    
    /**
     * Created new album.
     * 
     * @param album entity to be created
     */
    void create(Album album);
    
    /**
     * Updates album.
     *
     * @param album entity to be updated
     * @return updated album entity
     */
    Album update(Album album);
    
    /**
     * Removes album.
     * 
     * @param album entity to be removed
     * @throws IllegalArgumentException if passed album is null or is not stored in DB
     */
    void remove(Album album) throws IllegalArgumentException;
    
    /**
     * Get album entity by unique ID.
     * 
     * @param id id of album entity
     * @return album entity with given id, null if no album with this id exists
     */
    Album findById(Long id);
    
    /**
     * Returns all albums with given title
     * 
     * @param title title of albums to look for
     * @return list of all album entities with given title, empty if no such album exists
     */
    List<Album> findByTitle(String title);
    
    /**
     * Returns all albums.
     * 
     * @return list of all albums
     */
    List<Album> findAll();
}
