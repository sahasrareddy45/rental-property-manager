# Property Rental Management Platform

Complete Java Full Stack project based on the supplied project specification.

## Technologies
- Frontend: React + Vite
- Backend: Java 21 + Spring Boot 3.5.6
- Database: MySQL
- API: REST
- ORM: Spring Data JPA
- Security: Spring Security + BCrypt

## Features
### Tenant
Register/login, search verified properties, view details, request rental, track requests, pay approved rent, submit complaints.

### Property Owner
Register/login, add properties, update/manage properties through APIs, view rental requests, approve/reject requests.

### Administrator
Login, view users/properties/rentals/payments/complaints, verify/reject properties, resolve complaints, view reports.

## Run
1. Install Java 21, Maven, Node.js and MySQL.
2. Make sure MySQL Server is running.
3. If your MySQL root password is not `root`, set an environment variable before starting backend:
   Windows CMD:
   `set DB_PASSWORD=YOUR_PASSWORD`
4. Backend:
   `cd backend`
   `mvnw.cmd spring-boot:run`
5. Frontend:
   `cd frontend`
   `npm install`
   `npm run dev`
6. Open http://localhost:5173

## Demo accounts
Admin: admin@rental.com / admin123
Owner: owner@rental.com / owner123
Tenant: tenant@rental.com / tenant123

The database is automatically created as `property_rental_db` when the MySQL user has permission to create databases.
