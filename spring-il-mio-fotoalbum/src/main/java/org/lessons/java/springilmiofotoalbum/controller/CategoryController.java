package org.lessons.java.springilmiofotoalbum.controller;

import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
