    package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Album;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Zuzana Dankovcikova
 */
@Repository
public class AlbumDaoImpl implements AlbumDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Album album) {
        em.persist(album);
    }

    @Override
    public Album update(Album album) {
        return em.merge(album);
    }

    @Override
    public void remove(Album album) throws IllegalArgumentException {
        em.remove(album);
    }

    @Override
    public Album findById(Long id) {
        return em.find(Album.class, id);
    }

    @Override
    public List<Album> findByTitle(String title) {
        return em.createQuery("SELECT a FROM Album a WHERE a.title = :title", Album.class)
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public List<Album> findAll() {
        return em.createQuery("SELECT a FROM Album a", Album.class)
                .getResultList();
    }
    
}
