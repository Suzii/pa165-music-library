package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.UserAuthenticationDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.entity.User;
import cz.muni.fi.pa165.musiclib.exception.NoSuchEntityFoundException;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Martin Stefanko
 * @version 15/11/21
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void registerUser(UserDTO userDTO, String encryptedPass) {
        if (userDTO == null) {
            throw new IllegalArgumentException("userDTO cannot be null");
        }

        User user = beanMappingService.mapTo(userDTO, User.class);
        userService.registerUser(user, encryptedPass);
        userDTO.setId(user.getId());
    }

    @Override
    public boolean authenticate(UserAuthenticationDTO userAuthenticateDTO) {
        if (userAuthenticateDTO == null) {
            throw new IllegalArgumentException("userAuthenticateDTO cannot be null");
        }

        User user = userService.findUserById(userAuthenticateDTO.getUserId());
        if (user == null) {
            throw new NoSuchEntityFoundException("No such user exists");
        }

        return userService.authenticate(user, userAuthenticateDTO.getPassword());
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("userDTO cannot be null");
        }

        return userService.isAdmin(beanMappingService.mapTo(userDTO, User.class));
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new NoSuchEntityFoundException("No such user exists");
        }

        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new NoSuchEntityFoundException("No such user exists");
        }

        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllusers(), UserDTO.class);
    }
}
