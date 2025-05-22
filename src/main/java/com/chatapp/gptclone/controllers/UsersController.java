package com.chatapp.gptclone.controllers;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.TokenDTO;
import com.chatapp.gptclone.model.User;
import com.chatapp.gptclone.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private UsersService usersService;
    private JwtEncoder jwtEncoder;

    @Autowired
    public UsersController(UsersService usersService, JwtEncoder jwtEncoder) {
        this.usersService = usersService;
        this.jwtEncoder = jwtEncoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() throws CloneException {
        return ResponseEntity.ok(usersService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws CloneException {
        return ResponseEntity.ok(usersService.findUserById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User users) throws CloneException {
        User created = usersService.createUser(users);
        return ResponseEntity.status(201).body(created);
    }

    @PostMapping("/login")
    public TokenDTO login(Authentication authentication) throws CloneException {

        User loggedInUser = ((User)authentication.getPrincipal());
        // 2) Build JWT claims
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(6, ChronoUnit.HOURS))
                .subject(loggedInUser.getUsername())
//                .claim("roles", auth.getAuthorities().stream()
//                        .map(a -> a.getAuthority()).toList())
                .build();

        // 3) Encode & return token
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims))
                .getTokenValue();

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return tokenDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws CloneException {
        return ResponseEntity.ok(usersService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws CloneException {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}