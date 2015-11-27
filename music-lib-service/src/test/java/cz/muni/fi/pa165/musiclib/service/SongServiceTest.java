package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dao.AlbumDao;
import cz.muni.fi.pa165.musiclib.dao.GenreDao;
import cz.muni.fi.pa165.musiclib.dao.MusicianDao;
import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Milan
 * @version 26/11/2015
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class SongServiceTest extends AbstractTestNGSpringContextTests{

    @Mock
    public MusicianDao musicianDao;
    
    @Mock
    public SongDao songDao;

    @Mock
    public GenreDao genreDao;

    @Mock
    public AlbumDao albumDao;
    
    @InjectMocks
    private SongService songService = new SongServiceImpl();

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

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    private void init() {

        genre = genreBuilder.title("genre title").build();
        genreDao.create(genre);

        musician1 = musicianBuilder.artistName("artist1").sex(Sex.MALE).build();
        musician2 = musicianBuilder.artistName("artist2").sex(Sex.FEMALE).build();

        musicianDao.create(musician1);
        musicianDao.create(musician2);

        album1 = albumBuilder.title("album1").build();
        album2 = albumBuilder.title("album2").build();

        albumDao.create(album1);
        albumDao.create(album2);

        song1A = songBuilder.id(1l).title("song1").build();
        song1B = songBuilder.id(2l).title("song2").build();
        song2A = songBuilder.id(3l).title("song3").build();
        song2B = songBuilder.id(4l).title("song4").build();

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
    
    @BeforeMethod
    public void initMockBehaviour(){
        when(songDao.findById(1l)).thenReturn(song1A);
        when(songDao.findById(2l)).thenReturn(song2A);
        when(songDao.findById(0l)).thenReturn(null);
        
        //create
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if(argument == null) {
                    throw new IllegalArgumentException();
                }
                
                Song song = (Song) argument;
                if(song.getId() != null){
                    throw new IllegalArgumentException();
                }
                
                SetIdHelper.setId(song, 78l);                
                return null;
            }
        }).when(songDao).create(any(Song.class));
        
        //update
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Song song = (Song) argument;
                if (song.getId() == null) {
                    throw new IllegalArgumentException();
                }

                return cloneSong(song);
            }
        }).when(songDao).update(any(Song.class));

        //remove
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                Song song = (Song) argument;
                if (song.getId() == null) {
                    throw new IllegalArgumentException();
                }

                when(songDao.findById(song.getId())).thenThrow(IllegalArgumentException.class);

                return null;
            }
        }).when(songDao).remove(any(Song.class));
        
        
        when(songDao.findById(1L)).thenReturn(song1A);
        when(songDao.findById(2L)).thenReturn(song1B);
        when(songDao.findById(3L)).thenReturn(song2A);
        when(songDao.findById(4L)).thenReturn(song2B);
        when(songDao.findAll()).thenReturn(Arrays.asList(song1A, song1B, song2A, song2B));
        when(songDao.findByMusician(musician1)).thenReturn(Arrays.asList(song1A, song2A));
        when(songDao.findByAlbum(album1)).thenReturn(Arrays.asList(song1A, song1B));
        when(songDao.findByGenre(genre)).thenReturn(Arrays.asList(song1A, song1B, song2A, song2B));
    }
    

    
    @Test
    public void testClassInitializationTest() {
        assertNotNull(albumDao);
        assertNotNull(songDao);
        assertNotNull(musicianDao);
        assertNotNull(genreDao);
        assertNotNull(songService);
    }
    
    @Test
    public void findByIdTest(){
        Song song = songService.findById(1L);
        
        assertNotNull(song);
        assertEquals(song.getId(), song1A.getId());
        assertEquals(song.getTitle(), song1A.getTitle());
        assertEquals(song.getAlbum(), song1A.getAlbum());
        assertEquals(song.getMusician(), song1A.getMusician());
        assertEquals(song.getGenre(), song1A.getGenre());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void findNullTest(){
        when(songDao.findById(null)).thenThrow(IllegalArgumentException.class);
        
        songService.findById(null);
    }
    
    @Test
    public void findByValidMusicianTest() {
        List<Song> songs = songService.findByMusician(musician1);

        assertFalse(songs.isEmpty());
        assertEquals(songs.size(), 2);
        assertEquals(songs.get(0).getId(), song1A.getId());
        assertEquals(songs.get(0).getAlbum(), song1A.getAlbum());
        assertEquals(songs.get(0).getMusician(), song1A.getMusician());
        assertEquals(songs.get(0).getGenre(), song1A.getGenre());
        assertEquals(songs.get(1).getId(), song2A.getId());
        assertEquals(songs.get(1).getAlbum(), song2A.getAlbum());
        assertEquals(songs.get(1).getMusician(), song2A.getMusician());
        assertEquals(songs.get(1).getGenre(), song2A.getGenre());
    }

    @Test
    public void findNullMusicianTest() {
        List<Song> song = songService.findByMusician(null);

        assertTrue(song.isEmpty());
    }
    

    @Test
    public void findInvalidMusicianTest() {
        Musician musician3 = musicianBuilder.artistName("Bonobo").sex(Sex.MALE).build();
        
        List<Song> song = songService.findByMusician(musician3);

        assertTrue(song.isEmpty());
    }

     @Test
    public void findByValidAlbumTest() {
        List<Song> songs = songService.findByAlbum(album1);

        assertFalse(songs.isEmpty());
        assertEquals(songs.size(), 2);
        assertEquals(songs.get(0).getId(), song1A.getId());
        assertEquals(songs.get(0).getAlbum(), song1A.getAlbum());
        assertEquals(songs.get(0).getMusician(), song1A.getMusician());
        assertEquals(songs.get(0).getGenre(), song1A.getGenre());
        assertEquals(songs.get(1).getId(), song1B.getId());
        assertEquals(songs.get(1).getAlbum(), song1B.getAlbum());
        assertEquals(songs.get(1).getMusician(), song1B.getMusician());
        assertEquals(songs.get(1).getGenre(), song1B.getGenre());
    }

    @Test
    public void findNullAlbumTest() {
        List<Song> song = songService.findByAlbum(null);

        assertTrue(song.isEmpty());
    }
    

    @Test
    public void findInvalidAlbumTest() {
        Album albumX = albumBuilder.title("albumX").build();
        
        List<Song> song = songService.findByAlbum(albumX);

        assertTrue(song.isEmpty());
    }

     @Test
    public void findByValidGenreTest() {
        List<Song> songs = songService.findByGenre(genre);

        assertFalse(songs.isEmpty());
        assertEquals(songs.size(), 4);
        assertEquals(songs.get(0).getId(), song1A.getId());
        assertEquals(songs.get(0).getAlbum(), song1A.getAlbum());
        assertEquals(songs.get(0).getMusician(), song1A.getMusician());
        assertEquals(songs.get(0).getGenre(), song1A.getGenre());
        assertEquals(songs.get(1).getId(), song1B.getId());
        assertEquals(songs.get(1).getAlbum(), song1B.getAlbum());
        assertEquals(songs.get(1).getMusician(), song1B.getMusician());
        assertEquals(songs.get(1).getGenre(), song1B.getGenre());
        assertEquals(songs.get(2).getId(), song2A.getId());
        assertEquals(songs.get(2).getAlbum(), song2A.getAlbum());
        assertEquals(songs.get(2).getMusician(), song2A.getMusician());
        assertEquals(songs.get(2).getGenre(), song2A.getGenre());
        assertEquals(songs.get(3).getId(), song2B.getId());
        assertEquals(songs.get(3).getAlbum(), song2B.getAlbum());
        assertEquals(songs.get(3).getMusician(), song2B.getMusician());
        assertEquals(songs.get(3).getGenre(), song2B.getGenre());
    }

    @Test
    public void findNullGenreTest() {
        List<Song> song = songService.findByGenre(null);

        assertTrue(song.isEmpty());
    }
    

    @Test
    public void findInvalidGenreTest() {
        Genre genreX = genreBuilder.title("another title").build();
        
        List<Song> song = songService.findByGenre(genreX);

        assertTrue(song.isEmpty());
    }
    
    @Test
    public void findAllTest() {
        List<Song> songs = songService.findAll();

        assertFalse(songs.isEmpty());
        assertEquals(songs.size(), 4);
        assertEquals(songs.get(0).getId(), song1A.getId());
        assertEquals(songs.get(0).getAlbum(), song1A.getAlbum());
        assertEquals(songs.get(0).getMusician(), song1A.getMusician());
        assertEquals(songs.get(0).getGenre(), song1A.getGenre());
        assertEquals(songs.get(1).getId(), song1B.getId());
        assertEquals(songs.get(1).getAlbum(), song1B.getAlbum());
        assertEquals(songs.get(1).getMusician(), song1B.getMusician());
        assertEquals(songs.get(1).getGenre(), song1B.getGenre());
        assertEquals(songs.get(2).getId(), song2A.getId());
        assertEquals(songs.get(2).getAlbum(), song2A.getAlbum());
        assertEquals(songs.get(2).getMusician(), song2A.getMusician());
        assertEquals(songs.get(2).getGenre(), song2A.getGenre());
        assertEquals(songs.get(3).getId(), song2B.getId());
        assertEquals(songs.get(3).getAlbum(), song2B.getAlbum());
        assertEquals(songs.get(3).getMusician(), song2B.getMusician());
        assertEquals(songs.get(3).getGenre(), song2B.getGenre());
    }
    
    @Test
    public void findInvalidIdTest() {
        Song song = songService.findById(645L);

        assertNull(song);
    }
    
    @Test
    public void createTest() {
        Song newSong = songBuilder.id(null).title("Subtitle").album(album1)
                       .musician(musician1).genre(genre).build();
       
        
        songService.create(newSong);
        
        assertNotNull(newSong);
        assertNotNull(newSong.getId());
    }
    
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createIdAlreadySetTest() {
        Song newSong = songBuilder.id(1L).title("The art of chill").build();
        
        songService.create(newSong);
    }

    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void createNullTest(){
        songService.create(null);
    }

    @Test
    public void updateValidTest() {
        song1A.setTitle("new title");
        
        Song newSong = songService.update(song1A);
        
        assertNotNull(newSong);
        assertNotNull(newSong.getId());
        assertNotNull(newSong.getTitle());
        assertNotNull(newSong.getGenre());
        assertNotNull(newSong.getAlbum());
        assertNotNull(newSong.getMusician());
    }
    
    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullTest() {
        songService.update(null);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullTitleTest() {
        Song song = songBuilder.id(5l).title(null).build();
        when(songDao.update(song)).thenThrow(ConstraintViolationException.class);

        songService.update(song);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateNullIdTest() {
        Song song = songBuilder.id(null).build();

        songService.update(song);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void updateInvalidGenreTest() {
        Song song = songBuilder.id(5l).title("bad song").genre(null).build();
        when(songDao.update(song)).thenThrow(ConstraintViolationException.class);
        
        songService.update(song);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeTest() {
        Song toBeRemoved = songBuilder.id(48L).title("bad song to be removed").build();

        when(songDao.findById(toBeRemoved.getId())).thenReturn(toBeRemoved);

        assertNotNull(songService.findById(toBeRemoved.getId()));
        assertEquals(songService.findById(toBeRemoved.getId()), toBeRemoved);

        songService.remove(toBeRemoved);
        songService.findById(toBeRemoved.getId());
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeWithIdNullTest() {
        Song newSong = songBuilder.title("bad song to be removed").id(null).build();

        songService.remove(newSong);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void removeNullTest() {
        songService.remove(null);
    }
    
    private Song cloneSong(Song song, Long id) {
        return songBuilder.id(id) //set new id
                .title(song.getTitle())
                .commentary(song.getCommentary())
                .positionInAlbum(song.getPositionInAlbum())
                .bitrate(song.getBitrate())
                .album(song.getAlbum())
                .musician(song.getMusician())
                .genre(song.getGenre())
                .build();
    }

    private Song cloneSong(Song song) {
        return cloneSong(song, song.getId());
    }
    
    // UPDATE bussiness logic
    
        /*
    @Test
    public void createWithPositionTest() {
        Song newSong = songBuilder.id(54l).title("Song1").album(album1)
                       .musician(musician1).genre(genre).build();
        Song newSong2 = songBuilder.id(87l).title("Song2").album(album1)
                       .musician(musician1).genre(genre).build();
        songService.create(newSong);
        songService.create(newSong2);
        
        assertEquals(song1B.getPositionInAlbum(), 1);
    }
    */
    
    @Test
    public void updateAlbumSetToNullTest(){
        Song song = songBuilder.id(42l).title("Poor song").album(null).positionInAlbum(3).build();
        
        songService.update(song);
        assertNotNull(song);
        assertNull(song.getAlbum());
        assertEquals(song.getPositionInAlbum(), 0);
    }
    
    @Test
    public void updateAlbumSetAndPositionIsZetoTest(){
        Album blankAlbum = albumBuilder.id(5l).title("BlankAlbum").build();
        Song song = songBuilder.id(42l).title("Poor song").album(blankAlbum).positionInAlbum(0).build();
        
        songService.update(song);
        assertNotNull(song);
        assertNotNull(song.getAlbum());
        assertEquals(song.getPositionInAlbum(), 1);
    }
    
    @Test
    public void updateAlbumSetAndPositionIsBiggerThenNumberOfSongsTest(){
        Album blankAlbum = albumBuilder.id(5l).title("BlankAlbum").build();
        Song song = songBuilder.id(42l).title("Poor song").album(blankAlbum).positionInAlbum(3).build();
        
        songService.update(song);
        assertNotNull(song);
        assertNotNull(song.getAlbum());
        assertEquals(song.getPositionInAlbum(), 3);
    }
    
    @Test
    public void updateCorrectlySetsPositionTest(){
        Album albumWithSong = albumBuilder.id(5l).title("BlankAlbum").build();
        Song oldSong = songBuilder.id(42l).title("Old song").album(albumWithSong).positionInAlbum(1).build();
        Song newSong = songBuilder.id(42l).title("New song").album(albumWithSong).positionInAlbum(0).build();
        
        songService.update(newSong);
        assertNotNull(newSong);
        assertNotNull(newSong.getAlbum());
        assertEquals(newSong.getPositionInAlbum(), 2);
    }
    
    @Test
    public void updateCorrectlySetsPositionToLowestAvailableTest(){
        Album albumWithSong = albumBuilder.id(5l).title("BlankAlbum").build();
        Song oldSong = songBuilder.id(42l).title("Old song").album(albumWithSong).positionInAlbum(2).build();
        Song newSong = songBuilder.id(42l).title("New song").album(albumWithSong).positionInAlbum(0).build();
        
        songService.update(newSong);
        assertNotNull(newSong);
        assertNotNull(newSong.getAlbum());
        assertEquals(newSong.getPositionInAlbum(), 1);
    }
    
    
    @Test(expectedExceptions = MusicLibServiceException.class)
    public void updatePositionIsAlreadyTakenTest(){
        Album albumWithSong = albumBuilder.id(5l).title("BlankAlbum").build();
        Song oldSong = songBuilder.id(42l).title("Old song").album(albumWithSong).positionInAlbum(1).build();
        Song newSong = songBuilder.id(42l).title("New song").album(albumWithSong).positionInAlbum(1).build();
        
        songService.update(newSong);
    }
}
