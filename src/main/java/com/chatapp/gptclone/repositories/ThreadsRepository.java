package com.chatapp.gptclone.repositories;

import com.chatapp.gptclone.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface ThreadsRepository extends JpaRepository<Thread, Long> {
//    Page<Thread> findBySubjectContainingIgnoreCase(String keyword, Pageable pageable);
}

