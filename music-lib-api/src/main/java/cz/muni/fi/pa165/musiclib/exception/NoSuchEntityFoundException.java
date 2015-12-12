package cz.muni.fi.pa165.musiclib.exception;

/**
 * Exception thrown when user tries to access page by entering non existing id.
 *
 * @author Zuzana Dankovcikova
 * @version 12/12/2015
 */
public class NoSuchEntityFoundException extends RuntimeException {

    private String message;

    public NoSuchEntityFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
