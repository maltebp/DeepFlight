# FROM maven:3.3.9-jdk-8 as build
FROM openjdk:8

WORKDIR /usr/src/userapi

#COPY UserAPI.jar .
COPY UserAPI.jar .
# RUN mvn install -Dskiptests

#CMD ["java", "-jar", "UserAPI.jar"]

CMD ["java","-jar","UserAPI.jar"]

EXPOSE 7000
EXPOSE 7080

