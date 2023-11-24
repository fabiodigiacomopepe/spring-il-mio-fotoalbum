package org.lessons.java.springilmiofotoalbum.repository;

import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Interfaccia che estende la JPA REPOSITORY con in ingresso il Model e il tipo di dato dell'ID
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    // Query SQL automatica in base a nome del metodo
    // Con @Query si possono fare anche personalizzate
    List<Photo> findByTitleContainingIgnoreCase(String nameKeyword);

    List<Photo> findByTitleContainingIgnoreCaseAndVisibleTrue(String nameKeyword);

    List<Photo> findAllByVisibleTrue();
}
