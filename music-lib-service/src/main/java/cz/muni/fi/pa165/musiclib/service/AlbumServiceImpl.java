package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author zdank
 */
public class AlbumServiceImpl implements AlbumService {

    @Inject
    private AlbumDao albumDao;
    
    @Override
    public void create(Album album) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Album update(Album album) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Album album) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Album findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Album> findByTitle(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Album> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
