package com.chatapp.gptclone.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    /**
     * JSON‐formatted user preferences.
     * Stored as JSONB in Postgres, but mapped here as String.
     * If you want a richer type (Map or JsonNode), you can add a converter.
     */
    @Column(name = "extra_preferences", columnDefinition = "jsonb")
    private String extraPreferences;

    // --- UserDetails methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return actual roles if you add them later
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        // we use email as the principal
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // or add your own logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // or add your own logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // or add your own logic
    }

    @Override
    public boolean isEnabled() {
        return true;  // or add your own logic
    }
}