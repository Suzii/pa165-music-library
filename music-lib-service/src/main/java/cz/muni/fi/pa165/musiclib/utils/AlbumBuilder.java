package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author xstefank
 * @version 10/28/15
 */
public class AlbumBuilder {

    private Long id;
    
    private String title;

    private String commentary;

    private Date dateOfRelease;

    private byte[] albumArt;

    private String albumArtMimeType;

    private List<Song> songs = new ArrayList<>();

    public AlbumBuilder() {
    }
    
    public AlbumBuilder id(Long id){
        this.id = id;
        return this;
    }

    public AlbumBuilder title(String title) {
        this.title = title;
        return this;
    }

    public AlbumBuilder commentary(String commentary) {
        this.commentary = commentary;
        return this;
    }

    public AlbumBuilder dateOfRelease(Date dateOfRelease) {
        this.dateOfRelease = (dateOfRelease != null) ? new Date(dateOfRelease.getTime()) : null;
        return this;
    }

    public AlbumBuilder albumArt(byte[] albumArt) {
        this.albumArt = (albumArt != null) ? Arrays.copyOf(albumArt, albumArt.length) : null;
        return this;
    }

    public AlbumBuilder albumArtMimeType(String albumArtMimeType) {
        this.albumArtMimeType = albumArtMimeType;
        return this;
    }

    public AlbumBuilder songs(List<Song> songs) {
        this.songs = (songs != null) ? songs : new ArrayList<Song>();
        return this;
    }

    public Album build() {
        Album album = new Album(id);

        album.setTitle(this.title);
        album.setCommentary(this.commentary);
        album.setDateOfRelease(this.dateOfRelease);
        album.setAlbumArt(this.albumArt);
        album.setAlbumArtMimeType(this.albumArtMimeType);
        album.setSongs(this.songs);

        return album;
    }
}
