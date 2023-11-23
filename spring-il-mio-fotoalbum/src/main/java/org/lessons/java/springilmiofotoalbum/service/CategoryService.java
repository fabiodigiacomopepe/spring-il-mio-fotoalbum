package org.lessons.java.springilmiofotoalbum.service;

import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    // Istanzio automaticamente categoryRepository tramite Autowired
    @Autowired
    private CategoryRepository categoryRepository;

    // Ritorno lista di tutte le categorie
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
