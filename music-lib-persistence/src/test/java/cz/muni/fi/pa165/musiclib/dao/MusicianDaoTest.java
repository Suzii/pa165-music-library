package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.entity.*;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import javax.inject.Inject;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
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
        musician1 = musicianBuilder.artistName("Bruno Mars").build();
        musician2 = musicianBuilder.artistName("Adelle").build();
        
        song1A = songBuilder.title("Uptown funk").build();
        song1B = songBuilder.title("Locked out of heaven").build();
        song2A = songBuilder.title("Someone like you").build();
        song2B = songBuilder.title("Rolling in the deep").build();
        
    }
    
    @Test
    public void createTest() {
//        musicianDao.create(musician1);        
//        Musician m = musicianDao.findById(musician1.getId());        
//        Assert.assertEquals(musician1, m);
    }
    
    @Test
    public void findByIdTest() {
    
    }
    
    @Test
    public void findAllTest() {
    
    }
}
