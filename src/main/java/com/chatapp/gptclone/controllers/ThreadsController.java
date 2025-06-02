package com.chatapp.gptclone.controllers;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Message;
import com.chatapp.gptclone.model.Thread;
import com.chatapp.gptclone.model.dto.ThreadWithMessages;
import com.chatapp.gptclone.services.ThreadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {

    private final ThreadsService threadsService;

    @Autowired
    public ThreadsController(ThreadsService threadsService) {
        this.threadsService = threadsService;
    }

    @GetMapping
    public ResponseEntity<List<Thread>> getAllThreads() throws CloneException {
        return ResponseEntity.ok(threadsService.findAllThreads());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThreadWithMessages> getThreadById(@PathVariable Long id) throws CloneException {
        return ResponseEntity.ok(threadsService.findThreadById(id));
    }

    @PostMapping
    public ResponseEntity<Thread> createThread(@RequestBody Thread thread) throws CloneException {
        Thread created = threadsService.createThread(thread);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thread> updateThread(@PathVariable Long id,
                                               @RequestBody Thread thread) throws CloneException {
        return ResponseEntity.ok(threadsService.updateThread(id, thread));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) throws CloneException {
        threadsService.deleteThread(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a message to the specified thread.
     */
    @PostMapping("/{id}/messages")
    public ResponseEntity<Message> addMessage(@PathVariable Long id,
                                              @RequestBody Message payload) throws CloneException {
        Message saved = threadsService.addMessageToThread(id, payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * List messages in the specified thread.
     */
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long id) throws CloneException {
//        List<Message> messages = threadsService.findThreadById(id).getMessages();
      return ResponseEntity.ok(new ArrayList<>());
    }
}
