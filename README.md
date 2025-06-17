# 📬 Customer Notification Address Facade System

This project is a microservice-based backend application built with **Java (Spring Boot)** that centralizes and manages customer contact information and their notification preferences. It acts as a **single source of truth** for customer addresses, preferences, and delivery statuses, providing efficient APIs for communication with other systems.

---

## 🧩 Key Features

### 👤 User Management
- Create and manage admin accounts.
- Secure login functionality for admins using Spring Security.

### 👥 Customer Management
- Add new customers with contact details and notification preferences.
- Edit and update existing customer profiles.
- Delete customers from the system.
- View a full list of customers with their respective preferences.

### 🏠 Address Management
- Store and categorize multiple types of customer addresses: Email, SMS, Postal, etc.
- Update customer addresses.
- Delete obsolete or incorrect addresses.
- Retrieve all addresses associated with a customer.

### 📢 Preference Management
- Record customer preferences (opt-in/opt-out) for receiving different types of notifications (SMS, Email, Promotions).
- Update preferences upon customer request.
- View a customer’s current preferences.

### 🔌 API & Integration
- RESTful API endpoints for accessing customer addresses and preferences.
- Secure and authenticated access for external systems.
- Batch update capability for customer data synchronization.

### 🔔 Notification Tracking
- Track status of each notification: Delivered, Failed, Pending.
- Endpoints for querying delivery status per customer.
- Reports on delivery success rates and issues.

### 🔍 Search & Filtering
- Search customers by name, contact info, or preferences.
- Sort and filter results for more efficient management.

### 📊 Reporting
- Generate insights such as:
    - Customers opted in for each notification channel.
    - Delivery statistics and failure rates.

---

## 🛠️ Technical Details

### 🧱 Backend
- **Language:** Java
- **Framework:** Spring Boot
- **ORM:** Hibernate
- **Security:** Spring Security (JWT-based recommended)

### 🖥️ Frontend
- **UI Technology:** HTML, CSS, JavaScript

### 🗃️ Database
- **Type:** Relational
- **Options:** PostgreSQL
- **Schema:** Designed to support normalized relationships for customers, addresses, and preferences.

### 🔐 Security
- Role-based access control (admin-only access to APIs)
- Sensitive data encryption (e.g., hashed passwords)
- Input validation

### 🚀 Performance & Scalability
- Efficient query structure for low-latency data access.
- Supports concurrent admin operations.
- Designed to scale horizontally with growing data.

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java 17
- Maven
- PostgreSQL

### ⚙️ Setup

```bash
  git clone https://github.com/gvaso27/notifications.git
  cd notifications
```

### ⚙️ Run Back-End
```bash
  cd notifications
  mvn clean install
  mvn spring-boot:run
```

### ⚙️ Run Front-End
```bash
  cd front-end
  npx http-server -p 63342
```


### Accessing Swagger UI

After running the services, you can access the Swagger UI to interact with the API documentation and test the endpoints.

The Swagger UI is available at: http://localhost:8081/swagger-ui/index.html