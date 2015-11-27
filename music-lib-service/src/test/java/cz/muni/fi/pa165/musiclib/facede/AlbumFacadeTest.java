package cz.muni.fi.pa165.musiclib.facede;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.AlbumChangeAlbumArtDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.AlbumNewTitleDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.facade.AlbumFacadeImpl;
import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.AlbumServiceImpl;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.musiclib.service.SongService;
import cz.muni.fi.pa165.musiclib.service.SongServiceImpl;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import java.util.Arrays;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
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
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private AlbumFacadeImpl albumFacade = new AlbumFacadeImpl();

    AlbumBuilder albumBuilder = new AlbumBuilder();
    SongBuilder songBuilder = new SongBuilder();
    MusicianBuilder musicianBuilder = new MusicianBuilder();
    GenreBuilder genreBuilder = new GenreBuilder();

    private Song song1A;
    private Song song1B;
    private Song song2A;
    private Song song2B;
    private Song creepySongWithoutAlbum;
    private Genre genrePop;
    private Genre genreFolk;
    private Album album1;
    private Album album2;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        song1A = songBuilder.title("Uptown funk").build();
        song1B = songBuilder.title("Locked out of heaven").build();
        song2A = songBuilder.title("Someone like you").build();
        song2B = songBuilder.title("Rolling in the deep").build();
        creepySongWithoutAlbum = songBuilder
                .title("Creepy song")
                .commentary("Does anyone really listenes to folk genre tody?!")
                .genre(genreFolk)
                .build();

        genrePop = genreBuilder.id(1l).title("Pop").build();
        genreFolk = genreBuilder.id(2l).title("Folk").build();
        album1 = albumBuilder.id(1l).title("Hooligans").build();
        album2 = albumBuilder.id(2l).title("MDN").build();

        song1A.setAlbum(album1);
        song1B.setAlbum(album1);
        song2A.setAlbum(album2);
        song2B.setAlbum(album2);

        song1A.setGenre(genrePop);
        song1B.setGenre(genrePop);
        song2A.setGenre(genrePop);
        song2B.setGenre(genrePop);
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        // findById
        when(albumService.findById(1l)).thenReturn(album1);
        when(albumService.findById(2l)).thenReturn(album2);
        when(albumService.findById(0l)).thenReturn(null);
        
        //findByTitle
        when(albumService.findByTitle("Hooligans")).thenReturn(Arrays.asList(album1));
        when(albumService.findByTitle("MDN")).thenReturn(Arrays.asList(album2));
        
        //findAll
        when(albumService.findAll()).thenReturn(Arrays.asList(album1, album2));

        //create
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Album album = (Album) argument;
                if (album.getId() != null) {
                    throw new IllegalArgumentException();
                }
                if ("Dupe".equals(album.getTitle())) {
                    throw new IllegalArgumentException();
                }

                SetIdHelper.setId(album, 42l);
                return null;
            }
        }).when(albumService).create(any(Album.class));

        //update
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Album album = (Album) argument;
                if (album.getId() == null) {
                    throw new IllegalArgumentException();
                }
                if ("Dupe".equals(album.getTitle())) {
                    throw new IllegalArgumentException();
                }

                return cloneAlbum(album);
            }
        }).when(albumService).update(any(Album.class));

        //remove
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Album album = (Album) argument;
                if (album.getId() == null) {
                    throw new IllegalArgumentException();
                }

                when(albumService.findById(album.getId())).thenThrow(IllegalArgumentException.class);

                return null;
            }
        }).when(albumService).remove(any(Album.class));

        //change title        
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument1 = invocation.getArguments()[0];
                Object argument2 = invocation.getArguments()[1];
                if (argument1 == null || argument2 == null) {
                    throw new IllegalArgumentException();
                }

                Album album = (Album) argument1;
                String title = (String) argument2;
                if (album.getId() == null) {
                    throw new IllegalArgumentException();
                }
                if ("Dupe".equals(album.getTitle())) {
                    throw new IllegalArgumentException();
                }

                album.setTitle(title);
                return null;
            }
        }).when(albumService).changeTitle(any(Album.class), anyString());
    }

    @Test
    public void testClassInitializationTest() {
        assertNotNull(albumService);
        assertNotNull(songService);
        assertNotNull(albumFacade);
    }

    @Test
    public void createAlbumTest() {
        AlbumDTO albumDto = new AlbumDTO();
        albumDto.setTitle("New release");

        assertEquals(albumFacade.createAlbum(albumDto), new Long(42l));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void crateAlbumNullTest() {
        albumFacade.createAlbum(null);
    }

    @Test
    public void addSongTest() {
        //All logic in service layer
        Album album = albumBuilder.title("Blank album").songs(null).build();
        Song song = songBuilder.title("New song").album(null).build();

        albumFacade.addSong(album.getId(), song.getId());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addSongAlbumNullTest() {
        albumFacade.addSong(null, 1l);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addSongSongNullTest() {
        albumFacade.addSong(1l, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addSongAbumNullSongNullTest() {
        albumFacade.addSong(null, null);
    }

    @Test
    public void removeSongTest() {
        //All logic in service layer
        album1.setSongs(Arrays.asList(song1A,song1B));
        albumFacade.removeSong(album1.getId(), song1A.getId());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeSongSongNullTest() {
        when(songService.findById(null)).thenThrow(IllegalArgumentException.class);
        albumFacade.removeSong(1l, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeSongAlbumNullTest() {
        albumFacade.removeSong(null, 1l);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addSongAlbumNullSongNullTest() {
        albumFacade.removeSong(null, null);
    }

    @Test
    public void changeTitleTest() {
        AlbumNewTitleDTO albumNewTitleDTO = new AlbumNewTitleDTO();
        albumNewTitleDTO.setAlbumId(album1.getId());
        albumNewTitleDTO.setValue("New title");

        albumFacade.changeTitle(albumNewTitleDTO);
        assertNotNull(album1);
        assertEquals(album1.getTitle(), "New title");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void changeTitleNullTest() {
        albumFacade.changeTitle(null);
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void changeAlbumArtNullTest() {
        albumFacade.changeAlbumArt(null);
    }

    @Test
    public void deleteAlbumTest() {
        assertEquals(albumFacade.getAlbumById(album1.getId()), album1);
        albumFacade.deleteAlbum(album1.getId());
        assertEquals(albumFacade.getAlbumById(album1.getId()), album1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteAlbumNullTest() {
        albumFacade.deleteAlbum(null);
    }
    
    @Test
    public void getAllAlbumsTest() {
        assertEquals(albumFacade.getAllAlbums(), Arrays.asList(album1, album2));
    }

    @Test
    public void getAlbumByIdTest() {
        assertEquals(albumFacade.getAlbumById(album1.getId()), album1);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getAlbumByIdNullTest() {
        when(albumService.findById(null)).thenThrow(IllegalArgumentException.class);
        albumFacade.getAlbumById(null);
    }

    @Test
    public void getAlbumByTitleTest() {
        assertEquals(albumFacade.getAlbumByTitle(album1.getTitle()), Arrays.asList(album1));
    }
        
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getAlbumByTitleNullTest() {
        when(albumService.findByTitle(null)).thenThrow(IllegalArgumentException.class);        
        albumFacade.getAlbumByTitle(null);
    }

    private void assertDeepEquals(Album album01, Album album02) {
        assertEquals(album01.getId(), album02.getId());
        assertEquals(album01.getTitle(), album02.getTitle());
        assertEquals(album01.getCommentary(), album02.getCommentary());
        assertEquals(album01.getDateOfRelease(), album02.getDateOfRelease());
        assertEquals(album01.getAlbumArt(), album02.getAlbumArt());
        assertEquals(album01.getAlbumArtMimeType(), album02.getAlbumArtMimeType());
        assertEquals(album01.getSongs(), album02.getSongs());
    }

    private Album cloneAlbum(Album album, Long id) {
        return albumBuilder.id(id) //set new id
                .title(album.getTitle())
                .commentary(album.getCommentary())
                .dateOfRelease(album.getDateOfRelease())
                .albumArt(album.getAlbumArt())
                .albumArtMimeType(null)
                .albumArt(album.getAlbumArt())
                .songs(album.getSongs())
                .build();
    }

    private Album cloneAlbum(Album album) {
        return cloneAlbum(album, album.getId());
    }

}
