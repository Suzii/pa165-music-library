package cz.muni.fi.pa165.musiclib.entity;

import cz.muni.fi.pa165.musiclib.enums.Sex;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Milan Seman
 * @version 15/10/24
 */
@Entity
public class Musician {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String artistName;
    
    @NotNull
    @Enumerated
    private Sex sex;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @OneToMany(mappedBy = "musician")
    private List<Song> songs = new ArrayList<>();

    public Musician() {
        this.songs = new ArrayList<>();
    }
    
    public Musician(Long id) {
        this();
        this.id = id;
    }
    
    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }
    
    private void setId(Long id) {
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

    public void addSong(Song song) {
        this.songs.add(song);
    }    
    
    public void removeSong(Song song) {
        this.songs.remove(song);
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
        if (!(obj instanceof Musician)) {
            return false;
        }
        final Musician other = (Musician) obj;
        if ((artistName != null) ? !artistName.equals(other.getArtistName()): other.getArtistName()!= null){
            return false;
        }
        if ((dateOfBirth != null) ? !dateOfBirth.equals(other.getDateOfBirth()): other.getDateOfBirth()!= null){
            return false;
        }

        return true;
    }
    
    
}
