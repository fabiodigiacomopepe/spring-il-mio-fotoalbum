package org.lessons.java.springilmiofotoalbum.repository;

import org.lessons.java.springilmiofotoalbum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// Interfaccia che estende la JPA REPOSITORY con in ingresso il Model e il tipo di dato dell'ID
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
}
