package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Martin Stefanko (mstefank@redhat.com)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GenreServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private GenreService genreService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testingTest() {
        System.out.println("asdsada ds adqweqwewqeqwe");

        assertNotNull(genreService);
    }
}
