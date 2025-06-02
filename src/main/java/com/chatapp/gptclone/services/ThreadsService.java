package com.chatapp.gptclone.services;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Message;
import com.chatapp.gptclone.model.Thread;
import com.chatapp.gptclone.model.dto.ThreadWithMessages;
import com.chatapp.gptclone.model.dto.UserDto;
import com.chatapp.gptclone.repositories.MessagesRepository;
import com.chatapp.gptclone.repositories.ThreadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThreadsService {

    private final ThreadsRepository threadRepo;
    private final MessagesRepository messageRepo;

    @Autowired
    public ThreadsService(ThreadsRepository threadRepo,
                          MessagesRepository messageRepo) {
        this.threadRepo = threadRepo;
        this.messageRepo = messageRepo;
    }

    public List<Thread> findAllThreads() throws CloneException {
        try {
            return threadRepo.findAll();
        } catch (Exception e) {
            throw new CloneException("Error fetching threads", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ThreadWithMessages findThreadById(Long id) throws CloneException {
        try {
            Thread thread =  threadRepo.findById(id)
                    .orElseThrow(() -> new CloneException("Thread not found: " + id, HttpStatus.NOT_FOUND));
           List<Message> messageList = messageRepo.findByThreadIdOrderByIdAsc(thread.getId());
           return ThreadWithMessages.builder()
                    .id(thread.getId())
                   .name(thread.getName())
                   .messages(messageList)
                   .user(new UserDto())
                   .build();
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error fetching thread", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Thread createThread(Thread thread) throws CloneException {
        try {
            return threadRepo.save(thread);
        } catch (Exception e) {
            throw new CloneException("Error creating thread", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Thread updateThread(Long id, Thread thread) throws CloneException {
        try {
            Thread existing =  threadRepo.findById(id)
                    .orElseThrow(() -> new CloneException("Thread not found: " + id, HttpStatus.NOT_FOUND));
            existing.setName(thread.getName());
            existing.setUser(thread.getUser());
            // any other fields to update
            return threadRepo.save(existing);
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error updating thread", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void deleteThread(Long id) throws CloneException {
        Thread existing =  threadRepo.findById(id)
                .orElseThrow(() -> new CloneException("Thread not found: " + id, HttpStatus.NOT_FOUND));
        try {
            threadRepo.delete(existing);
        } catch (Exception e) {
            throw new CloneException("Error deleting thread", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Store a new message in the given thread.
     * @param threadId the ID of the thread to add the message to
     * @param message the message to store (must contain content and isLlmGenerated flag)
     * @return the persisted Message
     * @throws CloneException if the thread is not found or save fails
     */
    @Transactional
    public Message addMessageToThread(Long threadId, Message message) throws CloneException {
        // Find parent thread
        Thread thread = threadRepo.findById(threadId)
                .orElseThrow(() -> new CloneException("Thread not found: " + threadId, HttpStatus.NOT_FOUND));
        try {
            // Associate and save
            message.setThread(thread);
            return messageRepo.save(message);
        } catch (Exception e) {
            throw new CloneException("Error saving message to thread", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
