package cz.muni.fi.pa165.musiclib.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Dido
 * @version 10/24/15
 */
@Entity
public class Genre {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String title;

    public Long getId() {
        return id;
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
        if(this == obj){
            return true;
        }
        if (!(obj instanceof Album)) {
            return false;
        }
       
        final Genre other = (Genre) obj;
        
        if((title != null) ? !title.equals(other.getTitle()): other.getTitle()!= null){
            return false;
        }
        
        return true;
    }
    
    
}
