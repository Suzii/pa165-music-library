package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.enums.Sex;
import cz.muni.fi.pa165.musiclib.facade.MusicianFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * MusicianController class
 *
 * @author Milan Seman
 * @version 15/12/14
 */
@Controller
@RequestMapping(value = {"/musician"})
public class MusicianController  extends BaseController{

    final static Logger log = LoggerFactory.getLogger(MusicianController.class);
    
    @Inject
    private MusicianFacade musicianFacade;
    
    @Inject
    private MessageSource messageSource;
    
    /**
     * Lists all musicians from library. 
     * @param model
     * @param artistName
     * @return 
     */
    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "artistName", required = false) String artistName) {
        log.debug("getMusicians()");   
        List<MusicianDTO> musicians = (isNullOrWhiteSpace(artistName)) ? musicianFacade.getAllMusicians() : musicianFacade.searchMusicianByArtistName(artistName);
        model.addAttribute("musicians", musicians);
        return "musician/index";
    }
    
    /**
     * Prepares an empty musician create form.
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model){
        log.debug("create musician ()");        
        model.addAttribute("musicianCreate", new MusicianDTO());
        model.addAttribute("male", Sex.MALE);
        model.addAttribute("female", Sex.FEMALE);
        return "musician/create";
    }
    
        /**
     * Handles submit of musician create form.
     * @param musicianFormBean
     * @param model
     * @param bindingResult
     * @param redirectAttributes
     * @param uriComponentsBuilder
     * @return 
     */
    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("musicianCreate") MusicianDTO musicianFormBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder){
            
        log.debug("create musician(formBean={})", musicianFormBean);
        
        if (bindingResult.hasErrors()) {
            addValidationErrors(bindingResult, model);

            return "/musician/create";
        }
        
        Long id = musicianFacade.createMusician(musicianFormBean);        
        redirectAttributes.addFlashAttribute("alert_success", "Musican with id " + id + " created");
        
        return "redirect:" + uriComponentsBuilder.path("/musician/detail/{id}").buildAndExpand(id).encode().toUriString();     
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        
        MusicianDTO musician = musicianFacade.getMusicianById(id);

        model.addAttribute("musician", musician);
        return "musician/detail";
    }
    
    /**
     * Retrieves given musician from DB and prepopulates edit form.
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model ) {
        
        MusicianDTO persistedMusician = musicianFacade.getMusicianById(id);
        
//        MusicianDTO musicianUpdate = new MusicianDTO();
//        musicianUpdate.setId(id);
//        musicianUpdate.setArtistName(persistedMusician.getArtistName());
//        musicianUpdate.setSex(persistedMusician.getSex());
//        musicianUpdate.setDateOfBirth(persistedMusician.getDateOfBirth());
        
        model.addAttribute("male", Sex.MALE);
        model.addAttribute("female", Sex.FEMALE);
        
//        model.addAttribute("musicianUpdate", musicianUpdate);
        model.addAttribute("musicianUpdate", persistedMusician);
        return "musician/update";
    }
    
    /**
     * Handles submit of musician create form.
     * @param formBean
     * @param model
     * @param bindingResult
     * @param redirectAttributes
     * @param uriComponentsBuilder
     * @return 
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(
            @Valid @ModelAttribute("musicianUpdate") MusicianDTO musicianFormBean,
            BindingResult bindingResult,
            @PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {
        
        log.debug("musicianController.update(formBean={})", musicianFormBean);

        if (bindingResult.hasErrors()) {
            addValidationErrors(bindingResult, model);
            return "redirect:" + uriComponentsBuilder.path("/musician/update/{id}").buildAndExpand(id).encode().toUriString();
        }
        
        musicianFacade.updateMusician(musicianFormBean);
        redirectAttributes.addFlashAttribute("alert_success", "Musician " + musicianFormBean.getArtistName()+ " updated");
        return "redirect:" + uriComponentsBuilder.path("/musician/detail/{id}").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public String remove(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        
        log.debug("musicianController.remove()");
        musicianFacade.removeMusician(id);
        
        redirectAttributes.addFlashAttribute("alert_success", "Musician successfully deleted.");
        return "redirect:" + uriBuilder.path("/musician").toUriString();
    } 
   
}
