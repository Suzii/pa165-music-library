package cz.muni.fi.pa165.musiclib.sampledata;

import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import cz.muni.fi.pa165.musiclib.service.MusicianService;
import cz.muni.fi.pa165.musiclib.service.SongService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;

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

    @Override
    public void loadData() throws IOException {

    }
}
