/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.musiclib.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author zdank
 */
@Entity
@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    @Pattern(regexp=".+@.+\\....?")
    private String email;
    
    @NotNull
    private String passwordHash;
    
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;

    public Long getId() {
        return id;
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
        if (!(obj instanceof User)) {
            return false;
        }
        final User other = (User) obj;
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
