package cz.muni.fi.pa165.musiclib.service;

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
import org.springframework.stereotype.Service;

/**
 *
 * @author Zuzana Dankovcikova
 * @version 26/11/2015
 */
@Service
public class SongServiceImpl implements SongService {

    @Inject
    private SongDao songDao;

    @Inject
    private AlbumService albumService;

    @Override
    public void create(Song song) {
        if (song == null) {
            throw new MusicLibDataAccessException("song cannot be null");
        }

        try {
            if (song.getAlbum() == null) {
                song.setPositionInAlbum(0);
            } else {
                song.setPositionInAlbum(getFirstFreePositionInAlbum(song));
            }
            songDao.create(song);
        } catch (MusicLibServiceException ex) {
            song.setAlbum(null);
            song.setPositionInAlbum(0);
            throw ex;
        } catch (IllegalArgumentException | ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song create error", ex);
        }
    }

    @Override
    public Song update(Song song) {
        if (song == null) {
            throw new MusicLibDataAccessException("song cannot be null");
        }

        try {
            if (song.getAlbum() == null) {
                song.setPositionInAlbum(0);
            } else if (song.getPositionInAlbum() == 0) {
                int newPosition = getFirstFreePositionInAlbum(song);
                song.setPositionInAlbum(newPosition);
            } else if (!isDesiredPositionFreeOnAlbum(song)) {
                throw new MusicLibServiceException("Position on album is not free");
            }
            
            return songDao.update(song);
        } catch (MusicLibServiceException ex) {
            song.setAlbum(null);
            song.setPositionInAlbum(0);
            throw ex;
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
    public List<Song> searchByTitle(String titleFragment){
        try {
            return songDao.searchByTitle(titleFragment);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song findByTitleFragment error", ex);
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
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        try {
            if (song.getAlbum() != null) {
                song.getAlbum().removeSong(song);
            }

            if (song.getMusician() != null) {
                song.getMusician().removeSong(song);
            }
            songDao.remove(song);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new MusicLibDataAccessException("song remove error", ex);
        }
    }

    private int getFirstFreePositionInAlbum(Song song) {
        Album album = song.getAlbum();
        if (album == null) {
            return 0;
        }
        
        int maxPositionInAlbum = getMaxPositionInAlbum(album);
        boolean[] occupiedPositions = new boolean[maxPositionInAlbum];

        for (Song s : album.getSongs()) {
            if (s.getPositionInAlbum() > 0) {
                occupiedPositions[s.getPositionInAlbum() - 1] = true;
            }
        }

        for (int i = 0; i < occupiedPositions.length; i++) {
            if (!occupiedPositions[i]) {
                return i + 1;
            }
        }
        return maxPositionInAlbum + 1;
    }

    private int getMaxPositionInAlbum(Album album) {
        int maxPosition = album.getSongs().size();
        for(Song song : album.getSongs()) {
            if(song.getPositionInAlbum() > maxPosition) {
                maxPosition = song.getPositionInAlbum();
            }
        }
        
        return maxPosition;
    }

    private boolean isDesiredPositionFreeOnAlbum(Song song) {
        Album album = song.getAlbum();
        for (Song s : album.getSongs()) {
            if (!s.equals(song) && s.getPositionInAlbum() == song.getPositionInAlbum()) {
                return false;
            }
        }
        return true;
    }
}
