package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/27/15
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GenreFacadeTest extends AbstractTestNGSpringContextTests {

//    @Mock
//    private GenreService genreService;
//
//    @Mock
//    private BeanMappingService beanMappingService;
//
//    @InjectMocks
//    private GenreFacade genreFacade = new GenreFacadeImpl();
//
//    @BeforeClass
//    public void setup() throws ServiceException {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testingTest() {
//        assertNotNull(beanMappingService);
//        assertNotNull(genreFacade);
//    }
//
//    @Test
//    public void createValidTest() {
//        GenreDTO genreDTO = new GenreDTO(1L);
//        genreDTO.setTitle("Folk");
//
//        Long result = genreFacade.create(genreDTO);
//        assertNotNull(result);
//        assertNotNull(genreDTO);
//        assertEquals(genreDTO.getId(), result);
//
//    }
}
