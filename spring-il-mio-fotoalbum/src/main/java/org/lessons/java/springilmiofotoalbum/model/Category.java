package org.lessons.java.springilmiofotoalbum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Category {
    // Attributi (in questo caso nomi delle colonne)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Per auto-increment
    private Integer id;
    @NotNull(message = "La categoria non può essere nulla")
    @NotBlank(message = "La categoria non può essere vuota")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}