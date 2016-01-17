package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dao.MusicianDao;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author David Boron
 * @version 15/11/26
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MusicianServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MusicianDao musicianDao;

    @InjectMocks
    private MusicianService musicianService = new MusicianServiceImpl();

    MusicianBuilder musicianBuilder = new MusicianBuilder();

    private Musician musician1;
    private Musician musician2;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        musician1 = new MusicianBuilder().id(1l).artistName("musician1").dateOfBirth(new Date(1)).sex(Sex.MALE).build();
        musician2 = new MusicianBuilder().id(2l).artistName("musician2").dateOfBirth(new Date(2)).sex(Sex.FEMALE).build();
    }

    @BeforeMethod
    public void initMocksBehaviour() {
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
                if (musician.getArtistName() == null) {
                    throw new IllegalArgumentException();
                }
                if (musician.getSex() == null) {
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
                if (musician.getSex() == null) {
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
        Musician musician = musicianBuilder.artistName("musician name").sex(Sex.MALE).build();
        musicianService.create(musician);
        assertNotNull(musician);
        assertNotNull(musician.getId());
        assertEquals(musician.getArtistName(), "musician name");
    }

    @Test
    public void updateTest() {
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
        Musician musician = musicianBuilder.artistName(null).sex(Sex.MALE).build();
        musicianService.create(musician);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullSexTest() {
        Musician musician = musicianBuilder.artistName("musician name").sex(null).build();
        musicianService.create(musician);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createIdSetTest() {
        Musician musician = musicianBuilder.id(1L).artistName("musician name").build();
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
    public void updateSexNullTest() {
        musician1.setSex(null);
        musicianService.update(musician1);
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
        when(musicianDao.searchByArtistName("musician name")).thenReturn(expected);
        List<Musician> current = musicianService.searchByArtistName("musician name");

        assertNotNull(current);
        assertEquals(current, expected);
        assertDeepEquals(current.get(0), musician1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findNullNameTest() {
        musicianService.searchByArtistName(null);
    }

    @Test
    public void findWrongNameTest() {
        List<Musician> musician = musicianService.searchByArtistName("not existing name");
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
