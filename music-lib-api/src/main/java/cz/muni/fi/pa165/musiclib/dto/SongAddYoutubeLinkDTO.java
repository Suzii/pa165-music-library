/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.musiclib.dto;

import java.util.Objects;

/**
 *
 * @author zdank
 */
public class SongAddYoutubeLinkDTO {
    
    private Long songId;
    
    private String youtubeLink;

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + ((songId != null) ? songId.hashCode() : 0);
        hash = 53 * hash + ((youtubeLink != null) ? youtubeLink.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if (!(obj instanceof SongAddYoutubeLinkDTO)) {
            return false;
        }
        final SongAddYoutubeLinkDTO other = (SongAddYoutubeLinkDTO) obj;
        if (!Objects.equals(this.songId, other.songId)) {
            return false;
        }
        
        if (songId != null ? !songId.equals(other.getSongId()) : other.getSongId() != null) return false;
        if (youtubeLink != null ? !youtubeLink.equals(other.getYoutubeLink()) : other.getYoutubeLink() != null) return false;
        return true;
    }
    
}
