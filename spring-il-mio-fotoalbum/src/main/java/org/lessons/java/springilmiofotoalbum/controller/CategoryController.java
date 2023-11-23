package org.lessons.java.springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.exceptions.CategoryNameUniqueException;
import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Rotta "/categories" (GET - AL CARICAMENTO DELLA PAGINA DA NAVBAR)
    @GetMapping
    public String index(Model model) {
        // Passo al model la lista delle categorie per popolare pagina
        model.addAttribute("categoryList", categoryService.getAll());
        // Passo al model anche una nuova categoria per la create
        model.addAttribute("categoryObj", new Category());
        return "categories/list";
    }

    // Rotta "/categories" (POST - QUANDO SI CREA INGREDIENTE)
    @PostMapping
    public String create(@Valid @ModelAttribute("categoryObj") Category formCategory, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Se ci sono errori
        if (bindingResult.hasErrors()) {
            // Ricarico la pagina
            // Passo anche la lista sempre per ripopolare pagina
            model.addAttribute("categoryList", categoryService.getAll());
            return "categories/list";
        }
        try {
            // Provo a salvare categoria con dati presi da form
            categoryService.save(formCategory);
            redirectAttributes.addFlashAttribute("message", "Categoria '" + formCategory.getName() + "' creata correttamente!");
            return "redirect:/categories";
        } catch (CategoryNameUniqueException e) {
            // Se non ci riesco (perchè già esiste) ricarico la pagina e ritorno un messaggio
            redirectAttributes.addFlashAttribute("messageFailed", "La categoria '" + formCategory.getName() + "' esiste già");
            return "redirect:/categories";
        }
    }
}
