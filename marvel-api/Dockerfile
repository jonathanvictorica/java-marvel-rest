## Build project
FROM maven:3.8.3-amazoncorretto-17 as buildmvn
WORKDIR /albocomics
COPY . .
#REMOVE SKIPTEST WHEN JUNIT
RUN mvn clean install

FROM openjdk:17-alpine3.12
VOLUME /tmp
EXPOSE 8081
ADD target/marvel.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

