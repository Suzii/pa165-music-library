package cz.muni.fi.pa165.musiclib.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Zuzana Dankovcikova
 * @version 16/11/2015
 */
public class UserDTO {
    
    private Long id;
    
    @NotNull
    @Size(min=5, max=50)
    private String email;
    
    private String passwordHash;

    private String firstName;
    
    private String lastName;

    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + ((email == null) ? 0 : email.hashCode());
        hash = 23 * hash + ((firstName == null) ? 0 : firstName.hashCode());
        hash = 23 * hash + ((lastName == null) ? 0 : lastName.hashCode());
        hash = 23 * hash + ((passwordHash == null) ? 0 : passwordHash.hashCode());
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
        if (!(obj instanceof UserDTO)) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        if((email != null) ? !email.equals(other.getEmail()): other.getEmail()!= null){
            return false;
        }
        
        if((passwordHash != null) ? !passwordHash.equals(other.getPasswordHash()): other.getPasswordHash()!= null){
            return false;
        }
        
        if((firstName != null) ? !firstName.equals(other.getFirstName()): other.getFirstName()!= null){
            return false;
        }
        
        if((lastName != null) ? !lastName.equals(other.getLastName()): other.getLastName()!= null){
            return false;
        }
        return true;
    }
}
