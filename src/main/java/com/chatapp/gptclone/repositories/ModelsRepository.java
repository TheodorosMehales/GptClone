package com.chatapp.gptclone.repositories;

import com.chatapp.gptclone.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelsRepository extends JpaRepository<Model, String> {}