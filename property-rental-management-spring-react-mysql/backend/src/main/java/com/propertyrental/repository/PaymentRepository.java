package com.propertyrental.repository;

import com.propertyrental.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByRentalRequestTenantId(Long tenantId);
}
