package cz.muni.fi.pa165.musiclib.mixins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Jackson mix-in for disabling some unnecessary Album attributes
 *
 * @author David Boron
 */
@JsonIgnoreProperties({"albumArt", "albumArtMimeType"})
public abstract class AlbumDTOMixin {
}