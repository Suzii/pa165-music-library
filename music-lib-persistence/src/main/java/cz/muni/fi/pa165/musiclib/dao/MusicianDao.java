package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Musician;
import java.util.List;

/**
 *
 * @author Milan
 */

public interface MusicianDao {
    
    void create(Musician musician);
    
    Musician update (Musician musician);
    
    void remove(Musician musician) throws IllegalArgumentException;
    
    Musician findById(Long id);
    
    List<Musician> findByArtistName(String artistName);
    
    List<Musician> findAll();
        
}
