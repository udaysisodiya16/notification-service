# Notification Service

A Spring Boot-based microservice for managing user notifications via email. The service consumes Kafka messages for
events such as registration confirmations and order updates and integrates with third-party platforms for notification
delivery.

---

## Features

- **Kafka Integration**: Consumes notification events from a Kafka topic.
- **Email Notifications**: Sends emails using Amazon SES.
- **SMS Notifications**: (Optional) Sends SMS using Twilio.
- **Scalability**: Supports multiple notification types with a modular design.
- **Extensibility**: Easily add support for other notification types or platforms.

---

## Prerequisites

- **Java**: JDK 17 or higher.
- **Maven**: Version 3.6+.
- **Kafka**: A running Kafka cluster with the topic `notification-events`.
- **Amazon SES**: An active AWS account and verified email addresses for sending emails.
- **Twilio** (Optional): An active Twilio account for sending SMS.

---

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-repo/notification-service.git
cd notification-service
```

### 2. Configure Application Properties

Edit the `src/main/resources/application.yml` file with your Kafka, Amazon SES, and Twilio configurations:

```yaml
spring:
  kafka:
    bootstrap-servers: <your-kafka-server>
    consumer:
      group-id: notification-group

aws:
  ses:
    region: us-east-1

twilio:
  account-sid: <your-twilio-account-sid>
  auth-token: <your-twilio-auth-token>
  from-number: <your-twilio-phone-number>
```

### 3. Build the Project

```bash
mvn clean package
```

### 4. Run the Application

```bash
java -jar target/notification-service-0.0.1-SNAPSHOT.jar
```

---

## Kafka Topic Configuration

Create a Kafka topic named `notification-events`:

```bash
kafka-topics --create --topic notification-events --bootstrap-server <your-kafka-server> --partitions 3 --replication-factor 1
```

Test sending a message to the topic (JSON format example):

```bash
kafka-console-producer --broker-list <your-kafka-server> --topic notification-events
>{"type":"EMAIL","emailDetails":{"from":"no-reply@yourdomain.com","to":"user@example.com","subject":"Welcome!","body":"Thank you for registering!"}}
```

---

## Usage

### Email Notifications

1. Send an event to the `notification-events` Kafka topic with `type: EMAIL`.
2. The service will parse the event and send an email using Amazon SES.

### SMS Notifications (Optional)

1. Send an event to the `notification-events` Kafka topic with `type: SMS`.
2. The service will parse the event and send an SMS using Twilio.

---

## Project Structure

```plaintext
src/main/java
├── config                # Configuration files for Kafka and external platforms
├── controller            # (Optional) REST endpoints for debugging or manual triggers
├── dto                   # Data Transfer Objects for notifications
├── kafka                 # Kafka consumer and listener implementations
├── service               # Notification handling and third-party integration logic
└── NotificationServiceApplication.java # Main Spring Boot application class
```

---

## Extending the Service

1. **Add a New Notification Type**: Update the `NotificationType` enum and add handling logic in the
   `NotificationService`.
2. **Integrate a New Platform**: Implement a new service (e.g., `PushNotificationService`) and add it to
   `NotificationService`.

---

## Testing

### Unit Tests

Run unit tests with:

```bash
mvn test
```

### Integration Tests

1. Start Kafka locally or use a test container.
2. Use tools like Postman or Kafka CLI to simulate notification events.
3. Verify emails in your Amazon SES dashboard or SMS in Twilio logs.

---

## Best Practices

- **Retry Logic**: Implement Kafka dead-letter topics or use Spring Retry for transient failures.
- **Observability**: Integrate with tools like Prometheus and Grafana for monitoring.
- **Secrets Management**: Store sensitive configurations in AWS Secrets Manager or HashiCorp Vault.

---
