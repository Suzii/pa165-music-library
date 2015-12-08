package cz.muni.fi.pa165.musiclib.dto;

import cz.muni.fi.pa165.musiclib.enums.Sex;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Zuzana Dankovcikova
 * @version 16/11/2015
 */
public class MusicianDTO {
     
    private Long id;
    
    @NotNull
    @Size(min=3, max=50)
    private String artistName;
    
    private Sex sex;

    private Date dateOfBirth;
    
    private List<SongDTO> songs = new ArrayList<>();

    public MusicianDTO() {
        this.songs = new ArrayList<>();
    }
    
    public MusicianDTO(Long id) {
        this();
        this.id = id;
    }
    
    public List<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void addSong(SongDTO song) {
        this.songs.add(song);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + ((artistName == null) ? 0 : artistName.hashCode());
        hash = 23 * hash + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MusicianDTO)) {
            return false;
        }
        final MusicianDTO other = (MusicianDTO) obj;
        if ((artistName != null) ? !artistName.equals(other.getArtistName()): other.getArtistName()!= null){
            return false;
        }
        if ((dateOfBirth != null) ? !dateOfBirth.equals(other.getDateOfBirth()): other.getDateOfBirth()!= null){
            return false;
        }

        return true;
    }
    
    
}
