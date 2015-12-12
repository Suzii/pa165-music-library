package cz.muni.fi.pa165.musiclib.dto;

/**
 * DTO object for updating songs = extends create DTO by adding id property.
 * 
 * @author Zuzana Dankovcikova
 * @version 12/12/2015
 */
public class SongUpdateDTO extends SongCreateDTO {
    
    private Long id; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
