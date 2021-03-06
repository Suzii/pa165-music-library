package cz.muni.fi.pa165.musiclib.persistence;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * @author Martin Stefanko
 * @version 15/10/24
 */
@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContainerManagedEMTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    private SongDao songDao;

    @Test
    public void entityMangerTest() {
//        Song song = new Song();
//        Album a = new Album();
//        Genre g = new Genre();
//        g.setTitle("Best genre");
//        Musician m = new Musician();
//        m.setArtistName("Best band");
//        m.setDateOfBirth(new Date());
//
//
//
//        song.setAlbum(a);
//        song.setBitrate(2.56);
//        song.setCommentary("best song ever");
//        song.setGenre(g);
//        song.setMusician(m);
//        song.setPositionInAlbum(1);
//        song.setTitle("The best song in the world");
//
//        songDao.create(song);
//
//        Song result = songDao.findById(song.getId());
//
//        assertEquals(song, result);
    }

}
