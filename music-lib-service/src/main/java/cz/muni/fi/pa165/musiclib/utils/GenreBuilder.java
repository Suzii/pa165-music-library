package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.Genre;

/**
 * @author Zuzana Dankovcikova
 * @version 15/10/28
 */
public class GenreBuilder {

    private Long id;
    private String title;

    public GenreBuilder() {
    }
    
    public GenreBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public GenreBuilder title(String title) {
        this.title = title;
        return this;
    }

    public Genre build() {
        Genre s = new Genre(id);
        s.setTitle(title);
        clear();
        return s;
    }

    private void clear() {
        this.id = null;
        this.title = null;
    }
}
