package com.propertyrental.repository;

import com.propertyrental.entity.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentalRequestRepository extends JpaRepository<RentalRequest,Long> {
    List<RentalRequest> findByTenantId(Long tenantId);
    List<RentalRequest> findByPropertyOwnerId(Long ownerId);
}
