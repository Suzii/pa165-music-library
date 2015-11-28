package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.entity.User;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.musiclib.service.UserServiceImpl;
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
        when(userService.findUserById(1L)).thenReturn(user1);
        when(userService.findUserByEmail("456@asd.ds")).thenReturn(user2);
        when(userService.getAllusers()).thenReturn(Arrays.asList(user1, user2));
    }

    @Test(enabled = false)
    public void testClassInitializationTest() {
        assertNotNull(userService);
    }

    @Test
    public void registerUserTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Hanz");

        userFacade.registerUser(userDTO, user1.getPasswordHash());
        verify(userService).registerUser(any(User.class), any(String.class));
    }

//    @Test
//    public void authenticateTest() {
//        
//    }

    @Test
    public void findUserByIdTest() {
        userFacade.findUserById(1L);
        verify(userService, atLeastOnce()).findUserById(1L);
    }

    @Test
    public void findUserByEmailTest() {
        userFacade.findUserByEmail("456@asd.ds");
        verify(userService, atLeastOnce()).findUserByEmail("456@asd.ds");
    }

    @Test
    public void isAdminTest() {
//        Boolean result = user1.isAdmin();
//        assertEquals(result, user1.isAdmin());
    }

    @Test
    public void getAllUsersTest() {
        userFacade.getAllUsers();
        verify(userService, atLeastOnce()).getAllusers();
    }

}
