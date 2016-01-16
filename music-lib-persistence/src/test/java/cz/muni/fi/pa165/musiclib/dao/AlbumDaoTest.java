package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Martin Stefanko
 * @version 15/10/28
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AlbumDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @Inject
    private AlbumDao albumDao;

    private Album album01;
    private Album album02;

    private Musician musician01;
    private Musician musician02;
    private Genre genre01;
    private Genre genre02;
    private Song song01;
    private Song song02;
    private Song song03;

    @BeforeMethod
    public void initTest() {
        //musicians
        musician01 = new MusicianBuilder()
            .artistName("Ed Sheeran")
            .sex(Sex.MALE).build();
        em.persist(musician01);

        musician02 = new MusicianBuilder()
                .artistName("John Williams")
                .sex(Sex.MALE).build();
        em.persist(musician02);

        //genres
        genre01 = new GenreBuilder().title("Folk").build();
        genre02 = new GenreBuilder().title("Epic").build();
        em.persist(genre01);
        em.persist(genre02);

        //songs
        song01 = new SongBuilder()
                .title("I See Fire")
                .musician(musician01)
                .genre(genre01)
                .build();

        song02 = new SongBuilder()
                .title("Binary sunset")
                .musician(musician02)
                .genre(genre02)
                .build();

        song03 = new SongBuilder()
                .title("Imperial march")
                .musician(musician02)
                .genre(genre02)
                .build();

        em.persist(song01);
        em.persist(song02);
        em.persist(song03);

        album01 = getDefaultAlbum01();
        album02 = getDefaultAlbum02();
    }

    @Test
    public void createSuccessTest() {
        albumDao.create(album01);

        Album result = albumDao.findById(album01.getId());
        assertDeepEquals(album01, result);
    }

    @Test
    public void createWithSongsTest() {
        album02.addSong(song02);
        album02.addSong(song03);

        albumDao.create(album02);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createNullTest() {
        albumDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullTitleTest() {
        album01.setTitle(null);

        albumDao.create(album01);
    }

    @Test
    public void updateSuccessTest() {
        albumDao.create(album01);
        album01.setTitle("I'm not from Hobbit");

        Album updated = albumDao.update(album01);
        assertEquals(updated, album01);
        assertDeepEquals(updated, album01);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNullTest() {
        albumDao.update(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateNullTitleTest() {
        albumDao.create(album01);
        album01.setTitle(null);

        Album updated = albumDao.update(album01);
        em.flush();
    }

    @Test
    public void updateAddSongTest() {
        albumDao.create(album01);
        album01.addSong(song01);

        Album updated = albumDao.update(album01);
        em.flush();
        assertDeepEquals(updated, album01);
    }

    @Test
    public void updateCommentaryTest() {
        albumDao.create(album01);
        album01.setCommentary("some other comment");

        Album updated = albumDao.update(album01);
        em.flush();
        assertDeepEquals(updated, album01);
    }

    @Test
    public void removeSuccessTest() {
        albumDao.create(album01);
        assertDeepEquals(album01, albumDao.findById(album01.getId()));

        albumDao.remove(album01);
        assertNull(albumDao.findById(album01.getId()));
    }

    @Test
    public void removeWithSongsAttachedTest() {
        album02.addSong(song02);
        album02.addSong(song03);
        albumDao.create(album02);

        assertDeepEquals(album02, albumDao.findById(album02.getId()));

        albumDao.remove(album02);
        assertNull(albumDao.findById(album02.getId()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeAlreadyRemovedTest() {
        albumDao.create(album01);
        albumDao.remove(album01);
        em.flush();
        
        assertNotNull(album01);
        albumDao.remove(album01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNullTest() {
        albumDao.remove(null);
    }

    @Test
    public void findByIdSuccessTest() {
        albumDao.create(album01);

        Album result = albumDao.findById(album01.getId());
        assertDeepEquals(result, album01);
    }

    @Test
    public void findByInvalidIdTest() {
        Album result = albumDao.findById(0L);
        assertNull(result);
    }

    @Test
    public void findDeletedEntityTest() {
        albumDao.create(album01);
        Album result = albumDao.findById(album01.getId());
        assertNotNull(result);

        albumDao.remove(album01);
        result = albumDao.findById(album01.getId());
        assertNull(result);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findNotCreatedEntityTest() {
        albumDao.create(album01);

        Album result = albumDao.findById(album02.getId());
        assertNull(result);
    }

    @Test
    public void searchByTitleSuccessTest() {
        albumDao.create(album01);

        List<Album> result = albumDao.searchByTitle("The Hobbit: The Desolation of Smaug");
        assertEquals(result, Collections.singletonList(album01));
    }

    @Test
    public void searchByInvalidTitleTest() {
        albumDao.create(album01);

        List<Album> result = albumDao.searchByTitle("Star Wars: Episode IV");
        assertEquals(result, Collections.emptyList());
    }
    
    @Test
    public void searchByTitleFragmentTest() {
        albumDao.create(album01);

        List<Album> result = albumDao.searchByTitle("Hobbit");
        assertEquals(result, Collections.singletonList(album01));
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void searchByTitleNullTest() {
        List<Album> result = albumDao.searchByTitle(null);
    }

    @Test
    public void findAllSuccessTest() {
        albumDao.create(album01);
        albumDao.create(album02);

        List<Album> result = albumDao.findAll();
        assertEquals(result, Arrays.asList(album01, album02));
    }

    @Test
    public void getAlbumSampleValidTest() {
        albumDao.create(album01);
        albumDao.create(album02);

        List<Album> result = albumDao.getAlbumSample(2);
        assertNotNull(result);
        assertEquals(result.size(), 2);
    }

    @Test
    public void getAlbumSampleValidLessCountTest() {
        albumDao.create(album01);
        albumDao.create(album02);

        List<Album> result = albumDao.getAlbumSample(1);
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void getAlbumSampleInvlaidCountTest() {
        albumDao.create(album01);
        albumDao.create(album02);

        List<Album> result = albumDao.getAlbumSample(0);
    }

    private Album getDefaultAlbum01() {
        AlbumBuilder albumBuilder = new AlbumBuilder();
        Calendar cal = new GregorianCalendar(2013, Calendar.DECEMBER, 10);

        return albumBuilder
                .setTitle("The Hobbit: The Desolation of Smaug")
                .setCommentary("soundtrack")
                .setDateOfRelease(cal.getTime())
                .setAlbumArt(loadPictureToByteArray("albumArt01.jpg"))
                .setAlbumArtMimeType("image/jpeg")
                .build();
    }

    private Album getDefaultAlbum02() {
        AlbumBuilder albumBuilder = new AlbumBuilder();
        Calendar cal = new GregorianCalendar(1977, Calendar.MARCH, 5);

        return albumBuilder
                .setTitle("Star Wars: Episode IV")
                .setCommentary("soundtrack")
                .setDateOfRelease(cal.getTime())
                .setAlbumArt(loadPictureToByteArray("albumArt02.jpg"))
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

    private void assertDeepEquals(Album album01, Album album02) {
        assertEquals(album01.getId(), album02.getId());
        assertEquals(album01.getTitle(), album02.getTitle());
        assertEquals(album01.getCommentary(), album02.getCommentary());
        assertEquals(album01.getDateOfRelease(), album02.getDateOfRelease());
        assertEquals(album01.getAlbumArt(), album02.getAlbumArt());
        assertEquals(album01.getAlbumArtMimeType(), album02.getAlbumArtMimeType());
        assertEquals(album01.getSongs(), album02.getSongs());
    }
}
