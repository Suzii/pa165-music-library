package cz.muni.fi.pa165.musiclib.persistence;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * @author xstefank
 * @version 10/24/15
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
