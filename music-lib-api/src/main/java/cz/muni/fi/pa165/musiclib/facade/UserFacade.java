package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.UserAuthenticationDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;

import java.util.Collection;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/21/15
 */
public interface UserFacade {

    /**
     * Register new user with password to the system
     *
     * @param userDTO new user
     * @param encryptedPass user's encrypted password
     */
    void registerUser(UserDTO userDTO, String encryptedPass);

    /**
     * Authenticates the user
     *
     * @param u user user to authenticate
     * @return true if hashed password mathes the records, false otherwise
     */
    boolean authenticate(UserAuthenticationDTO u);

    /**
     * Checks if the user is admin
     *
     * @param userDTO user to be checked
     * @return true if user is admin, false otherwise
     */
    boolean isAdmin(UserDTO userDTO);

    /**
     * Returns the user by the given id
     *
     * @param userId given id of the user
     * @return found user if id is valid, null otherwise
     */
    UserDTO findUserById(Long userId);

    /**
     * Returns the user by the given email
     *
     * @param email given email of the user
     * @return found user if email is valid, null otherwise
     */
    UserDTO findUserByEmail(String email);

    /**
     * Returns all registered users
     *
     * @return all users
     */
    Collection<UserDTO> getAllUsers();
}
