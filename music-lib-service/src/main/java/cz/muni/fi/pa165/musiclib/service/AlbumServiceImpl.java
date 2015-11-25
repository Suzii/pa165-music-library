package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Martin Stefanko (mstefank@redhat.com)
 */
public class AlbumServiceImpl implements AlbumService {

    @Inject
    private AlbumDao albumDao;

    @Inject
    private SongDao songDao;
    
    @Override
    public void create(Album album) {
        try {
            albumDao.create(album);
        } catch(ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("album create error", ex);
        }
    }

    @Override
    public Album update(Album album) {
        try {
            return albumDao.update(album);
        } catch (ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("album update error", ex);
        }
    }

    @Override
    public void remove(Album album) throws IllegalArgumentException {
        try {
            albumDao.remove(album);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("album remove error", ex);
        }
    }

    @Override
    public Album findById(Long id) {
        try {
            return albumDao.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("album find error", ex);
        }
    }

    @Override
    public List<Album> findByTitle(String title) {
        try {
            return albumDao.findByTitle(title);
        } catch (IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("album find by title error", ex);
        }
    }

    @Override
    public List<Album> findAll() {
        try {
            return albumDao.findAll();
        } catch (IllegalArgumentException ex) {
            throw new MusicLibDataAccessException("album find all error", ex);
        }
    }

    @Override
    public void addSong(Album album, Song song) {
        if (album.getSongs().contains(song)) {
            throw new MusicLibServiceException("Album already contains this song; Album: "
                + album.getId() + ", song: " + song.getId());
        }
        album.addSong(song);
    }

    @Override
    public void removeSong(Album album, Song song) {
        if (!album.getSongs().contains(song)) {
            throw new MusicLibServiceException("Album does not contains the desired song; Album: "
                    + album.getId() + ", song: " + song.getId());
        }
        album.removeSong(song);
    }

    @Override
    public void changeTitle(Album album, String title) {
        album.setTitle(title);
    }

}
