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
import static org.testng.Assert.assertNotNull;
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

        song1A = songBuilder.title("Open eye signal").build();
        song1B = songBuilder.title("Collider").build();
        song2A = songBuilder.title("Oro y sangre").build();
        song2B = songBuilder.title("So will be now").build();

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
        
    }
    
    @Test
    public void testClassInitializationTest() {
        assertNotNull(albumDao);
        assertNotNull(songDao);
        assertNotNull(musicianDao);
        assertNotNull(songService);
    }
    /*
    @Test
    public void createTest() {
        Song newSong = songBuilder.id(null).title("We disappear").album(album1).build();
        //newSong.setAlbum(album1);
       // newSong.setGenre(genre);
       // newSong.setMusician(musician1);
        
        songService.create(newSong);
        
        assertNotNull(newSong);
        assertNotNull(newSong.getId());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createIdAlreadySetTest() {
        Song newSong = songBuilder.id(1l).title("The art of chill").build();
        
        songService.create(newSong);
    }*/
    
    @Test(expectedExceptions = NullPointerException.class)
    public void createNullTest(){
        songService.create(null);
    }
}
