package cz.muni.fi.pa165.musiclib.exception;

import org.springframework.dao.DataAccessException;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 11/25/15
 */
public class MusicLibDataAccessException extends DataAccessException {

    public MusicLibDataAccessException(String msg) {
        super(msg);
    }

    public MusicLibDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
