package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.enums.Sex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Zuzana Dankovcikova
 * @version 15/10/28
 */
public class MusicianBuilder {

        private Long id;
        private String artistName;
        private List<Song> songs = new ArrayList<>();
        private Sex sex;
        private Date dateOfBirth;
                
        public MusicianBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public MusicianBuilder artistName(String artistName) {
            this.artistName = artistName;
            return this;
        }
        
        public MusicianBuilder songs(List<Song> songs) {
            Collections.copy(this.songs, songs);
            return this;
        }

        public MusicianBuilder sex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public MusicianBuilder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Musician build() {
            Musician m = new Musician(id);
            m.setArtistName(artistName);
            m.setSex(sex);
            m.setDateOfBirth(dateOfBirth);
            m.setSongs(songs);
            clear();
            return m;
        }
        
        private void clear() {
            this.id = null;
            this.artistName = null;
            this.dateOfBirth = null;
            this.sex = null;
            this.songs = new ArrayList<>();
        }
    }