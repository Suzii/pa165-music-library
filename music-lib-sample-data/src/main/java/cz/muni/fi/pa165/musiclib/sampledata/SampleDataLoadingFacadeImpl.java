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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private AlbumBuilder albumBuilder = new AlbumBuilder();
    private SongBuilder songBuilder = new SongBuilder();
    private MusicianBuilder musicianBuilder = new MusicianBuilder();
    private GenreBuilder genreBuilder = new GenreBuilder();
    private UserBuilder userBuilder = new UserBuilder();

    @Override
    public void loadData() throws IOException {
        
        user("admin@gmail.com", "admin", "admin", "admin", true);
        user("skywalker@gmail.com", "deathStar1", "Luke", "Skywalker", false);
        user("gandalf@gmail.com", "YouShallNotPass", "Ganfalf", "Grey", false);
        user("thor@gmail.com", "mjolnir123", "Thor", "Odinsson", false);
        log.info("Music library users loaded.");

        Genre rock = genre("Rock");
        Genre pop = genre("Pop");
        Genre electro = genre("Electro");
        log.info("Music library genres loaded.");
        
        Musician adele = musician("Adele", new ArrayList<Song>(), Sex.FEMALE, new Date(1990, 1, 1));
        Musician brunoMars = musician("Bruno Mars", new ArrayList<Song>(), Sex.MALE, new Date(1985, 1, 1));
        Musician hozier = musician("Hozier", new ArrayList<Song>(), Sex.FEMALE, new Date(1980, 1, 1));
        log.info("Music library musicians loaded.");
        
        Album jukebox = album("Jukebox", "", new Date(2015,1,1), null, null, new ArrayList<Song>());
        Album _21 = album("21", "", new Date(2014,1,1), null, null, new ArrayList<Song>());
        Album hozierAlb = album("Hozier", "", new Date(2014,1,1), null, null, new ArrayList<Song>());
        log.info("Music library albums loaded.");
        
        song("Rolling in the deep", "Best song 2013", 1, 1, _21, adele, pop);
        song("Just the way you are", "Love song lvl.100", 1, 1, jukebox, brunoMars, pop);
        song("Take me to church", "Totaly crazy about this song", 1, 1, hozierAlb, hozier, rock);
    }

    private Genre genre(String title) {
        Genre g = genreBuilder.title(title).build();

        genreService.create(g);
        return g;
    }

    private Song song(String title, String commentary, int positionInAlbum, double bitrate, Album album, Musician musician, Genre genre) {
        Song s = songBuilder.title(title)
                .commentary(commentary)
                .positionInAlbum(positionInAlbum)
                .bitrate(bitrate)
                .album(album)
                .musician(musician)
                .genre(genre)
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
