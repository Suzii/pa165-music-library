package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;

import java.util.List;
import javax.inject.Inject;

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
        List<Song> songs = album.getSongs();
        for (Song song : songs) {
            //TODO is this correct ??
            songDao.create(song);
        }
        albumDao.create(album);
    }

    @Override
    public Album update(Album album) {
        return albumDao.update(album);
    }

    @Override
    public void remove(Album album) throws IllegalArgumentException {
        albumDao.remove(album);
    }

    @Override
    public Album findById(Long id) {
        return albumDao.findById(id);
    }

    @Override
    public List<Album> findByTitle(String title) {
        return albumDao.findByTitle(title);
    }

    @Override
    public List<Album> findAll() {
        return albumDao.findAll();
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
