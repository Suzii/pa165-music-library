package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.GenreDao;
import cz.muni.fi.pa165.musiclib.dao.MusicianDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
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
 * @author Milan
 * @version 26/11/2015
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class SongServiceTest extends AbstractTestNGSpringContextTests{
    
    @Mock
    private  SongDao songDao;
    
    @Mock
    private AlbumDao albumDao;
    
    @Mock
    private MusicianDao musicianDao;
    
    @InjectMocks
    private SongService songService = new SongServiceImpl();
    
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
    private Genre genre;
    private Album album1;
    private Album album2;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void init() {

        musician1 = musicianBuilder.artistName("Jon Hopkins").sex(Sex.MALE).build();
        musician2 = musicianBuilder.artistName("John Talabot").sex(Sex.MALE).build();

        song1A = songBuilder.id(1L).title("Open eye signal").build();
        song1B = songBuilder.id(2L).title("Collider").build();
        song2A = songBuilder.id(3L).title("Oro y sangre").build();
        song2B = songBuilder.id(4L).title("So will be now").build();

        genre = genreBuilder.id(1l).title("Electronica").build();
        
        album1 = albumBuilder.id(1l).title("Domino").build();
        album2 = albumBuilder.id(2l).title("Fin").build();

        song1A.setAlbum(album1);
        song1B.setAlbum(album1);
        song2A.setAlbum(album2);
        song2B.setAlbum(album2);

        song1A.setGenre(genre);
        song1B.setGenre(genre);
        song2A.setGenre(genre);
        song2B.setGenre(genre);
        
        song1A.setMusician(musician1);
        song1B.setMusician(musician2);
        song2A.setMusician(musician1);
        song2B.setMusician(musician2);
    }

    @BeforeMethod
    public void initMockBehaviour(){
        when(songDao.findById(1l)).thenReturn(song1A);
        when(songDao.findById(2l)).thenReturn(song2A);
        when(songDao.findById(0l)).thenReturn(null);
        
        //create
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }
                
                Song song = (Song) argument;
                if(song.getId() != null){
                    throw new IllegalArgumentException();
                }
                
                SetIdHelper.setId(song, 78l);                
                return null;
            }
        }).when(songDao).create(any(Song.class));
        
        
        
        when(songDao.findById(1L)).thenReturn(song1A);
        when(songDao.findById(2L)).thenReturn(song1B);
        when(songDao.findById(3L)).thenReturn(song2A);
        when(songDao.findById(4L)).thenReturn(song2B);
        when(songDao.findAll()).thenReturn(Arrays.asList(song1A, song1B, song2A, song2B));
        when(songDao.findByMusician(musician1)).thenReturn(Arrays.asList(song1A, song2A));
        when(songDao.findByAlbum(album1)).thenReturn(Arrays.asList(song1A, song1B));
        when(songDao.findByGenre(genre)).thenReturn(Arrays.asList(song1A, song1B, song2A, song2B));
    }
    

    
    @Test
    public void testClassInitializationTest() {
        assertNotNull(albumDao);
        assertNotNull(songDao);
        assertNotNull(musicianDao);
        assertNotNull(songService);
    }
    
    @Test
    public void findByIdTest(){
        Song song = songService.findById(1L);
        
        assertNotNull(song);
        assertEquals(song.getId(), song1A.getId());
        assertEquals(song.getTitle(), song1A.getTitle());
        assertEquals(song.getAlbum(), song1A.getAlbum());
        assertEquals(song.getMusician(), song1A.getMusician());
        assertEquals(song.getGenre(), song1A.getGenre());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void findNullTest(){
        when(songDao.findById(null)).thenThrow(IllegalArgumentException.class);
        
        songService.findById(null);
    }
    
    @Test
    public void findByValidMusicianTest() {
        List<Song> songs = songService.findByMusician(musician1);

        assertFalse(songs.isEmpty());
        assertEquals(songs.size(), 2);
        assertEquals(songs.get(0).getId(), song1A.getId());
        assertEquals(songs.get(0).getAlbum(), song1A.getAlbum());
        assertEquals(songs.get(0).getMusician(), song1A.getMusician());
        assertEquals(songs.get(0).getGenre(), song1A.getGenre());
        assertEquals(songs.get(1).getId(), song2A.getId());
        assertEquals(songs.get(1).getAlbum(), song2A.getAlbum());
        assertEquals(songs.get(1).getMusician(), song2A.getMusician());
        assertEquals(songs.get(1).getGenre(), song2A.getGenre());
    }

    @Test
    public void findNullMusicianTest() {
        List<Song> song = songService.findByMusician(null);

        assertTrue(song.isEmpty());
    }

    @Test
    public void findInvalidMusicianTest() {
        Musician musician3 = musicianBuilder.artistName("Bonobo").sex(Sex.MALE).build();
        
        List<Song> song = songService.findByMusician(musician3);

        assertTrue(song.isEmpty());
    }

    @Test
    public void findAllTest() {
        List<Song> songs = songService.findAll();

        assertFalse(songs.isEmpty());
        assertEquals(songs.size(), 4);
        assertEquals(songs.get(0).getId(), song1A.getId());
        assertEquals(songs.get(0).getAlbum(), song1A.getAlbum());
        assertEquals(songs.get(0).getMusician(), song1A.getMusician());
        assertEquals(songs.get(0).getGenre(), song1A.getGenre());
        assertEquals(songs.get(1).getId(), song1B.getId());
        assertEquals(songs.get(1).getAlbum(), song1B.getAlbum());
        assertEquals(songs.get(1).getMusician(), song1B.getMusician());
        assertEquals(songs.get(1).getGenre(), song1B.getGenre());
        assertEquals(songs.get(2).getId(), song2A.getId());
        assertEquals(songs.get(2).getAlbum(), song2A.getAlbum());
        assertEquals(songs.get(2).getMusician(), song2A.getMusician());
        assertEquals(songs.get(2).getGenre(), song2A.getGenre());
        assertEquals(songs.get(3).getId(), song2B.getId());
        assertEquals(songs.get(3).getAlbum(), song2B.getAlbum());
        assertEquals(songs.get(3).getMusician(), song2B.getMusician());
        assertEquals(songs.get(3).getGenre(), song2B.getGenre());
    }
    
    @Test
    public void findInvalidIdTest() {
        Song song = songService.findById(645L);

        assertNull(song);
    }
    
    
    
    /*
    @Test
    public void createValidTest(){
        Song song = songBuilder.title("Subtitle").album(album1).musician(musician1).genre(genre).build();
                
        songService.create(song);
        assertNotNull(song);
        assertNotNull(song.getId());
        assertNotNull(song.getGenre());
        assertNotNull(song.getAlbum());
        assertNotNull(song.getMusician());
        assertEquals(song.getTitle(),"Subtitle");               
        }
    
    @Test
    public void createTest() {
        Song newSong = songBuilder.id(null).title("Subtitle").album(album1).musician(musician1).genre(genre).build();
        //newSong.setAlbum(album1);
       // newSong.setGenre(genre);
       // newSong.setMusician(musician1);
        
        songService.create(newSong);
        
        assertNotNull(newSong);
        assertNotNull(newSong.getId());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createIdAlreadySetTest() {
        Song newSong = songBuilder.id(1L).title("The art of chill").build();
        
        songService.create(newSong);
    }
    */
    @Test(expectedExceptions = NullPointerException.class)
    public void createNullTest(){
        songService.create(null);
    }
    
}
