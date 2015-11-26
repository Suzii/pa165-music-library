package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import java.util.List;

/**
 * @author David
 */
public interface GenreFacade {

    Long create(GenreDTO genre);

    List<GenreDTO> getAllGenres();

    GenreDTO getGenreById(Long id);

    List<GenreDTO> getGenreByTitle(String title);

    void changeTitle(GenreDTO genre);

    void deleteGenre(Long genreId);
}
