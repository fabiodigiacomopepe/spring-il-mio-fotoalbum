package org.lessons.java.springilmiofotoalbum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "photos")
public class Photo {
    // Attributi (in questo caso nomi delle colonne)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Per auto-increment
    private Integer id;
    @NotBlank(message = "Il titolo non può essere vuoto")
    @Size(max = 255, message = "Il titolo non può essere più lungo di 255 caratteri")
    private String title;
    @NotBlank(message = "La descrizione non può essere vuota")
    @Size(max = 255, message = "La descrizione non può essere più lunga di 255 caratteri")
    private String description;
    private String url;
    @NotNull(message = "Indicare una visibilità")
    private boolean visible;

    @Lob
    @Column(length = 16777215)
    @JsonIgnore
    private byte[] cover;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;

    @NotNull
    @ManyToOne
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
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
