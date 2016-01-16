package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Stefanko
 * @version 15/10/28
 */
public class AlbumBuilder {

    private String title;

    private String commentary;

    private Date dateOfRelease;

    private byte[] albumArt;

    private String albumArtMimeType;

    private List<Song> songs = new ArrayList<>();

    public AlbumBuilder() {
    }

    public AlbumBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public AlbumBuilder setCommentary(String commentary) {
        this.commentary = commentary;
        return this;
    }

    public AlbumBuilder setDateOfRelease(Date dateOfRelease) {
        this.dateOfRelease = new Date(dateOfRelease.getTime());
        return this;
    }

    public AlbumBuilder setAlbumArt(byte[] albumArt) {
        this.albumArt = Arrays.copyOf(albumArt, albumArt.length);
        return this;
    }

    public AlbumBuilder setAlbumArtMimeType(String albumArtMimeType) {
        this.albumArtMimeType = albumArtMimeType;
        return this;
    }

    public AlbumBuilder setSongs(List<Song> songs) {
        this.songs = new ArrayList<>(songs);
        return this;
    }

    public Album build() {
        Album album = new Album();

        album.setTitle(this.title);
        album.setCommentary(this.commentary);
        album.setDateOfRelease(this.dateOfRelease);
        album.setAlbumArt(this.albumArt);
        album.setAlbumArtMimeType(this.albumArtMimeType);
        album.setSongs(this.songs);

        return album;
    }
}
