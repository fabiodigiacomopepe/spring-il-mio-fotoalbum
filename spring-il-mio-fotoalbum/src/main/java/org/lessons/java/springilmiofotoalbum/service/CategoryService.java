package org.lessons.java.springilmiofotoalbum.service;

import jakarta.transaction.Transactional;
import org.lessons.java.springilmiofotoalbum.exceptions.CategoryNameUniqueException;
import org.lessons.java.springilmiofotoalbum.exceptions.CategoryNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.lessons.java.springilmiofotoalbum.repository.CategoryRepository;
import org.lessons.java.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    // Istanzio automaticamente categoryRepository tramite Autowired
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PhotoRepository photoRepository;

    // Ritorno lista di tutte le categorie
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    // Salvo categoria
    public Category save(Category category) throws CategoryNameUniqueException {
        // Verifico che categoria non sia duplicato
        if (categoryRepository.existsByName(category.getName())) {
            // Se lo è, lancio eccezione passando il nome della categoria
            throw new CategoryNameUniqueException(category.getName());
        }
        // Trasformo il nome in lowercase
        category.setName(category.getName().toLowerCase());
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
        // Salvo in result in modo Optional perchè potrebbe non ritornare nulla
        Optional<Category> result = categoryRepository.findById(id);
        // Se il risultato è presente
        if (result.isPresent()) {
            // Ritorno la categoria (result.get())
            return result.get();
        } else {
            // Altrimenti lancio eccezione
            throw new CategoryNotFoundException("Category with ID " + id + ": Not Found");
        }
    }

    @Transactional
    public void deleteCategoryWithAssociations(Integer id) {
        // Recupero la categoria tramite ID
        Category categoryToDelete = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + ": Not Found"));
        // Rimuovo l'associazione tra categoria e foto
        // Ciclo su ogni oggetto foto e rimuovo la categoria
        for (Photo photo : photoRepository.findAll()) {
            photo.getCategories().remove(categoryToDelete);
        }
        // Elimino la categoria
        categoryRepository.delete(categoryToDelete);
    }
}
