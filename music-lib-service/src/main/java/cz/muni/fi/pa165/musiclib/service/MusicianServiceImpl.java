package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.MusicianDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Milan Seman
 * @version 15/11/17
 */
@Service
public class MusicianServiceImpl implements MusicianService {

    @Inject
    private MusicianDao musicianDao;
    
    @Inject
    private SongDao songDao;
    
    @Override
    public void create(Musician musician) {
        try {
            musicianDao.create(musician);
        } catch(IllegalArgumentException | ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("musician create error", ex);
        }
    }

    @Override
    public Musician update(Musician musician) {
        try {
            return musicianDao.update(musician);
        } catch(IllegalArgumentException | ConstraintViolationException | PersistenceException ex) {
           throw new MusicLibDataAccessException("musician update error", ex);
        }
    }

    
    @Override
    public void remove(Musician musician) {
        if(musician == null) {
            throw new MusicLibDataAccessException("musician cannot be null");
        }
        try {
            for(Song song : musician.getSongs()) {
                songDao.remove(song);
            }
            musicianDao.remove(musician);
        } catch(IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("musician remove error", ex);
        }
    }

    @Override
    public Musician findById(Long id) {
        try {
        return musicianDao.findById(id);
        }  catch(IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("musician find error", ex);
        }
    }

    @Override
    public List<Musician> searchByArtistName(String artistName) {
        if(artistName == null) {
            throw new IllegalArgumentException("artistName cannot be null");
        }
        
        try {
        return musicianDao.searchByArtistName(artistName);
        }  catch(IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("musician find by artist name error", ex);
        }
    }

    @Override
    public List<Musician> findAll() {
        try {
        return musicianDao.findAll();
        } catch (IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("musician find all error", ex);
        }
    }

    
}
