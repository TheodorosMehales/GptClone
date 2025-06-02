package com.chatapp.gptclone.controllers;
import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Message;
import com.chatapp.gptclone.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;


    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() throws CloneException {
        return ResponseEntity.ok(messagesService.findAllMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) throws CloneException {
        return ResponseEntity.ok(messagesService.findMessageById(id));
    }

//    @PostMapping
//    public ResponseEntity<Message> createMessage(@RequestBody Message message) throws CloneException {
//        Message created = messagesService.createMessage(message);
//        return ResponseEntity.status(201).body(created);
//    }

    @PostMapping("/threads/{threadId}")
    public ResponseEntity<Message> sendMessage(@PathVariable Long threadId,
                                               @RequestBody Message payload)
            throws CloneException {
        String content = payload.getContent();
        Message reply = messagesService.sendMessage(threadId, content);
        return ResponseEntity.status(201).body(reply);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message message) throws CloneException {
        return ResponseEntity.ok(messagesService.updateMessage(id, message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) throws CloneException {
        messagesService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
