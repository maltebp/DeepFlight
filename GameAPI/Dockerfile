# FROM maven:3.3.9-jdk-8 as build
FROM openjdk:8

WORKDIR /usr/src/gameapi

COPY GameAPI.jar .

# RUN mvn install -Dskiptests

CMD ["java", "-jar", "GameAPI.jar"]

EXPOSE 10000

