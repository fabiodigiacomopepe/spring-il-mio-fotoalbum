package org.lessons.java.springilmiofotoalbum.api;

import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.model.Message;
import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.lessons.java.springilmiofotoalbum.service.MessageService;
import org.lessons.java.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/photos")
@CrossOrigin
public class PhotoRestController {
    @Autowired
    private PhotoService photoService;

    @Autowired
    private MessageService messageService;

    // Rotta "/api/v1/photos" (GET)
    @GetMapping
    public List<Photo> index(@RequestParam Optional<String> search) {
        // Ritorno lista di foto
        // Visto che search è opzionale, se c'è lo uso
        // altrimenti passo stringa vuota
        return photoService.getPhotoListAPI(Optional.of(search.orElse("")));
    }

    // Rotta "/api/v1/photos/send" (POST)
    @PostMapping("/send")
    public Message createNewMessage(@Valid @RequestBody Message message) {
        // Creo una nuovo messaggio passando in ingresso dati ricevuti
        // Valido il tutto
        // Uso @RequestBody per fare conversione da JSON -> Oggetto Java
        return messageService.createMessage(message);
    }
}

