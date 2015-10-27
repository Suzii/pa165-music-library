package cz.muni.fi.pa165.musiclib.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author xstefank
 * @version 10/24/15
 */
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    private String commentary;

    private int positionInAlbum;
    
    private double bitrate;

    @ManyToOne
    private Album album;
//
//    @ManyToOne
//    @NotNull
//    private Musician musician;
//
//    @ManyToOne
//    @NotNull
//    private Genre genre;

    public Song() {
    }

    public Song(Long id) {
        this.id = id;
    }

    public Long getId() { return this.id; }

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

    public int getPositionInAlbum() {
        return positionInAlbum;
    }

    public void setPositionInAlbum(int positionInAlbum) {
        this.positionInAlbum = positionInAlbum;
    }

    public double getBitrate() {
        return bitrate;
    }

    public void setBitrate(double bitrate) {
        this.bitrate = bitrate;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

//    public Musician getMusician() {
//        return musician;
//    }
//
//    public void setMusician(Musician musician) {
//        this.musician = musician;
//    }
//
//    public Genre getGenre() {
//        return genre;
//    }
//
//    public void setGenre(Genre genre) {
//        this.genre = genre;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Song)) return false;

        Song song = (Song) o;

        if (positionInAlbum != song.positionInAlbum) return false;
        if (Double.compare(song.bitrate, bitrate) != 0) return false;
        if (!title.equals(song.title)) return false;
        return (!commentary.equals(song.commentary)) /*return false*/;
//        if (album != null ? !album.equals(song.album) : song.album != null) return false;
//        if (!musician.equals(song.musician)) return false;
//        return genre.equals(song.genre);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title.hashCode();
        result = 31 * result + commentary.hashCode();
        result = 31 * result + positionInAlbum;
        temp = Double.doubleToLongBits(bitrate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        result = 31 * result + (album != null ? album.hashCode() : 0);
//        result = 31 * result + musician.hashCode();
//        result = 31 * result + genre.hashCode();
        return result;
    }
}
