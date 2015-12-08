package cz.muni.fi.pa165.musiclib.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Zuzana Dankovcikova
 * @version 16/11/2015
 */
public class SongDTO {

    private Long id;

    @NotNull
    @Size(min=3, max=50)
    private String title;

    private String commentary;

    private int positionInAlbum;
    
    private double bitrate;
    
    private String youtubeLink;

    private AlbumDTO album;

    @NotNull
    private MusicianDTO musician;

    @NotNull
    private GenreDTO genre;

    public SongDTO() {
    }

    public SongDTO(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
        if(album != null) {
            album.addSong(this);
        }
    }

    public MusicianDTO getMusician() {
        return musician;
    }

    public void setMusician(MusicianDTO musician) {
        this.musician = musician;
        if (musician != null) {
            musician.addSong(this);
        }
    }

    public GenreDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreDTO genre) {
        this.genre = genre;
    }    
    
    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SongDTO)) return false;

        SongDTO song = (SongDTO) o;

        if (positionInAlbum != song.getPositionInAlbum()) return false;
        if (Double.compare(song.getBitrate(), bitrate) != 0) return false;
        if (title != null ? !title.equals(song.getTitle()) : song.getTitle() != null) return false;
        if (commentary != null ? !commentary.equals(song.getCommentary()) : song.getCommentary() != null) return false;
        if (album != null ? !album.equals(song.getAlbum()) : song.getAlbum() != null) return false;
        if (musician != null ? !musician.equals(song.getMusician()) : song.getMusician() != null) return false;
        return !(genre != null ? !genre.equals(song.getGenre()) : song.getGenre() != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        result = 31 * result + positionInAlbum;
        temp = Double.doubleToLongBits(bitrate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (musician != null ? musician.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }
}
