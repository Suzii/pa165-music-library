package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.SongService;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author zdank
 * @version 26/11/2015
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AlbumFacadeTest extends AbstractTestNGSpringContextTests {
    
    @Mock
    private AlbumService albumService;

    @Mock
    private SongService songService;

    @Inject
    private BeanMappingService beanMappingService;
    
    @InjectMocks
    private AlbumFacade albumFacade = new AlbumFacadeImpl();
    
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testClassInitializationTest() {
        assertNotNull(albumService);
        assertNotNull(songService);
        assertNotNull(albumFacade);
    }
    
     @BeforeMethod
    public void initMocksBehaviour() {
        // findById
        when(albumService.findById(1l)).thenReturn(null);
        when(albumService.findById(2l)).thenReturn(null);
        when(albumService.findById(0l)).thenReturn(null);

    }

}
