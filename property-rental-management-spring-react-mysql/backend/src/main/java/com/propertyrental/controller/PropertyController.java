package com.propertyrental.controller;

import com.propertyrental.entity.*;
import com.propertyrental.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyRepository properties;
    private final UserRepository users;

    public PropertyController(PropertyRepository properties, UserRepository users){
        this.properties=properties; this.users=users;
    }

    @GetMapping
    public List<Property> all(@RequestParam(required=false) String location,
                              @RequestParam(required=false) Double maxRent,
                              @RequestParam(required=false) String type) {
        if(location!=null && maxRent!=null && type!=null)
            return properties.findByLocationContainingIgnoreCaseAndMonthlyRentLessThanEqualAndPropertyTypeContainingIgnoreCase(
                location,maxRent,type);
        return properties.findByStatusAndAvailableTrue(PropertyStatus.VERIFIED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable Long id){
        return properties.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{ownerId}")
    public List<Property> ownerProperties(@PathVariable Long ownerId){
        return properties.findByOwnerId(ownerId);
    }

    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<?> create(@PathVariable Long ownerId,@RequestBody Property p){
        User owner=users.findById(ownerId).orElseThrow();
        if(owner.getRole()!=Role.OWNER) return ResponseEntity.badRequest().body(Map.of("message","Only owners can add properties"));
        p.setOwner(owner); p.setStatus(PropertyStatus.PENDING); p.setAvailable(true);
        return ResponseEntity.ok(properties.save(p));
    }

    @PutMapping("/{id}/owner/{ownerId}")
    public ResponseEntity<?> update(@PathVariable Long id,@PathVariable Long ownerId,@RequestBody Property p){
        Property old=properties.findById(id).orElseThrow();
        if(!old.getOwner().getId().equals(ownerId)) return ResponseEntity.status(403).body(Map.of("message","Not your property"));
        old.setTitle(p.getTitle()); old.setDescription(p.getDescription()); old.setLocation(p.getLocation());
        old.setMonthlyRent(p.getMonthlyRent()); old.setPropertyType(p.getPropertyType());
        old.setBedrooms(p.getBedrooms()); old.setBathrooms(p.getBathrooms()); old.setImageUrl(p.getImageUrl());
        old.setAvailable(p.isAvailable());
        return ResponseEntity.ok(properties.save(old));
    }

    @DeleteMapping("/{id}/owner/{ownerId}")
    public ResponseEntity<?> delete(@PathVariable Long id,@PathVariable Long ownerId){
        Property p=properties.findById(id).orElseThrow();
        if(!p.getOwner().getId().equals(ownerId)) return ResponseEntity.status(403).build();
        properties.delete(p); return ResponseEntity.ok(Map.of("message","Property deleted"));
    }
}
