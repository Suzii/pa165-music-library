package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Musician;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Milan Seman
 * @version 15/10/30
 */
@Repository
public class MusicianDaoImpl implements MusicianDao {

    @PersistenceContext
    private  EntityManager em;
    
    
    @Override
    public void create(Musician musician) {
        if(musician == null){
            throw new IllegalArgumentException("Attempted to delete null entity.");
        }
        em.persist(musician);
    }

    @Override
    public Musician update(Musician musician) {
       return em.merge(musician);
    }

    @Override
    public void remove(Musician musician) throws IllegalArgumentException {
        if(musician == null){
            throw new IllegalArgumentException("Attempted to delete null entity.");
        }
        em.remove(findById(musician.getId()));
    }

    @Override
    public Musician findById(Long id) {
        return em.find(Musician.class, id);
    }

    @Override   
    public List<Musician> searchByArtistName(String artistNameFragment) {
        if(artistNameFragment == null) {
            throw new IllegalArgumentException("artistNameFragment cannot be null.");
        }
        return em.createQuery("SELECT m FROM Musician m WHERE UPPER(m.artistName) LIKE '%'||:artistNameFragment||'%'", Musician.class)
                .setParameter("artistNameFragment", artistNameFragment.toUpperCase()).getResultList();
    }

    @Override
    public List<Musician> findAll() {
        return em.createQuery("SELECT m FROM Musician m", Musician.class).getResultList();
    }
    
}
