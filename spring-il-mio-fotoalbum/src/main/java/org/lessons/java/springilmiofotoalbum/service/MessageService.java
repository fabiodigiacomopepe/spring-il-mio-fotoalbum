package org.lessons.java.springilmiofotoalbum.service;

import org.lessons.java.springilmiofotoalbum.model.Message;
import org.lessons.java.springilmiofotoalbum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    // Istanzio automaticamente categoryRepository tramite Autowired
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) throws RuntimeException {
        return messageRepository.save(message);
    }

    // Ritorno lista di tutti i messaggi
    public List<Message> getAll() {
        return messageRepository.findAll();
    }
}