package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Martin Stefanko (mstefank@redhat.com)
 */
public class AlbumFacadeImpl implements AlbumFacade {

    private final static Logger log = LoggerFactory.getLogger(AlbumFacadeImpl.class);

    @Inject
    private AlbumService albumService;

    @Inject
    private SongService songService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void createAlbum(AlbumDTO album) {
    }

    @Override
    public void addSong(Long albumId, Long songId) {

    }

    @Override
    public void removeSong(Long albumId, Long songId) {

    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void changeImage(byte[] image) {

    }

    @Override
    public void deleteAlbum(Long albumId) {

    }

    @Override
    public List<AlbumDTO> getAllAlbums() {
        return null;
    }

    @Override
    public AlbumDTO getAlbumById(Long id) {
        return null;
    }

    @Override
    public AlbumDTO getAlbumByTitle(String title) {
        return null;
    }
}
