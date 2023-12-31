package org.lessons.java.springilmiofotoalbum.controller;

import org.lessons.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.lessons.java.springilmiofotoalbum.model.Role;
import org.lessons.java.springilmiofotoalbum.model.User;
import org.lessons.java.springilmiofotoalbum.repository.PhotoRepository;
import org.lessons.java.springilmiofotoalbum.repository.UserRepository;
import org.lessons.java.springilmiofotoalbum.security.DatabaseUserDetails;
import org.lessons.java.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/superadmin")
public class SuperadminController {
    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    // Rotta "/superadmin"
    // Parametro di ricerca è OPZIONALE perchè alla rotta si può accedere sia normalmente sia tramite ricerca
    @GetMapping
    public String index(@RequestParam Optional<String> search, Model model, Authentication authentication) {
        if (testSuperAdmin(authentication)) {
            // Passo il risultato al model
            model.addAttribute("photoList", photoService.getPhotoListSuperAdmin(search));
            return "superadmin/index";
        } else {
            return "redirect:/photos";
        }
    }

    // Rotta "/superadmin/showPhoto/id <---(dinamico)"
    @GetMapping("/showPhoto/{id}")
    // Prendo l'id dal path
    public String showPhoto(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            if (testSuperAdmin(authentication)) {
                Photo photo = photoService.getPhotoById(id);
                photo.setVisible(true);
                photoRepository.save(photo);
                redirectAttributes.addFlashAttribute("message", "Visibilità della foto '" + photo.getTitle() + "': ON");
                return "redirect:/superadmin";
            } else {
                return "redirect:/photos";
            }
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/superadmin/hiddenPhoto/id <---(dinamico)"
    @GetMapping("/hiddenPhoto/{id}")
    // Prendo l'id dal path
    public String hiddenPhoto(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            if (testSuperAdmin(authentication)) {
                Photo photo = photoService.getPhotoById(id);
                photo.setVisible(false);
                photoRepository.save(photo);
                redirectAttributes.addFlashAttribute("message", "Visibilità della foto '" + photo.getTitle() + "': OFF");
                return "redirect:/superadmin";
            } else {
                return "redirect:/photos";
            }
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public boolean testSuperAdmin(Authentication authentication) {
        DatabaseUserDetails principal = (DatabaseUserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findById(principal.getId()).get();
        boolean isAdmin = false;
        for (Role role : loggedUser.getRoles()) {
            if (role.getName().equals("SUPER_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }
}
