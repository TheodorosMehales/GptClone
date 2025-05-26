package com.chatapp.gptclone.repositories;

import com.chatapp.gptclone.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {
    // Retrieve all messages for a thread in chronological order
    List<Message> findByThreadIdOrderByIdAsc(Long threadId);
}
