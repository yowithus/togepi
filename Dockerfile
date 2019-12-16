FROM maven:3.6.1-jdk-8-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Pdocker

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/togepi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]