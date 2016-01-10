package cz.muni.fi.pa165.utils;

import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import java.util.Comparator;

/**
 *
 * @author Zuzana Dankovcikova
 */
public class SongPositionInAlbumComparator implements Comparator<SongDTO> {

    @Override
    public int compare(SongDTO song1, SongDTO song2) {
        return song1.getPositionInAlbum() - song2.getPositionInAlbum();
    }

}
