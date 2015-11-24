package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.MusicianDao;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author milan
 */
public class MusicianServiceImpl implements MusicianService {

    @Inject
    private MusicianDao musicianDao;
    
    @Override
    public void create(Musician musician) {
        musicianDao.create(musician);
    }

    @Override
    public Musician update(Musician musician) {
        return musicianDao.update(musician);
    }

    
    @Override
    public void remove(Musician musician) {
        musicianDao.remove(musician);
    }

    @Override
    public Musician findById(Long id) {
        return musicianDao.findById(id);
    }

    @Override
    public List<Musician> findByArtistName(String artistName) {
        return musicianDao.findByArtistName(artistName);
    }

    @Override
    public List<Musician> findAll() {
        return musicianDao.findAll();
    }

    
}
