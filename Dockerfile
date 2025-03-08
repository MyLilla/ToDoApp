FROM maven:3.9.6-eclipse-temurin-21 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package

FROM tomcat:10.1.36-jdk21

COPY --from=MAVEN_BUILD /target/ToDo.war /usr/local/tomcat/webapps/
