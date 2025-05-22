package com.chatapp.gptclone.model;


import jakarta.persistence.*;
import lombok.*;
//import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "thread_id", nullable = false)
    private Thread thread;

    @Column(name = "is_llm_generated", nullable = false)
    private Boolean isllmGenerated;



}
