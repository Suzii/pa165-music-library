/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.musiclib.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 *
 * @author David
 */
public class GenreNewTitleDTO {
    

    private Long id;
        
    @NotNull
    private String title;

    public Long getGenreId() {
        return id;
    }

    public void setGenreId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setValue(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SongAddYoutubeLinkDTO)) {
            return false;
        }
        
        GenreNewTitleDTO other = (GenreNewTitleDTO) obj;
        
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }

        if (id != null ? !id.equals(other.id) : other.id != null) return false;
        return !(title != null ? !title.equals(other.title) : other.title != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 9;
        result = 132 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}