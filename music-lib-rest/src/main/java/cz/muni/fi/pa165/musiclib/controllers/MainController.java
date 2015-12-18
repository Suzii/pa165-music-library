package cz.muni.fi.pa165.musiclib.controllers;

import cz.muni.fi.pa165.musiclib.rest.ApiUris;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Milan Seman
 */

@RestController
public class MainController {
    
    final static Logger logger = LoggerFactory.getLogger(MainController.class);
    
    /**
     * The main entry point of the REST API
     * Provides access to all the resources with links to resource URIs
     * @return resources uris
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {

        Map<String,String> resourcesMap = new HashMap<>();
        
        resourcesMap.put("albums_uri", ApiUris.ROOT_URI_ALBUMS);
        resourcesMap.put("genres_uri", ApiUris.ROOT_URI_GENRES);

        return Collections.unmodifiableMap(resourcesMap);
        
    }
}
