
package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.User;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Zuzana Dankovcikova
 * @version 15/10/29
 */
public interface UserDao {
    /**
     * Persists new user into DB
     *
     * @param user entity to be persisted
     * @throws ConstraintViolationException if any constraint on
     * columns is violated
     */
    void create(User user) throws ConstraintViolationException;

    /**
     * Updates already persisted entity in the DB
     *
     * @param user persisted entity to be updated
     * @return the merged object attached to the EM
     * @throws ConstraintViolationException if any constraint on
     * columns is violated
     */
    User update(User user) throws ConstraintViolationException;

    /**
     * Returns the entity attached to the given id
     *
     * @param id id of the entity
     * @return the entity with given id
     */
    User findById(Long id);

    /**
     * Returns all Users in the DB
     *
     * @return all persisted User entities
     */
    List<User> findAll();

    /**
     * Returns all Users with given email
     *
     * @return all persisted User with given email entities
     */
    User findByEmail(String email);
    /**
     * Removes the User entity from persistence context
     *
     * @param user user to be removed     
     * @throws IllegalArgumentException if passed user is null or is not stored in DB 
     */
    void remove(User user);
}
