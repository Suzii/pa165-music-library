package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Genre;
import java.util.List;

/**
 *
 * @author Dido
 */
public interface GenreDao {
    
    void create(Genre genre);
    
    Genre update (Genre genre);
    
    void remove(Genre genre) throws IllegalArgumentException;
    
    Genre findById(Long id);
    
    Genre findByTitle (String title);
    
    List<Genre> findAll();
        
}