package cz.muni.fi.pa165.musiclib.rest;

/**
 * Represents the entry points for the API
 * this list can be increased so that it contains all the
 * other URIs also for the sub-resources so that it can
 * reused globally from all the controllers
 *
 * @author Milan Seman
 * @version 15/12/15
**/
public abstract class ApiUris {
    public static final String ROOT_URI_ALBUMS = "/albums"; 
    public static final String ROOT_URI_GENRES = "/genres";
}
