package com.propertyrental.service;

import com.propertyrental.dto.*;
import com.propertyrental.entity.*;
import com.propertyrental.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository users, PasswordEncoder encoder) {
        this.users=users; this.encoder=encoder;
    }

    public Map<String,Object> register(RegisterRequest r) {
        if (users.findByEmail(r.email).isPresent())
            throw new RuntimeException("Email already registered");

        Role role;
        try { role = Role.valueOf(r.role.toUpperCase()); }
        catch(Exception e) { throw new RuntimeException("Role must be OWNER or TENANT"); }

        if (role == Role.ADMIN) throw new RuntimeException("Admin registration is not allowed");

        User u = new User(r.name, r.email, encoder.encode(r.password), role);
        users.save(u);
        return Map.of("message","Registration successful","userId",u.getId(),"role",u.getRole());
    }

    public Map<String,Object> login(LoginRequest r) {
        User u = users.findByEmail(r.email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!encoder.matches(r.password, u.getPassword()))
            throw new RuntimeException("Invalid email or password");
        return Map.of("message","Login successful","userId",u.getId(),
                "name",u.getName(),"email",u.getEmail(),"role",u.getRole());
    }
}
