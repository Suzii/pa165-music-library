package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Album;
import java.util.List;
import javax.validation.ConstraintViolationException;

/**
 * @author Zuzana Dankovcikova
 * @version 15/10/29
 */
public interface AlbumDao {
    
    /**
     * Persists new album into DB
     * 
     * @param album entity to be persisted
     * @throws ConstraintViolationException if any constraint on
     * columns is violated
     */
    void create(Album album) throws ConstraintViolationException;
    
    /**
     * Updates already persisted album entity in the DB
     *
     * @param album entity to be updated
     * @return the merged album entity attached to the EM
     * @throws ConstraintViolationException if any constraint on
     * columns is violated
     */
    Album update(Album album) throws ConstraintViolationException;
    
    /**
     * Removes album entity from database
     * 
     * @param album entity to be removed
     * @throws IllegalArgumentException if passed album is null or is not stored in DB
     */
    void remove(Album album) throws IllegalArgumentException;
    
    /**
     * Retrieves album entity with given id from D
     * 
     * @param id id of album entity to be retrieved
     * @return album entity with given id, null if no album with this id exists
     */
    Album findById(Long id);
    
    /**
     * Returns all albums containing given title fragment in their title
     * 
     * @param titleFragment title fragment of albums to look for
     * @return list of all album entities containing given title fragment , empty if no such album exists
     */
    List<Album> searchByTitle(String titleFragment);
    
    /**
     * Returns all albums in DB
     * 
     * @return list of all albums stored in DB
     */
    List<Album> findAll();

    /**
     * Returns the sample of albums stored in the DB. Returns up
     * to count albums or less if there is no sufficient ammount.
     *
     * @param count number of albums to return
     * @return list of sample albums
     */
    List<Album> getAlbumSample(int count);
}
