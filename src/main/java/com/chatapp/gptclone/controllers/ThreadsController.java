package com.chatapp.gptclone.controllers;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Thread;
import com.chatapp.gptclone.services.ThreadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {
    @Autowired
    private ThreadsService threadsService;

    @GetMapping
    public ResponseEntity<List<Thread>> getAllThreads() throws CloneException {
        return ResponseEntity.ok(threadsService.findAllThreads());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thread> getThreadById(@PathVariable Long id) throws CloneException {
        return ResponseEntity.ok(threadsService.findThreadById(id));
    }

    @PostMapping
    public ResponseEntity<Thread> createThread(@RequestBody Thread thread) throws CloneException {
        Thread created = threadsService.createThread(thread);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thread> updateThread(@PathVariable Long id, @RequestBody Thread thread) throws CloneException {
        return ResponseEntity.ok(threadsService.updateThread(id, thread));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) throws CloneException {
        threadsService.deleteThread(id);
        return ResponseEntity.noContent().build();
    }
}