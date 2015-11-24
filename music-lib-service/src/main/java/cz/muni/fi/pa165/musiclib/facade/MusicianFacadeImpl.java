package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;

import java.util.List;

/**
 * @author Milan
 */
public class MusicianFacadeImpl implements MusicianFacade {

    @Override
    public Long createMusician(MusicianDTO m) {
        return null;
    }

    @Override
    public void addSong(Long musicianId, Long songId) {

    }

    @Override
    public void removeSong(Long musicianId, Long songId) {

    }

    @Override
    public void changeArtistName(String artistName) {

    }

    @Override
    public void deleteMusician(Long musicianId) {

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
