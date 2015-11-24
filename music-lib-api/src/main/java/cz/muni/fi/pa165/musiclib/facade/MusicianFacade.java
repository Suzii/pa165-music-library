package cz.muni.fi.pa165.musiclib.facade;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import java.util.List;

/**
 * @author xseman
 */
public interface MusicianFacade {
Long createMusician(MusicianDTO m);

void addSong(Long musicianId, Long songId);

void removeSong(Long musicianId, Long songId);

void changeArtistName (String artistName);

void deleteMusician (Long musicianId);

List<MusicianDTO> getAllMusicians ();

MusicianDTO getMusicianById(Long musicianId);

List <MusicianDTO> getMusicianByArtistName (String artistName);

}
