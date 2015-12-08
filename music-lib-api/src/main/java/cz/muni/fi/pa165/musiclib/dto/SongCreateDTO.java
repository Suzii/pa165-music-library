/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.musiclib.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for creation of songs. Does not contain position on album nor songId,
 * as those will be set automatically by service. 
 * Album is not covered as well as Song creation process is always bound to specific album.
 * 
 * @author Zuzana Dankovcikova
 * @version 26/11/2015
 */
public class SongCreateDTO {

    @NotNull
    @Size(min=3, max=50)
    private String title;

    private String commentary;

    private double bitrate;

    @NotNull
    private Long musicianId;

    @NotNull
    private Long genreId;

    public SongCreateDTO() {
    }

    public String getTitle() {
        return title;
    }

        public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public double getBitrate() {
        return bitrate;
    }

    public void setBitrate(double bitrate) {
        this.bitrate = bitrate;
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

    public void setGenre(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SongCreateDTO)) return false;

        SongCreateDTO song = (SongCreateDTO) o;

        if (Double.compare(song.getBitrate(), bitrate) != 0) return false;
        if (title != null ? !title.equals(song.getTitle()) : song.getTitle() != null) return false;
        if (commentary != null ? !commentary.equals(song.getCommentary()) : song.getCommentary() != null) return false;
        if (musicianId != null ? !musicianId.equals(song.getMusicianId()) : song.getMusicianId() != null) return false;
        return !(genreId != null ? !genreId.equals(song.getGenreId()) : song.getGenreId() != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        temp = Double.doubleToLongBits(bitrate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (musicianId != null ? musicianId.hashCode() : 0);
        result = 31 * result + (genreId != null ? genreId.hashCode() : 0);
        return result;
    }
}
