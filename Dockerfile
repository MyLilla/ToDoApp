FROM maven:3.9.6-amazoncorretto-21 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package

FROM tomcat:9.0.84-jdk21-corretto-al2

COPY --from=MAVEN_BUILD /target/ROOT.war /usr/local/tomcat/webapps/
