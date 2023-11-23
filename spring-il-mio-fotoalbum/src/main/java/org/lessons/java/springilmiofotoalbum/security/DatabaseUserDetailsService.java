package org.lessons.java.springilmiofotoalbum.security;

import org.lessons.java.springilmiofotoalbum.model.User;
import org.lessons.java.springilmiofotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    // Implemento userRepository per fare ricerca partendo da email (che sarà l'username)
    @Autowired
    private UserRepository userRepository;

    // Override dei metodi (visto che ho fatto implements)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cerco su DB utente che ha come email, l'username che ricevo in ingresso
        // Utilizzo Optional perchè potrebbe non ritornare niente
        Optional<User> loggedUser = userRepository.findByEmail(username);
        // Se è presente
        if (loggedUser.isPresent()) {
            // Ritorno DatabaseUserDetails con dati di utente
            return new DatabaseUserDetails(loggedUser.get());
        } else {
            // Altrimenti lancio eccezione
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
    }
}