package org.lessons.java.springilmiofotoalbum.service;

import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.lessons.java.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    // Istanzio automaticamente photoRepository tramite Autowired
    @Autowired
    private PhotoRepository photoRepository;

    public List<Photo> getPhotoList(Optional<String> search) {
        // Se è stato passato un parametro di ricerca
        if (search.isPresent()) {
            // Lo prendo con il .GET() e lo utilizzo per farmi ritornare una lista filtrata in base al nome
            return photoRepository.findByTitleContainingIgnoreCase(search.get());
        } else {
            // Altrimenti ritorno lista completa
            return photoRepository.findAll();
        }
    }
}