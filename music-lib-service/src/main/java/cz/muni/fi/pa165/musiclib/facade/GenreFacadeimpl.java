package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author David
 */
@Service
@Transactional
public class GenreFacadeimpl implements GenreFacade {

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private GenreService genreService;

    @Override
    public Long create(GenreDTO genre) {
        genreService.create(beanMappingService.mapTo(genre, Genre.class));
        return genre.getId();
    }

    @Override
    public void changeTitle(GenreDTO newGenreTitle) {
        genreService.changeTitle(genreService.findById(newGenreTitle.getId()),
                newGenreTitle.getTitle());
    }

    @Override
    public void deleteGenre(Long genreId) {
        genreService.remove(genreService.findById(genreId));
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return beanMappingService.mapTo(genreService.findAll(), GenreDTO.class);
    }

    @Override
    public GenreDTO getGenreById(Long id) {
        return beanMappingService.mapTo(genreService.findById(id), GenreDTO.class);
    }

    @Override
    public List<GenreDTO> getGenreByTitle(String title) {
        return beanMappingService.mapTo(genreService.findByTitle(title), GenreDTO.class);
    }

}
