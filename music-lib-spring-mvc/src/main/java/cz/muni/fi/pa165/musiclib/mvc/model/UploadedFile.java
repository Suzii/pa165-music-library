package cz.muni.fi.pa165.musiclib.mvc.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/17/15
 */
public class UploadedFile {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
