package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.entity.Musician;
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
        musicianService.create(beanMappingService.mapTo(musician, Musician.class));
        return musician.getId();
    }

    @Override
    public void updateMusician(MusicianDTO musician) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void removeMusician(Long musicianId) {
        
    }

    @Override
    public List<MusicianDTO> getAllMusicians() {
        return null;
    }

    @Override
    public MusicianDTO getMusicianById(Long musicianId) {
        return null;
    }

    @Override
    public List<MusicianDTO> getMusicianByArtistName(String artistName) {
        return null;
    }

}
