package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Musician;
import java.util.List;
import javax.validation.ConstraintViolationException;

/**
 * @author Milan
 * @version 10/30/15
 */

public interface MusicianDao {
    
    /**
     * Persists new musician into DB
     * 
     * @param musician entity to be persisted
     * @throws ConstraintViolationException if any constraint on columns is violated
     */
    void create(Musician musician) throws ConstraintViolationException;
    
    /**
     * Updates already peristed musician entity in the DB
     * 
     * @param musician persisted entity to be updated
     * @return the merged object attached to the EM
     * @throws ConstraintViolationException if any constraint on columns is violated
     */
    Musician update (Musician musician) throws ConstraintViolationException;
    
    /**
     * Removes the musician entity form persistence context
     * 
     * @param musician to be removed
     * @throws IllegalArgumentException if parameter musician is null or not in a DB
     */
    void remove(Musician musician) throws IllegalArgumentException;
    
    /**
     * Returns musician entity identified by given id
     * 
     * @param id unique identifier of the musician
     * @return entity with given id
     */
    Musician findById(Long id);
    
    /**
     * Returns all musicians containing given artist name fragment in their name
     * 
     * @param artistNameFragment fragment of artist name to look for
     * @return list of all musician entities containing given artist name fragment, empty if no such musician exists
     */
    List<Musician> searchByArtistName(String artistNameFragment);
    
    /**
     * Returns all artists which are persisted in the database
     * @return list of all persisted musicians
     */
    List<Musician> findAll();
        
}
