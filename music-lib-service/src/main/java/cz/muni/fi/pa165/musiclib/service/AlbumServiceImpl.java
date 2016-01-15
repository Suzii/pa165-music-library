package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Stefanko (mstefank@redhat.com)
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Inject
    private AlbumDao albumDao;
    
    @Override
    public void create(Album album) {
        try {
            albumDao.create(album);
        } catch(IllegalArgumentException | ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("album create error", ex);
        }
    }

    @Override
    public Album update(Album album) {
        try {
            return albumDao.update(album);
        } catch (IllegalArgumentException | ConstraintViolationException | PersistenceException ex) {
            throw new MusicLibDataAccessException("album update error", ex);
        }
    }

    @Override
    public void remove(Album album) throws IllegalArgumentException {
        if (album == null) {
            throw new MusicLibDataAccessException("Album cannot be null");
        }

        try {
            //remove from songs
            List<Song> songs = album.getSongs();
            for (Song song : songs) {
                removeSong(album, song);
            }

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
    public List<Album> searchByTitle(String title) {
        if(title == null) {
            throw new IllegalArgumentException("title cannot be null");
        }
        
        try {
            return albumDao.searchByTitle(title);
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
        if (album == null) {
            throw new IllegalArgumentException("album cannot be null");
        }
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        if (song.getGenre() == null) {
            throw new MusicLibServiceException("genre of song cannot be null");
        }
        if (song.getMusician() == null) {
            throw new MusicLibServiceException("musician of song cannot be null");
        }

        //compare if the song is suitable for this album
//        double albumMajorGenreRatio = (double)genreCountMap.get(majorAlbumGenre) / (currSongs.size() + 1);
//        if (!song.getGenre().equals(majorAlbumGenre) && albumMajorGenreRatio < 0.4) {
//            throw new MusicLibServiceException("Cannot add song to album, album contains majority "
//                    + "of songs in different genre");
//        }
        
        if (album.getSongs().contains(song)) {
            throw new MusicLibServiceException("Album already contains this song; Album: "
                + album.getId() + ", song: " + song.getId());
        }
        
        song.setAlbum(album);
    }

    @Override
    public void removeSong(Album album, Song song) {
        if (album == null) {
            throw new IllegalArgumentException("album cannot be null");
        }
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }

        if (!album.getSongs().contains(song)) {
            throw new MusicLibServiceException("Album does not contains the desired song; Album: "
                    + album.getId() + ", song: " + song.getId());
        }
        album.removeSong(song);
        song.setAlbum(null);
    }

    @Override
    public GenreResult getMajorGenreForAlbum(Album album) {
        //business method #1
        List<Song> currSongs = album.getSongs();
        Map<Genre, Integer> genreCountMap = new HashMap<>();

        for (Song s : currSongs) {
            if (!genreCountMap.containsKey(s.getGenre())) {
                genreCountMap.put(s.getGenre(), 1);
            } else {
                Genre key = s.getGenre();
                genreCountMap.put(key, genreCountMap.get(key) + 1);
            }
        }

        GenreResult result = new GenreResult();

        if(!genreCountMap.isEmpty()) {
            for (Map.Entry<Genre, Integer> entry : genreCountMap.entrySet()) {
                if (result.getGenre() == null) {
                    result.setGenre(entry.getKey());
                } else {
                    if (entry.getValue() > genreCountMap.get(result.getGenre())) {
                        result.setGenre(entry.getKey());
                    }
                }
            }

            result.setPercentage((double)genreCountMap.get(result.getGenre()) / (currSongs.size()));
        }

        return result;
    }

    public class GenreResult {
        private Genre genre;
        private double percentage;

        public Genre getGenre() {
            return genre;
        }

        public void setGenre(Genre genre) {
            this.genre = genre;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }
    }
}
