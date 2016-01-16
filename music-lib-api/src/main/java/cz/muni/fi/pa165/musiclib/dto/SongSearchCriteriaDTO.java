package cz.muni.fi.pa165.musiclib.dto;

/**
 * @author Zuzana Dankovcikova
 * @version 15/11/16
 */
public class SongSearchCriteriaDTO {
    
    private String title;
    private Long albumId;
    private Long musicianId;
    private Long genreId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(Long musicianId) {
        this.musicianId = musicianId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
