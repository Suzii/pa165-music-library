package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Song service that provides all business logic for song entities. 
 * 
 * @author zdank
 * @version 16/11/2015
 */
@Service
public interface SongService {
    /**
     * Creates new song and assigns it first free position on album.
     * If song is not part of the album yet, position will be set to 0.
     *
     * @param song entity to be created
     */
    void create(Song song);

    /**
     * Updates song. 
     * If positionInAlbum is set to zero and album is not null, first free 
     * position will be assigned. If album id null, position will be set to zero.
     *
     * @param song entity to be updated
     * @throws MusicLibServiceException if position on album is not free on album
     * @return updated song entity
     */
    Song update(Song song) throws MusicLibServiceException;

    /**
     * Returns the song entity attached to the given id.
     *
     * @param id id of the song entity to be returned
     * @return the song entity with given id
     */
    Song findById(Long id);

    /**
     * Returns all Songs.
     *
     * @return list of all song entities
     */
    List<Song> findAll();

    /**
     * Returns all songs for the given album
     *
     * @param album album to which songs belong
     * @return list of Song entities in the given album
     */
    List<Song> findByAlbum(Album album);

    /**
     * Return all songs for the given musician
     *
     * @param musician musician to whom song belong
     * @return all Song entities for the given musician
     * @throws IllegalArgumentException if musician is null
     */
    List<Song> findByMusician(Musician musician);

    /**
     * Returns all songs for the given genre
     *
     * @param genre genre to which songs belong
     * @return all Song entities for the given genre
     * @throws IllegalArgumentException if the genre is null
     */
    List<Song> findByGenre(Genre genre);

    /**
     * Removes the Song entity from persistence context
     *
     * @param song song to be removed     
     * @throws IllegalArgumentException if passed song is null or is not stored in DB 
     */
    void remove(Song song);
}
