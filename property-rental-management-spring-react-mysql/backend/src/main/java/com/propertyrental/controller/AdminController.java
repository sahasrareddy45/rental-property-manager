package com.propertyrental.controller;

import com.propertyrental.entity.Property;
import com.propertyrental.entity.PropertyStatus;
import com.propertyrental.entity.RentalRequest;
import com.propertyrental.entity.Payment;
import com.propertyrental.entity.Complaint;
import com.propertyrental.entity.Role;
import com.propertyrental.entity.User;
import com.propertyrental.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository users;
    private final PropertyRepository properties;
    private final RentalRequestRepository rentals;
    private final PaymentRepository payments;
    private final ComplaintRepository complaints;

    public AdminController(UserRepository users,PropertyRepository properties,RentalRequestRepository rentals,
                           PaymentRepository payments,ComplaintRepository complaints){
        this.users=users;this.properties=properties;this.rentals=rentals;this.payments=payments;this.complaints=complaints;
    }

    @GetMapping("/users")
    public List<User> users(){return users.findAll();}

    @GetMapping("/properties")
    public List<Property> properties(){return properties.findAll();}

    @PutMapping("/properties/{id}/verify")
    public Property verify(@PathVariable Long id,@RequestParam PropertyStatus status){
        Property p=properties.findById(id).orElseThrow();p.setStatus(status);return properties.save(p);
    }

    @GetMapping("/rentals")
    public List<RentalRequest> rentals(){return rentals.findAll();}

    @GetMapping("/payments")
    public List<Payment> payments(){return payments.findAll();}

    @GetMapping("/complaints")
    public List<Complaint> complaints(){return complaints.findAll();}

    @GetMapping("/reports")
    public Map<String,Long> reports(){
        return Map.of(
            "users",users.count(),
            "owners",(long) users.findByRole(Role.OWNER).size(),
            "tenants",(long) users.findByRole(Role.TENANT).size(),
            "properties",properties.count(),
            "rentals",rentals.count(),
            "payments",payments.count(),
            "complaints",complaints.count()
        );
    }
}
