# Midas Core

## Overview
Midas Core is a Spring Boot Java application for the JPMC Advanced Software Engineering Forage program. This project provides a backend service with JPA persistence, Kafka messaging support, and RESTful endpoints.

## Project Structure
```
├── pom.xml                          # Maven build configuration
├── src/
│   ├── main/
│   │   ├── java/com/jpmc/midascore/
│   │   │   ├── MidasCoreApplication.java    # Main Spring Boot application
│   │   │   ├── component/
│   │   │   │   └── DatabaseConduit.java     # Database operations component
│   │   │   ├── controller/
│   │   │   │   └── HealthController.java    # REST health/status endpoints
│   │   │   ├── entity/
│   │   │   │   └── UserRecord.java          # User entity for JPA
│   │   │   ├── foundation/
│   │   │   │   ├── Balance.java             # Balance model
│   │   │   │   └── Transaction.java         # Transaction model
│   │   │   └── repository/
│   │   │       └── UserRepository.java      # User data repository
│   │   └── resources/
│   │       └── application.yml              # Spring configuration
│   └── test/
│       ├── java/com/jpmc/midascore/         # Test classes (Task1-5)
│       └── resources/test_data/             # Test data files
└── services/
    └── transaction-incentive-api.jar        # External service JAR
```

## Technology Stack
- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- H2 Database (in-memory)
- Spring Kafka
- Maven (mvnw wrapper)

## Running the Application
The application runs via the Maven wrapper:
```bash
./mvnw spring-boot:run -DskipTests
```

## Configuration
- Server port: 5000
- Database: H2 in-memory (jdbc:h2:mem:midasdb)
- H2 Console: Available at /h2-console

## API Endpoints
- `GET /` - Application info and status
- `GET /health` - Health check endpoint
- `GET /h2-console` - H2 database console (web interface)

## Development Notes
- The application is configured to run on port 5000 with address 0.0.0.0
- Test classes use TestContainers for Kafka testing
- The `-DskipTests` flag is used when running to avoid test compilation during development
