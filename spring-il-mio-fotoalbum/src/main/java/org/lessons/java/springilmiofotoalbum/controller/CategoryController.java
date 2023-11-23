package org.lessons.java.springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.exceptions.CategoryNameUniqueException;
import org.lessons.java.springilmiofotoalbum.exceptions.CategoryNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    // Rotta "/categories/delete/id <---(dinamico)" (POST)
    @PostMapping("/delete/{id}")
    // Parametri in ingresso:
    // @PathVariable Integer id -> per gestire quale elemento eliminare
    // RedirectAttributes redirectAttributes -> attributi che ci sono solo nel redirect
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Provo a prendere categoria in base a id
            Category categoryToDelete = categoryService.getCategoryById(id);
            // Elimino prima relazione con foto e poi categoria
            categoryService.deleteCategoryWithAssociations(id);
            // Passo il messaggio durante il redirect
            redirectAttributes.addFlashAttribute("message", "Categoria '" + categoryToDelete.getName() + "' eliminata correttamente!");
            return "redirect:/categories";
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
