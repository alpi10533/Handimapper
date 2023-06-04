FROM maven:3.9.1-sapmachine-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src/ ./src/
RUN mvn clean package -DskipTests

FROM openjdk:18.0.2.1-slim-bullseye
WORKDIR /app
COPY --from=build /app/target/Handimapper-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]