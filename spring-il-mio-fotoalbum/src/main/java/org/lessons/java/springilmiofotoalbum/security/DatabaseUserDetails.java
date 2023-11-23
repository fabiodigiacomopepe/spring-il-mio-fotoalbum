package org.lessons.java.springilmiofotoalbum.security;

import org.lessons.java.springilmiofotoalbum.model.Role;
import org.lessons.java.springilmiofotoalbum.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {
    // Attributi
    private Integer id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    // Costruttore
    public DatabaseUserDetails(User user) {
        // Valorizzo i miei attributi con valore che ricevo da user
        this.id = user.getId();
        this.username = user.getEmail();        // Imposto email come username !
        this.password = user.getPassword();
        // Per ogni oggetto role che prendo con getRoles(), lo aggiungo all'HashSet con metodo add()
        // e creo un nuovo SimpleGrantedAuthority (che è quello che vuole Spring)
        // passando il nome del role con getName()
        authorities = new HashSet<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    // Getter
    public Integer getId() {
        return id;
    }

    // Override dei metodi (visto che ho fatto implements)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;    // Passo le authorities
    }

    @Override
    public String getPassword() {
        return this.password;       // Passo la password
    }

    @Override
    public String getUsername() {
        return this.username;       // Passo l'username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;        // TRUE perchè non lo gestisco
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;        // TRUE perchè non lo gestisco
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;        // TRUE perchè non lo gestisco
    }

    @Override
    public boolean isEnabled() {
        return true;        // TRUE perchè non lo gestisco
    }
}
