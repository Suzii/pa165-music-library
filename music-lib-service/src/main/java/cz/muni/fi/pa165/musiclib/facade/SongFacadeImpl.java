package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.SongAddYoutubeLinkDTO;
import cz.muni.fi.pa165.musiclib.dto.SongCreateDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.dto.SongSearchDTO;
import cz.muni.fi.pa165.musiclib.dto.SongUpdateDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.exception.MusicLibServiceException;
import cz.muni.fi.pa165.musiclib.exception.NoSuchEntityFoundException;
import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import cz.muni.fi.pa165.musiclib.service.MusicianService;
import cz.muni.fi.pa165.musiclib.service.SongService;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author zdankovc
 * @version 22/11/2015
 */
@Service
@Transactional
public class SongFacadeImpl implements SongFacade {

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private SongService songService;

    @Inject
    private AlbumService albumService;

    @Inject
    private GenreService genreService;

    @Inject
    private MusicianService musicianService;

    @Override
    public Long create(SongCreateDTO song, Long albumId) {
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        Song newSong = new Song();
        newSong.setTitle(song.getTitle());
        newSong.setCommentary(song.getCommentary());
        newSong.setBitrate(song.getBitrate());
        newSong.setGenre(genreService.findById(song.getGenreId()));
        newSong.setMusician(musicianService.findById(song.getMusicianId()));
        if(albumId != null) {
            newSong.setAlbum(albumService.findById(albumId));
        }

        songService.create(newSong);
        return newSong.getId();
    }

    @Override
    public void remove(Long songId) {
        Song song = songService.findById(songId);
        if (song == null) {
            throw new NoSuchEntityFoundException("No such song exists");
        }

        songService.remove(song);
    }

    @Override
    public void update(SongUpdateDTO song) {
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }

        Song persistedSong = songService.findById(song.getId());
        if (persistedSong == null) {
            throw new NoSuchEntityFoundException("No such song exists");
        }

        persistedSong.setTitle(song.getTitle());
        persistedSong.setCommentary(song.getCommentary());
        persistedSong.setBitrate(song.getBitrate());
        persistedSong.setGenre(genreService.findById(song.getGenreId()));
        persistedSong.setMusician(musicianService.findById(song.getMusicianId()));
    }

    @Override
    public void addYoutubeLink(SongAddYoutubeLinkDTO addYoutubeLinkDTO) {
        if (addYoutubeLinkDTO == null) {
            throw new IllegalArgumentException("addYoutubeLinkDTO cannot be null");
        }

        if (addYoutubeLinkDTO.getYoutubeLink() == null) {
            throw new MusicLibServiceException("Cannot add empty youtube link");
        }

        Song song = songService.findById(addYoutubeLinkDTO.getSongId());
        if (song == null) {
            throw new NoSuchEntityFoundException("No such song exists");
        }

        song.setYoutubeLink(addYoutubeLinkDTO.getYoutubeLink());
    }

    @Override
    public SongDTO findById(Long id) {
        Song song = songService.findById(id);
        if (song == null) {
            throw new NoSuchEntityFoundException("No such song exists");
        }

        return beanMappingService.mapTo(song, SongDTO.class);
    }

    @Override
    public List<SongDTO> findAll() {
        return beanMappingService.mapTo(songService.findAll(), SongDTO.class);
    }

    @Override
    public List<SongDTO> findByAlbum(Long albumId) {
        Album album = albumService.findById(albumId);
        if (album == null) {
            throw new NoSuchEntityFoundException("No such album exists");
        }

        return beanMappingService.mapTo(songService.findByAlbum(album), SongDTO.class);
    }

    @Override
    public List<SongDTO> findByMusician(Long musicianId) {
        Musician musician = musicianService.findById(musicianId);
        if (musician == null) {
            throw new NoSuchEntityFoundException("No such musician exists");
        }

        return beanMappingService.mapTo(songService.findByMusician(musician), SongDTO.class);
    }

    @Override
    public List<SongDTO> findByGenre(Long genreId) {
        Genre genre = genreService.findById(genreId);
        if (genre == null) {
            throw new NoSuchEntityFoundException("No such genre exists");
        }

        return beanMappingService.mapTo(songService.findByGenre(genre), SongDTO.class);
    }

    @Override
    public List<SongDTO> search(SongSearchDTO songSearch) {
        if(songSearch == null) {
            throw new IllegalArgumentException("songSearchDTO");
        }
        
        List<SongDTO> filteredByTitle = (songSearch.getTitle() != null && !songSearch.getTitle().trim().isEmpty()) ? 
                beanMappingService.mapTo(songService.findByTitleFragment(songSearch.getTitle()), SongDTO.class) : 
                this.findAll();
        List<SongDTO> result = new ArrayList<>(filteredByTitle);
        
        for(SongDTO song : filteredByTitle) {
            if(!testMusicianMatch(songSearch, song)){
                result.remove(song);
            } else if(!testAlbumMatch(songSearch, song)){
                result.remove(song);
            } else if(!testGenreMatch(songSearch, song)){
                result.remove(song);
            } 
        }
        
        return result;
    }
    
    private static boolean testMusicianMatch(SongSearchDTO songSearch, SongDTO song) {
        return songSearch.getMusicianId() == null || (song.getMusician()!= null && song.getMusician().getId().equals(songSearch.getMusicianId()));
    }
    
    private static boolean testAlbumMatch(SongSearchDTO songSearch, SongDTO song) {
        return songSearch.getAlbumId() == null || (song.getAlbum()!= null && song.getAlbum().getId().equals(songSearch.getAlbumId()));
    }
    
    private static boolean testGenreMatch(SongSearchDTO songSearch, SongDTO song) {
        return songSearch.getGenreId() == null || (song.getGenre()!= null && song.getGenre().getId().equals(songSearch.getGenreId()));
    }
}
