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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
                if ("existing musician".equals(musician.getArtistName())) {
                    throw new IllegalArgumentException();
                }

                SetIdHelper.setId(musician, 1l);
                return null;
            }
        }).when(musicianDao).create(any(Musician.class));

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
                if (musician.getArtistName() == null) {
                    throw new IllegalArgumentException();
                }
                if ("existing musician".equals(musician.getArtistName())) {
                    throw new IllegalArgumentException();
                }

                return cloneMusician(musician);
            }
        }).when(musicianDao).update(any(Musician.class));

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
        return musicianBuilder.id(id) //set new id
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
    
    @Test
    public void updateTest(){
        musician1.setArtistName("changed name");
        
        Musician updatedMusician = musicianService.update(musician1);
        
        assertNotNull(updatedMusician);
        assertNotNull(updatedMusician.getId());
        assertEquals(updatedMusician.getArtistName(), "changed name");
        assertDeepEquals(updatedMusician, musician1);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeTest() {
        Musician musician = musicianBuilder.id(10l).artistName("musician name").build();

        when(musicianDao.findById(musician.getId())).thenReturn(musician);
        
        assertNotNull(musicianService.findById(musician.getId()));
        assertEquals(musicianService.findById(musician.getId()), musician);

        musicianService.remove(musician);
        musicianService.findById(musician.getId());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullTest() {
        musicianService.create(null);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullNameTest() {
        Musician musician = musicianBuilder.artistName(null).build();
        musicianService.update(musician);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createIdSetTest() {
        Musician musician = musicianBuilder.artistName("musician name").id(1L).build();
        musicianService.create(musician);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createDuplicateNameTest() {
        Musician musician = musicianBuilder.artistName("existing musician").build();
        musicianService.create(musician);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateIdNullTest() {
        Musician updatedMusician = musicianBuilder.id(null).artistName("musician name").build();
        musicianService.update(updatedMusician);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNameNullTest() {
        musician1.setArtistName(null);
        musicianService.update(musician1);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateDuplicateNameTest() {
        Musician updatedMusician = musicianBuilder.id(1l).artistName("existing musician").build();
        musicianService.update(updatedMusician);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullTest() {
        musicianService.update(null);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeIdNullTest() {
        Musician musician = musicianBuilder.id(null).artistName("musician name").build();
        musicianService.remove(musician);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeNullTest() {
        musicianService.remove(null);
    }
    
    @Test
    public void findTest() {
        Musician musician = musicianService.findById(1L);
        assertNotNull(musician);
        assertDeepEquals(musician, musician1);
    }
    
    @Test
    public void findNotExistingIdTest() {
        Musician musician = musicianService.findById(3l);
        assertNull(musician);
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void findNullTest() {
        when(musicianDao.findById(null)).thenThrow(IllegalArgumentException.class);
        musicianService.findById(null);
    }
    
    @Test
    public void findByNameTest() {
        List<Musician> expected = Arrays.asList(musician1);
        when(musicianDao.findByArtistName("musician name")).thenReturn(expected);
        List<Musician> current = musicianService.findByArtistName("musician name");

        assertNotNull(current);
        assertEquals(current, expected);
        assertDeepEquals(current.get(0), musician1);
    }
    
    @Test
    public void findNullNameTest() {
        List<Musician> musician = musicianService.findByArtistName(null);
        assertTrue(musician.isEmpty());
    }
    
    @Test
    public void findWrongNameTest() {
        List<Musician> musician = musicianService.findByArtistName("not existing name");
        assertTrue(musician.isEmpty());
    }

    @Test
    public void findAllTest() {
        when(musicianDao.findAll()).thenReturn(Arrays.asList(musician1, musician2));
        List<Musician> current = musicianService.findAll();
      
        assertNotNull(current);
        assertEquals(current.size(), 2);
        assertDeepEquals(current.get(0), musician1);
        assertDeepEquals(current.get(1), musician2);
    }

    @Test
    public void findAllEmptyTest() {
        when(musicianDao.findAll()).thenReturn(new ArrayList<Musician>());
        List<Musician> current = musicianService.findAll();
       
        assertNotNull(current);
        assertEquals(current.size(), 0);
    }
    
    
    

    private void assertDeepEquals(Musician musician1, Musician musician2) {
        assertEquals(musician1.getId(), musician2.getId());
        assertEquals(musician1.getArtistName(), musician2.getArtistName());
        assertEquals(musician1.getDateOfBirth(), musician2.getDateOfBirth());
        assertEquals(musician1.getSex(), musician2.getSex());
        assertEquals(musician1.getSongs(), musician2.getSongs());
    }    
    
    
}
