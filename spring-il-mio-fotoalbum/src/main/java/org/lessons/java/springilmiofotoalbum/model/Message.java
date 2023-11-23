package org.lessons.java.springilmiofotoalbum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "messages")
public class Message {
    // Attributi (in questo caso nomi delle colonne)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Per auto-increment
    private Integer id;
    @NotBlank(message = "Il contenuto non può essere vuoto")
    @Size(max = 255, message = "Il contenuto non può essere più lungo di 255 caratteri")
    @Lob
    private String body;

    @NotBlank(message = "L'email del mittente non può essere vuota")
    @Size(max = 255, message = "L'email del mittente non può essere più lunga di 255 caratteri")
    private String senderEmail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }
}
