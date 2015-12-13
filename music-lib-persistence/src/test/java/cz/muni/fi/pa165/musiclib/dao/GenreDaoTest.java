package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
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
 * @author Milan
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class GenreDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private GenreDao genreDao;
    
    
    GenreBuilder genreBuilder = new GenreBuilder();
    
    private Genre chillWave;
    private Genre acidJazz;
    
    @BeforeMethod
    public void initTest() {
    
        chillWave = genreBuilder.title("Chill Wave").build();
        acidJazz = genreBuilder.title("Acid Jazz").build();
        
    }
    
    @Test
    public void findByIdTest(){
        genreDao.create(chillWave);
        genreDao.create(acidJazz);
        
        Genre g = genreDao.findById(chillWave.getId());
        Assert.assertEquals(g, chillWave);
        assertDeepEquals(g, chillWave);

    }
    
    @Test
    public void findByIdNonexistingTest(){ 
        Assert.assertNull(genreDao.findById(new Long(1)));
    }
    
    @Test
    public void findByTitle(){
        genreDao.create(chillWave);
        genreDao.create(acidJazz);
        List<Genre> genres = genreDao.findByTitle(chillWave.getTitle());
        Assert.assertEquals(genres.size(), 1);
        Assert.assertEquals(genres.get(0), chillWave);
        assertDeepEquals(genres.get(0), chillWave);

    }
    
    @Test
    public void findByNonExistingTitle(){ 
        genreDao.create(chillWave);
        genreDao.create(acidJazz);
        List<Genre> genres = genreDao.findByTitle("Trip Hop");
        Assert.assertEquals(genres.size(), 0);

    }
       
    @Test
    public void findAllTest(){
       genreDao.create(chillWave);
       genreDao.create(acidJazz);
       Assert.assertEquals(genreDao.findAll().size(), 2);

    }
    
    @Test 
    public void createTest(){
        genreDao.create(chillWave);
        Assert.assertNotNull(chillWave.getId());
        Genre g = genreDao.findById(chillWave.getId());
        Assert.assertEquals(g, chillWave);

    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createWithIdSetTest() {
        Genre badGenre = genreBuilder.id(new Long(1)).title("Electro").build();
        genreDao.create(badGenre);
    }
    
    @Test(expectedExceptions=DataAccessException.class)
    public void createGenreWithSameTitle() {
        Genre downTempo1 = genreBuilder.title("Down Tempo").build();
        Genre downTempo2 = genreBuilder.title("Down Tempo").build();
        genreDao.create(downTempo1);
        genreDao.create(downTempo2);

    }    
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void createGentreWithNullTitle(){
        Genre downTempo1 = genreBuilder.title(null).build();
        genreDao.create(downTempo1);

    }
    
    @Test
    public void updateTest(){
        genreDao.create(chillWave);
        chillWave.setTitle("Chill Tide");
        
        Genre updated = genreDao.update(chillWave);
        Assert.assertEquals(updated, chillWave);
        assertDeepEquals(updated, chillWave);

    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void updateNullTest() {
        genreDao.update(null);
    }
        
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateTitleNullTest() {
        genreDao.create(chillWave);
        chillWave.setTitle(null);
        genreDao.update(chillWave);
        em.flush();
    }
    
    @Test
    public void removeTest(){
        genreDao.create(chillWave);
        genreDao.create(acidJazz);
        Assert.assertEquals(genreDao.findAll().size(), 2);
        
        genreDao.remove(chillWave);
        Assert.assertEquals(genreDao.findAll().size(), 1);
        
        Genre g1 = genreDao.findById(chillWave.getId());
        Genre g2 = genreDao.findById(acidJazz.getId());
        
        Assert.assertNull(g1);
        Assert.assertEquals(g2, acidJazz);
        assertDeepEquals(g2, acidJazz);

    }
    
    public void removeNonExistingTest() {
        em.flush();
        genreDao.create(chillWave);
        Assert.assertEquals(genreDao.findAll().size(), 1);
        
        genreDao.remove(acidJazz);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void removeNullTest() {
        genreDao.remove(null);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void removeAlreadyRemovedTest() {
        genreDao.create(chillWave);
        Assert.assertEquals(genreDao.findById(chillWave.getId()), chillWave);
        
        genreDao.remove(chillWave);
        em.flush();
        Assert.assertEquals(genreDao.findAll().size(), 0);
        
        genreDao.remove(chillWave);
    }
            
    private void assertDeepEquals(Genre actual, Genre expected){
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getTitle(), expected.getTitle());
    }
            
}
