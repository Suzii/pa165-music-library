package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.AlbumChangeAlbumArtDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumNewTitleDTO;

import java.util.List;

/**
 * @author xstefank (422697@mail.muni.cz)
 */
public interface AlbumFacade {
    Long createAlbum(AlbumDTO a);

    void addSong(Long albumId, Long songId);

    void removeSong(Long albumId, Long songId);

    void update(AlbumDTO newAlbum);

    void changeAlbumArt(AlbumChangeAlbumArtDTO dto);

    void deleteAlbum(Long albumId);

    List<AlbumDTO> getAllAlbums();

    AlbumDTO getAlbumById(Long id);

    List<AlbumDTO> getAlbumByTitle(String title);
}
