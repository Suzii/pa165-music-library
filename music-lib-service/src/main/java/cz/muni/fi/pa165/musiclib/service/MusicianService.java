package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.Musician;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Musician service that provides all business logic for musician entities. 
 * 
 * @author zdank
 * @version 16/11/2015
 */
@Service
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
     * Removes musician and all songs associated with him.
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
     * Returns all musicians containing given artist name fragment in their name.
     * 
     * @param artistName artist name fragment of the musician to look for
     * @return list of the musician containing given name or empty list if no artist was found
     */
    List<Musician> searchByArtistName(String artistName);
    
    /**
     * Returns all musicians.
     * @return list of all persisted musicians
     */
    List<Musician> findAll();
}
