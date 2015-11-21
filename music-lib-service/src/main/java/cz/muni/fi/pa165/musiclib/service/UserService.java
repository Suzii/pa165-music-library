package cz.muni.fi.pa165.musiclib.service;

import cz.muni.fi.pa165.musiclib.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An interface that defines a service access to the {@link User} entity.
 *
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/21/15
 */
@Service
public interface UserService {

    /**
     * Registers the user with the given password
     *
     * @param user new user
     * @param encryptedPass user's encrypted password
     */
    void registerUser(User user, String encryptedPass);

    /**
     * Tries to autheticate the user with the given password
     *
     * @param user user to authenticate
     * @param pass user's password
     * @return true if the user was successfully authenticated, false otherwise
     */
    boolean authenticate(User user, String pass);

    /**
     * Checks whether thew user is admin
     *
     * @param user user to be checked
     * @return true if user is admin, flase otherwise
     */
    boolean isAdmin(User user);

    /**
     * Returns the user with the given id
     *
     * @param id id of the user
     * @return found user if the id is valid, null otherwise
     */
    User findUserById(Long id);

    /**
     * Returns the user with the given email
     *
     * @param email email of the user
     * @return found user if the the email is valid, null otherwise
     */
    List<User> findUserByEmail(String email);

    /**
     * Returns all registered users
     *
     * @return all users
     */
    List<User> getAllusers();
}
