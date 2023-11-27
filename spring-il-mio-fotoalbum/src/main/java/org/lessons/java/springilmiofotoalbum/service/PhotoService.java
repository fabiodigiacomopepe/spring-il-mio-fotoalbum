package org.lessons.java.springilmiofotoalbum.service;

import org.lessons.java.springilmiofotoalbum.dto.PhotoDto;
import org.lessons.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Photo;
import org.lessons.java.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    // Istanzio automaticamente photoRepository tramite Autowired
    @Autowired
    private PhotoRepository photoRepository;

    public List<Photo> getPhotoList(Optional<String> search, Integer id) {
        // Se è stato passato un parametro di ricerca
        if (search.isPresent()) {
            // Lo prendo con il .GET() e lo utilizzo per farmi ritornare una lista filtrata in base al nome
            return photoRepository.findByTitleContainingIgnoreCaseAndUserId(search.get(), id);
        } else {
            // Altrimenti ritorno lista completa
            return photoRepository.findByUserId(id);
        }
    }

    public List<Photo> getPhotoListAPI(Optional<String> search) {
        // Se è stato passato un parametro di ricerca
        if (search.isPresent()) {
            // Lo prendo con il .GET() e lo utilizzo per farmi ritornare una lista filtrata in base al nome
            return photoRepository.findByTitleContainingIgnoreCaseAndVisibleTrue(search.get());
        } else {
            // Altrimenti ritorno lista completa
            return photoRepository.findAllByVisibleTrue();
        }
    }

    public List<Photo> getPhotoListSuperAdmin(Optional<String> search) {
        // Se è stato passato un parametro di ricerca
        if (search.isPresent()) {
            // Lo prendo con il .GET() e lo utilizzo per farmi ritornare una lista filtrata in base al nome
            return photoRepository.findByTitleContainingIgnoreCase(search.get());
        } else {
            // Altrimenti ritorno lista completa
            return photoRepository.findAll();
        }
    }

    public Photo getPhotoById(Integer id) throws PhotoNotFoundException {
        // Salvo in result in modo Optional perchè potrebbe non ritornare nulla
        Optional<Photo> result = photoRepository.findById(id);
        // Se il risultato è presente
        if (result.isPresent()) {
            // Ritorno la pizza (result.get())
            return result.get();
        } else {
            // Altrimenti lancio eccezione
            throw new PhotoNotFoundException("Photo with ID " + id + ": Not Found");
        }
    }

    public Photo createPhoto(Photo photo) throws RuntimeException {
        // Setto ID foto a null per evitare UPDATE involontario (!!)
        photo.setId(null);
        return photoRepository.save(photo);
    }

    public Photo createPhoto(PhotoDto photoDto) throws IOException {
        // Converto photoDto in photo
        Photo photo = convertDtoToPhoto(photoDto);
        // Salvo la photo sul DB
        return createPhoto(photo);
    }

    private static Photo convertDtoToPhoto(PhotoDto photoDto) throws IOException {
        // Creo una nuova photo
        Photo photo = new Photo();
        // Setto alla photo ciò che ricevo da photoDto
        photo.setTitle(photoDto.getTitle());
        photo.setDescription(photoDto.getDescription());
        photo.setVisible(photoDto.isVisible());
        photo.setCategories(photoDto.getCategories());
        photo.setUser(photoDto.getUser());
        photo.setId(photoDto.getId());
        if (photoDto.getCoverFile() != null && !photoDto.getCoverFile().isEmpty()) {
            // Trasformo MultipartFile in byte[]
            byte[] bytes = photoDto.getCoverFile().getBytes();
            photo.setCover(bytes);
        }
        // Ritorno la photo
        return photo;
    }

    private static PhotoDto convertPhotoToDto(Photo photo) {
        // Creo una nuova photoDto
        PhotoDto photoDto = new PhotoDto();
        // Setto alla photoDto ciò che ricevo da photo
        photoDto.setTitle(photo.getTitle());
        photoDto.setDescription(photo.getDescription());
        photoDto.setVisible(photo.isVisible());
        photoDto.setCategories(photo.getCategories());
        photoDto.setUser(photo.getUser());
        photoDto.setId(photo.getId());
        // Ritorno la photoDto
        return photoDto;
    }

    public PhotoDto getPhotoDtoById(Integer id) throws PhotoNotFoundException {
        // Cerco la photo
        Photo photo = getPhotoById(id);
        // Ritorno una photo convertita in photoDto
        return convertPhotoToDto(photo);
    }

    public Photo editPhoto(Photo photo) throws PhotoNotFoundException {
        // Provo a prendere photo in base a id
        Photo photoToEdit = getPhotoById(photo.getId());
        // Valorizzo con i setter i vari parametri passando quelli ricevuti dal form
        photoToEdit.setTitle(photo.getTitle());
        photoToEdit.setDescription(photo.getDescription());
        photoToEdit.setVisible(photo.isVisible());
        photoToEdit.setCategories(photo.getCategories());
        photoToEdit.setUser(photo.getUser());
        if (photo.getCover() != null && photo.getCover().length > 0) {
            photoToEdit.setCover(photo.getCover());
        }
        // Salvo la photo
        // Metodo .save salva ciò che riceve. Se i campi nel form mancano, li lascia vuoti (non si comporta come update)
        return photoRepository.save(photoToEdit);
    }

    public Photo editPhoto(PhotoDto photoDto) throws IOException {
        // Converto photoDto in photo
        Photo photo = convertDtoToPhoto(photoDto);
        // Chiamo il metodo edit passando una photo
        return editPhoto(photo);
    }

    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }
}