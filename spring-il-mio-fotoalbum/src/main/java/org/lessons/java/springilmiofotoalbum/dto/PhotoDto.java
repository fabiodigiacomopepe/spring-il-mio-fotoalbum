package org.lessons.java.springilmiofotoalbum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhotoDto {
    private Integer id;
    @NotBlank(message = "Il titolo non può essere vuoto")
    @Size(max = 255, message = "Il titolo non può essere più lungo di 255 caratteri")
    private String title;
    @NotBlank(message = "La descrizione non può essere vuota")
    @Size(max = 255, message = "La descrizione non può essere più lunga di 255 caratteri")
    private String description;

    @NotNull(message = "Indicare una visibilità")
    private boolean visible;

    private MultipartFile coverFile;

    private List<Category> categories;

    @NotNull
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public MultipartFile getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(MultipartFile coverFile) {
        this.coverFile = coverFile;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

