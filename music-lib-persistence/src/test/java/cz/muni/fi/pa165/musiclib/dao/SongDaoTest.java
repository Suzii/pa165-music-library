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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author David Boron
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SongDaoTest extends AbstractTestNGSpringContextTests {

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
    private Genre genre;
    private Album album1;
    private Album album2;
    private Song song1A;
    private Song song1B;
    private Song song2A;
    private Song song2B;

    @BeforeMethod
    private void init() {

        genre = genreBuilder.title("genre title").build();
        genreDao.create(genre);

        musician1 = musicianBuilder.artistName("artist1").sex(Sex.MALE).build();
        musician2 = musicianBuilder.artistName("artist2").sex(Sex.FEMALE).build();

        musicianDao.create(musician1);
        musicianDao.create(musician2);

        album1 = albumBuilder.setTitle("album1").build();
        album2 = albumBuilder.setTitle("album2").build();

        albumDao.create(album1);
        albumDao.create(album2);

        song1A = songBuilder.title("song1").build();
        song1B = songBuilder.title("song2").build();
        song2A = songBuilder.title("song3").build();
        song2B = songBuilder.title("song4").build();

        song1A.setAlbum(album1);
        song1B.setAlbum(album1);
        song2A.setAlbum(album2);
        song2B.setAlbum(album2);

        song1A.setGenre(genre);
        song1B.setGenre(genre);
        song2A.setGenre(genre);
        song2B.setGenre(genre);

        song1A.setMusician(musician1);
        song1B.setMusician(musician2);
        song2A.setMusician(musician1);
        song2B.setMusician(musician2);
    }

    @Test
    public void findByIdTest() {
        songDao.create(song1A);
        Song s = songDao.findById(song1A.getId());
        assertDeepEquals(s, song1A);
    }

    @Test
    public void findByIdNonexistingTest() {
        Assert.assertNull(songDao.findById(1L));
    }
    
    @Test
    public void searchByTitleFragmentFullTitleTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        List<Song> result = songDao.searchByTitle("song1");
        
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.contains(song1A));
    }
    
    @Test
    public void searchByTitleFragmentPartialTitleTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        List<Song> result = songDao.searchByTitle("song");
        
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertTrue(result.contains(song1A));
        assertTrue(result.contains(song1B));
    }
    
    @Test
    public void searchByTitleFragmentEmptyTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        List<Song> result = songDao.searchByTitle("");
        
        assertNotNull(result);
        assertEquals(result.size(), 2);
    }
    
    @Test
    public void searchByTitleFragmentNonMatchingTitleTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        List<Song> result = songDao.searchByTitle("aaa");
        
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }
    
    @Test
    public void findDeletedEntityTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        Song m = songDao.findById(song1A.getId());
        assertDeepEquals(m, song1A);
        
        songDao.remove(song1A);
        Assert.assertNull(songDao.findById(song1A.getId()));
    }
    
    @Test
    public void findAllTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        assertEquals(songDao.findAll().size(), 2);
    }

    @Test
    public void findByAlbumTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        List<Song> albumSongs = album1.getSongs();
        List<Song> songs = songDao.findByAlbum(album1);
        assertEquals(albumSongs, songs);
    }
    
    @Test
    public void findByNullAlbumTest() {
        songDao.create(song1A);
        songDao.create(song1B);

        List<Song> result = songDao.findByAlbum(null);
        assertEquals(result, Collections.emptyList());
    }

    @Test
    public void findByMusicianTest() {
        songDao.create(song1A);
        songDao.create(song2A);
        List<Song> musicianSongs = musician1.getSongs();
        List<Song> songs = songDao.findByMusician(musician1);
        assertEquals(musicianSongs, songs);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findByNullMusicianTest() {
        songDao.findByMusician(null);
    }
        
    @Test
    public void findByGenreTest() {
        songDao.create(song1A);
        songDao.create(song1B);
        songDao.create(song2A);
        songDao.create(song2B);
        List<Song> songs = new ArrayList<Song>();
        songs.add(song1A);
        songs.add(song1B);
        songs.add(song2A);
        songs.add(song2B);
        List<Song> songsByGenre = songDao.findByGenre(genre);
        assertEquals(songsByGenre, songs);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void findByNullGenreTest() {
        songDao.findByGenre(null);
    }
    
    
    @Test(expectedExceptions = DataAccessException.class)
    public void createNullTest() {
        songDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullTitleTest() {
        song1A.setTitle(null);
        songDao.create(song1A);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullMusicianTest() {
        song1A.setMusician(null);
        songDao.create(song1A);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullGenreTest() {
        song1A.setGenre(null);
        songDao.create(song1A);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void updateNullTest() {
        songDao.update(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateNullTitleTest() {
        songDao.create(song1A);
        song1A.setTitle(null);
        songDao.update(song1A);
        em.flush();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateNullMusicianTest() {
        songDao.create(song1A);
        song1A.setMusician(null);
        songDao.update(song1A);
        em.flush();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateNullGenreTest() {
        songDao.create(song1A);
        song1A.setGenre(null);
        songDao.update(song1A);
        em.flush();
    }
    
    @Test
    public void removeSuccessTest() {
        songDao.create(song1A);
        songDao.remove(song1A);
        assertNull(songDao.findById(song1A.getId()));
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void removeNullTest() {
        songDao.remove(null);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void removeRemovedTest() {
        songDao.create(song1A);
        songDao.remove(song1A);
        em.flush();
        assertNotNull(song1A);
        songDao.remove(song1A);
    }
    
    private void assertDeepEquals(Song song1, Song song2) {
        assertEquals(song1.getId(), song2.getId());
        assertEquals(song1.getTitle(), song2.getTitle());
        assertEquals(song1.getCommentary(), song2.getCommentary());
        assertEquals(song1.getPositionInAlbum(), song2.getPositionInAlbum());
        assertEquals(song1.getBitrate(), song2.getBitrate());
        assertEquals(song1.getMusician(), song2.getMusician());
        assertEquals(song1.getGenre(), song2.getGenre());
        assertEquals(song1.getAlbum(), song2.getAlbum());
    }
}
