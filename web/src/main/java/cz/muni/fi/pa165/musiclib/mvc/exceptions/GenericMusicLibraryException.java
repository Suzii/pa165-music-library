package cz.muni.fi.pa165.musiclib.mvc.exceptions;

/**
 * Generic exception.
 *
 * @author Zuzana Dankovcikova
 * @version 15/12/12
 */
public class GenericMusicLibraryException extends RuntimeException {

    private String errCode;
    private String errMsg;

    public GenericMusicLibraryException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
