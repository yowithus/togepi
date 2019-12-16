# Togepi

Togepi is a sample project intended to implement various functionality of Spring features.

## Features
```
Spring
- Data (JPA)
- Batch
- Scheduling
- AOP
- AMQP (RabbitMQ)
- Kafka

Others
- AsyncHttp
- OpenCSV
```

## Stacks
```
Language: Java 8
Framework: Spring
Database: PostgreSQL
Build tool: Maven
Others: RabbitMQ, Kafka, Docker
```

## Usage

Run normally
```
mvn clean package -DskipTests -Pdev
java -jar togepi-0.0.1-SNAPSHOT.jar
```

Run via Docker
```
mvn clean package -DskipTests -Pdocker dockerfile:build
docker run --rm -p 8080:8080 --name togepi springio/togepi
```

Access at
```http://localhost:8080```