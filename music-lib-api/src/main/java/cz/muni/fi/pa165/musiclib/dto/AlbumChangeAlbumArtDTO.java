package cz.muni.fi.pa165.musiclib.dto;

import java.util.Arrays;
import javax.validation.constraints.NotNull;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/21/15
 */
public class AlbumChangeAlbumArtDTO {

    @NotNull
    private Long albumId;
    private byte[] image;
    private String mimeType;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof AlbumNewTitleDTO)) return false;

        AlbumChangeAlbumArtDTO that = (AlbumChangeAlbumArtDTO) o;

        if (albumId != null ? !albumId.equals(that.albumId) : that.albumId != null) return false;
        if (!Arrays.equals(image, that.image)) return false;
        return !(mimeType != null ? !mimeType.equals(that.mimeType) : that.mimeType != null);

    }

    @Override
    public int hashCode() {
        int result = albumId != null ? albumId.hashCode() : 0;
        result = 31 * result + (image != null ? Arrays.hashCode(image) : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        return result;
    }
}
