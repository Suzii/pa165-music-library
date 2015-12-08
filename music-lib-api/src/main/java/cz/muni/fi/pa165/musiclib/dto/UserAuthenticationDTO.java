package cz.muni.fi.pa165.musiclib.dto;

import javax.validation.constraints.NotNull;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/21/15
 */
public class UserAuthenticationDTO {

    @NotNull
    private Long userId;
    
    @NotNull
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
