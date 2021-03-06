package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.SongAddYoutubeLinkDTO;
import cz.muni.fi.pa165.musiclib.dto.SongCreateDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.dto.SongSearchCriteriaDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.service.AlbumServiceImpl;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.musiclib.service.GenreServiceImpl;
import cz.muni.fi.pa165.musiclib.service.MusicianServiceImpl;
import cz.muni.fi.pa165.musiclib.service.SongServiceImpl;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 *
 * @author Milan Seman
 * @version 15/11/26
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SongFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AlbumServiceImpl albumService;

    @Mock
    private SongServiceImpl songService;

    @Mock
    private MusicianServiceImpl musicianService;

    @Mock
    private GenreServiceImpl genreService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final SongFacadeImpl songFacade = new SongFacadeImpl();

    AlbumBuilder albumBuilder = new AlbumBuilder();
    SongBuilder songBuilder = new SongBuilder();
    MusicianBuilder musicianBuilder = new MusicianBuilder();
    GenreBuilder genreBuilder = new GenreBuilder();

    private Song song1A;
    private Song song1B;
    private Song song2A;

    private Genre genre2;
    private Genre genre;

    private Album album2;
    private Album album1;

    private Musician musician;
    private Musician musician2;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        song1A = songBuilder.id(1l).title("song1A").build();
        song1B = songBuilder.id(2l).title("song1B").build();
        song2A = songBuilder.id(3l).title("song2A").build();

        genre = genreBuilder.id(1l).title("genre").build();
        genre2 = genreBuilder.id(2l).title("genre2").build();

        album1 = albumBuilder.id(1l).title("album").build();
        album2 = albumBuilder.id(2l).title("album2").build();

        musician = musicianBuilder.id(1l).artistName("name").build();
        musician2 = musicianBuilder.id(2l).artistName("name2").build();

        song1A.setAlbum(album1);
        song1B.setAlbum(album1);
        song2A.setAlbum(album2);

        song1A.setGenre(genre);
        song1B.setGenre(genre2);
        song2A.setGenre(genre2);

        song1A.setMusician(musician);
        song1B.setMusician(musician);
        song2A.setMusician(musician2);
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        // findById
        when(albumService.findById(1l)).thenReturn(album1);
        when(albumService.findById(0l)).thenReturn(null);

        when(songService.findById(1l)).thenReturn(song1A);
        when(songService.findById(2l)).thenReturn(song1B);

        when(genreService.findById(1l)).thenReturn(genre);
        when(genreService.findById(2l)).thenReturn(genre2);

        when(musicianService.findById(1l)).thenReturn(musician);
        when(musicianService.findById(2l)).thenReturn(musician2);

        //findByAlbum
        when(songService.findByAlbum(album1)).thenReturn(Arrays.asList(song1A, song1B));

        //findByMusican
        when(songService.findByMusician(musician2)).thenReturn(Arrays.asList(song2A));

        // findByGenre
        when(songService.findByGenre(genre)).thenReturn(Arrays.asList(song1A, song1B));

        //findAll
        when(songService.findAll()).thenReturn(Arrays.asList(song1A, song1B, song2A));
    }

    @Test(enabled = false)
    public void testClassInitializationTest() {
        assertNotNull(albumService);
        assertNotNull(songService);
        assertNotNull(genreService);
        assertNotNull(musicianService);
        assertNotNull(songFacade);
    }

    @Test
    public void createSongTest() {
        SongCreateDTO songDto = new SongCreateDTO();
        songDto.setTitle("My new song");

        songFacade.create(songDto, album1.getId());
        verify(songService).create(any(Song.class));
    }

    @Test
    public void addYTLinkTest() {
        SongAddYoutubeLinkDTO songYTLinkDTO = new SongAddYoutubeLinkDTO();
        songYTLinkDTO.setSongId(song1A.getId());
        songYTLinkDTO.setYoutubeLink("https://www.youtube.com/kngrd6sef");

        songFacade.addYoutubeLink(songYTLinkDTO);
        assertEquals(song1A.getYoutubeLink(), songYTLinkDTO.getYoutubeLink());
    }

    @Test
    public void findByIdTest() {
        songFacade.findById(1l);
        verify(songService, atLeastOnce()).findById(1l);
    }

    @Test
    public void getAllSongsTest() {
        songFacade.findAll();
        verify(songService).findAll();
    }

    @Test
    public void getSongsByAlbumTest() {
        songFacade.findByAlbum(album1.getId());
        verify(songService, atLeastOnce()).findByAlbum(album1);
    }

    @Test
    public void getSongsByGenreTest() {
        songFacade.findByGenre(1L);
        verify(songService, atLeastOnce()).findByGenre(any(Genre.class));
    }

    @Test
    public void getSongsByMusicianTest() {
        songFacade.findByMusician(2l);
        verify(songService, atLeastOnce()).findByMusician(any(Musician.class));
    }

    @Test
    public void searchWithTitleTest() {
        List<Song> expected = Arrays.asList(new Song[]{this.song1A, this.song1B, this.song2A});
        when(songService.findAll()).thenReturn(expected);
        when(songService.searchByTitle("song")).thenReturn(expected);

        SongSearchCriteriaDTO searchCriteria = new SongSearchCriteriaDTO();
        searchCriteria.setTitle("song");
        List<SongDTO> result = songFacade.search(searchCriteria);
        assertEquals(result.size(), 3);
        testSongsCriteria(result, searchCriteria);
    }

    @Test
    public void searchWithTitleAndAlbumTest() {
        List<Song> expected = Arrays.asList(new Song[]{this.song1A, this.song1B, this.song2A});
        when(songService.findAll()).thenReturn(expected);
        when(songService.searchByTitle("song")).thenReturn(expected);

        SongSearchCriteriaDTO searchCriteria = new SongSearchCriteriaDTO();
        searchCriteria.setTitle("song");
        searchCriteria.setAlbumId(this.album1.getId());
        List<SongDTO> result = songFacade.search(searchCriteria);

        assertEquals(result.size(), 2);
        testSongsCriteria(result, searchCriteria);
    }

    @Test
    public void searchWithMusicianTest() {
        List<Song> expected = Arrays.asList(new Song[]{this.song1A, this.song1B, this.song2A});
        when(songService.findAll()).thenReturn(expected);

        SongSearchCriteriaDTO searchCriteria = new SongSearchCriteriaDTO();
        searchCriteria.setMusicianId(this.musician2.getId());
        List<SongDTO> result = songFacade.search(searchCriteria);

        assertEquals(result.size(), 1);
        testSongsCriteria(result, searchCriteria);
    }

    @Test
    public void searchWithGenreTest() {
        List<Song> expected = Arrays.asList(new Song[]{this.song1A, this.song1B, this.song2A});
        when(songService.findAll()).thenReturn(expected);

        SongSearchCriteriaDTO searchCriteria = new SongSearchCriteriaDTO();
        searchCriteria.setGenreId(this.genre.getId());
        List<SongDTO> result = songFacade.search(searchCriteria);

        assertEquals(result.size(), 1);
        testSongsCriteria(result, searchCriteria);
    }
    
    @Test
    public void serachWithFullCriteriaTest() {
        List<Song> expected = Arrays.asList(new Song[]{this.song1A, this.song1B, this.song2A});
        when(songService.findAll()).thenReturn(expected);
        when(songService.searchByTitle("so")).thenReturn(expected);

        SongSearchCriteriaDTO searchCriteria = new SongSearchCriteriaDTO();
        searchCriteria.setTitle("so");
        searchCriteria.setAlbumId(this.album1.getId());
        searchCriteria.setMusicianId(this.musician.getId());
        searchCriteria.setGenreId(this.genre.getId());
        List<SongDTO> result = songFacade.search(searchCriteria);

        testSongsCriteria(result, searchCriteria);
    }
    
        @Test
    public void serachWithFullCriteriaAndEmptyResultTest() {
        List<Song> expected = Arrays.asList(new Song[]{this.song1A, this.song1B, this.song2A});
        when(songService.findAll()).thenReturn(expected);
        when(songService.searchByTitle("so")).thenReturn(expected);

        SongSearchCriteriaDTO searchCriteria = new SongSearchCriteriaDTO();
        searchCriteria.setTitle("so");
        searchCriteria.setAlbumId(this.album1.getId());
        searchCriteria.setMusicianId(this.musician2.getId());
        searchCriteria.setGenreId(this.genre.getId());
        List<SongDTO> result = songFacade.search(searchCriteria);

        assertTrue(result.isEmpty());
    }

    private void testSongsCriteria(List<SongDTO> songs, SongSearchCriteriaDTO criteria) {
        for (SongDTO song : songs) {
            testTitleInSong(song, null);
            testMusitianInSong(song, criteria.getMusicianId());
            testAlbumsInSongs(song, criteria.getAlbumId());
            testGenresInSongs(song, criteria.getGenreId());
        }
    }

    private void testTitleInSong(SongDTO song, String titleFragment) {
        if (titleFragment == null || titleFragment.trim().isEmpty()) {
            return;
        }

        if (titleFragment != null && titleFragment.trim().isEmpty()) {
            if (song.getTitle().trim().isEmpty() || !song.getTitle().contains(titleFragment)) {
                fail();
            }
        }
    }

    private void testMusitianInSong(SongDTO song, Long musitianId) {
        if (musitianId != null && !Objects.equals(song.getMusician().getId(), musitianId)) {
            fail();
        }
    }

    private void testAlbumsInSongs(SongDTO song, Long albumId) {
        if (albumId != null && !Objects.equals(song.getAlbum().getId(), albumId)) {
            fail();
        }
    }

    private void testGenresInSongs(SongDTO song, Long genreId) {
        if (genreId != null && !Objects.equals(song.getGenre().getId(), genreId)) {
            fail();
        }
    }
}
