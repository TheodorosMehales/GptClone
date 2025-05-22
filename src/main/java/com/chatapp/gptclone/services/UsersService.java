package com.chatapp.gptclone.services;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.User;
import com.chatapp.gptclone.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepo;

    public List<User> findAllUsers() throws CloneException {
        try {
            return userRepo.findAll();
        } catch (Exception e) {
            throw new CloneException("Error fetching users", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public User findUserById(Long id) throws CloneException {
        try {
            return userRepo.findById(id)
                    .orElseThrow(() -> new CloneException("User not found: " + id, HttpStatus.NOT_FOUND));
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error fetching user", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public User createUser(User user) throws CloneException {
        try {
            return userRepo.save(user);
        } catch (Exception e) {
            throw new CloneException("Error creating user", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public User updateUser(Long id, User user) throws CloneException {
        try {
            User existing = findUserById(id);
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            existing.setPassword(user.getPassword());
            return userRepo.save(existing);
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error updating user", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void deleteUser(Long id) throws CloneException {
        User existing = findUserById(id);
        try {
            userRepo.delete(existing);
        } catch (Exception e) {
            throw new CloneException("Error deleting user", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepo.findByEmail(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found: " + username, e);
        }
    }
}