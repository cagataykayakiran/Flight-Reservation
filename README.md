# Flight Reservation Backend Project

## Overview

The Flight Reservation Backend Project is a comprehensive system built with Spring Boot to manage flight bookings efficiently. It handles user management, flight scheduling, seat allocation, and order processing. The system ensures data integrity, real-time seat availability tracking, and seamless management of flight-related operations. Designed for scalability and reliability, it offers a robust foundation for any flight reservation platform.
## Features

#### 1. User Management

* Profile Management: Users can update their personal information.
* Order History: Users can view their past and current reservations.

#### 2. Flight Management
* Flight Scheduling: Create, update, and delete flight schedules.
* Seat Allocation: Manage seat configurations and availability for each flight.

#### 3. Seat Management
* Real-Time Availability: Track and update seat availability in real-time.
* Seat Classification: Differentiate seats based on class (Economy, Business).
* Seat Pricing: Manage dynamic pricing based on seat class and availability.

#### 4. Order Management
* Reservation Processing: Handle flight bookings and reservations.

## Tech Stack

* **Java 11**
* **Spring Boot**
* **Spring Data JPA**
* **Hibernate**
* **H2 Database** (in-memory database for testing and development)
* **Lombok**
* **Maven**

## How to Run

### Clone the Repository
```bash
git clone https://github.com/cagataykayakiran/Flight-Reservation
cd Flight-Reservation
````

## Build the Project
```bash
mvn clean install
````

## Run the Application
```bash
mvn spring-boot:run
````

The application will start on: `http://localhost:8080`

## Access H2 Console (For Testing)
* Navigate to: http://localhost:8080/api/v1/h2-console
* Database URL: `jdbc:h2:mem:flight-reservation`
* Username: `sa`
* Password: `(leave blank)`
