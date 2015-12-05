package cz.muni.fi.pa165.musiclib.utils;

import cz.muni.fi.pa165.musiclib.entity.*;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/27/15
 */
public class UserBuilder {
    private Long id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private boolean admin;

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder admin(boolean admin) {
        this.admin = admin;
        return this;
    }

    
    public User build() {
        User u = new User(id);

        u.setEmail(email);
        u.setPasswordHash(passwordHash);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setAdmin(admin);

        clear();

        return u;
    }

    private void clear() {
        this.email = null;
        this.passwordHash= null;
        this.firstName = null;
        this.lastName = null;
        this.admin = false;
    }
}
