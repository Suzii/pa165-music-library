package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Genre;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David Boron
 */
@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Genre genre) {
        em.persist(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public void remove(Genre genre) throws IllegalArgumentException {
        if(genre == null){
            throw new IllegalArgumentException("Attempted to delete null entity.");
        }
        em.remove(findById(genre.getId()));
    }

    @Override
    public Genre findById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> searchByTitle(String titleFragment) {
        if(titleFragment == null) {
            throw new IllegalArgumentException("titleFragment");
        }       
        TypedQuery<Genre> q = em.createQuery("SELECT g FROM Genre g WHERE UPPER(g.title) LIKE '%'||:titleFragment||'%'", Genre.class)
                .setParameter("titleFragment", titleFragment.toUpperCase());
        return q.getResultList();
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> q = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return q.getResultList();
    }

}
