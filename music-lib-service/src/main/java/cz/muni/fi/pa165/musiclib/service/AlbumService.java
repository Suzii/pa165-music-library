package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;

import java.util.List;

/**
 * Album service that provides all business logic for album entities. 
 * 
 * @author Martin Stefanko (mstefank@redhat.com)
 * @version 21/11/2015
 */
public interface AlbumService {
    
    /**
     * Created new album.
     * 
     * @param album entity to be created
     */
    void create(Album album);
    
    /**
     * Updates album.
     *
     * @param album entity to be updated
     * @return updated album entity
     */
    Album update(Album album);
    
    /**
     * Removes album.
     * 
     * @param album entity to be removed
     * @throws IllegalArgumentException if passed album is null or is not stored in DB
     */
    void remove(Album album) throws IllegalArgumentException;
    
    /**
     * Get album entity by unique ID.
     * 
     * @param id id of album entity
     * @return album entity with given id, null if no album with this id exists
     */
    Album findById(Long id);
    
    /**
     * Returns all albums with given title
     * 
     * @param title title of albums to look for
     * @return list of all album entities with given title, empty if no such album exists
     */
    List<Album> findByTitle(String title);
    
    /**
     * Returns all albums.
     * 
     * @return list of all albums
     */
    List<Album> findAll();

    /**
     * Adds a new song to the album if the resulting percentage of songs 
     * with majority same genre on the album will be more than 60%.
     *
     * @param album album to which the song is to be added
     * @throws MusicLibServiceException if addition of song to the album 
     *  would result in less than 40% of songs with same genre on album
     * @param song song to be added
     */
    void addSong(Album album, Song song) throws MusicLibServiceException;

    /**
     * Removes the song from the album
     *
     * @param album album from to which the song belongs
     * @param song song to be removed
     */
    void removeSong(Album album, Song song);
}
