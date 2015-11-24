package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import java.util.List;

/**
 * @author xseman
 */
public interface MusicianFacade {
    
    /**
    * Creates new musician.
    *
    * @param musician entity to be created
    * @return id of newly created song
    */    
    Long createMusician(MusicianDTO musician);
    
    /**
     * Updates musician 
     * 
     * @param musician
     */
    void updateMusician (MusicianDTO musician);
    
    /**
     * Deletes musician 
     * 
     * @param musicianId
     */
    void removeMusician (Long musicianId);

    /**
     * Returns all musicians.
     * 
     * @return list of all musician entities
     */
    List<MusicianDTO> getAllMusicians ();

    /**
     * Returns musician according to given id
     * 
     * @param musicianId
     * @return musician identified by unique id
     * @throws IllegalArgumentException if musician is null
     */
    MusicianDTO getMusicianById(Long musicianId);

    /**
     * Finds and returns all the musicians with atrist names containing given string.
     * 
     * @param artistName
     * @return list of all the musicians whose names contain given string
     * @throws IllegalArgumentException if the artist name si null
     */
    List <MusicianDTO> getMusicianByArtistName (String artistName);

}
