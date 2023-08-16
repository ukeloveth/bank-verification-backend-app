# Bank Verification Number Application

### Introduction
The Bank Verification Number (BVN) Application is designed to facilitate user registration, login, logout, and BVN verification. It provides RESTful APIs for these operations, allowing users to interact with the system securely.
---
* Table of Contents
* Endpoints
* Project Structure
* Getting Started
* Dependencies
* Testing
* Contributing
* Endpoints
* Register User
* Endpoint: POST /api/v1/users/signup

* Registers a new user with the provided information.

* Login User
* Endpoint: POST /api/v1/users/login

* Logs in a user with the provided credentials.

* Logout User
* Endpoint: POST /api/v1/users/logout

* Logs out a user by invalidating their session token.

* Verify User BVN
* Endpoint: POST /api/v1/users/verifyUserBvn

* Verifies the BVN (Bank Verification Number) of a logged-in user.

* For more detailed information on request and response payloads, refer to the code documentation.

# Project Structure
* The project is structured as follows:

`com.task.Bank.Verification.Number.Application.controllers`: Contains the main controller classes handling user and verification operations.
`com.task.Bank.Verification.Number.Application.dtos`: Data transfer objects used in the application.
`com.task.Bank.Verification.Number.Application.payload.request`: Request payload classes.
`com.task.Bank.Verification.Number.Application.payload.responses`: Response payload classes.
`com.task.Bank.Verification.Number.Application.services.impl`: Service implementation classes.
`com.task.Bank.Verification.Number.Application.webClient`: WebClient utility classes for external service integration.
* resources: Contains configuration files and application properties.

### Getting Started
* Clone the repository: git clone https://github.com/ukeloveth/bank-verification-backend-app.git
* Open the terminal and navigate to the folder containing the application.
* Configure your database settings in application.properties.
* Build and run the application: mvn spring-boot:run

### Dependencies
* Spring Boot Starter Web
* Spring Boot Starter Data JPA
* Spring Boot Starter Validation
* Spring Boot Starter Security
* Lombok
* Jackson Databind
* JWT (io.jsonwebtoken)
* HATEOAS (spring-boot-starter-hateoas)
* MySQL Connector
* Spring Boot Starter Test (for testing)
* JUnit Jupiter (for testing)
* Mockito Core (for testing)
* Spring Security Test (for testing)

### Testing
* The application includes unit tests and integration tests. To run tests, execute: mvn test

### Contributing
* Contributions are welcome! Please create an issue or pull request for any enhancements or bug fixes.