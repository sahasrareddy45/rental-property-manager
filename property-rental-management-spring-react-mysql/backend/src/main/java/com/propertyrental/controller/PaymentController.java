package com.propertyrental.controller;

import com.propertyrental.entity.*;
import com.propertyrental.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentRepository payments;
    private final RentalRequestRepository rentals;

    public PaymentController(PaymentRepository payments,RentalRequestRepository rentals){
        this.payments=payments;this.rentals=rentals;
    }

    @PostMapping
    public ResponseEntity<?> pay(@RequestParam Long rentalId,@RequestParam Double amount,
                                 @RequestParam String method){
        RentalRequest r=rentals.findById(rentalId).orElseThrow();
        if(r.getStatus()!=RentalStatus.APPROVED)
            return ResponseEntity.badRequest().body(Map.of("message","Rental request is not approved"));
        Payment p=new Payment();p.setRentalRequest(r);p.setAmount(amount);p.setPaymentMethod(method);
        return ResponseEntity.ok(payments.save(p));
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Payment> tenantPayments(@PathVariable Long tenantId){
        return payments.findByRentalRequestTenantId(tenantId);
    }
}
