package com.chatapp.gptclone.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*; import lombok.*; //import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity @Data @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "threads")
 public class Thread {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @CreationTimestamp
 @Column(name = "created_at", nullable = false, updatable = false)
 private Instant createdAt;

 @ManyToOne(fetch = FetchType.LAZY, optional = false)
 @JoinColumn(name = "user_id", nullable = false)
 private User user;

 @Column(name = "name", nullable = false, unique = true)
 private String name;

// @OneToMany(
//         mappedBy = "thread",
//         cascade = CascadeType.ALL,
//         orphanRemoval = true
// )
// @OrderBy("id ASC")
// @JsonIgnoreProperties("thread")

// private List<Message> messages = new ArrayList<>();

}
