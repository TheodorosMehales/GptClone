package com.chatapp.gptclone.model.dto;

import com.chatapp.gptclone.model.Message;
import com.chatapp.gptclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadWithMessages {
    private Long id;


    private Instant createdAt;


    private UserDto user;


    private String name;
    private List<Message> messages;

}
