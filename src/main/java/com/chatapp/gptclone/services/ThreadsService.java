package com.chatapp.gptclone.services;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Thread;
import com.chatapp.gptclone.repositories.ThreadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadsService {


    private ThreadsRepository threadRepo;

    @Autowired
    public ThreadsService(ThreadsRepository threadRepo) {
        this.threadRepo = threadRepo;
    }

    public List<Thread> findAllThreads() throws CloneException {
        try {
            return threadRepo.findAll();
        } catch (Exception e) {
            throw new CloneException("Error fetching threads", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Thread findThreadById(Long id) throws CloneException {
        try {
            return threadRepo.findById(id)
                    .orElseThrow(() -> new CloneException("Thread not found: " + id, HttpStatus.NOT_FOUND));
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
            Thread existing = findThreadById(id);
            return threadRepo.save(existing);
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error updating thread", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void deleteThread(Long id) throws CloneException {
        Thread existing = findThreadById(id);
        try {
            threadRepo.delete(existing);
        } catch (Exception e) {
            throw new CloneException("Error deleting thread", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
