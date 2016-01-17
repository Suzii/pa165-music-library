package cz.muni.fi.pa165.musiclib.mvc.validation;

import cz.muni.fi.pa165.musiclib.mvc.model.UploadedFile;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Martin Stefanko
 * @version 15/12/18
 */
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UploadedFile file = (UploadedFile) o;

        if (file.getFile().getSize() == 0) {
            errors.rejectValue("file", "uploadForm.selectFile",
                    "Please select a file!");
        }
    }
}
