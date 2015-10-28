package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.testng.Assert.assertNotNull;

/**
 * @author xstefank
 * @version 10/28/15
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AlbumDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private AlbumDao albumDao;

    @Inject
    private SongDao songDao;

    private Album album01;

    private Musician metallica;
    private Genre metal;
    private Song song01;

    @BeforeMethod
    public void initTest() {
        metallica = new Musician();
        metallica.setArtistName("Metallica");
        metallica.setSex(Sex.MALE);

        em.persist(metallica);

        metal = new Genre();
        metal.setTitle("metal");

        em.persist(metal);

        song01 = new SongBuilder()
                .title("Fade to black")
                .musician(metallica)
                .genre(metal)
                .build();

        songDao.create(song01);

        album01 = getDefaultAlbum01();
        album01.addSong(song01);

        albumDao.create(album01);
    }

    @Test
    public void testingTest() {
        System.out.println(album01.getTitle());
    }

    private Album getDefaultAlbum01() {
        AlbumBuilder albumBuilder = new AlbumBuilder();
        Calendar cal = new GregorianCalendar(1984, Calendar.JULY, 27);

        return albumBuilder
                .setTitle("Ride the lighting")
                .setCommentary("the best album")
                .setDateOfRelease(cal.getTime())
                .setAlbumArt(loadPictureToByteArray("rtl.jpg"))
                .setAlbumArtMimeType("image/jpeg")
                .build();
    }

    private byte[] loadPictureToByteArray(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        byte[] picture = new byte[0];
        try {
            picture = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return picture;
    }
}
