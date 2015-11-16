package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.Musician;
import java.util.List;

/**
 * Musician service that provides all business logic for musician entities. 
 * 
 * @author zdank
 * @version 16/11/2015
 */
public interface MusicianService {
    /**
     * Creates new musician.
     * 
     * @param musician entity to be created
     */
    void create(Musician musician);
    
    /**
     * Updates musician.
     * 
     * @param musician entity to be updated
     * @return updated musician entity
     */
    Musician update (Musician musician);
    
    /**
     * Removes musician.
     * 
     * @param musician to be removed
     */
    void remove(Musician musician);
    
    /**
     * Returns musician entity identified by given id.
     * 
     * @param id unique identifier of the musician
     * @return musician entity with given id
     */
    Musician findById(Long id);
    
    /**
     * Returns all musicians according to a given artist name.
     * 
     * @param artistName artist name of the musician
     * @return list of the musician with given name or empty list if no artist was found
     */
    List<Musician> findByArtistName(String artistName);
    
    /**
     * Returns all musicians.
     * @return list of all persisted musicians
     */
    List<Musician> findAll();
}
