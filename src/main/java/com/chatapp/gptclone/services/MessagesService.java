package com.chatapp.gptclone.services;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Message;
import com.chatapp.gptclone.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Message;
import com.chatapp.gptclone.model.Thread;
import com.chatapp.gptclone.model.dto.ChatCompletionRequest;
import com.chatapp.gptclone.model.dto.ChatMessage;
import com.chatapp.gptclone.model.dto.ChatCompletionResponse;
import com.chatapp.gptclone.repositories.MessagesRepository;
import com.chatapp.gptclone.repositories.ThreadsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessagesService {


    private final MessagesRepository messageRepo;
    private final ThreadsRepository threadRepo;
    private final RestTemplate restTemplate;

    @Value("${enable.openai}")
    private boolean openaiEnabled;

    @Value("${openai.api.url}")
    private String openAiUrl;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.request.temperature}")
    private Double temperature;

    @Value("${openai.request.max_tokens}")
    private Integer maxTokens;

    public MessagesService(MessagesRepository messageRepo,
                           ThreadsRepository threadRepo,
                           RestTemplate restTemplate) {
        this.messageRepo = messageRepo;
        this.threadRepo = threadRepo;
        this.restTemplate = restTemplate;
    }

    public Message sendMessage(Long threadId, String userContent) throws CloneException {
//        if (!openaiEnabled) {
//            throw new CloneException("OpenAI integration is disabled", HttpStatus.SERVICE_UNAVAILABLE, null);
//        }

        Thread thread = threadRepo.findById(threadId)
                .orElseThrow(() -> new CloneException("Thread not found", HttpStatus.NOT_FOUND, null));

        // Persist user message
        Message userMsg = Message.builder()
                .content(userContent)
                .thread(thread)
                .isllmGenerated(false)
                .build();
        messageRepo.save(userMsg);

        // Build chat history
        List<Message> history = messageRepo.findByThreadIdOrderByIdAsc(threadId);
        List<ChatMessage> messages = history.stream()
                .map(m -> new ChatMessage(
                        m.getIsllmGenerated() ? "assistant" : "user",
                        m.getContent()))
                .collect(Collectors.toList());

        // Prepare OpenAI request
        ChatCompletionRequest completionRequest = new ChatCompletionRequest();
        completionRequest.setModel(model);
        completionRequest.setMessages(messages);
        completionRequest.setTemperature(temperature);
        completionRequest.setMaxTokens(maxTokens);

        HttpEntity<ChatCompletionRequest> requestEntity = new HttpEntity<>(completionRequest);
        ResponseEntity<ChatCompletionResponse> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(openAiUrl, requestEntity, ChatCompletionResponse.class);
        } catch (Exception e) {
            throw new CloneException("OpenAI request failed", HttpStatus.BAD_GATEWAY, e);
        }

        String aiContent = responseEntity.getBody()
                .getChoices().get(0).getMessage().getContent();

        // Persist assistant message
        Message assistantMsg = Message.builder()
                .content(aiContent)
                .thread(thread)
                .isllmGenerated(true)
                .build();
        messageRepo.save(assistantMsg);

        return assistantMsg;
    }

    // CRUD Operations
    public List<Message> findAllMessages() throws CloneException {
        try {
            return messageRepo.findAll();
        } catch (Exception e) {
            throw new CloneException("Error fetching messages", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Message findMessageById(Long id) throws CloneException {
        return messageRepo.findById(id)
                .orElseThrow(() -> new CloneException("Message not found: " + id, HttpStatus.NOT_FOUND, null));
    }

    public Message createMessage(Message message) throws CloneException {
        try {
            return messageRepo.save(message);
        } catch (Exception e) {
            throw new CloneException("Error creating message", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Message updateMessage(Long id, Message message) throws CloneException {
        Message existing = findMessageById(id);
        existing.setContent(message.getContent());
        existing.setThread(message.getThread());
        existing.setIsllmGenerated(message.getIsllmGenerated());
        try {
            return messageRepo.save(existing);
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
