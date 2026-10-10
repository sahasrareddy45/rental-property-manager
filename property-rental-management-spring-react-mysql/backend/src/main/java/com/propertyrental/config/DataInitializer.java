package com.propertyrental.config;

import com.propertyrental.entity.Property;
import com.propertyrental.entity.PropertyStatus;
import com.propertyrental.entity.Role;
import com.propertyrental.entity.User;
import com.propertyrental.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(UserRepository users, PropertyRepository properties, PasswordEncoder encoder) {
        return args -> {
            User admin = users.findByEmail("admin@rental.com").orElseGet(() -> {
                User u=new User("System Admin","admin@rental.com",encoder.encode("admin123"),Role.ADMIN);
                return users.save(u);
            });
            User owner = users.findByEmail("owner@rental.com").orElseGet(() -> {
                User u=new User("Demo Owner","owner@rental.com",encoder.encode("owner123"),Role.OWNER);
                return users.save(u);
            });
            User tenant = users.findByEmail("tenant@rental.com").orElseGet(() -> {
                User u=new User("Demo Tenant","tenant@rental.com",encoder.encode("tenant123"),Role.TENANT);
                return users.save(u);
            });

            if(properties.count()==0){
                Property p=new Property();
                p.setTitle("Modern 2 BHK Apartment");
                p.setDescription("Spacious apartment near public transport, schools and shopping.");
                p.setLocation("Hyderabad");
                p.setMonthlyRent(18000.0);
                p.setPropertyType("Apartment");
                p.setBedrooms(2); p.setBathrooms(2);
                p.setImageUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267");
                p.setAvailable(true); p.setStatus(PropertyStatus.VERIFIED); p.setOwner(owner);
                properties.save(p);

                Property q=new Property();
                q.setTitle("Family Independent House");
                q.setDescription("Comfortable independent house suitable for a family.");
                q.setLocation("Secunderabad");
                q.setMonthlyRent(25000.0);
                q.setPropertyType("House");
                q.setBedrooms(3); q.setBathrooms(2);
                q.setImageUrl("https://images.unsplash.com/photo-1564013799919-ab600027ffc6");
                q.setAvailable(true); q.setStatus(PropertyStatus.VERIFIED); q.setOwner(owner);
                properties.save(q);
            }
        };
    }
}
