package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.SongAddYoutubeLinkDTO;
import cz.muni.fi.pa165.musiclib.dto.SongCreateDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.entity.User;
import cz.muni.fi.pa165.musiclib.service.AlbumServiceImpl;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.musiclib.service.GenreServiceImpl;
import cz.muni.fi.pa165.musiclib.service.MusicianServiceImpl;
import cz.muni.fi.pa165.musiclib.service.SongServiceImpl;
import cz.muni.fi.pa165.musiclib.service.UserServiceImpl;
import cz.muni.fi.pa165.musiclib.utils.AlbumBuilder;
import cz.muni.fi.pa165.musiclib.utils.GenreBuilder;
import cz.muni.fi.pa165.musiclib.utils.MusicianBuilder;
import cz.muni.fi.pa165.musiclib.utils.SongBuilder;
import cz.muni.fi.pa165.musiclib.utils.UserBuilder;
import java.util.Arrays;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author milan
 * @version 26/11/2015
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserServiceImpl userService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final UserFacadeImpl userFacade = new UserFacadeImpl();

    UserBuilder userBuilder = new UserBuilder();

    private User user1;
    private User user2;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        user1 = userBuilder.id(1L).email("123@okj.sa").firstName("Ferko")
                .lastName("Fraco").passwordHash("dsd5421rr4e1").build();
        user2 = userBuilder.id(2L).email("456@asd.ds").firstName("Jozko")
                .lastName("Pucik").passwordHash("ytu4578er54fd").build();
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        //when(userService.registerUser(user1, null))
    }

    @Test(enabled = false)
    public void testClassInitializationTest() {
        assertNotNull(userService);
    }
    /*
    @Test
    public void registerUserTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Hanz");
        
        userFacade.registerUser(userDTO, user1.getPasswordHash());
        verify(userService).registerUser(any(User.class));
        
    }

    @Override
    public boolean authenticate(UserAuthenticationDTO u) {
        
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        
    }

    @Override
    public UserDTO findUserById(Long userId) {
        
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        
    }

    @Override
    
    }*/
}

