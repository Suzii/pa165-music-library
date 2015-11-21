package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.UserAuthenticationDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;

import java.util.Collection;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/21/15
 */
public class UserFacadeImpl implements UserFacade {

    @Override
    public void registerUser(UserDTO userDTO, String encryptedPass) {

    }

    @Override
    public boolean authenticate(UserAuthenticationDTO u) {
        return false;
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        return false;
    }

    @Override
    public UserDTO findUserById(Long userId) {
        return null;
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return null;
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return null;
    }
}
