package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dao.GenreDao;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Martin Stefanko
 * @version 15/11/26
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GenreServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private GenreDao genreDao;

    @InjectMocks
    private GenreService genreService = new GenreServiceImpl();

    private Genre genre01;
    private Genre genre02;
    private Song song1A;
    private Song song1B;
    private Song song2A;

    private GenreBuilder genreBuilder = new GenreBuilder();
    private SongBuilder songBuilder = new SongBuilder();

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() throws ServiceException {
        genre01 = genreBuilder.id(1L).title("Rock").build();
        genre02 = genreBuilder.id(2L).title("Pop").build();
        song1A = songBuilder.id(1L).genre(genre01).build();
        song1B = songBuilder.id(2L).genre(genre01).build();
        song2A = songBuilder.id(3L).genre(genre02).build();
    }

    @BeforeMethod
    public void initMocks() {

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }

                Genre genre = (Genre) argument;
                if(genre.getId() != null){
                    throw new IllegalArgumentException();
                }
                if(genre.getTitle() == null) {
                    throw new IllegalArgumentException();
                }
                if(genre.getTitle() == "Rock") {
                    throw new IllegalArgumentException();
                }

                SetIdHelper.setId(genre, 3L);
                return null;
            }
        }).when(genreDao).create(any(Genre.class));

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }

                Genre genre = (Genre) argument;
                if(genre.getId() == null){
                    throw new IllegalArgumentException();
                }
                if(genre.getTitle() == null) {
                    throw new IllegalArgumentException();
                }
                if(genre.getTitle() == "Pop") {
                    throw new IllegalArgumentException();
                }

                return cloneGenre(genre);
            }
        }).when(genreDao).update(any(Genre.class));

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }

                Genre genre = (Genre) argument;
                if(genre.getId() == null){
                    throw new IllegalArgumentException();
                }

                when(genreDao.findById(genre.getId())).thenThrow(IllegalArgumentException.class);

                return null;
            }
        }).when(genreDao).remove(any(Genre.class));

        when(genreDao.findById(1L)).thenReturn(genre01);
        when(genreDao.findById(2L)).thenReturn(genre02);
        when(genreDao.findAll()).thenReturn(Arrays.asList(genre01, genre02));
        when(genreDao.searchByTitle("Rock")).thenReturn(Collections.singletonList(genre01));
        when(genreDao.searchByTitle("Pop")).thenReturn(Collections.singletonList(genre02));
    }

    @Test
    public void createValidTest() {
        Genre genre = genreBuilder.title("Folk").build();

        genreService.create(genre);
        assertNotNull(genre);
        assertNotNull(genre.getId());
        assertEquals(genre.getTitle(), "Folk");
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullTest() {
        genreService.create(null);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullTitleTest() {
        Genre genre = genreBuilder.title(null).build();

        genreService.create(genre);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createInvalidIdTest() {
        Genre genre = genreBuilder.title("Sth").id(1L).build();

        genreService.create(genre);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createInvalidTitleTest() {
        Genre genre = genreBuilder.title("Rock").build();

        genreService.create(genre);
    }

    @Test
    public void updateValidTest() {
        genre01.setTitle("Rockish");

        Genre newGenre = genreService.update(genre01);

        assertNotNull(newGenre);
        assertNotNull(newGenre.getId());
        assertNotNull(newGenre.getTitle());
        assertEquals(newGenre.getTitle(), "Rockish");
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullTest() {
        genreService.update(null);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullTitleTest() {
        genre01.setTitle(null);

        genreService.update(genre01);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullIdTest() {
        Genre genre = genreBuilder.id(null).build();

        genreService.update(genre);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateInvalidGenreTest() {
        Genre genre = genreBuilder.title("invalid genre").build();

        genreService.update(genre);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateInvalidTitleTest() {
        genre01.setTitle("Pop");

        genreService.update(genre01);
    }

    //TODO look at this
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeValidTest() {
        Genre genre = genreBuilder.id(666l).title("Corny stuff").build();

        when(genreDao.findById(genre.getId())).thenReturn(genre);

        assertNotNull(genreService.findById(genre.getId()));
        assertEquals(genreService.findById(genre.getId()), genre);

        genreService.remove(genre);
        genreService.findById(genre.getId());
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeNullTest() {
        genreService.remove(null);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeNullIdTest() {
        Genre genre = genreBuilder.id(null).build();

        genreService.remove(genre);
    }

    @Test
    public void findValidTest() {
        Genre genre = genreService.findById(1L);

        assertNotNull(genre);
        assertEquals(genre.getId(), genre01.getId());
        assertEquals(genre.getTitle(), genre01.getTitle());
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void findNullTest() {
        when(genreDao.findById(null)).thenThrow(IllegalArgumentException.class);

        genreService.findById(null);
    }

    @Test
    public void findInvalidIdTest() {
        Genre genre = genreService.findById(645L);

        assertNull(genre);
    }

    @Test
    public void findValidTitleTest() {
        List<Genre> genres = genreService.searchByTitle("Rock");

        assertFalse(genres.isEmpty());
        assertEquals(genres.size(), 1);
        Genre genre = genres.get(0);
        assertEquals(genre.getId(), genre01.getId());
        assertEquals(genre.getTitle(), genre01.getTitle());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findNullTitleTest() {
        genreService.searchByTitle(null);
    }

    @Test
    public void findInvalidTitleTest() {
        List<Genre> genre = genreService.searchByTitle("asd");

        assertTrue(genre.isEmpty());
    }

    @Test
    public void findAllTest() {
        List<Genre> genres = genreService.findAll();

        assertFalse(genres.isEmpty());
        assertEquals(genres.size(), 2);
        assertEquals(genres.get(0).getId(), genre01.getId());
        assertEquals(genres.get(0).getTitle(), genre01.getTitle());
        assertEquals(genres.get(1).getId(), genre02.getId());
        assertEquals(genres.get(1).getTitle(), genre02.getTitle());
    }

    @Test
    public void findAllNoGenresTest() {
        when(genreDao.findAll()).thenReturn(new ArrayList<Genre>());

        List<Genre> genres = genreService.findAll();
        assertTrue(genres.isEmpty());
    }

    private Genre cloneGenre(Genre genre, Long id) {
        return genreBuilder.id(id)
                .title(genre.getTitle())
                .build();
    }

    private Genre cloneGenre(Genre genre){
        return cloneGenre(genre, genre.getId());
    }

}
