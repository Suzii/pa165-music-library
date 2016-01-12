package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.AlbumChangeAlbumArtDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumNewTitleDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.NoSuchEntityFoundException;
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
    public Long createAlbum(AlbumDTO albumDto) {
        if (albumDto == null) {
            throw new IllegalArgumentException("albumDto cannot be null");
        }

        Album album = new Album();
        album.setTitle(albumDto.getTitle());
        album.setCommentary(albumDto.getCommentary());
        album.setDateOfRelease(albumDto.getDateOfRelease());
        album.setAlbumArt(albumDto.getAlbumArt());
        album.setAlbumArtMimeType(albumDto.getAlbumArtMimeType());

        albumService.create(album);
        return album.getId();
    }

    @Override
    public void addSong(Long albumId, Long songId) {
        Album album = albumService.findById(albumId);
        if (album == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        Song song = songService.findById(songId);
        if (song == null) {
            throw new NoSuchEntityFoundException("No such song exists");
        }

        albumService.addSong(album, song);
    }

    @Override
    public void removeSong(Long albumId, Long songId) {
        Album album = albumService.findById(albumId);
        if (album == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        Song song = songService.findById(songId);
        if (song == null) {
            throw new NoSuchEntityFoundException("No such song exists");
        }

        albumService.removeSong(album, song);
    }

    @Override
    public void changeAlbumArt(AlbumChangeAlbumArtDTO dto) {
        Album album = albumService.findById(dto.getAlbumId());
        if (album == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        album.setAlbumArt(dto.getImage());
        album.setAlbumArtMimeType(dto.getMimeType());
        albumService.update(album);
    }

    @Override
    public void update(AlbumDTO newAlbum) {
        Album album = albumService.findById(newAlbum.getId());
        if (album == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        album.setTitle(newAlbum.getTitle());
        album.setCommentary(newAlbum.getCommentary());
        album.setDateOfRelease(newAlbum.getDateOfRelease());
        albumService.update(album);
    }

    @Override
    public void deleteAlbum(Long albumId) {
        Album album = albumService.findById(albumId);
        if (album == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        albumService.remove(album);
    }

    @Override
    public List<AlbumDTO> getAllAlbums() {
        return beanMappingService.mapTo(albumService.findAll(), AlbumDTO.class);
    }

    @Override
    public AlbumDTO getAlbumById(Long id) {
        Album album = albumService.findById(id);
        if (album == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        return beanMappingService.mapTo(album, AlbumDTO.class);
    }

    @Override
    public List<AlbumDTO> searchAlbumByTitle(String title) {
        List<Album> albums = albumService.searchByTitle(title);
        if (albums == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        return beanMappingService.mapTo(albums, AlbumDTO.class);
    }
}
