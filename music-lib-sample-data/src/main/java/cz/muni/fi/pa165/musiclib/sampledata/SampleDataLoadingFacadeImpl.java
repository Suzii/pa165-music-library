package cz.muni.fi.pa165.musiclib.sampledata;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.entity.User;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import cz.muni.fi.pa165.musiclib.service.MusicianService;
import cz.muni.fi.pa165.musiclib.service.SongService;
import cz.muni.fi.pa165.musiclib.service.UserService;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import cz.muni.fi.pa165.musiclib.utils.UserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates and populate the database with sample data.
 *
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/5/15
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    private static final Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);
    public static final String JPEG = "image/jpeg";

    @Inject
    private AlbumService albumService;

    @Inject
    private SongService songService;

    @Inject
    private MusicianService musicianService;

    @Inject
    private GenreService genreService;

    @Inject
    private UserService userService;

    private final AlbumBuilder albumBuilder = new AlbumBuilder();
    private final SongBuilder songBuilder = new SongBuilder();
    private final MusicianBuilder musicianBuilder = new MusicianBuilder();
    private final GenreBuilder genreBuilder = new GenreBuilder();
    private final UserBuilder userBuilder = new UserBuilder();

    Map<String, Genre> genres = new HashMap<String, Genre>();
    Map<String, Album> albums = new HashMap<String, Album>();
    Map<String, Musician> musicians = new HashMap<String, Musician>();
    
    @Override
    public void loadData() throws IOException {
        
        createUsers();
                
        createGenres();
        
        createMusicians();

        createAlbums();
        
        createSongs();
    }

    private void createSongs() {
        Album album = albums.get("21");
        Musician artist = musicians.get("adele");
        Genre genre = genres.get("pop");        
        
        song("Rolling in the Deep", "Best song 2013", 1, 1, album, artist, genre, "rYEDA3JcQqw");
        song("Roumor Has It", "", 2, 1, album, artist, genre, "bgpInZ6OQ40");
        song("Turning Tables", "", 3, 1, album, artist, genre, "bsFCO8-oCEQ");
        song("Don't You Remember", "", 4, 1, album, artist, genre);
        song("Set Fire to the Rain", "", 5, 1, album, artist, genre, "FlsBObg-1BQ");
        song("He Won't Go", "", 6, 1, album, artist, genre);
        song("Take It All", "", 7, 1, album, artist, genre);
        song("I'll be Waiting", "", 8, 1, album, artist, genre);
        song("One and Only", "", 9, 1, album, artist, genre);
        song("Love Song", "", 10, 1, album, artist, genre);
        song("Someone Like You", "Ghoosebumps", 11, 1, album, artist, genre, "hLQl3WQQoQ0");
        
        album = albums.get("jukebox");
        artist = musicians.get("brunoMars");
        song("Young Girls", "Love song lvl.100", 1, 1, album, artist, genre, "CyM5AjiZris");
        song("Locked out of Heaven", "", 2, 1, album, artist, genre, "e-fA-gBCkj0");
        song("Gorilla", "", 3, 1, album, artist, genre);
        song("Treasure", "", 4, 1, album, artist, genre, "nPvuNsRccVw");
        song("Moonshine", "", 5, 1, album, artist, genre);
        song("When I Was Your Man", "", 6, 1, album, artist, genre, "ekzHIouo8Q4");
        song("Natalie", "", 7, 1, album, artist, genre);
        song("Show Me", "", 8, 1, album, artist, genre);
        song("Money Make Her Smile", "", 9, 1, album, artist, genre);
        song("If I knew", "", 10, 1, album, artist, genre);
        
        album = albums.get("hozier");
        artist = musicians.get("hozier");
        genre = genres.get("rock");
        song("Take me to church", "Totaly crazy about this song", 1, 1, album, artist, genre);
        
        album = albums.get("blackSands");
        artist = musicians.get("bonobo");
        genre = genres.get("electro");
        song("Prelude", "", 1, 1, album, artist, genre, "GezIBBJkhME");
        song("Kiara", "", 2, 1, album, artist, genre, "L-kyRh7N-kE");
        song("Kong", "", 3, 1, album, artist, genre, "lZbgyKJkHxQ");
        song("Eyesdown", "", 4, 1, album, artist, genre, "-6bbM5HZz5c");
        song("We Could Forever", "", 5, 1, album, artist, genre, "urCLo4vNpzs");
        song("1009", "", 6, 1, album, artist, genre, "2K_2LTLb5tI");
        song("All in Forms", "", 7, 1, album, artist, genre, "9W-epjHQDZs");
        song("The Keeper", "", 8, 1, album, artist, genre, "Q7Y4sPkTDbE");
        song("Stay the Same", "", 9, 1, album, artist, genre, "TsxQQuIrfhQ");
        song("Animals", "", 10, 1, album, artist, genre, "Q8RzwkyhYCg");
        song("Black Sands", "", 11, 1, album, artist, genre, "cTjF2_-bneM");
        
    }

    private void createAlbums() throws IOException {
        Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 1);
        cal.set(2012, Calendar.DECEMBER, 7);
        albums.put("jukebox", album("Jukebox", "jukebox comment", cal.getTime(), readImage("jukebox.jpg"), JPEG, new ArrayList<Song>()));
        
        cal.set(2011, Calendar.JANUARY, 19);
        albums.put("21", album("21", "the best comments are empty comments", cal.getTime(), readImage("21.jpg"), JPEG, new ArrayList<Song>()));
        
        cal.set(2014, Calendar.FEBRUARY, 15);
        albums.put("hozier", album("Hozier", "", cal.getTime(), readImage("hozier.jpg"), JPEG, new ArrayList<Song>()));
        
        cal.set(2010, Calendar.MARCH, 29);
        albums.put("blackSands", album("Black Sands", "just chill :)", cal.getTime(), readImage("blackSands.jpg"), JPEG, new ArrayList<Song>()));
        
        log.info("Music library albums loaded.");
    }

    private void createMusicians() {
        Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 1);
        cal.set(1988, Calendar.MAY, 5);
        musicians.put("adele", musician("Adele", new ArrayList<Song>(), Sex.FEMALE, cal.getTime()));
        
        cal.set(1985, Calendar.OCTOBER, 8);
        musicians.put("brunoMars", musician("Bruno Mars", new ArrayList<Song>(), Sex.MALE, cal.getTime()));
        
        cal.set(1990, Calendar.MARCH, 17);
        musicians.put("hozier", musician("Hozier", new ArrayList<Song>(), Sex.FEMALE, cal.getTime()));
        
        cal.set(1976, Calendar.MARCH, 30);
        musicians.put("bonobo", musician("Bonobo", new ArrayList<Song>(), Sex.MALE, cal.getTime()));
        
        log.info("Music library musicians loaded.");
    }

    private void createGenres() {
        genres.put("rock", genre("Rock"));
        genres.put("pop", genre("Pop"));
        genres.put("rap", genre("Rap"));
        genres.put("electro", genre("Electro"));
        genres.put("rnb", genre("RnB"));
        genres.put("soul", genre("Soul"));
        genres.put("metal", genre("Metal"));
        genres.put("country", genre("country"));
        
        log.info("Music library genres loaded.");
    }

    private void createUsers() {
        user("admin@gmail.com", "admin", "admin", "admin", true);
        user("test@test.com", "Tester99", "test", "test", false);
        user("skywalker@gmail.com", "deathStar1", "Luke", "Skywalker", false);
        user("gandalf@gmail.com", "YouShallNotPass", "Ganfalf", "Grey", false);
        user("thor@gmail.com", "mjolnir123", "Thor", "Odinsson", false);
        
        log.info("Music library users loaded.");
    }

    private Genre genre(String title) {
        Genre g = genreBuilder.title(title).build();

        genreService.create(g);
        return g;
    }

    private Song song(String title, String commentary, int positionInAlbum, double bitrate, Album album, Musician musician, Genre genre) {
        return this.song(title, commentary, positionInAlbum, bitrate, album, musician, genre, null);
    }
    
    private Song song(String title, String commentary, int positionInAlbum, double bitrate, Album album, Musician musician, Genre genre, String youtubeLink) {
        Song s = songBuilder.title(title)
                .commentary(commentary)
                .positionInAlbum(positionInAlbum)
                .bitrate(bitrate)
                .album(album)
                .musician(musician)
                .genre(genre)
                .youtubeLink(youtubeLink)
                .build();

        songService.create(s);
        return s;
    }

    private Album album(String title, String commentary, Date dateOfRelease, byte[] albumArt, String albumArtMimeType, List<Song> songs) {
        Album a = albumBuilder.title(title)
                .commentary(commentary)
                .dateOfRelease(dateOfRelease)
                .albumArt(albumArt)
                .albumArtMimeType(albumArtMimeType)
                .songs(songs)
                .build();

        albumService.create(a);
        return a;
    }

    private Musician musician(String artistName, List<Song> songs, Sex sex, Date dateOfBirth) {
        Musician m = musicianBuilder.artistName(artistName)
                .songs(songs)
                .sex(sex)
                .dateOfBirth(dateOfBirth)
                .build();

        musicianService.create(m);
        return m;
    }

    private User user(String email, String passwordHash, String firstName, String lastName, boolean admin) {
        User u = userBuilder.email(email)
                .passwordHash(passwordHash)
                .firstName(firstName)
                .lastName(lastName)
                .admin(admin)
                .build();

        userService.registerUser(u, u.getPasswordHash());
        return u;
    }

    private byte[] readImage(String file) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream("/" + file)) {
            int nRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }
}
