package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.entity.*;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.test.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.test.utils.SongBuilder;
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
    public SongDao songDao;
    
    @Inject
    public MusicianDao musicianDao;
    
    MusicianBuilder musicianBuilder = new MusicianBuilder();
    SongBuilder songBuilder = new SongBuilder();
    private Musician musician1;
    private Musician musician2;
    private Song song1A;
    private Song song1B;
    private Song song2A;
    private Song song2B;

    @BeforeMethod
    private void init() {
        musician1 = musicianBuilder.artistName("Bruno Mars").sex(Sex.Male).build();
        musician2 = musicianBuilder.artistName("Adelle").sex(Sex.Female).build();
        
        song1A = songBuilder.title("Uptown funk").build();
        song1B = songBuilder.title("Locked out of heaven").build();
        song2A = songBuilder.title("Someone like you").build();
        song2B = songBuilder.title("Rolling in the deep").build();
        
    }
    
    @Test
    public void findByIdTest() {
        musicianDao.create(musician1);
        Assert.assertEquals(musicianDao.findById(musician1.getId()), musician1);
    }
    
    @Test
    public void findByIdNonexistingTest() {
        Assert.assertNull(musicianDao.findById(new Long(1)));
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
        Assert.assertEquals(musician1, m);
        Assert.assertEquals(m.getArtistName(), musician1.getArtistName());
        Assert.assertEquals(m.getSex(), musician1.getSex());
        Assert.assertEquals(m.getDateOfBirth(), musician1.getDateOfBirth());
    }
    
    @Test(expectedExceptions=PersistenceException.class)
    public void createWithSameArtistNamesTest() {
        Musician m1 = musicianBuilder.artistName("Dupe").sex(Sex.Female).build();
        Musician m2 = musicianBuilder.artistName("Dupe").sex(Sex.Male).build();
        musicianDao.create(m1);
        musicianDao.create(m2);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void createWithArtistNameNullTest() {
        Musician m1 = musicianBuilder.sex(Sex.Female).build();
        musicianDao.create(m1);
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void createWithSexNullTest() {
        Musician m1 = musicianBuilder.artistName("Madonna").build();;
        musicianDao.create(m1);
    }
    
    @Test
    public void createWithSongs() {
        songDao.create(song1A);
        songDao.create(song1B);
        songDao.create(song2A);
        
        musicianDao.create(musician1);
        
        song1A.setMusician(musician1);
        song1B.setMusician(musician1);
        
        Assert.assertEquals(musicianDao.findById(musician1.getId()).getSongs().size(), 2);
    }
    
}
