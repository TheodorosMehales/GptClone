package com.chatapp.gptclone.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
//import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "models")
public class Model {
    @Id
    @Column(length = 100)
    private String name;

    @Column(nullable = false)
    private String url;
}