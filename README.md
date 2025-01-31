# E-Commerce Microservices Project

## Overview
This project is an e-commerce application built using microservices architecture. Each service is responsible for a specific domain within the system, ensuring scalability, flexibility, and fault tolerance. The services are containerized and orchestrated using Docker Compose.

---

## Microservices and Features

### 1. Discovery Server
- **Purpose**: Acts as a service registry for discovering and managing all microservices in the system.
- **Technology**: Eureka Discovery Server.

### 2. Auth Service
- **Purpose**: Handles user authentication and authorization.
- **Features**:
  - Stores user information in a PostgreSQL database.
  - Generates JWT tokens for secure communication.
- **Technology**: Spring Boot, PostgreSQL.

### 3. Product Service
- **Purpose**: Manages product information and inventory.
- **Features**:
  - Stores product data in a PostgreSQL database.
- **Technology**: Spring Boot, PostgreSQL.

### 4. Cart Service
- **Purpose**: Manages shopping cart functionality for users.
- **Features**:
  - Stores carts for authenticated users in MongoDB.
  - Stores carts for unauthenticated users in Redis.
- **Technology**: Spring Boot, MongoDB, Redis.

### 5. Order Service
- **Purpose**: Handles order creation and management.
- **Features**:
  - Stores orders in MongoDB.
  - Communicates order status to the Notification Service asynchronously using Kafka.
- **Technology**: Spring Boot, MongoDB, Kafka.

### 6. Notification Service
- **Purpose**: Sends email notifications to users regarding order status.
- **Features**:
  - Receives order status updates from the Order Service through Kafka.
  - Sends email notifications (email functionality not implemented yet).
- **Technology**: Spring Boot, Kafka.

---

## Additional Features

### Circuit Breaker
- **Purpose**: Ensures system stability and fault tolerance.
- **Implementation**: Configured for each microservice to handle failures gracefully.

### Docker Compose
- **Purpose**: Containerizes and orchestrates all microservices and their databases.
- **Components**:
  - PostgreSQL for Auth and Product Services.
  - MongoDB for Cart and Order Services.
  - Redis for Cart Service.
  - Kafka for asynchronous communication.

### Environment Configuration
- **Purpose**: Uses `.env` files to store environment-specific variables securely.

---

## Setup and Usage

### Prerequisites
1. Docker and Docker Compose installed.
2. Java 17 installed.
3. Maven installed.

### Steps to Run
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```
2. Build and start the containers:
   ```bash
   docker-compose up --build
   ```


---

## Future Improvements
1. Implement API endpoints for all services.
2. Add email functionality in the Notification Service.
3. Integrate frontend applications with these microservices.
4. Add more detailed monitoring and logging.

---

## Contributions
Contributions are welcome! Feel free to fork the repository, create a feature branch, and submit a pull request.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.
