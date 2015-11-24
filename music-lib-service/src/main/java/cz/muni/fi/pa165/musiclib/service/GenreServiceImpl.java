package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.GenreDao;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author zdank
 */
public class GenreServiceImpl implements GenreService {

    @Inject
    private GenreDao genreDao;
    
    @Override
    public void create(Genre genre) {
        genreDao.create(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return genreDao.update(genre);
    }

    @Override
    public void remove(Genre genre) {
        genreDao.remove(genre);
    }

    @Override
    public Genre findById(Long id) {
        return genreDao.findById(id);
    }

    @Override
    public List<Genre> findByTitle(String title) {
        return genreDao.findByTitle(title);
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }
    
    @Override
    public void changeTitle(Genre genre, String title) {
        genre.setTitle(title);
    }
    
}
