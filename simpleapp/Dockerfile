FROM openjdk:11-jdk AS builder
#WORKDIR target/dependency
ARG APPJAR=build/libs/*.jar
COPY ${APPJAR} /app.jar

VOLUME /tmp
ENTRYPOINT ["java", "-jar","/app.jar"]