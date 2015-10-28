package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;

import java.util.List;

/**
 * @author xstefank
 * @version 10/24/15
 */
public interface SongDao {

    void create(Song song);

    Song update(Song song);

    Song findById(Long id);

    List<Song> findAll();

    List<Song> findByAlbum(Album album);

    List<Song> findByMusician(Musician musician);

    List<Song> findByGenre(Genre genre);

    void remove(Song song);
}
