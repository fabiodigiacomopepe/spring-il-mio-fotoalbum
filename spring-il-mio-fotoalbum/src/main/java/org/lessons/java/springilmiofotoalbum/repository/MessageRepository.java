package org.lessons.java.springilmiofotoalbum.repository;

import org.lessons.java.springilmiofotoalbum.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}