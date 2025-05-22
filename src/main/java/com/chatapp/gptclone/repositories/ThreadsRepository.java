package com.chatapp.gptclone.repositories;

import com.chatapp.gptclone.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadsRepository extends JpaRepository<Thread, Long> {}