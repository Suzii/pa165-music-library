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
        if(album == null){
            throw new IllegalArgumentException("Attempted to delete null entity.");
        }
        em.remove(findById(album.getId()));
    }

    @Override
    public Album findById(Long id) {
        return em.find(Album.class, id);
    }

    @Override
    public List<Album> searchByTitle(String titleFragment) {
        if(titleFragment == null) {
            throw new IllegalArgumentException("titleFragment cannot be null.");
        }       
        return em.createQuery("SELECT a FROM Album a WHERE UPPER(a.title) LIKE '%'||:titleFragment||'%'", Album.class)
                .setParameter("titleFragment", titleFragment.toUpperCase())
                .getResultList();
    }

    @Override
    public List<Album> findAll() {
        return em.createQuery("SELECT a FROM Album a", Album.class)
                .getResultList();
    }
}
