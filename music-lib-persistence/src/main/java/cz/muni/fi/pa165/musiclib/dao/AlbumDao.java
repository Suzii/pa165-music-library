package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Album;
import java.util.List;

/**
 *
 * @author Zuzana Dankovcikova
 */
public interface AlbumDao {
    
    void create(Album album);
    Album update(Album album);
    void remove(Album album) throws IllegalArgumentException;
    Album findById(Long id);
    Album findByTitle(String title);
    List<Album> findAll();
}
