package com.chatapp.gptclone.repositories;


import com.chatapp.gptclone.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {}