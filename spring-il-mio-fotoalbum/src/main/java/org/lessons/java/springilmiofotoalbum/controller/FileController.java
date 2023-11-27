package org.lessons.java.springilmiofotoalbum.controller;

import org.lessons.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.lessons.java.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private PhotoService photoService;

    // Rotta "/files/cover/id <---(dinamico)"
    @GetMapping("/cover/{photoId}")
    public ResponseEntity<byte[]> serveCover(@PathVariable Integer photoId) {
        try {
            // Recupero photo da ID
            Photo photo = photoService.getPhotoById(photoId);
            byte[] coverBytes = photo.getCover();
            if (coverBytes != null && coverBytes.length > 0) {
                MediaType mediaType = MediaType.IMAGE_JPEG;
                // Ritorno HTTP 200
                return ResponseEntity.ok().contentType(mediaType).body(coverBytes);
            } else {
                // Ritorno HTTP 404
                return ResponseEntity.notFound().build();
            }
        } catch (PhotoNotFoundException e) {
            // Ritorno HTTP 404
            return ResponseEntity.notFound().build();
        }
        // Ritorno byte[] della cover
    }
}
