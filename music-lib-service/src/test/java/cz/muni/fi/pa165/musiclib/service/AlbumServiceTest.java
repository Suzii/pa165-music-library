package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
public class AlbumServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    public SongDao songDao;

    @Mock
    public AlbumDao albumDao;

    @InjectMocks
    public AlbumService albumService = new AlbumServiceImpl();

    AlbumBuilder albumBuilder = new AlbumBuilder();
    SongBuilder songBuilder = new SongBuilder();
    MusicianBuilder musicianBuilder = new MusicianBuilder();
    GenreBuilder genreBuilder = new GenreBuilder();

    private Musician musician1;
    private Musician musician2;
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

        musician1 = musicianBuilder.artistName("Bruno Mars").sex(Sex.MALE).build();
        musician2 = musicianBuilder.artistName("Adelle").sex(Sex.FEMALE).build();

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
        when(albumDao.findById(1l)).thenReturn(album1);
        when(albumDao.findById(2l)).thenReturn(album2);
        when(albumDao.findById(0l)).thenReturn(null);
        
        //create
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }

                Album album = (Album) argument;
                if(album.getId() != null){
                    throw new IllegalArgumentException();
                }
                if(album.getTitle() == "Dupe"){
                    throw new IllegalArgumentException();
                }
                                
                SetIdHelper.setId(album, 42l);                
                return null;
            }
        }).when(albumDao).create(any(Album.class));
        
        //update
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }
                
                Album album = (Album) argument;
                if(album.getId() == null){
                    throw new IllegalArgumentException();
                }
                if(album.getTitle() == "Dupe"){
                    throw new IllegalArgumentException();
                }                                
                
                return cloneAlbum(album);
            }
        }).when(albumDao).update(any(Album.class));
        
        //remove
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }
                
                Album album = (Album) argument;
                if(album.getId() == null){
                    throw new IllegalArgumentException();
                }
                
                when(albumDao.findById(album.getId())).thenThrow(IllegalArgumentException.class);
                
                return null;
            }
        }).when(albumDao).remove(any(Album.class));
    }
    


    @Test
    public void testClassInitializationTest() {
        assertNotNull(albumDao);
        assertNotNull(songDao);
        assertNotNull(albumService);
    }

    @Test
    public void createTest() {
        Album newAlbum = albumBuilder.id(null).title("Fresh picks").build();
        
        albumService.create(newAlbum);
        
        assertNotNull(newAlbum);
        assertNotNull(newAlbum.getId());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createIdAlreadySetTest() {
        Album newAlbum = albumBuilder.id(1l).title("Fresh picks").build();
        
        albumService.create(newAlbum);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createWithDuplicateTitleTest() {
        Album newAlbum = albumBuilder.id(1l).title("Dupe").build();
        
        albumService.create(newAlbum);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullTest() {
        albumService.create(null);
    }

    @Test
    public void updateTest() {
        album1.setTitle("New title");
        
        Album updated = albumService.update(album1);
        
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals(updated.getTitle(), "New title");
        assertDeepEquals(updated, album1);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateWithIdNullTest() {
        Album newAlbum = albumBuilder.id(null).title("Fresh picks").build();
        
        albumService.update(newAlbum);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateWithDuplicateTitleTest() {
        Album newAlbum = albumBuilder.id(1l).title("Dupe").build();
        
        albumService.update(newAlbum);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullTest() {
        albumService.update(null);
    }    

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeTest() {
        Album toBeRemoved = albumBuilder.id(666l).title("Corny stuff").build();
       
        when(albumDao.findById(toBeRemoved.getId())).thenReturn(toBeRemoved);

        assertNotNull(albumService.findById(toBeRemoved.getId()));
        assertEquals(albumService.findById(toBeRemoved.getId()), toBeRemoved);
        
        albumService.remove(toBeRemoved);
        albumService.findById(toBeRemoved.getId());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeWithIdNullTest() {
        Album newAlbum = albumBuilder.id(null).title("Corny stuff").build();
        
        albumService.remove(newAlbum);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeNullTest() {
        albumService.remove(null);
    }    

    @Test
    public void findByIdTest() {
        Album actual = albumService.findById(1l);

        assertNotNull(actual);
        assertDeepEquals(actual, album1);
    }

    @Test
    public void findByNonexistingIdReturnsNullTest() {
        Album actual = albumService.findById(3l);

        assertNull(actual);
    }

    @Test
    public void findByTitleTest() {
        List<Album> expected = Arrays.asList(album1);
        when(albumDao.findByTitle("Hooligans")).thenReturn(expected);
        List<Album> actual = albumService.findByTitle("Hooligans");

        assertNotNull(actual);
        assertEquals(actual, expected);
        assertDeepEquals(actual.get(0), album1);
    }

    @Test
    public void findByNonExistingTitleTest() {
        List<Album> expected = new ArrayList<>();
        when(albumDao.findByTitle("Moonshine jungle")).thenReturn(expected);
        List<Album> actual = albumService.findByTitle("Moonshine jungle");

        assertNotNull(actual);
        assertEquals(actual.size(), 0);
        assertEquals(actual, expected);
    }

    @Test
    public void findAllTest() {
        when(albumDao.findAll()).thenReturn(Arrays.asList(album1, album2));
        List<Album> actual = albumService.findAll();

        assertNotNull(actual);
        assertEquals(actual.size(), 2);
        assertTrue(actual.contains(album1));
        assertTrue(actual.contains(album2));
    }

    @Test
    public void findAllEpmtyTest() {
        when(albumDao.findAll()).thenReturn(new ArrayList<Album>());
        List<Album> actual = albumService.findAll();

        assertNotNull(actual);
        assertEquals(actual.size(), 0);
    }

    @Test
    public void removeSongSuccessTest() {
        assertEquals(album1.getSongs().size(), 2);
        
        albumService.removeSong(album1, song1A);
        assertEquals(album1.getSongs().size(), 1);
        assertFalse(album1.getSongs().contains(song1A));
        assertNull(song1A.getAlbum());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNullSongTest() {
        albumService.removeSong(album1, null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNullAlbumTest() {
        albumService.removeSong(null, song1A);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNullAlbumNullSongTest() {
        albumService.removeSong(null, null);
    }
    
    @Test(expectedExceptions = MusicLibServiceException.class)
    public void removeWrongSongTest() {
        assertFalse(album1.getSongs().contains(song2A));
        
        albumService.removeSong(album1, song2A);
    }    

    // 0 : 1
    @Test
    public void addSongFirstSongTest(){
        Album blankAlbum = albumBuilder.title("Brand new album").songs(null).build();
        Song firstSong = songBuilder.title("First song").build();
        albumService.addSong(blankAlbum, firstSong);
        
        assertNotNull(blankAlbum.getSongs());
        assertEquals(blankAlbum.getSongs().size(), 1);
        assertTrue(blankAlbum.getSongs().contains(firstSong));
        assertEquals(firstSong.getAlbum(), blankAlbum);
    }
    
    // 0 : 1
    @Test
    public void addSongSecondSongTest(){
        Album blankAlbum = albumBuilder.title("Brand new album").songs(null).build();
        Song firstSong = songBuilder.title("First song").album(null).build();
        Song secondSong = songBuilder.title("Second song").album(null).build();
        albumService.addSong(blankAlbum, firstSong);
        albumService.addSong(blankAlbum, secondSong);
        
        assertNotNull(blankAlbum.getSongs());
        assertEquals(blankAlbum.getSongs().size(), 1);
        assertTrue(blankAlbum.getSongs().contains(firstSong));
        assertEquals(firstSong.getAlbum(), blankAlbum);
        assertTrue(blankAlbum.getSongs().contains(secondSong));
        assertEquals(secondSong.getAlbum(), blankAlbum);
    }
    
    // 3 : 0
    @Test
    public void addSongWithOkGenreTest(){
        Song okSong = songBuilder.title("Ok song").album(null).genre(genrePop).build();
        albumService.addSong(album1, okSong);
        
        assertNotNull(album1.getSongs());
        assertEquals(album1.getSongs().size(), 3);
        assertTrue(album1.getSongs().contains(okSong));
        assertEquals(okSong.getAlbum(), album1);
    }
    
    // 3 : 1 should allow to add song that results in 75% ratio for majority genre
    @Test
    public void addSongWithMinorityGenreGenreTest(){
        Song okSong = songBuilder.title("Ok song").album(null).genre(genrePop).build();
        Song minorityGenreSong = songBuilder.title("Poor folk song").album(null).genre(genreFolk).build();
        albumService.addSong(album1, okSong);
        assertNotNull(album1.getSongs());
        assertTrue(album1.getSongs().contains(okSong));
        
        albumService.addSong(album1, minorityGenreSong);
        assertNotNull(album1.getSongs());
        assertEquals(album1.getSongs().size(), 4);
        assertTrue(album1.getSongs().contains(minorityGenreSong));
        assertEquals(minorityGenreSong.getAlbum(), album1);
    }

    // 2 : 1
    @Test(expectedExceptions = MusicLibServiceException.class)
    public void addSongWithWrongGenreTest() {
        albumService.addSong(album1, creepySongWithoutAlbum);
    }
    
    @Test(expectedExceptions = MusicLibServiceException.class)
    public void addSongDuplicateTest() {
        albumService.addSong(album1, song1A);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addSongAlbumNullTest() {
        albumService.addSong(null, song1A);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addSongSongNullTest() {
        albumService.addSong(album1, null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addSongAlbumNullSongNullTest() {
        albumService.addSong(null, null);
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

    private Album cloneAlbum(Album album){
        return cloneAlbum(album, album.getId());
    }
}
