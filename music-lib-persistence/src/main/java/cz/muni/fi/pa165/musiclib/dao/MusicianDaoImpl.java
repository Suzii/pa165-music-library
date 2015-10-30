package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Musician;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Milan
 */
@Repository
public class MusicianDaoImpl implements MusicianDao {

    @PersistenceContext
    private  EntityManager em;
    
    
    @Override
    public void create(Musician musician) {
        em.persist(musician);
    }

    @Override
    public Musician update(Musician musician) {
       return em.merge(musician);
    }

    @Override
    public void remove(Musician musician) throws IllegalArgumentException {
        em.remove(musician);
    }

    @Override
    public Musician findById(Long id) {
        return em.find(Musician.class, id);
    }

    @Override   
    public List<Musician> findByArtistName(String artistName) {
        return em.createQuery("SELECT m FROM Musician m WHERE m.artistName = :artistName", Musician.class)
                .setParameter("artistName", artistName).getResultList();
    }

    @Override
    public List<Musician> findAll() {
        return em.createQuery("SELECT m FROM Musician m", Musician.class).getResultList();
    }
    
}
