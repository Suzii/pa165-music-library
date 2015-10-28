/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Song;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author zdank
 */
public class AlbumBuilder {
    
    private String title;
    
    public AlbumBuilder title(String title){
        this.title = title;
        return this;
    }
    
    public Album build() { 
        Album a = new Album();
        a.setTitle(title);
        clear();
        
        return a;
    }

    private void clear() {
        this.title = null;
    }
}
