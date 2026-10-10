package com.propertyrental.controller;

import com.propertyrental.entity.*;
import com.propertyrental.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    private final ComplaintRepository complaints;
    private final UserRepository users;

    public ComplaintController(ComplaintRepository complaints,UserRepository users){
        this.complaints=complaints;this.users=users;
    }

    @PostMapping
    public Complaint create(@RequestParam Long userId,@RequestBody Complaint c){
        c.setUser(users.findById(userId).orElseThrow());
        c.setStatus("OPEN");
        return complaints.save(c);
    }

    @GetMapping
    public List<Complaint> all(){return complaints.findAll();}

    @GetMapping("/user/{userId}")
    public List<Complaint> user(@PathVariable Long userId){return complaints.findByUserId(userId);}

    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable Long id,@RequestParam String status){
        Complaint c=complaints.findById(id).orElseThrow();c.setStatus(status);return complaints.save(c);
    }
}
