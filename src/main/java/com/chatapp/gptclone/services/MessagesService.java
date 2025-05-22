package com.chatapp.gptclone.services;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Message;
import com.chatapp.gptclone.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messageRepo;

    public List<Message> findAllMessages() throws CloneException {
        try {
            return messageRepo.findAll();
        } catch (Exception e) {
            throw new CloneException("Error fetching messages", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Message findMessageById(Long id) throws CloneException {
        try {
            return messageRepo.findById(id)
                    .orElseThrow(() -> new CloneException("Message not found: " + id, HttpStatus.NOT_FOUND));
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error fetching message", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Message createMessage(Message message) throws CloneException {
        try {
            return messageRepo.save(message);
        } catch (Exception e) {
            throw new CloneException("Error creating message", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Message updateMessage(Long id, Message message) throws CloneException {
        try {
            Message existing = findMessageById(id);
            existing.setContent(message.getContent());
            existing.setThread(message.getThread());
            existing.setIsllmGenerated(message.getIsllmGenerated());
            return messageRepo.save(existing);
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error updating message", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void deleteMessage(Long id) throws CloneException {
        Message existing = findMessageById(id);
        try {
            messageRepo.delete(existing);
        } catch (Exception e) {
            throw new CloneException("Error deleting message", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}