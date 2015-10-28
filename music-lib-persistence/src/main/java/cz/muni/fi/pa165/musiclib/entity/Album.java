package cz.muni.fi.pa165.musiclib.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author Zuzana Dankovcikova
 * @version 10/25/15
 */
@Entity
public class Album {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String title;
    
    private String commentary;
    
    @Temporal(TemporalType.DATE)
    private Date dateOfRelease;
    
    @Lob
    private byte[] albumArt;
    
    private String albumArtMimeType;
    
    @OneToMany(mappedBy = "album")
        private List<Song> songs;

    public Album() {
    songs = new ArrayList<>();
    }

    public Long getId() {
        return id;
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

    public Date getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(Date dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public byte[] getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(byte[] albumArt) {
        this.albumArt = albumArt;
    }

    public String getAlbumArtMimeType() {
        return albumArtMimeType;
    }

    public void setAlbumArtMimeType(String albumArtMimeType) {
        this.albumArtMimeType = albumArtMimeType;
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public void setSongs(List<Song> songs) {
        Collections.copy(this.songs, songs);
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ((title == null) ? 0 : title.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(this == obj){
            return true;
        }
        if (!(obj instanceof Album)) {
            return false;
        }
        final Album other = (Album) obj;
        if (! title.equals(other.title)) {
            return false;
        }
        return true;
    }
    
    
    
}
