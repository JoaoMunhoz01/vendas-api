FROM maven:3.9.6-eclipse-temurin-21 as maven-builder

ARG USER_HOME_DIR="/root"
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN mkdir -p /usr/src/api
RUN mkdir -p /$USER_HOME_DIR/.m2  && mkdir /$USER_HOME_DIR/.m2/repository

WORKDIR /usr/src/api

COPY . /usr/src/api
COPY settings.xml /$USER_HOME_DIR/.m2

RUN mvn clean install

FROM amazoncorretto:21

WORKDIR /api

COPY --from=maven-builder /usr/src/api/target/*.jar /api/

CMD ["java", "-jar", "-Dspring.profiles.active=hom", "-Xmx128m", "/api/carnefy-api-1.0-SNAPSHOT.jar"]

EXPOSE 8080
