package cz.muni.fi.pa165.musiclib.dao;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author xstefank
 * @version 10/24/15
 */
@Repository
public class SongDaoImpl implements SongDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Song song) {
        em.persist(song);
    }

    @Override
    public Song update(Song song) {
        return em.merge(song);
    }

    @Override
    public Song findById(Long id) {
        return em.find(Song.class, id);
    }

    @Override
    public List<Song> findAll() {
        TypedQuery<Song> q = em.createQuery("SELECT s FROM Song s", Song.class);
        return q.getResultList();
    }

    @Override
    public List<Song> findByAlbum(Album album) {
        TypedQuery<Song> q = em.createQuery("SELECT s FROM Song s WHERE s.album = :albumId",
                Song.class).setParameter("albumId", album);
        return q.getResultList();
    }

    @Override
    public List<Song> findByMusician(Musician musician) {
        TypedQuery<Song> q = em.createQuery("SELECT s FROM Song s WHERE s.musician = :musicianId",
                Song.class).setParameter("musicianId", musician);
        return q.getResultList();
    }

    @Override
    public List<Song> findByGenre(Genre genre) {
        TypedQuery<Song> q = em.createQuery("SELECT s FROM Song s WHERE s.genre = :genreId",
                Song.class).setParameter("genreId", genre);
        return q.getResultList();
    }

    @Override
    public void remove(Song song) {
        em.remove(song);
    }
}
