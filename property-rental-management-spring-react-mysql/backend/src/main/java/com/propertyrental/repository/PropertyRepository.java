package com.propertyrental.repository;

import com.propertyrental.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property,Long> {
    List<Property> findByStatusAndAvailableTrue(PropertyStatus status);
    List<Property> findByOwnerId(Long ownerId);
    List<Property> findByLocationContainingIgnoreCaseAndMonthlyRentLessThanEqualAndPropertyTypeContainingIgnoreCase(
        String location, Double rent, String type);
}
