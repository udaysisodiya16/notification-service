# Notification Service

A Spring Boot-based microservice for managing user notifications via email. The service consumes Kafka messages for
events such as registration confirmations and order updates and integrates with third-party platforms for notification
delivery.

---

## Features

- **Kafka Integration**: Consumes notification events from a Kafka topic.
- **Email Notifications**: Sends emails using JavaMail API.
- **Scalability**: Supports multiple notification types with a modular design.
- **Extensibility**: Easily add support for other notification types or platforms.

---

## Prerequisites

- **Java**: JDK 17 or higher.
- **Maven**: Version 3.6+.
- **Kafka**: A running Kafka cluster.

---

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/udaysisodiya16/notification-service.git
```

### 2. Configure Application Properties

Update `application.properties` configuration to connect with the Kafka instance.

```properties
# Kafka bootstrap server URL
spring.kafka.bootstrap-servers=localhost:9092
```

Add below kafka topics to `application.properties` file :

```properties
# Kafka Topics
kafka.topic.user.signup.notification=user_signup_notification
kafka.topic.password.reset.notification=password_reset_notification
kafka.topic.order.notification=order_notification
```

### 3. Start Kafka in local

**Start Zookeeper :**
Kafka requires Zookeeper to manage brokers. Start Zookeeper using the default configuration:

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

**Start Kafka Broker :**
In another terminal, start the Kafka server

```bash
bin/kafka-server-start.sh config/server.properties
```

### 4. Build the Project

```bash
mvn clean package
```

### 4. Run the Application

```bash
java -jar target/notificationservice-0.0.1-SNAPSHOT.jar
```

---

## Usage

### Email Notifications

1. Send an event to any of the above Kafka topic with `type: EMAIL`.
2. The service will parse the event and send an email using JavaMail API.

---

## Extending the Service

1. **Add a New Notification Type**: Update the `NotificationType` enum and add handling logic in the
   `NotificationService`.
2. **Integrate a New Platform**: Implement a new service (e.g., `PushNotificationService`) and add it to
   `NotificationService`.

---
