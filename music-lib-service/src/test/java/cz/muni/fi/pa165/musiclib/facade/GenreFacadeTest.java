package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/27/15
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

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        genre01 = genreBuilder.title("Rock").id(1L).build();

        when(genreService.findById(1L)).thenReturn(genre01);
    }

    @Test
    public void createValidTest() {
        GenreDTO genreDTO = new GenreDTO(8L);
        genreDTO.setTitle("Folk");

        Long result = genreFacade.create(genreDTO);
        verify(genreService).create(any(Genre.class));
        assertNotNull(result);
        assertNotNull(genreDTO);
        assertEquals(genreDTO.getId(), result);
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
    public void deleteGenreTest() {
        genreFacade.deleteGenre(genre01.getId());
        verify(genreService).remove(genre01);
    }
}
