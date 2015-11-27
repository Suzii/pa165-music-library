package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author zdank
 * @version 26/11/2015
 */
public class SongServiceImpl implements SongService {

    @Inject
    private SongDao songDao;
    
    @Inject
    private AlbumDao albumDao;

    @Override
    public void create(Song song) {
        try {
            song.setPositionInAlbum(getFirstFreePositionInAlbum(song));
            songDao.create(song);
        } catch (IllegalArgumentException | ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song create error", ex);
        }
    }


    @Override
    public Song update(Song song) {
        try {
            if(song.getPositionInAlbum() == 0){
                song.setPositionInAlbum(getFirstFreePositionInAlbum(song));
            }
            else if(!isDesiredPositionFreeOnAlbum(song)){
                throw new MusicLibServiceException("position on album is not free");
            }            
            return songDao.update(song);
        } catch (IllegalArgumentException | ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song update error", ex);
        }
    }

    @Override
    public Song findById(Long id) {
        try {
            return songDao.findById(id);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song findById error", ex);
        }
    }

    @Override
    public List<Song> findAll() {
        try {
            return songDao.findAll();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song findAll error", ex);
        }
    }

    @Override
    public List<Song> findByAlbum(Album album) {
        try {
            return songDao.findByAlbum(album);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song find by album error", ex);
        }
    }

    @Override
    public List<Song> findByMusician(Musician musician) {
        try {
            return songDao.findByMusician(musician);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song find by musician error", ex);
        }
    }

    @Override
    public List<Song> findByGenre(Genre genre) {
        try {
            return songDao.findByGenre(genre);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song find by genre error", ex);
        }
    }

    @Override
    public void remove(Song song) {
        try {
            // TODO : when to call removeSong on album ???
            songDao.remove(song);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song remove error", ex);
        }
    }
    
    private int getFirstFreePositionInAlbum(Song song) {
        if (song.getAlbum() == null) {
            return 0;
        }
        
        Album album = albumDao.findById(song.getAlbum().getId());
        if (album == null) {
            return 0;
        }
        
        boolean[] occupiedPositions = new boolean[album.getSongs().size()];
        
        for(Song s : album.getSongs()){
            if(s.getPositionInAlbum() > 0){
                occupiedPositions[s.getPositionInAlbum()-1] = true;
            }
        }
        
        for(int i = 0; i < occupiedPositions.length; i++){
            if(!occupiedPositions[i]){
                return i+1;
            }
        }
        return album.getSongs().size()+1;
    }

    private boolean isDesiredPositionFreeOnAlbum(Song song) {
        Album album = albumDao.findById(song.getAlbum().getId());
        for(Song s : album.getSongs()){
            if(!s.equals(song) && s.getPositionInAlbum() == song.getPositionInAlbum()){
                return false;
            }
        }        
        return true;
    }
}
