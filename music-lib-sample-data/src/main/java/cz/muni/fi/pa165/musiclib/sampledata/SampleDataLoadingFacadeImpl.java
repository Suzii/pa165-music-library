package cz.muni.fi.pa165.musiclib.sampledata;

import cz.muni.fi.pa165.musiclib.entity.*;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.service.*;
import cz.muni.fi.pa165.musiclib.utils.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
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

    }

    private Genre genre(String title) {
        Genre g = genreBuilder.title("Rock").build();

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
}
