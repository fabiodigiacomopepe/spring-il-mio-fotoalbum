package org.lessons.java.springilmiofotoalbum.api;

import org.lessons.java.springilmiofotoalbum.model.Photo;
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

    // Rotta "/api/v1/photos" (GET)
    @GetMapping
    public List<Photo> index(@RequestParam Optional<String> search) {
        // Ritorno lista di foto
        // Visto che search è opzionale, se c'è lo uso
        // altrimenti passo stringa vuota
        return photoService.getPhotoList(Optional.of(search.orElse("")));
    }
}

