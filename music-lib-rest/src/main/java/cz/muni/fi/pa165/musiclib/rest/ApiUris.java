package cz.muni.fi.pa165.musiclib.rest;

/**
 *
 * @author Milan
**/


/*
 * Represents the entry points for the API
 * this list can be increased so that it contains all the 
 * other URIs also for the sub-resources so that it can 
 * reused globally from all the controllers
 *
 */
public abstract class ApiUris {
    public static final String ROOT_URI_ALBUMS   = "/albums"; 
    public static final String ROOT_URI_GENRES      = "/genres";
}
