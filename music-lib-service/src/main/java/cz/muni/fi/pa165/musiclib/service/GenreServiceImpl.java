package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.GenreDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Boron
 */
@Service
public class GenreServiceImpl implements GenreService {

    @Inject
    private GenreDao genreDao;
    
    @Inject
    private SongDao songDao;

    @Override
    public void create(Genre genre) {
        try {
            genreDao.create(genre);
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException | NullPointerException e) {
            throw new MusicLibDataAccessException("genre create error", e);
        }
    }

    @Override
    public Genre update(Genre genre) {
        try {
            return genreDao.update(genre);
        } catch (IllegalArgumentException | TransactionRequiredException | NullPointerException e) {
            throw new MusicLibDataAccessException("genre update error", e);
        }
    }

    @Override
    public void remove(Genre genre) {
        try {
            for(Song song : songDao.findByGenre(genre)) {
                songDao.remove(song);
            }
            genreDao.remove(genre);
        } catch (IllegalArgumentException | TransactionRequiredException | NullPointerException e) {
            throw new MusicLibDataAccessException("genre remove error", e);
        }
    }

    @Override
    public Genre findById(Long id) {
        try {
            return genreDao.findById(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new MusicLibDataAccessException("genre findById error", e);
        }
    }

    @Override
    public List<Genre> findByTitle(String title) {
        try {
            return genreDao.searchByTitle(title);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new MusicLibDataAccessException("genre findByTitle error", e);
        }
    }

    @Override
    public List<Genre> findAll() {
        try {
            return genreDao.findAll();
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new MusicLibDataAccessException("genre findAll error", e);
        }
    }

    @Override
    public void changeTitle(Genre genre, String title) {
        try {
            genre.setTitle(title);
            genreDao.update(genre);
        } catch (IllegalArgumentException | TransactionRequiredException | NullPointerException e) {
            throw new MusicLibDataAccessException("genre changeTitle error", e);
        }
    }

}
