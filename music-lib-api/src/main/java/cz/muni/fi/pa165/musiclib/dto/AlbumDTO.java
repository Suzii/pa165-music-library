package cz.muni.fi.pa165.musiclib.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author zdankovc
 * @version 16/11/2015
 */
public class AlbumDTO {
    
    private Long id;
    
    private String title;
    
    private String commentary;
    
    private Date dateOfRelease;
    
    private byte[] albumArt;
    
    private String albumArtMimeType;
    
    private List<SongDTO> songs = new ArrayList<>();

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

    public List<SongDTO> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public void setSongs(List<SongDTO> songs) {
        Collections.copy(this.songs, songs);
    }

    public void addSong(SongDTO song) {
        this.songs.add(song);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ((title == null) ? 0 : title.hashCode());
        hash = 53 * hash + ((commentary == null) ? 0 : commentary.hashCode());
        hash = 53 * hash + ((dateOfRelease == null) ? 0 : dateOfRelease.hashCode());
        hash = 53 * hash + ((albumArtMimeType == null) ? 0 : albumArtMimeType.hashCode());
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
        if (!(obj instanceof AlbumDTO)) {
            return false;
        }
        final AlbumDTO other = (AlbumDTO) obj;
        if((title != null) ? !title.equals(other.getTitle()): other.getTitle()!= null){
            return false;
        }
        
        if((commentary != null) ? !commentary.equals(other.getCommentary()): other.getCommentary() != null){
            return false;
        }
        
        if((dateOfRelease != null) ? !dateOfRelease.equals(other.getDateOfRelease()): other.getDateOfRelease()!= null){
            return false;
        }
        
        if((albumArt != null) ? !albumArt.equals(other.getAlbumArt()): other.getAlbumArt()!= null){
            return false;
        }
        
        if((albumArtMimeType != null) ? !albumArtMimeType.equals(other.getAlbumArtMimeType()): other.getAlbumArtMimeType()!= null){
            return false;
        }
        return true;
    }
}