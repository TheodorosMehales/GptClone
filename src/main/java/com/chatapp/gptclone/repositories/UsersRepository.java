package com.chatapp.gptclone.repositories;

import com.chatapp.gptclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
