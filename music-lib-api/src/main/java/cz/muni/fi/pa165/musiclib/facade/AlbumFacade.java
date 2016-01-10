package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.AlbumChangeAlbumArtDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumNewTitleDTO;

import java.util.List;

/**
 * @author xstefank (422697@mail.muni.cz)
 */
public interface AlbumFacade {
    
    /**
    * Creates new album.
    *
    * @param album entity to be created
    * @return id of newly created album
    */    
    Long createAlbum(AlbumDTO album);

    /**
     * Adds song with given id to and album with given id.
     * 
     * @param albumId id of an album to add song to
     * @param songId id of a song to be added
     * @throws IllegalArgumentException if albumId or songId is null
     */
    void addSong(Long albumId, Long songId);

    /**
     * Removes song with given id from an album with given id.
     * 
     * @param albumId id of an album from which song should be deleted
     * @param songId id of a song to be deleted from an album
     * @throws IllegalArgumentException if albumId or songId is null
     */
    void removeSong(Long albumId, Long songId);

    /**
     * Updates album. 
     * 
     * @param newAlbum entity to be updated
     */
    void update(AlbumDTO newAlbum);

    /**
     * Changes album art of an album.
     * 
     * @param dto 
     */
    void changeAlbumArt(AlbumChangeAlbumArtDTO dto);

    /**
     * Deleted album.
     * 
     * @param albumId id of an album to be deleted
     * @throws IllegalArgumentException if albumId is null
     */
    void deleteAlbum(Long albumId);

    /**
     * Returns all known albums in library.
     * 
     * @return all albums in library
     */
    List<AlbumDTO> getAllAlbums();

    /**
     * Returns album with given id.
     * 
     * @param id id of an album to be returned
     * @return album with given id
     * @throws IllegalArgumentException if albumId si null
     */
    AlbumDTO getAlbumById(Long id);

    /**
     * Return album with given title.
     * 
     * @param title title of an album to be returned
     * @return album with given title
     * @throws IllegalArgumentException if the title is null
     */
    List<AlbumDTO> getAlbumByTitle(String title);
}
