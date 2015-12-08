package cz.muni.fi.pa165.musiclib.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Zuzana Dankovcikova
 * @version 16/11/2015
 */
public class GenreDTO {

    private Long id;

    @NotNull
    private String title;

    public GenreDTO() {
    }

    public GenreDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + ((title == null) ? 0 : title.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlbumDTO)) {
            return false;
        }

        final GenreDTO other = (GenreDTO) obj;

        if ((title != null) ? !title.equals(other.getTitle()) : other.getTitle() != null) {
            return false;
        }

        return true;
    }

}
