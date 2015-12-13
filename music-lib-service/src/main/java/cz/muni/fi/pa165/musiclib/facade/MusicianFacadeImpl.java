package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.exception.NoSuchEntityFoundException;
import cz.muni.fi.pa165.musiclib.service.BeanMappingService;
import cz.muni.fi.pa165.musiclib.service.MusicianService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author Milan
 */
@Service
@Transactional
public class MusicianFacadeImpl implements MusicianFacade {

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private MusicianService musicianService;

    @Override
    public Long createMusician(MusicianDTO musician) {
        if (musician == null) {
            throw new IllegalArgumentException("musician cannot be null");
        }

        musicianService.create(beanMappingService.mapTo(musician, Musician.class));
        return musician.getId();
    }

    /*   @Override
     public void updateMusician(MusicianDTO musician) {
     throw new UnsupportedOperationException("Not supported yet."); 
     }
     */
    @Override
    public void removeMusician(Long musicianId) {
        Musician musician = musicianService.findById(musicianId);
        if (musician == null) {
            throw new NoSuchEntityFoundException("No such musician exists");
        }

        musicianService.remove(musician);
    }

    @Override
    public List<MusicianDTO> getAllMusicians() {
        return beanMappingService.mapTo(musicianService.findAll(), MusicianDTO.class);
    }

    @Override
    public MusicianDTO getMusicianById(Long musicianId) {
        Musician musician = musicianService.findById(musicianId);
        if (musician == null) {
            throw new NoSuchEntityFoundException("No such musician exists");
        }

        return beanMappingService.mapTo(musician, MusicianDTO.class);
    }

    @Override
    public List<MusicianDTO> getMusicianByArtistName(String artistName) {
        List<Musician> musicians = musicianService.findByArtistName(artistName);
        if (musicians == null) {
            throw new NoSuchEntityFoundException("No such musician exists");
        }

        return beanMappingService.mapTo(musicians, MusicianDTO.class);
    }

}
