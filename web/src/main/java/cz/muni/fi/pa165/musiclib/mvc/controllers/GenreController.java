package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.facade.GenreFacade;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Genre controller
 *
 * @author David Boron
 */
@Controller
@RequestMapping(value = {"/genre"})
public class GenreController  extends BaseController{

    final static Logger log = LoggerFactory.getLogger(GenreController.class);

    @Inject
    private MessageSource messageSource;

    @Inject
    private GenreFacade genreFacade;

    /**
     * List of all genres in library
     * 
     * @param model
     * @param title
     * @return String jsp template
     */
    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "title", required = false) String title) {
        log.debug("getGenres()");
        List<GenreDTO> genres = (isNullOrWhiteSpace(title)) ? genreFacade.getAllGenres() : genreFacade.searchGenreByTitle(title);
        model.addAttribute("genres", genres);
        
        return "genre/index";
    }

    /**
     * Prepares form for genre creating
     * 
     * @param model
     * @return String jsp template
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create genre()");
        model.addAttribute("genreCreate", new GenreDTO());
        return "genre/create";
    }

    /**
     * Creates genre from form
     * 
     * @param genreFormBean
     * @param bindingResult
     * @param model
     * @param redirectAttributes
     * @param uriBuilder
     * @return String redirect to genre list
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("genreCreate") GenreDTO genreFormBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        log.debug("create genre(formBean={})", genreFormBean);

        if (bindingResult.hasErrors()) {
            addValidationErrors(bindingResult, model);

            return "/genre/create";
        }

        Long id = genreFacade.create(genreFormBean);

        redirectAttributes.addFlashAttribute("alert_success", "Genre with id " + id + " created");
        return "redirect:" + uriBuilder.path("/genre").build().encode().toUriString();
    }

    /**
     * Prepares form for genre updating
     * 
     * @param id
     * @param model
     * @return String jsp template
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
        log.debug("update genre()");
        GenreDTO genre = genreFacade.getGenreById(id);
        model.addAttribute("genreUpdate", genre);

        return "genre/update";
    }

    /**
     * Updates genre from form
     * 
     * @param genreFormBean
     * @param bindingResult
     * @param model
     * @param redirectAttributes
     * @param uriBuilder
     * @return String redirect to genre list
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            @Valid @ModelAttribute("genreUpdate") GenreDTO genreFormBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        log.debug("update genre(formBean={})", genreFormBean);

        GenreDTO oldGenre = genreFacade.getGenreById(genreFormBean.getId());

        if (bindingResult.hasErrors()) {
            addValidationErrors(bindingResult, model);

            return "/genre/update";
        }

        genreFacade.changeTitle(genreFormBean);

        redirectAttributes.addFlashAttribute("alert_success", "Genre \"" + oldGenre.getTitle() + "\" renamed to \"" + genreFormBean.getTitle() + "\"");
        return "redirect:" + uriBuilder.path("/genre").build().encode().toUriString();
    }

    /**
     * Removes genre from library
     * 
     * @param id
     * @param model
     * @param uriBuilder
     * @param redirectAttributes
     * @return String redirect to genre list
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public String remove(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        log.debug("remove()");
        GenreDTO genre = new GenreDTO();
        try {
            genre = genreFacade.getGenreById(id);
            genreFacade.deleteGenre(id);
        } catch (Exception ex) {
            log.error("Genre not found to be deleted.");
            redirectAttributes.addFlashAttribute("alert_danger", "Genre not found.");
            return "redirect:" + uriBuilder.path("/genre").toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_success", "Genre \"" + genre.getTitle() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/genre").toUriString();
    }
}
