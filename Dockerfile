FROM openjdk:17-jdk-alpine
COPY build/libs/auth-service-0.0.1-SNAPSHOT.jar auth-service-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/auth-service-1.0.0.jar"]