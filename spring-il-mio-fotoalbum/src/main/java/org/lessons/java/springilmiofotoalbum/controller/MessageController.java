package org.lessons.java.springilmiofotoalbum.controller;

import org.lessons.java.springilmiofotoalbum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    // Rotta "/messages" (GET - AL CARICAMENTO DELLA PAGINA DA NAVBAR)
    @GetMapping
    public String index(Model model) {
        // Passo al model la lista dei messaggi per popolare pagina
        model.addAttribute("messageList", messageService.getAll());
        return "messages/list";
    }
}
