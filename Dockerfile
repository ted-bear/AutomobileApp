FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

#COPY ./AutomobileApp-0.0.1-SNAPSHOT.jar app.jar for remote server
COPY ./target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
