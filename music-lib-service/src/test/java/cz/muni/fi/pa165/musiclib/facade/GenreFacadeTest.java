package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * @author Martin Stefanko
 * @version 15/11/27
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GenreFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private GenreService genreService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private GenreFacade genreFacade = new GenreFacadeImpl();


    private GenreBuilder genreBuilder = new GenreBuilder();
    private Genre genre01;
    private Genre genre02;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        genre01 = genreBuilder.title("Rock").id(1L).build();
        genre02 = genreBuilder.title("Pop").id(2L).build();

        when(genreService.findById(1L)).thenReturn(genre01);
        when(genreService.searchByTitle(genre01.getTitle())).thenReturn(Collections.singletonList(genre01));
        when(genreService.findById(2L)).thenReturn(genre02);
        when(genreService.findAll()).thenReturn(Arrays.asList(genre01, genre02));
    }

    @Test
    public void createValidTest() {
        GenreDTO genreDTO = new GenreDTO(8L);
        genreDTO.setTitle("Folk");

        Long result = genreFacade.create(genreDTO);
        verify(genreService).create(any(Genre.class));
    }

    @Test
    public void changeTitleValidTest() {
        GenreDTO genreDTO = new GenreDTO(genre01.getId());
        genreDTO.setTitle("Folk");

        genreFacade.changeTitle(genreDTO);
        verify(genreService).changeTitle(any(Genre.class), anyString());
        assertNotNull(genreDTO);
        assertEquals(genreDTO.getTitle(), "Folk");
    }

    @Test
    public void deleteGenreValidTest() {
        genreFacade.deleteGenre(genre01.getId());
        verify(genreService).remove(genre01);
    }

    @Test
    public void getAllGenresValidTest() {
        List<GenreDTO> result = genreFacade.getAllGenres();
        verify(genreService).findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getId(), genre01.getId());
    }

    @Test
    public void findByIdValidTest() {
        GenreDTO result = genreFacade.getGenreById(genre01.getId());
        assertNotNull(result);
        assertEquals(genre01.getId(), result.getId());
    }

    @Test
    public void findByTitleValidTest() {
        List<GenreDTO> result = genreFacade.searchGenreByTitle(genre01.getTitle());
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(genre01.getId(), result.get(0).getId());
    }
}
