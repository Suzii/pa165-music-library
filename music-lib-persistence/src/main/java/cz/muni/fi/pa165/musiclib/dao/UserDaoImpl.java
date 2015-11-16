/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zdank
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(User user) throws ConstraintViolationException {
        em.persist(user);
    }

    @Override
    public User update(User user) throws ConstraintViolationException {
        return em.merge(user);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> q = em.createQuery("SELECT u FROM User u", User.class);
        return q.getResultList();
    }

    @Override
    public List<User> findByEmail(String email) {
        TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.email = :email",
                User.class).setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public void remove(User user) {
        if(user == null){
            throw new IllegalArgumentException("Attempted to delete null entity.");
        }
        em.remove(findById(user.getId()));
    }    
}
