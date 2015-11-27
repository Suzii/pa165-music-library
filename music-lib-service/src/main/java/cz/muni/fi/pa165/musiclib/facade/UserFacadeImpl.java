package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.UserAuthenticationDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.entity.User;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/21/15
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
        User user = beanMappingService.mapTo(userDTO, User.class);
        userService.registerUser(user, encryptedPass);
        userDTO.setId(user.getId());
    }

    @Override
    public boolean authenticate(UserAuthenticationDTO u) {
        return userService.authenticate(
            userService.findUserById(u.getUserId()), u.getPassword());
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        return userService.isAdmin(beanMappingService.mapTo(userDTO, User.class));
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userService.findUserById(userId);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllusers(), UserDTO.class);
    }
}
