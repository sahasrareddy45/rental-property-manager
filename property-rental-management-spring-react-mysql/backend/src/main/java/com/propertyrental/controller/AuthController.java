package com.propertyrental.controller;

import com.propertyrental.dto.*;
import com.propertyrental.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service){this.service=service;}

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){
        try{return ResponseEntity.ok(service.register(r));}
        catch(Exception e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest r){
        try{return ResponseEntity.ok(service.login(r));}
        catch(Exception e){return ResponseEntity.status(401).body(Map.of("message",e.getMessage()));}
    }
}
