package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;

import java.util.List;

/**
 * @author xstefank (422697@mail.muni.cz)
 */
public interface AlbumFacade {
    void createAlbum(AlbumDTO album);

    void addSong(Long albumId, Long songId);

    void removeSong(Long albumId, Long songId);

    void changeTitle(String title);

    void changeImage(byte[] image);

    void deleteAlbum(Long albumId);

    List<AlbumDTO> getAllAlbums();

    AlbumDTO getAlbumById(Long id);

    AlbumDTO getAlbumByTitle(String title);
}
