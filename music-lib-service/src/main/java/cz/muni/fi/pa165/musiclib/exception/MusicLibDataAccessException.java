package cz.muni.fi.pa165.musiclib.exception;

import org.springframework.dao.DataAccessException;

/**
 * @author Martin Stefanko
 * @version 15/11/25
 */
public class MusicLibDataAccessException extends DataAccessException {

    public MusicLibDataAccessException(String msg) {
        super(msg);
    }

    public MusicLibDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
