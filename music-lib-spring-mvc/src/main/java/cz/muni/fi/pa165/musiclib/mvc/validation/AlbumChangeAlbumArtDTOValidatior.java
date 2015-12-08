package cz.muni.fi.pa165.musiclib.mvc.validation;

import cz.muni.fi.pa165.musiclib.dto.AlbumChangeAlbumArtDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates, that either both mime type and image are populated or none of them
 * is.
 *
 * @author Zuzana Dankovcikova
 * @version 08/12/2015
 */
public class AlbumChangeAlbumArtDTOValidatior implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.isAssignableFrom(AlbumChangeAlbumArtDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AlbumChangeAlbumArtDTO changeAlbumArtDTO = (AlbumChangeAlbumArtDTO) o;

        if (changeAlbumArtDTO.getImage() == null && changeAlbumArtDTO.getMimeType() == null) {
            return; //ok, admin wants to delete album art
        }
        if (changeAlbumArtDTO.getImage() == null && changeAlbumArtDTO.getMimeType() != null) {
            errors.rejectValue("image", "ChangeAlbumArtValidatior.image.empty");

        }
        if (changeAlbumArtDTO.getImage() != null && changeAlbumArtDTO.getMimeType() == null) {
            errors.rejectValue("mimeType", "ChangeAlbumArtValidatior.mimeType.empty");
        }
    }
}
