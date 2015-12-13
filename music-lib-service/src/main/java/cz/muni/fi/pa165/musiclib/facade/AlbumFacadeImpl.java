package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.AlbumChangeAlbumArtDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumNewTitleDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Martin Stefanko (mstefank@redhat.com)
 */
@Service
@Transactional
public class AlbumFacadeImpl implements AlbumFacade {

    private final static Logger log = LoggerFactory.getLogger(AlbumFacadeImpl.class);

    @Inject
    private AlbumService albumService;

    @Inject
    private SongService songService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createAlbum(AlbumDTO a) {
        Album album = new Album();
        album.setTitle(a.getTitle());
        album.setCommentary(a.getCommentary());
        album.setDateOfRelease(a.getDateOfRelease());
        album.setAlbumArt(a.getAlbumArt());
        album.setAlbumArtMimeType(a.getAlbumArtMimeType());
        albumService.create(album);

        return album.getId();
    }

    @Override
    public void addSong(Long albumId, Long songId) {
        albumService.addSong(albumService.findById(albumId),
                songService.findById(songId));
    }

    @Override
    public void removeSong(Long albumId, Long songId) {
        albumService.removeSong(albumService.findById(albumId),
                songService.findById(songId));
    }

    @Override
    public void changeAlbumArt(AlbumChangeAlbumArtDTO dto) {
        Album album = albumService.findById(dto.getAlbumId());
        album.setAlbumArt(dto.getImage());
        album.setAlbumArtMimeType(dto.getMimeType());
        albumService.update(album);
    }

    @Override
    public void update(AlbumDTO newAlbum) {
        Album album = albumService.findById(newAlbum.getId());
        album.setTitle(newAlbum.getTitle());
        album.setCommentary(newAlbum.getCommentary());
        album.setDateOfRelease(newAlbum.getDateOfRelease());
        albumService.update(album);
    }

    @Override
    public void deleteAlbum(Long albumId) {
        albumService.remove(albumService.findById(albumId));
    }

    @Override
    public List<AlbumDTO> getAllAlbums() {
        return beanMappingService.mapTo(albumService.findAll(), AlbumDTO.class);
    }

    @Override
    public AlbumDTO getAlbumById(Long id) {
        return beanMappingService.mapTo(albumService.findById(id), AlbumDTO.class);
    }

    @Override
    public List<AlbumDTO> getAlbumByTitle(String title) {
        return beanMappingService.mapTo(albumService.findByTitle(title), AlbumDTO.class);
    }
}
