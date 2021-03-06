package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Martin Stefanko
 * @version 15/10/24
 */
public interface SongDao {

    /**
     * Persists new song into DB
     *
     * @param song entity to be persisted
     * @throws ConstraintViolationException if any constraint on
     * columns is violated
     */
    void create(Song song) throws ConstraintViolationException;

    /**
     * Updates already persisted entity in the DB
     *
     * @param song persisted entity to be updated
     * @return the merged object attached to the EM
     * @throws ConstraintViolationException if any constraint on
     * columns is violated
     */
    Song update(Song song) throws ConstraintViolationException;

    /**
     * Returns the entity attached to the given id
     *
     * @param id id of the entity
     * @return the entity with given id
     */
    Song findById(Long id);

    /**
     * Returns all songs that contain given title fragment in their title.
     * 
     * @param titleFragment fragment of the title to look for
     * @return list of songs containing given fragment in their title
     */
    List<Song> searchByTitle(String titleFragment);
    
    /**
     * Returns all Songs in the DB
     *
     * @return all persisted Song entities
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
