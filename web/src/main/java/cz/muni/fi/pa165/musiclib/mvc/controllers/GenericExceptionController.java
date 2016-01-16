package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.mvc.exceptions.GenericMusicLibraryException;
import cz.muni.fi.pa165.musiclib.exception.NoSuchEntityFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to handle all exceptions.
 *
 * @author Zuzana Dankovcikova
 * @version 12/12/2015
 */
@ControllerAdvice
public class GenericExceptionController {

    final static Logger log = LoggerFactory.getLogger(GenericExceptionController.class);

    @ExceptionHandler(NoSuchEntityFoundException.class)
    public ModelAndView handleCustomException(NoSuchEntityFoundException ex) {

        ModelAndView model = new ModelAndView("/error");
        model.addObject("errMsg", ex.getMessage());

        return model;
    }

    @ExceptionHandler(GenericMusicLibraryException.class)
    public ModelAndView handleGenericMusicLibraryException(GenericMusicLibraryException ex) {

        ModelAndView model = new ModelAndView("/error");
        model.addObject("errMsg", "this is GenericMusicLibraryException.class");

        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView model = new ModelAndView("/error");
        model.addObject("errMsg", ex);
        log.error(ex.getLocalizedMessage());

        return model;
    }
}
