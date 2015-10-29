package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.Genre;

/**
 *
 * @author zdank
 */
public class GenreBuilder {

    private String title;

    public GenreBuilder title(String title) {
        this.title = title;
        return this;
    }

    public Genre build() {
        Genre s = new Genre();
        s.setTitle(title);
        clear();
        return s;
    }

    private void clear() {
        this.title = null;
    }
}
