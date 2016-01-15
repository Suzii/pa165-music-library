package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.AlbumChangeAlbumArtDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumNewTitleDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.facade.AlbumFacadeImpl;
import cz.muni.fi.pa165.musiclib.service.AlbumServiceImpl;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.musiclib.service.SongServiceImpl;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import java.util.Arrays;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author zdank
 * @version 26/11/2015
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AlbumFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AlbumServiceImpl albumService;

    @Mock
    private SongServiceImpl songService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final AlbumFacadeImpl albumFacade = new AlbumFacadeImpl();

    AlbumBuilder albumBuilder = new AlbumBuilder();
    SongBuilder songBuilder = new SongBuilder();
    MusicianBuilder musicianBuilder = new MusicianBuilder();
    GenreBuilder genreBuilder = new GenreBuilder();

    private Song song1A;
    private Song song1B;
    private Genre genrePop;
    private Album album1;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        song1A = songBuilder.id(1l).title("Uptown funk").build();
        song1B = songBuilder.id(2l).title("Locked out of heaven").build();
        
        genrePop = genreBuilder.id(1l).title("Pop").build();
        album1 = albumBuilder.id(1l).title("Hooligans").build();

        song1A.setAlbum(album1);
        song1B.setAlbum(album1);
        
        song1A.setGenre(genrePop);
        song1B.setGenre(genrePop);
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        // findById
        when(albumService.findById(1l)).thenReturn(album1);
        when(albumService.findById(0l)).thenReturn(null);
        
        when(songService.findById(1l)).thenReturn(song1A);
        when(songService.findById(2l)).thenReturn(song1B);
        
        //findByTitle
        when(albumService.searchByTitle("Hooligans")).thenReturn(Arrays.asList(album1));
        
        //findAll
        when(albumService.findAll()).thenReturn(Arrays.asList(album1));
    }

    @Test(enabled = false)
    public void testClassInitializationTest() {
        assertNotNull(albumService);
        assertNotNull(songService);
        assertNotNull(albumFacade);
    }

    @Test
    public void createAlbumTest() {
        AlbumDTO albumDto = new AlbumDTO();
        albumDto.setTitle("New release");

        albumFacade.createAlbum(albumDto);
        verify(albumService).create(any(Album.class));
    }

    @Test
    public void addSongTest() {
        Album album = albumBuilder.title("Blank album").songs(null).build();
        Song song = songBuilder.title("New song").album(null).build();

        albumFacade.addSong(album.getId(), song.getId());
        verify(albumService).addSong(any(Album.class), any(Song.class));
    }
        
    @Test
    public void removeSongTest() {
        album1.setSongs(Arrays.asList(song1A, song1B));
        albumFacade.removeSong(album1.getId(), song1A.getId());
        verify(albumService).removeSong(album1, song1A);
    }

    @Test
    public void changeTitleTest() {
        AlbumDTO albumNewTitleDTO = new AlbumDTO();
        albumNewTitleDTO.setId(album1.getId());
        albumNewTitleDTO.setTitle("New title");

        albumFacade.update(albumNewTitleDTO);
        assertEquals(album1.getTitle(), albumNewTitleDTO.getTitle());
    }
    
    @Test
    public void changeAlbumArtTest() {

        album1.setAlbumArt(null);
        album1.setAlbumArtMimeType(null);
        byte[] image = new byte[]{0, 1, 2, 3};
        AlbumChangeAlbumArtDTO albumChangeAlbumArtDTO = new AlbumChangeAlbumArtDTO();
        albumChangeAlbumArtDTO.setAlbumId(album1.getId());
        albumChangeAlbumArtDTO.setMimeType("prank");
        albumChangeAlbumArtDTO.setImage(image);

        when(albumService.update(album1)).thenReturn(album1);
        albumFacade.changeAlbumArt(albumChangeAlbumArtDTO);
        assertEquals(album1.getAlbumArtMimeType(), "prank");
        assertEquals(album1.getAlbumArt(), image);
    }

    @Test
    public void deleteAlbumTest() {
        albumFacade.deleteAlbum(album1.getId());
        verify(albumService).remove(album1);
    }

    @Test
    public void getAllAlbumsTest() {
        albumFacade.getAllAlbums();
        verify(albumService).findAll();
    }

    @Test
    public void getAlbumByIdTest() {
        albumFacade.getAlbumById(1l);
        verify(albumService, atLeastOnce()).findById(1l);
    }
    
    @Test
    public void getAlbumByTitleTest() {
        albumFacade.searchAlbumByTitle("Title");
        verify(albumService).searchByTitle("Title");
    }
    
    @Test
    public void getMajorGenreForAlbumTest() {
        when(albumService.getMajorGenreForAlbum(any(Album.class))).thenReturn(new AlbumServiceImpl.GenreResult());
        albumFacade.getMajorGanreForAlbum(1l);
        verify(albumService).getMajorGenreForAlbum(any(Album.class));
    }
}
