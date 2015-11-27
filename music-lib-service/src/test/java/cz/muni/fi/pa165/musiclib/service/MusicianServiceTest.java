package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dao.MusicianDao;
import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import java.util.Date;
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
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author David Boron
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MusicianServiceTest extends AbstractTestNGSpringContextTests{
    
    @Mock
    private  SongDao songDao;
    
    @Mock
    private MusicianDao musicianDao;
    
    @Mock
    private AlbumDao albumDao;
    
    @InjectMocks
    private MusicianService musicianService = new MusicianServiceImpl();
    
    
    MusicianBuilder musicianBuilder = new MusicianBuilder();
    SongBuilder songBuilder = new SongBuilder();
    AlbumBuilder albumBuilder = new AlbumBuilder();
    GenreBuilder genreBuilder = new GenreBuilder();

    private Musician musician1;
    private Musician musician2;
    private Musician musician3;
    private Song song1A;
    private Song song1B;
    private Song song2A;
    private Song song2B;
    private Song song3;
    private Genre genre1;
    private Genre genre2;
    private Album album1;
    private Album album2;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void init() {
        musician1 = new MusicianBuilder().id(1l).artistName("musician1").dateOfBirth(new Date(1)).sex(Sex.MALE).build();
        musician2 = new MusicianBuilder().id(2l).artistName("musician2").dateOfBirth(new Date(2)).sex(Sex.FEMALE).build();
        
        song1A = songBuilder.title("title1").build();
        song1B = songBuilder.title("title2").build();
        song2A = songBuilder.title("title3").build();
        song2B = songBuilder.title("title4").build();
        song3 = songBuilder.title("title5").genre(genre2).build();

        genre1 = genreBuilder.id(1l).title("Pop").build();
        genre2 = genreBuilder.id(2l).title("Folk").build();
        album1 = albumBuilder.id(1l).title("Hooligans").build();
        album2 = albumBuilder.id(2l).title("MDN").build();

        song1A.setAlbum(album1);
        song1B.setAlbum(album1);
        song2A.setAlbum(album2);
        song2B.setAlbum(album2);

        song1A.setGenre(genre1);
        song1B.setGenre(genre1);
        song2A.setGenre(genre1);
        song2B.setGenre(genre1);
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        // findById
        when(musicianDao.findById(1l)).thenReturn(musician1);
        when(musicianDao.findById(2l)).thenReturn(musician2);
        when(musicianDao.findById(0l)).thenReturn(null);

        //create
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Musician musician = (Musician) argument;
                if (musician.getId() != null) {
                    throw new IllegalArgumentException();
                }
                if ("musician1".equals(musician.getArtistName())) {
                    throw new IllegalArgumentException();
                }

                SetIdHelper.setId(musician, 1l);
                return null;
            }
        }).when(musicianDao).create(any(Musician.class));

        //update
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Musician musician = (Musician) argument;
                if (musician.getId() == null) {
                    throw new IllegalArgumentException();
                }
                if ("musician1".equals(musician.getArtistName())) {
                    throw new IllegalArgumentException();
                }

                return cloneMusician(musician);
            }
        }).when(musicianDao).update(any(Musician.class));

        //remove
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Musician musician = (Musician) argument;
                if (musician.getId() == null) {
                    throw new IllegalArgumentException();
                }

                when(musicianDao.findById(musician.getId())).thenThrow(IllegalArgumentException.class);

                return null;
            }
        }).when(musicianDao).remove(any(Musician.class));
    }


    private Musician cloneMusician(Musician musician, Long id) {
        return musicianBuilder.id(id) 
                .artistName(musician.getArtistName())
                .dateOfBirth(musician.getDateOfBirth())
                .sex(musician.getSex())
                .songs(musician.getSongs())
                .build();
    }

    private Musician cloneMusician(Musician musician) {
        return cloneMusician(musician, musician.getId());
    }
    
    
    @Test
    public void createTest() {
        Musician musician = musicianBuilder.artistName("musician name").build();
        musicianService.create(musician);
        assertNotNull(musician);
        assertNotNull(musician.getId());
        assertEquals(musician.getArtistName(), "musician name");
    }
    
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullTest() {
        musicianService.create(null);
    }

    @Test
    public void updateTest(){
        musician1.setArtistName("changed name");;
        
        Musician updatedMusician = musicianService.update(musician1);
        
        assertNotNull(updatedMusician);
        assertNotNull(updatedMusician.getId());
        assertEquals(updatedMusician.getArtistName(), "changed name");
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeTest() {
        Musician musician = musicianBuilder.artistName("musician name").build();

        when(musicianDao.findById(musician.getId())).thenReturn(musician);
        
        assertNotNull(musicianService.findById(musician.getId()));
        assertEquals(musicianService.findById(musician.getId()), musician);

        musicianService.remove(musician);
        musicianService.findById(musician.getId());
    }
    
}
