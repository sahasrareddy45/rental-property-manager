package com.propertyrental.controller;

import com.propertyrental.entity.*;
import com.propertyrental.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalRequestRepository rentals;
    private final PropertyRepository properties;
    private final UserRepository users;

    public RentalController(RentalRequestRepository rentals,PropertyRepository properties,UserRepository users){
        this.rentals=rentals;this.properties=properties;this.users=users;
    }

    @PostMapping("/request")
    public ResponseEntity<?> request(@RequestParam Long tenantId,@RequestParam Long propertyId,
                                     @RequestBody(required=false) Map<String,String> body){
        User tenant=users.findById(tenantId).orElseThrow();
        Property p=properties.findById(propertyId).orElseThrow();
        if(tenant.getRole()!=Role.TENANT) return ResponseEntity.badRequest().body(Map.of("message","Only tenants can request"));
        if(p.getStatus()!=PropertyStatus.VERIFIED || !p.isAvailable())
            return ResponseEntity.badRequest().body(Map.of("message","Property is not available"));
        RentalRequest r=new RentalRequest(); r.setTenant(tenant); r.setProperty(p);
        r.setMessage(body==null?null:body.get("message"));
        return ResponseEntity.ok(rentals.save(r));
    }

    @GetMapping("/tenant/{tenantId}")
    public List<RentalRequest> tenant(@PathVariable Long tenantId){return rentals.findByTenantId(tenantId);}

    @GetMapping("/owner/{ownerId}")
    public List<RentalRequest> owner(@PathVariable Long ownerId){return rentals.findByPropertyOwnerId(ownerId);}

    @PutMapping("/{id}/status")
    public ResponseEntity<?> status(@PathVariable Long id,@RequestParam RentalStatus status){
        RentalRequest r=rentals.findById(id).orElseThrow();
        r.setStatus(status);
        if(status==RentalStatus.APPROVED){
            r.getProperty().setAvailable(false);
            r.getProperty().setStatus(PropertyStatus.RENTED);
            properties.save(r.getProperty());
        }
        return ResponseEntity.ok(rentals.save(r));
    }
}
