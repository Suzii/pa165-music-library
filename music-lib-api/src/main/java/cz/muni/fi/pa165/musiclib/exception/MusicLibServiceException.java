package cz.muni.fi.pa165.musiclib.exception;

/**
 * @author Martin Stefanko (mstefank@redhat.com)
 */
public class MusicLibServiceException extends RuntimeException {

    public MusicLibServiceException() {
    }

    public MusicLibServiceException(String message) {
        super(message);
    }

    public MusicLibServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MusicLibServiceException(Throwable cause) {
        super(cause);
    }

    public MusicLibServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
