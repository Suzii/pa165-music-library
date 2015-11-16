package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author zdank
 */
public class SongServiceImpl implements SongService {

    @Inject
    private SongDao songDao;
    
    @Override
    public void create(Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Song update(Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Song findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Song> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Song> findByAlbum(Album album) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Song> findByMusician(Musician musician) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Song> findByGenre(Genre genre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
