package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.exception.NoSuchEntityFoundException;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author David
 */
@Service
@Transactional
public class GenreFacadeImpl implements GenreFacade {

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private GenreService genreService;

    @Override
    public Long create(GenreDTO genreDto) {
        if (genreDto == null) {
            throw new IllegalArgumentException("genre title cannot be null");
        }

        Genre genre = new Genre();
        genre.setTitle(genreDto.getTitle());
        genreService.create(genre);
        return genre.getId();
    }

    @Override
    public void changeTitle(GenreDTO newGenreTitle) {
        if (newGenreTitle == null) {
            throw new IllegalArgumentException("newGenreTitle title cannot be null");
        }

        Genre genre = genreService.findById(newGenreTitle.getId());
        if (genre == null) {
            throw new NoSuchEntityFoundException("No such genre exists");
        }

        genreService.changeTitle(genre, newGenreTitle.getTitle());
    }

    @Override
    public void deleteGenre(Long genreId) {
        Genre genre = genreService.findById(genreId);
        if (genre == null) {
            throw new NoSuchEntityFoundException("No such genre exists");
        }

        genreService.remove(genre);
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return beanMappingService.mapTo(genreService.findAll(), GenreDTO.class);
    }

    @Override
    public GenreDTO getGenreById(Long id) {
        Genre genre = genreService.findById(id);
        if (genre == null) {
            throw new NoSuchEntityFoundException("No such genre exists");
        }

        return beanMappingService.mapTo(genre, GenreDTO.class);
    }

    @Override
    public List<GenreDTO> searchGenreByTitle(String title) {
        List<Genre> genres = genreService.searchByTitle(title);
        if (genres == null) {
            throw new NoSuchEntityFoundException("No such genre exists");
        }

        return beanMappingService.mapTo(genres, GenreDTO.class);
    }

}
