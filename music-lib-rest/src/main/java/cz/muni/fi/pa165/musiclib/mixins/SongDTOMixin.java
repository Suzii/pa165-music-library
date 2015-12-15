package cz.muni.fi.pa165.musiclib.mixins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Jackson mix-in for disabling some unnecessary Song attributes
 *
 * @author David Boron
 */
@JsonIgnoreProperties({"album", "musician"})
public abstract class SongDTOMixin {
}