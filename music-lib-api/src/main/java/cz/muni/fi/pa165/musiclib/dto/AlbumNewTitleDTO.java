package cz.muni.fi.pa165.musiclib.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/21/15
 */
public class AlbumNewTitleDTO {

    @NotNull
    private Long albumId;

    @NotNull
    @Size(min = 1, max = 50)
    private String value;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof AlbumNewTitleDTO)) {
            return false;
        }

        AlbumNewTitleDTO that = (AlbumNewTitleDTO) o;

        if (albumId != null ? !albumId.equals(that.albumId) : that.albumId != null) {
            return false;
        }
        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = albumId != null ? albumId.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
