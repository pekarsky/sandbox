FROM openjdk:11-jdk AS builder
ARG APPJAR=build/libs/*.jar
COPY ${APPJAR} /app.jar

VOLUME /tmp
ENTRYPOINT ["java", "-jar","/app.jar"]