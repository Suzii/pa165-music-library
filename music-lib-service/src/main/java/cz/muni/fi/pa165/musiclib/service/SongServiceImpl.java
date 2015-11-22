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
        songDao.create(song);
    }

    @Override
    public Song update(Song song) {
        return songDao.update(song);
    }

    @Override
    public Song findById(Long id) {
        return songDao.findById(id);
    }

    @Override
    public List<Song> findAll() {
        return songDao.findAll();
    }

    @Override
    public List<Song> findByAlbum(Album album) {
        return songDao.findByAlbum(album);
    }

    @Override
    public List<Song> findByMusician(Musician musician) {
        return songDao.findByMusician(musician);
    }

    @Override
    public List<Song> findByGenre(Genre genre) {
        return songDao.findByGenre(genre);
    }

    @Override
    public void remove(Song song) {
        songDao.remove(song);
    }
}
