package cz.muni.fi.pa165.musiclib.mvc.controllers;

import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.SongCreateDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.facade.AlbumFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Album controller
 *
 * @author Martin Stefanko
 * @version 12/12/2015
 */
@Controller
@RequestMapping(value = {"/album"})
public class AlbumController  extends BaseController {

    final static Logger log = LoggerFactory.getLogger(AlbumController.class);

    @Inject
    private AlbumFacade albumFacade;

    @Inject
    private MessageSource messageSource;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Albums");
        List<AlbumDTO> albums = albumFacade.getAllAlbums();
        model.addAttribute("albums", albums);

        return "album/index";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model) {

        model.addAttribute("albumCreate", new AlbumDTO());
        return "album/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            //DO NOT CHANGE the order of first two parameters
            @Valid @ModelAttribute("albumCreate") AlbumDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder) {

        log.debug("create(formBean={})", formBean);

        //if there are any validation errors forward back to the the form
        if (bindingResult.hasErrors()) {
            addValidationErrors(bindingResult, model);
            return "/album/create";
        }

        Long albumId = albumFacade.createAlbum(formBean);
        redirectAttributes.addFlashAttribute("alert_success", "Album with id " + albumId + " created");

        return "redirect:" + uriComponentsBuilder.path("/album/detail/{id}").buildAndExpand(albumId).encode().toUriString();
    }


}
