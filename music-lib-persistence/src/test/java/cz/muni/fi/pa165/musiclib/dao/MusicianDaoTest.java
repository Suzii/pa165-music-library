package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.entity.*;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.utils.*;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Zuzana Dankovcikova
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MusicianDaoTest extends AbstractTestNGSpringContextTests {
    
    @PersistenceContext
    public EntityManager em;
    
    @Inject
    public MusicianDao musicianDao;
    
    @Inject
    public SongDao songDao;
    
    @Inject
    public GenreDao genreDao;
    
    @Inject
    public AlbumDao albumDao;
    
    MusicianBuilder musicianBuilder = new MusicianBuilder();
    SongBuilder songBuilder = new SongBuilder();
    GenreBuilder genreBuilder = new GenreBuilder();
    AlbumBuilder albumBuilder = new AlbumBuilder();
    private Musician musician1;
    private Musician musician2;
    private Song song1A;
    private Song song1B;
    private Song song2A;
    private Song song2B;
    private Genre genre;
    private Album album1;
    private Album album2;

    @BeforeMethod
    private void init() {
        
        Calendar cal = Calendar.getInstance();
        cal.set(1985, 9, 7);
        Date date1 = cal.getTime();
                
        musician1 = musicianBuilder.artistName("Bruno Mars").sex(Sex.MALE).dateOfBirth(date1).build();
        musician2 = musicianBuilder.artistName("Adelle").sex(Sex.FEMALE).build();
        
        song1A = songBuilder.title("Uptown funk").build();
        song1B = songBuilder.title("Locked out of heaven").build();
        song2A = songBuilder.title("Someone like you").build();
        song2B = songBuilder.title("Rolling in the deep").build();
        
        genre = genreBuilder.title("Pop").build();
        album1 = albumBuilder.title("Hooligans").build();
        album2 = albumBuilder.title("MDN").build();
        
        genreDao.create(genre);
        albumDao.create(album1);
        albumDao.create(album2);
        
        song1A.setAlbum(album1);
        song1B.setAlbum(album1);
        song2A.setAlbum(album2);
        song2B.setAlbum(album2);
        
        song1A.setGenre(genre);
        song1B.setGenre(genre);
        song2A.setGenre(genre);
        song2B.setGenre(genre);
    }
    
    @Test
    public void findByIdTest() {
        musicianDao.create(musician1);
        musicianDao.create(musician2);
        Musician m = musicianDao.findById(musician1.getId());
        Assert.assertEquals(m, musician1);
        assertDeepEquals(m, musician1);
    }
    
    @Test
    public void findByIdNonexistingTest() {
        Assert.assertNull(musicianDao.findById(new Long(1)));
    }
    
    @Test
    public void findByArtistName() {
        musicianDao.create(musician1);
        musicianDao.create(musician2);
        
        Musician m = musicianDao.findByArtistName(musician1.getArtistName());
        Assert.assertEquals(m, musician1);
        assertDeepEquals(m, musician1);
    }
    
    @Test
    public void findAllTest() {
        musicianDao.create(musician1);
        musicianDao.create(musician2);
        Assert.assertEquals(musicianDao.findAll().size(), 2);
    }
    
    @Test
    public void createTest() {
        musicianDao.create(musician1);        
        Assert.assertNotNull(musician1.getId());
        Musician m = musicianDao.findById(musician1.getId());        
        Assert.assertEquals(m, musician1);
        assertDeepEquals(m, musician1);
    }
    
    @Test(expectedExceptions=PersistenceException.class)
    public void createWithSameArtistNamesTest() {
        Musician m1 = musicianBuilder.artistName("Dupe").sex(Sex.FEMALE).build();
        Musician m2 = musicianBuilder.artistName("Dupe").sex(Sex.MALE).build();
        musicianDao.create(m1);
        musicianDao.create(m2);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void createWithArtistNameNullTest() {
        Musician m1 = musicianBuilder.sex(Sex.FEMALE).build();
        musicianDao.create(m1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void createWithSexNullTest() {
        Musician m1 = musicianBuilder.artistName("Madonna").build();;
        musicianDao.create(m1);
    }
    
    @Test
    public void createWithSongs() {
        
        musicianDao.create(musician1);
        musicianDao.create(musician2);
        song1A.setMusician(musician1);
        song1B.setMusician(musician1);
        song2A.setMusician(musician2);
        
        songDao.create(song1A);
        songDao.create(song1B);
        songDao.create(song2A);
        final Musician m1 = musicianDao.findById(musician1.getId());
        final Musician m2 = musicianDao.findById(musician2.getId());
        
        Assert.assertEquals(m1.getSongs().size(), 2);
        Assert.assertEquals(m2.getSongs().size(), 1);
        
        assertDeepEquals(m1, musician1);
        assertDeepEquals(m2, musician2);
    }
    
    private void assertDeepEquals(Musician actual, Musician expected){
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getArtistName(), expected.getArtistName());
        Assert.assertEquals(actual.getSex(), expected.getSex());
        Assert.assertEquals(actual.getDateOfBirth(), expected.getDateOfBirth());
        Assert.assertEquals(actual.getSongs(), expected.getSongs());
    }
    
}
