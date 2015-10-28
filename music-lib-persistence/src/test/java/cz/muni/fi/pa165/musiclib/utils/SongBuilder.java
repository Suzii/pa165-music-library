package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.*;

/**
 *
 * @author Zuzana Dankovcikova
 */
public class SongBuilder {
    
    private String title;
    private String commentary;
    private int positionInAlbum;    
    private double bitrate;
    private Album album;
    private Musician musician;
    private Genre genre;
    
    public SongBuilder title(String title) {
        this.title = title;
        return this;
    }
    
    public SongBuilder commentary(String commentary) {
        this.title = title;
        return this;
    }
    
    public SongBuilder positionInAlbum(int positionInAlbum) {
        this.positionInAlbum = positionInAlbum;
        return this;
    }
    
    public SongBuilder bitrate(double bitrate) {
        this.bitrate = bitrate;
        return this;
    }
    
    public SongBuilder album(Album album) {
        this.album = album;
        return this;
    }
    
    public SongBuilder musician(Musician musician) {
        this.musician = musician;
        return this;
    }
    
    public SongBuilder genre(Genre genre) {
        this.genre = genre;
        return this;
    }
    
    public Song build() {
        Song s = new Song();
        
        s.setTitle(title);
        s.setCommentary(commentary);
        s.setPositionInAlbum(positionInAlbum);
        s.setBitrate(bitrate);
        s.setAlbum(album);
        s.setMusician(musician);
        s.setGenre(genre);
        
        clear();
        
        return s;
    }
    
    private void clear() {
        this.title = null;
        this.commentary = null;
        this.bitrate = 0;
        this.positionInAlbum = 0;
        this.musician = null;
        this.genre = null;
        this.album = null;
    }

}
