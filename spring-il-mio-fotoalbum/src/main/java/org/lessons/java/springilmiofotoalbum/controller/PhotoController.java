package org.lessons.java.springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.lessons.java.springilmiofotoalbum.model.User;
import org.lessons.java.springilmiofotoalbum.repository.UserRepository;
import org.lessons.java.springilmiofotoalbum.security.DatabaseUserDetails;
import org.lessons.java.springilmiofotoalbum.service.CategoryService;
import org.lessons.java.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    // Rotta "/photos"
    // Parametro di ricerca è OPZIONALE perchè alla rotta si può accedere sia normalmente sia tramite ricerca
    @GetMapping
    public String index(@RequestParam Optional<String> search, Model model, Authentication authentication) {
        DatabaseUserDetails principal = (DatabaseUserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findById(principal.getId()).get();
        // Passo il risultato al model
        model.addAttribute("photoList", photoService.getPhotoList(search, loggedUser.getId()));
        return "administrations/list";
    }

    // Rotta "/photos/show/id <---(dinamico)"
    @GetMapping("/show/{id}")
    // Prendo l'id dal path
    public String show(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getPhotoById(id);
            model.addAttribute("photo", photo);
            return "administrations/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/photos/create" (GET)
    @GetMapping("/create")
    public String createGet(Model model) {
        // Istanzio un nuovo oggetto Photo e lo passo con il model
        model.addAttribute("photo", new Photo());
        model.addAttribute("categoryList", categoryService.getAll());
        return "administrations/create_edit";
    }

    // Rotta "/photos/create" (POST)
    @PostMapping("/create")
    public String createPost(Model model, @Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult) {
        // Controllo se ci sono errori
        if (bindingResult.hasErrors()) {
            // Se ci sono ricarico la pagina mantendendo i dati (grazie al model)
            model.addAttribute("categoryList", categoryService.getAll());
            return "administrations/create_edit";
        }
        // Recupero l'oggetto Pohoto dal model e lo salvo in formPhoto
        // Creo una nuovo oggetto Photo chiamato savedPhoto e passo i dati dal form (formPhoto)
        Photo savedPhoto = photoService.createPhoto(formPhoto);
        return "redirect:/photos/show/" + savedPhoto.getId();
    }

    // Rotta "/photos/edit/id <---(dinamico)" (GET)
    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable Integer id, Model model) {
        try {
            // Passo la photo con il model
            model.addAttribute("photo", photoService.getPhotoById(id));
            model.addAttribute("categoryList", categoryService.getAll());
            return "administrations/create_edit";
        } catch (PhotoNotFoundException e) {
            // Altrimenti lancio eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/photos/edit/id <---(dinamico)" (POST)
    @PostMapping("/edit/{id}")
    // Parametri in ingresso:
    // @PathVariable Integer id -> per gestire quale elemento modifcare
    // @Valid -> per validazioni
    // @ModelAttribute("photo") -> per ritornare stessa photo in caso di errori
    // Pizza formPhoto -> cioò che ricevo dal form
    // BindingResult bindingResult -> mappa errori
    public String editPost(
            @PathVariable Integer id,
            @Valid
            @ModelAttribute("photo")
            Photo formPhoto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            // Se ci sono errori ricarico la pagina
            model.addAttribute("categoryList", categoryService.getAll());
            return "/administrations/create_edit";
        }
        try {
            Photo savedPhoto = photoService.editPhoto(formPhoto);
            return "redirect:/photos/show/" + savedPhoto.getId();
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/photos/delete/id <---(dinamico)" (POST)
    @PostMapping("/delete/{id}")
    // Parametri in ingresso:
    // @PathVariable Integer id -> per gestire quale elemento eliminare
    // RedirectAttributes redirectAttributes -> attributi che ci sono solo nel redirect
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Provo a prendere photo in base a id
            Photo photoToDelete = photoService.getPhotoById(id);
            // Elimino photo per id
            photoService.deletePhoto(id);
            // Passo il messaggio durante il redirect
            redirectAttributes.addFlashAttribute("message", "Foto '" + photoToDelete.getTitle() + "' eliminata correttamente!");
            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
