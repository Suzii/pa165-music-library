package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dao.UserDao;
import cz.muni.fi.pa165.musiclib.entity.User;
import cz.muni.fi.pa165.musiclib.exception.MusicLibDataAccessException;
import cz.muni.fi.pa165.musiclib.utils.SetIdHelper;
import cz.muni.fi.pa165.musiclib.utils.UserBuilder;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Martin Stefanko
 * @version 15/11/27
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    UserBuilder userBuilder = new UserBuilder();

    private User user01;
    private User user02;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        user01 = userBuilder.id(1L).email("lol@gmail.com").passwordHash("asd")
                .firstName("Janko").lastName("Hrasko").admin(true).build();

        user02 = userBuilder.id(2L).email("nolol@gmail.com").passwordHash("qwe")
                .firstName("Janko").lastName("Mrkvicka").admin(false).build();
    }

    @BeforeMethod
    public void initMocksBehaviour() {
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                User user = (User) argument;
                if (user.getId() != null) {
                    throw new IllegalArgumentException();
                }

                SetIdHelper.setId(user, 3L);
                return null;
            }
        }).when(userDao).create(any(User.class));

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                User user = (User) argument;
                if (user.getId() == null) {
                    throw new IllegalArgumentException();
                }

                return cloneUser(user);
            }
        }).when(userDao).update(any(User.class));

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object argument = invocation.getArguments()[0];
                if (argument == null) {
                    throw new IllegalArgumentException();
                }

                User user= (User) argument;
                if (user.getId() == null) {
                    throw new IllegalArgumentException();
                }

                when(userDao.findById(user.getId())).thenThrow(IllegalArgumentException.class);

                return null;
            }
        }).when(userDao).remove(any(User.class));

        when(userDao.findById(1L)).thenReturn(user01);
        when(userDao.findById(2L)).thenReturn(user02);
        when(userDao.findAll()).thenReturn(Arrays.asList(user01, user02));
        when(userDao.findByEmail(user01.getEmail())).thenReturn(user01);
        when(userDao.findByEmail(user02.getEmail())).thenReturn(user02);
    }

    @Test
    public void registerTest() {
        User newUser = userBuilder.id(null).email("404@gmail.com").build();

        userService.registerUser(newUser, "rty");

        assertNotNull(newUser);
        assertNotNull(newUser.getId());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registerNullUserTest() {
        userService.registerUser(null, "qwe");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registerNullPassTest() {
        User newUser = userBuilder.id(null).email("404@gmail.com").build();

        userService.registerUser(newUser, null);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void registerAlreadyRegisteredTest() {
        User newUser = userBuilder.id(1L).email("404@gmail.com").build();

        userService.registerUser(newUser, "qwe");
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void registerWithDuplicateEmailTest() {
        User newUser = userBuilder.id(3L).email("lol@gmail.com").build();

        userService.registerUser(newUser, "qwe");
    }

    @Test
    public void authenticateTest() {
        User newUser = userBuilder.id(null).email("404@gmail.com").build();

        userService.registerUser(newUser, "rty");

        assertNotNull(newUser);
        assertNotNull(newUser.getPasswordHash());

        boolean result = userService.authenticate(newUser, "rty");
        assertTrue(result);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void authenticateNullUserTest() {
        assertFalse(userService.authenticate(null, "qwe"));
    }

    @Test
    public void authenticateNullPassTest() {
        User newUser = userBuilder.id(null).email("404@gmail.com").build();

        assertFalse(userService.authenticate(newUser, null));
    }

    @Test
    public void authenticateInvalidPassTest() {
        User newUser = userBuilder.id(null).email("404@gmail.com").build();

        userService.registerUser(newUser, "rty");

        assertNotNull(newUser);
        assertNotNull(newUser.getPasswordHash());

        boolean result = userService.authenticate(newUser, "qwe");
        assertFalse(result);
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void authenticateInvalidUserTest() {
        User newUser = userBuilder.id(null).email("404@gmail.com")
                .passwordHash("sth").build();

        assertNotNull(newUser);
        assertNotNull(newUser.getPasswordHash());

        boolean result = userService.authenticate(newUser, "sth");
        assertFalse(result);
    }

    @Test
    public void isAdminTest() {
        assertTrue(user01.isAdmin());
        assertFalse(user02.isAdmin());
    }

    @Test
    public void findValidTest() {
        User user = userService.findUserById(1L);

        assertNotNull(user);
        assertDeepEquals(user, user01);
    }

    @Test(expectedExceptions = MusicLibDataAccessException.class)
    public void findNullTest() {
        when(userDao.findById(null)).thenThrow(IllegalArgumentException.class);

        userService.findUserById(null);
    }

    @Test
    public void findInvalidIdTest() {
        User user = userService.findUserById(123L);

        assertNull(user);
    }

    @Test
    public void findValidEmailTest() {
        User user = userService.findUserByEmail("lol@gmail.com");

        assertNotNull(user);
        assertDeepEquals(user, user01);
    }

    @Test
    public void findNullEmailTest() {
        User user = userService.findUserByEmail(null);

        assertNull(user);
    }

    @Test
    public void findInvalidEmailTest() {
        User user = userService.findUserByEmail("invalid@gmail.com");

        assertNull(user);
    }

    @Test
    public void getAllUsersTest() {
        List<User> result = userService.getAllusers();

        assertFalse(result.isEmpty());
        assertEquals(result.size(), 2);
        assertDeepEquals(result.get(0), user01);
        assertDeepEquals(result.get(1), user02);
    }

    @Test
    public void findAllNoGenresTest() {
        when(userDao.findAll()).thenReturn(new ArrayList<User>());

        List<User> users = userService.getAllusers();
        assertTrue(users.isEmpty());
    }

    private void assertDeepEquals(User actual, User expected) {
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getPasswordHash(), expected.getPasswordHash());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.isAdmin(), expected.isAdmin());
    }

    private User cloneUser(User user, Long id) {
        return userBuilder.id(id)
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .admin(user.isAdmin())
                .build();
    }

    private User cloneUser(User user) {
        return cloneUser(user, user.getId());
    }

}
