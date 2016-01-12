package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.musiclib.service.MusicianService;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author David Boron
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MusicianFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MusicianService musicianService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private MusicianFacade musicianFacade = new MusicianFacadeImpl();

    private MusicianBuilder musicianBuilder = new MusicianBuilder();
    private Musician musician1;
    private Musician musician2;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        musician1 = musicianBuilder.id(1l).artistName("musician name").sex(Sex.MALE).build();
        musician2 = musicianBuilder.id(1l).artistName("musician name2").sex(Sex.FEMALE).build();
        
        when(musicianService.findById(1l)).thenReturn(musician1);
        when(musicianService.findById(2l)).thenReturn(musician2);
        when(musicianService.searchByArtistName(musician1.getArtistName())).thenReturn(Collections.singletonList(musician1));
        when(musicianService.findAll()).thenReturn(Arrays.asList(musician1, musician2));
    }

    @Test
    public void createTest() {
        MusicianDTO musicianDTO = new MusicianDTO(3l);
        musicianDTO.setArtistName("musician name3");

        Long createdId = musicianFacade.createMusician(musicianDTO);
        verify(musicianService).create(any(Musician.class));
        
    }

    @Test
    public void removeMusicianTest() {
        musicianFacade.removeMusician(musician1.getId());
        verify(musicianService).remove(musician1);
    }

    @Test
    public void getAllMusiciansValidTest() {
        List<MusicianDTO> musicians = musicianFacade.getAllMusicians();
        verify(musicianService).findAll();
        assertNotNull(musicians);
        assertEquals(musicians.get(0).getId(), musician1.getId());
        assertEquals(musicians.get(1).getId(), musician2.getId());
    }

    @Test
    public void getByIdTest() {
        MusicianDTO musicianId = musicianFacade.getMusicianById(musician1.getId());
        assertNotNull(musicianId);
        assertEquals(musician1.getId(), musicianId.getId());
    }

    @Test
    public void getByNameTest() {
        List<MusicianDTO> musician = musicianFacade.searchMusicianByArtistName(musician1.getArtistName());
        assertNotNull(musician);
        assertEquals(musician1.getId(), musician.get(0).getId());
    }
}